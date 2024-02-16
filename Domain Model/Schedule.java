/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/



// line 40 "model.ump"
// line 155 "model.ump"
public class Schedule
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Schedule Attributes
  private int openTime;
  private int closeTime;

  //Schedule Associations
  private SportsRegistrationSystem sportsRegistrationSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Schedule(int aOpenTime, int aCloseTime, SportsRegistrationSystem aSportsRegistrationSystem)
  {
    openTime = aOpenTime;
    closeTime = aCloseTime;
    boolean didAddSportsRegistrationSystem = setSportsRegistrationSystem(aSportsRegistrationSystem);
    if (!didAddSportsRegistrationSystem)
    {
      throw new RuntimeException("Unable to create schedule due to sportsRegistrationSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOpenTime(int aOpenTime)
  {
    boolean wasSet = false;
    openTime = aOpenTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setCloseTime(int aCloseTime)
  {
    boolean wasSet = false;
    closeTime = aCloseTime;
    wasSet = true;
    return wasSet;
  }

  public int getOpenTime()
  {
    return openTime;
  }

  public int getCloseTime()
  {
    return closeTime;
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
      existingSportsRegistrationSystem.removeSchedule(this);
    }
    sportsRegistrationSystem.addSchedule(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    SportsRegistrationSystem placeholderSportsRegistrationSystem = sportsRegistrationSystem;
    this.sportsRegistrationSystem = null;
    if(placeholderSportsRegistrationSystem != null)
    {
      placeholderSportsRegistrationSystem.removeSchedule(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "openTime" + ":" + getOpenTime()+ "," +
            "closeTime" + ":" + getCloseTime()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "sportsRegistrationSystem = "+(getSportsRegistrationSystem()!=null?Integer.toHexString(System.identityHashCode(getSportsRegistrationSystem())):"null");
  }
}