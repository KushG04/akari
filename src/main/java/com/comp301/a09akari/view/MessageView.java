package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class MessageView implements FXComponent, ModelObserver {
  private final Model model;
  private final ClassicMvcController controller;

  public MessageView(Model model, ClassicMvcController controller) {
    if (model == null || controller == null) {
      throw new IllegalArgumentException("model and controller cannot be null");
    }

    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    HBox messageContainer = new HBox(5);
    messageContainer.setAlignment(Pos.CENTER);
    Label messageLabel = new Label();

    if (model.isSolved()) {
      messageLabel.setText("Puzzle Solved!");
    } else {
      messageLabel.setText("Solve the puzzle!");
    }

    messageContainer.getChildren().add(messageLabel);
    return messageContainer;
  }

  @Override
  public void update(Model model) {}
}
