package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    public TextField folderTextField;
    public Button searchButton;
    public Button checkButton;
    public Label scoreLabel = new Label();

    public File file;
    public ScrollPane scrollPane;
    public GridPane matrix;
    public ArrayList<scoreChecker> scoreCheckers = new ArrayList<>();

    public void searchOnAction(ActionEvent actionEvent) {
        Stage resourceStage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        file = directoryChooser.showDialog(resourceStage);
        folderTextField.setText(file.toString());
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

            matrix = gcm.gridPaneMaker(10, 10, files);

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
                    Rectangle coloredScore = gcm.checkColor(scores[x][y]);
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
        for (sample.scoreChecker scoreChecker : scoreCheckers) {
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
