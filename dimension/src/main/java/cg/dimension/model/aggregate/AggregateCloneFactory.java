package cg.dimension.model.aggregate;

public class AggregateCloneFactory<V extends Number> implements AggregateFactory<V>
{
  protected CloneableAggregate<V> templateAggregate;
  
  public AggregateCloneFactory(){}
  
  public AggregateCloneFactory(CloneableAggregate<V> templateAggregate)
  {
    setTemplateAggregate(templateAggregate);
  }
  
  @Override
  public Aggregate<V> createAggregate()
  {
    return templateAggregate.cloneMe();
  }

  public CloneableAggregate<V> getTemplateAggregate()
  {
    return templateAggregate;
  }

  public void setTemplateAggregate(CloneableAggregate<V> templateAggregate)
  {
    this.templateAggregate = templateAggregate;
  }

}
