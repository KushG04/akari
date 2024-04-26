package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.Puzzle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class PuzzleView implements FXComponent {
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
    int puzzleSizeLimit = 11;
    int sizePerCell = 50;
    BorderPane layoutPane = new BorderPane();

    HBox topHeader = new HBox();
    topHeader.setAlignment(Pos.CENTER);
    topHeader.setPadding(new Insets(10));
    topHeader.setSpacing(10);

    Text gameTitle = new Text("Akari Puzzle Game");
    gameTitle.getStyleClass().add("game-title-text");

    Text currentPuzzleIndex = new Text("Puzzle " + (model.getActivePuzzleIndex() + 1) + " of 5");
    currentPuzzleIndex.getStyleClass().add("puzzle-number");

    HBox.setHgrow(gameTitle, Priority.ALWAYS);
    HBox.setHgrow(currentPuzzleIndex, Priority.ALWAYS);
    topHeader.getChildren().addAll(gameTitle, currentPuzzleIndex);

    GridPane puzzleGrid = new GridPane();
    puzzleGrid.setAlignment(Pos.CENTER);
    puzzleGrid.setMinSize(puzzleSizeLimit * sizePerCell, puzzleSizeLimit * sizePerCell);

    Puzzle currentPuzzle = model.getActivePuzzle();
    for (int r = 0; r < currentPuzzle.getHeight(); r++) {
      for (int c = 0; c < currentPuzzle.getWidth(); c++) {
        final int finalR = r;
        final int finalC = c;

        StackPane puzzleCell = new StackPane();
        puzzleCell.getStyleClass().add("cell");

        CellType type = currentPuzzle.getCellType(r, c);
        if (type == CellType.CORRIDOR) {
          puzzleCell.getStyleClass().add("corridor-cell");
          Button cellButton = new Button();
          cellButton.getStyleClass().addAll("cell", "clear-button");
          cellButton.setOnAction(event -> controller.clickCell(finalR, finalC));
          puzzleCell.getChildren().add(cellButton);

          if (model.isLamp(r, c)) {
            try {
              Image lampImage = new Image(getClass().getResourceAsStream("/light-bulb.png"));
              ImageView lampView = new ImageView(lampImage);
              lampView.setFitHeight(30);
              lampView.setFitWidth(30);
              lampView.setMouseTransparent(true);
              puzzleCell.getChildren().add(lampView);
            } catch (NullPointerException error) {
              System.err.println("failed to load lamp image");
            }

            if (model.isLampIllegal(r, c)) {
              puzzleCell.getStyleClass().add("illegal-lamp");
            }
          } else if (model.isLit(r, c)) {
            puzzleCell.getStyleClass().add("lit-cell");
          }
        } else if (type == CellType.CLUE) {
          Text clueValue = new Text(Integer.toString(currentPuzzle.getClue(r, c)));
          puzzleCell.getChildren().add(clueValue);
          if (model.isClueSatisfied(r, c)) {
            puzzleCell.getStyleClass().add("satisfied-clue");
          }
        } else if (type == CellType.WALL) {
          Rectangle wallBlock = new Rectangle(50, 50);
          wallBlock.getStyleClass().add("wall-cell");
          puzzleCell.getChildren().add(wallBlock);
        }

        puzzleGrid.add(puzzleCell, c, r);
      }
    }

    layoutPane.setTop(topHeader);
    layoutPane.setCenter(puzzleGrid);

    return layoutPane;
  }
}
