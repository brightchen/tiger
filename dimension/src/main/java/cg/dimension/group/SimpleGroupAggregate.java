package cg.dimension.group;

import cg.dimension.model.matcher.MatcherSameValueMapper;
import cg.dimension.model.matcher.TypicalValueMatcherSpec;

public class SimpleGroupAggregate<M extends TypicalValueMatcherSpec<M, MV, MV>, B, MV, AV extends Number>  
    extends DefaultGroupAggregate<M, MV, B, MV, AV>
{
  public SimpleGroupAggregate()
  {
    matcherValueMapper = new MatcherSameValueMapper<MV>();
  }
}
