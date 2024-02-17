package ca.mcgill.ecse321.gymregistration.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

import jakarta.persistence.Entity;

// line 12 "model.ump"
// line 107 "model.ump"
@Entity
public class Customer extends GymUser
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private int creditCardNumber;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(String aEmail, String aPassword, Person aPerson, int aCreditCardNumber)
  {
    super(aEmail, aPassword, aPerson);
    creditCardNumber = aCreditCardNumber;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCreditCardNumber(int aCreditCardNumber)
  {
    boolean wasSet = false;
    creditCardNumber = aCreditCardNumber;
    wasSet = true;
    return wasSet;
  }

  public int getCreditCardNumber()
  {
    return creditCardNumber;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "creditCardNumber" + ":" + getCreditCardNumber()+ "]";
  }
}