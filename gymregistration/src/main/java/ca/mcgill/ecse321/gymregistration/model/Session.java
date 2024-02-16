/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/
package ca.mcgill.ecse321.gymregistration.model;


import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * start and end Times included as fields.
 * Use 1h duration for all?
 */
// line 49 "model.ump"
// line 129 "model.ump"
@Entity
public class Session
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Session Attributes
  @Id
  private int id;
  private Date date;
  private Time startTime;
  private Time endTime;
  private String description;
  private String name;
  private String location;

  //Session Associations
  @ManyToOne
  private SportsRegistrationSystem sportsRegistrationSystem;
  @ManyToOne
  private ClassType classType;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Session(int aId, Date aDate, Time aStartTime, Time aEndTime, String aDescription, String aName, String aLocation, SportsRegistrationSystem aSportsRegistrationSystem, ClassType aClassType)
  {
    id = aId;
    date = aDate;
    startTime = aStartTime;
    endTime = aEndTime;
    description = aDescription;
    name = aName;
    location = aLocation;
    boolean didAddSportsRegistrationSystem = setSportsRegistrationSystem(aSportsRegistrationSystem);
    if (!didAddSportsRegistrationSystem)
    {
      throw new RuntimeException("Unable to create Session due to sportsRegistrationSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setClassType(aClassType))
    {
      throw new RuntimeException("Unable to create Session due to aClassType. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setLocation(String aLocation)
  {
    boolean wasSet = false;
    location = aLocation;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public Date getDate()
  {
    return date;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  public String getDescription()
  {
    return description;
  }

  public String getName()
  {
    return name;
  }

  public String getLocation()
  {
    return location;
  }
  /* Code from template association_GetOne */
  public SportsRegistrationSystem getSportsRegistrationSystem()
  {
    return sportsRegistrationSystem;
  }
  /* Code from template association_GetOne */
  public ClassType getClassType()
  {
    return classType;
  }
  /* Code from template association_SetOneToMany */
  public boolean setSportsRegistrationSystem(SportsRegistrationSystem aSportsRegistrationSystem)
  {
    boolean wasSet = false;
    if (aSportsRegistrationSystem == null)
    {
      return wasSet;
    }

    SportsRegistrationSystem existingSportsRegistrationSystem = sportsRegistrationSystem;
    sportsRegistrationSystem = aSportsRegistrationSystem;
    if (existingSportsRegistrationSystem != null && !existingSportsRegistrationSystem.equals(aSportsRegistrationSystem))
    {
      existingSportsRegistrationSystem.removeSession(this);
    }
    sportsRegistrationSystem.addSession(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setClassType(ClassType aNewClassType)
  {
    boolean wasSet = false;
    if (aNewClassType != null)
    {
      classType = aNewClassType;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    SportsRegistrationSystem placeholderSportsRegistrationSystem = sportsRegistrationSystem;
    this.sportsRegistrationSystem = null;
    if(placeholderSportsRegistrationSystem != null)
    {
      placeholderSportsRegistrationSystem.removeSession(this);
    }
    classType = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "description" + ":" + getDescription()+ "," +
            "name" + ":" + getName()+ "," +
            "location" + ":" + getLocation()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "sportsRegistrationSystem = "+(getSportsRegistrationSystem()!=null?Integer.toHexString(System.identityHashCode(getSportsRegistrationSystem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "classType = "+(getClassType()!=null?Integer.toHexString(System.identityHashCode(getClassType())):"null");
  }
}