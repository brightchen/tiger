package cg.dimension.order;

import cg.common.generate.BeanGenerator;
import cg.common.generate.CharRandomGenerator;
import cg.common.generate.EnumRandomGenerator;
import cg.common.generate.FixLengthStringRandomGenerator;
import cg.common.generate.RandomHolder;
import cg.common.generate.VaryLengthStringRandomGenerator;

public class CustomerRandomGenerator implements BeanGenerator<Customer>
{
  public static final int ID_LENGTH = 10;
  protected FixLengthStringRandomGenerator idGenerator = new FixLengthStringRandomGenerator(CharRandomGenerator.digitAndLetterGenerator, ID_LENGTH);
  protected VaryLengthStringRandomGenerator varyLengthStringGenerator = new VaryLengthStringRandomGenerator(CharRandomGenerator.letterGenerator);
  protected FixLengthStringRandomGenerator zipGenerator = new FixLengthStringRandomGenerator(CharRandomGenerator.digitCharGenerator, 5);
  protected FixLengthStringRandomGenerator phoneGenerator = new FixLengthStringRandomGenerator(CharRandomGenerator.digitAndLetterGenerator, 10);
  protected EnumRandomGenerator<Country> countryGenerator = new EnumRandomGenerator<>(Country.class);
  
  @Override
  public Customer generate()
  {
    Customer customer = new Customer();
    customer.id = idGenerator.generate();
    customer.firstName = varyLengthStringGenerator.generate(2, 10);
    customer.lastName = varyLengthStringGenerator.generate(2, 10);
    customer.sex = ( RandomHolder.random.nextInt(2) == 0 ? Sex.Male : Sex.Female );
    customer.age = RandomHolder.random.nextInt(100) + 1;
    customer.address = varyLengthStringGenerator.generate(10, 30);
    customer.city = varyLengthStringGenerator.generate(2, 30);
    customer.stateOrProvince = varyLengthStringGenerator.generate(2, 20);
    customer.country = countryGenerator.generate();
    customer.zip = zipGenerator.generate();
    customer.phone = phoneGenerator.generate();

    return customer;
  }

}
