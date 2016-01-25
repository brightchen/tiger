package cg.dimension.model.matcher;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

public class MatcherList<V, K>
{
  protected List<Matcher<V, K>> matchers;

  @SuppressWarnings("unchecked")
  public void setMatchers(Matcher<V, K> ... matchers)
  {
    this.matchers = Lists.newArrayList(matchers);
  }
  
  public void addMatcher(Matcher<V, K> matcher)
  {
    if(matcher == null)
      return;
    if(matchers == null)
      matchers = Lists.newArrayList();
    matchers.add(matcher);
  }
  
  @SuppressWarnings("unchecked")
  public void addMatchers(Matcher<V, K> ... matchers)
  {
    if(matchers == null)
      return;
    if(this.matchers == null)
      this.matchers = Lists.newArrayList();
    for(Matcher matcher : matchers)
      this.matchers.add(matcher);
  }
  
  public void addMatchers(Collection<Matcher<V, K>> matchers)
  {
    if(matchers == null)
      return;
    if(this.matchers == null)
      this.matchers = Lists.newArrayList();
    this.matchers.addAll(matchers);
  }
}
