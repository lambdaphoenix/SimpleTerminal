/**
 * Provides classes and enums for ANSI escape code handling.
 *
 * <p>This package contains utilities to apply ANSI colors and text styles to console output. It
 * supports the 16 standard ANSI colors, 256-color mode, and true color (24-bit RGB). It also
 * includes common text styles such as bold, italic, underline, and reset codes.
 *
 * <h2>Key Components:</h2>
 *
 * <ul>
 *   <li>{@link io.github.lambdaphoenix.simpleterminal.ansi.AnsiColor} – foreground and background
 *       colors
 *   <li>{@link io.github.lambdaphoenix.simpleterminal.ansi.AnsiStyle} – text styles such as bold,
 *       italic, underline
 * </ul>
 *
 * <h2>Usage Example:</h2>
 *
 * <pre>{@code
 * System.out.println(AnsiColor.RED.fg() + "Error!" + AnsiColor.RESET);
 * System.out.println(AnsiStyle.BOLD + "Bold text" + AnsiStyle.RESET_ALL);
 * }</pre>
 *
 * @author lambdaphoenix
 * @version 2025-09-25
 * @since 0.1.0
 */
package io.github.lambdaphoenix.simpleterminal.ansi;
