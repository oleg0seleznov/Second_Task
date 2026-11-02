package by.developer.handling.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import by.developer.handling.model.CompositeTextComponent;
import by.developer.handling.model.CompositeType;
import by.developer.handling.model.GrammarSign;
import by.developer.handling.model.Letter;
import by.developer.handling.model.TextComponent;
import by.developer.handling.model.TextLeaf;
import java.util.List;
import org.junit.jupiter.api.Test;

class TextParserChainTest {
  private static final String SOURCE = "Первая строка.\tВторая строка!\n\nНовый абзац.";
  private static final TextParserChain PARSER = new TextParserChain();

  @Test
  void restoresOriginalTextAndBuildsCompositeHierarchy() {
    // given
    String source = SOURCE;

    // when
    CompositeTextComponent text = PARSER.parse(source);

    // then
    assertEquals(source, text.getContent());
    List<TextComponent> textChildren = text.getChildren();
    CompositeTextComponent paragraph =
        assertInstanceOf(CompositeTextComponent.class, textChildren.get(0));
    assertEquals(CompositeType.PARAGRAPH, paragraph.getType());
    List<TextComponent> paragraphChildren = paragraph.getChildren();
    CompositeTextComponent sentence =
        assertInstanceOf(CompositeTextComponent.class, paragraphChildren.get(0));
    assertEquals(CompositeType.SENTENCE, sentence.getType());
    List<TextComponent> sentenceChildren = sentence.getChildren();
    CompositeTextComponent lexeme =
        assertInstanceOf(CompositeTextComponent.class, sentenceChildren.get(0));
    assertEquals(CompositeType.LEXEME, lexeme.getType());
    List<TextComponent> lexemeChildren = lexeme.getChildren();
    CompositeTextComponent word =
        assertInstanceOf(CompositeTextComponent.class, lexemeChildren.get(0));
    assertEquals(CompositeType.WORD, word.getType());
    List<TextComponent> wordChildren = word.getChildren();
    Letter letter = assertInstanceOf(Letter.class, wordChildren.get(0));
    assertInstanceOf(TextLeaf.class, letter);
    CompositeTextComponent secondLexeme =
        assertInstanceOf(CompositeTextComponent.class, sentenceChildren.get(2));
    List<TextComponent> secondLexemeChildren = secondLexeme.getChildren();
    TextComponent grammarSign =
        secondLexemeChildren.get(secondLexemeChildren.size() - 1);
    GrammarSign parsedGrammarSign = assertInstanceOf(GrammarSign.class, grammarSign);
    assertInstanceOf(TextLeaf.class, parsedGrammarSign);
  }
}
