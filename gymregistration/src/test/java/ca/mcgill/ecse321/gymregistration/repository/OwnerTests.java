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

import java.util.List;

@SpringBootTest
public class OwnerTests {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    @BeforeEach
    @AfterEach
    private void clearDatabase() {
        ownerRepository.deleteAll();
        personRepository.deleteAll();
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
        //assertEquals(john, ownerFromDB.getPerson());  // compares addresses, not values
        
        // Assert person is not null and has correct attributes.
        assertNotNull(personRepository.findPersonById(john.getId()));
        
        assertEquals(john.getId(), ownerFromDB.getPerson().getId());
        assertEquals(name, ownerFromDB.getPerson().getName());
    }
    @Test
    public void testFindOwnersByPersonName() {
        // Create and persist person.
        String name = "John Doe";
        Person john = new Person(name);
        john = personRepository.save(john);

        // Create owners with the same person.
        String email1 = "owner1@emailprovider.ca";
        String password1 = "owner1Password";
        Owner owner1 = new Owner(email1, password1, john);
        ownerRepository.save(owner1);

        String email2 = "owner2@emailprovider.ca";
        String password2 = "owner2Password";
        Owner owner2 = new Owner(email2, password2, john);
        ownerRepository.save(owner2);

        // Find owners by person name.
        List<Owner> owners = ownerRepository.findOwnersByPerson_Name(name);

        // Assert the list is not null and contains the expected number of owners.
        assertNotNull(owners);
        assertEquals(2, owners.size());

        // Assert that the owners in the list have the correct attributes.
        assertEquals(email1, owners.get(0).getEmail());
        assertEquals(password1, owners.get(0).getPassword());

        assertEquals(email2, owners.get(1).getEmail());
        assertEquals(password2, owners.get(1).getPassword());
    }
}
