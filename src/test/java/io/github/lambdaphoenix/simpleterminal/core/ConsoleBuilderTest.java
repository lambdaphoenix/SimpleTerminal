package io.github.lambdaphoenix.simpleterminal.core;

import static org.junit.jupiter.api.Assertions.*;

import io.github.lambdaphoenix.simpleterminal.ansi.AnsiColor;
import io.github.lambdaphoenix.simpleterminal.ansi.AnsiStyle;
import io.github.lambdaphoenix.simpleterminal.box.BoxStyle;
import java.util.Locale;
import org.junit.jupiter.api.Test;

class ConsoleBuilderTest {

  @Test
  void msg() {
    ConsoleBuilder cb = new ConsoleBuilder();
    String msg = cb.msg("error.invalidInt");
    assertNotNull(msg);
  }

  @Test
  void ruleWidth() {
    ConsoleBuilder cb = new ConsoleBuilder();
    cb.ruleWidth(50);
    String output = cb.rule('-').build();
    assertEquals(output, "-".repeat(50) + System.lineSeparator());
    assertThrows(IllegalArgumentException.class, () -> cb.ruleWidth(0));
  }

  @Test
  void locale() {
    ConsoleBuilder cb = new ConsoleBuilder();
    cb.locale(Locale.ENGLISH);
    // TODO

  }

  @Test
  void indentUnit() {
    ConsoleBuilder cb = new ConsoleBuilder();
    cb.indentUnit(">>");
    cb.indent(1).line("test");
    assertTrue(cb.build().startsWith(">>"));
    assertThrows(IllegalArgumentException.class, () -> cb.indentUnit(""));
  }

  @Test
  void indent() {
    ConsoleBuilder cb = new ConsoleBuilder();
    cb.indent(2).line("X");
    String out = cb.build();
    assertTrue(out.startsWith(ConsoleConfig.DEFAULT_INDENT_UNIT.repeat(2)));
  }

  @Test
  void boxStyle() {
    ConsoleBuilder cb = new ConsoleBuilder();
    cb.boxStyle(BoxStyle.DOUBLE).box("T", "C");
    String out = cb.build();
    assertTrue(out.contains("╔"));
  }

  @Test
  void color() {
    ConsoleBuilder cb = new ConsoleBuilder();
    cb.color(AnsiColor.RED).text("X");
    String out = cb.build();
    assertTrue(out.contains(AnsiColor.RED.fg()));
  }

  @Test
  void bg() {
    ConsoleBuilder cb = new ConsoleBuilder();
    cb.bg(AnsiColor.BLUE).text("X");
    String out = cb.build();
    assertTrue(out.contains(AnsiColor.BLUE.bg()));
  }

  @Test
  void style() {
    ConsoleBuilder cb = new ConsoleBuilder();
    cb.style(AnsiStyle.BOLD).text("X");
    String out = cb.build();
    assertTrue(out.contains(AnsiStyle.BOLD.toString()));
  }

  @Test
  void reset() {
    ConsoleBuilder cb = new ConsoleBuilder();
    cb.color(AnsiColor.RED).reset().text("X");
    String out = cb.build();
    assertTrue(out.contains(AnsiColor.RESET));
  }

  @Test
  void text() {
    ConsoleBuilder cb = new ConsoleBuilder();
    cb.text("Hello");
    assertTrue(cb.build().contains("Hello"));
  }

  @Test
  void space() {
    ConsoleBuilder cb = new ConsoleBuilder();
    cb.text("A").space().text("B");
    assertEquals("A B", cb.build());
  }

  @Test
  void newline() {
    ConsoleBuilder cb = new ConsoleBuilder();
    cb.text("A").newline().text("B");
    String out = cb.build();
    assertTrue(out.contains(System.lineSeparator()));
  }

  @Test
  void line() {
    ConsoleBuilder cb = new ConsoleBuilder();
    cb.indent(1).line("Test");
    String out = cb.build();
    assertTrue(out.endsWith("Test" + System.lineSeparator()));
  }

  @Test
  void linef() {
    ConsoleBuilder cb = new ConsoleBuilder();
    cb.linef("Hello %s", "World");
    assertTrue(cb.build().contains("Hello World"));
  }

  @Test
  void rule() {
    ConsoleBuilder cb = new ConsoleBuilder();
    cb.rule('*');
    String out = cb.build();
    assertTrue(out.contains("*".repeat(ConsoleConfig.DEFAULT_RULE_WIDTH)));
  }

  @Test
  void testRule() {
    ConsoleBuilder cb = new ConsoleBuilder();
    cb.rule('#', 5);
    assertEquals("#####" + System.lineSeparator(), cb.build());
  }

  @Test
  void box() {
    ConsoleBuilder cb = new ConsoleBuilder();
    cb.box("Title", "Line1\nLine2", BoxStyle.ASCII);
    String out = cb.build();
    assertTrue(out.contains("Title"));
    assertTrue(out.contains("Line1"));
    assertTrue(out.contains("Line2"));

    cb = new ConsoleBuilder();
    cb.box("", "Content", BoxStyle.ASCII);
    String output = cb.build();
    assertFalse(output.contains("Title"));
    assertTrue(output.contains("+"));
    ConsoleConfig.DEFAULT_BOX_STYLE = BoxStyle.UNICODE;
    cb = new ConsoleBuilder();
    cb.box("T", "C");
    assertTrue(cb.build().contains("┌"));
  }

  @Test
  void when() {
    ConsoleBuilder cb = new ConsoleBuilder();
    cb.when(true, () -> cb.text("X"));
    assertTrue(cb.build().contains("X"));
    cb.clear();
    cb.when(false, () -> cb.text("Y"));
    assertFalse(cb.build().contains("Y"));
  }

  @Test
  void build() {
    ConsoleBuilder cb = new ConsoleBuilder();
    cb.text("ABC");
    assertEquals("ABC", cb.build());
  }

  @Test
  void clear() {
    ConsoleBuilder cb = new ConsoleBuilder();
    cb.text("ABC").clear();
    assertEquals("", cb.build());
  }

  @Test
  void print() {
    ConsoleBuilder cb = new ConsoleBuilder();
    cb.text("Hello");
    cb.print();
    assertEquals("", cb.build());
  }

  @Test
  void println() {
    ConsoleBuilder cb = new ConsoleBuilder();
    cb.text("Hello");
    cb.println();
    assertEquals("", cb.build());
  }
}
