package cg.dimension.group;

import cg.dimension.model.matcher.MatcherSameValueMapper;
import cg.dimension.model.matcher.TypicalValueMatcherSpec;

/**
 * SimpleGroupAggregate is a type of aggregate group which same type of input value of expected value
 * @author bright
 *
 * @param <M>
 * @param <B>
 * @param <MV>
 * @param <AV>
 */
public class SimpleGroupAggregate<M extends TypicalValueMatcherSpec<M, MV, MV, MV>, B, MV, AV extends Number>  
    extends DefaultGroupAggregate<M, MV, B, MV, AV>
{
  public SimpleGroupAggregate()
  {
    matcherValueMapper = new MatcherSameValueMapper<MV>();
  }
}
