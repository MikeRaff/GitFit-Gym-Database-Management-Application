package ca.mcgill.ecse321.gymregistration.dao;

import ca.mcgill.ecse321.gymregistration.model.Customer;
import ca.mcgill.ecse321.gymregistration.model.CustomerRegistration;
import ca.mcgill.ecse321.gymregistration.model.Session;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRegistrationRepository extends CrudRepository<CustomerRegistration, Integer> {
    CustomerRegistration findCustomerRegistrationById(int id);
    CustomerRegistration findCustomerRegistrationByCustomerAndSession(Customer customer, Session session);
}
