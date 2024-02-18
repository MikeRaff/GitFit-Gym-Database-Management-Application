package ca.mcgill.ecse321.gymregistration.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gymregistration.dao.CustomerRepository;
import ca.mcgill.ecse321.gymregistration.dao.PersonRepository;
import ca.mcgill.ecse321.gymregistration.model.Customer;
import ca.mcgill.ecse321.gymregistration.model.Person;

@SpringBootTest
public class CustomerTests {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    @AfterEach
    private void clearDatabase() {
        customerRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadCustomer() {
        // Create and persist person.
        String name = "Jim";
        Person jim = new Person(name);
        jim = personRepository.save(jim);
        
        // Create customer.
        String email = "customeremail@emailprovider.net";
        String password = "password";
        int creditCardNumber = 123456789;
        Customer customer = new Customer(email, password, jim, creditCardNumber);
    
        // Save customer to database.
        customer = customerRepository.save(customer);
    
        // Read customer from database.
        Customer customerFromDB = customerRepository.findCustomerById(customer.getId());

        // Assert customer is not null and has correct non-null attributes.
        assertNotNull(customerFromDB);
        
        assertEquals(customer.getId(), customerFromDB.getId());
        assertEquals(email, customerFromDB.getEmail());
        assertEquals(password, customerFromDB.getPassword());
        assertEquals(creditCardNumber, customerFromDB.getCreditCardNumber());
        
        assertNotNull(customerFromDB.getPerson());
        //assertEquals(jim, customerFromDB.getPerson());    // compares addresses, not values

        // Assert person is not null and has correct attributes.
        assertNotNull(personRepository.findPersonById(jim.getId()));
        
        assertEquals(jim.getId(), customerFromDB.getPerson().getId());
        assertEquals(name, customerFromDB.getPerson().getName());
    }
}
