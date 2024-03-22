package ca.mcgill.ecse321.gymregistration.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;

import ca.mcgill.ecse321.gymregistration.dao.CustomerRegistrationRepository;
import ca.mcgill.ecse321.gymregistration.dao.CustomerRepository;
import ca.mcgill.ecse321.gymregistration.dao.SessionRepository;
import ca.mcgill.ecse321.gymregistration.model.Customer;
import ca.mcgill.ecse321.gymregistration.model.CustomerRegistration;
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
     * @param sessionId
     * @param customerId
     * @return new customer registration
     * @throws GRSException if attempt to register for session is invalid
     */
    @Transactional
    public CustomerRegistration registerCustomerToSession(int sessionId, int customerId){

        if (sessionId == 0 || customerId == 0){
            throw new GRSException(HttpStatus.NOT_FOUND, "No session or customer entered.");
        }

        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Customer not found.");
        } 
        if (customer.getCreditCardNumber() == 0){
            throw new GRSException(HttpStatus.UNAUTHORIZED, "Credit card must be entered to register for a class.");
        }
        
        Session session = sessionRepository.findSessionById(sessionId);
        if(session == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Session not found.");
        }

        CustomerRegistration customerRegistration = customerRegistrationRepository.findCustomerRegistrationByCustomerAndSession(customer, session);
        if (customerRegistration != null){
            throw new GRSException(HttpStatus.UNAUTHORIZED, "Cannot register for same session twice.");
        }
        
        List<CustomerRegistration> customerRegistrations = customerRegistrationRepository.findCustomerRegistrationsBySession_Id(sessionId);
        if(customerRegistrations.size() == session.getCapacity()){
            throw new GRSException(HttpStatus.UNAUTHORIZED, "Session is already at capacity.");
        }

        customerRegistration = new CustomerRegistration(session.getDate(), session, customer);     
        /*
         * Need to refactor this to include time as well 
         */
        if (customerRegistration.getDate().after(session.getDate()) || customerRegistration.getDate().equals(session.getDate())){
            throw new GRSException(HttpStatus.UNAUTHORIZED, "Cannot register for in-progress or completed session.");
        }

        customerRegistrationRepository.save(customerRegistration);
        return customerRegistration;
    }

    /**
     * Remove customer from a session 
     * @param sessionId
     * @param customerId
     * @throws GRSException not able to remove customer
     */
    @Transactional
    public void removeCustomerFromSession(int sessionId , int customerId) {
        if (sessionId == 0 || customerId == 0){
            throw new GRSException(HttpStatus.NOT_FOUND, "No session or customer entered.");
        }

        Customer customer = customerRepository.findCustomerById(customerId);
        Session session = sessionRepository.findSessionById(sessionId);

        if (customer == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Customer not found.");
        } 
        if(session == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Session not found.");
        }

        CustomerRegistration customerRegistration = customerRegistrationRepository.findCustomerRegistrationByCustomerAndSession(customer, session);
        if (customerRegistration == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "The registration does not exist.");
        }

        /*
        * Look into using Calendar instead of java.sql.Date & java.sql.Time 
        * Need to set constraint for deleting within 48 hours of session start
        */

        customerRegistrationRepository.deleteCustomerRegistrationById(customerId);
    }


    @Transactional 
    public void removeCustomerFromAllSessions(int id){
        Customer customer = customerRepository.findCustomerById(id);

        if (customer == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Customer not found.");
        }

        customerRegistrationRepository.deleteCustomerRegistrationsByCustomer_Id(id);
    }

}
