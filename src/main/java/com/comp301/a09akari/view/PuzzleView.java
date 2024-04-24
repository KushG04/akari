package com.comp301.a09akari.view;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;

public class PuzzleView implements FXComponent, ModelObserver {
  private final Model model;
  private final GridPane grid;

  public PuzzleView(Model model) {
    this.model = model;
    this.grid = new GridPane();
    this.model.addObserver(this);
  }

  @Override
  public Parent render() {
    update(model);
    return grid;
  }

  public void update(Model model) {
    grid.getChildren().clear();
    for (int r = 0; r < model.getActivePuzzle().getHeight(); r++) {
      for (int c = 0; c < model.getActivePuzzle().getWidth(); c++) {
        final int finalR = r;
        final int finalC = c;
        Button cell = new Button();
        cell.setMinSize(30, 30);

        updateCellAppearance(cell, finalR, finalC);
        cell.setOnMouseClicked(
            event -> {
              if (event.getButton() == MouseButton.PRIMARY) {
                handleCellClick(finalR, finalC);
              }
            });
        grid.add(cell, finalC, finalR);
      }
    }
  }

  private void updateCellAppearance(Button cell, int r, int c) {
    if (model.isLamp(r, c)) {
      cell.setStyle("-fx-background-color: gold; -fx-border-color: black;");
    } else if (model.isLit(r, c)) {
      cell.setStyle("-fx-background-color: lightyellow; -fx-border-color: black;");
    } else {
      cell.setStyle("-fx-background-color: lightgrey; -fx-border-color: black;");
    }
  }

  private void handleCellClick(int r, int c) {
    if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
      if (model.isLamp(r, c)) {
        model.removeLamp(r, c);
      } else {
        model.addLamp(r, c);
      }
    }
  }
}
