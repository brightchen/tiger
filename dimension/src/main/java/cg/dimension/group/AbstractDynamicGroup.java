package cg.dimension.group;

import cg.dimension.model.CloneableBean;
import cg.dimension.model.matcher.Matcher;
import cg.dimension.model.property.BeanPropertyValueGenerator;

/**
 * The matcher is probably depended on the value.
 * Think about the case group by zip, when a new zip come, a new group should dynamically created.
 * and the matcher for this group should depend on the zip.
 * 
 * @author bright
 *
 * @param <T>
 * @param <B>
 * @param <MV>
 */
public abstract class AbstractDynamicGroup<T extends CloneableBean<T>, B, MV> implements CloneableGroup<T, B>
{
  protected BeanPropertyValueGenerator<B, MV> matchPropertyValueGenerator;
  protected Matcher<MV> matcher;
  
  protected AbstractDynamicGroup(){}
  
  public AbstractDynamicGroup(BeanPropertyValueGenerator<B, MV> matchPropertyValueGenerator)
  {
    this.setMatchPropertyValueGenerator(matchPropertyValueGenerator);
  }

  @Override
  public boolean put(B bean)
  {
    MV value = matchPropertyValueGenerator.getValue(bean);
    if(matcher == null)
    {
      createMatcher(bean, value);
    }

    if(!matcher.matches(value))
      return false;
    handleMatchedBean(bean, value);
    return true;
  }

  protected abstract void createMatcher(B bean, MV value);
  
  protected abstract void handleMatchedBean(B bean, MV value);
  
  
  public BeanPropertyValueGenerator<B, MV> getMatchPropertyValueGenerator()
  {
    return matchPropertyValueGenerator;
  }

  public void setMatchPropertyValueGenerator(BeanPropertyValueGenerator<B, MV> matchPropertyValueGenerator)
  {
    this.matchPropertyValueGenerator = matchPropertyValueGenerator;
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