package ca.mcgill.ecse321.gymregistration.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.gymregistration.dao.CustomerRepository;
import ca.mcgill.ecse321.gymregistration.dao.InstructorRepository;
import ca.mcgill.ecse321.gymregistration.dao.PersonRepository;
import ca.mcgill.ecse321.gymregistration.dto.CustomerDto;
import ca.mcgill.ecse321.gymregistration.model.CustomerRegistration;
import ca.mcgill.ecse321.gymregistration.model.Person;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerServiceIntegrationTest {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private InstructorRepository instructorRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase(){
        customerRepository.deleteAll();
        instructorRepository.deleteAll();
    }

    @Test
    public void testCreateAndGetCustomer(){
        String email = testCreateCustomer("customer@email.com", "password");
        testGetCustomer(email);
    }

    private String testCreateCustomer(String email, String password){
        Person person = new Person();
        personRepository.save(person);
        ResponseEntity<CustomerDto> response = client.postForEntity("/customers/create", 
                new CustomerDto(email, password, person), CustomerDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");

        return response.getBody().getEmail();
    }

    private void testGetCustomer(String email){
        ResponseEntity<CustomerDto> response = client.getForEntity("/customers/"+email, CustomerDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Reponse has body");
        assertEquals(email, response.getBody(), response.getBody().getEmail());
    }


}
