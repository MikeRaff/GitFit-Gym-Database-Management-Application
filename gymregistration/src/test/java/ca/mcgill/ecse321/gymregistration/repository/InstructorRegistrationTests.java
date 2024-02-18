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
import ca.mcgill.ecse321.gymregistration.dao.InstructorRegistrationRepository;
import ca.mcgill.ecse321.gymregistration.dao.InstructorRepository;
import ca.mcgill.ecse321.gymregistration.dao.PersonRepository;
import ca.mcgill.ecse321.gymregistration.dao.SessionRepository;
import ca.mcgill.ecse321.gymregistration.model.ClassType;
import ca.mcgill.ecse321.gymregistration.model.Instructor;
import ca.mcgill.ecse321.gymregistration.model.InstructorRegistration;
import ca.mcgill.ecse321.gymregistration.model.Person;
import ca.mcgill.ecse321.gymregistration.model.Session;

@SpringBootTest
public class InstructorRegistrationTests {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private InstructorRepository instructorRepository;
    @Autowired
    private ClassTypeRepository classTypeRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private InstructorRegistrationRepository instructorRegistrationRepository;

    @BeforeEach
    @AfterEach
    private void clearDatabase() {
        instructorRegistrationRepository.deleteAll();
        instructorRepository.deleteAll();
        personRepository.deleteAll();
        sessionRepository.deleteAll();
        classTypeRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadInstructorRegistration() {
        // Create and persist person.
        String personName = "Maxime";
        Person maxime = new Person(personName);
        maxime = personRepository.save(maxime);
        
        // Create and persist instructor.
        String email = "emailaddress@emailprovider.us";
        String password = "instructorPassword";
        Instructor instructorMaxime = new Instructor(email, password, maxime);
        instructorMaxime = instructorRepository.save(instructorMaxime);

        // Create and persist class type.
        String className = "Badminton";
        boolean isApproved = true;  // must be true in order to sign up for class.
        ClassType badminton = new ClassType(className, isApproved);
        badminton = classTypeRepository.save(badminton);

        // Create and persist session.
        Date sessionDate = Date.valueOf("2024-02-18");
        Time startTime = Time.valueOf("16:25:00");
        Time endTime = Time.valueOf("17:25:00");
        String sessionDescription = "Badminton for beginners";
        String sessionName = "Badminton 101";
        String sessionLocation = "Gymnasium";
        Session badmintonSession = new Session(sessionDate, startTime, endTime, sessionDescription, sessionName, sessionLocation, badminton);
        badmintonSession = sessionRepository.save(badmintonSession);

        // Create instructor registration.
        Date registrationDate = Date.valueOf("2024-02-19");
        InstructorRegistration instructorRegistration = new InstructorRegistration(registrationDate, instructorMaxime, badmintonSession);
        
        // Save instructor registration to database.
        instructorRegistration = instructorRegistrationRepository.save(instructorRegistration);

        // Read instructor registration from database.
        InstructorRegistration instructorRegistrationFromDB = instructorRegistrationRepository.findInstructorRegistrationById(instructorRegistration.getId());

        // Assert instructor registration is not null and has correct non-null attributes.
        assertNotNull(instructorRegistrationFromDB);
        
        assertEquals(instructorRegistration.getId(), instructorRegistrationFromDB.getId());
        assertEquals(registrationDate, instructorRegistrationFromDB.getDate());

        assertNotNull(instructorRegistrationFromDB.getInstructor());
        //assertEquals(instructorMaxime, instructorRegistrationFromDB.getInstructor()); // compares addresses, not values
        assertNotNull(instructorRegistrationFromDB.getSession());
        //assertEquals(badmintonSession, instructorRegistrationFromDB.getSession()); // compares addresses, not values

        // Assert instructor is not null and has correct non-null attributes.
        assertNotNull(instructorRepository.findInstructorById(instructorMaxime.getId()));

        assertEquals(instructorMaxime.getId(), instructorRegistrationFromDB.getInstructor().getId());
        assertEquals(email, instructorRegistrationFromDB.getInstructor().getEmail());
        assertEquals(password, instructorRegistrationFromDB.getInstructor().getPassword());

        assertNotNull(instructorRegistrationFromDB.getInstructor().getPerson());
        //assertEquals(maxime, instructorRegistrationFromDB.getInstructor().getPerson());   // compares addresses, not values

        // Assert person is not null and has correct attributes.
        assertNotNull(personRepository.findPersonById(maxime.getId()));

        assertEquals(maxime.getId(), instructorRegistrationFromDB.getInstructor().getPerson().getId());
        assertEquals(personName, instructorRegistrationFromDB.getInstructor().getPerson().getName());

        // Assert session is not null and has correct non-null attributes.
        assertNotNull(sessionRepository.findSessionById(badmintonSession.getId()));
        
        assertEquals(badmintonSession.getId(), instructorRegistrationFromDB.getSession().getId());
        assertEquals(sessionDate, instructorRegistrationFromDB.getSession().getDate());
        assertEquals(startTime, instructorRegistrationFromDB.getSession().getStartTime());
        assertEquals(endTime, instructorRegistrationFromDB.getSession().getEndTime());
        assertEquals(sessionDescription, instructorRegistrationFromDB.getSession().getDescription());
        assertEquals(sessionName, instructorRegistrationFromDB.getSession().getName());
        assertEquals(sessionLocation, instructorRegistrationFromDB.getSession().getLocation());

        assertNotNull(instructorRegistrationFromDB.getSession().getClassType());
        //assertEquals(badminton, instructorRegistrationFromDB.getSession().getClassType());    // compares addresses, not values

        // Assert class type is not null and has correct attributes.
        assertNotNull(classTypeRepository.findClassTypeById(badminton.getId()));
        
        assertEquals(badminton.getId(), instructorRegistrationFromDB.getSession().getClassType().getId());
        assertEquals(className, instructorRegistrationFromDB.getSession().getClassType().getName());
        assertEquals(isApproved, instructorRegistrationFromDB.getSession().getClassType().getIsApproved());  
        
    }        
}
