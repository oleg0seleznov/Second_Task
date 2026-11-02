package by.developer.handling.task.impl;

import by.developer.handling.model.CompositeTextComponent;
import by.developer.handling.model.CompositeType;
import by.developer.handling.model.TextComponent;
import by.developer.handling.task.TextTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class MaxRepeatedWordSentencesTask implements TextTask<RepeatedWordResult> {
  @Override
  public RepeatedWordResult execute(CompositeTextComponent text) {
    Map<String, Integer> sentenceCounts = new HashMap<>();
    collectSentenceCounts(text, sentenceCounts);

    int maximum = 0;
    for (int count : sentenceCounts.values()) {
      maximum = Math.max(maximum, count);
    }

    Set<String> mostCommonWords = new TreeSet<>();
    for (Map.Entry<String, Integer> entry : sentenceCounts.entrySet()) {
      if (entry.getValue() == maximum && maximum > 0) {
        mostCommonWords.add(entry.getKey());
      }
    }
    return new RepeatedWordResult(maximum, new ArrayList<>(mostCommonWords));
  }

  private void collectSentenceCounts(
      CompositeTextComponent component, Map<String, Integer> sentenceCounts) {
    for (TextComponent child : component.getChildren()) {
      if (child instanceof CompositeTextComponent
          && ((CompositeTextComponent) child).getType() == CompositeType.SENTENCE) {
        Set<String> wordsInSentence = new HashSet<>();
        collectWords((CompositeTextComponent) child, wordsInSentence);
        for (String word : wordsInSentence) {
          sentenceCounts.put(word, sentenceCounts.getOrDefault(word, 0) + 1);
        }
      } else if (child instanceof CompositeTextComponent) {
        collectSentenceCounts((CompositeTextComponent) child, sentenceCounts);
      }
    }
  }

  private void collectWords(CompositeTextComponent component, Set<String> words) {
    for (TextComponent child : component.getChildren()) {
      if (child instanceof CompositeTextComponent
          && ((CompositeTextComponent) child).getType() == CompositeType.WORD) {
        String wordContent = child.getContent();
        String normalizedWord = wordContent.toLowerCase(Locale.ROOT);
        words.add(normalizedWord);
      } else if (child instanceof CompositeTextComponent) {
        collectWords((CompositeTextComponent) child, words);
      }
    }
  }
}
