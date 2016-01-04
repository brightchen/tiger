package cg.dimension.groupcoll;

/**
 * A collection of group, which handle same bean
 * 
 * @author bright
 *
 */
public interface GroupCollection<B>
{
  /**
   * put the bean into the group. return true if the bean belong to this group.
   * @param bean
   * @return
   */
  public boolean put(B bean);
}
