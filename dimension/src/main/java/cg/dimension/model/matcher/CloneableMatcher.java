package cg.dimension.model.matcher;

import cg.dimension.model.CloneableBean;

public interface CloneableMatcher<T extends CloneableMatcher<T, V>, V> 
    extends Matcher<V>, CloneableBean<T>
{
}
