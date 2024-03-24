package ca.mcgill.ecse321.gymregistration.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gymregistration.dto.CustomerRegistrationDto;
import ca.mcgill.ecse321.gymregistration.model.CustomerRegistration;
import ca.mcgill.ecse321.gymregistration.service.CustomerRegistrationService;

@CrossOrigin( origins = "*")
@RestController
public class CustomerRegistrationRestController {
    
    @Autowired
    private CustomerRegistrationService customerRegistrationService;

    /**
     * getCustomerRegistrationsOfCustomer: gets all registrations for a customer
     * @param email
     * @return all registrations in database of customer
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/customer-registrations/{email}", "/customer-registrations/{email}/"})
    public List<CustomerRegistrationDto> getCustomerRegistrationsOfCustomer(@PathVariable("email") String email) throws IllegalArgumentException{
        return customerRegistrationService.getCustomerRegistrationsByCustomer(email).stream().map(CustomerRegistrationDto::new).collect(Collectors.toList());
    }

    /**
     * getCustomerRegistrationOfSession: gets all registrations for a session
     * @param sessionId
     * @return all registrations in database of session
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/customer-registrations/{sessionId}", "/customer-registrations/{sessionId}/"})
    public List<CustomerRegistrationDto> getCustomerRegistrationOfSession(@PathVariable("sessionId") int sessionId) throws IllegalArgumentException{
        return customerRegistrationService.getCustomerRegistrationsBySession(sessionId).stream().map(CustomerRegistrationDto::new).collect(Collectors.toList());
    }

    /**
     * getCustomerRegistration: returns registration to a session for a customer
     * @param email
     * @param id
     * @return customer registration in the system
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/customer-registration/{email}/{id}", "/customer-registration/{email}/{sessionId}/"})
    public ResponseEntity<CustomerRegistrationDto> getCustomerRegistration(@PathVariable("email") String email, 
            @PathVariable("sessionId") int sessionId) throws IllegalArgumentException{
        CustomerRegistration customerRegistration = customerRegistrationService.getCustomerRegistrationByCustomerAndSession(sessionId, email);
        return new ResponseEntity<>(new CustomerRegistrationDto(customerRegistration), HttpStatus.OK);
    }

    /**
     * registerCustomerToSession: creates a new registration to register a customer to an event
     * @param sessionId
     * @param email
     * @return customer registration in the system
     * @throws IllegalArgumentException
     */
    @PostMapping(value = {"/customer-registration/register", "/customer-registration/register/"})
    public ResponseEntity<CustomerRegistrationDto> registerCustomerToSession(@RequestParam(name = "sessionId") int sessionId, 
            @RequestParam(name="email") String email) throws IllegalArgumentException{
        CustomerRegistration customerRegistration = customerRegistrationService.registerCustomerToSession(sessionId, email);
        return new ResponseEntity<>(new CustomerRegistrationDto(customerRegistration), HttpStatus.OK);
    }

    /**
     * removeCustomerRegistration: deletes registration to a session for a customer
     * @param email
     * @param sessionId
     * @throws IllegalArgumentException
     */
    @DeleteMapping(value = {"/customer-registration/remove/{email}/{sessionId}", "/customer-registration/remove/{email}/{sessionId}/"})
    public void removeCustomerRegistration(@PathVariable("email") String email, @PathVariable("sessionId") int sessionId) throws IllegalArgumentException{
        customerRegistrationService.removeCustomerFromSession(sessionId, email);
    }

    /**
     * removeAllCustomerRegistrations: deletes registrations to all sessions for a customer
     * @param email
     * @throws IllegalArgumentException
     */
    @DeleteMapping(value = {"/customer-registration/remove/{email}", "/customer-registration/remove/{email}/"})
    public void removeAllCustomerRegistrations(@PathVariable("email") String email) throws IllegalArgumentException{
        customerRegistrationService.removeCustomerFromAllSessions(email);
    }

}
