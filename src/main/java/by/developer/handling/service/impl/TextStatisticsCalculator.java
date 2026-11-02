package by.developer.handling.service.impl;

import by.developer.handling.model.CompositeTextComponent;
import by.developer.handling.service.TextStatistics;
import by.developer.handling.service.TextStatisticsService;
import java.util.stream.IntStream;

public class TextStatisticsCalculator implements TextStatisticsService {
  @Override
  public TextStatistics calculate(CompositeTextComponent text) {
    String content = text.getContent();
    int letters = 0;
    IntStream codePoints = content.codePoints();
    int[] points = codePoints.toArray();
    for (int codePoint : points) {
      if (Character.isLetter(codePoint)) {
        letters++;
      }
    }
    return new TextStatistics(letters, content.codePointCount(0, content.length()));
  }
}
