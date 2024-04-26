package com.comp301.a09akari.model;

import java.util.HashSet;
import java.util.Set;

public class ModelImpl implements Model {
  private final PuzzleLibrary library;
  private final Set<ModelObserver> observers;
  private int currentPuzzleIndex;
  private boolean[][] lamps;

  public ModelImpl(PuzzleLibrary library) {
    if (library == null || library.size() == 0) {
      throw new IllegalArgumentException("library cannot be null or empty");
    }

    this.library = library;
    this.currentPuzzleIndex = 0;
    this.observers = new HashSet<>();
    this.lamps =
        new boolean[library.getPuzzle(currentPuzzleIndex).getHeight()]
            [library.getPuzzle(currentPuzzleIndex).getWidth()];
  }

  @Override
  public void addLamp(int r, int c) {
    validatePosition(r, c);
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("lamps can only be placed in corridors");
    }

    if (!lamps[r][c]) {
      lamps[r][c] = true;
      notifyObservers();
    }
  }

  @Override
  public void removeLamp(int r, int c) {
    validatePosition(r, c);
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("lamps can only be removed from corridors");
    }

    if (lamps[r][c]) {
      lamps[r][c] = false;
      notifyObservers();
    }
  }

  /*
  @Override
  public boolean isLit(int r, int c) {
    validatePosition(r, c);
    if (getActivePuzzle().getCellType(r, c) == CellType.WALL) {
      return false;
    }

    return checkDirectionLight(r, c, -1, 0)
        || checkDirectionLight(r, c, 1, 0)
        || checkDirectionLight(r, c, 0, -1)
        || checkDirectionLight(r, c, 0, 1);
  }
  */

  @Override
  public boolean isLit(int r, int c) {
    validatePosition(r, c);
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("invalid cell for a lamp");
    }

    if (lamps[r][c]) {
      return true;
    }

    for (int i = r; i >= 0; i--) {
      if (getActivePuzzle().getCellType(i, c) != CellType.WALL && lamps[i][c]) {
        return true;
      }
    }
    for (int i = r; i < getActivePuzzle().getHeight(); i++) {
      if (getActivePuzzle().getCellType(i, c) != CellType.WALL && lamps[i][c]) {
        return true;
      }
    }
    for (int j = c; j >= 0; j--) {
      if (getActivePuzzle().getCellType(r, j) != CellType.WALL && lamps[r][j]) {
        return true;
      }
    }
    for (int j = c; j < getActivePuzzle().getWidth(); j++) {
      if (getActivePuzzle().getCellType(r, j) != CellType.WALL && lamps[r][j]) {
        return true;
      }
    }

    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    validatePosition(r, c);
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("can only query lamps in corridors");
    }

    return lamps[r][c];
  }

  /*
  @Override
  public boolean isLampIllegal(int r, int c) {
    validatePosition(r, c);
    if (!lamps[r][c]) {
      return false;
    }

    return checkDirectionLamp(r, c, -1, 0)
        || checkDirectionLamp(r, c, 1, 0)
        || checkDirectionLamp(r, c, 0, -1)
        || checkDirectionLamp(r, c, 0, 1);
  }
  */

  /*
  @Override
  public boolean isLampIllegal(int r, int c) {
    validatePosition(r, c);
    if (!lamps[r][c]) {
      throw new IllegalArgumentException("no lamp at this position");
    }

    for (int i = 0; i < r; i++) {
      if (getActivePuzzle().getCellType(i, c) == CellType.CORRIDOR && lamps[i][c]) {
        return true;
      }
      if (getActivePuzzle().getCellType(i, c) == CellType.WALL
          || getActivePuzzle().getCellType(i, c) == CellType.CLUE) {
        break;
      }
    }
    for (int i = r + 1; i < getActivePuzzle().getHeight(); i++) {
      if (getActivePuzzle().getCellType(i, c) == CellType.CORRIDOR && lamps[i][c]) {
        return true;
      }
      if (getActivePuzzle().getCellType(i, c) == CellType.WALL
          || getActivePuzzle().getCellType(i, c) == CellType.CLUE) {
        break;
      }
    }
    for (int j = 0; j < c; j++) {
      if (getActivePuzzle().getCellType(r, j) == CellType.CORRIDOR && lamps[r][j]) {
        return true;
      }
      if (getActivePuzzle().getCellType(r, j) == CellType.WALL
          || getActivePuzzle().getCellType(r, j) == CellType.CLUE) {
        break;
      }
    }
    for (int j = c + 1; j < getActivePuzzle().getWidth(); j++) {
      if (getActivePuzzle().getCellType(r, j) == CellType.CORRIDOR && lamps[r][j]) {
        return true;
      }
      if (getActivePuzzle().getCellType(r, j) == CellType.WALL
          || getActivePuzzle().getCellType(r, j) == CellType.CLUE) {
        break;
      }
    }

    return false;
  }
  */

  /*
  @Override
  public boolean isLampIllegal(int r, int c) {
    validatePosition(r, c);
    if (!lamps[r][c]) {
      throw new IllegalArgumentException("no lamp at this position");
    }

    if (r > 0 && getActivePuzzle().getCellType(r - 1, c) == CellType.CORRIDOR && lamps[r - 1][c]) {
      return true;
    }
    if (r < getActivePuzzle().getHeight() - 1 && getActivePuzzle().getCellType(r + 1, c) == CellType.CORRIDOR && lamps[r + 1][c]) {
      return true;
    }
    if (c > 0 && getActivePuzzle().getCellType(r, c - 1) == CellType.CORRIDOR && lamps[r][c - 1]) {
      return true;
    }
    if (c < getActivePuzzle().getWidth() - 1 && getActivePuzzle().getCellType(r, c + 1) == CellType.CORRIDOR && lamps[r][c + 1]) {
      return true;
    }

    return false;
  }
  */

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (!isLamp(r, c)) {
      throw new IllegalArgumentException("no lamp at this position");
    }

    for (int i = r - 1; i >= 0; i--) {
      if (getActivePuzzle().getCellType(i, c) == CellType.WALL || getActivePuzzle().getCellType(i, c) == CellType.CLUE) {
        break;
      }
      if (lamps[i][c]) {
        return true;
      }
    }
    for (int i = r + 1; i < getActivePuzzle().getHeight(); i++) {
      if (getActivePuzzle().getCellType(i, c) == CellType.WALL || getActivePuzzle().getCellType(i, c) == CellType.CLUE) {
        break;
      }
      if (lamps[i][c]) {
        return true;
      }
    }
    for (int j = c - 1; j >= 0; j--) {
      if (getActivePuzzle().getCellType(r, j) == CellType.WALL || getActivePuzzle().getCellType(r, j) == CellType.CLUE) {
        break;
      }
      if (lamps[r][j]) {
        return true;
      }
    }
    for (int j = c + 1; j < getActivePuzzle().getWidth(); j++) {
      if (getActivePuzzle().getCellType(r, j) == CellType.WALL || getActivePuzzle().getCellType(r, j) == CellType.CLUE) {
        break;
      }
      if (lamps[r][j]) {
        return true;
      }
    }

    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return library.getPuzzle(currentPuzzleIndex);
  }

  @Override
  public int getActivePuzzleIndex() {
    return currentPuzzleIndex;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= library.size()) {
      throw new IndexOutOfBoundsException("puzzle index is out of range");
    }

    currentPuzzleIndex = index;
    lamps = new boolean[getActivePuzzle().getHeight()][getActivePuzzle().getWidth()];
    notifyObservers();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    lamps = new boolean[getActivePuzzle().getHeight()][getActivePuzzle().getWidth()];
    notifyObservers();
  }

  @Override
  public boolean isSolved() {
    for (int r = 0; r < getActivePuzzle().getHeight(); r++) {
      for (int c = 0; c < getActivePuzzle().getWidth(); c++) {
        CellType type = getActivePuzzle().getCellType(r, c);
        if (type == CellType.CORRIDOR && !isLit(r, c)) {
          return false;
        }
        if (type == CellType.CLUE && !isClueSatisfied(r, c)) {
          return false;
        }
      }
    }

    return true;
  }

  /*
  @Override
  public boolean isSolved() {
    for (int r = 0; r < getActivePuzzle().getHeight(); r++) {
      for (int c = 0; c < getActivePuzzle().getWidth(); c++) {
        switch (getActivePuzzle().getCellType(r, c)) {
          case WALL:
            break;
          case CORRIDOR:
            if (this.isLamp(r, c) && this.isLampIllegal(r, c)) {
              return false;
            }
            if (!this.isLit(r, c)) {
              return false;
            }
            break;
          case CLUE:
            if (!this.isClueSatisfied(r, c)) {
              return false;
            }
            break;
        }
      }
    }

    return true;
  }
  */

  @Override
  public boolean isClueSatisfied(int r, int c) {
    validatePosition(r, c);
    if (getActivePuzzle().getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException("specified cell is not a clue cell");
    }

    int requiredLamps = getActivePuzzle().getClue(r, c);
    int lampCount = 0;

    if (r > 0 && lamps[r - 1][c]) {
      lampCount++;
    }
    if (r < getActivePuzzle().getHeight() - 1 && lamps[r + 1][c]) {
      lampCount++;
    }
    if (c > 0 && lamps[r][c - 1]) {
      lampCount++;
    }
    if (c < getActivePuzzle().getWidth() - 1 && lamps[r][c + 1]) {
      lampCount++;
    }

    return lampCount == requiredLamps;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    if (observer != null) {
      observers.add(observer);
    }
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    if (observer != null) {
      observers.remove(observer);
    }
  }

  private void notifyObservers() {
    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }

  private void validatePosition(int r, int c) {
    if (r < 0 || r >= getActivePuzzle().getHeight() || c < 0 || c >= getActivePuzzle().getWidth()) {
      throw new IndexOutOfBoundsException("row or column is out of bounds");
    }
  }

  private boolean checkDirectionLight(int r, int c, int rowStep, int colStep) {
    int newRow = r + rowStep;
    int newCol = c + colStep;

    while (newRow >= 0
        && newRow < getActivePuzzle().getHeight()
        && newCol >= 0
        && newCol < getActivePuzzle().getWidth()) {
      CellType type = getActivePuzzle().getCellType(newRow, newCol);
      if (type == CellType.WALL) {
        return false;
      }
      if (!isLamp(newRow, newCol)) {
        return true;
      }

      newRow += rowStep;
      newCol += colStep;
    }

    return false;
  }

  /*
  private boolean checkDirectionLamp(int r, int c, int rowStep, int colStep) {
    int newRow = r + rowStep;
    int newCol = c + colStep;
    boolean pastWall = false;

    while (newRow >= 0
        && newRow < getActivePuzzle().getHeight()
        && newCol >= 0
        && newCol < getActivePuzzle().getWidth()
        && !pastWall) {
      CellType type = getActivePuzzle().getCellType(newRow, newCol);
      if (type == CellType.WALL) {
        pastWall = true;
      }
      if (lamps[newRow][newCol] && !pastWall) {
        return true;
      }

      newRow += rowStep;
      newCol += colStep;
    }

    return false;
  }
  */
}
