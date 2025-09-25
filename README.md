# SimpleTerminal
[![Javadoc](https://img.shields.io/badge/docs-Javadoc-blue)](https://lambdaphoenix.github.io/SimpleTerminal/)
[![Changelog](https://img.shields.io/badge/Changelog-available-brightgreen.svg)](./CHANGELOG.md)
[![GitHub License](https://img.shields.io/github/license/lambdaphoenix/SimpleTerminal)](./LICENSE)

>A Java library for **styled console output** and **interactive prompts**.  
SimpleTerminal provides a fluent API for ANSI colors, text styles, box rendering, and user input with validation and internationalization support.

---

## ✨ Features

- **ANSI Colors & Styles** 🎨  
  Foreground, background, bold, italic, underline, reset, and more.
- **Box Rendering**  
  Predefined box styles (ASCII, Unicode, Double, Rounded, Heavy, Block, Minimal).
- **Fluent ConsoleBuilder API**  
  Chainable methods for text, rules, boxes, indentation, and styling.
- **Interactive Prompts**  
  Ask for strings, integers, yes/no, choices, regex‑validated input, or mapped values.
- **Internationalization** 🌍  
  Message lookup via `ResourceBundle` (`messages.properties`).
- **Configurable Defaults** ⚙️  
  Rule width, indent unit, locale, and box style via `ConsoleConfig` or `simpleterminal.properties`.

---

## 📦 Installation

### Gradle (Kotlin DSL)

```kotlin
dependencies {
    implementation("io.github.lambdaphoenix:simpleterminal:0.1.0")
}
```
### Maven

```Xml
<dependency>
  <groupId>io.github.lambdaphoenix</groupId>
  <artifactId>simpleterminal</artifactId>
  <version>0.1.0</version>
</dependency>
```

---

## 🚀 Usage Examples

### ConsoleBuilder
```java
import io.github.lambdaphoenix.simpleterminal.ansi.AnsiColor;
import io.github.lambdaphoenix.simpleterminal.ansi.AnsiStyle;
import io.github.lambdaphoenix.simpleterminal.core.ConsoleBuilder;

void main() {
  ConsoleBuilder cb = new ConsoleBuilder();
  cb.color(AnsiColor.GREEN)
      .style(AnsiStyle.BOLD)
      .text("Hello, world!")
      .reset()
      .println();
}
```
### BoxStyle
```java
import io.github.lambdaphoenix.simpleterminal.box.BoxStyle;
import io.github.lambdaphoenix.simpleterminal.core.ConsoleBuilder;

void main() {
  ConsoleBuilder cb = new ConsoleBuilder();
  cb.box("Title", "This is the content", BoxStyle.DOUBLE).print();
}
```
### Prompt
```java
import io.github.lambdaphoenix.simpleterminal.core.ConsoleBuilder;
import io.github.lambdaphoenix.simpleterminal.prompt.Prompt;

void main() throws IOException {
  Prompt prompt = new Prompt(new ConsoleBuilder());
  boolean cont = prompt.askYesNo("Continue?");
  System.out.println("Answer: " + cont);
}
```

---

## 📖 Documentation
- [**Javadoc**](https://lambdaphoenix.github.io/SimpleTerminal/)  
  The complete API reference, automatically generated and published via GitHub Pages.

- [**Changelog**](./CHANGELOG.md)  
  A detailed list of all notable changes, new features, and fixes for each release.

---

## 📜 License
This project is licensed under the [GPL License](./LICENSE).