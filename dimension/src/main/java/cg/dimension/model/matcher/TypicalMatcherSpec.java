package cg.dimension.model.matcher;

/**
 * The typical matcher is matcher and cloneable bean
 * and it can be injected with expect value
 * 
 * so a new instance of TypicalMatcherSpec can be created by clone and inject new expect value
 * @author bright
 *
 */
public interface TypicalMatcherSpec<T extends CloneableMatcher<T, V>, EV, V> extends CloneableMatcher<T, V>
{
  public void injectExpectValue(EV value);
}
