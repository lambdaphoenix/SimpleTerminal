package io.github.lambdaphoenix.simpleterminal.prompt;

import io.github.lambdaphoenix.simpleterminal.core.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChoiceTest {
  @Test
  void label() {
    Choice<Integer> c = new Choice<>("Option A", 1);
    assertEquals("Option A", c.label());

    assertThrows(ValidationException.class, () -> new Choice<>(null, 1));

    assertThrows(ValidationException.class, () -> new Choice<>("", 1));

    assertThrows(ValidationException.class, () -> new Choice<>("   ", 1));
  }

  @Test
  void value() {
    Choice<String> c = new Choice<>("Option B", "value");
    assertEquals("value", c.value());

    assertThrows(ValidationException.class, () -> new Choice<>("Option C", null));
  }
}
