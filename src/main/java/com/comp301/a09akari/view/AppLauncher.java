package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelImpl;
import com.comp301.a09akari.model.PuzzleImpl;
import com.comp301.a09akari.model.PuzzleLibraryImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    PuzzleLibraryImpl library = new PuzzleLibraryImpl();
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_01));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_02));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_03));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_04));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_05));

    Model model = new ModelImpl(library);
    ClassicMvcController controller = new ControllerImpl(model);

    FXComponent puzzleView = new PuzzleView(model, controller);
    FXComponent controlView = new ControlView(model, controller);
    FXComponent messageView = new MessageView(model, controller);

    model.addObserver(
        updatedModel -> {
          Scene updatedScene =
              new Scene(new VBox(puzzleView.render(), controlView.render(), messageView.render()));
          updatedScene.getStylesheets().add("main.css");
          stage.setScene(updatedScene);
          stage.show();
        });

    Scene scene =
        new Scene(new VBox(puzzleView.render(), controlView.render(), messageView.render()));
    scene.getStylesheets().add("main.css");
    stage.setScene(scene);
    stage.setFullScreen(true);
    stage.show();
  }
}
