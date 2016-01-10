package cg.dimension.group;

import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.aggregate.CloneableAggregate;
import cg.dimension.model.matcher.MatcherValueMapper;
import cg.dimension.model.matcher.TypicalMatcherSpec;

/**
 * DefaultGroupAggregate create new matcher by clone from the template and inject the value 
 * @author bright
 *
 * @param <M> The class of Matcher for clone
 * @param <EV>
 * @param <B>
 * @param <MV>
 * @param <AV>
 */
public class DefaultGroupAggregate<M extends TypicalMatcherSpec<M, EV, MV>, EV, B, MV, AV extends Number> 
    extends AbstractGroupAggregate<DefaultGroupAggregate<M, EV, B, MV, AV>, B, MV, AV>
{
  protected TypicalMatcherSpec<M, EV, MV> matcherTemplate;
  protected MatcherValueMapper<MV, EV> matcherValueMapper;
  
  public DefaultGroupAggregate<M, EV, B, MV, AV> withMatcherTemplate(TypicalMatcherSpec<M, EV, MV> matcherTemplate)
  {
    this.matcherTemplate = matcherTemplate;
    return this;
  }
  
  public DefaultGroupAggregate<M, EV, B, MV, AV> withMatcherValueMapper(MatcherValueMapper<MV, EV> matcherValueMapper)
  {
    this.matcherValueMapper = matcherValueMapper;
    return this;
  }
  
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public DefaultGroupAggregate<M, EV, B, MV, AV> cloneMe()
  {
    DefaultGroupAggregate clone = null;
    try
    {
      clone = this.getClass().newInstance();   
    }
    catch(Exception e)
    {
      clone = new DefaultGroupAggregate();
    }
    if(aggregate instanceof CloneableAggregate)
    {
      //clone the aggregate instead of using same aggregate
      clone.withAggregate((Aggregate)((CloneableAggregate)aggregate).cloneMe());
    }
    else
      clone.withAggregate(aggregate);
    clone.withAggregatePropertyValueGenerator(aggregatePropertyValueGenerator);
    clone.withMatchPropertyValueGenerator(matchPropertyValueGenerator);
    clone.withMatcherValueMapper(matcherValueMapper);
    clone.withMatcherTemplate(matcherTemplate);
    return clone;
  }

  @Override
  protected void createMatcher(B bean, MV value)
  {
    M matcher = matcherTemplate.cloneMe();
    
    //the match value maybe different from expected value.
    //for example, the expected value maybe a range from RangeMatcher.
    matcher.injectExpectValue(getExpectedValue(value));
    this.matcher = matcher;
  }
  
  protected EV getExpectedValue(MV value)
  {
    return matcherValueMapper.getExpectValue(value);
  }
}
