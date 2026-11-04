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

  @Override 
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof TextLeaf)) {
      return false;
    }
    TextLeaf other = (TextLeaf) obj;
    return content.equals(other.content);
  }
}
