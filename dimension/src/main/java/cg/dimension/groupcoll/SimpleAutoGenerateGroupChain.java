package cg.dimension.groupcoll;

import cg.dimension.group.CloneableGroup;
import cg.dimension.group.Group;
import cg.dimension.group.GroupCloneFactory;

/**
 * The SimpleAutoGenerateGroupChain automatically gererate groups by group factory.
 * The GroupCloneFactory create new group by clone the template group.
 * @author bright
 *
 * @param <B>
 */
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
    
    //create a group for this bean
    Group<B> group = groupFactory.createGroup();
    group.put(bean);
    
    return groups.add(group);
  }
  
  public void setTemplate(CloneableGroup<? extends Group<B>, B> template)
  {
    groupFactory.setTemplate(template);
  }
}
