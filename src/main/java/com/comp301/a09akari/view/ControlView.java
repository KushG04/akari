package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ControlView implements FXComponent {
  private final Model model;
  private final ClassicMvcController controller;

  public ControlView(Model model, ClassicMvcController controller) {
    if (model == null || controller == null) {
      throw new IllegalArgumentException("model and controller cannot be null");
    }

    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    HBox controls = new HBox(10);
    controls.setAlignment(Pos.CENTER);
    Button nextButton = new Button("Next");
    Button prevButton = new Button("Previous");
    Button resetButton = new Button("Reset");
    Button randomButton = new Button("Random");

    nextButton.setOnAction(event -> controller.clickNextPuzzle());
    prevButton.setOnAction(event -> controller.clickPrevPuzzle());
    resetButton.setOnAction(event -> controller.clickResetPuzzle());
    randomButton.setOnAction(event -> controller.clickRandPuzzle());

    controls.getChildren().addAll(prevButton, nextButton, resetButton, randomButton);
    return controls;
  }
}
