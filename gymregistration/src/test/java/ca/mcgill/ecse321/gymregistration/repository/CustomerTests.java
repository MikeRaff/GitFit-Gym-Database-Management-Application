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
    public void clearDatabase() {
        customerRepository.deleteAll();
        personRepository.deleteAll();
    }
    @Test
    public void testCreateAndReadCustomer() {
        // Create and persist person.
        Person jim = createAndPersistPerson("Jim");

        // Create customer.
        Customer customer = createAndPersistCustomer("customeremail@emailprovider.net", "password", jim, 123456789);

        // Read customer from the database.
        Customer customerFromDB = customerRepository.findCustomerById(customer.getId());

        // Assertions
        assertCustomerAttributes(customer, customerFromDB);
        assertPersonAttributes(jim, customerFromDB.getPerson());
    }

    @Test
    public void testFindCustomersByPersonName() {
        // Create and persist person.
        Person bob = createAndPersistPerson("Bob Johnson");

        // Create customers with the same person.
        createAndPersistCustomer("customer1@emailprovider.ca", "customer1Password", bob, 987654321);
        createAndPersistCustomer("customer2@emailprovider.ca", "customer2Password", bob, 456789123);

        // Find customers by person name.
        List<Customer> customers = customerRepository.findCustomersByPerson_Name(bob.getName());

        // Assertions
        assertNotNull(customers);
        assertEquals(2, customers.size());
    }

    private Person createAndPersistPerson(String name) {
        Person person = new Person(name);
        return personRepository.save(person);
    }

    private Customer createAndPersistCustomer(String email, String password, Person person, int creditCardNumber) {
        Customer customer = new Customer(email, password, person, creditCardNumber);
        return customerRepository.save(customer);
    }

    private void assertCustomerAttributes(Customer expected, Customer actual) {
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getCreditCardNumber(), actual.getCreditCardNumber());
    }

    private void assertPersonAttributes(Person expected, Person actual) {
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }
}
