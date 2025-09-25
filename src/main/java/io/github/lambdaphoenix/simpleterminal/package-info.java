/**
 * Root package of the SimpleTerminal library.
 *
 * <p>The SimpleTerminal library provides a fluent API for styled console output, interactive
 * prompts, and internationalized messages. It is organized into several subpackages:
 *
 * <h2>Subpackages:</h2>
 *
 * <ul>
 *   <li>{@link io.github.lambdaphoenix.simpleterminal.ansi} - ANSI colors and text styles
 *   <li>{@link io.github.lambdaphoenix.simpleterminal.box} - predefined box styles
 *   <li>{@link io.github.lambdaphoenix.simpleterminal.core} - core classes such as {@code
 *       ConsoleBuilder}, {@code ConsoleConfig}, and {@code ValidationException}
 *   <li>{@link io.github.lambdaphoenix.simpleterminal.prompt} - interactive prompts and choices
 * </ul>
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
 *
 * Prompt prompt = new Prompt(cb);
 * boolean cont = prompt.askYesNo("Continue?");
 * }</pre>
 *
 * @author lambdaphoenix
 * @version 2025-09-25
 * @since 0.1.0
 */
package io.github.lambdaphoenix.simpleterminal;
