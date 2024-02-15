package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Time;
import java.sql.Date;

@Entity
public class Session {
    @Id
    private Long sessionId;
    private Date date;
    private Time startTime;
    private Time endTime;
    private String description;
    private String name;
    private String location;
}
