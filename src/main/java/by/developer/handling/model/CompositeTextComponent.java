package by.developer.handling.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CompositeTextComponent implements TextComponent {
  private final CompositeType type;
  private final List<TextComponent> children = new ArrayList<>();

  public CompositeTextComponent(CompositeType type) {
    this.type = type;
  }

  public CompositeType getType() {
    return type;
  }

  public void add(TextComponent component) {
    children.add(component);
  }

  public List<TextComponent> getChildren() {
    return Collections.unmodifiableList(children);
  }

  @Override
  public String getContent() {
    StringBuilder content = new StringBuilder();
    for (TextComponent child : children) {
      content.append(child.getContent());
    }
    return content.toString();
  }

  @Override 
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof CompositeTextComponent)) {
      return false;
    }
    CompositeTextComponent other = (CompositeTextComponent) obj;
    return type == other.type && children.equals(other.children);
  }
}
