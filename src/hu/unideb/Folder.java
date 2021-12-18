package hu.unideb;

import java.util.ArrayList;
import java.util.List;

public class Folder {
    private String folderName;
    private List<Folder> subFolders;
    private List<String> files;
    private int depth;

    public Folder(String folderName, int depth) {
        this.folderName = folderName;
        this.subFolders = new ArrayList<>();
        this.files = new ArrayList<>();
        this.depth = depth;
    }

    public String getFolderName() {
        return folderName;
    }

    public List<Folder> getSubFolders() {
        return subFolders;
    }

    public List<String> getFiles() {
        return files;
    }

    public int getDepth() {
        return depth;
    }

    public void addSubfolder(Folder subfolder) {
        subFolders.add(subfolder);
    }

    public void addFile(String file) {
        files.add(file);
    }

}
