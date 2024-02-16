package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class Staff extends GymUser{
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ManyToOne
    private Schedule schedule;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Staff Associations
  private Schedule schedule;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Staff(String aEmail, String aPassword, int aID, Schedule aSchedule)
  {
    super(aEmail, aPassword, aID);
    if (!setSchedule(aSchedule))
    {
      throw new RuntimeException("Unable to create Staff due to aSchedule. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Schedule getSchedule()
  {
    return schedule;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setSchedule(Schedule aNewSchedule)
  {
    boolean wasSet = false;
    if (aNewSchedule != null)
    {
      schedule = aNewSchedule;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    schedule = null;
    super.delete();
  }

}