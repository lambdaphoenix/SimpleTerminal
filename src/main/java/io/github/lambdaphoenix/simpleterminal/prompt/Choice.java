package io.github.lambdaphoenix.simpleterminal.prompt;

import io.github.lambdaphoenix.simpleterminal.core.ValidationException;

/**
 * Represents a selectable option in a prompt.
 *
 * <p>A {@code Choice} consists of a display label and an associated value. It is used in
 * selection-based prompts to present options to the user and return a corresponding result. Labels
 * must be non-null and non-blank.
 *
 * <h2>Usage Example:</h2>
 *
 * <pre>{@code
 * Choice<Integer> c = new Choice<>("Option A", 1);
 * }</pre>
 *
 * @param <T> the type of the value associated with this choice
 * @param label the display label shown to the user
 * @param value the value returned when this choice is selected
 * @author lambdaphoenix
 * @version 2025-09-25
 * @since 0.1.0
 */
public record Choice<T>(String label, T value) {
  /**
   * Constructs a new {@code Choice} with the given label and value.
   *
   * <p>The label must be non-null and contain at least one non-whitespace character.
   *
   * @param label the display label shown to the user
   * @param value the value returned when this choice is selected
   * @throws ValidationException if the label is null or blank
   */
  public Choice {
    if (label == null || label.isBlank()) {
      throw new ValidationException("Choice label cannot be empty");
    }
  }
}
