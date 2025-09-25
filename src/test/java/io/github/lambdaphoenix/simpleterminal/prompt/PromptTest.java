package io.github.lambdaphoenix.simpleterminal.prompt;

import static org.junit.jupiter.api.Assertions.*;

import io.github.lambdaphoenix.simpleterminal.core.ConsoleBuilder;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class PromptTest {

  @Test
  void ask() {}

  @Test
  void testAsk() {}

  @Test
  void askInt() throws IOException {
    String input = "42\n";
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    Prompt prompt = new Prompt(new ConsoleBuilder());
    int result = prompt.askInt("Enter number:");
    assertEquals(42, result);
  }

  @Test
  void testAskInt() {}

  @Test
  void askYesNo() {}

  @Test
  void askChoice() {}

  @Test
  void askPattern() {}

  @Test
  void askMapped() {}

  @Test
  void cb() {}
}
