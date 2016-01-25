package cg.dimension.group;

public interface GroupFactory<B, K>
{
  /**
   * create Group
   * @return
   */
  public Group<B, K> createGroup();
}
