package cg.dimension.group;

import cg.dimension.model.CloneableBean;
import cg.dimension.model.matcher.Matcher;

public abstract class AbstractBeanMatcherDynamicGroup<T extends CloneableBean<T>, B, K> implements CloneableGroup<T, B, K>
{
  protected Matcher<B, K> matcher;
  
  public AbstractBeanMatcherDynamicGroup<T, B, K> withMatcher(Matcher<B, K> matcher)
  {
    this.setMatcher(matcher);
    return this;
  }
  
  @Override
  public boolean put(B bean)
  {
    if(matcher == null)
    {
      createMatcher(bean);
    }

    if(!matcher.matches(bean))
      return false;

    handleMatchedBean(bean);
    return true;
  }

  protected abstract void handleMatchedBean(B bean);
  
  protected abstract void createMatcher(B bean);

  public Matcher<B, K> getMatcher()
  {
    return matcher;
  }

  public void setMatcher(Matcher<B, K> matcher)
  {
    this.matcher = matcher;
  }
}
