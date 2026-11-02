package by.developer.handling.task.impl;

import by.developer.handling.model.CompositeTextComponent;
import by.developer.handling.model.CompositeType;
import by.developer.handling.model.TextComponent;
import by.developer.handling.task.TextTask;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class SentenceSortTask implements TextTask<List<String>> {
  private final char targetLetter;

  public SentenceSortTask(char targetLetter) {
    this.targetLetter = Character.toLowerCase(targetLetter);
  }

  @Override
  public List<String> execute(CompositeTextComponent text) {
    List<CompositeTextComponent> sentences = new ArrayList<>();
    collectSentences(text, sentences);
    sentences.sort(Comparator.comparingInt(this::countTargetLetter));

    List<String> result = new ArrayList<>();
    for (CompositeTextComponent sentence : sentences) {
      result.add(sentence.getContent());
    }
    return result;
  }

  private void collectSentences(
      CompositeTextComponent component, List<CompositeTextComponent> sentences) {
    for (TextComponent child : component.getChildren()) {
      if (child instanceof CompositeTextComponent
          && ((CompositeTextComponent) child).getType() == CompositeType.SENTENCE) {
        sentences.add((CompositeTextComponent) child);
      } else if (child instanceof CompositeTextComponent) {
        collectSentences((CompositeTextComponent) child, sentences);
      }
    }
  }

  private int countTargetLetter(CompositeTextComponent sentence) {
    int count = 0;
    String content = sentence.getContent();
    IntStream codePointStream = content.codePoints();
    int[] codePoints = codePointStream.toArray();
    for (int codePoint : codePoints) {
      if (Character.toLowerCase(codePoint) == targetLetter) {
        count++;
      }
    }
    return count;
  }
}
