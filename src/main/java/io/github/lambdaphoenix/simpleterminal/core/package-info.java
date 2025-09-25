/**
 * Provides core classes for console configuration, building, and validation.
 *
 * <p>This package contains the foundational classes of the SimpleTerminal library. It includes the
 * {@code ConsoleBuilder} for styled console output, the {@code ConsoleConfig} for managing global
 * defaults, and the {@code ValidationException} for signaling invalid input or configuration. These
 * classes form the backbone of higher-level features such as prompts, box rendering, and
 * internationalized messages.
 *
 * <h2>Key Components:</h2>
 *
 * <ul>
 *   <li>{@link io.github.lambdaphoenix.simpleterminal.core.ConsoleBuilder} - fluent API for styled
 *       console output
 *   <li>{@link io.github.lambdaphoenix.simpleterminal.core.ConsoleConfig} - global configuration
 *       defaults for console behavior
 *   <li>{@link io.github.lambdaphoenix.simpleterminal.core.ValidationException} - runtime exception
 *       for validation errors
 * </ul>
 *
 * <h2>Usage Example:</h2>
 *
 * <pre>{@code
 * ConsoleBuilder cb = new ConsoleBuilder();
 * cb.color(AnsiColor.GREEN).text("Hello, world!").reset().println();
 *
 * ConsoleConfig.DEFAULT_RULE_WIDTH = 100;
 * }</pre>
 *
 * @author lambdaphoenix
 * @version 2025-09-25
 * @since 0.1.0
 */
package io.github.lambdaphoenix.simpleterminal.core;
