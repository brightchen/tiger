package cg.common.generate;

public class CharRange extends Range<Character>
{
  public static final CharRange digits = new CharRange('0', '9');
  public static final CharRange lowerLetters = new CharRange('a', 'z');
  public static final CharRange upperLetters = new CharRange('A', 'Z');

  public CharRange()
  {
    this('a', 'z');
  }

  public CharRange(Character from, Character to)
  {
    super(from, to);
  }

}
