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
import ca.mcgill.ecse321.gymregistration.dao.GymUserRepository;
import ca.mcgill.ecse321.gymregistration.model.GymUser;
import ca.mcgill.ecse321.gymregistration.model.Owner;
import ca.mcgill.ecse321.gymregistration.model.Instructor;
import ca.mcgill.ecse321.gymregistration.model.Customer;


@SpringBootTest
public class PersonTests {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private GymUserRepository gymUserRepository;

    @BeforeEach
    @AfterEach
    private void clearDatabase() {
        gymUserRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadOwnerPerson() {    // this is all garbage redo it all
        // Create and persist owner.
        String email = "myemail@emailprovider.com";
        String password = "1234abcd";

        String name = "John Doe";
        Person john = new Person(name);

        john = repo.save(john);

        Person johnFromDB = repo.findPersonById(john.getId());

        assertNotNull(johnFromDB);
        assertEquals(name, johnFromDB.getName());    
}
}
