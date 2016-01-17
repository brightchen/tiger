package cg.dimension.groupcoll;

import cg.dimension.group.CloneableGroup;
import cg.dimension.group.Group;
import cg.dimension.group.GroupCloneFactory;

/**
 * The AbstractAutoGenerateGroupCollection automatically generate groups by group factory.
 * The GroupCloneFactory create new group by clone the template group.
 * 
 * @author bright
 *
 * @param <B>
 */
public abstract class AbstractAutoGenerateGroupCollection<B> implements GroupCollection<B>
{
  protected GroupCloneFactory<B> groupFactory = new GroupCloneFactory<>();
  
  @Override
  public boolean put(B bean)
  {
    if(putToExistingGroups(bean))
      return true;
    
    //create a group for this bean
    Group<B> group = groupFactory.createGroup();
    group.put(bean);
    
    return true;
  }
  
  /**
   * @param bean
   * @return true if successful put to existing groups, else return false
   */
  protected abstract boolean putToExistingGroups(B bean);
  
  public void setTemplate(CloneableGroup<? extends Group<B>, B> template)
  {
    groupFactory.setTemplate(template);
  }
}
