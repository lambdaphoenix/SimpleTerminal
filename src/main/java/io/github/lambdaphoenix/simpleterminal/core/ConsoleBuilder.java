package io.github.lambdaphoenix.simpleterminal.core;

import io.github.lambdaphoenix.simpleterminal.ansi.AnsiColor;
import io.github.lambdaphoenix.simpleterminal.ansi.AnsiStyle;
import io.github.lambdaphoenix.simpleterminal.box.BoxStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Supplier;

/**
 * Provides a fluent API for building styled console output.
 *
 * <p>The {@code ConsoleBuilder} class allows the creation of formatted text with ANSI colors,
 * styles, indentation, rules, and boxed content. Output can be accumulated in an internal buffer
 * and then printed to the console.
 *
 * <h2>Usage Example:</h2>
 *
 * <pre>{@code
 * ConsoleBuilder cb = new ConsoleBuilder();
 * cb.color(AnsiColor.GREEN)
 *   .style(AnsiStyle.BOLD)
 *   .text("Hello, world!")
 *   .reset()
 *   .println();
 * }</pre>
 *
 * @author lambdaphoenix
 * @version 2025-09-25
 * @since 0.1.0
 */
public class ConsoleBuilder {
  /** Internal buffer used to accumulate console output before printing or returning as a string. */
  private final StringBuilder buf = new StringBuilder();

  /** Current rule width for horizontal separators. */
  private int ruleWidth = ConsoleConfig.DEFAULT_RULE_WIDTH;

  /** Current unit of indentation (e.g. spaces or tab). */
  private String indentUnit = ConsoleConfig.DEFAULT_INDENT_UNIT;

  /** Current locale for message lookup. */
  private Locale locale = ConsoleConfig.DEFAULT_LOCALE;

  /** Resource bundle for localized messages. */
  private ResourceBundle resources = ResourceBundle.getBundle("messages", this.locale);

  /** Current default box style for framed content. */
  private BoxStyle boxStyle = ConsoleConfig.DEFAULT_BOX_STYLE;

  /** Current indentation level (non-negative). */
  private int indent = 0;

  /**
   * Returns the maximum string length in an array of lines.
   *
   * @param lines the array of strings
   * @return the maximum length among the strings, or 0 if empty
   */
  private static int maxLen(String[] lines) {
    int m = 0;
    for (String s : lines) m = Math.max(m, s.length());
    return m;
  }

  /**
   * Pads a string with trailing spaces to reach a given length.
   *
   * @param s the string to pad
   * @param len the target length
   * @return the padded string, or the original string if already long enough
   */
  private static String pad(String s, int len) {
    int diff = len - s.length();
    return diff > 0 ? s + " ".repeat(diff) : s;
  }

  /**
   * Returns a localized message from the resource bundle.
   *
   * @param key the message key
   * @return the localized message
   */
  public String msg(String key) {
    return resources.getString(key);
  }

  /**
   * Sets the default rule width for horizontal separators.
   *
   * @param ruleWidth the rule width, must be greater than 0
   * @return this builder for chaining
   * @throws IllegalArgumentException if {@code ruleWidth} is not positive
   */
  public ConsoleBuilder ruleWidth(int ruleWidth) {
    if (ruleWidth <= 0) throw new IllegalArgumentException("Width must be > 0");
    this.ruleWidth = ruleWidth;
    return this;
  }

  /**
   * Sets the locale for message lookup.
   *
   * @param locale the locale to use
   * @return this builder for chaining
   */
  public ConsoleBuilder locale(Locale locale) {
    this.locale = locale;
    this.resources = ResourceBundle.getBundle("messages", this.locale);
    return this;
  }

  /**
   * Sets the unit of indentation.
   *
   * @param indentUnit the string used for one indentation level
   * @return this builder for chaining
   * @throws IllegalArgumentException if {@code indentUnit} is null or empty
   */
  public ConsoleBuilder indentUnit(String indentUnit) {
    if (indentUnit == null || indentUnit.isEmpty())
      throw new IllegalArgumentException("Indent unit cannot be empty");
    this.indentUnit = indentUnit;
    return this;
  }

  /**
   * Sets the current indentation level.
   *
   * @param levels the number of indentation levels (non-negative)
   * @return this builder for chaining
   */
  public ConsoleBuilder indent(int levels) {
    this.indent = Math.max(0, levels);
    return this;
  }

  /**
   * Sets the default box style for framed content.
   *
   * @param boxStyle the box style to use
   * @return this builder for chaining
   */
  public ConsoleBuilder boxStyle(BoxStyle boxStyle) {
    this.boxStyle = boxStyle;
    return this;
  }

  /**
   * Returns the current indentation string.
   *
   * <p>The indentation is computed by repeating the configured {@code indentUnit} for the current
   * {@code indent} level.
   *
   * @return the current indentation string
   */
  private String currentIndent() {
    return this.indentUnit.repeat(this.indent);
  }

  /**
   * Appends a foreground color escape code.
   *
   * @param color the ANSI color
   * @return this builder for chaining
   */
  public ConsoleBuilder color(AnsiColor color) {
    this.buf.append(color.fg());
    return this;
  }

  /**
   * Appends a background color escape code.
   *
   * @param bgColor the ANSI background color
   * @return this builder for chaining
   */
  public ConsoleBuilder bg(AnsiColor bgColor) {
    this.buf.append(bgColor.bg());
    return this;
  }

