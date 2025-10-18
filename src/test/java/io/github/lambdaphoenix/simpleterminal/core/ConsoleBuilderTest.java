package io.github.lambdaphoenix.simpleterminal.core;

import static org.junit.jupiter.api.Assertions.*;

import io.github.lambdaphoenix.simpleterminal.ansi.AnsiColor;
import io.github.lambdaphoenix.simpleterminal.ansi.AnsiStyle;
import io.github.lambdaphoenix.simpleterminal.box.BoxStyle;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsoleBuilderTest {
  private ByteArrayOutputStream out;
  private ConsoleBuilder cb;

  @BeforeEach
  void setUp() {
    out = new ByteArrayOutputStream();
    cb = new ConsoleBuilder(new PrintWriter(out, true));
    cb.useFallback(false);
  }

  @Test
  void msg_locale() {
    cb.locale(Locale.ENGLISH);
    String msg = cb.msg("error.invalidInt");
    assertNotNull(msg);
    assertEquals("Invalid number, please try again.", msg);
    cb.locale(Locale.GERMAN);
    msg = cb.msg("error.invalidInt");
    assertEquals("Ungültige Zahl, bitte erneut versuchen.", msg);
  }

  @Test
  void ruleWidth() {
    cb.ruleWidth(42);
    cb.rule('-').print();
    assertEquals("-".repeat(42) + System.lineSeparator(), out.toString());
  }

  @Test
  void indent_indentUnit() {
    cb.indentUnit("--").indent(2).line("X");
    assertEquals("--".repeat(2) + "X" + System.lineSeparator(), cb.build());
    cb.clear().indentUnit("*").indent(1).line("X").print();
    assertEquals("*X" + System.lineSeparator(), out.toString());
  }

  @Test
  void box_boxStyle() {
    cb.boxStyle(BoxStyle.ASCII).box(null, "Hello").print();
    assertTrue(out.toString().contains("+--") && out.toString().contains("|"));
  }

  @Test
  void color() {
    cb.color(AnsiColor.RED).text("red").reset().print();
    String output = out.toString();
    assertTrue(output.contains("red"));
    assertTrue(output.contains("\u001B"));
  }

  @Test
  void bg() {
    cb.bg(AnsiColor.BLUE).text("bg").reset().print();
    String output = out.toString();
    assertTrue(output.contains("bg"));
    assertTrue(output.contains("\u001B"));
  }

  @Test
  void style() {
    cb.style(AnsiStyle.BOLD).text("bold").reset().print();
    String output = out.toString();
    assertTrue(output.contains("bold"));
    assertTrue(output.contains("\u001B"));
  }

  @Test
  void reset() {
    cb.color(AnsiColor.GREEN).reset().text("ok").print();
    String output = out.toString();
    assertTrue(output.contains("ok"));
    assertTrue(output.contains("\u001B"));
  }

  @Test
  void text() {
    cb.text("abc").print();
    assertEquals("abc", out.toString());
  }

  @Test
  void space() {
    cb.text("a").space().text("b").print();
    assertEquals("a b", out.toString());
  }

  @Test
  void newline() {
    cb.text("line").newline().print();
    assertEquals("line" + System.lineSeparator(), out.toString());
  }

  @Test
  void line() {
    cb.line("hello").print();
    assertEquals("hello" + System.lineSeparator(), out.toString());
  }

  @Test
  void linef() {
    cb.linef("num: %d", 42).print();
    assertEquals("num: 42" + System.lineSeparator(), out.toString());
  }

  @Test
  void when() {
    cb.when(true, b -> b.text("test")).print();
    assertEquals("test", out.toString());

    out.reset();
    cb.when(false, b -> b.text("no")).print();
    assertEquals("", out.toString());
  }

  @Test
  void build() {
    cb.text("abc");
    assertEquals("abc", cb.build());
  }

  @Test
  void clear() {
    cb.text("abc").clear().print();
    assertEquals("", out.toString());
  }

  @Test
  void print() {
    cb.text("abc").print();
    assertEquals("abc", out.toString());
  }

  @Test
  void println() {
    cb.text("abc").println();
    assertEquals("abc" + System.lineSeparator(), out.toString());
  }
}
