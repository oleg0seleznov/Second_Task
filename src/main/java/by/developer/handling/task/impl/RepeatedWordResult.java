package by.developer.handling.task.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepeatedWordResult {
  private final int sentenceCount;
  private final List<String> words;

  public RepeatedWordResult(int sentenceCount, List<String> words) {
    this.sentenceCount = sentenceCount;
    this.words = Collections.unmodifiableList(new ArrayList<>(words));
  }

  public int getSentenceCount() {
    return sentenceCount;
  }

  public List<String> getWords() {
    return words;
  }
}
