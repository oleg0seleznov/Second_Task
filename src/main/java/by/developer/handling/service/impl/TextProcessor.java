package by.developer.handling.service.impl;

import by.developer.handling.model.CompositeTextComponent;
import by.developer.handling.parser.TextParserChain;
import by.developer.handling.service.TextFileReaderService;
import by.developer.handling.service.TextProcessingService;
import java.nio.file.Path;

public class TextProcessor implements TextProcessingService {
  private final TextFileReaderService fileReader;
  private final TextParserChain parserChain;

  public TextProcessor(TextFileReaderService fileReader, TextParserChain parserChain) {
    this.fileReader = fileReader;
    this.parserChain = parserChain;
  }

  @Override
  public CompositeTextComponent processFile(Path path) {
    String content = fileReader.read(path);
    return parserChain.parse(content);
  }
}
