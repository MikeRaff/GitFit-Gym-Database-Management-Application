/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/
package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.Entity;

// line 28 "model.ump"
// line 117 "model.ump"
@Entity
public class Owner extends GymUser
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  // Hibernate needs a default constructor, but it doesn't need to be public
  @SuppressWarnings("unused")
  public Owner()
  {
    super();
  }

  public Owner(String aEmail, String aPassword, Person aPerson)
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