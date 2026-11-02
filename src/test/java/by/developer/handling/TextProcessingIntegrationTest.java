package by.developer.handling;

import static org.junit.jupiter.api.Assertions.assertEquals;

import by.developer.handling.model.CompositeTextComponent;
import by.developer.handling.parser.TextParserChain;
import by.developer.handling.service.TextFileReaderService;
import by.developer.handling.service.TextProcessingService;
import by.developer.handling.service.impl.TextFileReader;
import by.developer.handling.service.impl.TextProcessor;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class TextProcessingIntegrationTest {
  private static final String SOURCE = "Red blue red. Blue green! Green red?";
  private static final String FILE_NAME = "text.txt";
  private static final TextFileReaderService FILE_READER = new TextFileReader();
  private static final TextParserChain PARSER = new TextParserChain();
  private static final TextProcessingService TEXT_PROCESSOR =
      new TextProcessor(FILE_READER, PARSER);

  @TempDir Path temporaryDirectory;

  @Test
  void readsParsesAndProcessesTextFromFile() throws IOException {
    // given
    Path file = temporaryDirectory.resolve(FILE_NAME);
    Files.write(file, SOURCE.getBytes(StandardCharsets.UTF_8));

    // when
    CompositeTextComponent text = TEXT_PROCESSOR.processFile(file);

    // then
    assertEquals(SOURCE, text.getContent());
  }
}
