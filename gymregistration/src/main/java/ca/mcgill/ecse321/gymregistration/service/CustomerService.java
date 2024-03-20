package ca.mcgill.ecse321.gymregistration.service;

import ca.mcgill.ecse321.gymregistration.dao.CustomerRegistrationRepository;
import ca.mcgill.ecse321.gymregistration.dao.CustomerRepository;
import ca.mcgill.ecse321.gymregistration.dao.InstructorRepository;
import ca.mcgill.ecse321.gymregistration.dao.OwnerRepository;
import ca.mcgill.ecse321.gymregistration.model.ClassType;
import ca.mcgill.ecse321.gymregistration.model.Customer;
import ca.mcgill.ecse321.gymregistration.model.CustomerRegistration;
import ca.mcgill.ecse321.gymregistration.model.Instructor;
import ca.mcgill.ecse321.gymregistration.model.Session;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    CustomerRegistrationRepository customerRegistrationRepository;

    /**
     * CreateCustomer: creating a customer
     * @param email: Email of the customer
     * @param password: Password of the customer
     * @return The created customer
     * @throws GRSException Invalid creation request
     */
    @Transactional
    public Customer createCustomer(String email, String password){
        if (email == null || password == null){
            throw new GRSException(HttpStatus.BAD_REQUEST, "Must include email and password.");
        }
        if(customerRepository.findCustomerByEmail(email) != null || ownerRepository.findOwnerByEmail(email) != null || instructorRepository.findInstructorByEmail(email) != null){
            throw new GRSException(HttpStatus.CONFLICT, "User with email already exists.");
        }
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(password);
        return customerRepository.save(customer);
    }

    /**
     * UpdateCreditCard: Allow users to add or update their credit card information
     * @param email: email of customer
     * @param creditCardNumber: credit card number to be added
     * @return the new customer
     * @throws GRSException Customer not found
     */
    @Transactional
    public Customer updateCreditCard(String email, int creditCardNumber){
        Customer customer = customerRepository.findCustomerByEmail(email);
        if (customer == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Customer not found.");
        }
        customer.setCreditCardNumber(creditCardNumber);
        return customerRepository.save(customer);
    }

    /**
     * UpdateEmail: Allow users to edit their email information
     * @param oldEmail: old email of customer
     * @param newEmail: new email of customer
     * @return The new customer
     * @throws GRSException Customer not found or invalid email
     */
    @Transactional
    public Customer updateEmail(String oldEmail, String newEmail){
        Customer customer = customerRepository.findCustomerByEmail(oldEmail);
        if (customer == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Customer not found.");
        }
        if (newEmail == null){
            throw new GRSException(HttpStatus.BAD_REQUEST, "Invalid email.");
        }
        customer.setEmail(newEmail);
        return customerRepository.save(customer);
    }
    /**
     * UpdateEmail: Allow users to edit their email information
     * @param email: email of customer
     * @param oldPassword: old password of customer
     * @param newPassword: new password of customer
     * @return The new customer
     * @throws GRSException Customer not found or invalid password
     */
    @Transactional
    public Customer updatePassword(String email, String oldPassword, String newPassword){
        Customer customer = customerRepository.findCustomerByEmail(email);
        if (customer == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Customer not found.");
        }
        if (newPassword == null){
            throw new GRSException(HttpStatus.BAD_REQUEST, "Invalid new password.");
        }
        if (oldPassword != customer.getPassword()){
            throw new GRSException(HttpStatus.BAD_REQUEST, "Incorrect current password.");
        }
        customer.setPassword(newPassword);
        return customerRepository.save(customer);
    }

    /**
     * GetCustomerByEmail: getting a customer by their email
     * @param email: the email to search with
     * @return The customer
     * @throws GRSException Customer not found
     */
    @Transactional
    public Customer getCustomerByEmail(String email){
        Customer customer = customerRepository.findCustomerByEmail(email);
        if (customer == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Customer not found.");
        }
        return customer;
    }

    /**
     * GetAllCustomers: getting all existing customers
     * @return List of all existing customers
     * @throws GRSException No customers found
     */
    @Transactional
    public List<Customer> getAllCustomers(){
        List<Customer> customers = customerRepository.findAll();
        if(customers.size() == 0){
            throw new GRSException(HttpStatus.NOT_FOUND, "No Customers found in the system.");
        }
        return customers;
    }

    /**
     * DeleteCustomer: delete the customer
     * @param email: Email of customer to be deleted
     * @throws GRSException Customer not found
     */
    @Transactional
    public void deleteCustomer(String email){
        Customer customer = customerRepository.findCustomerByEmail(email);
        if(customer == null){
            throw new GRSException(HttpStatus.NOT_FOUND, "Customer not found.");
        }
        customerRepository.deleteCustomerByEmail(email);
    }

    /**
     * LoginCustomer: allow a customer to log in
     * @param email: Email of the customer
     * @param password: password of the customer
     * @return The customer
     * @throws GRSException Invalid customer email or password
     */
    public Customer loginCustomer(String email, String password){
        Customer customer = customerRepository.findCustomerByEmailAndPassword(email, password);
        if (customer == null) {
            throw new GRSException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
        return customer;
    }

    public  CustomerRegistration registerCustomerForClass(Session session, String email, String password)
    {
        Customer customer = customerRepository.findCustomerByEmailAndPassword(email, password);
        CustomerRegistration customerRegistration = customerRegistrationRepository.findCustomerRegistrationByCustomerAndSession(customer, session);
        if(customerRegistration !=null)
        {
            throw new GRSException(HttpStatus.UNAUTHORIZED, "already registered");
        }
        customerRegistration = new CustomerRegistration(session.getDate(),session, customer);
        customerRegistrationRepository.save(customerRegistration);
        return customerRegistration;
    }
}
