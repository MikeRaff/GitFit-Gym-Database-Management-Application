package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.sql.Date;

@Entity
public class CustomerRegistration {
    @Id
    private int id;
    private Date date;
    @ManyToOne
    Session session;
    @ManyToOne
    Customer customer;

    public CustomerRegistration() {
    }

    public CustomerRegistration(int id, Date date, Session session, Customer customer) {
        this.id = id;
        this.date = date;
        this.session = session;
        this.customer = customer;
    }
    public void delete()
    {
        session = null;
        customer = null;
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

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "CustomerRegistration{" +
                "id=" + id +
                ", date=" + date +
                ", session=" + session +
                ", customer=" + customer +
                '}';
    }
}
