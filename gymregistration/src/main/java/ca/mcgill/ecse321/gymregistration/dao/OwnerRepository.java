package ca.mcgill.ecse321.gymregistration.dao;

import ca.mcgill.ecse321.gymregistration.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OwnerRepository extends CrudRepository<Owner, Integer> {
    Owner findOwnerById(Integer id);
    List<Owner> findOwnersByPerson_Name(String name);
}
