package ca.mcgill.ecse321.gymregistration.dao;

import ca.mcgill.ecse321.gymregistration.model.GymUser;
import org.springframework.data.repository.CrudRepository;

public interface GymUserRepository extends CrudRepository<GymUser, Integer> {
    GymUser findGymUserByEmail(String email);
}
