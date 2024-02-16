package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.sql.Date;

@Entity
public class InstructorRegistration {
    @Id
    private int id;
    private Date date;
    @ManyToOne
    private Instructor instructor;
    @ManyToOne
    private Session session;

    public InstructorRegistration(int id, Date date, Instructor instructor, Session session) {
        this.id = id;
        this.date = date;
        this.instructor = instructor;
        this.session = session;
    }

    public InstructorRegistration() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void delete()
    {
        instructor = null;
        session = null;
    }

    @Override
    public String toString() {
        return "InstructorRegistration{" +
                "id=" + id +
                ", date=" + date +
                ", instructor=" + instructor +
                ", session=" + session +
                '}';
    }
}
