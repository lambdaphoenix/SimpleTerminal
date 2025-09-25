package io.github.lambdaphoenix.simpleterminal.box;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BoxStyleTest {
  @Test
  void testAsciiBoxStyle() {
    assertEquals("+", BoxStyle.ASCII.topLeft());
    assertEquals("-", BoxStyle.ASCII.horizontal());
  }

  @Test
  void fromName() {
    assertEquals(BoxStyle.UNICODE, BoxStyle.fromName("unicode"));
    assertEquals(BoxStyle.ASCII, BoxStyle.fromName("unknown"));
  }
}
