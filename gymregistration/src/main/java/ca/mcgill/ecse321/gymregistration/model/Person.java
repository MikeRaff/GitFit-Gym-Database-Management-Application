/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/
package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

// line 85 "model.ump"
// line 150 "model.ump"
@Entity
public class Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  @Id
  @GeneratedValue
  private Integer id;
  private String name;

  //Person Associations

  //------------------------
  // CONSTRUCTOR
  //------------------------

  // Hibernate needs a default constructor, but it doesn't need to be public
  @SuppressWarnings("unused")
  private Person() {
  }

  public Person(String aName)
  {
    name = aName;
    
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template association_GetOne */
  
  /* Code from template association_SetOneToMany */
  
  public void delete()
  {
   
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator");
  }

public Integer getId() {
    return id;
}
}