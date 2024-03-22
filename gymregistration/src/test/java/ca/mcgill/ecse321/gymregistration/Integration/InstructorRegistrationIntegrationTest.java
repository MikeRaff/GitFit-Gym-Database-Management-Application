package ca.mcgill.ecse321.gymregistration.Integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import ca.mcgill.ecse321.gymregistration.dao.ClassTypeRepository;
import ca.mcgill.ecse321.gymregistration.dao.InstructorRegistrationRepository;
import ca.mcgill.ecse321.gymregistration.dao.InstructorRepository;
import ca.mcgill.ecse321.gymregistration.dao.PersonRepository;
import ca.mcgill.ecse321.gymregistration.dao.SessionRepository;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class InstructorRegistrationIntegrationTest {
    @Autowired
	private TestRestTemplate client;
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
	public void clearDatabase() {
		personRepository.deleteAll();
        instructorRepository.deleteAll();;
        classTypeRepository.deleteAll();
        sessionRepository.deleteAll();
        instructorRepository.deleteAll();
	}

    
    //for get, create, update, delete 
    //test valid test invalid

}
