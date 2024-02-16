/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/
package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.Entity;

// line 12 "model.ump"
// line 106 "model.ump"
@Entity
public class Customer extends GymUser
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private int CreditCardNumber;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(String aEmail, String aPassword, int aId, int aCreditCardNumber)
  {
    super(aEmail, aPassword, aId);
    CreditCardNumber = aCreditCardNumber;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCreditCardNumber(int aCreditCardNumber)
  {
    boolean wasSet = false;
    CreditCardNumber = aCreditCardNumber;
    wasSet = true;
    return wasSet;
  }

  public int getCreditCardNumber()
  {
    return CreditCardNumber;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "CreditCardNumber" + ":" + getCreditCardNumber()+ "]";
  }
}