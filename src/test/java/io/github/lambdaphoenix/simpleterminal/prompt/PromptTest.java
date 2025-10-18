package io.github.lambdaphoenix.simpleterminal.prompt;

import io.github.lambdaphoenix.simpleterminal.core.ConsoleBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PromptTest {
  private ByteArrayOutputStream out;
  private ConsoleBuilder cb;

  @BeforeEach
  void setUp() {
    out = new ByteArrayOutputStream();
    cb = new ConsoleBuilder(new PrintWriter(out, true));
    cb.useFallback(false);
  }

  @Test
  void ask() throws IOException {
    Prompt prompt = new Prompt(cb, new BufferedReader(new StringReader("hello\n")));
    String answer = prompt.ask("Your name?");
    assertEquals("hello", answer);
    assertTrue(out.toString().contains("Your name?"));
  }

  @Test
  void testAsk() throws IOException {
    String input = "wrong\nok\n";
    Prompt prompt = new Prompt(cb, new BufferedReader(new StringReader(input)));
    String result = prompt.ask("Enter ok", s -> s.equals("ok"), "error");
    assertEquals("ok", result);
    assertTrue(out.toString().contains("error"));
  }

  @Test
  void askInt() throws IOException {
    Prompt prompt = new Prompt(cb, new BufferedReader(new StringReader("42\n")));
    int result = prompt.askInt("Enter number");
    assertEquals(42, result);
  }

  @Test
  void testAskInt() throws IOException {
    String input = "abc\n2\n42\n";
    Prompt prompt = new Prompt(cb, new BufferedReader(new StringReader(input)));
    int result = prompt.askInt("Enter int", i -> i > 5, "too small");
    assertEquals(42, result);
    assertTrue(out.toString().contains("too small") || out.toString().contains("invalidInt"));
  }

  @Test
  void askYesNo() throws IOException {
    Prompt promptYes = new Prompt(cb, new BufferedReader(new StringReader("y\n")));
    assertTrue(promptYes.askYesNo("Continue?"));

    Prompt promptNo = new Prompt(cb, new BufferedReader(new StringReader("n\n")));
    assertFalse(promptNo.askYesNo("Continue?"));
  }

  @Test
  void askChoice() throws IOException {
    List<Choice<String>> choices = List.of(
        new Choice<>("One", "1"),
        new Choice<>("Two", "2")
    );
    Prompt prompt = new Prompt(cb, new BufferedReader(new StringReader("2\n")));
    String result = prompt.askChoice("Pick one", choices);
    assertEquals("2", result);
    assertTrue(out.toString().contains("1) One"));
    assertTrue(out.toString().contains("2) Two"));
  }

  @Test
  void askPattern() throws IOException {
    String input = "abc\n123\n";
    Prompt prompt = new Prompt(cb, new BufferedReader(new StringReader(input)));
    String result = prompt.askPattern("Enter digits", "\\d+", "not digits");
    assertEquals("123", result);
    assertTrue(out.toString().contains("not digits"));
  }

  @Test
  void askMapped() throws IOException {
    String input = "foo\n42\n";
    Prompt prompt = new Prompt(cb, new BufferedReader(new StringReader(input)));
    int result = prompt.askMapped("Enter int", Integer::parseInt, "bad int");
    assertEquals(42, result);
    assertTrue(out.toString().contains("bad int"));
  }

  @Test
  void cb() {
    Prompt prompt = new Prompt(cb, new BufferedReader(new StringReader("x\n")));
    assertSame(cb, prompt.cb());
  }

  @Test
  void br() {
    BufferedReader br = new BufferedReader(new StringReader("x\n"));
    Prompt prompt = new Prompt(cb, br);
    assertSame(br, prompt.br());
  }
}
