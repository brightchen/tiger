package cg.dimension.model.group;

import cg.dimension.model.matcher.Matcher;
import cg.dimension.model.property.BeanPropertyValueGenerator;

public abstract class AbstractPresetGroup<B, MV> implements Group<B>
{
  protected BeanPropertyValueGenerator<B, MV> propertyValueGenerator;
  protected Matcher<MV> matcher;
  
  public AbstractPresetGroup(){}
  
  public AbstractPresetGroup(BeanPropertyValueGenerator<B, MV> propertyValueGenerator, Matcher<MV> matcher)
  {
    this.setPropertyValueGenerator(propertyValueGenerator);
    this.setMatcher(matcher);
  }
  
  @Override
  public boolean put(B bean)
  {
    MV value = propertyValueGenerator.getValue(bean);
    if(!matcher.matches(value))
      return false;
    handleMatchedBean(bean, value);
    return true;
  }

  protected abstract void handleMatchedBean(B bean, MV value);
  
  public BeanPropertyValueGenerator<B, MV> getPropertyValueGenerator()
  {
    return propertyValueGenerator;
  }

  public void setPropertyValueGenerator(BeanPropertyValueGenerator<B, MV> propertyValueGenerator)
  {
    this.propertyValueGenerator = propertyValueGenerator;
  }

  public Matcher<MV> getMatcher()
  {
    return matcher;
  }

  public void setMatcher(Matcher<MV> matcher)
  {
    this.matcher = matcher;
  }
  
  
}
