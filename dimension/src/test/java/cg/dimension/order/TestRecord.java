package cg.dimension.order;

import java.util.Calendar;

import cg.dimension.model.property.PropertyValue;
import cg.dimension.model.property.PropertyValueProvider;

public class TestRecord implements PropertyValueProvider
{
  public int id;
  public String name;
  public int age;
  public double salary;
  public long createdTime;
  
  public TestRecord(int id)
  {
    this(id, "name"+id, id%100, id%1000);
  }
  
  public TestRecord(int id, String name, int age, float salary)
  {
    this.id = id;
    this.name = name;
    this.age = age;
    this.salary = salary;
    createdTime = Calendar.getInstance().getTimeInMillis();
  }
  
  @Override
  public void get(String propName, PropertyValue<?> value)
  {
    if(propName.equals("id"))
    {
      value = new PropertyValue<Integer>(Integer.class, id);
      return;
    }
    if(propName.equals("name"))
    {
      value = new PropertyValue<String>(String.class, name);
      return;
    }
    if(propName.equals("age"))
    {
      value = new PropertyValue<Integer>(Integer.class, age);
      return;
    }
    if(propName.equals("salary"))
    {
      value = new PropertyValue<Double>(Double.class, salary);
      return;
    }
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public int getAge()
  {
    return age;
  }

  public void setAge(int age)
  {
    this.age = age;
  }

  public double getSalary()
  {
    return salary;
  }

  public void setSalary(double salary)
  {
    this.salary = salary;
  }

  public long getCreatedTime()
  {
    return createdTime;
  }

  public void setCreatedTime(long createdTime)
  {
    this.createdTime = createdTime;
  }
  
  
}
