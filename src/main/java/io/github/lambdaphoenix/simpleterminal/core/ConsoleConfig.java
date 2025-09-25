package io.github.lambdaphoenix.simpleterminal.core;

import io.github.lambdaphoenix.simpleterminal.box.BoxStyle;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

/**
 * Provides global configuration defaults for the SimpleTerminal library.
 *
 * <p>The {@code ConsoleConfig} class defines default values for console rendering, including rule
 * width, indentation unit, locale, and box style. These defaults can be customized programmatically
 * or loaded from a {@code simpleterminal.properties} file on the classpath.
 *
 * <h2>Usage Example:</h2>
 *
 * <pre>{@code
 * // Change defaults programmatically
 * ConsoleConfig.DEFAULT_RULE_WIDTH = 100;
 * ConsoleConfig.DEFAULT_INDENT_UNIT = "  ";
 * ConsoleConfig.DEFAULT_LOCALE = Locale.GERMAN;
 * ConsoleConfig.DEFAULT_BOX_STYLE = BoxStyle.DOUBLE;
 *
 * // Or load from properties file
 * ConsoleConfig.loadDefaults();
 * }</pre>
 *
 * @author lambdaphoenix
 * @version 2025-09-25
 * @since 0.1.0
 */
public final class ConsoleConfig {
  /**
   * The default width for horizontal rules.
   *
   * <p>This value is used by console rendering components when drawing horizontal separators.
   */
  public static int DEFAULT_RULE_WIDTH = 80;

  /**
   * The default unit of indentation.
   *
   * <p>Typically a sequence of spaces or a tab character. Used by {@code ConsoleBuilder} when
   * indenting output.
   */
  public static String DEFAULT_INDENT_UNIT = "  ";

  /**
   * The default locale for internationalized messages.
   *
   * <p>Determines which resource bundle is used for localized output.
   */
  public static Locale DEFAULT_LOCALE = Locale.ENGLISH;

  /**
   * The default box style for framed content.
   *
   * <p>Defines which {@link BoxStyle} is used when no explicit style is provided.
   */
  public static BoxStyle DEFAULT_BOX_STYLE = BoxStyle.UNICODE;

  /** Private constructor to prevent instantiation. */
  private ConsoleConfig() {}

  /**
   * Loads default configuration values from a {@code simpleterminal.properties} file.
   *
   * <p>The file is expected to be located on the classpath. Supported keys:
   *
   * <ul>
   *   <li>{@code rule.width} - integer value for rule width
   *   <li>{@code indent.unit} - string value for indentation unit
   *   <li>{@code locale} - BCP 47 language tag (e.g. {@code en}, {@code de-DE})
   *   <li>{@code box.style} - name of a predefined {@link BoxStyle}
   * </ul>
   *
   * If the file is not found, defaults remain unchanged.
   *
   * @throws NumberFormatException if {@code rule.width} is not a valid integer
   */
  public static void loadDefaults() {
    try (InputStream in = ConsoleConfig.class.getResourceAsStream("/simpleterminal.properties")) {
      if (in == null) return;
      Properties props = new Properties();
      props.load(in);

      if (props.containsKey("rule.width"))
        DEFAULT_RULE_WIDTH = Integer.parseInt(props.getProperty("rule.width"));
      if (props.containsKey("indent.unit")) DEFAULT_INDENT_UNIT = props.getProperty("indent.unit");
      if (props.containsKey("locale"))
        DEFAULT_LOCALE = Locale.forLanguageTag(props.getProperty("locale"));
      if (props.containsKey("box.style")) {
        DEFAULT_BOX_STYLE = BoxStyle.fromName(props.getProperty("box.style"));
      }
    } catch (IOException _) {
    }
  }
}
