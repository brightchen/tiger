package cg.dimension.groupcoll;

import java.util.Collection;

import cg.dimension.group.CloneableGroup;
import cg.dimension.group.Group;
import cg.dimension.group.GroupCloneFactory;
import cg.dimension.model.ValueGenerator;

/**
 * The AbstractAutoGenerateGroupCollection automatically generate groups by group factory.
 * The GroupCloneFactory create new group by clone the template group.
 * 
 * @author bright
 *
 * @param <B>
 */
public abstract class AbstractAutoGenerateGroupCollection<B, MV, K> implements GroupCollection<B>
{
  protected ValueGenerator<B, MV> matchValueGenerator;
  protected GroupCloneFactory<B, K> groupFactory = new GroupCloneFactory<>();
  
  public abstract Collection<Group<B, K>> getGroups();
  
  public void setTemplate(CloneableGroup<? extends Group<B, K>, B, K> template)
  {
    groupFactory.setTemplate(template);
  }

  public ValueGenerator<B, MV> getMatchValueGenerator()
  {
    return matchValueGenerator;
  }

  public void setValueGenerator(ValueGenerator<B, MV> matchValueGenerator)
  {
    this.matchValueGenerator = matchValueGenerator;
  }

  public void setGroupFactory(GroupCloneFactory<B, K> groupFactory)
  {
    this.groupFactory = groupFactory;
  }
  
}
