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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gymregistration.dto.CustomerRegistrationDto;
import ca.mcgill.ecse321.gymregistration.model.CustomerRegistration;
import ca.mcgill.ecse321.gymregistration.model.GymUser;
import ca.mcgill.ecse321.gymregistration.service.CustomerRegistrationService;

@CrossOrigin( origins = "*")
@RestController
public class CustomerRegistrationRestController {
    
    @Autowired
    private CustomerRegistrationService customerRegistrationService;

    /**
     * GetCustomerRegistrationsOfCustomer: gets all registrations for a customer
     * @param email: email of customer
     * @return all registrations in database of customer
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/customer-registrations/{email}", "/customer-registrations/{email}/"})
    public List<CustomerRegistrationDto> getCustomerRegistrationsOfCustomer(@PathVariable("email") String email) throws IllegalArgumentException{
        return customerRegistrationService.getCustomerRegistrationsByCustomer(email).stream().map(CustomerRegistrationDto::new).collect(Collectors.toList());
    }

    /**
     * GetCustomerRegistrationOfSession: gets all registrations for a session
     * @param sessionId: id of session
     * @return all registrations in database of session
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/customer-registrations/{sessionId}", "/customer-registrations/{sessionId}/"})
    public List<CustomerRegistrationDto> getCustomerRegistrationOfSession(@PathVariable("sessionId") int sessionId) throws IllegalArgumentException{
        return customerRegistrationService.getCustomerRegistrationsBySession(sessionId).stream().map(CustomerRegistrationDto::new).collect(Collectors.toList());
    }

    /**
     * GetCustomerRegistration: returns registration to a session for a customer
     * @param email: email of customer
     * @param sessionId: id of session
     * @return customer registration in the system
     * @throws IllegalArgumentException
     */
    @GetMapping(value = {"/customer-registration/{email}/{sessionId}", "/customer-registration/{email}/{sessionId}/"})
    public ResponseEntity<CustomerRegistrationDto> getCustomerRegistration(@PathVariable("email") String email, 
            @PathVariable("sessionId") int sessionId) throws IllegalArgumentException{
        CustomerRegistration customerRegistration = customerRegistrationService.getCustomerRegistrationByCustomerAndSession(sessionId, email);
        return new ResponseEntity<>(new CustomerRegistrationDto(customerRegistration), HttpStatus.OK);
    }

    /**
     * RegisterCustomerToSession: creates a new registration to register a customer to an event
     * @param sessionId: id of session
     * @param email: email of customer
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
     * RemoveCustomerRegistration: deletes registration to a session for a customer
     * @param email: email of customer
     * @param sessionId: id of session
     * @param gymUser: user removing the registration
     * @throws IllegalArgumentException
     */
    @DeleteMapping(value = {"/customer-registration/remove/{email}/{sessionId}", "/customer-registration/remove/{email}/{sessionId}/"})
    public void removeCustomerRegistration(@PathVariable("email") String email, @PathVariable("sessionId") int sessionId, @RequestBody GymUser gymUser) throws IllegalArgumentException{
        customerRegistrationService.removeCustomerFromSession(sessionId, email, gymUser);
    }

    /**
     * RemoveAllCustomerRegistrations: deletes registrations to all sessions for a customer
     * @param email: email of customer
     * @param gymUser: user removing the registrations
     * @throws IllegalArgumentException
     */
    @DeleteMapping(value = {"/customer-registration/remove/{email}", "/customer-registration/remove/{email}/"})
    public void removeAllCustomerRegistrations(@PathVariable("email") String email, @RequestBody GymUser gymUser) throws IllegalArgumentException{
        customerRegistrationService.removeCustomerFromAllSessions(email, gymUser);
    }

}
