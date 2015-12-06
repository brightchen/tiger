package cg.dimension.model.matcher;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

public class MatcherList<V>
{
  protected List<Matcher<V>> matchers;

  @SuppressWarnings("unchecked")
  public void setMatchers(Matcher<V> ... matchers)
  {
    this.matchers = Lists.newArrayList(matchers);
  }
  
  public void addMatcher(Matcher<V> matcher)
  {
    if(matcher == null)
      return;
    if(matchers == null)
      matchers = Lists.newArrayList();
    matchers.add(matcher);
  }
  
  @SuppressWarnings("unchecked")
  public void addMatchers(Matcher<V> ... matchers)
  {
    if(matchers == null)
      return;
    if(this.matchers == null)
      this.matchers = Lists.newArrayList();
    for(Matcher matcher : matchers)
      this.matchers.add(matcher);
  }
  
  public void addMatchers(Collection<Matcher<V>> matchers)
  {
    if(matchers == null)
      return;
    if(this.matchers == null)
      this.matchers = Lists.newArrayList();
    this.matchers.addAll(matchers);
  }
}
