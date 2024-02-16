package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.sql.Time;
import java.sql.Date;

@Entity
public class Session {
    @Id
    private int sessionId;
    private Date date;
    private Time startTime;
    private Time endTime;
    private String description;
    private String name;
    private String location;
    private Time openingTime;
    private Time closingTIme;
    @ManyToOne
    private Schedule schedule;
    @ManyToOne
    private ClassType classType;

    public Session(int sessionId, Date date, Time startTime, Time endTime, String description, String name, String location, Time openingTime, Time closingTIme, Schedule schedule, ClassType classType) {
        this.sessionId = sessionId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTIme = closingTIme;
        this.schedule = schedule;
        this.classType = classType;
    }

    public Session() {
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Time getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Time openingTime) {
        this.openingTime = openingTime;
    }

    public Time getClosingTIme() {
        return closingTIme;
    }

    public void setClosingTIme(Time closingTIme) {
        this.closingTIme = closingTIme;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionId=" + sessionId +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", openingTime=" + openingTime +
                ", closingTIme=" + closingTIme +
                ", schedule=" + schedule +
                ", classType=" + classType +
                '}';
    }
}
