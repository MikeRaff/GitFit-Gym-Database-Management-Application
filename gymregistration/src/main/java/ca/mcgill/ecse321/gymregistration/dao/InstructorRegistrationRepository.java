package ca.mcgill.ecse321.gymregistration.dao;

import ca.mcgill.ecse321.gymregistration.model.InstructorRegistration;
import org.springframework.data.repository.CrudRepository;

public interface InstructorRegistrationRepository extends CrudRepository<InstructorRegistration, Integer> {
    InstructorRegistration findInstructorRegistrationById(int id);
}
