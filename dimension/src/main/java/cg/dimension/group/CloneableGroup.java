package cg.dimension.group;

import cg.dimension.model.CloneableBean;

public interface CloneableGroup<T extends CloneableBean<T>, B, K> extends Group<B, K>, CloneableBean<T>
{

}
