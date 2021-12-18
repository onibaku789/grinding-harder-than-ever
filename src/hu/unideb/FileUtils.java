package hu.unideb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;


public class FileUtils {
    public static Folder readDirectory(String path) {
        return readDirectory(path, new Folder("", 0), 0); // a Folder-t példányosítjuk egy üres névvel és 0 mélységgel, mivel ez a gyökér mappa
    }

    private static Folder readDirectory(String path, Folder rootFolder, int depth) {
        File root = new File(path);
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isDirectory() && isDirectoryContainsImage(file)) {
                Folder subfolder = new Folder(file.getName(), depth + 1);
                rootFolder.addSubfolder(subfolder);
                readDirectory(file.getAbsolutePath(), subfolder, depth + 1);
            } else if (file.isFile() && isFileImage(file)) {
                rootFolder.addFile(file.getName());
            }
        }
        return rootFolder;
    }

    private static boolean isFileImage(File file) {
        try {
            String mimetype = Files.probeContentType(file.toPath());
            return mimetype.contains("image");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    private static boolean isDirectoryContainsImage(File directory) {
        for (File file : directory.listFiles()) {
            if (file.isFile() && isFileImage(file)) {
                return true;
            }
        }
        return false;
    }


    public static void writeFile(String path, String fileName, String fileContent) {
        File resultFolder = new File(path);
        resultFolder.mkdirs();
        try (PrintWriter writer = new PrintWriter(resultFolder.getAbsolutePath() + "\\" + fileName)) {
            writer.println(fileContent);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void deleteHTMLFiles(String resultFolderPath) {
        File resultFolder = new File(resultFolderPath);
        if (resultFolder.exists()) {
            for (File file : resultFolder.listFiles()) {
                if (file.isDirectory()) {
                    deleteHTMLFiles(file.getAbsolutePath());
                } else if (file.isFile() && file.getName().contains(".html")) {
                    file.delete();
                }
            }
        }
    }
}
