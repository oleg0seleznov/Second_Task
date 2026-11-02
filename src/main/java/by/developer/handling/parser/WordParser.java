package by.developer.handling.parser;

import by.developer.handling.factory.TextComponentFactory;
import by.developer.handling.model.CompositeTextComponent;
import by.developer.handling.model.CompositeType;
import by.developer.handling.model.TextComponent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class WordParser extends TextParser {
  private static final Pattern WORD = Pattern.compile("\\p{L}+");
  private static final Pattern GRAMMAR_SIGN = Pattern.compile("\\p{P}");

  private final TextComponentFactory componentFactory;

  public WordParser(TextComponentFactory componentFactory) {
    this.componentFactory = componentFactory;
  }

  @Override
  public CompositeTextComponent parse(String content) {
    CompositeTextComponent lexeme = componentFactory.createComposite(CompositeType.LEXEME);
    Matcher matcher = WORD.matcher(content);
    int position = 0;
    while (matcher.find()) {
      addAtomicComponents(lexeme, content.substring(position, matcher.start()));
      CompositeTextComponent word = componentFactory.createComposite(CompositeType.WORD);
      String wordContent = matcher.group();
      IntStream wordCodePoints = wordContent.codePoints();
      wordCodePoints.forEach(
          codePoint ->
              word.add(componentFactory.createLetter(new String(Character.toChars(codePoint)))));
      lexeme.add(word);
      position = matcher.end();
    }
    addAtomicComponents(lexeme, content.substring(position));
    return lexeme;
  }

  private void addAtomicComponents(CompositeTextComponent lexeme, String content) {
    IntStream codePoints = content.codePoints();
    codePoints.forEach(codePoint -> addAtomicComponent(lexeme, codePoint));
  }

  private void addAtomicComponent(CompositeTextComponent lexeme, int codePoint) {
    String content = new String(Character.toChars(codePoint));
    Matcher grammarSignMatcher = GRAMMAR_SIGN.matcher(content);
    if (grammarSignMatcher.matches()) {
      TextComponent grammarSign = componentFactory.createGrammarSign(content);
      lexeme.add(grammarSign);
    } else {
      TextComponent textLeaf = componentFactory.createTextLeaf(content);
      lexeme.add(textLeaf);
    }
  }
}
