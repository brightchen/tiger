package cg.dimension.model.aggregate;

public class AggregateCloneFactory<T extends CloneableAggregate<T, V>, V extends Number> implements AggregateFactory<T, V>
{
  protected CloneableAggregate<T, V> templateAggregate;
  
  public AggregateCloneFactory(){}
  
  public AggregateCloneFactory(CloneableAggregate<T, V> templateAggregate)
  {
    setTemplateAggregate(templateAggregate);
  }
  

  public CloneableAggregate<T, V> getTemplateAggregate()
  {
    return templateAggregate;
  }

  public void setTemplateAggregate(CloneableAggregate<T, V> templateAggregate)
  {
    this.templateAggregate = templateAggregate;
  }

  @Override
  public T createAggregate()
  {
    return templateAggregate.cloneMe();
  }

}
