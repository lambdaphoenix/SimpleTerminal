# Changelog

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