package ca.mcgill.ecse321.gymregistration.service;

import ca.mcgill.ecse321.gymregistration.dao.CustomerRepository;
import ca.mcgill.ecse321.gymregistration.dao.InstructorRepository;
import ca.mcgill.ecse321.gymregistration.dao.OwnerRepository;
import ca.mcgill.ecse321.gymregistration.dao.PersonRepository;
import ca.mcgill.ecse321.gymregistration.model.Instructor;
import ca.mcgill.ecse321.gymregistration.model.Person;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {

    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    PersonRepository personRepository;

    /**
     * CreateInstructor: creating an instructor
     * @param email: Email of the instructor
     * @param password: Password of the instructor
     * @param person_id: iD of the person
     * @return The created instructor
     * @throws GRSException Invalid instructor creation request
     */
    @Transactional
    public Instructor createInstructor(String email, String password, int person_id) {
        if (email == null || password == null || !email.contains("@")) {
            throw new GRSException(HttpStatus.BAD_REQUEST, "Must include valid email and password.");
        }
        if (instructorRepository.findInstructorByEmail(email) != null || ownerRepository.findOwnerByEmail(email) != null || customerRepository.findCustomerByEmail(email) != null) {
            throw new GRSException(HttpStatus.CONFLICT, "User with email already exists.");
        }
        Person person = personRepository.findPersonById(person_id);
        if (person == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Person not found.");
        }
        Instructor instructor = new Instructor();
        instructor.setEmail(email);
        instructor.setPassword(password);
        instructor.setPerson(person);
        return instructorRepository.save(instructor);
    }

    /**
     * UpdateEmail: Allow users to edit their email information
     * @param oldEmail: Old email of instructor
     * @param password: Password of instructor
     * @param newEmail: New email of instructor
     * @return The new instructor
     * @throws GRSException Instructor not found, invalid email and password combination, or invalid new email
     */
    @Transactional
    public Instructor updateEmail(String oldEmail, String password, String newEmail) {
        Instructor instructor = instructorRepository.findInstructorByEmailAndPassword(oldEmail, password);
        if (instructor == null) {
            throw new GRSException(HttpStatus.NOT_FOUND, "Instructor not found, invalid email and password combination.");
        }
            if (newEmail == null || !newEmail.contains("@")) {
            throw new GRSException(HttpStatus.BAD_REQUEST, "Invalid new email.");
        }
        instructor.setEmail(newEmail);       
        return instructorRepository.save(instructor);
    }

    /**
     * UpdatePassword: Allow users to edit their password information
     * @param email: Email of instructor
     * @param oldPassword: Old password of instructor
     * @param newPassword: New password of instructor
     * @return The new instructor
     * @throws GRSException Instructor not found, invalid email and password combination, or invalid new password
     */
    public Instructor updatePassword(String email, String oldPassword, String newPassword) {
        Instructor instructor = instructorRepository.findInstructorByEmailAndPassword(email, oldPassword);
        if (instructor == null)
            throw new GRSException(HttpStatus.NOT_FOUND, "Instructor not found, invalid email and password combination.");
        if (newPassword == null) {
            throw new GRSException(HttpStatus.BAD_REQUEST, "Invalid new password.");
        }
        instructor.setPassword(newPassword);
        return instructorRepository.save(instructor);
    }

    /**
     * Finds a desired instructor
     * 
     * @param id
     * @return instructor with matching id
     * @throws GRSException instructor not in the database
     */
    @Transactional
    public Instructor getInstructorById(int id) {
        Instructor instructor = instructorRepository.findInstructorById(id);
        if (instructor == null)
            throw new GRSException(HttpStatus.NOT_FOUND, "Instructor not found");
        return instructor;
    }

    /**
     * delete an instructor with an id
     * 
     * @param id
     * @throws GRSException instructor not in the database
     */
    @Transactional
    public void deleteIntructor(int id) {
        Instructor instructor = instructorRepository.findInstructorById(id);
        if (instructor == null)
            throw new GRSException(HttpStatus.NOT_FOUND, "Instructor not found");
        instructorRepository.delete(instructor);
    }

    /**
     * Get all the instructors in the database
     * 
     * @return a list of the instructors
     * @throws GRSException no instructors in the database
     */
    @Transactional
    public List<Instructor> getAllInstructors() {
        List<Instructor> instructors = instructorRepository.findAll();
        if (instructors.size() == 0) {
            throw new GRSException(HttpStatus.NOT_FOUND, "No Instructors found in the system.");
        }

        return instructors;
    }

    @Transactional
    public Instructor logInInstructor(String email, String password) {
        Instructor instructor = instructorRepository.findInstructorByEmailAndPassword(email, password);
        if (instructor == null) {
            throw new GRSException(HttpStatus.UNAUTHORIZED, "Invalid Email or Password");
        }
        return instructor;
    }
}
