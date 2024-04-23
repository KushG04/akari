package com.comp301.a09akari.view;

import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MessageView implements FXComponent, ModelObserver {
    private final Model model;
    private Label messageLabel;

    public MessageView(Model model) {
        this.model = model;
        this.messageLabel = new Label("Solve the puzzle.");
        this.messageLabel.setStyle("-fx-background-color: grey; -fx-text-fill: black;");
        this.model.addObserver(this);
    }

    @Override
    public Parent render() {
        VBox messageBox = new VBox();
        messageBox.getChildren().add(messageLabel);
        update(model);
        return messageBox;
    }

    @Override
    public void update(Model model) {
        if (model.isSolved()) {
            messageLabel.setText("Puzzle Solved!");
            messageLabel.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        } else {
            messageLabel.setText("Solve the puzzle.");
            messageLabel.setStyle("-fx-background-color: grey; -fx-text-fill: black;");
        }
    }
}
