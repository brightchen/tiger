package cg.dimension.group;

/**
 * There are two types of group:
 *   - The group start with no item, any item can be added to the group, and a matcher will be create/initialize, 
 *     and other items must match the matcher before add to the group.
 *   - The group pre-set with matcher, all the item add to the group must match the matcher.
 *   
 * @author bright
 *
 * @param <V>
 */
public interface Group<B>
{
  /**
   * put the bean into the group. return true if the bean belong to this group.
   * @param bean
   * @return
   */
  public boolean put(B bean);
  
}
