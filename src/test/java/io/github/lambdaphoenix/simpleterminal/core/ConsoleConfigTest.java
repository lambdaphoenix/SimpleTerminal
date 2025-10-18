package io.github.lambdaphoenix.simpleterminal.core;

import static org.junit.jupiter.api.Assertions.*;

import io.github.lambdaphoenix.simpleterminal.box.BoxStyle;
import java.util.Locale;
import org.junit.jupiter.api.Test;

class ConsoleConfigTest {

  @Test
  void loadDefaults() {
    ConsoleConfig.loadDefaults();

    assertEquals(99, ConsoleConfig.DEFAULT_RULE_WIDTH);
    assertEquals("..", ConsoleConfig.DEFAULT_INDENT_UNIT);
    assertEquals(Locale.FRENCH, ConsoleConfig.DEFAULT_LOCALE);
    assertEquals(BoxStyle.ASCII, ConsoleConfig.DEFAULT_BOX_STYLE);
    assertTrue(ConsoleConfig.DEFAULT_USE_FALLBACK);
  }
}