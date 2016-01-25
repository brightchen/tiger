package cg.dimension.groupcoll;

import cg.dimension.group.CloneableGroup;
import cg.dimension.group.Group;
import cg.dimension.group.GroupCloneFactory;

/**
 * The SimpleAutoGenerateGroupChain automatically generate groups by group factory.
 * The GroupCloneFactory create new group by clone the template group.
 * 
 * @author bright
 *
 * @param <B>
 */
public class SimpleAutoGenerateGroupChain<B, K> extends AbstractGroupChain<B, K>
{
  protected GroupCloneFactory<B, K> groupFactory = new GroupCloneFactory<>();
  
  public SimpleAutoGenerateGroupChain()
  {
    this.initGroups();
  }
  
  @Override
  public boolean put(B bean)
  {
    for(Group<B, K> group : groups)
    {
      if(group.put(bean))
        return true;
    }
    
    //create a group for this bean
    Group<B, K> group = groupFactory.createGroup();
    group.put(bean);
    
    return groups.add(group);
  }
  
  public void setTemplate(CloneableGroup<? extends Group<B, K>, B, K> template)
  {
    groupFactory.setTemplate(template);
  }
}
