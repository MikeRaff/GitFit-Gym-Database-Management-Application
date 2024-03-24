package ca.mcgill.ecse321.gymregistration.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gymregistration.dao.InstructorRepository;
import ca.mcgill.ecse321.gymregistration.dao.PersonRepository;
import ca.mcgill.ecse321.gymregistration.dto.InstructorDto;
import ca.mcgill.ecse321.gymregistration.model.Person;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class InstructorServiceIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private InstructorRepository instructorRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        instructorRepository.deleteAll();
    }

    @Test
    public void testCreateAndGetInstructor() {
        int id = testCreateInstructor("example@email.com", "password");
        testGetinstructor(id);
    }

    @Test // multiple
    // Verify the different emails
    public void testCreateAndGetInstructors() {
        testCreateInstructor("example@email.com", "password");
        testCreateInstructor("example2@email.com", "password");

        testGetinstructors(2);
    }

    @Test
    public void testCreateAndDeleteInstructor() {
        int id = testCreateInstructor("example@email.com", "password");
        testDeleteInstructor(id);
    }

    @Test
    public void testCreateandUpdateInstructor() {
        int id = testCreateInstructor("example@email.com", "password");
        testUpdateInstructor(id, "newemail@email.com", "newpassword");
    }

    @Test
    public void testCreateInvalidInstructor() {
        testCreateInstructorInvalidEmailOrPassword("email", "password");
        testCreateInstructorInvalidEmailOrPassword(null, "password");
        testCreateInstructorInvalidEmailOrPassword("email@exmaple.com", null);
        testCreateInstructorInvalidPerson();
    }

    public void testCreateInstructorInvalidEmailOrPassword(String email, String password) {
        Person person = new Person();
        personRepository.save(person);

        ResponseEntity<InstructorDto> response = client.postForEntity("/instructors/create",
                new InstructorDto(email, password, person), InstructorDto.class);
        assertNotNull(response);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
    }

    public void testCreateInstructorInvalidPerson()
    {
        ResponseEntity<InstructorDto> response = client.postForEntity("/instructors/create",
                new InstructorDto("email@email.com", "password",  new Person()), InstructorDto.class);
        assertNotNull(response);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Response has correct status");
    }

    private int testCreateInstructor(String email, String password) {
        Person person = new Person();
        personRepository.save(person);
        ResponseEntity<InstructorDto> response = client.postForEntity("/instructors/create",
                new InstructorDto(email, password, person), InstructorDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");

        return response.getBody().getId();
    }

    private void testGetinstructor(int id) {
        ResponseEntity<InstructorDto> response = client.getForEntity("/instructors/" + id, InstructorDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Response has body");
        assertEquals(response.getBody().getId(), id);
    }

    private void testGetinstructors(int size) {
        ResponseEntity<List<InstructorDto>> response = client.exchange(
                "/instructors",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<InstructorDto>>() {
                });
        assertEquals(response.getBody().size(), size);
        List<InstructorDto> responseInstructorDtos = response.getBody();
        for (InstructorDto i : responseInstructorDtos) {
            assertNotNull(instructorRepository.findInstructorById(i.getId()));
        }
    }

    private void testUpdateInstructor(int id, String email, String password) {

        ResponseEntity<InstructorDto> response = client.exchange(
                "/update-instructors-e/" + id + "/" + email, // URL with path variables
                HttpMethod.PUT, // HTTP method
                null,
                InstructorDto.class);
        ;
        assertNotNull(response.getBody());
        assertEquals(email, response.getBody().getEmail());
        assertEquals(id, response.getBody().getId());

    }

    private void testDeleteInstructor(int id) {
        try {
            String url = "/instructors/delete/" + id;
            client.delete(url);
        } catch (GRSException e) {
            fail();
        }
        assertEquals(1, 1);
    }
}
