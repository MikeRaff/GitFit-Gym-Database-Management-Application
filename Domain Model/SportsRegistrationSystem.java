/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import java.util.*;

// line 117 "model.ump"
// line 207 "model.ump"
public class SportsRegistrationSystem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SportsRegistrationSystem Associations
  private List<Schedule> schedule;
  private List<Person> persons;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SportsRegistrationSystem()
  {
    schedule = new ArrayList<Schedule>();
    persons = new ArrayList<Person>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Schedule getSchedule(int index)
  {
    Schedule aSchedule = schedule.get(index);
    return aSchedule;
  }

  public List<Schedule> getSchedule()
  {
    List<Schedule> newSchedule = Collections.unmodifiableList(schedule);
    return newSchedule;
  }

  public int numberOfSchedule()
  {
    int number = schedule.size();
    return number;
  }

  public boolean hasSchedule()
  {
    boolean has = schedule.size() > 0;
    return has;
  }

  public int indexOfSchedule(Schedule aSchedule)
  {
    int index = schedule.indexOf(aSchedule);
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
  public static int minimumNumberOfSchedule()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Schedule addSchedule(int aOpenTime, int aCloseTime)
  {
    return new Schedule(aOpenTime, aCloseTime, this);
  }

  public boolean addSchedule(Schedule aSchedule)
  {
    boolean wasAdded = false;
    if (schedule.contains(aSchedule)) { return false; }
    SportsRegistrationSystem existingSportsRegistrationSystem = aSchedule.getSportsRegistrationSystem();
    boolean isNewSportsRegistrationSystem = existingSportsRegistrationSystem != null && !this.equals(existingSportsRegistrationSystem);
    if (isNewSportsRegistrationSystem)
    {
      aSchedule.setSportsRegistrationSystem(this);
    }
    else
    {
      schedule.add(aSchedule);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSchedule(Schedule aSchedule)
  {
    boolean wasRemoved = false;
    //Unable to remove aSchedule, as it must always have a sportsRegistrationSystem
    if (!this.equals(aSchedule.getSportsRegistrationSystem()))
    {
      schedule.remove(aSchedule);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addScheduleAt(Schedule aSchedule, int index)
  {  
    boolean wasAdded = false;
    if(addSchedule(aSchedule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSchedule()) { index = numberOfSchedule() - 1; }
      schedule.remove(aSchedule);
      schedule.add(index, aSchedule);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveScheduleAt(Schedule aSchedule, int index)
  {
    boolean wasAdded = false;
    if(schedule.contains(aSchedule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSchedule()) { index = numberOfSchedule() - 1; }
      schedule.remove(aSchedule);
      schedule.add(index, aSchedule);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addScheduleAt(aSchedule, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPersons()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Person addPerson(String aName)
  {
    return new Person(aName, this);
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
    while (schedule.size() > 0)
    {
      Schedule aSchedule = schedule.get(schedule.size() - 1);
      aSchedule.delete();
      schedule.remove(aSchedule);
    }
    
    while (persons.size() > 0)
    {
      Person aPerson = persons.get(persons.size() - 1);
      aPerson.delete();
      persons.remove(aPerson);
    }
    
  }

}