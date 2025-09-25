package io.github.lambdaphoenix.simpleterminal.core;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Locale;
import org.junit.jupiter.api.Test;

class ConsoleConfigTest {

  @Test
  void testDefaults() {
    assertEquals(80, ConsoleConfig.DEFAULT_RULE_WIDTH);
    assertEquals("  ", ConsoleConfig.DEFAULT_INDENT_UNIT);
    assertEquals(Locale.ENGLISH, ConsoleConfig.DEFAULT_LOCALE);
  }

  @Test
  void loadDefaults() { // TODO test if working
    ConsoleConfig.loadDefaults();
    assertNotNull(ConsoleConfig.DEFAULT_INDENT_UNIT);
  }
}
