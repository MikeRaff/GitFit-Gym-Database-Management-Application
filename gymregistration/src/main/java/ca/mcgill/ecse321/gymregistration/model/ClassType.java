package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ClassType {
    @Id
    private int id;
    private String name;

    public ClassType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ClassType() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClassType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
