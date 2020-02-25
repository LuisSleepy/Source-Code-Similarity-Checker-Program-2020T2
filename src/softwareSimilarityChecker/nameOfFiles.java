package softwareSimilarityChecker;

import javafx.beans.property.SimpleStringProperty;
public class nameOfFiles {
    private SimpleStringProperty fileName;

    public nameOfFiles(String fileName) {
        this.fileName = new SimpleStringProperty(fileName);
    }

    public String getFileName() {
        return fileName.get();
    }

    public void setFileName(SimpleStringProperty fileName) {
        this.fileName = fileName;
    }
}
