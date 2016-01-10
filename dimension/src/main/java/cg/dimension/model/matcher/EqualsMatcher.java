package cg.dimension.model.matcher;

public class EqualsMatcher<EV, V> implements TypicalMatcherSpec<EqualsMatcher<EV, V>, EV, V>
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

  @Override
  public EqualsMatcher<EV, V> cloneMe()
  {
    return new EqualsMatcher<EV, V>(expectedValue);
  }

  @Override
  public void injectExpectValue(EV value)
  {
    expectedValue = value;
  }

  
}
