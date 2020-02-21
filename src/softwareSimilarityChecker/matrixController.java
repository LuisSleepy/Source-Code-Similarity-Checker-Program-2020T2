package softwareSimilarityChecker;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class matrixController {
    public TableView listOfFiles = new TableView();
    public TextField folderTextField;
    public Label projectTitle;

    public Button homeButton;

    public ScrollPane scrollPane;
    public GridPane matrix;

    public File file;

    public ArrayList<scoreChecker> scoreCheckers = new ArrayList<>();

    public void setData(TextField folderTextField, File file) {
        this.folderTextField = folderTextField;
        projectTitle.setText(folderTextField.getText());
        this.file = file;
        checkOnAction();
        setListOfFiles();
    }

    private void checkOnAction() {
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

    private void setListOfFiles() {

    }
}
