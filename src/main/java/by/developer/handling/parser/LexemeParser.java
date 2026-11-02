package by.developer.handling.parser;

import by.developer.handling.factory.TextComponentFactory;
import by.developer.handling.model.CompositeTextComponent;
import by.developer.handling.model.CompositeType;
import by.developer.handling.model.TextComponent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexemeParser extends TextParser {
  private static final Pattern LEXEME = Pattern.compile("\\S+");

  private final TextComponentFactory componentFactory;

  public LexemeParser(TextComponentFactory componentFactory) {
    this.componentFactory = componentFactory;
  }

  @Override
  public CompositeTextComponent parse(String content) {
    CompositeTextComponent sentence = componentFactory.createComposite(CompositeType.SENTENCE);
    Matcher matcher = LEXEME.matcher(content);
    int position = 0;
    while (matcher.find()) {
      if (position < matcher.start()) {
        String unmatchedContent = content.substring(position, matcher.start());
        sentence.add(componentFactory.createTextLeaf(unmatchedContent));
      }
      TextComponent lexeme = parseNext(matcher.group());
      sentence.add(lexeme);
      position = matcher.end();
    }
    if (position < content.length()) {
      String remainingContent = content.substring(position);
      sentence.add(componentFactory.createTextLeaf(remainingContent));
    }
    return sentence;
  }
}
