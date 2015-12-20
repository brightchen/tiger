package cg.common.generate;

import java.util.List;

import com.google.common.collect.Lists;

public class StringCompositeGenerator <T> implements BeanGenerator<String>
{
  private List<BeanGenerator<String>> generators;

  public StringCompositeGenerator()
  {
  }

  @SafeVarargs
  public StringCompositeGenerator(BeanGenerator<String>... generators)
  {
    if (generators == null || generators.length == 0)
      return;
    this.generators = Lists.newArrayList(generators);
  }

  @Override
  public String generate()
  {
    StringBuilder sb = new StringBuilder();
    for (BeanGenerator<String> generator : generators) {
      sb.append(generator.generate());
    }
    return sb.toString();
  }

  public List<BeanGenerator<String>> getGenerators()
  {
    return generators;
  }

  public void setGenerators(List<BeanGenerator<String>> generators)
  {
    this.generators = generators;
  }

}