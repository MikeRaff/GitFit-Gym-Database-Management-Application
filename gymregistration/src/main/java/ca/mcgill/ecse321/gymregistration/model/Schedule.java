package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.*;

import java.sql.Time;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int scheduleId;
    private Time openTime;
    private Time closeTime;
    @ManyToOne
    private SportsRegistrationSystem sportsRegistrationSystem;

    public Schedule(int scheduleId, Time openTime, Time closeTime, SportsRegistrationSystem sportsRegistrationSystem) {
        this.scheduleId = scheduleId;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.sportsRegistrationSystem = sportsRegistrationSystem;
    }

    public Schedule() {
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Time getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Time openTime) {
        this.openTime = openTime;
    }

    public Time getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Time closeTime) {
        this.closeTime = closeTime;
    }

    public SportsRegistrationSystem getSportsRegistrationSystem() {
        return sportsRegistrationSystem;
    }

    public void setSportsRegistrationSystem(SportsRegistrationSystem sportsRegistrationSystem) {
        this.sportsRegistrationSystem = sportsRegistrationSystem;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId=" + scheduleId +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                ", sportsRegistrationSystem=" + sportsRegistrationSystem +
                '}';
    }
}
