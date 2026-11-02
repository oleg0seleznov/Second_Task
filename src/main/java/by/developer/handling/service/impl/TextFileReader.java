package by.developer.handling.service.impl;

import by.developer.handling.exception.TextProcessingException;
import by.developer.handling.service.TextFileReaderService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextFileReader implements TextFileReaderService {
  private static final Logger LOGGER = LogManager.getLogger(TextFileReader.class);

  @Override
  public String read(Path path) {
    try {
      List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
      return String.join(System.lineSeparator(), lines);
    } catch (IOException exception) {
      LOGGER.error("Не удалось прочитать файл: {}", path, exception);
      throw new TextProcessingException("Не удалось прочитать файл: " + path, exception);
    }
  }
}
