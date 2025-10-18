# Changelog
## [0.2.0] - 2025-10-18
### Changed
- `ConsoleBuilder.when()` now takes a `Consumer<ConsoleBuilder>` instead of `Supplier`.
- Reworked JUnit tests for core functionality.

### Added
- `ConsoleBuilder.useFallback(boolean)` to control ResourceBundle fallback behavior.
- `requireNonNull` checks for critical parameters.
- Configurable input/output streams for `ConsoleBuilder` and `Prompt` (no longer bound to `System.in/out`).
- `Prompt.askYesNo` now supports language equivalents (e.g. "ja"/"nein", "yes"/"no").

---
## [0.1.0] - 2025-09-25
### Added
- **Core API**:
    - `ConsoleBuilder` with fluent methods for text, colors, styles, rules, boxes, and printing.
    - `ConsoleConfig` with configurable defaults (`rule.width`, `indent.unit`, `locale`, `box.style`).
    - `ValidationException` for invalid input and configuration errors.
- **ANSI Support**:
    - `AnsiColor` with 16 standard colors, 256-color mode, and truecolor (RGB).
    - `AnsiStyle` with bold, italic, underline, invert, strikethrough, and reset codes.
- **Box Rendering**:
    - `BoxStyle` record with predefined styles: ASCII, Unicode, Double, Rounded, Heavy, Block, Minimal.
- **Prompt API**:
    - `Prompt` for interactive input (`ask`, `askInt`, `askYesNo`, `askChoice`, `askPattern`, `askMapped`).
    - `Choice<T>` record for selection prompts.
- **Internationalization**:
    - Message lookup via `ResourceBundle` (`messages.properties`).
---