package cg.dimension.model.property;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cg.dimension.model.ValueGenerator;

/**
 * BeanMultiplePropertyValueGenerator implements ValueGenerator 
 * @author bright
 *
 * @param <B>
 */
public class BeanMultiplePropertyValueGenerator<B> implements ValueGenerator<B, Map<String, ?>>
{
  protected List<BeanPropertyValueGenerator<B, ?>> propertyValueGenerators;
  
  @Override
  public Map<String, Object> getValue(B source)
  {
    Map<String, Object> propertyToValue = Maps.newHashMap();
    for(BeanPropertyValueGenerator<B, ?> valueGenerator : propertyValueGenerators)
    {
      Object value = valueGenerator.getValue(source);
      propertyToValue.put(valueGenerator.getPropertyExpression(), value);
    }
    return propertyToValue;
  }

  public List<BeanPropertyValueGenerator<B, ?>> getPropertyValueGenerators()
  {
    return propertyValueGenerators;
  }

  public void setPropertyValueGenerators(List<BeanPropertyValueGenerator<B, ?>> propertyValueGenerators)
  {
    this.propertyValueGenerators = propertyValueGenerators;
  }
  
  @SuppressWarnings("unchecked")
  public BeanMultiplePropertyValueGenerator<B> addPropertyValueGenerator(BeanPropertyValueGenerator<B, ?> propertyValueGenerator)
  {
    if(propertyValueGenerators == null)
      propertyValueGenerators = Lists.newArrayList();
    
    propertyValueGenerators.add(propertyValueGenerator);
    return this;
  }

}
