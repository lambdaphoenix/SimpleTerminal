package io.github.lambdaphoenix.simpleterminal.prompt;

import io.github.lambdaphoenix.simpleterminal.ansi.AnsiColor;
import io.github.lambdaphoenix.simpleterminal.core.ConsoleBuilder;
import io.github.lambdaphoenix.simpleterminal.core.ValidationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Provides interactive console prompts for user input.
 *
 * <p>The {@code Prompt} class supports various input types including strings, integers, yes/no
 * confirmations, pattern matching, and selectable choices. It uses a {@link ConsoleBuilder} for
 * styled output and supports validation and error messaging. All input is read from {@code
 * System.in}.
 *
 * <h2>Usage Example:</h2>
 *
 * <pre>{@code
 * Prompt prompt = new Prompt(new ConsoleBuilder());
 * int age = prompt.askInt("Enter your age:");
 * }</pre>
 *
 * @param cb the console builder used for styled output
 * @author lambdaphoenix
 * @version 2025-09-25
 * @since 0.1.0
 */
public record Prompt(ConsoleBuilder cb) {
  /**
   * Shared buffered reader for reading user input from {@code System.in}.
   *
   * <p>This reader is used internally by all prompt methods to capture console input line by line.
   */
  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  /**
   * Prints an error message in red to the console.
   *
   * <p>This method uses the associated {@link ConsoleBuilder} to apply red foreground color, output
   * the message, reset styles, and append a line break. It is intended for internal use by
   * validation loops.
   *
   * @param errorMessage the error message text to display
   */
  private void printErrorMessage(String errorMessage) {
    this.cb.color(AnsiColor.RED).text(errorMessage).reset().println();
  }

  /**
   * Asks the user a plain text question and returns the input.
   *
   * @param question the question to display
   * @return the user's input as a string
   * @throws IOException if an I/O error occurs
   */
  public String ask(String question) throws IOException {
    this.cb.color(AnsiColor.CYAN).text(question + " ").reset().print();
    return br.readLine();
  }

  /**
   * Asks the user a question and validates the input using a predicate. Repeats until the input
   * passes validation.
   *
   * @param question the question to display
   * @param validator the predicate to validate input
   * @param errorMessage the message to display on invalid input
   * @return the validated input
   * @throws IOException if an I/O error occurs
   * @throws NullPointerException if validator is null
   * @see #ask(String)
   */
  public String ask(String question, Predicate<String> validator, String errorMessage)
      throws IOException {
    Objects.requireNonNull(validator);
    while (true) {
      String s = this.ask(question);
      if (validator.test(s)) return s;
      this.printErrorMessage(errorMessage);
    }
  }

  /**
   * Asks the user for an integer input. Repeats until a valid integer is entered.
   *
   * @param question the question to display
   * @return the parsed integer
   * @throws IOException if an I/O error occurs
   * @see #ask(String)
   */
  public int askInt(String question) throws IOException {
    while (true) {
      try {
        return Integer.parseInt(this.ask(question).trim());
      } catch (NumberFormatException _) {
        this.printErrorMessage(this.cb.msg("error.invalidInt"));
      }
    }
  }

  /**
   * Asks the user for an integer input and validates it. Repeats until the input passes validation.
   *
   * @param question the question to display
   * @param validator the predicate to validate the integer
   * @param errorMessage the message to display on invalid input
   * @return the validated integer
   * @throws IOException if an I/O error occurs
   * @throws NullPointerException if validator is null
   * @see #askInt(String)
   */
  public int askInt(String question, Predicate<Integer> validator, String errorMessage)
      throws IOException {
    Objects.requireNonNull(validator);
    while (true) {
      int v = this.askInt(question);
      if (validator.test(v)) return v;
      this.printErrorMessage(errorMessage);
    }
  }

  /**
   * Asks the user a yes/no question. Accepts {@code y}, {@code yes}, {@code n}, {@code no}
   * (case-insensitive).
   *
   * @param question the question to display
   * @return {@code true} for yes, {@code false} for no
   * @throws IOException if an I/O error occurs
   * @see #ask(String)
   */
  public boolean askYesNo(String question) throws IOException {
    while (true) {
      String s = this.ask(question + " [y/n]").trim().toLowerCase();
      if (s.equals("y") || s.equals("yes")) return true;
      if (s.equals("n") || s.equals("no")) return false;
      this.cb.color(AnsiColor.YELLOW).text(this.cb.msg("error.yesno")).reset().println();
    }
  }

  /**
   * Asks the user to select from a list of choices. Displays numbered options and returns the
   * associated value.
   *
   * @param question the question to display
   * @param choices the list of selectable choices
   * @param <T> the type of value returned
   * @return the value of the selected choice
   * @throws IOException if an I/O error occurs
   * @throws ValidationException if the choices list is null or empty
   * @see #ask(String)
   */
  public <T> T askChoice(String question, List<Choice<T>> choices) throws IOException {
    if (choices == null || choices.isEmpty()) {
      throw new ValidationException("No choices provided");
    }

    this.cb.text(question).println();
    for (int i = 0; i < choices.size(); i++) {
      this.cb.text("  " + (i + 1) + ") " + choices.get(i).label()).println();
    }

    while (true) {
      String s = this.ask(this.cb.msg("prompt.choice"));
      try {
        int idx = Integer.parseInt(s.trim()) - 1;
        if (idx >= 0 && idx < choices.size()) return choices.get(idx).value();
      } catch (NumberFormatException _) {
      }
      this.printErrorMessage(this.cb.msg("error.invalidChoice"));
    }
  }

  /**
   * Asks the user for input matching a regular expression. Repeats until the input matches the
   * pattern.
   *
   * @param question the question to display
   * @param regex the regular expression to match
   * @param errorMessage the message to display on mismatch
   * @return the validated input
   * @throws IOException if an I/O error occurs
   * @throws NullPointerException if regex is null
   * @see #ask(String)
   */
  public String askPattern(String question, String regex, String errorMessage) throws IOException {
    Objects.requireNonNull(regex);
    while (true) {
      String s = this.ask(question);
      if (s != null && s.matches(regex)) return s;
      this.printErrorMessage(errorMessage);
    }
  }

  /**
   * Asks the user for input and maps it to a value using a function. Repeats until the mapping
   * succeeds.
   *
   * @param question the question to display
   * @param mapper the function to convert input
   * @param errorMessage the message to display on failure
   * @param <T> the type of value returned
   * @return the mapped value
   * @throws IOException if an I/O error occurs
   * @throws NullPointerException if mapper is null
   * @see #ask(String)
   */
  public <T> T askMapped(String question, Function<String, T> mapper, String errorMessage)
      throws IOException {
    Objects.requireNonNull(mapper);
    while (true) {
      String s = this.ask(question);
      try {
        return mapper.apply(s);
      } catch (Exception _) {
        this.printErrorMessage(errorMessage);
      }
    }
  }
}
