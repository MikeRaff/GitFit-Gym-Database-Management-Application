/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import java.util.*;

// line 11 "model.ump"
// line 131 "model.ump"
public class Customer extends GymUser
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private int customerId;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(String aEmail, String aPassword, int aCustomerId)
  {
    super(aEmail, aPassword);
    customerId = aCustomerId;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCustomerId(int aCustomerId)
  {
    boolean wasSet = false;
    customerId = aCustomerId;
    wasSet = true;
    return wasSet;
  }

  public int getCustomerId()
  {
    return customerId;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "customerId" + ":" + getCustomerId()+ "]";
  }
}