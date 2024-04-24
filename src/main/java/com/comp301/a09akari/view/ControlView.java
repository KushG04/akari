package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ControlView implements FXComponent {
  private final ClassicMvcController controller;

  public ControlView(ClassicMvcController controller) {
    if (controller == null) {
      throw new IllegalArgumentException("controller cannot be null");
    }

    this.controller = controller;
  }

  @Override
  public Parent render() {
    HBox controls = new HBox(10);
    Button nextButton = new Button("Next");
    Button prevButton = new Button("Previous");
    Button resetButton = new Button("Reset");

    nextButton.setOnAction(event -> controller.clickNextPuzzle());
    prevButton.setOnAction(event -> controller.clickPrevPuzzle());
    resetButton.setOnAction(event -> controller.clickResetPuzzle());

    controls.getChildren().addAll(nextButton, prevButton, resetButton);
    return controls;
  }
}
