package softwareSimilarityChecker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class matrixController {
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
    @FXML private GridPane matrix;
    // A container for the file (folder) chosen
    @FXML private HBox bottomBox;
    @FXML private File file;
    // For the entire stage of the matrix
    @FXML private Stage matrixStage;

    // Container for the highest scores
    private ArrayList<Float> highestScores = new ArrayList<>();
    // Container for the two compared files in relation to the stored highest scores
    private ArrayList<String> namesOfHighestScores = new ArrayList<>();
    // Container for the names of the files inside the chosen folder
    private String[] files;

    // A method for the initializer of the table for the list of names of the files inside the chosen folder
    private void setFilesTableView() {
        filesTableColumn.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        filesTableView.setItems(getFileNames(files));
    }

    // A method for initializing the table displaying the top ten comparisons with the highest scores
    private void setHighestScoresDataTableView() {
        highestScoresDataTableColumn.setCellValueFactory(new PropertyValueFactory<>("nameAndScore"));
        highestScoresDataTableView.setItems(getHighestScoresData(namesOfHighestScores));
    }

    // A method for creating the list of names of the files inside the chosen folder
    public ObservableList<nameOfFiles> getFileNames(String[] names) {
        ObservableList<nameOfFiles> fileNames = FXCollections.observableArrayList();

        // Traverses through all the names of the files
        for (int i = 0; i < names.length; i++) {
            fileNames.add(new nameOfFiles(i + 1, names[i]));
        }
        return fileNames;
    }

    // A method for creating the list of the top ten comparisons with the highest scores
    private ObservableList<highestScoresData> getHighestScoresData(ArrayList<String> namesAndScores) {
        ObservableList<highestScoresData> data = FXCollections.observableArrayList();

        // Traverses through all the names of the highest comparisons with the corresponding scores
        for (String namesAndScore : namesAndScores) {
            data.add(new highestScoresData(namesAndScore));
        }
        return data;
    }

    public void setData(TextField folderTextField, File file, Stage matrixStage) {
        this.folderTextField = folderTextField;
        projectTitle.setText("Folder Name: " + folderTextField.getText());
        this.file = file;
        this.matrixStage = matrixStage;
        checkOnAction();
        setFilesTableView();
        setHighestScoresDataTableView();
    }

    private void checkOnAction() {
        //bottomBox.setMaxWidth(matrixStage.getMaxWidth());
        //bottomBox.setMaxHeight(matrixStage.getMaxHeight());
        projectFileSearcher getF = new projectFileSearcher();
        if (file == null) {
            folderTextField.setText("No File Directory Chosen");
        } else {
            gridColorMaker gcm = new gridColorMaker();
            // Initializes the matrix

            // Stores the names of the files currently handled by "file"
            files = file.list();
            assert files != null;

            matrix = gcm.matrixGridPaneMaker(5, 5, files);

            // Create a loop for reading the files
            ArrayList<File[]> projFiles = getF.projectFileSearcher(file);
            int rows;
            int cols;
            float[][] scores;
            similarityChecker sc = new similarityChecker();
            getFileInArray gFA = new getFileInArray();
            float simScore, totalSimScore = 0, totalCount = 0;

            // Puts a file on hold and compare it to the rest of the files, also with itself

            // All Source Code in One Folder
            if (projFiles.size() == 1) {
                File[] proj1 = projFiles.get(0);
                File[] proj2 = projFiles.get(0);
                rows = proj1.length;
                cols = proj2.length;
                scores = new float[cols][rows];
                for (int x = 0; x < proj1.length; x++) {
                    for (int y = 0; y < proj2.length; y++) {
                        File f1 = gFA.getFileInArray(proj1, x);
                        File f2 = gFA.getFileInArray(proj2, y);
                        try {
                            simScore = sc.check(f1, f2);
                            //System.out.print(String.format("%.2f", simScore) + "\t");
                            scores[x][y] = simScore;

                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                    }
                    //System.out.print("\n");
                }
                // Instantiates gridColorMaker
                for (int x = 0; x < proj1.length; x++) {
                    for (int y = 0; y < proj2.length; y++) {
                        // Creates a colored rectangle using the checkColor method of gridColorMaker
                        StackPane coloredScore = gcm.matrixCheckColor(scores[x][y]);
                        // Adds the rectangle in a to-be-created cell of the gridPane
                        matrix.add(coloredScore, x + 1, y + 1);

                        // for Top 10 highest scores
                        if (x != y) {
                            System.out.println(highestScores.size());
                            if (highestScores.size() == 0) {
                                highestScores.add(scores[x][y]);
                                namesOfHighestScores.add(files[x] + " and " + files[y] + " : " + scores[x][y]);
                            } else if (highestScores.size() < 10) {
                                for (int i = 0; i < highestScores.size(); i++) {
                                    if (Math.max(highestScores.get(i), scores[x][y]) == scores[x][y]) {
                                        highestScores.add(i, scores[x][y]);
                                        namesOfHighestScores.add(i, files[x] + " and " + files[y] + " : " + scores[x][y]);
                                        break;
                                    }
                                }
                            } else if (highestScores.size() == 10) {
                                for (int i = 0; i < highestScores.size(); i++) {
                                    if (Math.max(highestScores.get(i), scores[x][y]) == scores[x][y]) {
                                        highestScores.remove(highestScores.get(i));
                                        highestScores.add(i, scores[x][y]);
                                        namesOfHighestScores.remove(namesOfHighestScores.get(i));
                                        namesOfHighestScores.add(i, files[x] + " and " + files[y] + " : " + scores[x][y]);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //By Folders
            else {
                rows = projFiles.size();
                cols = projFiles.size();
                scores = new float[cols][rows];
                for (int x = 0; x < projFiles.size(); x++) {
                    for (int y = 0; y < projFiles.size(); y++) {
                        File[] proj1 = projFiles.get(x);
                        File[] proj2 = projFiles.get(y);
                        try {
                            for (File f1 : proj1) {
                                for (File f2 : proj2) {
                                    if(x == y){
                                        totalSimScore = 1;
                                        totalCount = 1;
                                    }else{
                                        float simScore1 = sc.check(f1, f2);
                                        float simScore2 = sc.check(f2, f1);
                                        totalSimScore = totalSimScore +(simScore1+simScore2)/2;
                                        totalCount++;
                                    }
                                }
                            }
                            float aveSimScore = totalSimScore / totalCount;
                            //System.out.print(String.format("%.2f", aveSimScore) + "\t");
                            scores[x][y] = scores[y][x]= aveSimScore;
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                    }
                    //System.out.print("\n");
                }

                // Instantiates gridColorMaker
                for (int x = 0; x < projFiles.size(); x++) {
                    for (int y = 0; y < projFiles.size(); y++) {
                        // Creates a colored rectangle using the checkColor method of gridColorMaker
                        StackPane coloredScore = gcm.matrixCheckColor(scores[x][y]);
                        // Adds the rectangle in a to-be-created cell of the gridPane
                        matrix.add(coloredScore, x + 1, y + 1);

                        // for Top 10 highest scores
                        if (x != y) {
                            System.out.println(highestScores.size());
                            if (highestScores.size() == 0) {
                                highestScores.add(scores[x][y]);
                                namesOfHighestScores.add(files[x] + " and " + files[y] + " : " + scores[x][y]);
                            } else if (highestScores.size() < 10) {
                                for (int i = 0; i < highestScores.size(); i++) {
                                    if (Math.max(highestScores.get(i), scores[x][y]) == scores[x][y]) {
                                        highestScores.add(i, scores[x][y]);
                                        namesOfHighestScores.add(i, files[x] + " and " + files[y] + " : " + scores[x][y]);
                                        break;
                                    }
                                }
                            } else if (highestScores.size() == 10) {
                                for (int i = 0; i < highestScores.size(); i++) {
                                    if (Math.max(highestScores.get(i), scores[x][y]) == scores[x][y]) {
                                        highestScores.remove(highestScores.get(i));
                                        highestScores.add(i, scores[x][y]);
                                        namesOfHighestScores.remove(namesOfHighestScores.get(i));
                                        namesOfHighestScores.add(i, files[x] + " and " + files[y] + " : " + scores[x][y]);
                                        break;
                                    }
                                }
                            }
                        }
                        // For testing of top 10 highest scores
                    }
                }
            }

            // Sets up matrix as a content of the scrollPane
            scrollPane.setContent(matrix);
        }

    }

    public void setHomeButton() throws IOException {
        matrixStage.close();
        Stage mainStage = new Stage();
        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(getClass().getResource("MainUI.fxml"));
        Parent matrixRoot = mainLoader.load();
        mainController controller = mainLoader.getController();
        controller.initStage(mainStage);
        Scene parentRoot = new Scene(matrixRoot);
        mainStage.setTitle("Code Plagiarism Checker");
        mainStage.setResizable(false);
        mainStage.setScene(parentRoot);
        mainStage.show();

    }

    public void setGoToMetricsButton(ActionEvent actionEvent) throws IOException {
        matrixStage.close();
        Stage metricsStage = new Stage();
        FXMLLoader matrixLoader = new FXMLLoader();
        matrixLoader.setLocation(getClass().getResource("MetricsUI.fxml"));
        Parent metricsRoot = matrixLoader.load();
        metricsController controller = matrixLoader.getController();
        metricsStage.setMaximized(true);
        controller.setData(folderTextField, file, metricsStage);
        Scene parentRoot = new Scene(metricsRoot);
        metricsStage.setTitle("Code Plagiarism Checker");
        metricsStage.setResizable(false);
        metricsStage.setScene(parentRoot);
        metricsStage.show();
    }
}
