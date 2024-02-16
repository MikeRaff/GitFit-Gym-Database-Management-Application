package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Instructor extends Staff{
    @Id
    private int instructorId;

    public Instructor(String aEmail, String aPassword, Schedule schedule, int instructorId) {
        super(aEmail, aPassword, schedule);
        this.instructorId = instructorId;
    }

    public Instructor() {
    }

    public void delete()
    {
        super.delete();
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "instructorId=" + instructorId +
                '}';
    }
}
