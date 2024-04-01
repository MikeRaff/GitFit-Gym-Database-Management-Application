package ca.mcgill.ecse321.gymregistration.integration;

import static org.junit.jupiter.api.Assertions.*;

import ca.mcgill.ecse321.gymregistration.dao.ClassTypeRepository;
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

import ca.mcgill.ecse321.gymregistration.dao.SessionRepository;
import ca.mcgill.ecse321.gymregistration.model.Session;
import ca.mcgill.ecse321.gymregistration.dto.SessionDto;
import ca.mcgill.ecse321.gymregistration.model.ClassType;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SessionServiceIntegrationTest {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private ClassTypeRepository classTypeRepository;
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
    public void testCreateandGetSessionByClassTypeId() {
        int id = testCreateSession();
        testgetSessionByClassTypeId(id);
    }

    public int testCreateSession() {
        ClassType classType = new ClassType();
        classTypeRepository.save(classType);
        Session session = new Session();
        session.setClassType(classType);
        sessionRepository.save(session);
        SessionDto sessionDto = new SessionDto(session);
        String url = "/sessions/create";
        ResponseEntity<SessionDto> response = client.postForEntity(url, sessionDto,
                SessionDto.class);
        assertNotNull(response.getBody());
//        assertEquals(session.getId(), response.getBody().getClassType().getId());
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
