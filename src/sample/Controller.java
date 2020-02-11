package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    public TextField folderTextField;
    public Button searchButton;
    public Button checkButton;

    public File file;
    public GridPane matrix;
    public ScrollPane scrollPane;


    public void searchOnAction(ActionEvent actionEvent) {
        Stage resourceStage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        file = directoryChooser.showDialog(resourceStage);
    }


    public void checkOnAction(ActionEvent actionEvent) throws IOException {
        scrollPane.setVisible(true);
        String[] files = file.list();
        assert files != null;

        similarityChecker sc = new similarityChecker();

        // Create a loop for reading the files

        ArrayList<String> projFiles = new ArrayList<>();

        for (String s : files) {
            projFiles.add(file.getAbsolutePath() + '\\' + s);
        }

        int rows = files.length;
        int cols = files.length;
        float[][] scores = new float[cols][rows];

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
        matrix.setGridLinesVisible(true);
        for (int x = 0; x < projFiles.size(); x++) {
            for (int y = 0; y < projFiles.size(); y++) {
                String text = String.format("%.2f", scores[x][y]);
                Label label = new Label(text);
                matrix.add(label, x, y);
            }
        }
    }
}
