package cg.dimension.groupcoll;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import cg.dimension.group.Group;
import cg.dimension.model.matcher.MatcherValueMapper;

/**
 * keep the match value and group in a new to find the group when new bean come.
 * 
 * @author bright
 *
 * @param <B>
 * @param <MV>
 * @param <K>
 */
public class MapAutoGenerateGroupCollection<B, MV, K> extends AbstractAutoGenerateGroupCollection<B, MV, K>
{
  Map<K, Group<B, K>> matchValueToGroupMap = Maps.newHashMap();
  //map from match value to key
  protected MatcherValueMapper<MV, K> matcherValueMapper;
  
  /**
   * This solution only valid when match by one value.
   */
  @Override
  public boolean put(B bean)
  {
    MV matchValue = matchValueGenerator.getValue(bean);
    K key = getKey(matchValue);
    Group<B, K> group = matchValueToGroupMap.get(key);
    if(group != null)
    {
      return group.put(bean);
    }
    
    //create a group for this bean
    Group<B, K> newGroup = groupFactory.createGroup();
    if(!newGroup.put(bean))
      return false;
    
    matchValueToGroupMap.put(key, newGroup);
    
    return true;
  }
  
  protected K getKey(MV matchValue)
  {
    return matcherValueMapper.getExpectValue(matchValue);
  }
  
  @Override
  public Collection<Group<B, K>> getGroups()
  {
    return matchValueToGroupMap.values();
  }

  public MatcherValueMapper<MV, K> getMatcherValueMapper()
  {
    return matcherValueMapper;
  }

  public void setMatcherValueMapper(MatcherValueMapper<MV, K> matcherValueMapper)
  {
    this.matcherValueMapper = matcherValueMapper;
  }

}
