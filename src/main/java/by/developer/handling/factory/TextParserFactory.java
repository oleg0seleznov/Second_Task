package by.developer.handling.factory;

import by.developer.handling.parser.LexemeParser;
import by.developer.handling.parser.ParagraphParser;
import by.developer.handling.parser.SentenceParser;
import by.developer.handling.parser.WordParser;

public class TextParserFactory {
  private final TextComponentFactory componentFactory;

  public TextParserFactory(TextComponentFactory componentFactory) {
    this.componentFactory = componentFactory;
  }

  public ParagraphParser createParagraphParser() {
    return new ParagraphParser(componentFactory);
  }

  public SentenceParser createSentenceParser() {
    return new SentenceParser(componentFactory);
  }

  public LexemeParser createLexemeParser() {
    return new LexemeParser(componentFactory);
  }

  public WordParser createWordParser() {
    return new WordParser(componentFactory);
  }
}
