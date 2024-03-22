package ca.mcgill.ecse321.gymregistration.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gymregistration.dao.InstructorRepository;
import ca.mcgill.ecse321.gymregistration.dao.PersonRepository;
import ca.mcgill.ecse321.gymregistration.model.Instructor;
import ca.mcgill.ecse321.gymregistration.model.Person;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;

import static org.mockito.Mockito.lenient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest
@ExtendWith(MockitoExtension.class)

public class TestInstructorService {
    @Mock
    private InstructorRepository instructorRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private InstructorService instructorService = new InstructorService();

    private Instructor INSTRUCTOR = new Instructor();

    private int ID = INSTRUCTOR.getId();

    private Person PERSON = new Person();
    private int PERSON_ID = PERSON.getId();

    @BeforeEach
    public void setMockOutput() {
        // Stubbing findInstructorById method
        lenient().when(instructorRepository.findInstructorById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            int id = invocation.getArgument(0);
            if (id == ID) { // Replace INSTRUCTOR_ID with your expected instructor ID
                Instructor instructor = new Instructor();
                instructor.setId(id);
                instructor.setEmail(null);
                return instructor;
            } else {
                return null;
            }
        });

        lenient().when(personRepository.findPersonById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            int id = invocation.getArgument(0);
            if (id == PERSON_ID) { // Replace INSTRUCTOR_ID with your expected instructor ID
                Person person = new Person();
                person.setId(id);
                return person;
            } else {
                return null;
            }
        });

        lenient().when(instructorRepository.save(any(Instructor.class))).thenAnswer((InvocationOnMock invocation) -> {
            return invocation.getArgument(0); // Return the saved object
        });

        lenient().when(personRepository.save(any(Person.class))).thenAnswer((InvocationOnMock invocation) -> {
            return invocation.getArgument(0); // Return the saved object
        });
    }

    @Test
    public void testCreateInstructor()
    {
        String email = "Email@email.com";
        String password = "password";
        Person person = new Person();
        int person_id = person.getId();
        Instructor instructor = null;
        personRepository.save(person);
        try{
        instructor = instructorService.createInstructor(email, password, person_id);}
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        assertNotNull(instructor);
        assertEquals(email, instructor.getEmail());
        assertEquals(password, instructor.getPassword());
        assertEquals(person_id, instructor.getPerson().getId());

    }

    @Test
    public void testDeleteInstructor()
    {
        lenient().doAnswer(invocation -> {
            Instructor instructor = invocation.getArgument(0);
            return null; // Simulate successful deletion
        }).when(instructorRepository).delete(any(Instructor.class));
        String email = "Email@email.com";
        String password = "password";
        Person person = new Person();
        int person_id = person.getId();
        Instructor instructor = null;
        personRepository.save(person);
        instructor = instructorService.createInstructor(email, password, person_id);
        instructorService.deleteIntructor(instructor.getId());
        lenient().doReturn(null).when(instructorRepository).findInstructorById(instructor.getId());
        instructor = instructorRepository.findInstructorById(instructor.getId());
        assertNull(instructor);
    }

    @Test
    public void testUpdateInstuctor()
    {
        String email = "email@example.com";
        String password = "password";
       

        Instructor instructor = new Instructor();
        instructorRepository.save(instructor);

        Instructor updatedInstructor = instructorService.updateInstructor(instructor.getId(), email, password);

        assertEquals(instructor.getId(), updatedInstructor.getId());
        assertEquals(email, updatedInstructor.getEmail());
        assertEquals(updatedInstructor.getPassword(), password);

    }

    @Test 
    public void testGetInstructor()
    {
        Instructor instructor = new Instructor();
        instructorRepository.save(instructor);
        Instructor newInstructor = instructorService.getInstructorById(instructor.getId());
        assertEquals(instructor.getId(), newInstructor.getId());
    }
}
