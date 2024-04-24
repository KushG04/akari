package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelImpl;
import com.comp301.a09akari.model.PuzzleImpl;
import com.comp301.a09akari.model.PuzzleLibraryImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    PuzzleLibraryImpl library = new PuzzleLibraryImpl();
    for (int[][] puzzleData : SamplePuzzles.getPuzzles()) {
      library.addPuzzle(new PuzzleImpl(puzzleData));
    }

    Model model = new ModelImpl(library);
    ClassicMvcController controller = new ControllerImpl(model);

    FXComponent puzzleView = new PuzzleView(model);
    FXComponent controlView = new ControlView(controller);
    FXComponent messageView = new MessageView(model);

    VBox root = new VBox(puzzleView.render(), controlView.render(), messageView.render());
    model.addObserver(
        (Model updatedModel) -> {
          Platform.runLater(
              () -> {
                root.getChildren()
                    .setAll(puzzleView.render(), controlView.render(), messageView.render());
              });
        });

    Scene scene = new Scene(root, 800, 600);
    stage.setTitle("Akari Puzzle Game");
    stage.setScene(scene);
    stage.show();
  }
}
