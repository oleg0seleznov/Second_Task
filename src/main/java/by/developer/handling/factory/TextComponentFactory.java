package by.developer.handling.factory;

import by.developer.handling.model.CompositeTextComponent;
import by.developer.handling.model.CompositeType;
import by.developer.handling.model.GrammarSign;
import by.developer.handling.model.Letter;
import by.developer.handling.model.TextLeaf;

public class TextComponentFactory {
  public CompositeTextComponent createComposite(CompositeType type) {
    return new CompositeTextComponent(type);
  }

  public Letter createLetter(String content) {
    return new Letter(content);
  }

  public GrammarSign createGrammarSign(String content) {
    return new GrammarSign(content);
  }

  public TextLeaf createTextLeaf(String content) {
    return new TextLeaf(content);
  }
}
