package by.developer.handling.service;

import by.developer.handling.model.CompositeTextComponent;
import java.nio.file.Path;

public interface TextProcessingService {
  CompositeTextComponent processFile(Path path);
}
