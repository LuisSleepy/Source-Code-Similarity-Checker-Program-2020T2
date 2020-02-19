package softwareSimilarityChecker;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.image.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Controller {
    private Stage stage;
    // Panes
    public ScrollPane scrollPane;
    public AnchorPane mainMenuPane;
    public GridPane matrix;

    // Shapes
    public Circle circleGIF;

    // Containers
    public TextField folderTextField;
    public Label scoreLabel = new Label();
    public ArrayList<scoreChecker> scoreCheckers = new ArrayList<>();

    // Buttons
    public Button startButton;
    public Button aboutButton;
    public Button exitButton;
    public Button searchButton;
    public Button continueButton;
    public Button backToMainMenuButton;
    public Button checkerButton;
    public Button metricsButton;
    public Button backToSearchMenuButton;

    // Others
    public File file;

    public void initialize() throws URISyntaxException {
        File codingFile = new File("assets/coding.gif");
        Image codingImage = new Image(codingFile.toURI().toString(), false);
        circleGIF.setFill(new ImagePattern(codingImage));
    }

    public void initStage(Stage stage) {
        this.stage = stage;
    }

    public void setStartButton() {
        // Remove buttons from the main menu
        startButton.setVisible(false);
        aboutButton.setVisible(false);
        exitButton.setVisible(false);

        folderTextField.setVisible(true);
        searchButton.setVisible(true);
        continueButton.setVisible(true);
        continueButton.setDisable(true);
        backToMainMenuButton.setVisible(true);
    }

    public void setAboutButton() {
        startButton.setVisible(false);
        aboutButton.setVisible(false);
        exitButton.setVisible(false);
    }

    public void setExitButton() {
        stage.close();
    }

    public void setSearchButton(ActionEvent actionEvent) {
        Stage resourceStage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        file = directoryChooser.showDialog(resourceStage);
        folderTextField.setText(file.toString());

        if (folderTextField.getText() != null) {
            continueButton.setDisable(false);
        }
    }

    public void setContinueButton(ActionEvent actionEvent) {
        folderTextField.setVisible(false);
        searchButton.setVisible(false);
        continueButton.setVisible(false);
        backToMainMenuButton.setVisible(false);

        checkerButton.setVisible(true);
        metricsButton.setVisible(true);
        backToSearchMenuButton.setVisible(true);
    }

    public void setBackToMainMenuButton(ActionEvent actionEvent) {
        folderTextField.clear();

        folderTextField.setVisible(false);
        searchButton.setVisible(false);
        continueButton.setVisible(false);
        backToMainMenuButton.setVisible(false);

        startButton.setVisible(true);
        aboutButton.setVisible(true);
        exitButton.setVisible(true);
    }

    public void setCheckerButton(ActionEvent actionEvent) {
    }

    public void setMetricsButton(ActionEvent actionEvent) {
    }

    public void setBackToSearchMenuButton(ActionEvent actionEvent) {
        checkerButton.setVisible(false);
        metricsButton.setVisible(false);
        backToSearchMenuButton.setVisible(false);

        folderTextField.clear();
        folderTextField.setVisible(true);
        searchButton.setVisible(true);
        continueButton.setVisible(true);
        continueButton.setDisable(true);
        backToMainMenuButton.setVisible(true);
    }


    public void checkOnAction(ActionEvent actionEvent) throws IOException {
        if (file == null) {
            folderTextField.setText("No File Directory Chosen");
        } else {
            gridColorMaker gcm = new gridColorMaker();
            // Initializes the matrix

            // Stores the names of the files currently handled by "file"
            String[] files = file.list();
            assert files != null;

            matrix = gcm.gridPaneMaker(5, 5, files);

            // Provides the dimensions of the array that will store the similarity scores
            int rows = files.length;
            int cols = files.length;
            float[][] scores = new float[cols][rows];

            // Create a loop for reading the files
            ArrayList<String> projFiles = new ArrayList<>();
            for (String s : files) {
                projFiles.add(file.getAbsolutePath() + '\\' + s);
            }

            similarityChecker sc = new similarityChecker();
            // Puts a file on hold and compare it to the rest of the files, also with itself
            for (int x = 0; x < projFiles.size(); x++) {
                for (int y = 0; y < projFiles.size(); y++) {
                    String proj1 = projFiles.get(x);
                    String proj2 = projFiles.get(y);
                    File code1 = new File(proj1);
                    File code2 = new File(proj2);
                    try {
                        float simScore = sc.check(code1, code2);
                        System.out.print(String.format("%.2f", simScore) + "\t");
                        scores[x][y] = simScore;
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }
                System.out.print("\n");
            }

            // Instantiates gridColorMaker

            for (int x = 0; x < projFiles.size(); x++) {
                for (int y = 0; y < projFiles.size(); y++) {
                    // Creates a colored rectangle using the checkColor method of gridColorMaker
                    StackPane coloredScore = gcm.checkColor(scores[x][y]);
                    // Adds the rectangle in a to-be-created cell of the gridPane
                    matrix.add(coloredScore, x + 1, y + 1);
                    scoreChecker checker = new scoreChecker(scores[x][y], x + 1, y + 1);
                    scoreCheckers.add(checker);

                }
            }

            // Sets up matrix as a content of the scrollPane
            scrollPane.setContent(matrix);
        }

    }

    public void showScore(MouseEvent mouseEvent) throws IllegalArgumentException {
        System.out.println(GridPane.getColumnIndex(mouseEvent.getPickResult().getIntersectedNode()));
        System.out.println(GridPane.getRowIndex(mouseEvent.getPickResult().getIntersectedNode()));
        for (softwareSimilarityChecker.scoreChecker scoreChecker : scoreCheckers) {
            scoreLabel.setText("");
            if (GridPane.getColumnIndex(mouseEvent.getPickResult().getIntersectedNode()) == scoreChecker.column &&
                    GridPane.getRowIndex(mouseEvent.getPickResult().getIntersectedNode()) == scoreChecker.row) {
                String scoreString = String.format("%.2f", scoreChecker.score);
                scoreLabel.setText(scoreString);
                matrix.add(scoreLabel, scoreChecker.column, scoreChecker.row);
                System.out.println(mouseEvent.getX());
                System.out.println(mouseEvent.getY());
                break;
            }
        }
    }
}
