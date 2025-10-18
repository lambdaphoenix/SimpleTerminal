package io.github.lambdaphoenix.simpleterminal.prompt;

import io.github.lambdaphoenix.simpleterminal.core.ValidationException;

/**
 * Represents a selectable option in a prompt.
 *
 * <p>A {@code Choice} consists of a display label and an associated value. It is used in
 * selection-based prompts to present options to the user and return a corresponding result. Labels
 * must not be {@code null} or blank. Values must not be {@code null}.
 *
 * <h2>Usage Example:</h2>
 *
 * <pre>{@code
 * Choice<Integer> c = new Choice<>("Option A", 1);
 * }</pre>
 *
 * @param <T> the type of the value associated with this choice
 * @param label the display label shown to the user (must not be {@code null} or blank)
 * @param value the value returned when this choice is selected (must not be {@code null})
 * @author lambdaphoenix
 * @version 0.2.0 (2025-10-18)
 * @since 0.1.0
 */
public record Choice<T>(String label, T value) {
  /**
   * Constructs a new {@code Choice} with the given label and value.
   *
   * <p>The label must be non-null and contain at least one non-whitespace character. The value must
   * not be {@code null}.
   *
   * @param label the display label shown to the user
   * @param value the value returned when this choice is selected
   * @throws ValidationException if the label is {@code null} or blank, or if the value is {@code
   *     null}
   */
  public Choice {
    if (label == null || label.isBlank()) {
      throw new ValidationException("Choice label cannot be empty");
    }
    if (value == null) {
      throw new ValidationException("Choice value cannot be null");
    }
  }
}
