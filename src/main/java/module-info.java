/**
 * Module definition for the SimpleTerminal library.
 *
 * <p>This module provides styled console output, box rendering, ANSI color and style utilities, and
 * interactive prompts.
 */
module io.github.lambdaphoenix.simpleterminal {
  requires java.desktop;

  exports io.github.lambdaphoenix.simpleterminal.ansi;
  exports io.github.lambdaphoenix.simpleterminal.core;
  exports io.github.lambdaphoenix.simpleterminal.prompt;
  exports io.github.lambdaphoenix.simpleterminal.box;
}
