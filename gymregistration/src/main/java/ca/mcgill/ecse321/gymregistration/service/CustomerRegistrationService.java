package ca.mcgill.ecse321.gymregistration.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;

import ca.mcgill.ecse321.gymregistration.dao.CustomerRegistrationRepository;
import ca.mcgill.ecse321.gymregistration.dao.CustomerRepository;
import ca.mcgill.ecse321.gymregistration.dao.SessionRepository;
import ca.mcgill.ecse321.gymregistration.model.Customer;
import ca.mcgill.ecse321.gymregistration.model.CustomerRegistration;
import ca.mcgill.ecse321.gymregistration.model.InstructorRegistration;
import ca.mcgill.ecse321.gymregistration.model.Session;

import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;
import org.springframework.http.HttpStatus;

@Service
public class CustomerRegistrationService {
    
    @Autowired
    CustomerRegistrationRepository customerRegistrationRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    SessionRepository sessionRepository;

     /**
     * Create a new customer registeration to add a customer to a class
     * @param session
     * @param email
     * @return new customer registration
     * @throws GRSException attempt to register for session is invalid
     */
    @Transactional
    public CustomerRegistration registerCustomerToSession(int sessionId, int customerId){

        // can't have null customer register
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null){
            throw new GRSException(HttpStatus.UNAUTHORIZED, "Customer does not exist.");
        } 

        // can't register for null session
        Session session = sessionRepository.findSessionById(sessionId);
        if(session == null){
            throw new GRSException(HttpStatus.UNAUTHORIZED, "Session does not exist.");
        }

        // can't register again for same session
        CustomerRegistration customerRegistration = customerRegistrationRepository.findCustomerRegistrationByCustomerAndSession(customer, session);
        if (customerRegistration != null){
            throw new GRSException(HttpStatus.UNAUTHORIZED, "Cannot register for same session twice.");
        }
        
        // can't register if session is at capacity
        // Check if the customer has entered their credit card info

        // can't register if session has already started   
        customerRegistration = new CustomerRegistration(session.getDate(), session, customer);     
        if (customerRegistration.getDate().after(session.getDate())){
            throw new GRSException(HttpStatus.UNAUTHORIZED, "Cannot register for in-progress session.");
        }

        customerRegistrationRepository.save(customerRegistration);
        return customerRegistration;
    }


    /**
     * Remove customer from a session 
     * @param Session
     * @param email
     * @throws GRSException not able to remove customer
     */
    @Transactional
    public void removeCustomerFromSession(Session Session, String email)
    {

    }


    @Transactional 
    public void removeCustomerFromAllSessions(String email){

    }

}
