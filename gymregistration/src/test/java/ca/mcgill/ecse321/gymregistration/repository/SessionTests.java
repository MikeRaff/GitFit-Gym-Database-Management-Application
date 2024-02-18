package ca.mcgill.ecse321.gymregistration.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;

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
        Date date = Date.valueOf("2024-17-02");
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
        assertEquals(classType, sessionFromDB.getClassType());

        // Assert session class type not null and has correct attributes.
        assertNotNull(classTypeRepository.findClassTypeById(classType.getId()));

        assertEquals(classType.getId(), sessionFromDB.getClassType().getId());
        assertEquals(classTypeName, sessionFromDB.getClassType().getName());
        assertEquals(isApproved, sessionFromDB.getClassType().getIsApproved());
    }
}
