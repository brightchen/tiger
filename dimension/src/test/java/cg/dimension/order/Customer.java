package cg.dimension.order;

public class Customer {
  public String id;
  public String firstName;
  public String lastName;
  public Sex sex;
  public int age;
  public String address;
  public String city;
  public String stateOrProvince;
  public String country;
  public String zip;
  public String phone;
  
  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }
  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Customer other = (Customer)obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }
  
  
}
