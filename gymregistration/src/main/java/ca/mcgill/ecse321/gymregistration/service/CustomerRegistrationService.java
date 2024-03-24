package ca.mcgill.ecse321.gymregistration.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;

import ca.mcgill.ecse321.gymregistration.dao.CustomerRegistrationRepository;
import ca.mcgill.ecse321.gymregistration.dao.CustomerRepository;
import ca.mcgill.ecse321.gymregistration.dao.InstructorRegistrationRepository;
import ca.mcgill.ecse321.gymregistration.dao.SessionRepository;
import ca.mcgill.ecse321.gymregistration.model.Customer;
import ca.mcgill.ecse321.gymregistration.model.CustomerRegistration;
import ca.mcgill.ecse321.gymregistration.model.GymUser;
import ca.mcgill.ecse321.gymregistration.model.Instructor;
import ca.mcgill.ecse321.gymregistration.model.Owner;
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
    InstructorRegistrationRepository instructorRegistrationRepository;
    @Autowired
    SessionRepository sessionRepository;

     /**
     * RegisterCustomerToSession: Create a new customer registration to add a customer to a session
     * @param sessionId: id of the session
     * @param email: email of the customer
     * @return new customer registration
     * @throws GRSException if attempt to register customer to session is invalid
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
            throw new GRSException(HttpStatus.CONFLICT, "Cannot register for same session twice.");
        }
        
        List<CustomerRegistration> customerRegistrations = customerRegistrationRepository.findCustomerRegistrationsBySession_Id(sessionId);
        if(customerRegistrations.size() == session.getCapacity()){
            throw new GRSException(HttpStatus.UNAUTHORIZED, "Session is already at capacity.");
        }

        //getting current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        //converting startTime and date to LocalDateTime
        LocalDateTime sessionDatetime = LocalDateTime.of(session.getDate().toLocalDate(), session.getStartTime().toLocalTime());
        //calculating difference
        Duration timeDifference = Duration.between(currentDateTime, sessionDatetime);
        //checking if registration time (current) is before the start of the session
        if (timeDifference.toHours() < 0){
            throw new GRSException(HttpStatus.BAD_REQUEST, "Cannot register for in-progress or completed session.");
        }

        customerRegistration = new CustomerRegistration(session.getDate(), session, customer);     
        customerRegistrationRepository.save(customerRegistration);
        return customerRegistration;
    }

    /**
     * getCustomerRegistrationByCustomerAndSession: fetch an existing registration associated to a customer and a session
     * @param sessionId: id of the session
     * @param email: email of the customer
     * @return registration of customer for a session
     * @throws GRSException if attempt to find registration is invalid
     */
    @Transactional
    public CustomerRegistration getCustomerRegistrationByCustomerAndSession(int sessionId, String email){
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
     * GetCustomerRegistrationsByCustomer: gets all registrations for a customer
     * @param email: email of the customer to search with
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
     * GetCustomerRegistrationsBySession: gets all registrations for a session
     * @param sessionId: id of the session to search with
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
     * RemoveCustomerFromSession: remove customer from a session 
     * @param sessionId: id of the session
     * @param email: email of the customer
     * @param gymUser: gymUser trying to remove customer from session
     * @throws GRSException if attempt to remove customer from session is invalid
     */
    @Transactional
    public void removeCustomerFromSession(int sessionId, String email, GymUser gymUser) {
        if (sessionId == 0 || email == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "No session or customer entered.");
        }
        if((gymUser instanceof Customer && !gymUser.getEmail().equals(email)) || (gymUser instanceof Instructor && instructorRegistrationRepository.findInstructorRegistrationByInstructor_idAndSession_id(gymUser.getId().intValue(), sessionId) == null)){
            throw new GRSException(HttpStatus.UNAUTHORIZED, "Customers can only be removed from sessions by the owner, instructor or themselves.");
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

        // current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        // session date and time
        LocalDateTime sessionDateTime = LocalDateTime.of(session.getDate().toLocalDate(), session.getStartTime().toLocalTime());
        // getting difference
        Duration timeDifference = Duration.between(currentDateTime, sessionDateTime);
        if (timeDifference.toHours() < 48){
            throw new GRSException(HttpStatus.BAD_REQUEST, "Cancellation of registration must be at least 48 hours before session start.");
        }

        customerRegistrationRepository.delete(customerRegistration);
    }

    /**
     * RemoveCustomerFromAllSessions: remove a customer from all sessions in which he is registered
     * @param email: email of the customer
     * @param gymUser: gymUser trying to remove customer from all sessions
     * @throws GRSException if attempt to remove customer from all sessions is invalid
     */
    @Transactional 
    public void removeCustomerFromAllSessions(String email, GymUser gymUser) {
        if(!(gymUser instanceof Owner) || gymUser instanceof Customer && !gymUser.getEmail().equals(email)){
            throw new GRSException(HttpStatus.UNAUTHORIZED, "Customers can only remove themselves from sessions.");
        }
        Customer customer = customerRepository.findCustomerByEmail(email);

        if (customer == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Customer not found.");
        }
        customerRegistrationRepository.deleteCustomerRegistrationsByCustomer_Email(email);
    }
}
