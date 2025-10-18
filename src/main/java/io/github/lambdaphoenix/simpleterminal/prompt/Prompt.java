package io.github.lambdaphoenix.simpleterminal.prompt;

import io.github.lambdaphoenix.simpleterminal.ansi.AnsiColor;
import io.github.lambdaphoenix.simpleterminal.core.ConsoleBuilder;
import io.github.lambdaphoenix.simpleterminal.core.ValidationException;
import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Provides interactive console prompts for user input.
 *
 * <p>The {@code Prompt} class supports various input types including strings, integers, yes/no
 * confirmations, pattern matching, and selectable choices. It uses a {@link ConsoleBuilder} for
 * styled output and supports validation and error messaging.
 *
 * <p>The {@code Prompt} class reads from an {@link InputStream} (default: {@code System.in}) and
 * writes messages using a {@link ConsoleBuilder}. This design allows injection of alternative input
 * sources for testing or custom environments.
 *
 * <h2>Usage Example:</h2>
 *
 * <pre>{@code
 * Prompt prompt = new Prompt(new ConsoleBuilder());
 * int age = prompt.askInt("Enter your age:");
 * }</pre>
 *
 * @param cb Console builder used to render prompt messages with styles, colors, and localization.
 *     Must never be {@code null}.
 * @param br Reader used internally by all prompt methods to capture console input line by line.
 *     Backed by a {@link BufferedReader} wrapping the provided {@link InputStream}. Defaults to
 *     {@code System.in} if not specified.
 * @author lambdaphoenix
 * @version 0.2.0 (2025-10-18)
 * @since 0.1.0
 */
public record Prompt(ConsoleBuilder cb, BufferedReader br) {
  /**
   * Creates a {@code Prompt} using {@code System.in} as input source.
   *
   * @param cb the console builder used for output (must not be {@code null})
   * @throws NullPointerException if {@code cb} is {@code null}
   */
  public Prompt(ConsoleBuilder cb) {
    this(cb, System.in);
  }

  /**
   * Creates a {@code Prompt} with a custom input stream.
   *
   * @param cb the console builder used for output (must not be {@code null})
   * @param in the input stream to read from (must not be {@code null})
   * @throws NullPointerException if {@code console} or {@code in} is {@code null}
   * @since 0.2.0
   */
  public Prompt(ConsoleBuilder cb, InputStream in) {
    this(
        Objects.requireNonNull(cb, "ConsoleBuilder must not be null."),
        new BufferedReader(
            new InputStreamReader(Objects.requireNonNull(in, "InputStream must not be null."))));
  }

  /**
   * Creates a {@code Prompt} with a custom {@link BufferedReader}.
   *
   * <p>This constructor is useful if you already have a prepared reader, for example with a
   * specific charset, a {@link StringReader}, or a custom wrapper.
   *
   * <p><b>Note:</b> The {@code Prompt} does not close the provided reader. The caller is
   * responsible for managing its lifecycle.
   *
   * @param cb the console builder used for output (must not be {@code null})
   * @param br the reader to use for input (must not be {@code null})
   * @throws NullPointerException if {@code cb} or {@code br} is {@code null}
   * @since 0.2.0
   */
  public Prompt(ConsoleBuilder cb, BufferedReader br) {
    this.cb = Objects.requireNonNull(cb, "ConsoleBuilder must not be null.");
    this.br = Objects.requireNonNull(br, "BufferedReader must not be null.");
  }

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
   * @throws IOException if an I/O error occurs or end-of-stream is reached
   * @throws NullPointerException if {@code question} is null
   */
  public String ask(String question) throws IOException {
    Objects.requireNonNull(question, "Question must not be null");
    this.cb.color(AnsiColor.CYAN).text(question + " ").reset().print();
    String line = br.readLine();
    if (line == null) {
      throw new EOFException("End of input reached");
    }
    return line;
  }

