package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class MainView implements FXComponent, ModelObserver {
  private final FXComponent PuzzleView;
  private final FXComponent ControlView;
  private final FXComponent MessageView;
  private final Scene scene;

  public MainView(Model model, ClassicMvcController controller) {
    if (model == null || controller == null) {
      throw new IllegalArgumentException("model and controller cannot be null");
    }

    this.PuzzleView = new PuzzleView(model, controller);
    this.ControlView = new PuzzleView(model, controller);
    this.MessageView = new PuzzleView(model, controller);
    this.scene = new Scene(render());
    scene.getStylesheets().add("main.css");
    model.addObserver(this);
  }

  @Override
  public Parent render() {
    BorderPane pane = new BorderPane();
    pane.setCenter(PuzzleView.render());
    pane.setBottom(ControlView.render());
    pane.setTop(MessageView.render());
    return pane;
  }

  @Override
  public void update(Model model) {
    scene.setRoot(render());
  }

  public Scene getScene() {
    return scene;
  }
}
