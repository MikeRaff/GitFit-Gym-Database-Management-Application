package ca.mcgill.ecse321.gymregistration.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gymregistration.dao.ClassTypeRepository;
import ca.mcgill.ecse321.gymregistration.model.ClassType;
import ca.mcgill.ecse321.gymregistration.dao.SessionRepository;
import ca.mcgill.ecse321.gymregistration.model.Session;

@SpringBootTest
public class SessionTests {
    @Autowired
    private ClassTypeRepository classTypeRepository;
    @Autowired
    private SessionRepository sessionRepository;

    @BeforeEach
    @AfterEach
    private void clearDatabase() {
        sessionRepository.deleteAll();
        classTypeRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadSession() {
        // Create and persist class type.
        String classTypeName = "testClassType";
        Boolean isApproved = true;  // Must be approved to create session.
        ClassType classType = new ClassType(classTypeName, isApproved);
        classType = classTypeRepository.save(classType);

        // Create session.
        Date date = Date.valueOf("2024-02-17");
        Time startTime = Time.valueOf("17:22:00");
        Time endTime = Time.valueOf("18:22:00");
        String description = "A description of the session.";
        String sessionName = "Session name";
        String location = "Where session takes place.";
        Session session = new Session(date, startTime, endTime, description, sessionName, location, classType);

        // Save session
        sessionRepository.save(session);

        // Read session from database.
        Session sessionFromDB = sessionRepository.findSessionById(session.getId());

        // Assert session not null and has correct non-null attributes.
        assertNotNull(sessionFromDB);

        assertEquals(session.getId(), sessionFromDB.getId());
        assertEquals(date, sessionFromDB.getDate());
        assertEquals(startTime, sessionFromDB.getStartTime());
        assertEquals(endTime, sessionFromDB.getEndTime());
        assertEquals(description, sessionFromDB.getDescription());
        assertEquals(sessionName, sessionFromDB.getName());
        assertEquals(location, sessionFromDB.getLocation());

        assertNotNull(sessionFromDB.getClassType());
        //assertEquals(classType, sessionFromDB.getClassType()); // compares addresses, not values

        // Assert session class type not null and has correct attributes.
        assertNotNull(classTypeRepository.findClassTypeById(classType.getId()));

        assertEquals(classType.getId(), sessionFromDB.getClassType().getId());
        assertEquals(classTypeName, sessionFromDB.getClassType().getName());
        assertEquals(isApproved, sessionFromDB.getClassType().getIsApproved());
    }
    @Test
    public void testFindSessionsByClassTypeName() {
        // Create and persist class type.
        String classTypeName = "testClassType";
        Boolean isApproved = true;  // Must be approved to create session.
        ClassType classType = new ClassType(classTypeName, isApproved);
        classType = classTypeRepository.save(classType);

        // Create sessions for the class type.
        Date date1 = Date.valueOf("2024-02-17");
        Time startTime1 = Time.valueOf("17:22:00");
        Time endTime1 = Time.valueOf("18:22:00");
        String description1 = "A description of the session 1";
        String sessionName1 = "Session name 1";
        String location1 = "Where session takes place 1";
        Session session1 = new Session(date1, startTime1, endTime1, description1, sessionName1, location1, classType);
        sessionRepository.save(session1);

        Date date2 = Date.valueOf("2024-02-18");
        Time startTime2 = Time.valueOf("19:00:00");
        Time endTime2 = Time.valueOf("20:00:00");
        String description2 = "A description of the session 2";
        String sessionName2 = "Session name 2";
        String location2 = "Where session takes place 2";
        Session session2 = new Session(date2, startTime2, endTime2, description2, sessionName2, location2, classType);
        sessionRepository.save(session2);

        // Perform the test
        List<Session> sessions = sessionRepository.findSessionsByClassType_Name(classTypeName);

        // Assert the results
        assertNotNull(sessions);
        assertEquals(2, sessions.size());
        assertEquals(session1.getId(), sessions.get(0).getId());
        assertEquals(session2.getId(), sessions.get(1).getId());
    }
}
