package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class PuzzleView implements FXComponent, ModelObserver {
  private final Model model;
  private final ClassicMvcController controller;

  public PuzzleView(Model model, ClassicMvcController controller) {
    if (model == null || controller == null) {
      throw new IllegalArgumentException("model and controller cannot be null");
    }

    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    GridPane grid = new GridPane();

    for (int r = 0; r < model.getActivePuzzle().getHeight(); r++) {
      for (int c = 0; c < model.getActivePuzzle().getWidth(); c++) {
        Button cell = new Button();
        cell.setMinSize(30, 30);

        CellType type = model.getActivePuzzle().getCellType(r, c);
        if (type == CellType.WALL) {
          cell.setStyle("-fx-background-color: #888;");
        } else if (model.isLit(r, c)) {
          cell.setStyle("-fx-background-color: lightyellow;");
        } else {
          cell.setStyle("-fx-background-color: white;");
        }

        int finalR = r;
        int finalC = c;
        cell.setOnAction(event -> controller.clickCell(finalR, finalC));
        grid.add(cell, c, r);
      }
    }

    return grid;
  }

  @Override
  public void update(Model model) {}
}
