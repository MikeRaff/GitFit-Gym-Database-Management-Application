package ca.mcgill.ecse321.gymregistration.Integration;

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

import ca.mcgill.ecse321.gymregistration.dao.ClassTypeRepository;
import ca.mcgill.ecse321.gymregistration.dao.InstructorRegistrationRepository;
import ca.mcgill.ecse321.gymregistration.dao.InstructorRepository;
import ca.mcgill.ecse321.gymregistration.dao.PersonRepository;
import ca.mcgill.ecse321.gymregistration.dao.SessionRepository;
import ca.mcgill.ecse321.gymregistration.dto.InstructorRegistrationDto;
import ca.mcgill.ecse321.gymregistration.model.Instructor;
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

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        instructorRegistrationRepository.deleteAll();
        instructorRepository.deleteAll();
        sessionRepository.deleteAll();
        
    }

    @Test
    public void testCreateandGetInstructorRegistration() {
        int id = testCreateInstructorRegistration();
        testgetInstructorRegistration(id);
    }

    @Test
    public void testCreateandDeleteInstructorRegistratioOneInstructor()
    {
        int id = testCreateInstructorRegistration();
        testDeleteInstructorRegistration(id, 1);
    }

    @Test
    public void testCreateandDeleteInstructorRegistratioTwoInstructors()
    {
        int id = testCreateInstructorRegistration();
        id = testCreateInstructorRegistration();
        testDeleteInstructorRegistration(id, 2);
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

    public void testgetInstructorRegistration(int id)
    {
        String url = "/instructor-registration/"+id;
        ResponseEntity<InstructorRegistrationDto> response = client.getForEntity(url,
                InstructorRegistrationDto.class);
        assertNotNull(response);
        assertEquals(id, response.getBody().getId());
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    }

    public void testDeleteInstructorRegistration(int id, int numInstructors)
    {
        String url ="/instructor-registration/delete/"+id;
        try{
        client.delete(url);
        }
        catch(GRSException e)
        {
            if(numInstructors == 1)
            {
                assertEquals("not enough instructors registered", e.getMessage());
            }
            else
            {
                fail();
                return;
            }
        }
        assertEquals(1,1);
    }
    
}
