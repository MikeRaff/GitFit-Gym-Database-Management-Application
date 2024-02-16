/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import java.util.*;

// line 31 "model.ump"
// line 148 "model.ump"
public class Owner extends Staff
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Owner Attributes
  private int ownerId;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(String aEmail, String aPassword, Schedule aSchedule, int aOwnerId)
  {
    super(aEmail, aPassword, aSchedule);
    ownerId = aOwnerId;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOwnerId(int aOwnerId)
  {
    boolean wasSet = false;
    ownerId = aOwnerId;
    wasSet = true;
    return wasSet;
  }

  public int getOwnerId()
  {
    return ownerId;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "ownerId" + ":" + getOwnerId()+ "]";
  }
}