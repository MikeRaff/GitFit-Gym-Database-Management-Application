package ca.mcgill.ecse321.gymregistration.dao;

import ca.mcgill.ecse321.gymregistration.model.SportsRegistrationSystem;
import org.springframework.data.repository.CrudRepository;

public interface SportsRegistrationSystemRepository extends CrudRepository<SportsRegistrationSystem, Integer> {
    SportsRegistrationSystem findSportsRegistrationSystemsById(int id);
}
