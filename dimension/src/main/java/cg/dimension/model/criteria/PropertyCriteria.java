package cg.dimension.model.criteria;

import cg.dimension.model.matcher.Matcher;
import cg.dimension.model.property.BeanPropertyValueGenerator;

/**
 * The expected value set from the client code.
 * the comparing value is get from the bean ( the property value )
 * the matcher set from the client code
 * 
 * 
 * @author bright
 *
 */
public class PropertyCriteria<EV, B>
{
  protected BeanPropertyValueGenerator<B> valueProvider;
  protected Matcher<Object> matcher;

  public PropertyCriteria(){}
  
  public PropertyCriteria(BeanPropertyValueGenerator<B> valueProvider, Matcher<Object> matcher)
  {
    setValueProvider(valueProvider);
    setMatcher(matcher);
  }
  
  public boolean matches(B bean)
  {
    Object value = valueProvider.getPropertyValue(bean);
    return matcher.matches(value);
  }

  public BeanPropertyValueGenerator<B> getValueProvider()
  {
    return valueProvider;
  }

  public void setValueProvider(BeanPropertyValueGenerator<B> valueProvider)
  {
    this.valueProvider = valueProvider;
  }

  public Matcher<Object> getMatcher()
  {
    return matcher;
  }

  public void setMatcher(Matcher<Object> matcher)
  {
    this.matcher = matcher;
  }
  
  
}
