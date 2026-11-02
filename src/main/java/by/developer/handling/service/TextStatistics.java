package by.developer.handling.service;

public class TextStatistics {
  private final int letterCount;
  private final int characterCount;

  public TextStatistics(int letterCount, int characterCount) {
    this.letterCount = letterCount;
    this.characterCount = characterCount;
  }

  public int getLetterCount() {
    return letterCount;
  }

  public int getCharacterCount() {
    return characterCount;
  }
}
