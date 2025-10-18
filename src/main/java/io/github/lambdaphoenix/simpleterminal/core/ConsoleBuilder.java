package io.github.lambdaphoenix.simpleterminal.core;

import io.github.lambdaphoenix.simpleterminal.ansi.AnsiColor;
import io.github.lambdaphoenix.simpleterminal.ansi.AnsiStyle;
import io.github.lambdaphoenix.simpleterminal.box.BoxStyle;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * Provides a fluent API for building styled console output.
 *
 * <p>The {@code ConsoleBuilder} class allows the creation of formatted text with ANSI colors, styles, indentation, rules, and boxed content. The {@code ConsoleBuilder} accumulates formatted text in an internal buffer and writes it to a configurable {@link java.io.PrintWriter}. By default, it writes to {@code System.out}, but alternative output streams can be injected for testing or custom environments.
 *
 * <h2>Usage Example:</h2>
 *
 * <pre>{@code
 * ConsoleBuilder cb = new ConsoleBuilder();
 * cb
 *  .useFallback(false)
 *  .locale(Locale.ENGLISH)
 *  .color(AnsiColor.GREEN)
 *  .style(AnsiStyle.BOLD)
 *  .text("Hello, world!")
 *  .reset()
 *  .println();
 * }</pre>
 *
 * @author lambdaphoenix
 * @version 0.2.0 (2025-10-18)
 * @since 0.1.0
 */
public class ConsoleBuilder {
  /** Internal buffer used to accumulate console output before printing or returning as a string. */
  private final StringBuilder buf = new StringBuilder();

  /**
   * The writer used to emit console output. Defaults to {@code System.out}, but can be replaced for testing or redirection.
   *
   * @since 0.2.0
   */
  private final PrintWriter out;

  /** Current rule width for horizontal separators. */
  private int ruleWidth;

  /** Current unit of indentation (e.g. spaces or tab). */
  private String indentUnit;

  /** Resource bundle for localized messages. */
  private ResourceBundle resources;

  /** Current default box style for framed content. */
  private BoxStyle boxStyle;

  /**
   * Weather to allow {@link java.util.ResourceBundle} fallback for this builder instance.
   *
   * @since 0.2.0
   */
  private boolean useFallback;

  /** Current indentation level (non-negative). */
  private int indent = 0;

  /**
   * Creates a new {@code ConsoleBuilder} writing to {@code System.out}.
   */
  public ConsoleBuilder() {
    this(new PrintWriter(System.out, true));
  }
  /**
   * Creates a new {@code ConsoleBuilder} writing to the given output writer.
   *
   * @param out the writer to use for output (must not be {@code null})
   * @since 0.2.0
   */
  public ConsoleBuilder(PrintWriter out) {
    this.out = java.util.Objects.requireNonNull(out, "Output writer must not be null");
    this.ruleWidth = ConsoleConfig.DEFAULT_RULE_WIDTH;
    this.indentUnit = ConsoleConfig.DEFAULT_INDENT_UNIT;
    this.useFallback = ConsoleConfig.DEFAULT_USE_FALLBACK;
    this.locale(ConsoleConfig.DEFAULT_LOCALE);
    this.boxStyle = ConsoleConfig.DEFAULT_BOX_STYLE;
  }

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
   * <p>If {@link #useFallback(boolean)} is set to {@code true}, Java's normal fallback chain is
   * used, which may load bundles for the system default locale. If set to {@code false}, only the
   * requested locale and the base bundle {@code messages.properties} are considered.
   *
   * @param locale the locale to use
   * @return this builder for chaining
   */
  public ConsoleBuilder locale(Locale locale) {
    if (this.useFallback) {
      this.resources = ResourceBundle.getBundle("messages", locale);
    } else {
      ResourceBundle.Control noFallback =
          ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_DEFAULT);
      this.resources = ResourceBundle.getBundle("messages", locale, noFallback);
    }
    return this;
  }

  /**
   * Controls whether this builder instance allows {@link java.util.ResourceBundle} to fall back to
   * the JVM default locale.
   *
   * <p>If {@code true} (default, inherited from {@link ConsoleConfig#DEFAULT_USE_FALLBACK}), Java's
   * normal fallback chain is used. If {@code false}, only the requested locale and the base bundle
   * {@code messages.properties} are considered. If neither exists, a {@code
   * java.util.MissingResourceException} is thrown.
   *
   * <p>This setting overrides the global default for this builder instance only.
   *
   * <p><b>Important:</b> This setting only takes effect the next time {@link #locale(Locale)} is
   * called. Changing it does not retroactively reload an already loaded bundle.
   *
   * @param useFallback whether to allow fallback
   * @return this builder for chaining
   * @since 0.2.0
   */
  public ConsoleBuilder useFallback(boolean useFallback) {
    this.useFallback = useFallback;
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
   * @throws NullPointerException if {@code content} is {@code null}
   * @return this builder for chaining
   */
  public ConsoleBuilder box(String title, String content, BoxStyle style) {
    Objects.requireNonNull(content, "Content must not be null");
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
   * Executes the given action if the condition is true.
   *
   * <p>This allows conditional building of console output in a fluent style.
   *
   * @param condition the condition to evaluate
   * @param action the action that is executed if {@code condition} is true
   * @return this builder for chaining
   */
  public ConsoleBuilder when(boolean condition, Consumer<ConsoleBuilder> action) {
    if (condition) action.accept(this);
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

  /** Prints the accumulated output without a newline and clears the buffer. */
  public void print() {
    out.print(this.build());
    out.flush();
    this.clear();
  }

  /**
   * Appends a newline, prints the accumulated output, and clears the buffer.
   */
  public void println() {
    this.newline();
    this.print();
  }
}
