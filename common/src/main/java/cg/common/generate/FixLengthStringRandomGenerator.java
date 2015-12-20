package cg.common.generate;

public class FixLengthStringRandomGenerator extends AbstractStringRandomGenerator
{
  protected int length;

  public FixLengthStringRandomGenerator()
  {
  }

  public FixLengthStringRandomGenerator(CharRandomGenerator charGenerator, int length)
  {
    if (length <= 0)
      throw new IllegalArgumentException("The length should large than zero.");
    this.length = length;
    this.charGenerator = charGenerator;
  }

  @Override
  protected int getStringLength()
  {
    return length;
  }
  
  public String generate(int length)
  {
    if (charGenerator == null)
      throw new RuntimeException("Please set the char generator first.");
    return generateWithLength(length);
  }

  public int getLength()
  {
    return length;
  }

  public void setLength(int length)
  {
    this.length = length;
  }

}
