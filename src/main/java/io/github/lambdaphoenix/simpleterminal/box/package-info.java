/**
 * Provides predefined box styles for rendering framed content in the terminal.
 *
 * <p>This package contains the {@link io.github.lambdaphoenix.simpleterminal.box.BoxStyle} record,
 * which defines the visual appearance of boxes using Unicode or ASCII characters. Each style
 * specifies corner characters, horizontal and vertical lines, and junction characters for
 * separators. These styles are used by console rendering components to display structured output
 * such as prompts, messages, or tables.
 *
 * <h2>Available Styles:</h2>
 *
 * <ul>
 *   <li>{@code ASCII} – simple plus, minus, and pipe characters
 *   <li>{@code UNICODE} – single-line Unicode box drawing characters
 *   <li>{@code DOUBLE} – double-line Unicode box drawing characters
 *   <li>{@code ROUNDED} – rounded corners with Unicode
 *   <li>{@code HEAVY} – bold, heavy lines
 *   <li>{@code BLOCK} – solid block characters
 *   <li>{@code MINIMAL} – whitespace and vertical bars only
 * </ul>
 *
 * <h2>Key Components:</h2>
 *
 * <ul>
 *   <li>{@link io.github.lambdaphoenix.simpleterminal.box.BoxStyle} – record defining box styles
 * </ul>
 *
 * <h2>Usage Example:</h2>
 *
 * <pre>{@code
 * BoxStyle style = BoxStyle.DOUBLE;
 * System.out.println(style.topLeft() + style.horizontal().repeat(20) + style.topRight());
 * }</pre>
 *
 * @author lambdaphoenix
 * @version 2025-09-25
 * @since 0.1.0
 */
package io.github.lambdaphoenix.simpleterminal.box;
