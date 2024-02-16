package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Customer extends GymUser{
    @Id
    private int customerId;

    public Customer(String aEmail, String aPassword, int aCustomerId)
    {
        super(aEmail, aPassword);
        this.customerId = aCustomerId;
    }

    public Customer() {
        super();
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void delete()
    {
        super.delete();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                '}';
    }
}
