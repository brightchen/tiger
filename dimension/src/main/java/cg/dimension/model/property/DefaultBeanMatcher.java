package cg.dimension.model.property;

import cg.common.general.Pair;
import cg.dimension.model.matcher.CloneableMatcher;
import cg.dimension.model.matcher.Matcher;
import cg.dimension.model.matcher.TypicalValueMatcherSpec;

public class DefaultBeanMatcher<B>
    extends AbstractBeanMatcher<DefaultBeanMatcher<B>, B>
{

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public DefaultBeanMatcher<B> cloneMe()
  {
    DefaultBeanMatcher<B> clone = new DefaultBeanMatcher<B>();
    for(Pair<BeanPropertyValueGenerator<B, Object>, TypicalValueMatcherSpec<?,Object,Object>> pair : matcherInfos)
    {
      Matcher<Object> matcher = pair.getRight();
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
    for(Pair<BeanPropertyValueGenerator<B, Object>, TypicalValueMatcherSpec<?,Object,Object>> pair : matcherInfos)
    {
      TypicalValueMatcherSpec<?,Object,Object> matcher = pair.getRight();
      Object value = pair.getLeft().getValue(bean);
      matcher.injectExpectValue(value);
    }
  }

}
