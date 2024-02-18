package ca.mcgill.ecse321.gymregistration.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gymregistration.dao.InstructorRepository;
import ca.mcgill.ecse321.gymregistration.dao.PersonRepository;
import ca.mcgill.ecse321.gymregistration.model.Instructor;
import ca.mcgill.ecse321.gymregistration.model.Person;

@SpringBootTest
public class InstructorTests {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private InstructorRepository instructorRepository;

    @BeforeEach
    @AfterEach
    private void clearDatabase() {
        personRepository.deleteAll();
        instructorRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadInstructor() {
        // Create and persist person.
        String name = "Joe Smith";
        Person joe = new Person(name);
        joe = personRepository.save(joe);
        
        // Create instructor.
        String email = "instructoremail@emailprovider.ca";
        String password = "instructorPassword";
        Instructor instructor = new Instructor(email, password, joe);

        // Save instructor to database.
        instructor = instructorRepository.save(instructor);

        // Read instructor from database.
        Instructor instructorFromDB = instructorRepository.findInstructorById(instructor.getId());

        // Assert instructor is not null and has correct non-null attributes.
        assertNotNull(instructorFromDB);

        assertEquals(instructor.getId(), instructorFromDB.getId());
        assertEquals(email, instructorFromDB.getEmail());
        assertEquals(password, instructorFromDB.getPassword());
        
        assertNotNull(instructorFromDB.getPerson());
        assertEquals(joe, instructorFromDB.getPerson());

        // Assert person is not null and has correct attributes.
        assertNotNull(personRepository.findPersonById(joe.getId()));
        
        assertEquals(joe.getId(), instructorFromDB.getPerson().getId());
        assertEquals(name, instructorFromDB.getPerson().getName());
    }

}
