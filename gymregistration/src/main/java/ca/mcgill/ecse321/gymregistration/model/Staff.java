package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Staff extends GymUser{
    @ManyToOne
    private Schedule schedule;

    public Staff(String aEmail, String aPassword, Schedule schedule) {
        super(aEmail, aPassword);
        this.schedule = schedule;
    }

    public Staff() {
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
    public void delete()
    {
        schedule = null;
        super.delete();
    }

    @Override
    public String toString() {
        return "Staff{" +
                "schedule=" + schedule +
                '}';
    }
}
