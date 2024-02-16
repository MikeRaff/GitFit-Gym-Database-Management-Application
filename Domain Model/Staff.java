/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import java.util.*;

/**
 * Is there a need for staff?
 */
// line 19 "model.ump"
// line 137 "model.ump"
public abstract class Staff extends GymUser
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Staff Associations
  private Schedule schedule;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Staff(String aEmail, String aPassword, Schedule aSchedule)
  {
    super(aEmail, aPassword);
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