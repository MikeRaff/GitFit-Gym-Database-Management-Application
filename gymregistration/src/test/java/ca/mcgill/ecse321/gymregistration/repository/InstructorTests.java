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

import java.util.List;

@SpringBootTest
public class InstructorTests {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private InstructorRepository instructorRepository;

    @BeforeEach
    @AfterEach
    private void clearDatabase() {    
        instructorRepository.deleteAll();
        personRepository.deleteAll();
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
        //assertEquals(joe, instructorFromDB.getPerson());  // compares addresses, not values

        // Assert person is not null and has correct attributes.
        assertNotNull(personRepository.findPersonById(joe.getId()));
        
        assertEquals(joe.getId(), instructorFromDB.getPerson().getId());
        assertEquals(name, instructorFromDB.getPerson().getName());
    }
    @Test
    public void testFindInstructorsByPersonName() {
        // Create and persist person.
        String name = "John Doe";
        Person john = new Person(name);
        john = personRepository.save(john);

        // Create instructors with the same person.
        String email1 = "instructor1@emailprovider.ca";
        String password1 = "instructor1Password";
        Instructor instructor1 = new Instructor(email1, password1, john);
        instructorRepository.save(instructor1);

        String email2 = "instructor2@emailprovider.ca";
        String password2 = "instructor2Password";
        Instructor instructor2 = new Instructor(email2, password2, john);
        instructorRepository.save(instructor2);

        // Find instructors by person name.
        List<Instructor> instructors = (List<Instructor>) instructorRepository.findInstructorsByPerson_Name(name);

        // Assert the list is not null and contains the expected number of instructors.
        assertNotNull(instructors);
        assertEquals(2, instructors.size());

        // Assert that the instructors in the list have the correct attributes.
        assertEquals(email1, instructors.get(0).getEmail());
        assertEquals(password1, instructors.get(0).getPassword());

        assertEquals(email2, instructors.get(1).getEmail());
        assertEquals(password2, instructors.get(1).getPassword());
    }
}
