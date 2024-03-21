//package ca.mcgill.ecse321.gymregistration.servicetests;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//import java.sql.Date;
//import java.sql.Time;
//import java.util.List;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import ca.mcgill.ecse321.gymregistration.model.ClassType;
//import ca.mcgill.ecse321.gymregistration.model.Session;
//import ca.mcgill.ecse321.gymregistration.service.SessionService;
//@SpringBootTest
//public class SessionServiceTests {
//    @Test
//    public void testCreateSession()
//    {
//
//    ClassType classType = new ClassType("goat yoga", true);
//    Session expected = new Session(("2024-3-20", "8:00", "9:15","A description", "sessionName","room 1", classType);
//    SessionService sessionService = new SessionService();
//    Session session = sessionService.createSession("2024-3-20", "8:00", "9:15","A description", "sessionName","room 1", classType);
//
//    assertSessionAttributes(expected, session);
//
//    }
//
//    private void assertSessionAttributes(Session expected, Session actual) {
//        assertNotNull(actual);
//        assertEquals(expected.getId(), actual.getId());
//        assertEquals(expected.getDate(), actual.getDate());
//        assertEquals(expected.getStartTime(), actual.getStartTime());
//        assertEquals(expected.getEndTime(), actual.getEndTime());
//        assertEquals(expected.getDescription(), actual.getDescription());
//        assertEquals(expected.getName(), actual.getName());
//        assertEquals(expected.getLocation(), actual.getLocation());
//    }
//}
//
