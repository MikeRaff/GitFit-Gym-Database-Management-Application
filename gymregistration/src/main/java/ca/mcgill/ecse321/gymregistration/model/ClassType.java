/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/
package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

// line 73 "model.ump"
// line 143 "model.ump"
@Entity
public class ClassType
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ClassType Attributes
  @Id
  @GeneratedValue
  private int id;
  private String name;
  private boolean isApproved;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ClassType(String aName, boolean aIsApproved)
  {
    name = aName;
    isApproved = aIsApproved;
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

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsApproved(boolean aIsApproved)
  {
    boolean wasSet = false;
    isApproved = aIsApproved;
    wasSet = true;
    return wasSet;
  }

  /**
   * Necessary?? can use name for PK?
   */
  public int getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public boolean getIsApproved()
  {
    return isApproved;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsApproved()
  {
    return isApproved;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "name" + ":" + getName()+ "," +
            "isApproved" + ":" + getIsApproved()+ "]";
  }
}