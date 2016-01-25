package cg.dimension.model.matcher;

/**
 * The typical matcher is value matcher and a cloneable bean
 * and it can be injected with expect value
 * 
 * so a new instance of TypicalMatcherSpec can be created by clone and inject new expect value
 * @author bright
 *
 */
public interface TypicalValueMatcherSpec<T extends TypicalValueMatcherSpec<T, EV, V, K>, EV, V, K> 
    extends CloneableMatcher<T, V, K>
{
  public void injectExpectValue(EV value);
}
