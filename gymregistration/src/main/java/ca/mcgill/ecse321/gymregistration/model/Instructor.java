package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Instructor extends Staff{
    @Id
    private Long instructorId;
}
