package softwareSimilarityChecker;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class mainController {
    private Stage stage;
    // Panes
    public AnchorPane mainMenuPane;
    // Shapes
    public Circle circleGIF;

    // Containers
    public TextField folderTextField;

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

    public void setSearchButton() {
        Stage resourceStage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        file = directoryChooser.showDialog(resourceStage);
        if (file != null) {
            folderTextField.setText(file.toString());
            continueButton.setDisable(false);
        } else if (!folderTextField.getText().isEmpty()) {
            file = new File(folderTextField.getText());
        }
    }

    public void setContinueButton() {
        folderTextField.setVisible(false);
        searchButton.setVisible(false);
        continueButton.setVisible(false);
        backToMainMenuButton.setVisible(false);

        checkerButton.setVisible(true);
        metricsButton.setVisible(true);
        backToSearchMenuButton.setVisible(true);
    }

    public void setBackToMainMenuButton() {
        folderTextField.clear();

        folderTextField.setVisible(false);
        searchButton.setVisible(false);
        continueButton.setVisible(false);
        backToMainMenuButton.setVisible(false);

        startButton.setVisible(true);
        aboutButton.setVisible(true);
        exitButton.setVisible(true);
    }

    public void setCheckerButton() throws IOException {
        checkerButton.setVisible(false);
        metricsButton.setVisible(false);
        backToSearchMenuButton.setVisible(false);

        stage.close();
        Stage matrixStage = new Stage();
        matrixStage.setMaximized(true);
        FXMLLoader matrixLoader = new FXMLLoader();
        matrixLoader.setLocation(getClass().getResource("MatrixUI.fxml"));
        Parent matrixRoot = matrixLoader.load();
        matrixController controller = matrixLoader.getController();
        controller.setData(folderTextField, file, matrixStage);
        Scene parentRoot = new Scene(matrixRoot);
        matrixStage.setTitle("Code Plagiarism Checker");
        matrixStage.setResizable(false);
        matrixStage.setScene(parentRoot);
        matrixStage.show();
    }

    public void setMetricsButton() throws IOException {
        checkerButton.setVisible(false);
        metricsButton.setVisible(false);
        backToSearchMenuButton.setVisible(false);

        stage.close();
        Stage metricsStage = new Stage();
        metricsStage.setMaximized(true);
        FXMLLoader metricsLoader = new FXMLLoader();
        metricsLoader.setLocation(getClass().getResource("MetricsUI.fxml"));
        Parent metricsRoot = metricsLoader.load();
        metricsController controller = metricsLoader.getController();
        controller.setData(folderTextField, file, metricsStage);
        Scene parentRoot = new Scene(metricsRoot);
        metricsStage.setTitle("Code Plagiarism Checker");
        metricsStage.setResizable(false);
        metricsStage.setScene(parentRoot);
        metricsStage.show();
    }

    public void setBackToSearchMenuButton() {
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
}
