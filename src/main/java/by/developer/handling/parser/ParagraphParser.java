package by.developer.handling.parser;

import by.developer.handling.factory.TextComponentFactory;
import by.developer.handling.model.CompositeTextComponent;
import by.developer.handling.model.CompositeType;
import by.developer.handling.model.TextComponent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParagraphParser extends TextParser {
  private static final Pattern PARAGRAPH_SEPARATOR = Pattern.compile("(?:\\r?\\n[\\t ]*){2,}");

  private final TextComponentFactory componentFactory;

  public ParagraphParser(TextComponentFactory componentFactory) {
    this.componentFactory = componentFactory;
  }

  @Override
  public CompositeTextComponent parse(String content) {
    CompositeTextComponent text = componentFactory.createComposite(CompositeType.TEXT);
    Matcher matcher = PARAGRAPH_SEPARATOR.matcher(content);
    int position = 0;
    while (matcher.find()) {
      addParagraph(text, content.substring(position, matcher.start()));
      text.add(componentFactory.createTextLeaf(matcher.group()));
      position = matcher.end();
    }
    addParagraph(text, content.substring(position));
    return text;
  }

  private void addParagraph(CompositeTextComponent text, String content) {
    if (!content.isEmpty()) {
      TextComponent paragraph = parseNext(content);
      text.add(paragraph);
    }
  }
}
