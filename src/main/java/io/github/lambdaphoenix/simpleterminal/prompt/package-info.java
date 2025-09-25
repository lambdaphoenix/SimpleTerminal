/**
 * Provides interactive prompt utilities for console-based user input.
 *
 * <p>This package contains classes for building structured prompts, validating user input, and
 * presenting selectable choices. It is designed to support internationalization, styled output, and
 * flexible input handling.
 *
 * <h2>Key Components:</h2>
 *
 * <ul>
 *   <li>{@link io.github.lambdaphoenix.simpleterminal.prompt.Prompt} – main class for interactive
 *       input
 *   <li>{@link io.github.lambdaphoenix.simpleterminal.prompt.Choice} – generic option wrapper for
 *       selection prompts
 * </ul>
 *
 * <h2>Usage Example:</h2>
 *
 * <pre>{@code
 * List<Choice<String>> options = List.of(
 *     new Choice<>("Yes", "y"),
 *     new Choice<>("No", "n")
 * );
 * String result = prompt.askChoice("Continue?", options);
 * }</pre>
 *
 * @author lambdaphoenix
 * @version 2025-09-25
 * @since 0.1.0
 */
package io.github.lambdaphoenix.simpleterminal.prompt;
