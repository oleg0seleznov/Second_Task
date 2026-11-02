package by.developer.handling.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import by.developer.handling.model.CompositeTextComponent;
import by.developer.handling.parser.TextParserChain;
import by.developer.handling.service.TextStatistics;
import by.developer.handling.service.TextStatisticsService;
import by.developer.handling.service.impl.TextStatisticsCalculator;
import java.util.Arrays;
import java.util.List;

import by.developer.handling.task.impl.MaxRepeatedWordSentencesTask;
import by.developer.handling.task.impl.RepeatedWordResult;
import by.developer.handling.task.impl.SentenceSortTask;
import by.developer.handling.task.impl.SwapSentenceLexemesTask;
import org.junit.jupiter.api.Test;

class TextTasksTest {
  private static final String REPEATED_WORD_SOURCE = "Cat and dog. Cat, cat! Dog and bird.";
  private static final String SORT_SOURCE = "Banana. Pear! APPLE.";
  private static final String SWAP_SOURCE = "One two three. Hi!";
  private static final String STATISTICS_SOURCE = "Ёж 🐈.";
  private static final List<String> EXPECTED_REPEATED_WORDS = Arrays.asList("and", "cat", "dog");
  private static final List<String> EXPECTED_SORTED_SENTENCES =
      Arrays.asList("Pear!", "APPLE.", "Banana.");
  private static final TextParserChain PARSER = new TextParserChain();
  private static final MaxRepeatedWordSentencesTask REPEATED_WORDS_TASK =
      new MaxRepeatedWordSentencesTask();
  private static final SentenceSortTask SENTENCE_SORT_TASK = new SentenceSortTask('a');
  private static final SwapSentenceLexemesTask SWAP_LEXEMES_TASK = new SwapSentenceLexemesTask();
  private static final TextStatisticsService STATISTICS_SERVICE = new TextStatisticsCalculator();

  @Test
  void findsWordsPresentInMostDistinctSentences() {
    // given
    CompositeTextComponent text = PARSER.parse(REPEATED_WORD_SOURCE);

    // when
    RepeatedWordResult result = REPEATED_WORDS_TASK.execute(text);

    // then
    assertEquals(2, result.getSentenceCount());
    assertEquals(EXPECTED_REPEATED_WORDS, result.getWords());
  }

  @Test
  void sortsSentencesByCaseInsensitiveLetterCount() {
    // given
    CompositeTextComponent text = PARSER.parse(SORT_SOURCE);

    // when
    List<String> sortedSentences = SENTENCE_SORT_TASK.execute(text);

    // then
    assertEquals(EXPECTED_SORTED_SENTENCES, sortedSentences);
  }

  @Test
  void swapsFirstAndLastLexemesWithoutChangingSentenceLength() {
    // given
    CompositeTextComponent text = PARSER.parse(SWAP_SOURCE);

    // when
    String result = SWAP_LEXEMES_TASK.execute(text);

    // then
    assertEquals("three. two One Hi!", result);
    assertEquals(SWAP_SOURCE.length(), result.length());
  }

  @Test
  void countsUnicodeLettersAndCodePoints() {
    // given
    CompositeTextComponent text = PARSER.parse(STATISTICS_SOURCE);

    // when
    TextStatistics statistics = STATISTICS_SERVICE.calculate(text);

    // then
    assertEquals(2, statistics.getLetterCount());
    assertEquals(5, statistics.getCharacterCount());
  }
}
