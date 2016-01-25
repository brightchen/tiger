package cg.dimension.group;

import cg.dimension.handler.BeanHandler;
import cg.dimension.model.CloneableBean;
import cg.dimension.model.matcher.Matcher;
import cg.dimension.model.matcher.TypicalValueMatcherSpec;

public class DefaultBeanMatcherDynamicGroup<M extends TypicalValueMatcherSpec<M, B, B, K>, B, AV extends Number, K> 
    extends AbstractBeanMatcherDynamicGroup<DefaultBeanMatcherDynamicGroup<M, B, AV, K>, B, K>
{
  protected TypicalValueMatcherSpec<M, B, B, K> matcherTemplate;
  protected BeanHandler<B> beanHandler;
  
  public DefaultBeanMatcherDynamicGroup<M, B, AV, K> withMatchTemplate(TypicalValueMatcherSpec<M, B, B, K> matcherTemplate)
  {
    this.matcherTemplate = matcherTemplate;
    return this;
  }
  
  public DefaultBeanMatcherDynamicGroup<M, B, AV, K> withBeanHandler(BeanHandler<B> beanHandler)
  {
    this.beanHandler = beanHandler;
    return this;
  }
  
  @Override
  protected void handleMatchedBean(B bean)
  {
    beanHandler.handleBean(bean);
  }


  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public DefaultBeanMatcherDynamicGroup<M, B, AV, K> cloneMe()
  {
    DefaultBeanMatcherDynamicGroup<M, B, AV, K> clone = new DefaultBeanMatcherDynamicGroup<>();
    if(beanHandler != null)
    {
      if(beanHandler instanceof CloneableBean)
        clone.withBeanHandler((BeanHandler)((CloneableBean)beanHandler).cloneMe());
      else
        clone.withBeanHandler(beanHandler);
    }
    
    if(matcher != null)
    {
      if(matcher instanceof CloneableBean)
        clone.withMatcher((Matcher)((CloneableBean)matcher).cloneMe());
      else
        clone.withMatcher(matcher);
    }
    
    clone.matcherTemplate = matcherTemplate;
    
    return clone;
  }

  @Override
  protected void createMatcher(B bean)
  {
    M matcher = matcherTemplate.cloneMe();
    
    //the match value maybe different from expected value.
    //for example, the expected value maybe a range from RangeMatcher.
    matcher.injectExpectValue(bean);
    this.matcher = matcher;    
  }

  public TypicalValueMatcherSpec<M, B, B, K> getMatcherTemplate()
  {
    return matcherTemplate;
  }

  public void setMatcherTemplate(TypicalValueMatcherSpec<M, B, B, K> matcherTemplate)
  {
    this.matcherTemplate = matcherTemplate;
  }

  public BeanHandler<B> getBeanHandler()
  {
    return beanHandler;
  }

  public void setBeanHandler(BeanHandler<B> beanHandler)
  {
    this.beanHandler = beanHandler;
  }

  @Override
  public K getKey()
  {
    return matcher == null ? null : matcher.getKey();
  }

  
}
