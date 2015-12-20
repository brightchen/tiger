package cg.dimension.order;

import cg.common.generate.CachedBeanGenerator;

public class CustomerGenerator extends CachedBeanGenerator<Customer>
{
  CustomerRandomGenerator randomGenerator = new CustomerRandomGenerator();
  
  @Override
  protected Customer generateBean()
  {
    return randomGenerator.generate();
  }

}
