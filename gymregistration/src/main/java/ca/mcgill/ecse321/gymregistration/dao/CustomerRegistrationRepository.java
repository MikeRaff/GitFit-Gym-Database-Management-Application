package ca.mcgill.ecse321.gymregistration.dao;

import ca.mcgill.ecse321.gymregistration.model.CustomerRegistration;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRegistrationRepository extends CrudRepository<CustomerRegistration, Integer> {
    CustomerRegistration findCustomerRegistrationById(int id);
}
