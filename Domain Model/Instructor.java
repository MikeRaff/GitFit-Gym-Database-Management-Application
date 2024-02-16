/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import java.util.*;

// line 25 "model.ump"
// line 142 "model.ump"
public class Instructor extends Staff
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Instructor Attributes
  private int intructorId;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Instructor(String aEmail, String aPassword, Schedule aSchedule, int aIntructorId)
  {
    super(aEmail, aPassword, aSchedule);
    intructorId = aIntructorId;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIntructorId(int aIntructorId)
  {
    boolean wasSet = false;
    intructorId = aIntructorId;
    wasSet = true;
    return wasSet;
  }

  public int getIntructorId()
  {
    return intructorId;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "intructorId" + ":" + getIntructorId()+ "]";
  }
}