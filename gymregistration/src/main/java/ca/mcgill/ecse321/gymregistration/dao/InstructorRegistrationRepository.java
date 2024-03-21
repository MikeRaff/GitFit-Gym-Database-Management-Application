package ca.mcgill.ecse321.gymregistration.dao;

import ca.mcgill.ecse321.gymregistration.model.Instructor;
import ca.mcgill.ecse321.gymregistration.model.InstructorRegistration;
import ca.mcgill.ecse321.gymregistration.model.Session;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface InstructorRegistrationRepository extends CrudRepository<InstructorRegistration, Integer> {
    InstructorRegistration findInstructorRegistrationById(int id);
    InstructorRegistration findInstructorRegistrationByInstructorAndSession(Instructor instructor, Session session);
    List<InstructorRegistration> findInstructorRegistrationsBySession(Session session);
    InstructorRegistration findInstructorRegistrationByInstructorAndSession(Instructor instructor, Session session);
    List<InstructorRegistration> findInstructorRegistrationsBySession(Session session);
}
