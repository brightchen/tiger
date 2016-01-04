package cg.dimension.group;

import cg.dimension.model.CloneableBean;

public interface CloneableGroup<T extends CloneableBean<T>, B> extends Group<B>, CloneableBean<T>
{

}
