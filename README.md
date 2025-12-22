# Sudoku 9×9 – Programming II Project

A Java-based **Sudoku 9×9 desktop application** built using **MVC architecture**, **design patterns**, and **Swing GUI** as part of the Programming II course.

The system supports:
- Loading and playing Sudoku games from CSV files
- Generating new games by difficulty
- Validating and solving boards
- Saving and resuming unfinished games
- Undoing moves
- Clear separation between frontend, controller, and backend logic

---

## 🎮 Features

### 🧩 Gameplay
- Load Sudoku boards from CSV files
- Interactive 9×9 grid with validation
- Undo last move
- Check solution correctness
- Solve puzzle (only when exactly **5 cells are empty**)

### 📂 Game Management
- Generate new games (Easy / Medium / Hard) from a valid solution
- Save unfinished games automatically
- Resume unfinished games from catalogue
- Automatically deletes solved games

### 🧠 Validation & Solver
- Detects **VALID / INVALID / INCOMPLETE** boards
- Row, column, and 3×3 box validation
- Solver uses permutation-based logic (for limited empty cells)

---

## 🧱 Architecture

The project follows a **View–Controller architecture with external facades**:

- **Frontend (View)**  
  Swing GUI panels (`ViewTable`, `CatalogueScreen`, `PlayPanel`)

- **Controller (Facade / Coordinator)**  
  `GameDriver` acts as the central interface between UI and backend logic

- **Backend (Model & Services)**  
  Board storage, validation, generation, solver, CSV persistence

### Design Patterns Used
- **Facade** – `GameDriver`
- **Singleton** – `csvManager`
- **Flyweight** – Solver board view abstraction
- **Iterator** – Permutation generation
- **Command-like Undo Stack** – Move tracking

---



## 📁 Project Structure

src/  
├── backend/  
│ ├── csvManager.java  
│ ├── GenerateGame.java  
│ ├── SequentialValidation.java  
│ ├── SudokuSolver.java  
│ ├── RandomPairs.java  
│ ├── ValidationResult.java  
│ ├── Difficulty.java  
│ ├── Validity.java  
│ └── InvalidGame.java  
│  
├── controller/  
│ ├── GameDriver.java  
│ └── SolverService.java  
│  
├── frontend/  
│ ├── ViewTable.java  
│ ├── CatalogueScreen.java  
│ ├── PlayPanel.java  
│ └── Test.java  
│  
└── main/  
└── FrameManager.java  
  
## 📦 Storage Layout

SudokuStorage/  
├── 1-EASY/  
├── 2-MEDIUM/  
├── 3-HARD/  
└── 4-INCOMPLETE/  

All boards are stored as CSV files.
