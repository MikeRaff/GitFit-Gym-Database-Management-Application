/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/
package ca.mcgill.ecse321.gymregistration.model;


import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

// line 84 "model.ump"
// line 149 "model.ump"
@Entity
public class Person
{
  @GeneratedValue(strategy = GenerationType.AUTO)
  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  
  private String name;
  @Id
  private Integer id;
  //Person Associations
  @ManyToOne
  private SportsRegistrationSystem sportsRegistrationSystem;
  @ManyToOne
  private List<GymUser> gymUsers;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Person(String aName, SportsRegistrationSystem aSportsRegistrationSystem, GymUser... allGymUsers)
  {
    name = aName;
    boolean didAddSportsRegistrationSystem = setSportsRegistrationSystem(aSportsRegistrationSystem);
    if (!didAddSportsRegistrationSystem)
    {
      throw new RuntimeException("Unable to create person due to sportsRegistrationSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    gymUsers = new ArrayList<GymUser>();
    boolean didAddGymUsers = setGymUsers(allGymUsers);
    if (!didAddGymUsers)
    {
      throw new RuntimeException("Unable to create Person, must have 1 to 3 gymUsers. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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

  public boolean setID(Integer anID)
  {
    boolean wasSet = false;
    id = anID;
    wasSet = true;
    return wasSet;
  }
  public Integer getID()
  {
    return id;
  }
  public String getName()
  {
    return name;
  }
  /* Code from template association_GetOne */
  public SportsRegistrationSystem getSportsRegistrationSystem()
  {
    return sportsRegistrationSystem;
  }
  /* Code from template association_GetMany */
  public GymUser getGymUser(int index)
  {
    GymUser aGymUser = gymUsers.get(index);
    return aGymUser;
  }

  public List<GymUser> getGymUsers()
  {
    List<GymUser> newGymUsers = Collections.unmodifiableList(gymUsers);
    return newGymUsers;
  }

  public int numberOfGymUsers()
  {
    int number = gymUsers.size();
    return number;
  }

  public boolean hasGymUsers()
  {
    boolean has = gymUsers.size() > 0;
    return has;
  }

  public int indexOfGymUser(GymUser aGymUser)
  {
    int index = gymUsers.indexOf(aGymUser);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setSportsRegistrationSystem(SportsRegistrationSystem aSportsRegistrationSystem)
  {
    boolean wasSet = false;
    if (aSportsRegistrationSystem == null)
    {
      return wasSet;
    }

    SportsRegistrationSystem existingSportsRegistrationSystem = sportsRegistrationSystem;
    sportsRegistrationSystem = aSportsRegistrationSystem;
    if (existingSportsRegistrationSystem != null && !existingSportsRegistrationSystem.equals(aSportsRegistrationSystem))
    {
      existingSportsRegistrationSystem.removePerson(this);
    }
    sportsRegistrationSystem.addPerson(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGymUsers()
  {
    return 1;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfGymUsers()
  {
    return 3;
  }
  /* Code from template association_AddUnidirectionalMN */
  public boolean addGymUser(GymUser aGymUser)
  {
    boolean wasAdded = false;
    if (gymUsers.contains(aGymUser)) { return false; }
    if (numberOfGymUsers() < maximumNumberOfGymUsers())
    {
      gymUsers.add(aGymUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean removeGymUser(GymUser aGymUser)
  {
    boolean wasRemoved = false;
    if (!gymUsers.contains(aGymUser))
    {
      return wasRemoved;
    }

    if (numberOfGymUsers() <= minimumNumberOfGymUsers())
    {
      return wasRemoved;
    }

    gymUsers.remove(aGymUser);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalMN */
  public boolean setGymUsers(GymUser... newGymUsers)
  {
    boolean wasSet = false;
    ArrayList<GymUser> verifiedGymUsers = new ArrayList<GymUser>();
    for (GymUser aGymUser : newGymUsers)
    {
      if (verifiedGymUsers.contains(aGymUser))
      {
        continue;
      }
      verifiedGymUsers.add(aGymUser);
    }

    if (verifiedGymUsers.size() != newGymUsers.length || verifiedGymUsers.size() < minimumNumberOfGymUsers() || verifiedGymUsers.size() > maximumNumberOfGymUsers())
    {
      return wasSet;
    }

    gymUsers.clear();
    gymUsers.addAll(verifiedGymUsers);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGymUserAt(GymUser aGymUser, int index)
  {  
    boolean wasAdded = false;
    if(addGymUser(aGymUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGymUsers()) { index = numberOfGymUsers() - 1; }
      gymUsers.remove(aGymUser);
      gymUsers.add(index, aGymUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGymUserAt(GymUser aGymUser, int index)
  {
    boolean wasAdded = false;
    if(gymUsers.contains(aGymUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGymUsers()) { index = numberOfGymUsers() - 1; }
      gymUsers.remove(aGymUser);
      gymUsers.add(index, aGymUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGymUserAt(aGymUser, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    SportsRegistrationSystem placeholderSportsRegistrationSystem = sportsRegistrationSystem;
    this.sportsRegistrationSystem = null;
    if(placeholderSportsRegistrationSystem != null)
    {
      placeholderSportsRegistrationSystem.removePerson(this);
    }
    gymUsers.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "sportsRegistrationSystem = "+(getSportsRegistrationSystem()!=null?Integer.toHexString(System.identityHashCode(getSportsRegistrationSystem())):"null");
  }
}