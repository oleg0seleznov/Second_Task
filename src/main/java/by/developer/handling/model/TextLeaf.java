package by.developer.handling.model;

public class TextLeaf implements TextComponent {
  private final String content;

  public TextLeaf(String content) {
    this.content = content;
  }

  @Override
  public String getContent() {
    return content;
  }
}
