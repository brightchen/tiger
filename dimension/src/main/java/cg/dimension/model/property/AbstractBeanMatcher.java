package cg.dimension.model.property;

import java.util.List;

import com.google.common.collect.Lists;

import cg.common.general.Pair;
import cg.dimension.model.matcher.Matcher;
import cg.dimension.model.matcher.TypicalValueMatcherSpec;

/**
 * This class provide method to evaluate if a bean match this matcher
 * @author bright
 *
 */
public abstract class AbstractBeanMatcher<T extends AbstractBeanMatcher<T, B>, B> 
    implements TypicalValueMatcherSpec<T, B, B> 
{
  //protected List<BeanMatcherInfo<Object>> matcherInfos;
  protected List<Pair<BeanPropertyValueGenerator<B, Object>, TypicalValueMatcherSpec<?,Object,Object>>> matcherInfos = Lists.newArrayList();
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public <V> void addMatcherInfo(BeanPropertyValueGenerator<B, V> valueGenerator, Matcher<V> matcher)
  {
    matcherInfos.add((Pair)new Pair<BeanPropertyValueGenerator<B, V>, Matcher<V>>(valueGenerator, matcher));
  }
  
  public <V> void addMatcherInfo(Class<B> beanClass, String valueExpression, Class<V> valueType, Matcher<V> matcher)
  {
    addMatcherInfo(new BeanPropertyValueGenerator<B, V>(beanClass, valueExpression, valueType), matcher);
  }
  
  @Override
  public boolean matches(B bean)
  {
    for(Pair<BeanPropertyValueGenerator<B, Object>, TypicalValueMatcherSpec<?,Object,Object>> matcherInfo : matcherInfos)
    {
      //use logic and
      if(!matches(bean, matcherInfo.getLeft(), matcherInfo.getRight()))
        return false;
    }
    return true;
  }

  protected boolean matches(B bean, BeanPropertyValueGenerator<B, Object> valueGenerator, Matcher<Object> valueMatcher)
  {
    Object value = valueGenerator.getValue(bean);
    return valueMatcher.matches(value);
  }

  public List<Pair<BeanPropertyValueGenerator<B, Object>, TypicalValueMatcherSpec<?, Object, Object>>> getMatcherInfos()
  {
    return matcherInfos;
  }

  public void setMatcherInfos(
      List<Pair<BeanPropertyValueGenerator<B, Object>, TypicalValueMatcherSpec<?, Object, Object>>> matcherInfos)
  {
    this.matcherInfos = matcherInfos;
  }
  
}
