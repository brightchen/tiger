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
public class PropertyCriteria<B, V>
{
  protected BeanPropertyValueGenerator<B, V> matchValueGenerator;
  protected Matcher<V> matcher;

  public PropertyCriteria(){}
  
  public PropertyCriteria(BeanPropertyValueGenerator<B, V> valueProvider, Matcher<V> matcher)
  {
    setMatchValueGenerator(valueProvider);
    setMatcher(matcher);
  }
  
  public boolean matches(B bean)
  {
    V value = matchValueGenerator.getValue(bean);
    return matcher.matches(value);
  }

  public BeanPropertyValueGenerator<B, V> getMatchValueGenerator()
  {
    return matchValueGenerator;
  }

  public void setMatchValueGenerator(BeanPropertyValueGenerator<B, V> matchValueGenerator)
  {
    this.matchValueGenerator = matchValueGenerator;
  }

  public Matcher<V> getMatcher()
  {
    return matcher;
  }

  public void setMatcher(Matcher<V> matcher)
  {
    this.matcher = matcher;
  }
  
  
}
