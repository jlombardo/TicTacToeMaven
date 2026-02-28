# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
mvn clean package          # Build the JAR
mvn test                   # Run all tests
java -jar target/TicTacToe-2.0.0.jar  # Run the application

# Run a single test class or method
mvn test -Dtest=TileTest
mvn test -Dtest=RailTest#railShouldBeWinnerIf3TilesMarkedX
```

## Architecture

This is a Java Swing desktop game using MVC with three packages:

- **`app`** — `TicTacToe.java`: entry point, sets Swing look-and-feel, launches `GameWindow`
- **`domain`** — pure game logic, no Swing imports:
  - `GameEngine`: the controller; manages 8 `Rail` objects (all win combos), handles move selection and win/draw detection
  - `Rail`: holds 3 `Tile` refs; `isWinner()` checks if all 3 have the same mark
  - `Tile`: extends `JButton` but overrides `isSelected()`/`setText()` to independently track board state
- **`ui`** — `GameWindow`: all Swing code; delegates every move to `GameEngine`, reads results back to update display

The domain layer (`GameEngine`, `Rail`, `Tile`) is deliberately decoupled from the UI so it can be reused with any front-end.

## Game Flow

1. User clicks a `Tile` → `GameWindow` marks it "X", checks for win/draw
2. `GameWindow` calls `GameEngine.selectComputerMove(smarts)` → returns the best available `Tile`
3. `GameWindow` marks returned tile "0", checks for win/draw
4. Repeat until terminal state

## AI Difficulty (controlled by `smarts` slider value)

| Level | `smarts` | Strategy |
|-------|----------|----------|
| Easy  | < 50 | 30% chance to find winning move, else random |
| Smart | == 50 | Heuristic: win → block → center → corner → random |
| Genius | > 50 | Minimax (optimal, unbeatable) |

## Key Details

- Java 8, JUnit 4.13.2 (only dependency)
- Main class: `edu.wctc.java.demo.tictactoe.app.TicTacToe`
- Computer mark is `"0"` (zero), not `"O"` (letter) — matters for string comparisons
- Resources (help icon image) are stored under `src/main/java/resources/` and bundled into the JAR
