/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.Entity;

/**
 * Is there a need for staff?
 */
// line 22 "model.ump"
// line 112 "model.ump"
@Entity
public class Instructor extends GymUser
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Instructor(String aEmail, String aPassword, Person aPerson)
  {
    super(aEmail, aPassword, aPerson);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}