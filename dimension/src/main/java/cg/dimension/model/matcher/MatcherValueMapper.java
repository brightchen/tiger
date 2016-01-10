package cg.dimension.model.matcher;

/**
 * This inteface is used to map a match value to the expected value.
 * which is used to automatically create Matcher
 * 
 * @author bright
 *
 * @param <MV>
 * @param <EV>
 */
public interface MatcherValueMapper<MV, EV>
{
  public EV getExpectValue(MV matchValue);
}
