package ca.mcgill.ecse321.gymregistration.dao;

import ca.mcgill.ecse321.gymregistration.model.Instructor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InstructorRepository extends CrudRepository<Instructor, Integer> {
    Instructor findInstructorById(Integer id);
    List<Instructor> findInstructorsByPerson_Name(String name);
}
