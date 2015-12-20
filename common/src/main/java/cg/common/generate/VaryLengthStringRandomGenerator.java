package cg.common.generate;

public class VaryLengthStringRandomGenerator extends AbstractStringRandomGenerator
{
  protected Range<Integer> lengthRange;

  public VaryLengthStringRandomGenerator()
  {
  }

  public VaryLengthStringRandomGenerator(CharRandomGenerator charGenerator)
  {
    init(charGenerator);
  }
  public VaryLengthStringRandomGenerator(CharRandomGenerator charGenerator, int minLength, int maxLength)
  {
    init(charGenerator, minLength, maxLength);
  }
  
  public void init(CharRandomGenerator charGenerator)
  {
    this.charGenerator = charGenerator;
  }
  
  public void init(CharRandomGenerator charGenerator, int minLength, int maxLength)
  {
    if (minLength <= 0 || maxLength <= 0 || minLength >= maxLength)
      throw new IllegalArgumentException("Invliad length: minLength = " + minLength + "; maxLength = " + maxLength);
    lengthRange = new Range<Integer>(minLength, maxLength);
    this.charGenerator = charGenerator;
  }

  @Override
  protected int getStringLength()
  {
    return lengthRange.from + RandomHolder.random.nextInt(lengthRange.to - lengthRange.from);
  }

  public String generate(int minLength, int maxLength)
  {
    if (charGenerator == null)
      throw new RuntimeException("Please set the char generator first.");
    final int stringLen = minLength + RandomHolder.random.nextInt(maxLength - minLength);
    return generateWithLength(stringLen);
  }
}