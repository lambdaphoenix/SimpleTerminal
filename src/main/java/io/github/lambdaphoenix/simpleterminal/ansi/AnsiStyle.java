package io.github.lambdaphoenix.simpleterminal.ansi;

/**
 * Enumeration of common ANSI text styles.
 *
 * <p>Each constant represents an ANSI escape code for a text style such as bold, italic, underline,
 * or reset. Styles can be combined with colors from {@link AnsiColor} to produce rich console
 * output.
 *
 * <h2>Usage Example:</h2>
 *
 * <pre>{@code
 * System.out.println(AnsiStyle.BOLD + "Bold text" + AnsiStyle.RESET_ALL);
 * System.out.println(AnsiStyle.UNDERLINE + AnsiColor.BLUE.fg() + "Link" + AnsiColor.RESET);
 * }</pre>
 *
 * @author lambdaphoenix
 * @version 2025-09-25
 * @since 0.1.0
 */
public enum AnsiStyle {
  /** Bold text. */
  BOLD("\u001B[1m"),
  /** Dim or faint text. */
  DIM("\u001B[2m"),
  /** Italic text. */
  ITALIC("\u001B[3m"),
  /** Underlined text. */
  UNDERLINE("\u001B[4m"),
  /** Inverted foreground/background colors. */
  INVERT("\u001B[7m"),
  /** Strikethrough text. */
  STRIKETHROUGH("\u001B[9m"),
  /** Double underline text. */
  DOUBLE_UNDERLINE("\u001B[21m"),

  /** Reset all styles and colors. */
  RESET_ALL("\u001B[0m"),
  /** Reset bold and dim. */
  RESET_BOLD_DIM("\u001B[22m"),
  /** Reset italic. */
  RESET_ITALIC("\u001B[23m"),
  /** Reset underline. */
  RESET_UNDERLINE("\u001B[24m"),
  /** Reset invert. */
  RESET_INVERT("\u001B[27m"),
  /** Reset strikethrough. */
  RESET_STRIKETHROUGH("\u001B[29m");

  private final String code;

  /**
   * Creates a new {@code AnsiStyle} with the given escape code.
   *
   * @param code the ANSI escape code string
   */
  AnsiStyle(String code) {
    this.code = code;
  }

  /**
   * Returns the ANSI escape code string for this style.
   *
   * @return the ANSI escape code
   */
  @Override
  public String toString() {
    return code;
  }
}
