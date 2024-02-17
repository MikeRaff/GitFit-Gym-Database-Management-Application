/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

// line 2 "model.ump"
// line 101 "model.ump"
@MappedSuperclass
public abstract class GymUser
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GymUser Attributes
  private String email;
  private String password;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  //GymUser Associations
  @ManyToOne
  private Person person;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GymUser(String aEmail, String aPassword, Person aPerson)
  {
    email = aEmail;
    password = aPassword;
    
    if (!setPerson(aPerson))
    {
      throw new RuntimeException("Unable to create GymUser due to aPerson. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
  public Person getPerson()
  {
    return person;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setPerson(Person aNewPerson)
  {
    boolean wasSet = false;
    if (aNewPerson != null)
    {
      person = aNewPerson;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    person = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "," +
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "person = "+(getPerson()!=null?Integer.toHexString(System.identityHashCode(getPerson())):"null");
  }
}