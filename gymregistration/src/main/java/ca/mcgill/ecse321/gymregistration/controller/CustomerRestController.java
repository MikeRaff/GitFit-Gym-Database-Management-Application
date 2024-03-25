package ca.mcgill.ecse321.gymregistration.controller;

import ca.mcgill.ecse321.gymregistration.dto.CustomerDto;
import ca.mcgill.ecse321.gymregistration.dto.InstructorDto;
import ca.mcgill.ecse321.gymregistration.dto.PersonDto;
import ca.mcgill.ecse321.gymregistration.model.Customer;
import ca.mcgill.ecse321.gymregistration.model.GymUser;
import ca.mcgill.ecse321.gymregistration.model.Instructor;
import ca.mcgill.ecse321.gymregistration.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class CustomerRestController {
    @Autowired
    private CustomerService customerService;

    /**
     * GetAllCustomers: getting all customers
     * @return All customers in database
     */
    @GetMapping(value = { "/customers", "/customers/"})
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers().stream().map(CustomerDto::new).collect(Collectors.toList());
    }

    /**
     * GetCustomer: logging in customer
     * @param email: email to search with
     * @param password: password of customer
     * @return The customer
     * @throws IllegalArgumentException
     */
    @GetMapping(value = { "/customers/login", "/customers/login/"})
    public ResponseEntity<CustomerDto> loginCustomer(@RequestBody String email, @RequestBody String password) throws IllegalArgumentException {
        Customer customer = customerService.loginCustomer(email, password);
        return new ResponseEntity<>(new CustomerDto(customer), HttpStatus.OK);
    }
    /**
     * GetCustomer: getting customer by email
     * @param email: email to search with
     * @return The customer
     * @throws IllegalArgumentException
     */
    @GetMapping(value = { "/customers/{email}", "/customers/{email}/"})
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("email") String email) throws IllegalArgumentException {
        Customer customer = customerService.getCustomerByEmail(email);
        return new ResponseEntity<>(new CustomerDto(customer), HttpStatus.OK);
    }

    /**
     * CreateCustomer: creating a customer
     * @param email: email of customer
     * @param password: password of customer
     * @param person: Person to be created
     * @return Created customer
     */
    @PostMapping(value= { "/customers/create", "/customers/create/"})
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody String email, @RequestBody String password, @RequestBody PersonDto person){
        Customer customer = customerService.createCustomer(email, password, person.getId());
        return new ResponseEntity<>(new CustomerDto(customer), HttpStatus.CREATED);
    }

    /**
     * UpdateCustomerCreditCard: updating customer credit card
     * @param email: email of customer
     * @param password: password of customer
     * @param creditCardNumber: credit card of customer
     * @return Updated customer
     * @throws IllegalArgumentException
     */
    @PutMapping(value = {"/customers/updateCreditCard", "/customers/updateCreditCard/"})
    public ResponseEntity<CustomerDto> updateCustomerCreditCard(@RequestBody String email, @RequestBody String password, @RequestBody int creditCardNumber) throws IllegalArgumentException{
        Customer customer = customerService.updateCreditCard(email, password, creditCardNumber);
        return new ResponseEntity<>(new CustomerDto(customer), HttpStatus.OK);
    }

    /**
     * UpdateCustomerEmail: updating customer email
     * @param oldEmail: old email of customer
     * @param password: password of customer
     * @param newEmail: new email of customer
     * @return Edited customer
     * @throws IllegalArgumentException
     */
    @PutMapping(value = {"/customers/updateEmail", "/customers/updateEmail/"})
    public ResponseEntity<CustomerDto> updateCustomerEmail(@RequestBody String oldEmail, @RequestBody String password, @RequestBody String newEmail) throws IllegalArgumentException{
        Customer customer = customerService.updateEmail(oldEmail, password, newEmail);
        return new ResponseEntity<>(new CustomerDto(customer), HttpStatus.OK);
    }

    /**
     * UpdateCustomerPassword: updating a customers password
     * @param email: email of customer
     * @param oldPassword: old password of customer
     * @param newPassword: new password of customer
     * @return Updated customer
     * @throws IllegalArgumentException
     */
    @PutMapping(value = {"/customers/updatePassword", "/customers/updatedPassword/"})
    public ResponseEntity<CustomerDto> updateCustomerPassword(@RequestBody String email, @RequestBody String oldPassword, @RequestBody String newPassword) throws IllegalArgumentException{
        Customer customer = customerService.updatePassword(email, oldPassword, newPassword);
        return new ResponseEntity<>(new CustomerDto(customer), HttpStatus.OK);
    }

    /**
     * UpdateCustomerType: changing customer to instructor
     * @param email: email of customer
     * @param gymUser: new gym user type
     * @return Instructor
     * @throws IllegalArgumentException
     */
    @PutMapping(value = {"/customers/updateType", "/customers/updateType/"})
    public ResponseEntity<InstructorDto> updateCustomerType(@RequestBody String email, @RequestBody GymUser gymUser) throws IllegalArgumentException{
        Instructor instructor = customerService.changeAccountType(email, gymUser);
        return new ResponseEntity<>(new InstructorDto(instructor), HttpStatus.OK);
    }

    /**
     * DeleteCustomer: deleting a customer from database
     * @param email: email of customer
     * @param gymUser: type of customer
     * @throws IllegalArgumentException
     */
    @DeleteMapping(value = {"/customers/delete/{email}", "/customers/delete/{email}/"})
    public void deleteCustomer(@PathVariable("email") String email, @RequestBody GymUser gymUser) throws IllegalArgumentException{
        customerService.deleteCustomer(email, gymUser);
    }
}
