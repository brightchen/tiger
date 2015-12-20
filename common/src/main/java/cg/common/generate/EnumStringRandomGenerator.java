package cg.common.generate;

import java.util.Random;

public class EnumStringRandomGenerator implements BeanGenerator<String>
{
  protected static final Random random = new Random();
  protected String[] candidates;

  public EnumStringRandomGenerator()
  {
  }

  public EnumStringRandomGenerator(String[] candidates)
  {
    if (candidates == null || candidates.length == 0)
      throw new IllegalArgumentException("candidates can't null or empty.");
    this.candidates = candidates;
  }

  @Override
  public String generate()
  {
    if (candidates.length == 1)
      return candidates[0];
    return candidates[random.nextInt(candidates.length)];
  }
}