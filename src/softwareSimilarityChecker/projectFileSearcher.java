package softwareSimilarityChecker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class projectFileSearcher {
    ArrayList<String> projFiles = new ArrayList<>();

    public ArrayList<File[]> projectFileSearcher(File file){
        ArrayList<String> allFiles = new ArrayList<>();
        ArrayList<File[]> filesPerFolder = new ArrayList<>();
        // Gets Folder Paths of Java/CPP Files
        allFiles= findProjFile(file.getAbsoluteFile());
        // Array of File[] == files per folder
        filesPerFolder = sortByFolder(allFiles);
        System.out.println("\n");
        for (int x = 0; x < filesPerFolder.size(); x++) {
            File[] proj1 = filesPerFolder.get(x);
            for (File f : proj1){
                System.out.println(f.getName());
            }
            System.out.println("\n");
        }
        return filesPerFolder;
    }

    public ArrayList<String> findProjFile(File file) {
        File[] list = file.listFiles();
        String name;
        if (list != null) {
            for (File fil : list) {
                name = fil.getName();
                if (fil.isDirectory()) {
                    System.out.println(fil.getName());
                    findProjFile(fil);
                } else if (name.contains(".java") || name.contains(".cpp")) {
                    if(fil.getParent().contains("sample") || fil.getParent().contains("src") || fil.getParent().contains("Files")){
                        projFiles.add(fil.getParent());
                        return projFiles;
                    }
                }
            }
        }
        /*for(String s : projFiles){
            System.out.println(s);
        }*/
        return projFiles;
}



    public ArrayList<File[]> sortByFolder(ArrayList<String> files){
        ArrayList<File[]> byFolder = new ArrayList<>();
        String folderName ="";
        for(int x=0; x <files.size(); x++){
            String s = files.get(x);
            if(s != folderName){
                File file = new File(s);
                File[] getFiles = file.listFiles();
                byFolder.add(getFiles);
            }
            folderName = s;
        }
        return byFolder;
    }


}
