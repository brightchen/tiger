package cg.dimension.model.matcher;

public class EqualsMatcher<EV> implements Matcher<Object>
{
  protected EV expectedValue;
  
  public EqualsMatcher(){}
  
  public EqualsMatcher(EV expectedValue)
  {
    setExpectedValue(expectedValue);
  }
  
  @Override
  public boolean matches(Object value)
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
