package cg.dimension.groupcoll;

import java.util.Collection;

import com.google.common.collect.Lists;

import cg.dimension.group.Group;

public abstract class AbstractGroupChain<B, K> implements GroupCollection<B>
{
  protected Collection<Group<B, K>> groups;
  
  protected void initGroups()
  {
    if(groups == null)
      groups = Lists.newArrayList();
  }
  
  public boolean put(B bean)
  {
    for(Group<B, K> group : groups)
    {
      if(group.put(bean))
        return true;
    }
    return false;
  }
  
  public Collection<Group<B, K>> getGroups()
  {
    return groups;
  }
}
