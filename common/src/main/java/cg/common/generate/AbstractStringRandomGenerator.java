package cg.common.generate;

public abstract class AbstractStringRandomGenerator implements BeanGenerator<String>
{
  protected CharRandomGenerator charGenerator;

  @Override
  public String generate()
  {
    if (charGenerator == null)
      throw new RuntimeException("Please set the char generator first.");
    final int stringLen = getStringLength();
    return generateWithLength(stringLen);
  }

  public String generateWithLength(int stringLen)
  {
    if (stringLen < 0)
      throw new RuntimeException("The string lenght expect not less than zero.");
    if (stringLen == 0)
      return "";
    char[] chars = new char[stringLen];
    for (int index = 0; index < stringLen; ++index)
      chars[index] = charGenerator.generate();
    return new String(chars);
  }
  
  protected abstract int getStringLength();

  public CharRandomGenerator getCharGenerator()
  {
    return charGenerator;
  }

  public void setCharGenerator(CharRandomGenerator charGenerator)
  {
    this.charGenerator = charGenerator;
  }
  
  
}