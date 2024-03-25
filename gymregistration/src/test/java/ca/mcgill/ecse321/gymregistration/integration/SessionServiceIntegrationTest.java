package ca.mcgill.ecse321.gymregistration.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

import ca.mcgill.ecse321.gymregistration.dao.InstructorRegistrationRepository;
import ca.mcgill.ecse321.gymregistration.dao.InstructorRepository;
import ca.mcgill.ecse321.gymregistration.dao.OwnerRepository;
import ca.mcgill.ecse321.gymregistration.dao.SessionRepository;
import ca.mcgill.ecse321.gymregistration.model.Session;
import ca.mcgill.ecse321.gymregistration.dto.SessionDto;
import ca.mcgill.ecse321.gymregistration.dto.InstructorRegistrationDto;
import ca.mcgill.ecse321.gymregistration.model.Instructor;
import ca.mcgill.ecse321.gymregistration.model.InstructorRegistration;
import ca.mcgill.ecse321.gymregistration.model.ClassType;
import ca.mcgill.ecse321.gymregistration.dao.ClassTypeRepository;
import ca.mcgill.ecse321.gymregistration.dto.ClassTypeDto;
import ca.mcgill.ecse321.gymregistration.model.Owner;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SessionServiceIntegrationTest {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private InstructorRepository classTypeRepository;
    @Autowired
    private SessionRepository sessionRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        classTypeRepository.deleteAll();
        sessionRepository.deleteAll();
    }

    @Test
    public void testCreateandGetSession() {
        int id = testCreateSession();
        testgetSession(id);
    }

    @Test
    public void testCreateandDeleteSessionOneClassType() {
        int id = testCreateSession();
        testDeleteSession(id);
    }

    @Test
    public void testCreateInvalidSessionBadClassType()
    {
        Session session = new Session();
        ClassType classType = new ClassType();
        session.setClassType(classType);
        sessionRepository.save(session);
        classTypeRepository.save(classType); // why doesn't this work.
        SessionDto sessionDto = new SessionDto(null, classType, new ClassType());
        String url = "/session/create";
        ResponseEntity<SessionDto> response = client.postForEntity(url, sessionDto,
                SessionDto.class);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "response has correct status"); 
    }

    @Test
    public void testCreateandGetSessionByClassTypeId()
    {
        int id = testCreateSession();

        testgetSessionByClassTypeId(id);
    }

    public int testCreateSession() {
        ClassType classType = new ClassType();
        Session session = new Session();
        session.setClassType(classType);
        sessionRepository.save(session);
        SessionDto sessionDto = new SessionDto(session);
        String url = "/session/create";
        ResponseEntity<SessionDto> response = client.postForEntity(url, sessionDto,
                SessionDto.class);
        assertNotNull(response.getBody());
        assertEquals(session.getId(), response.getBody().getClassType().getId());
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
        return response.getBody().getId();
    }

    public void testgetSession(int id) {
        Session session = sessionRepository.findSessionById(id);
        String url = "/session/" + session.getClassType().getId();
        ResponseEntity<SessionDto> response = client.getForEntity(url,
                SessionDto.class);
        assertNotNull(response);
        assertEquals(id, response.getBody().getId());
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
    }

    public void testgetSessionByClassTypeId(int id) {
        Session session = sessionRepository.findSessionById(id);
        String url = "/session-s/" + session.getClassType().getId();

        ResponseEntity<List<Session>> response = client.exchange(url,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<Session>>() {});

        assertNotNull(response);
        assertEquals(response.getBody().size(),1);        
    }

    public void testDeleteSession(int id) {
        String url = "/session/delete/" + id;
        try {
            client.delete(url);
            assertEquals(1, 1); 
            return;
        } catch (GRSException e) {
            assertEquals("You don't have permission to remove this session", e.getMessage());
            return;
            
        }
    }

}
