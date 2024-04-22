package com.comp301.a09akari.model;

import java.util.HashSet;
import java.util.Set;

public class ModelImpl implements Model {
    private final PuzzleLibrary library;
    private int currentPuzzleIndex;
    private Set<ModelObserver> observers;
    private boolean[][] lamps;

    public ModelImpl(PuzzleLibrary library) {
        if (library == null || library.size() == 0) {
            throw new IllegalArgumentException("library cannot be null or empty");
        }

        this.library = library;
        this.currentPuzzleIndex = 0;
        this.observers = new HashSet<>();
        this.lamps = new boolean[library.getPuzzle(currentPuzzleIndex).getHeight()][library.getPuzzle(currentPuzzleIndex).getWidth()];
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

    @Override
    public boolean isLit(int r, int c) {
        validatePosition(r, c);

        for (int i = c + 1; i < getActivePuzzle().getWidth(); i++) {
            CellType type = getActivePuzzle().getCellType(r, i);
            if (type == CellType.WALL) {
                break;
            }
            if (lamps[r][i]) {
                return true;
            }
        }
        for (int i = c - 1; i >= 0; i--) {
            CellType type = getActivePuzzle().getCellType(r, i);
            if (type == CellType.WALL) {
                break;
            }
            if (lamps[r][i]) {
                return true;
            }
        }
        for (int i = r + 1; i < getActivePuzzle().getHeight(); i++) {
            CellType type = getActivePuzzle().getCellType(i, c);
            if (type == CellType.WALL) {
                break;
            }
            if (lamps[i][c]) {
                return true;
            }
        }
        for (int i = r - 1; i >= 0; i--) {
            CellType type = getActivePuzzle().getCellType(i, c);
            if (type == CellType.WALL) {
                break;
            }
            if (lamps[i][c]) {
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

    @Override
    public boolean isLampIllegal(int r, int c) {
        validatePosition(r, c);
        if (!lamps[r][c]) {
            throw new IllegalArgumentException("no lamp at the specified location");
        }

        for (int i = c + 1; i < getActivePuzzle().getWidth(); i++) {
            if (getActivePuzzle().getCellType(r, i) == CellType.WALL) {
                break;
            }
            if (lamps[r][i]) {
                return true;
            }
        }
        for (int i = c - 1; i >= 0; i--) {
            if (getActivePuzzle().getCellType(r, i) == CellType.WALL) {
                break;
            }
            if (lamps[r][i]) {
                return true;
            }
        }
        for (int i = r + 1; i < getActivePuzzle().getHeight(); i++) {
            if (getActivePuzzle().getCellType(i, c) == CellType.WALL) {
                break;
            }
            if (lamps[i][c]) {
                return true;
            }
        }
        for (int i = r - 1; i >= 0; i--) {
            if (getActivePuzzle().getCellType(i, c) == CellType.WALL) {
                break;
            }
            if (lamps[i][c]) {
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
                if (getActivePuzzle().getCellType(r, c) == CellType.CLUE && !isClueSatisfied(r, c)) {
                    return false;
                }
            }
        }
        for (int r = 0; r < getActivePuzzle().getHeight(); r++) {
            for (int c = 0; c < getActivePuzzle().getWidth(); c++) {
                if (getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR && !isLit(r, c)) {
                    return false;
                }
            }
        }
        for (int r = 0; r < getActivePuzzle().getHeight(); r++) {
            for (int c = 0; c < getActivePuzzle().getWidth(); c++) {
                if (lamps[r][c] && isLampIllegal(r, c)) {
                    return false;
                }
            }
        }

        return true;
    }

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
}
