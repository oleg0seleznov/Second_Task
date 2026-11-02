package by.developer.handling.parser;

import by.developer.handling.exception.TextProcessingException;
import by.developer.handling.model.TextComponent;

public abstract class TextParser {
  private TextParser nextParser;

  public void setNextParser(TextParser nextParser) {
    this.nextParser = nextParser;
  }

  protected TextComponent parseNext(String content) {
    if (nextParser == null) {
      throw new TextProcessingException("В цепочке парсеров отсутствует следующий парсер.");
    }
    return nextParser.parse(content);
  }

  public abstract TextComponent parse(String content);
}
