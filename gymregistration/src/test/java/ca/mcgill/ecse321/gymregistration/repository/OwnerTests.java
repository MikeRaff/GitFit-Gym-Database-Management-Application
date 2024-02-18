package ca.mcgill.ecse321.gymregistration.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gymregistration.dao.PersonRepository;
import ca.mcgill.ecse321.gymregistration.model.Owner;
import ca.mcgill.ecse321.gymregistration.model.Person;
import ca.mcgill.ecse321.gymregistration.dao.OwnerRepository;

@SpringBootTest
public class OwnerTests {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    @BeforeEach
    @AfterEach
    private void clearDatabase() {
        personRepository.deleteAll();
        ownerRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadOwner() {
        // Create and persist person.
        String name = "John";
        Person john = new Person(name);
        john = personRepository.save(john);
        
        // Create owner.
        String email = "myemail@emailprovider.com";
        String password = "password";
        Owner owner = new Owner(email, password, john);

        // Save owner to database.
        owner = ownerRepository.save(owner);

        // Read owner from database.
        Owner ownerFromDB = ownerRepository.findOwnerById(owner.getId());

        // Assert owner is not null and has correct non-null attributes.
        assertNotNull(ownerFromDB);

        assertEquals(owner.getId(), ownerFromDB.getId());
        assertEquals(email, ownerFromDB.getEmail());
        assertEquals(password, ownerFromDB.getPassword());

        assertNotNull(ownerFromDB.getPerson());
        assertEquals(john, ownerFromDB.getPerson());
        
        // Assert person is not null and has correct attributes.
        assertNotNull(personRepository.findPersonById(john.getId()));
        
        assertEquals(john.getId(), ownerFromDB.getPerson().getId());
        assertEquals(name, ownerFromDB.getPerson().getName());
        
    }
}
