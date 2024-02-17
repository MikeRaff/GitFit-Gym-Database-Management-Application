/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/
package ca.mcgill.ecse321.gymregistration.model;


import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * start and end Times included as fields.
 * Use 1h duration for all?
 */
// line 49 "model.ump"
// line 130 "model.ump"
@Entity
public class Session
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Session Attributes
  @Id
  @GeneratedValue
  private Integer id;
  private Date date;
  private Time startTime;
  private Time endTime;
  private String description;
  private String name;
  private String location;

  //Session Associations
  
  @ManyToOne
  private ClassType classType;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Session(Date aDate, Time aStartTime, Time aEndTime, String aDescription, String aName, String aLocation, ClassType aClassType)
  {
    date = aDate;
    startTime = aStartTime;
    endTime = aEndTime;
    description = aDescription;
    name = aName;
    location = aLocation;
    
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
  
  /* Code from template association_GetOne */
  public ClassType getClassType()
  {
    return classType;
  }
  /* Code from template association_SetOneToMany */
  
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
    classType = null;
  }


  
}