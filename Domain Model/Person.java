/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/



// line 111 "model.ump"
// line 201 "model.ump"
public class Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  private String name;

  //Person Associations
  private SportsRegistrationSystem sportsRegistrationSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Person(String aName, SportsRegistrationSystem aSportsRegistrationSystem)
  {
    name = aName;
    boolean didAddSportsRegistrationSystem = setSportsRegistrationSystem(aSportsRegistrationSystem);
    if (!didAddSportsRegistrationSystem)
    {
      throw new RuntimeException("Unable to create person due to sportsRegistrationSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public String getName()
  {
    return name;
  }
  /* Code from template association_GetOne */
  public SportsRegistrationSystem getSportsRegistrationSystem()
  {
    return sportsRegistrationSystem;
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

  public void delete()
  {
    SportsRegistrationSystem placeholderSportsRegistrationSystem = sportsRegistrationSystem;
    this.sportsRegistrationSystem = null;
    if(placeholderSportsRegistrationSystem != null)
    {
      placeholderSportsRegistrationSystem.removePerson(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "sportsRegistrationSystem = "+(getSportsRegistrationSystem()!=null?Integer.toHexString(System.identityHashCode(getSportsRegistrationSystem())):"null");
  }
}