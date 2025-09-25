package io.github.lambdaphoenix.simpleterminal.ansi;

/**
 * Represents an ANSI color with both foreground and background escape codes.
 *
 * <p>Provides constants for the 16 standard ANSI colors (8 normal and 8 bright), as well as factory
 * methods for 256-color mode and true color (24-bit RGB). Each {@code AnsiColor} instance can
 * return its foreground ({@link #fg()}) and background ({@link #bg()}) escape codes.
 *
 * <h2>Usage Example:</h2>
 *
 * <pre>{@code
 * System.out.println(AnsiColor.RED.fg() + "Error" + AnsiColor.RESET);
 * System.out.println(AnsiColor.from256(202).fg() + "Orange" + AnsiColor.RESET);
 * System.out.println(AnsiColor.fromRgb(255, 128, 0).bg() + "Custom background" + AnsiColor.RESET);
 * }</pre>
 *
 * @author lambdaphoenix
 * @version 2025-09-25
 * @since 0.1.0
 */
public final class AnsiColor {

  /**
   * ANSI escape code to reset all colors and styles.
   *
   * <p>This code clears any foreground color, background color, and text styling previously
   * applied, restoring the terminal to its default appearance.
   */
  public static final String RESET = "\u001B[0m";

  /** ANSI black foreground and background color. */
  public static final AnsiColor BLACK = new AnsiColor("\u001B[30m", "\u001B[40m");

  /** ANSI red foreground and background color. */
  public static final AnsiColor RED = new AnsiColor("\u001B[31m", "\u001B[41m");

  /** ANSI green foreground and background color. */
  public static final AnsiColor GREEN = new AnsiColor("\u001B[32m", "\u001B[42m");

  /** ANSI yellow foreground and background color. */
  public static final AnsiColor YELLOW = new AnsiColor("\u001B[33m", "\u001B[43m");

  /** ANSI blue foreground and background color. */
  public static final AnsiColor BLUE = new AnsiColor("\u001B[34m", "\u001B[44m");

  /** ANSI magenta foreground and background color. */
  public static final AnsiColor MAGENTA = new AnsiColor("\u001B[35m", "\u001B[45m");

  /** ANSI cyan foreground and background color. */
  public static final AnsiColor CYAN = new AnsiColor("\u001B[36m", "\u001B[46m");

  /** ANSI white foreground and background color. */
  public static final AnsiColor WHITE = new AnsiColor("\u001B[37m", "\u001B[47m");

  /** ANSI bright black foreground and background color. */
  public static final AnsiColor BRIGHT_BLACK = new AnsiColor("\u001B[90m", "\u001B[100m");

  /** ANSI bright red foreground and background color. */
  public static final AnsiColor BRIGHT_RED = new AnsiColor("\u001B[91m", "\u001B[101m");

  /** ANSI bright green foreground and background color. */
  public static final AnsiColor BRIGHT_GREEN = new AnsiColor("\u001B[92m", "\u001B[102m");

  /** ANSI bright yellow foreground and background color. */
  public static final AnsiColor BRIGHT_YELLOW = new AnsiColor("\u001B[93m", "\u001B[103m");

  /** ANSI bright blue foreground and background color. */
  public static final AnsiColor BRIGHT_BLUE = new AnsiColor("\u001B[94m", "\u001B[104m");

  /** ANSI bright magenta foreground and background color. */
  public static final AnsiColor BRIGHT_MAGENTA = new AnsiColor("\u001B[95m", "\u001B[105m");

  /** ANSI bright cyan foreground and background color. */
  public static final AnsiColor BRIGHT_CYAN = new AnsiColor("\u001B[96m", "\u001B[106m");

  /** ANSI bright white foreground and background color. */
  public static final AnsiColor BRIGHT_WHITE = new AnsiColor("\u001B[97m", "\u001B[107m");

  private final String fgCode;
  private final String bgCode;

  /**
   * Creates a new {@code AnsiColor} with the given foreground and background codes.
   *
   * @param fgCode the ANSI escape code for the foreground
   * @param bgCode the ANSI escape code for the background
   */
  AnsiColor(String fgCode, String bgCode) {
    this.fgCode = fgCode;
    this.bgCode = bgCode;
  }

  /**
   * Creates an {@code AnsiColor} from a 256-color palette index.
   *
   * @param code the color index (0–255)
   * @return a new {@code AnsiColor} instance
   * @throws IllegalArgumentException if the code is outside 0–255
   */
  public static AnsiColor from256(int code) {
    if (code < 0 || code > 255) throw new IllegalArgumentException("256-color code must be 0–255");
    return new AnsiColor("\u001B[38;5;" + code + "m", "\u001B[48;5;" + code + "m");
  }

  /**
   * Creates an {@code AnsiColor} from RGB values (true color).
   *
   * @param r red component (0–255)
   * @param g green component (0–255)
   * @param b blue component (0–255)
   * @return a new {@code AnsiColor} instance
   * @throws IllegalArgumentException if any component is outside 0–255
   */
  public static AnsiColor fromRgb(int r, int g, int b) {
    if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255)
      throw new IllegalArgumentException("RGB values 0–255");
    return new AnsiColor(
        "\u001B[38;2;" + r + ";" + g + ";" + b + "m", "\u001B[48;2;" + r + ";" + g + ";" + b + "m");
  }

  /**
   * Creates an {@code AnsiColor} from an {@link java.awt.Color}.
   *
   * @param color the AWT color
   * @return a new {@code AnsiColor} instance
   * @see #fromRgb(int, int, int)
   */
  public static AnsiColor fromRgb(java.awt.Color color) {
    return AnsiColor.fromRgb(color.getRed(), color.getGreen(), color.getBlue());
  }

  /**
   * Returns the ANSI escape code for the foreground color.
   *
   * @return the foreground escape code
   */
  public String fg() {
    return fgCode;
  }

  /**
   * Returns the ANSI escape code for the background color.
   *
   * @return the background escape code
   */
  public String bg() {
    return bgCode;
  }
}
