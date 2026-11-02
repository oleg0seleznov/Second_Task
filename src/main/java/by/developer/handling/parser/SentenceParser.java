package by.developer.handling.parser;

import by.developer.handling.factory.TextComponentFactory;
import by.developer.handling.model.CompositeTextComponent;
import by.developer.handling.model.CompositeType;
import by.developer.handling.model.TextComponent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentenceParser extends TextParser {
  private static final Pattern SENTENCE =
      Pattern.compile("[^.!?]+[.!?]+[\\\"'»”’)]*|[.!?]+[\\\"'»”’)]*|[^.!?]+$");
  private static final Pattern LEADING_WHITESPACE = Pattern.compile("^\\s+");

  private final TextComponentFactory componentFactory;

  public SentenceParser(TextComponentFactory componentFactory) {
    this.componentFactory = componentFactory;
  }

  @Override
  public CompositeTextComponent parse(String content) {
    CompositeTextComponent paragraph = componentFactory.createComposite(CompositeType.PARAGRAPH);
    Matcher matcher = SENTENCE.matcher(content);
    int position = 0;
    while (matcher.find()) {
      if (position < matcher.start()) {
        String unmatchedContent = content.substring(position, matcher.start());
        paragraph.add(componentFactory.createTextLeaf(unmatchedContent));
      }
      addSentenceOrWhitespace(paragraph, matcher.group());
      position = matcher.end();
    }
    if (position < content.length()) {
      String remainingContent = content.substring(position);
      paragraph.add(componentFactory.createTextLeaf(remainingContent));
    }
    return paragraph;
  }

  private void addSentenceOrWhitespace(CompositeTextComponent paragraph, String content) {
    Matcher whitespace = LEADING_WHITESPACE.matcher(content);
    if (whitespace.find()) {
      paragraph.add(componentFactory.createTextLeaf(whitespace.group()));
      content = content.substring(whitespace.end());
    }
    if (content.isEmpty()) {
      return;
    }
    TextComponent sentence = parseNext(content);
    paragraph.add(sentence);
  }
}
