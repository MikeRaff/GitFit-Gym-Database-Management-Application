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
     * registerCustomerToSession: Create a new customer registration to add a customer to a session
     * @param sessionId
     * @param email
     * @return new customer registration
     * @throws GRSException if attempt to register for session is invalid
     */
    @Transactional
    public CustomerRegistration registerCustomerToSession(int sessionId, String email){

        if (sessionId == 0 || email == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "No session or customer entered.");
        }

        Customer customer = customerRepository.findCustomerByEmail(email);
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
     * getCustomerRegistrationByCustomerAndSession: fetch an existing registration associated to a customer and a session
     * @param sessionId
     * @param email
     * @return registration of customer for a session
     * @throws GRSException if attempt to find registration is invalid
     */
    @Transactional
    public CustomerRegistration getCustomerRegistrationByCustomerAndSession( int sessionId, String email ){
        if (sessionId == 0 || email == null){
            throw new GRSException(HttpStatus.BAD_REQUEST, "No session or customer entered.");
        }

        Customer customer = customerRepository.findCustomerByEmail(email);
        if (customer == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Customer not found.");
        } 
        Session session = sessionRepository.findSessionById(sessionId);
        if(session == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Session not found.");
        }

        CustomerRegistration customerRegistration = customerRegistrationRepository.findCustomerRegistrationByCustomerAndSession(customer, session);
        if (customerRegistration == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "The registration does not exist.");
        }
        return customerRegistration;
    }

    /**
     * getCustomerRegistrationsByCustomer: gets all registrations for a customer
     * @param email
     * @return list of all customer registrations for a customer
     * @throws GRSException if attempt to get all registrations from customer is invalid
     */
    @Transactional
    public List<CustomerRegistration> getCustomerRegistrationsByCustomer(String email){
        if (email == null){
            throw new GRSException(HttpStatus.BAD_REQUEST, "No customer entered.");
        }

        Customer customer = customerRepository.findCustomerByEmail(email);
        if (customer == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Customer not found.");
        } 
        List<CustomerRegistration> registrations = customerRegistrationRepository.findCustomerRegistrationsByCustomer_Email(email);
        if(registrations.size() == 0){
            throw new GRSException(HttpStatus.NOT_FOUND, "No registrations found in the system.");
        }
        return registrations;
    }

    /**
     * getCustomerRegistrationsBySession: gets all registrations for a session
     * @param sessionId
     * @return list of all customer registrations for a session
     * @throws GRSException if attempt to get all registrations from session is invalid
     */
    @Transactional
    public List<CustomerRegistration> getCustomerRegistrationsBySession(int sessionId){
        if (sessionId == 0){
            throw new GRSException(HttpStatus.BAD_REQUEST, "No customer entered.");
        }

        Session session = sessionRepository.findSessionById(sessionId);
        if (session == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Session not found.");
        } 
        List<CustomerRegistration> registrations = customerRegistrationRepository.findCustomerRegistrationsBySession_Id(sessionId);
        if(registrations.size() == 0){
            throw new GRSException(HttpStatus.NOT_FOUND, "No registrations found in the system.");
        }
        return registrations;
    }

    /**
     * removeCustomerFromSession: Remove customer from a session 
     * @param sessionId
     * @param email
     * @throws GRSException if attempt to remove customer from session is invalid
     */
    @Transactional
    public void removeCustomerFromSession(int sessionId , String email) {
        if (sessionId == 0 || email == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "No session or customer entered.");
        }

        Customer customer = customerRepository.findCustomerByEmail(email);
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

        customerRegistrationRepository.delete(customerRegistration);
    }

    /**
     * removeCustomerFromAllSessions: remove a customer from all sessions in which he is registered
     * @param id
     * @throws GRSException if customer is invalid
     */
    @Transactional 
    public void removeCustomerFromAllSessions(int id){
        Customer customer = customerRepository.findCustomerById(id);

        if (customer == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Customer not found.");
        }

        customerRegistrationRepository.deleteCustomerRegistrationsByCustomer_Id(id);
    }

}
