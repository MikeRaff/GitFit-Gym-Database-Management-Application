package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Owner extends Staff{
    @Id
    private int ownerId;

    public Owner(String aEmail, String aPassword, Schedule schedule, int ownerId) {
        super(aEmail, aPassword, schedule);
        this.ownerId = ownerId;
    }

    public Owner() {
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
    public void delete()
    {
        super.delete();
    }

    @Override
    public String toString() {
        return "Owner{" +
                "ownerId=" + ownerId +
                '}';
    }
}
