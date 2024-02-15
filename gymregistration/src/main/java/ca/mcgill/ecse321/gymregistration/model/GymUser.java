package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class GymUser {
    @Id
    private Long id;

    private String name;
    private String email;
    private String password;
}
