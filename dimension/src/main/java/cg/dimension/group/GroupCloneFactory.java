package cg.dimension.group;

public class GroupCloneFactory<B, K> implements GroupFactory<B, K>
{
  protected CloneableGroup<? extends Group<B, K>, B, K> template;
  
  public GroupCloneFactory(){};
  
  public GroupCloneFactory(CloneableGroup<? extends Group<B, K>, B, K> template)
  {
    this.setTemplate(template);
  }
  
  @Override
  public Group<B, K> createGroup()
  {
    return template.cloneMe();
  }

  public CloneableGroup<? extends Group<B, K>, B, K> getTemplate()
  {
    return template;
  }

  public void setTemplate(CloneableGroup<? extends Group<B, K>, B, K> template)
  {
    this.template = template;
  }

  
}
