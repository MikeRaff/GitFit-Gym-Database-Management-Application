package ca.mcgill.ecse321.gymregistration.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gymregistration.dao.PersonRepository;
import ca.mcgill.ecse321.gymregistration.model.Person;

@SpringBootTest
public class PersonTests {
    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    @AfterEach
    private void clearDatabase() {
        personRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadPerson() {
        // Create person.
        String name = "John";
        Person person = new Person(name);

        // Save person to database.
        personRepository.save(person);

        // Read person from database.
        Person personFromDB = personRepository.findPersonById(person.getId());

        // Assert person is not null and has correct attributes.
        assertNotNull(personFromDB);
        
        assertEquals(person.getId(), personFromDB.getId());
        assertEquals(name, personFromDB.getName());
    }
}
