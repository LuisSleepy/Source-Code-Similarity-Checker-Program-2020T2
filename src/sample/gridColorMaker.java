package sample;

import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class gridColorMaker {
    public GridPane gridPaneMaker(double vGap, double hGap, String[] names) {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(vGap);
        gridPane.setHgap(hGap);

        for (int i = 0; i < names.length; i++) {
            Label text = new Label(names[i]);
            text.setFont(Font.font("Georgia", 12));
            GridPane.setHalignment(text, HPos.CENTER);
            gridPane.add(text, i + 1, 0);
        }

        for (int i = 0; i < names.length; i++) {
            Label text = new Label(names[i]);
            text.setFont(Font.font("Georgia", 12));
            GridPane.setHalignment(text, HPos.CENTER);
            gridPane.add(text, 0, i + 1);
        }
        return gridPane;

    }
    public Rectangle checkColor(float value) {
        // Creates a rectangle with a specified width and height
        Rectangle scoreRectangle = new Rectangle();
        scoreRectangle.setWidth(100);
        scoreRectangle.setHeight(50);

        // Assigns a color for the rectangle based on the specified score
        if (value >= 0 && value <= 0.10) {
            scoreRectangle.setFill(Color.BLUE);
        } else if (value > 0.10 && value <= 0.20) {
            scoreRectangle.setFill(Color.LIGHTBLUE);
        } else if (value > 0.20 && value <= 0.30) {
            scoreRectangle.setFill(Color.LIGHTGREEN);
        } else if (value > 0.30 && value <= 0.40) {
            scoreRectangle.setFill(Color.GREEN);
        } else if (value > 0.40 && value <= 0.50) {
            scoreRectangle.setFill(Color.GREENYELLOW);
        } else if (value > 0.50 && value <= 0.60) {
            scoreRectangle.setFill(Color.YELLOW);
        } else if (value > 0.60 && value <= 0.70) {
            scoreRectangle.setFill(Color.ORANGE);
        } else if (value > 0.70 && value <= 0.80) {
            scoreRectangle.setFill(Color.ORANGERED);
        } else if (value > 0.80 && value <= 0.90) {
            scoreRectangle.setFill(Color.RED);
        } else {
            scoreRectangle.setFill(Color.MAROON);
        }

        return scoreRectangle;
    }
}
