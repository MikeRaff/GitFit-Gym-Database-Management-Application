package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ClassTypeProposal {
    @Id
    private int id;
    private String name;
    @ManyToOne
    private Instructor instructor;
    @ManyToOne
    private Owner owner;
    @ManyToOne
    private ClassType classType;

    public ClassTypeProposal() {
    }

    public ClassTypeProposal(int id, String name, Instructor instructor, Owner owner, ClassType classType) {
        this.id = id;
        this.name = name;
        this.instructor = instructor;
        this.owner = owner;
        this.classType = classType;
    }

    public void delete()
    {
        instructor = null;
        owner = null;
        classType = null;
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

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    @Override
    public String toString() {
        return "ClassTypeProposal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", instructor=" + instructor +
                ", owner=" + owner +
                ", classType=" + classType +
                '}';
    }
}
