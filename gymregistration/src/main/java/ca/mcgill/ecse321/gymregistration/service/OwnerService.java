package ca.mcgill.ecse321.gymregistration.service;

import ca.mcgill.ecse321.gymregistration.dao.CustomerRegistrationRepository;
import ca.mcgill.ecse321.gymregistration.dao.OwnerRepository;
import ca.mcgill.ecse321.gymregistration.model.Owner;
import ca.mcgill.ecse321.gymregistration.model.Session;
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
    /**
     * CreateOwner: creating a owner
     * @param email: Email of the owner
     * @param password: Password of the owner
     * @return The created owner
     * @throws GRSException Invalid creation request
     */
    @Transactional
    public Owner createOwner(String email, String password){
        if (email == null || password == null){
            throw new GRSException(HttpStatus.BAD_REQUEST, "Must include email and password.");
        }
        if(ownerRepository.findOwnerByEmail(email) != null){
            throw new GRSException(HttpStatus.CONFLICT, "User with email already exists.");
        }
        Owner owner = new Owner();
        owner.setEmail(email);
        owner.setPassword(password);
        return ownerRepository.save(owner);
    }

    /**
     * UpdateEmail: Allow users to edit their email information
     * @param oldEmail: old email of customer
     * @param newEmail: new email of customer
     * @return The new customer
     * @throws GRSException Customer not found or invalid email
     */
    @Transactional
    public Owner updateEmail(String oldEmail, String newEmail){
        Owner owner = ownerRepository.findOwnerByEmail(oldEmail);
        if (owner == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Customer not found.");
        }
        if (newEmail == null){
            throw new GRSException(HttpStatus.BAD_REQUEST, "Invalid email.");
        }
        owner.setEmail(newEmail);
        return ownerRepository.save(owner);
    }

    /**
     * UpdateEmail: Allow users to edit their email information
     * @param email: email of owner
     * @param oldPassword: old password of owner
     * @param newPassword: new password of owner
     * @return The new owner
     * @throws GRSException Owner not found or invalid password
     */
    @Transactional
    public Owner updatePassword(String email, String oldPassword, String newPassword){
        Owner owner = ownerRepository.findOwnerByEmail(email);
        if (owner == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Owner not found.");
        }
        if (newPassword == null){
            throw new GRSException(HttpStatus.BAD_REQUEST, "Invalid new password.");
        }
        if (oldPassword != owner.getPassword()){
            throw new GRSException(HttpStatus.BAD_REQUEST, "Incorrect current password.");
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
            throw new GRSException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
        return owner;
    }
   
}
