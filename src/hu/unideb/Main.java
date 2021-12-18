package hu.unideb;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Error: provide the path of a directory");
            System.exit(1);
        }
        String resourcesFolder = args[0];
        FileUtils.deleteHTMLFiles(resourcesFolder);
        Folder folder = FileUtils.readDirectory(resourcesFolder);
        Html.createHTMLFromImagesAndFolders(resourcesFolder, folder);
    }
}
