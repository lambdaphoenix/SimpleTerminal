package io.github.lambdaphoenix.simpleterminal.core;

/**
 * Thrown to indicate that a validation check has failed.
 *
 * <p>This exception is used throughout the SimpleTerminal library to signal invalid user input or
 * configuration values. It extends {@link RuntimeException}, so it is unchecked and does not need
 * to be declared in method signatures.
 *
 * <h2>Usage Example:</h2>
 *
 * <pre>{@code
 * if (label == null || label.isBlank()) {
 *     throw new ValidationException("Choice label cannot be empty");
 * }
 * }</pre>
 *
 * @author lamdaphoenix
 * @version 2025-09-25
 * @since 0.1.0
 */
public class ValidationException extends RuntimeException {
  /**
   * Constructs a new {@code ValidationException} with the specified detail message.
   *
   * @param message the detail message describing the validation failure
   */
  public ValidationException(String message) {
    super(message);
  }
}
