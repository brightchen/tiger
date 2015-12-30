package cg.dimension.order;

import cg.common.generate.BeanGenerator;
import cg.common.generate.CharRandomGenerator;
import cg.common.generate.EnumRandomGenerator;
import cg.common.generate.FixLengthStringRandomGenerator;
import cg.common.generate.RandomHolder;

public class ProductInfoRandomGenerator implements BeanGenerator<ProductInfo>
{
  public static final int PROD_CODE_LENGTH = 10;
  protected FixLengthStringRandomGenerator prodCodeGenerator = new FixLengthStringRandomGenerator(CharRandomGenerator.digitAndLetterGenerator, PROD_CODE_LENGTH);
  protected EnumRandomGenerator<ProductCategory> prodCategoryGenerator = new EnumRandomGenerator<>(ProductCategory.class);
  protected EnumRandomGenerator<Color> colorGenerator = new EnumRandomGenerator<>(Color.class);
  @Override
  public ProductInfo generate()
  {
    ProductInfo pi = new ProductInfo();
    pi.productCode = prodCodeGenerator.generate();
    pi.productCategy = prodCategoryGenerator.generate();
    pi.color = colorGenerator.generate();
    pi.weight = RandomHolder.random.nextFloat();
    return pi;
  }

}
