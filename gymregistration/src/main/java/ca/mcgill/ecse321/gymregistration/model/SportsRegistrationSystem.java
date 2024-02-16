/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

package ca.mcgill.ecse321.gymregistration.model;

import java.sql.Time;
import java.util.*;
import java.sql.Date;

// line 91 "model.ump"
// line 156 "model.ump"
public class SportsRegistrationSystem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SportsRegistrationSystem Attributes
  private Time openingTime;
  private Time closingTime;

  //SportsRegistrationSystem Associations
  private List<Session> Session;
  private List<Person> persons;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SportsRegistrationSystem(Time aOpeningTime, Time aClosingTime)
  {
    openingTime = aOpeningTime;
    closingTime = aClosingTime;
    Session = new ArrayList<Session>();
    persons = new ArrayList<Person>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOpeningTime(Time aOpeningTime)
  {
    boolean wasSet = false;
    openingTime = aOpeningTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setClosingTime(Time aClosingTime)
  {
    boolean wasSet = false;
    closingTime = aClosingTime;
    wasSet = true;
    return wasSet;
  }

  public Time getOpeningTime()
  {
    return openingTime;
  }

  public Time getClosingTime()
  {
    return closingTime;
  }
  /* Code from template association_GetMany */
  public Session getSession(int index)
  {
    Session aSession = Session.get(index);
    return aSession;
  }

  public List<Session> getSession()
  {
    List<Session> newSession = Collections.unmodifiableList(Session);
    return newSession;
  }

  public int numberOfSession()
  {
    int number = Session.size();
    return number;
  }

  public boolean hasSession()
  {
    boolean has = Session.size() > 0;
    return has;
  }

  public int indexOfSession(Session aSession)
  {
    int index = Session.indexOf(aSession);
    return index;
  }
  /* Code from template association_GetMany */
  public Person getPerson(int index)
  {
    Person aPerson = persons.get(index);
    return aPerson;
  }

  public List<Person> getPersons()
  {
    List<Person> newPersons = Collections.unmodifiableList(persons);
    return newPersons;
  }

  public int numberOfPersons()
  {
    int number = persons.size();
    return number;
  }

  public boolean hasPersons()
  {
    boolean has = persons.size() > 0;
    return has;
  }

  public int indexOfPerson(Person aPerson)
  {
    int index = persons.indexOf(aPerson);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSession()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Session addSession(int aId, Date aDate, Time aStartTime, Time aEndTime, String aDescription, String aName, String aLocation, ClassType aClassType)
  {
    return new Session(aId, aDate, aStartTime, aEndTime, aDescription, aName, aLocation, this, aClassType);
  }

  public boolean addSession(Session aSession)
  {
    boolean wasAdded = false;
    if (Session.contains(aSession)) { return false; }
    SportsRegistrationSystem existingSportsRegistrationSystem = aSession.getSportsRegistrationSystem();
    boolean isNewSportsRegistrationSystem = existingSportsRegistrationSystem != null && !this.equals(existingSportsRegistrationSystem);
    if (isNewSportsRegistrationSystem)
    {
      aSession.setSportsRegistrationSystem(this);
    }
    else
    {
      Session.add(aSession);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSession(Session aSession)
  {
    boolean wasRemoved = false;
    //Unable to remove aSession, as it must always have a sportsRegistrationSystem
    if (!this.equals(aSession.getSportsRegistrationSystem()))
    {
      Session.remove(aSession);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSessionAt(Session aSession, int index)
  {  
    boolean wasAdded = false;
    if(addSession(aSession))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSession()) { index = numberOfSession() - 1; }
      Session.remove(aSession);
      Session.add(index, aSession);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSessionAt(Session aSession, int index)
  {
    boolean wasAdded = false;
    if(Session.contains(aSession))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSession()) { index = numberOfSession() - 1; }
      Session.remove(aSession);
      Session.add(index, aSession);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSessionAt(aSession, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPersons()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Person addPerson(String aName, GymUser... allGymUsers)
  {
    return new Person(aName, this, allGymUsers);
  }

  public boolean addPerson(Person aPerson)
  {
    boolean wasAdded = false;
    if (persons.contains(aPerson)) { return false; }
    SportsRegistrationSystem existingSportsRegistrationSystem = aPerson.getSportsRegistrationSystem();
    boolean isNewSportsRegistrationSystem = existingSportsRegistrationSystem != null && !this.equals(existingSportsRegistrationSystem);
    if (isNewSportsRegistrationSystem)
    {
      aPerson.setSportsRegistrationSystem(this);
    }
    else
    {
      persons.add(aPerson);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePerson(Person aPerson)
  {
    boolean wasRemoved = false;
    //Unable to remove aPerson, as it must always have a sportsRegistrationSystem
    if (!this.equals(aPerson.getSportsRegistrationSystem()))
    {
      persons.remove(aPerson);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPersonAt(Person aPerson, int index)
  {  
    boolean wasAdded = false;
    if(addPerson(aPerson))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPersons()) { index = numberOfPersons() - 1; }
      persons.remove(aPerson);
      persons.add(index, aPerson);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePersonAt(Person aPerson, int index)
  {
    boolean wasAdded = false;
    if(persons.contains(aPerson))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPersons()) { index = numberOfPersons() - 1; }
      persons.remove(aPerson);
      persons.add(index, aPerson);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPersonAt(aPerson, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (Session.size() > 0)
    {
      Session aSession = Session.get(Session.size() - 1);
      aSession.delete();
      Session.remove(aSession);
    }
    
    while (persons.size() > 0)
    {
      Person aPerson = persons.get(persons.size() - 1);
      aPerson.delete();
      persons.remove(aPerson);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "openingTime" + "=" + (getOpeningTime() != null ? !getOpeningTime().equals(this)  ? getOpeningTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "closingTime" + "=" + (getClosingTime() != null ? !getClosingTime().equals(this)  ? getClosingTime().toString().replaceAll("  ","    ") : "this" : "null");
  }
}