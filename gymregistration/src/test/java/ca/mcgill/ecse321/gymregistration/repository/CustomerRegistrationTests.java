package ca.mcgill.ecse321.gymregistration.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired; 

import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gymregistration.dao.ClassTypeRepository;
import ca.mcgill.ecse321.gymregistration.dao.CustomerRegistrationRepository;
import ca.mcgill.ecse321.gymregistration.dao.CustomerRepository;
import ca.mcgill.ecse321.gymregistration.dao.PersonRepository;
import ca.mcgill.ecse321.gymregistration.dao.SessionRepository;
import ca.mcgill.ecse321.gymregistration.model.ClassType;
import ca.mcgill.ecse321.gymregistration.model.Customer;
import ca.mcgill.ecse321.gymregistration.model.CustomerRegistration;
import ca.mcgill.ecse321.gymregistration.model.Person;
import ca.mcgill.ecse321.gymregistration.model.Session;

@SpringBootTest
public class CustomerRegistrationTests {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ClassTypeRepository classTypeRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private CustomerRegistrationRepository customerRegistrationRepository;

    @BeforeEach
    @AfterEach
    private void clearDatabase() {
        customerRegistrationRepository.deleteAll();
        customerRepository.deleteAll();
        personRepository.deleteAll();
        sessionRepository.deleteAll();
        classTypeRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadCustomerRegistration() {
        // Create and persist person.
        String personName = "Simon";
        Person simon = new Person(personName);
        simon = personRepository.save(simon);
        
        // Create and persist customer.
        String email = "customeremailaddress@emailprovider.org";
        String password = "password";
        int creditCardNumber = 987654321;
        Customer customerSimon = new Customer(email, password, simon, creditCardNumber);
        customerSimon = customerRepository.save(customerSimon);

        // Create and persist class type.
        String className = "Yoga";
        boolean isApproved = true;  // must be true in order to sign up for class.
        ClassType yoga = new ClassType(className, isApproved);
        yoga = classTypeRepository.save(yoga);

        // Create and persist session.
        Date sessionDate = Date.valueOf("2024-02-18");
        Time startTime = Time.valueOf("15:48:00");
        Time endTime = Time.valueOf("16:48:00");
        String sessionDescription = "A description of this session.";
        String sessionName = "Session name";
        String sessionLocation = "Where session takes place.";
        Session session = new Session(sessionDate, startTime, endTime, sessionDescription, sessionName, sessionLocation, yoga);
        session = sessionRepository.save(session);

        // Create customer registration.
        Date registrationDate = Date.valueOf("2024-02-17");
        CustomerRegistration customerRegistration = new CustomerRegistration(registrationDate, session, customerSimon);
    
        // Save customer registration to database.
        customerRegistration = customerRegistrationRepository.save(customerRegistration);
    
        // Read customer registration from database.
        CustomerRegistration customerRegistrationFromDB = customerRegistrationRepository.findCustomerRegistrationById(customerRegistration.getId());

        // Assert customer registration is not null and has correct non-null attributes.
        assertNotNull(customerRegistrationFromDB);
        
        assertEquals(customerRegistration.getId(), customerRegistrationFromDB.getId());
        assertEquals(registrationDate, customerRegistrationFromDB.getDate());
        
        assertNotNull(customerRegistrationFromDB.getSession());
        //assertEquals(session, customerRegistrationFromDB.getSession());   // compares addresses, not values
        assertNotNull(customerRegistrationFromDB.getCustomer());
        //assertEquals(customerSimon, customerRegistrationFromDB.getCustomer());    // compares addresses, not values

        // Assert session is not null and has correct non-null attributes.
        assertNotNull(sessionRepository.findSessionById(session.getId()));
        
        assertEquals(session.getId(), customerRegistrationFromDB.getSession().getId());
        assertEquals(sessionDate, customerRegistrationFromDB.getSession().getDate());
        assertEquals(startTime, customerRegistrationFromDB.getSession().getStartTime());
        assertEquals(endTime, customerRegistrationFromDB.getSession().getEndTime());
        assertEquals(sessionDescription, customerRegistrationFromDB.getSession().getDescription());
        assertEquals(sessionName, customerRegistrationFromDB.getSession().getName());
        assertEquals(sessionLocation, customerRegistrationFromDB.getSession().getLocation());
        
        assertNotNull(customerRegistrationFromDB.getSession().getClassType());
        //assertEquals(yoga, customerRegistrationFromDB.getSession().getClassType()); // compares addresses, not values

        // Assert class type is not null and has correct attributes.
        assertNotNull(classTypeRepository.findClassTypeById(yoga.getId()));
        
        assertEquals(yoga.getId(), customerRegistrationFromDB.getSession().getClassType().getId());
        assertEquals(className, customerRegistrationFromDB.getSession().getClassType().getName());
        assertEquals(isApproved, customerRegistrationFromDB.getSession().getClassType().getIsApproved());

        // Assert customer is not null and has correct non-null attributes.
        assertNotNull(customerRepository.findCustomerById(customerSimon.getId()));

        assertEquals(customerSimon.getId(), customerRegistrationFromDB.getCustomer().getId());
        assertEquals(email, customerRegistrationFromDB.getCustomer().getEmail());
        assertEquals(password, customerRegistrationFromDB.getCustomer().getPassword());
        assertEquals(creditCardNumber, customerRegistrationFromDB.getCustomer().getCreditCardNumber());
        
        assertNotNull(customerRegistrationFromDB.getCustomer().getPerson());
        //assertEquals(simon, customerRegistrationFromDB.getCustomer().getPerson());    // compares addresses, not values

        // Assert person is not null and has correct attributes.
        assertNotNull(personRepository.findPersonById(simon.getId()));
        
        assertEquals(simon.getId(), customerRegistrationFromDB.getCustomer().getPerson().getId());
        assertEquals(personName, customerRegistrationFromDB.getCustomer().getPerson().getName());
    }
}
