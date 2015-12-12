package cg.dimension.model.matcher;

public class EqualsMatcher<EV, V> implements Matcher<V>
{
  protected EV expectedValue;
  
  public EqualsMatcher(){}
  
  public EqualsMatcher(EV expectedValue)
  {
    setExpectedValue(expectedValue);
  }
  
  @Override
  public boolean matches(V value)
  {
    return expectedValue.equals(value);
  }

  public EV getExpectedValue()
  {
    return expectedValue;
  }

  public void setExpectedValue(EV expectedValue)
  {
    this.expectedValue = expectedValue;
  }

  
}
