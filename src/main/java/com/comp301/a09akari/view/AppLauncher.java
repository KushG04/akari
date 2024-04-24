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
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    PuzzleLibraryImpl library = new PuzzleLibraryImpl();
    for (int[][] puzzleData : SamplePuzzles.getPuzzles()) {
      library.addPuzzle(new PuzzleImpl(puzzleData));
    }

    Model model = new ModelImpl(library);
    ClassicMvcController controller = new ControllerImpl(model);
    MainView view = new MainView(model, controller);

    stage.setTitle("Akari Puzzle Game");
    Scene scene = view.getScene();
    stage.setScene(scene);
    stage.setFullScreen(true);
    stage.show();
  }
}
