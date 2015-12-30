package cg.dimension.model.group;

import cg.dimension.model.matcher.EqualsMatcher;

public class GroupAggregateByEqualsMatcher<B, MV, AV extends Number> extends AbstractGroupAggregate<B, MV, AV>
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

}
