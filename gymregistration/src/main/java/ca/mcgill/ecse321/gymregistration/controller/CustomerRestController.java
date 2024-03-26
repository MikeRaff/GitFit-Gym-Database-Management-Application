package ca.mcgill.ecse321.gymregistration.controller;

import ca.mcgill.ecse321.gymregistration.dto.CustomerDto;
import ca.mcgill.ecse321.gymregistration.dto.GymUserDto;
import ca.mcgill.ecse321.gymregistration.dto.InstructorDto;
import ca.mcgill.ecse321.gymregistration.dto.PersonDto;
import ca.mcgill.ecse321.gymregistration.model.Customer;
import ca.mcgill.ecse321.gymregistration.model.GymUser;
import ca.mcgill.ecse321.gymregistration.model.Instructor;
import ca.mcgill.ecse321.gymregistration.model.Owner;
import ca.mcgill.ecse321.gymregistration.service.CustomerService;
import ca.mcgill.ecse321.gymregistration.service.exception.GRSException;

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
    @GetMapping(value = { "/customers/login/{email}/{password}", "/customers/login/{email}/{password}/"})
    public ResponseEntity<CustomerDto> loginCustomer(@PathVariable("email") String email, @PathVariable("password") String password) throws IllegalArgumentException {
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
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto)throws IllegalArgumentException{
        try{
            Customer customer = customerService.createCustomer(customerDto.getEmail(), customerDto.getPassword(), customerDto.getPerson().getId());
            return new ResponseEntity<>(new CustomerDto(customer), HttpStatus.CREATED);
        }catch(GRSException e){
            return new ResponseEntity<>(new CustomerDto(), e.getStatus());
        }
    }
    /**
     * UpdateCustomerCreditCard: updating customer credit card
     * @param email: email of customer
     * @param password: password of customer
     * @param creditCardNumber: credit card of customer
     * @return Updated customer
     * @throws IllegalArgumentException
     */
    @PutMapping(value = {"/customers/updateCreditCard/{newCreditCard}", "/customers/updateCreditCard/{newCreditCard}/"})
    public ResponseEntity<CustomerDto> updateCustomerCreditCard(@PathVariable("newCreditCard") int newCreditCard, @RequestBody CustomerDto customerDto) throws IllegalArgumentException{
        Customer customer = customerService.updateCreditCard(customerDto.getEmail(), customerDto.getPassword(), newCreditCard);
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
    @PutMapping(value = {"/customers/updateEmail/{newEmail}", "/customers/updateEmail/{newEmail}/"})
    public ResponseEntity<CustomerDto> updateCustomerEmail(@RequestBody CustomerDto customerDto, @PathVariable String newEmail) throws IllegalArgumentException{
        Customer customer = customerService.updateEmail(customerDto.getEmail(), customerDto.getPassword(), newEmail);
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
    @PutMapping(value = {"/customers/updatePassword/{newPassword}", "/customers/updatedPassword/{newPassword}/"})
    public ResponseEntity<CustomerDto> updateCustomerPassword(@RequestBody CustomerDto customerDto, @PathVariable String newPassword) throws IllegalArgumentException{
        Customer customer = customerService.updatePassword(customerDto.getEmail(), customerDto.getPassword(), newPassword);
        return new ResponseEntity<>(new CustomerDto(customer), HttpStatus.OK);
    }

    /**
     * UpdateCustomerType: changing customer to instructor
     * @param email: email of customer
     * @param gymUser: new gym user type
     * @return Instructor
     * @throws IllegalArgumentException
     */
    @PutMapping(value = {"/customers/updateToInstructor/{customerEmail}", "/customers/updateToInstructor/{customerEmail}/"})
    public ResponseEntity<InstructorDto> updateCustomerType(@PathVariable String email, @RequestBody Owner owner) throws IllegalArgumentException{
        Instructor instructor = customerService.changeAccountType(email, owner);
        return new ResponseEntity<>(new InstructorDto(instructor), HttpStatus.OK);
    }

    /**
     * DeleteCustomer: deleting a customer from database
     * @param email: email of customer
     * @param gymUser: type of customer
     * @throws IllegalArgumentException
     */
    @DeleteMapping(value = {"/customers/delete/{email}", "/customers/delete/{email}/"})
    public ResponseEntity<String> deleteCustomer(@PathVariable("email") String email, @RequestBody Owner owner) throws IllegalArgumentException{
        customerService.deleteCustomer(email, owner);
        return new ResponseEntity<>(email, HttpStatus.OK);
    }
}
