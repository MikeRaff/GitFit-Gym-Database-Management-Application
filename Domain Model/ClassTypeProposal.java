/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/



// line 100 "model.ump"
// line 193 "model.ump"
public class ClassTypeProposal
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ClassTypeProposal Attributes
  private int id;
  private String name;

  //ClassTypeProposal Associations
  private Instructor instructor;
  private Owner owner;
  private ClassType classType;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ClassTypeProposal(int aId, String aName, Instructor aInstructor, Owner aOwner, ClassType aClassType)
  {
    id = aId;
    name = aName;
    if (!setInstructor(aInstructor))
    {
      throw new RuntimeException("Unable to create ClassTypeProposal due to aInstructor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setOwner(aOwner))
    {
      throw new RuntimeException("Unable to create ClassTypeProposal due to aOwner. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setClassType(aClassType))
    {
      throw new RuntimeException("Unable to create ClassTypeProposal due to aClassType. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template association_GetOne */
  public Instructor getInstructor()
  {
    return instructor;
  }
  /* Code from template association_GetOne */
  public Owner getOwner()
  {
    return owner;
  }
  /* Code from template association_GetOne */
  public ClassType getClassType()
  {
    return classType;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setInstructor(Instructor aNewInstructor)
  {
    boolean wasSet = false;
    if (aNewInstructor != null)
    {
      instructor = aNewInstructor;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setOwner(Owner aNewOwner)
  {
    boolean wasSet = false;
    if (aNewOwner != null)
    {
      owner = aNewOwner;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setClassType(ClassType aNewClassType)
  {
    boolean wasSet = false;
    if (aNewClassType != null)
    {
      classType = aNewClassType;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    instructor = null;
    owner = null;
    classType = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "instructor = "+(getInstructor()!=null?Integer.toHexString(System.identityHashCode(getInstructor())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "owner = "+(getOwner()!=null?Integer.toHexString(System.identityHashCode(getOwner())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "classType = "+(getClassType()!=null?Integer.toHexString(System.identityHashCode(getClassType())):"null");
  }
}