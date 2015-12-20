package cg.common.generate;

import java.util.Set;

import com.google.common.collect.Sets;

public class CharRandomGenerator implements BeanGenerator<Character>
{
  public static final CharRandomGenerator digitCharGenerator = new CharRandomGenerator(CharRange.digits);
  public static final CharRandomGenerator lowerLetterGenerator = new CharRandomGenerator(CharRange.lowerLetters);
  public static final CharRandomGenerator upperLetterGenerator = new CharRandomGenerator(CharRange.upperLetters);
  public static final CharRandomGenerator letterGenerator = new CharRandomGenerator(CharRange.upperLetters, CharRange.upperLetters);
  public static final CharRandomGenerator digitAndLetterGenerator = new CharRandomGenerator(CharRange.digits, CharRange.upperLetters, CharRange.upperLetters);

  private char[] candidates;

  public CharRandomGenerator()
  {
  }

  public CharRandomGenerator(CharRange... ranges)
  {
    addRanges(ranges);
  }

  public void addRanges(CharRange... ranges)
  {
    if (ranges == null || ranges.length == 0)
      return;
    Set<Character> chars = Sets.newHashSet();

    for (CharRange range : ranges) {
      if (range.from == null || range.to == null)
        continue; //invalid
      for (Character theChar = range.from; theChar <= range.to; ++theChar) {
        chars.add(theChar);
      }
    }

    //merge with the old candidate
    if (candidates != null && candidates.length > 0) {
      for (char theChar : candidates)
        chars.add(theChar);
    }

    candidates = new char[chars.size()];
    int index = 0;
    for (char theChar : chars) {
      candidates[index++] = theChar;
    }
  }

  @Override
  public Character generate()
  {
    return candidates[RandomHolder.random.nextInt(candidates.length)];
  }
}
