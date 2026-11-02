package by.developer.handling.parser;

import by.developer.handling.factory.TextComponentFactory;
import by.developer.handling.factory.TextParserFactory;
import by.developer.handling.model.CompositeTextComponent;

public class TextParserChain {
  private final ParagraphParser paragraphParser;

  public TextParserChain() {
    TextComponentFactory componentFactory = new TextComponentFactory();
    TextParserFactory parserFactory = new TextParserFactory(componentFactory);
    paragraphParser = parserFactory.createParagraphParser();
    SentenceParser sentenceParser = parserFactory.createSentenceParser();
    LexemeParser lexemeParser = parserFactory.createLexemeParser();
    WordParser wordParser = parserFactory.createWordParser();

    paragraphParser.setNextParser(sentenceParser);
    sentenceParser.setNextParser(lexemeParser);
    lexemeParser.setNextParser(wordParser);
  }

  public CompositeTextComponent parse(String content) {
    return paragraphParser.parse(content);
  }
}
