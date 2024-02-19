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

import java.util.List;

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
    @Test
    public void testFindCustomersByPersonName() {
        // Create and persist person.
        String name = "Bob Johnson";
        Person bob = new Person(name);
        bob = personRepository.save(bob);

        // Create customers with the same person.
        String email1 = "customer1@emailprovider.ca";
        String password1 = "customer1Password";
        int creditCardNumber1 = 987654321;
        Customer customer1 = new Customer(email1, password1, bob, creditCardNumber1);
        customerRepository.save(customer1);

        String email2 = "customer2@emailprovider.ca";
        String password2 = "customer2Password";
        int creditCardNumber2 = 456789123;
        Customer customer2 = new Customer(email2, password2, bob, creditCardNumber2);
        customerRepository.save(customer2);

        // Find customers by person name.
        List<Customer> customers = customerRepository.findCustomersByPerson_Name(name);

        // Assert the list is not null and contains the expected number of customers.
        assertNotNull(customers);
        assertEquals(2, customers.size());

        // Assert that the customers in the list have the correct attributes.
        assertEquals(email1, customers.get(0).getEmail());
        assertEquals(password1, customers.get(0).getPassword());
        assertEquals(creditCardNumber1, customers.get(0).getCreditCardNumber());

        assertEquals(email2, customers.get(1).getEmail());
        assertEquals(password2, customers.get(1).getPassword());
        assertEquals(creditCardNumber2, customers.get(1).getCreditCardNumber());
    }
}
