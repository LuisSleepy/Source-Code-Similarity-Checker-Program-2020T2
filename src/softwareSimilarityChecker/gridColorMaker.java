package softwareSimilarityChecker;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;

public class gridColorMaker {
    public GridPane matrixGridPaneMaker(double vGap, double hGap, String[] names) {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(vGap);
        gridPane.setHgap(hGap);

        for (int i = 0; i < names.length; i++) {
            Label text = new Label(String.valueOf(i + 1));
            text.setFont(Font.font("Georgia", 12));
            GridPane.setHalignment(text, HPos.CENTER);
            gridPane.add(text, i + 1, 0);
        }

        for (int i = 0; i < names.length; i++) {
            Label text = new Label(String.valueOf(i + 1));
            text.setFont(Font.font("Georgia", 12));
            GridPane.setHalignment(text, HPos.CENTER);
            gridPane.add(text, 0, i + 1);
        }
        return gridPane;

    }
    public StackPane matrixCheckColor(float value) {
        // Creates a rectangle with a specified width and height
        Rectangle scoreRectangle = new Rectangle();
        scoreRectangle.setWidth(50);
        scoreRectangle.setHeight(25);

        //value = Math.round(value);
        // Assigns a color for the rectangle based on the specified score
        if (value >= 0 && value < 0.10) {
            scoreRectangle.setFill(Color.WHITE);
        } else if (value >= 0.10 && value < 0.20) {
            scoreRectangle.setFill(Color.rgb(255,255,255));
        } else if (value >= 0.20 && value < 0.30) {
            scoreRectangle.setFill(Color.rgb(252, 216, 208));
        } else if (value >= 0.30 && value < 0.40) {
            scoreRectangle.setFill(Color.rgb(253, 164, 145));
        } else if (value >= 0.40 && value < 0.50) {
            scoreRectangle.setFill(Color.rgb(250, 112, 82));
        } else if (value >= 0.50 && value < 0.60) {
            scoreRectangle.setFill(Color.rgb(254, 63, 22));
        } else if (value >= 0.60 && value < 0.70) {
            scoreRectangle.setFill(Color.rgb(220, 39, 0));
        } else if (value >= 0.70 && value < 0.80) {
            scoreRectangle.setFill(Color.rgb(173, 31, 0));
        } else if (value >= 0.80 && value < 0.90) {
            scoreRectangle.setFill(Color.rgb(120, 25, 0));
        } else {
            scoreRectangle.setFill(Color.rgb(91, 16, 0));
        }

        String scoreString = String.valueOf(value);
        scoreString = scoreString.substring(0, Math.min(scoreString.length(), 4));
        Text scoreText = new Text(scoreString);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(scoreRectangle, scoreText);

        return stackPane;
    }
    public GridPane metricsGridPaneMaker(double vGap, double hGap, String[] names) {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(vGap);
        gridPane.setHgap(hGap);

        ArrayList<String> columnTitles = new ArrayList<>();
        columnTitles.add("File Name");
        columnTitles.add("Program Length");
        columnTitles.add("Vocabulary Size");
        columnTitles.add("Program Volume");
        columnTitles.add("Difficulty");
        columnTitles.add("Program Level");
        columnTitles.add("Effort to Implement");
        columnTitles.add("Time to Implement");
        columnTitles.add("No. of Delivered Bugs");

        for (int i = 0; i < columnTitles.size(); i++) {
            Label text = new Label();
            text.setText(columnTitles.get(i));
            text.setFont(Font.font("Georgia", 12));
            GridPane.setHalignment(text, HPos.CENTER);
            gridPane.add(text, i, 0);
        }

        for (int i = 0; i < names.length; i++) {
            Label text = new Label();
            text.setText(names[i]);
            text.setFont(Font.font("Georgia", 12));
            GridPane.setHalignment(text, HPos.CENTER);
            gridPane.add(text, 0, i + 1);
        }

        return gridPane;
    }
    public StackPane intMetricScoreMaker(int value) {
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Color.WHITE);
        rectangle.setWidth(50);
        rectangle.setHeight(25);

        String intMetricScore = String.valueOf(value);
        intMetricScore = intMetricScore.substring(0, Math.min(intMetricScore.length(), 4));
        Text scoreText = new Text(intMetricScore);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rectangle, scoreText);

        return stackPane;
    }
    public StackPane floatMetricsScoreMaker(float value) {
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Color.WHITE);
        rectangle.setWidth(50);
        rectangle.setHeight(25);

        String intMetricScore = String.valueOf(value);
        intMetricScore = intMetricScore.substring(0, Math.min(intMetricScore.length(), 7));
        Text scoreText = new Text(intMetricScore);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rectangle, scoreText);

        return stackPane;
    }
}
