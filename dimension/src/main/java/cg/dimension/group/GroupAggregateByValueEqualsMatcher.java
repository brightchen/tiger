package cg.dimension.group;

import cg.dimension.model.CloneableBean;
import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.aggregate.CloneableAggregate;
import cg.dimension.model.matcher.EqualsMatcher;

public class GroupAggregateByValueEqualsMatcher<B, MV, AV extends Number> 
    extends AbstractValueMatcherDynamicGroupAggregate<GroupAggregateByValueEqualsMatcher<B, MV, AV>, B, MV, AV, MV> 
    implements CloneableBean<GroupAggregateByValueEqualsMatcher<B, MV, AV>>
{
  public static <B, MV, AV extends Number> GroupAggregateByValueEqualsMatcher<B, MV, AV> create()
  {
    return new GroupAggregateByValueEqualsMatcher<B, MV, AV>();
  }
  
  @Override
  protected void createMatcher(B bean, MV value)
  {
    this.matcher = new EqualsMatcher<>(value);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public GroupAggregateByValueEqualsMatcher<B, MV, AV> cloneMe()
  {
    GroupAggregateByValueEqualsMatcher<B, MV, AV> clone = new GroupAggregateByValueEqualsMatcher<B, MV, AV>();
    clone.withAggregatePropertyValueGenerator(aggregatePropertyValueGenerator).withMatchPropertyValueGenerator(matchPropertyValueGenerator);
    if(this.getAggregate() instanceof CloneableAggregate)
    {
      clone.withAggregate((Aggregate)((CloneableAggregate)aggregate).cloneMe());
    }
    return clone;
  }

  @Override
  public MV getKey()
  {
    return matcher.getKey();
  }

}
