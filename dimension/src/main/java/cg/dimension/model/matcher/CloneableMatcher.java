package cg.dimension.model.matcher;

import cg.dimension.model.CloneableBean;

public interface CloneableMatcher<T extends CloneableMatcher<T, V, K>, V, K> 
    extends Matcher<V, K>, CloneableBean<T>
{
}
