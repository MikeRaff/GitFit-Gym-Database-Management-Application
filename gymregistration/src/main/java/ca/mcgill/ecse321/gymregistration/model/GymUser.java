package ca.mcgill.ecse321.gymregistration.model;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;

import java.util.Collections;
import java.util.List;

@MappedSuperclass
public abstract class GymUser {
    private String email;
    private String password;
    @ManyToMany
    private List<Person> persons;

    public GymUser(String aEmail, String aPassword) {
    }

    public void delete()
    {
        persons.clear();
    }

    public GymUser() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getPerson(int index)
    {
        Person aPerson = persons.get(index);
        return aPerson;
    }

    public List<Person> getPersons()
    {
        List<Person> newPersons = Collections.unmodifiableList(persons);
        return newPersons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public int numberOfPersons()
    {
        int number = persons.size();
        return number;
    }

    public boolean hasPersons()
    {
        boolean has = persons.size() > 0;
        return has;
    }

    public int indexOfPerson(Person aPerson)
    {
        int index = persons.indexOf(aPerson);
        return index;
    }

    public boolean addPerson(Person aPerson)
    {
        boolean wasAdded = false;
        if (persons.contains(aPerson)) { return false; }
        persons.add(aPerson);
        wasAdded = true;
        return wasAdded;
    }

    public boolean removePerson(Person aPerson)
    {
        boolean wasRemoved = false;
        if (persons.contains(aPerson))
        {
            persons.remove(aPerson);
            wasRemoved = true;
        }
        return wasRemoved;
    }
    /* Code from template association_AddIndexControlFunctions */
    public boolean addPersonAt(Person aPerson, int index)
    {
        boolean wasAdded = false;
        if(addPerson(aPerson))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfPersons()) { index = numberOfPersons() - 1; }
            persons.remove(aPerson);
            persons.add(index, aPerson);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMovePersonAt(Person aPerson, int index)
    {
        boolean wasAdded = false;
        if(persons.contains(aPerson))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfPersons()) { index = numberOfPersons() - 1; }
            persons.remove(aPerson);
            persons.add(index, aPerson);
            wasAdded = true;
        }
        else
        {
            wasAdded = addPersonAt(aPerson, index);
        }
        return wasAdded;
    }

    @Override
    public String toString() {
        return "GymUser{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
