package softwareSimilarityChecker;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class mainController {
    private Stage stage;
    // Panes
    public ScrollPane scrollPane;
    public AnchorPane mainMenuPane;
    //public GridPane matrix;

    // Shapes
    public Circle circleGIF;

    // Containers
    public TextField folderTextField;
    public Label scoreLabel = new Label();

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

    public void initialize() {
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

    public void setCheckerButton(ActionEvent actionEvent) throws IOException {
        checkerButton.setVisible(false);
        metricsButton.setVisible(false);
        backToSearchMenuButton.setVisible(false);

        stage.close();
        Stage matrixStage = new Stage();
        FXMLLoader matrixLoader = new FXMLLoader();
        matrixLoader.setLocation(getClass().getResource("MatrixUI.fxml"));
        Parent matrixRoot = matrixLoader.load();
        matrixController controller = matrixLoader.getController();
        controller.setData(folderTextField, file);
        Scene parentRoot = new Scene(matrixRoot);
        matrixStage.setTitle("Similarity Matrix");
        matrixStage.setResizable(false);
        matrixStage.setScene(parentRoot);
        matrixStage.show();
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

    /*
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

     */
}
