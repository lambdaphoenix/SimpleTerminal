package io.github.lambdaphoenix.simpleterminal.ansi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AnsiStyleTest {

  @Test
  void testBoldCode() {
    assertEquals("\u001B[1m", AnsiStyle.BOLD.toString());
  }

  @Test
  void testResetAll() {
    assertEquals("\u001B[0m", AnsiStyle.RESET_ALL.toString());
  }
}
