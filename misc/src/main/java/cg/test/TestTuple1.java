package cg.test;

import java.io.Serializable;
import java.util.Map;

import org.apache.hadoop.hbase.util.Bytes;

public class TestTuple1 implements Serializable
{
  private static final long serialVersionUID = 2153417121590225192L;

  public static String[] getFieldName()
  {
    return new String[]{"rowId","name","age","address","desc"};
  }
  public static String getRowExpression()
  {
    return "row";
  }
  
  public static TestTuple1 from( Map<String,byte[]> map )
  {
    TestTuple1 testEntity = new TestTuple1();
    for( Map.Entry<String, byte[]> entry : map.entrySet() )
    {
      testEntity.setValue(entry.getKey(), entry.getValue() );
    }
    return testEntity;
  }
  
  private Long rowId = null;
  private String name;
  private int age;
  private String address;
  private String desc;

  public static TestTuple1 createRandomly(long rowId)
  {
    TestTuple1 entity = new TestTuple1();
    entity.rowId = rowId;
    entity.name = RandomStringGenerator.getString();
    entity.age = (int)(Math.random()*100);
    entity.address = RandomStringGenerator.getString();
    entity.desc = RandomStringGenerator.getString();
    return entity;
  }
  
  public TestTuple1(){}
  
  public TestTuple1(long rowId)
  {
    this(rowId, "name" + rowId, (int) rowId, "address" + rowId, "desc" + rowId);
  }

  public TestTuple1(long rowId, String name, int age, String address, String desc)
  {
    this.rowId = rowId;
    setName(name);
    setAge(age);
    setAddress(address);
    setDesc(desc);
  }
  
  public void setValue( String fieldName, byte[] value )
  {
    if( "row".equalsIgnoreCase(fieldName) )
    {
      setRow( Bytes.toString(value) );
      return;
    }
    if( "name".equalsIgnoreCase(fieldName))
    {
      setName( Bytes.toString(value));
      return;
    }
    if( "address".equalsIgnoreCase(fieldName))
    {
      setAddress( Bytes.toString(value));
      return;
    }
    if( "age".equalsIgnoreCase(fieldName))
    {
      setAge( Bytes.toInt(value) );
      return;
    }
  }

  public String getRow()
  {
    return String.valueOf(rowId);
  }
  public void setRow( String row )
  {
    setRowId( Long.valueOf(row) );
  }
  public void setRowId( Long rowId )
  {
    this.rowId = rowId;
  }
  public Long getRowId()
  {
    return rowId;
  }
  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Integer getAge()
  {
    return age;
  }

  public void setAge( Integer age)
  {
    this.age = age;
  }

  public String getAddress()
  {
    return address;
  }

  public void setAddress(String address)
  {
    this.address = address;
  }
  
  
  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  @Override
  public boolean equals( Object obj )
  {
    if( obj == null )
      return false;
    if( !( obj instanceof TestTuple1 ) )
      return false;
    
    return completeEquals( (TestTuple1)obj );
  }

  public boolean outputFieldsEquals( TestTuple1 other )
  {
    if( other == null )
      return false;
    if( !fieldEquals( getName(), other.getName() ) )
      return false;
    if( !fieldEquals( getAge(), other.getAge() ) )
      return false;
    if( !fieldEquals( getAddress(), other.getAddress() ) )
      return false;
    return true;
  }
  
  public boolean completeEquals( TestTuple1 other )
  {
    if( other == null )
      return false;
    if( !outputFieldsEquals( other ) )
      return false;
    if( !fieldEquals( getRow(), other.getRow() ) )
      return false;
    return true;
  }
  
  public <T> boolean fieldEquals( T v1, T v2 )
  {
    if( v1 == null && v2 == null )
      return true;
    if( v1 == null || v2 == null )
      return false;
    return v1.equals( v2 );
  }
  
  @Override
  public String toString()
  {
    return String.format( "id={%d}; name={%s}; age={%d}; address={%s}", rowId, name, age, address);
  }
}