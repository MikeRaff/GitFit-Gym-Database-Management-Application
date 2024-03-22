package ca.mcgill.ecse321.gymregistration.Integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.SpringBootTest;


import ca.mcgill.ecse321.gymregistration.dao.InstructorRepository;
import ca.mcgill.ecse321.gymregistration.dao.PersonRepository;
import ca.mcgill.ecse321.gymregistration.dto.InstructorDto;
import ca.mcgill.ecse321.gymregistration.dto.PersonDto;
import ca.mcgill.ecse321.gymregistration.model.Person;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class InstructorServiceIntegrationTests {
    @Autowired
	private TestRestTemplate client;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private InstructorRepository instructorRepository;

    
    @BeforeEach
	@AfterEach
	public void clearDatabase() {
        instructorRepository.deleteAll();;
	}
    
    @Test
    public void testCreateAndGetInstructor()
    {
        int id = testCreateInstructor("example@email.com");
        testGetinstructor(id);
    }

    @Test//multiple
    //Verify the different emails
    public void testCreateAndGetInstructors()
    {
        testCreateInstructor("example@email.com");
        testCreateInstructor("example2@email.com");
        
        testGetinstructors(2);
    }

   private int testCreateInstructor(String email)
   {
    Person person = new Person();
    personRepository.save(person);
    ResponseEntity<InstructorDto> response = client.postForEntity("/instructors/create", new InstructorDto(email, "password",person), InstructorDto.class);
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Response has correct status");
    
    return response.getBody().getId();
    }
    
    private void testGetinstructor(int id)
    {
        ResponseEntity<InstructorDto> response = client.getForEntity("/instructors/" + id, InstructorDto.class);
        
        assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
		assertNotNull(response.getBody(), "Response has body");
        assertEquals(response.getBody().getId(), id);
    }
    
    private void testGetinstructors(int size)
    {
        ResponseEntity<List<InstructorDto>> response = client.exchange(
        "/instructors",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<InstructorDto>>() {}
        );
        
        assertEquals(response.getBody().size(), size);
        List<InstructorDto> responseInstructorDtos = response.getBody();
        for(InstructorDto i: responseInstructorDtos )
        {
            assertNotNull(instructorRepository.findInstructorById(i.getId()));
        }
    }
    
    
}
