package softwareSimilarityChecker;

import javafx.beans.property.SimpleStringProperty;

public class highestScoresData {
    private SimpleStringProperty nameAndScore;

    public highestScoresData(String nameAndScore) {
        this.nameAndScore = new SimpleStringProperty(nameAndScore);
    }

    public String getNameAndScore() {
        return nameAndScore.get();
    }

    public void setNameAndScore(SimpleStringProperty nameAndScore) {
        this.nameAndScore = nameAndScore;
    }
}
