package ca.mcgill.ecse321.gymregistration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321.gymregistration.dao.PersonRepository;
import ca.mcgill.ecse321.gymregistration.model.Person;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;

@ExtendWith(MockitoExtension.class)
public class TestPersonService {
    
    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    private static final String NAME = "Joe Biden";
    private static final int ID = 0;
    private final Person person = new Person();

    @BeforeEach
    public void setMockOutput(){
        lenient().when(personRepository.findPersonById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            int id = invocation.getArgument(0);
            if(id == ID){
                Person person = new Person();
                person.setName(NAME);
                person.setId(ID);
                return person;
            }else{
                return null;
            }
        });
    }

    @Test
    public void testCreatePerson(){
        String name = "SpongeBob SquarePants";

        Person person = null;
        try{
            person = personService.createPerson(name);
        }catch (GRSException e){
            fail(e.getMessage());
        }
        assertNotNull(person);
        assertEquals(name, person.getName());
    }

    @Test
    public void testCreatePersonNullName(){
        String name = null;

        Person person = null;
        try{
            person = personService.createPerson(name);
            fail();
        }catch (GRSException e){
            assertEquals("Must include name.", e.getMessage());
        }
        assertNull(person);
    }

    @Test
    public void testCreatePersonEmptryName(){
        String name = "";

        Person person = null;
        try{
            person = personService.createPerson(name);
            fail();
        }catch (GRSException e){
            assertEquals("Must include name.", e.getMessage());
        }
        assertNull(person);
    }

    @Test
    public void updatePerson(){
        int id = 0;
        String oldName = "Joe Biden";
        String newName = "SpongeBob SquarePants";

        Person person = null;
        try{
            person = personService.updateName(id, oldName, newName);
        }catch (GRSException e){
            fail(e.getMessage());
        }
        assertNotNull(person);
        assertEquals(newName, person.getName());
    }

    @Test
    public void updateNonexistantPerson(){
        int id = 1;
        String oldName = "Joe Biden";
        String newName = "SpongeBob SquarePants";

        Person person = null;
        try{
            person = personService.updateName(id, oldName, newName);
            fail();
        }catch (GRSException e){
            assertEquals("Person not found.", e.getMessage());
        }
        assertNull(person);
    }

    @Test 
    public void updatePersonToNullName(){
        int id = 0;
        String oldName = "Joe Biden";
        String newName = null;

        Person person = null;
        try{
            person = personService.updateName(id, oldName, newName);
            fail();
        }catch (GRSException e){
            assertEquals("Invalid name.", e.getMessage());
        }
        assertNull(person);
    }

    @Test 
    public void updatePersonToEmptyName(){
        int id = 0;
        String oldName = "Joe Biden";
        String newName = "";

        Person person = null;
        try{
            person = personService.updateName(id, oldName, newName);
            fail();
        }catch (GRSException e){
            assertEquals("Invalid name.", e.getMessage());
        }
        assertNull(person);
    }
}