  /**
   * Appends a text style escape code.
   *
   * @param style the ANSI style
   * @return this builder for chaining
   */
  public ConsoleBuilder style(AnsiStyle style) {
    this.buf.append(style);
    return this;
  }

  /**
   * Resets all colors and styles.
   *
   * @return this builder for chaining
   */
  public ConsoleBuilder reset() {
    this.buf.append(AnsiColor.RESET);
    return this;
  }

  /**
   * Appends plain text.
   *
   * @param text the text to append
   * @return this builder for chaining
   */
  public ConsoleBuilder text(String text) {
    this.buf.append(text);
    return this;
  }

  /**
   * Appends a single space.
   *
   * @return this builder for chaining
   */
  public ConsoleBuilder space() {
    this.buf.append(' ');
    return this;
  }

  /**
   * Appends a newline.
   *
   * @return this builder for chaining
   */
  public ConsoleBuilder newline() {
    this.buf.append(System.lineSeparator());
    return this;
  }

  /**
   * Appends a line of text with current indentation.
   *
   * @param text the text to append
   * @return this builder for chaining
   */
  public ConsoleBuilder line(String text) {
    this.buf.append(this.currentIndent()).append(text).append(System.lineSeparator());
    return this;
  }

  /**
   * Appends a formatted line of text with current indentation.
   *
   * @param format the format string
   * @param args the arguments for the format string
   * @return this builder for chaining
   */
  public ConsoleBuilder linef(String format, Object... args) {
    return this.line(String.format(format, args));
  }

  /**
   * Appends a horizontal rule using the default width.
   *
   * @param ch the character to repeat
   * @return this builder for chaining
   */
  public ConsoleBuilder rule(char ch) {
    return this.rule(ch, this.ruleWidth);
  }

  /**
   * Appends a horizontal rule with a specified width.
   *
   * @param ch the character to repeat
   * @param width the width of the rule
   * @return this builder for chaining
   */
  public ConsoleBuilder rule(char ch, int width) {
    this.buf
        .append(this.currentIndent())
        .append(String.valueOf(ch).repeat(Math.max(1, width)))
        .append(System.lineSeparator());
    return this;
  }

  /**
   * Appends a boxed section with a title and content using the default box style.
   *
   * @param title the box title (may be null or blank)
   * @param content the box content
   * @return this builder for chaining
   */
  public ConsoleBuilder box(String title, String content) {
    return this.box(title, content, this.boxStyle);
  }

  /**
   * Appends a boxed section with a title and content using a specific box style.
   *
   * @param title the box title (may be null or blank)
   * @param content the box content
   * @param style the box style to use
   * @return this builder for chaining
   */
  public ConsoleBuilder box(String title, String content, BoxStyle style) {
    String[] lines = content.split("\\R", -1);
    int max =
        title != null && !title.isBlank() ? Math.max(title.length(), maxLen(lines)) : maxLen(lines);
    int inner = Math.max(0, max);
    String horizontalLine = style.horizontal().repeat(inner + 2);
    this.buf
        .append(this.currentIndent())
        .append(style.topLeft())
        .append(horizontalLine)
        .append(style.topRight())
        .append(System.lineSeparator());

    if (title != null && !title.isBlank()) {
      this.buf
          .append(this.currentIndent())
          .append(style.vertical())
          .append(" ")
          .append(ConsoleBuilder.pad(title, inner))
          .append(" ")
          .append(style.vertical())
          .append(System.lineSeparator());

      this.buf
          .append(this.currentIndent())
          .append(style.junctionLeft())
          .append(style.junctionHorizontal().repeat(inner + 2))
          .append(style.junctionRight())
          .append(System.lineSeparator());
    }

    for (String l : lines) {
      this.buf
          .append(this.currentIndent())
          .append(style.vertical())
          .append(" ")
          .append(ConsoleBuilder.pad(l, inner))
          .append(" ")
          .append(style.vertical())
          .append(System.lineSeparator());
    }

    this.buf
        .append(this.currentIndent())
        .append(style.bottomLeft())
        .append(horizontalLine)
        .append(style.bottomRight())
        .append(System.lineSeparator());

    return this;
  }

  /**
   * Executes the given supplier if the condition is true.
   * <p>
   * This allows conditional building of console output in a fluent style.
   * </p>
   *
   * @param condition the condition to evaluate
   * @param then the supplier that is executed if {@code condition} is true
   * @return this builder for chaining
   */
  public ConsoleBuilder when(boolean condition, Supplier<ConsoleBuilder> then) {
    if (condition) then.get();
    return this;
  }

  /**
   * Builds the accumulated output as a string.
   *
   * @return the built string
   */
  public String build() {
    return this.buf.toString();
  }

  /**
   * Clears the internal buffer.
   *
   * @return this builder for chaining
   */
  public ConsoleBuilder clear() {
    this.buf.setLength(0);
    return this;
  }

  /** Prints the accumulated output to {@code System.out} and clears the buffer. */
  public void print() {
    System.out.print(this.build());
    this.clear();
  }

  /**
   * Appends a newline, prints the accumulated output to {@code System.out}, and clears the buffer.
   */
  public void println() {
    this.newline();
    this.print();
  }

  /**
   * Creates a new {@code ConsoleBuilder} with default configuration values.
   */
  public ConsoleBuilder() {}
}
