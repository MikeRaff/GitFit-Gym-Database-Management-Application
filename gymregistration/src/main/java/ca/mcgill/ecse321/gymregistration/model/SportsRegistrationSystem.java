package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class SportsRegistrationSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToMany(mappedBy = "sportsRegistrationSystem", cascade = CascadeType.ALL)
    private List<Schedule> schedule;
    @OneToMany(mappedBy = "sportsRegistrationSystem", cascade = CascadeType.ALL)
    private List<Person> person;

    public SportsRegistrationSystem(List<Schedule> schedule, List<Person> person) {
        this.schedule = schedule;
        this.person = person;
    }

    public SportsRegistrationSystem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }

    public List<Person> getPerson() {
        return person;
    }

    public void setPerson(List<Person> person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "SportsRegistrationSystem{" +
                "id=" + id +
                ", schedule=" + schedule +
                ", person=" + person +
                '}';
    }
}
