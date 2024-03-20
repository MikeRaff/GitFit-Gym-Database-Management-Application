package ca.mcgill.ecse321.gymregistration.dto;

import org.checkerframework.checker.units.qual.Time;
import ca.mcgill.ecse321.gymregistration.model.ClassType;
import ca.mcgill.ecse321.gymregistration.model.Session;
import java.sql.Date;
public class SessionDto {

    private int id;
  private Date date;
  private Time startTime;
  private Time endTime;
  private String description;
  private String name;
  
  private String location;
  private ClassType classType;
  public SessionDto() {
  }

  public SessionDto(Date date, Time startTime, Time endTime, String description, String name, String location, ClassType classType) {
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
    this.description = description;
    this.name = name;
    this.location = location;
    this.classType = classType;
  }
  //------------------------
  // INTERFACE
  //------------------------


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

  public ClassType getClassType() {
    return classType;
  }

  public void setClassType(ClassType classType) {
    this.classType = classType;
  }

  @Override
  public String toString() {
    return "Session{" +
            "id=" + id +
            ", date=" + date +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            ", description='" + description + '\'' +
            ", name='" + name + '\'' +
            ", location='" + location + '\'' +
            ", classType=" + classType +
            '}';
  }
}

