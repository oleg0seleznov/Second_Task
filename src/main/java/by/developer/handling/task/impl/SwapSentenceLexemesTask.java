package by.developer.handling.task.impl;

import by.developer.handling.model.CompositeTextComponent;
import by.developer.handling.model.CompositeType;
import by.developer.handling.model.TextComponent;
import by.developer.handling.task.TextTask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SwapSentenceLexemesTask implements TextTask<String> {
  private static final Pattern LEXEME = Pattern.compile("\\S+");

  @Override
  public String execute(CompositeTextComponent text) {
    StringBuilder result = new StringBuilder();
    appendTransformed(text, result);
    return result.toString();
  }

  private void appendTransformed(CompositeTextComponent component, StringBuilder result) {
    for (TextComponent child : component.getChildren()) {
      if (child instanceof CompositeTextComponent
          && ((CompositeTextComponent) child).getType() == CompositeType.SENTENCE) {
        result.append(swapFirstAndLastLexemes(child.getContent()));
      } else if (child instanceof CompositeTextComponent) {
        appendTransformed((CompositeTextComponent) child, result);
      } else {
        result.append(child.getContent());
      }
    }
  }

  private String swapFirstAndLastLexemes(String sentence) {
    Matcher matcher = LEXEME.matcher(sentence);
    if (!matcher.find()) {
      return sentence;
    }
    int firstStart = matcher.start();
    int firstEnd = matcher.end();
    String first = matcher.group();
    if (!matcher.find()) {
      return sentence;
    }

    int lastStart = matcher.start();
    int lastEnd = matcher.end();
    String last = matcher.group();
    while (matcher.find()) {
      lastStart = matcher.start();
      lastEnd = matcher.end();
      last = matcher.group();
    }

    return sentence.substring(0, firstStart)
        + last
        + sentence.substring(firstEnd, lastStart)
        + first
        + sentence.substring(lastEnd);
  }
}
