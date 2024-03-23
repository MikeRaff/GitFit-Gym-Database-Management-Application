package ca.mcgill.ecse321.gymregistration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gymregistration.dao.InstructorRepository;
import ca.mcgill.ecse321.gymregistration.dao.PersonRepository;
import ca.mcgill.ecse321.gymregistration.model.Instructor;
import ca.mcgill.ecse321.gymregistration.model.Person;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;
import jakarta.transaction.Transactional;

@Service
public class InstructorService {
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    PersonRepository personRepository;
    
    
    /**
     * 
     * @param email
     * @param password
     * @param person_id
     * @throws GRSException missing information, instructor already exists, no person found
     * @return instructor, the created object
     */
    @Transactional
    public Instructor createInstructor(String email, String password, int person_id){
        if (email == null || password == null || !email.contains("@")){
            throw new GRSException(HttpStatus.BAD_REQUEST, "Invalid Email or Password");
        }
        Instructor instructor = instructorRepository.findInstructorByEmail(email);
        if(instructor != null)
            throw new GRSException(HttpStatus.BAD_REQUEST, "Email Already in use");
        
        Person person = personRepository.findPersonById(person_id);
        if(person == null)
            throw new GRSException(HttpStatus.NOT_FOUND, "Person not found");
        
        
        instructor = new Instructor(email, password, person);
        
        instructor = instructorRepository.save(instructor);

        return instructor;
    }

    /**
     * update the email or password of an instructor
     * @param id
     * @param email
     * @param password
     * @throws GRSException instructor not found
     * @return
     */
    @Transactional
    public Instructor updateInstructor(int id, String email, String password)
    {
        Instructor instructor = instructorRepository.findInstructorById(id);
        if(instructor == null)
            throw new GRSException(HttpStatus.NOT_FOUND, "Instructor not found");
        instructor.setEmail(email);
        instructor.setPassword(password);
        instructor.setId(id);
        instructorRepository.save(instructor);
        return instructor;
    }
    /**
     * Finds a desired instructor
     * @param id
     * @return instructor with matching id
     * @throws GRSException instructor not in the database
     */
    @Transactional
    public Instructor getInstructorById(int id)
    {
        Instructor instructor = instructorRepository.findInstructorById(id);
        if(instructor == null)
            throw new GRSException(HttpStatus.NOT_FOUND, "Instructor not found");
        return instructor;
    }
    /**
     * delete an instructor with an id
     * @param id
     * @throws GRSException instructor not in the database
     */
    @Transactional
    public void deleteIntructor(int id)
    {
        Instructor instructor = instructorRepository.findInstructorById(id);
        if(instructor == null)
            throw new GRSException(HttpStatus.NOT_FOUND, "Instructor not found");
        instructorRepository.delete(instructor);
    }
    /**
     * Get all the instructors in the database
     * @return a list of the instructors
     * @throws GRSException no instructors in the database
     */
    @Transactional
    public List<Instructor> getAllInstructors()
    {
         List<Instructor> instructors = instructorRepository.findAll();
        if(instructors.size() == 0){
            throw new GRSException(HttpStatus.NOT_FOUND, "No Instructors found in the system.");
        }
        
        return instructors;
    }

    @Transactional
    public Instructor logInInstructor(String email, String password)
    {
        Instructor instructor = instructorRepository.findInstructorByEmailAndPassword(email, password);
        if (instructor == null) {
            throw new GRSException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
        return instructor;
    }
}
