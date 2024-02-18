package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Owner extends Staff{
    @Id

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(String aEmail, String aPassword, int aID, Schedule aSchedule)
  {
    super(aEmail, aPassword, aID, aSchedule);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}