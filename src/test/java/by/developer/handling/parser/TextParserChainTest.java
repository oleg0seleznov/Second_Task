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

  @Test
  void breaksCustomTextIntoTypedComponentsWithTheirContent() {
    // given
    String source = "Hi, 42!\n\nA?";

    // when
    CompositeTextComponent text = PARSER.parse(source);

    // then
    assertEquals(CompositeType.TEXT, text.getType());
    assertEquals(source, text.getContent());
    List<TextComponent> textChildren = text.getChildren();
    CompositeTextComponent firstParagraph =
        assertInstanceOf(CompositeTextComponent.class, textChildren.get(0));
    assertEquals(CompositeType.PARAGRAPH, firstParagraph.getType());
    assertEquals("Hi, 42!", firstParagraph.getContent());

    CompositeTextComponent firstSentence =
        assertInstanceOf(CompositeTextComponent.class, firstParagraph.getChildren().get(0));
    assertEquals(CompositeType.SENTENCE, firstSentence.getType());
    assertEquals("Hi, 42!", firstSentence.getContent());

    CompositeTextComponent firstLexeme =
        assertInstanceOf(CompositeTextComponent.class, firstSentence.getChildren().get(0));
    assertEquals(CompositeType.LEXEME, firstLexeme.getType());
    assertEquals("Hi,", firstLexeme.getContent());
    CompositeTextComponent firstWord =
        assertInstanceOf(CompositeTextComponent.class, firstLexeme.getChildren().get(0));
    assertEquals(CompositeType.WORD, firstWord.getType());
    assertEquals("Hi", firstWord.getContent());
        Letter firstLetter = assertInstanceOf(Letter.class, firstWord.getChildren().get(0));
        assertEquals("H", firstLetter.getContent());
        Letter secondLetter = assertInstanceOf(Letter.class, firstWord.getChildren().get(1));
        assertEquals("i", secondLetter.getContent());
    GrammarSign comma =
        assertInstanceOf(GrammarSign.class, firstLexeme.getChildren().get(1));
    assertEquals(",", comma.getContent());

    assertEquals(" ", firstSentence.getChildren().get(1).getContent());
    CompositeTextComponent numberLexeme =
        assertInstanceOf(CompositeTextComponent.class, firstSentence.getChildren().get(2));
    assertEquals(CompositeType.LEXEME, numberLexeme.getType());
    assertEquals("42!", numberLexeme.getContent());
    TextLeaf four = assertInstanceOf(TextLeaf.class, numberLexeme.getChildren().get(0));
    assertEquals("4", four.getContent());
    TextLeaf two = assertInstanceOf(TextLeaf.class, numberLexeme.getChildren().get(1));
    assertEquals("2", two.getContent());
    GrammarSign exclamationMark =
        assertInstanceOf(
            GrammarSign.class, numberLexeme.getChildren().get(2));
    assertEquals("!", exclamationMark.getContent());

    TextLeaf paragraphSeparator =
        assertInstanceOf(TextLeaf.class, textChildren.get(1));
    assertEquals("\n\n", paragraphSeparator.getContent());
    CompositeTextComponent secondParagraph =
        assertInstanceOf(CompositeTextComponent.class, textChildren.get(2));
    assertEquals(CompositeType.PARAGRAPH, secondParagraph.getType());
    assertEquals("A?", secondParagraph.getContent());
    CompositeTextComponent secondSentence =
        assertInstanceOf(CompositeTextComponent.class, secondParagraph.getChildren().get(0));
    assertEquals(CompositeType.SENTENCE, secondSentence.getType());
    CompositeTextComponent finalLexeme =
        assertInstanceOf(CompositeTextComponent.class, secondSentence.getChildren().get(0));
    assertEquals(CompositeType.LEXEME, finalLexeme.getType());
    assertEquals("A?", finalLexeme.getContent());
    CompositeTextComponent finalWord =
        assertInstanceOf(CompositeTextComponent.class, finalLexeme.getChildren().get(0));
    assertEquals(CompositeType.WORD, finalWord.getType());
    assertEquals("A", finalWord.getContent());
    Letter finalLetter = assertInstanceOf(Letter.class, finalWord.getChildren().get(0));
    assertEquals("A", finalLetter.getContent());
    GrammarSign questionMark =
        assertInstanceOf(GrammarSign.class, finalLexeme.getChildren().get(1));
    assertEquals("?", questionMark.getContent());
  }
}
