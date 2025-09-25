package io.github.lambdaphoenix.simpleterminal.box;

/**
 * Represents a visual box style using Unicode or ASCII characters.
 *
 * <p>A {@code BoxStyle} defines the characters used to render borders around boxed content in the
 * console. It includes corner characters, horizontal and vertical lines, and junction characters
 * for separators. Several predefined styles are available, including ASCII, Unicode, double
 * lines,rounded corners, heavy lines, block characters, and minimal spacing.
 *
 * <h2>Usage Example:</h2>
 *
 * <pre>{@code
 * BoxStyle style = BoxStyle.ROUNDED;
 * System.out.println(style.topLeft() + style.horizontal().repeat(10) + style.topRight());
 * }</pre>
 *
 * @param topLeft the character used for the top-left corner
 * @param topRight the character used for the top-right corner
 * @param bottomLeft the character used for the bottom-left corner
 * @param bottomRight the character used for the bottom-right corner
 * @param horizontal the character used for horizontal lines
 * @param vertical the character used for vertical lines
 * @param junctionLeft the character used for junctions on the left side
 * @param junctionRight the character used for junctions on the right side
 * @param junctionHorizontal the character used for horizontal junction lines
 *
 * @author lambdaphoenix
 * @version 2025-09-25
 * @since 0.1.0
 */
public record BoxStyle(
    String topLeft,
    String topRight,
    String bottomLeft,
    String bottomRight,
    String horizontal,
    String vertical,
    String junctionLeft,
    String junctionRight,
    String junctionHorizontal) {

  /** ASCII box style using plus, minus, and pipe characters. */
  public static final BoxStyle ASCII = new BoxStyle("+", "+", "+", "+", "-", "|", "+", "+", "-");

  /** Unicode box style with single-line borders. */
  public static final BoxStyle UNICODE = new BoxStyle("┌", "┐", "└", "┘", "─", "│", "├", "┤", "─");

  /** Unicode box style with double-line borders. */
  public static final BoxStyle DOUBLE = new BoxStyle("╔", "╗", "╚", "╝", "═", "║", "╠", "╣", "═");

  /** Unicode box style with rounded corners. */
  public static final BoxStyle ROUNDED = new BoxStyle("╭", "╮", "╰", "╯", "─", "│", "├", "┤", "─");

  /** Unicode box style with heavy (bold) lines. */
  public static final BoxStyle HEAVY = new BoxStyle("┏", "┓", "┗", "┛", "━", "┃", "┣", "┫", "━");

  /** Box style using full block characters for solid borders. */
  public static final BoxStyle BLOCK = new BoxStyle("█", "█", "█", "█", "█", "█", "█", "█", "█");

  /** Minimal box style using whitespace and vertical bars only. */
  public static final BoxStyle MINIMAL = new BoxStyle(" ", " ", " ", " ", " ", "|", " ", " ", " ");

  /**
   * Returns a predefined {@code BoxStyle} matching the given name.
   *
   * <p>The name is case-insensitive. If the name is unknown or {@code null}, the ASCII style is
   * returned as a fallback.
   *
   * @param name the name of the style (e.g. "UNICODE", "DOUBLE", "HEAVY")
   * @return the matching {@code BoxStyle}, or {@link #ASCII} if not found
   */
  public static BoxStyle fromName(String name) {
    if (name == null) return ASCII;
    return switch (name.toUpperCase()) {
      case "UNICODE" -> UNICODE;
      case "DOUBLE" -> DOUBLE;
      case "ROUNDED" -> ROUNDED;
      case "HEAVY" -> HEAVY;
      case "BLOCK" -> BLOCK;
      case "MINIMAL" -> MINIMAL;
      default -> ASCII;
    };
  }
}
