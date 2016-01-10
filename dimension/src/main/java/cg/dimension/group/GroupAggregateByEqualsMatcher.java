package cg.dimension.group;

import cg.dimension.model.CloneableBean;
import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.aggregate.CloneableAggregate;
import cg.dimension.model.matcher.EqualsMatcher;

public class GroupAggregateByEqualsMatcher<B, MV, AV extends Number> extends AbstractGroupAggregate<GroupAggregateByEqualsMatcher<B, MV, AV>, B, MV, AV> 
  implements CloneableBean<GroupAggregateByEqualsMatcher<B, MV, AV>>
{
  public static <B, MV, AV extends Number> GroupAggregateByEqualsMatcher<B, MV, AV> create()
  {
    return new GroupAggregateByEqualsMatcher<B, MV, AV>();
  }
  
  @Override
  protected void createMatcher(B bean, MV value)
  {
    this.matcher = new EqualsMatcher<>(value);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public GroupAggregateByEqualsMatcher<B, MV, AV> cloneMe()
  {
    GroupAggregateByEqualsMatcher<B, MV, AV> clone = new GroupAggregateByEqualsMatcher<B, MV, AV>();
    clone.withAggregatePropertyValueGenerator(aggregatePropertyValueGenerator).withMatchPropertyValueGenerator(matchPropertyValueGenerator);
    if(this.getAggregate() instanceof CloneableAggregate)
    {
      clone.withAggregate((Aggregate)((CloneableAggregate)aggregate).cloneMe());
    }
    return clone;
  }

}
