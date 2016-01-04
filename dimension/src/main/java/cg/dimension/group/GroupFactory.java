package cg.dimension.group;

public interface GroupFactory<B>
{
  /**
   * create Group
   * @return
   */
  public Group<B> createGroup();
}
