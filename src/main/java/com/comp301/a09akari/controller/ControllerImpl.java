package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import java.util.Random;

public class ControllerImpl implements ClassicMvcController {
  private final Model model;
  private final Random random;

  public ControllerImpl(Model model) {
    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }

    this.model = model;
    this.random = new Random();
  }

  @Override
  public void clickNextPuzzle() {
    int currentIndex = model.getActivePuzzleIndex();
    int nextIndex = (currentIndex + 1) % model.getPuzzleLibrarySize();
    model.setActivePuzzleIndex(nextIndex);
  }

  @Override
  public void clickPrevPuzzle() {
    int currentIndex = model.getActivePuzzleIndex();
    int prevIndex =
        (currentIndex - 1 + model.getPuzzleLibrarySize()) % model.getPuzzleLibrarySize();
    model.setActivePuzzleIndex(prevIndex);
  }

  @Override
  public void clickRandPuzzle() {
    int randomIndex = random.nextInt(model.getPuzzleLibrarySize());
    model.setActivePuzzleIndex(randomIndex);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (model.isLamp(r, c)) {
      model.removeLamp(r, c);
    } else if (model.isLit(r, c)
        || model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
      model.addLamp(r, c);
    }
  }
}
