package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.*;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int personId;
    private String name;
    @ManyToOne
    SportsRegistrationSystem sportsRegistrationSystem;

    public Person(String name, SportsRegistrationSystem sportsRegistrationSystem) {
        this.name = name;
        this.sportsRegistrationSystem = sportsRegistrationSystem;
    }

    public Person() {
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SportsRegistrationSystem getSportsRegistrationSystem() {
        return sportsRegistrationSystem;
    }

    public void setSportsRegistrationSystem(SportsRegistrationSystem sportsRegistrationSystem) {
        this.sportsRegistrationSystem = sportsRegistrationSystem;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", name='" + name + '\'' +
                ", sportsRegistrationSystem=" + sportsRegistrationSystem +
                '}';
    }
}
