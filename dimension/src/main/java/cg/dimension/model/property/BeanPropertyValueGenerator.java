package cg.dimension.model.property;

import com.datatorrent.lib.util.PojoUtils;
import com.datatorrent.lib.util.PojoUtils.Getter;

import cg.dimension.ReflectionUtil;
import cg.dimension.model.ValueGenerator;

/**
 * Get the the value of an property.
 * 
 * @author bright
 *
 * @param <B>
 */
public class BeanPropertyValueGenerator<B, V> implements ValueGenerator<B, V>
{
  protected Getter<B, V> getter;
  protected String propertyExpression;
  
  /**
   * this construct use the property type as the value type
   * @param beanClass
   * @param propertyExpression
   */
  public BeanPropertyValueGenerator(Class<B> beanClass, String propertyExpression)
  {
    this(beanClass, propertyExpression, ReflectionUtil.getPropertyType(beanClass, propertyExpression));
  }
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public BeanPropertyValueGenerator(Class<B> beanClass, String propertyExpression, Class valueType)
  {
    getter = PojoUtils.createGetter(beanClass, propertyExpression, valueType);
    this.setPropertyExpression(propertyExpression);
  }
  
  @SuppressWarnings("unchecked")
  public BeanPropertyValueGenerator(Class<B> beanClass, PropertyInfo propertyInfo)
  {
    getter = PojoUtils.createGetter(beanClass, propertyInfo.getPropertyExpression(), propertyInfo.getType().getJavaType());
    this.setPropertyExpression(propertyInfo.getPropertyExpression());
  }
  
  @Override
  public V getValue(B bean)
  {
    return getter.get(bean);
  }

  public String getPropertyExpression()
  {
    return propertyExpression;
  }

  public void setPropertyExpression(String propertyExpression)
  {
    this.propertyExpression = propertyExpression;
  }
  
}
