package by.developer.handling.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import by.developer.handling.exception.TextProcessingException;
import by.developer.handling.service.impl.TextFileReader;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class TextFileReaderTest {
  private static final TextFileReaderService FILE_READER = new TextFileReader();

  @TempDir Path temporaryDirectory;

  @Test
  void throwsTextProcessingExceptionWhenFileCannotBeRead() {
    // given
    Path missingFile = temporaryDirectory.resolve("missing.txt");

    // when
    TextProcessingException exception =
        assertThrows(TextProcessingException.class, () -> FILE_READER.read(missingFile));

    // then
    assertEquals("Не удалось прочитать файл: " + missingFile, exception.getMessage());
  }
}
