package cg.dimension.groupcoll;

import cg.dimension.group.CloneableGroup;
import cg.dimension.group.Group;
import cg.dimension.group.GroupCloneFactory;
import cg.dimension.group.GroupFactory;

public class SimpleAutoGenerateGroupChain<B> extends AbstractGroupChain<B>
{
  protected GroupCloneFactory<B> groupFactory = new GroupCloneFactory<>();
  
  public SimpleAutoGenerateGroupChain()
  {
    this.initGroups();
  }
  
  @Override
  public boolean put(B bean)
  {
    for(Group<B> group : groups)
    {
      if(group.put(bean))
        return true;
    }
    
    //create a group for this B
    Group<B> group = groupFactory.createGroup();
    group.put(bean);
    
    return groups.add(group);
  }
  
  public void setTemplate(CloneableGroup<? extends Group<B>, B> template)
  {
    groupFactory.setTemplate(template);
  }
}
