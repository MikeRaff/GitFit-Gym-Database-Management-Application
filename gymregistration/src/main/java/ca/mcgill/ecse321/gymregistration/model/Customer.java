package ca.mcgill.ecse321.gymregistration.model;

import jakarta.persistence.Entity;

@Entity
public class Customer extends GymUser {

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private int creditCardNumber;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  // Hibernate needs a default constructor
  public Customer() {
    super();
  }

  public Customer(String aEmail, String aPassword, Person aPerson, int aCreditCardNumber) {
    super(aEmail, aPassword, aPerson);
    creditCardNumber = aCreditCardNumber;
  }

  //------------------------
  // INTERFACE
  //------------------------


  public int getCreditCardNumber() {
    return creditCardNumber;
  }

  public void setCreditCardNumber(int creditCardNumber) {
    this.creditCardNumber = creditCardNumber;

  }

  @Override
  public String toString() {
    return "Customer{" +
            "creditCardNumber=" + creditCardNumber +
            '}';
  }
}