package cg.dimension.model.property;

import java.util.Map;

import com.google.common.collect.Maps;

import cg.common.general.Pair;
import cg.dimension.model.matcher.CloneableMatcher;
import cg.dimension.model.matcher.Matcher;
import cg.dimension.model.matcher.TypicalValueMatcherSpec;

/**
 * should create a map( property-expression, value ) as the key.
 * @author bright
 *
 * @param <B>
 */
public class DefaultBeanMatcher<B>
    extends AbstractBeanMatcher<DefaultBeanMatcher<B>, B, Map<String, ?>>
{

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public DefaultBeanMatcher<B> cloneMe()
  {
    DefaultBeanMatcher<B> clone = new DefaultBeanMatcher<B>();
    for(Pair<BeanPropertyValueGenerator<B, Object>, TypicalValueMatcherSpec<?,Object,Object, Object>> pair : matcherInfos)
    {
      Matcher<Object, Object> matcher = pair.getRight();
      if(matcher instanceof CloneableMatcher)
      {
        matcher = (Matcher)((CloneableMatcher)matcher).cloneMe();
      }
      clone.addMatcherInfo(pair.getLeft(), matcher);
    }
    return clone;
  }

  /**
   * inject the value from the bean.
   * inject the value to the matchers
   */
  @Override
  public void injectExpectValue(B bean)
  {
    for(Pair<BeanPropertyValueGenerator<B, Object>, TypicalValueMatcherSpec<?,Object,Object, Object>> pair : matcherInfos)
    {
      TypicalValueMatcherSpec<?,Object,Object, Object> matcher = pair.getRight();
      Object value = pair.getLeft().getValue(bean);
      matcher.injectExpectValue(value);
    }
  }

  @Override
  public Map<String, Object> getKey()
  {
    Map<String, Object> key = Maps.newHashMap();
    for(Pair<BeanPropertyValueGenerator<B, Object>, TypicalValueMatcherSpec<?,Object,Object, Object>> pair : matcherInfos)
    {
      Matcher<Object, Object> matcher = pair.getRight();
      key.put(pair.getLeft().getPropertyExpression(), matcher.getKey());
    }
    return key;
  }

}
