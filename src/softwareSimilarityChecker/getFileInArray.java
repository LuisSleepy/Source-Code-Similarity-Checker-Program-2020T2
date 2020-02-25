package softwareSimilarityChecker;

import java.io.File;

public class getFileInArray {

    public File getFileInArray(File[] fileArr, int index){
        int count=0;
        for(File file : fileArr){
            if(count == index){
                return file;
            }
            count++;
        }
        return null;
    }

}