  /**
   * Asks the user a question and validates the input using a predicate. Repeats until the input
   * passes validation.
   *
   * @param question the question to display
   * @param validator the predicate to validate input
   * @param errorMessage the message to display on invalid input
   * @return the validated input
   * @throws IOException if an I/O error occurs or end-of-stream is reached
   * @throws NullPointerException if {@code question}, {@code validator} or {@code errorMessage} is
   *     null
   * @see #ask(String)
   */
  public String ask(String question, Predicate<String> validator, String errorMessage)
      throws IOException {
    Objects.requireNonNull(validator, "Validator must not be null");
    Objects.requireNonNull(errorMessage, "Error message must not be null");
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
   * @throws IOException if an I/O error occurs or end-of-stream is reached
   * @throws NullPointerException if {@code question} is null
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
   * @throws IOException if an I/O error occurs or end-of-stream is reached
   * @throws NullPointerException if {@code question}, {@code validator} or {@code errorMessage} is
   *     null
   * @see #askInt(String)
   */
  public int askInt(String question, Predicate<Integer> validator, String errorMessage)
      throws IOException {
    Objects.requireNonNull(validator, "Validator must not be null");
    Objects.requireNonNull(errorMessage, "Error message must not be null");
    while (true) {
      int v = this.askInt(question);
      if (validator.test(v)) return v;
      this.printErrorMessage(errorMessage);
    }
  }

  /**
   * Asks the user a yes/no question. Accepts {@code y}, {@code yes}, {@code n}, {@code no}
   * (case-insensitive) and localized equivalents if available.
   *
   * @param question the question to display
   * @return {@code true} for yes, {@code false} for no
   * @throws IOException if an I/O error occurs or end-of-stream is reached
   * @throws NullPointerException if {@code question} is null
   * @see #ask(String)
   */
  public boolean askYesNo(String question) throws IOException {
    final Set<String> yesAnswers = new HashSet<>(Arrays.asList("y", "yes"));
    final Set<String> noAnswers = new HashSet<>(Arrays.asList("n", "no"));

    String shortYes = "y";
    String shortNo = "n";
    try {
      shortYes = this.cb.msg("input.yes.short").toLowerCase();
      shortNo = this.cb.msg("input.no.short").toLowerCase();

      yesAnswers.add(shortYes);
      yesAnswers.add(this.cb.msg("input.yes").toLowerCase());
      noAnswers.add(shortNo);
      noAnswers.add(this.cb.msg("input.no").toLowerCase());
    } catch (MissingResourceException _) {
    }

    while (true) {
      String s = this.ask(question + " [" + shortYes + "/" + shortNo + "]").trim().toLowerCase();
      if (yesAnswers.contains(s)) return true;
      if (noAnswers.contains(s)) return false;
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
   * @throws IOException if an I/O error occurs or end-of-stream is reached
   * @throws NullPointerException if {@code question} is null
   * @throws ValidationException if the {@code choices} list is null empty or contains null
   *     labels/values
   * @see #ask(String)
   */
  public <T> T askChoice(String question, List<Choice<T>> choices) throws IOException {
    if (choices == null || choices.isEmpty()) {
      throw new ValidationException("No choices provided");
    }
    Objects.requireNonNull(question, "Question must not be null");
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
   * @throws IOException if an I/O error occurs or end-of-stream is reached
   * @throws NullPointerException if {@code question}, {@code regex} or {@code errorMessage} is null
   * @see #ask(String)
   */
  public String askPattern(String question, String regex, String errorMessage) throws IOException {
    Objects.requireNonNull(regex, "Regex not be null");
    Objects.requireNonNull(errorMessage, "Error message must not be null");
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
   * @throws IOException if an I/O error occurs or end-of-stream is reached
   * @throws NullPointerException if {@code question}, {@code mapper} or {@code errorMessage} is
   *     {@code null}
   * @see #ask(String)
   */
  public <T> T askMapped(String question, Function<String, T> mapper, String errorMessage)
      throws IOException {
    Objects.requireNonNull(mapper, "Mapper must not be null");
    Objects.requireNonNull(errorMessage, "Error message must not be null");
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
