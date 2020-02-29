package softwareSimilarityChecker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class metricsController {
    // For the table displaying the name of the files being compared
    @FXML private TableView<nameOfFiles> filesTableView;
    @FXML private TableColumn<nameOfFiles, String> filesTableColumn;
    // For the table displaying the top 10 comparison with the highest scores
    @FXML private TableView<highestScoresData> highestScoresDataTableView;
    @FXML private TableColumn<highestScoresData, String> highestScoresDataTableColumn;
    // For displaying the absolute path of the chosen folder while on the "Search" or "Continue" screen"
    @FXML private TextField folderTextField;
    // For displaying the absolute path of the chosen folder while on the matrix screen
    @FXML private Label projectTitle;
    // A button for going back to the main menu screen
    // For the scroll pane displaying the GridPane of the matrix
    @FXML private ScrollPane scrollPane;
    // For displaying the matrix
    @FXML private GridPane metrics;
    @FXML File file;
    @FXML Stage metricsStage;
    @FXML Button homeButton;
    private String[] files;

    public void setData(TextField folderTextField, File file, Stage metricsStage) {
        this.folderTextField = folderTextField;
        projectTitle.setText(folderTextField.getText());
        this.file = file;
        this.metricsStage = metricsStage;
        metricsOnAction();
    }
    private void metricsOnAction() {
        projectFileSearcher getF = new projectFileSearcher();
        if (file == null) {
            folderTextField.setText("No File Directory Chosen");
        } else {
            gridColorMaker gcm = new gridColorMaker();
            // Initializes the matrix

            // Stores the names of the files currently handled by "file"
            files = file.list();
            assert files != null;

            metrics = gcm.metricsGridPaneMaker(50, 25, files);

            // Create a loop for reading the files
            ArrayList<File[]> projFiles = getF.projectFileSearcher(file);
            softwareMetrics sm = new softwareMetrics();
            getFileInArray gFA = new getFileInArray();

            if (projFiles.size() == 1) {
                File[] project = projFiles.get(0);
                for (int i = 0; i < project.length; i++) {
                    File projectFile = gFA.getFileInArray(project, i);
                    sm.getMetrics(projectFile);
                    StackPane programLengthStackPane = gcm.intMetricScoreMaker(sm.getProgramLength());
                    metrics.add(programLengthStackPane, 1, i + 1);
                    StackPane vocabularySizeStackPane = gcm.intMetricScoreMaker(sm.getVocabularySize());
                    metrics.add(vocabularySizeStackPane, 2, i + 1);
                    StackPane programVolumeStackPane = gcm.floatMetricsScoreMaker(sm.getProgramVolume());
                    metrics.add(programVolumeStackPane, 3, i + 1);
                    StackPane difficultyStackPane = gcm.floatMetricsScoreMaker(sm.getDifficulty());
                    metrics.add(difficultyStackPane, 4, i + 1);
                    StackPane programLevelStackPane = gcm.floatMetricsScoreMaker(sm.getProgramLevel());
                    metrics.add(programLevelStackPane, 5, i + 1);
                    StackPane effortToImplementStackPane = gcm.floatMetricsScoreMaker(sm.getEffortToImplement());
                    metrics.add(effortToImplementStackPane, 6, i + 1);
                    StackPane timeToImplementStackPane = gcm.floatMetricsScoreMaker(sm.getTimeToImplement());
                    metrics.add(timeToImplementStackPane, 7, i + 1);
                    StackPane deliveredBugsStackPane = gcm.floatMetricsScoreMaker(sm.getDeliveredBugs());
                    metrics.add(deliveredBugsStackPane, 8, i + 1);
                }
            } else {
                int totalProgramLength = 0, totalVocabularySize = 0, totalCount = 0;
                float totalProgramVolume = 0, totalDifficulty = 0, totalProgramLevel = 0, totalEffortToImpelement = 0, totalTimeToImplement = 0, totalDeliveredBugs = 0;
                for (int i = 0; i < projFiles.size(); i++) {
                    File[] proj1 = projFiles.get(i);
                    for (File file : proj1) {
                        sm.getMetrics(file);
                        totalProgramLength = sm.getProgramLength();
                        totalVocabularySize = sm.getVocabularySize();
                        totalProgramVolume = sm.getProgramVolume();
                        totalDifficulty = sm.getDifficulty();
                        totalProgramLevel = sm.getProgramLevel();
                        totalEffortToImpelement = sm.getEffortToImplement();
                        totalTimeToImplement = sm.getTimeToImplement();
                        totalDeliveredBugs = sm.getDeliveredBugs();
                        totalCount++;
                    }
                    totalProgramLength = totalProgramLength / totalCount;
                    totalVocabularySize = totalVocabularySize / totalCount;
                    totalProgramVolume = totalProgramVolume / totalCount;
                    totalDifficulty = totalDifficulty / totalCount;
                    totalProgramLevel = totalProgramLevel / totalCount;
                    totalEffortToImpelement = totalEffortToImpelement / totalCount;
                    totalTimeToImplement = totalTimeToImplement / totalCount;
                    totalDeliveredBugs = totalDeliveredBugs / totalCount;

                    StackPane programLengthStackPane = gcm.intMetricScoreMaker(sm.getProgramLength());
                    metrics.add(programLengthStackPane, 1, i + 1);
                    StackPane vocabularySizeStackPane = gcm.intMetricScoreMaker(sm.getVocabularySize());
                    metrics.add(vocabularySizeStackPane, 2, i + 1);
                    StackPane programVolumeStackPane = gcm.floatMetricsScoreMaker(sm.getProgramVolume());
                    metrics.add(programVolumeStackPane, 3, i + 1);
                    StackPane difficultyStackPane = gcm.floatMetricsScoreMaker(sm.getDifficulty());
                    metrics.add(difficultyStackPane, 4, i + 1);
                    StackPane programLevelStackPane = gcm.floatMetricsScoreMaker(sm.getProgramLevel());
                    metrics.add(programLevelStackPane, 5, i + 1);
                    StackPane effortToImplementStackPane = gcm.floatMetricsScoreMaker(sm.getEffortToImplement());
                    metrics.add(effortToImplementStackPane, 6, i + 1);
                    StackPane timeToImplementStackPane = gcm.floatMetricsScoreMaker(sm.getTimeToImplement());
                    metrics.add(timeToImplementStackPane, 7, i + 1);
                    StackPane deliveredBugsStackPane = gcm.floatMetricsScoreMaker(sm.getDeliveredBugs());
                    metrics.add(deliveredBugsStackPane, 8, i + 1);
                }
            }

            scrollPane.setContent(metrics);
        }



    }

    public void setHomeButton() throws IOException {
        metricsStage.close();
        Stage mainStage = new Stage();
        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("MainUI.fxml"));
        Parent metricsRoot = mainLoader.load();
        mainController controller = mainLoader.getController();
        controller.initStage(mainStage);
        Scene parentRoot = new Scene(metricsRoot);
        mainStage.setTitle("Code Plagiarism Checker");
        mainStage.setResizable(false);
        mainStage.setScene(parentRoot);
        mainStage.show();
    }

    public void setGoToMatrixButton(ActionEvent actionEvent) throws IOException {
        metricsStage.close();
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
}

