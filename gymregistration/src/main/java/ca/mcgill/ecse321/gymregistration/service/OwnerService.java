package ca.mcgill.ecse321.gymregistration.service;

import ca.mcgill.ecse321.gymregistration.dao.CustomerRepository;
import ca.mcgill.ecse321.gymregistration.dao.InstructorRepository;
import ca.mcgill.ecse321.gymregistration.dao.OwnerRepository;
import ca.mcgill.ecse321.gymregistration.dao.PersonRepository;
import ca.mcgill.ecse321.gymregistration.model.Owner;
import ca.mcgill.ecse321.gymregistration.model.Person;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    PersonRepository personRepository;

    /**
     * CreateOwner: creating an owner
     * @param email: Email of the owner
     * @param password: Password of the owner
     * @param person_id: iD of the person
     * @return The created owner
     * @throws GRSException Invalid owner creation request
     */
    @Transactional
    public Owner createOwner(String email, String password, int person_id){
        if (email == null || password == null || !email.contains("@")) {
            throw new GRSException(HttpStatus.BAD_REQUEST, "Must include valid email and password.");
        }
        if(ownerRepository.findOwnerByEmail(email) != null || instructorRepository.findInstructorByEmail(email) != null || customerRepository.findCustomerByEmail(email) != null) {
            throw new GRSException(HttpStatus.CONFLICT, "User with email already exists.");
        }
        Person person = personRepository.findPersonById(person_id);
        if (person == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Person not found.");
        }
        Owner owner = new Owner();
        owner.setEmail(email);
        owner.setPassword(password);
        owner.setPerson(person);
        return ownerRepository.save(owner);
    }

    /**
     * UpdateEmail: Allow users to edit their email information
     * @param oldEmail: Old email of owner
     * @param password: Password of owner
     * @param newEmail: New email of owner
     * @return The new owner
     * @throws GRSException Owner not found, invalid email and password combination, or invalid new email
     */
    @Transactional
    public Owner updateEmail(String oldEmail, String password, String newEmail){
        Owner owner = ownerRepository.findOwnerByEmailAndPassword(oldEmail, password);
        if (owner == null) {
            throw new GRSException(HttpStatus.NOT_FOUND, "Owner not found, invalid email and password combination.");
        }
        if (newEmail == null || !newEmail.contains("@")) {
            throw new GRSException(HttpStatus.BAD_REQUEST, "Invalid new email.");
        }
        owner.setEmail(newEmail);
        return ownerRepository.save(owner);
    }

    /**
     * UpdatePassword: Allow users to edit their password information
     * @param email: Email of owner
     * @param oldPassword: Old password of owner
     * @param newPassword: New password of owner
     * @return The new owner
     * @throws GRSException Owner not found, invalid email and password combination, or invalid new password
     */
    @Transactional
    public Owner updatePassword(String email, String oldPassword, String newPassword){
        Owner owner = ownerRepository.findOwnerByEmailAndPassword(email, oldPassword);
        if (owner == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Owner not found, invalid email and password combination.");
        }
        if (newPassword == null){
            throw new GRSException(HttpStatus.BAD_REQUEST, "Invalid new password.");
        }
        owner.setPassword(newPassword);
        return ownerRepository.save(owner);
    }

    /**
     * getOwnerByEmail: getting a owner by their email
     * @param email: the owner to search with
     * @return The owner
     * @throws GRSException owner not found
     */
    @Transactional
    public Owner getOwnerByEmail(String email){
        Owner owner = ownerRepository.findOwnerByEmail(email);
        if (owner == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Owner not found.");
        }
        return owner;
    }

    /**
     * GetAllOwners: getting all existing owners
     * @return List of all existing owners
     * @throws GRSException No customers found
     */
    @Transactional
    public List<Owner> getAllOwners(){
        List<Owner> owners = (List<Owner>) ownerRepository.findAll();
        if(owners.size() == 0){
            throw new GRSException(HttpStatus.NOT_FOUND, "No owners found in the system.");
        }
        return owners;
    }

    /**
     * DeleteOwner: delete the owner
     * @param email: Email of owner to be deleted
     * @throws GRSException owner not found
     */
    @Transactional
    public void deleteOwner(String email){
        Owner owner = ownerRepository.findOwnerByEmail(email);
        if(owner == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Owner not found.");
        }
        ownerRepository.deleteOwnerByEmail(email);
    }

    /**
     * LoginOwner: allow an owner to log in
     * @param email: Email of the owner
     * @param password: password of the owner
     * @return The owner
     * @throws GRSException Invalid owner email or password
     */
    public Owner loginOwner(String email, String password){
        Owner owner = ownerRepository.findOwnerByEmailAndPassword(email, password);
        if (owner == null) {
            throw new GRSException(HttpStatus.UNAUTHORIZED, "Invalid email or password.");
        }
        return owner;
    }
   
}
