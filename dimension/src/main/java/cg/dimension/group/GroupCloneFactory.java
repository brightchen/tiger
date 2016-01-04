package cg.dimension.group;

public class GroupCloneFactory<B> implements GroupFactory<B>
{
  protected CloneableGroup<? extends Group<B>, B> template;
  
  public GroupCloneFactory(){};
  
  public GroupCloneFactory(CloneableGroup<? extends Group<B>, B> template)
  {
    this.setTemplate(template);
  }
  
  @Override
  public Group<B> createGroup()
  {
    return template.cloneMe();
  }

  public CloneableGroup<? extends Group<B>, B> getTemplate()
  {
    return template;
  }

  public void setTemplate(CloneableGroup<? extends Group<B>, B> template)
  {
    this.template = template;
  }

  
}
