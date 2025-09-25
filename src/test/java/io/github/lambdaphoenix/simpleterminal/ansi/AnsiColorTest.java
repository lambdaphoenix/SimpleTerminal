package io.github.lambdaphoenix.simpleterminal.ansi;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import org.junit.jupiter.api.Test;

class AnsiColorTest {

  @Test
  void from256() {
    AnsiColor c = AnsiColor.from256(200);
    assertTrue(c.fg().contains("38;5;200"));
    assertTrue(c.bg().contains("48;5;200"));
  }

  @Test
  void fromRgb() {
    AnsiColor c = AnsiColor.fromRgb(10, 20, 30);
    assertTrue(c.fg().contains("38;2;10;20;30"));
    c = AnsiColor.fromRgb(new Color(255, 100, 100));
    assertTrue(c.bg().contains("48;2;255;100;100"));
  }

  @Test
  void fg() {
    assertEquals("\u001B[31m", AnsiColor.RED.fg());
  }

  @Test
  void bg() {
    assertEquals("\u001B[41m", AnsiColor.RED.bg());
  }
}
