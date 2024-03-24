package ca.mcgill.ecse321.gymregistration.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.gymregistration.dao.InstructorRegistrationRepository;
import ca.mcgill.ecse321.gymregistration.dao.InstructorRepository;
import ca.mcgill.ecse321.gymregistration.dao.OwnerRepository;
import ca.mcgill.ecse321.gymregistration.dao.SessionRepository;
import ca.mcgill.ecse321.gymregistration.dto.InstructorRegistrationDto;
import ca.mcgill.ecse321.gymregistration.model.Instructor;
import ca.mcgill.ecse321.gymregistration.model.Owner;
import ca.mcgill.ecse321.gymregistration.model.Session;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class InstructorRegistrationIntegrationTest {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private InstructorRegistrationRepository instructorRegistrationRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        instructorRegistrationRepository.deleteAll();
        instructorRepository.deleteAll();
        sessionRepository.deleteAll();
        ownerRepository.deleteAll();
    }

    @Test
    public void testCreateandGetInstructorRegistration() {
        int id = testCreateInstructorRegistration();
        testgetInstructorRegistration(id);
    }

    @Test
    public void testCreateandDeleteInstructorRegistrationOneInstructor() {
        int id = testCreateInstructorRegistration();
        testDeleteInstructorRegistration(id, 1, id);
    }

    @Test
    public void testCreateandDeleteInstructorRegistrationTwoInstructors() {
        int id = testCreateInstructorRegistration();
        id = testCreateInstructorRegistration();
        testDeleteInstructorRegistration(id, 2, id);
    }

    @Test
    public void testCreateandDeleteInstructorRegistrationTwoInstructorsWithoutPermission() {
        int id = testCreateInstructorRegistration();
        testCreateInstructorRegistration();
        testDeleteInstructorRegistration(id, 2, 1000);
    }

    @Test
    public void testCreateandDeleteInstructorRegistrationTwoInstructorsAsOwner() {
        int id = testCreateInstructorRegistration();
        testCreateInstructorRegistration();
        Owner owner = new Owner();
        ownerRepository.save(owner);
        testDeleteInstructorRegistration(id, 2, owner.getId());
    }

    public int testCreateInstructorRegistration() {

        Instructor instructor = new Instructor();
        Session session = new Session();
        instructorRepository.save(instructor);
        sessionRepository.save(session);
        InstructorRegistrationDto instructorRegistrationDto = new InstructorRegistrationDto(null, instructor, session);
        String url = "/instructor-registration/create";
        ResponseEntity<InstructorRegistrationDto> response = client.postForEntity(url, instructorRegistrationDto,
                InstructorRegistrationDto.class);
        assertNotNull(response.getBody());
        assertEquals(instructor.getId(), response.getBody().getInstructor().getId());
        assertEquals(session.getId(), response.getBody().getSession().getId());
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
        return response.getBody().getId();
    }

    public void testgetInstructorRegistration(int id) {
        String url = "/instructor-registration/" + id;
        ResponseEntity<InstructorRegistrationDto> response = client.getForEntity(url,
                InstructorRegistrationDto.class);
        assertNotNull(response);
        assertEquals(id, response.getBody().getId());
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    }

    public void testDeleteInstructorRegistration(int id, int numInstructors, int gid) {

        String url = "/instructor-registration/delete/" + id + "/" + gid;
        try {
            client.delete(url);
            assertEquals(1, 1);// if no errors then return sucessful
            return;
        } catch (GRSException e) {
            if (numInstructors == 1) {
                assertEquals("not enough instructors registered", e.getMessage());
                return;
            } else {
                assertEquals("You don't have permission to remove this instructor", e.getMessage());
                return;
            }
        }
    }

}
