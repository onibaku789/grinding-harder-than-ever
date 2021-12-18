package hu.unideb;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1 || args[0].contains(".")) {
            System.out.println("Error: provide the path of a directory");
            System.exit(1);
        }
        String resourcesFolder = args[0];
        FileUtils.deleteResultFolder(resourcesFolder);
        Folder folder = FileUtils.readDirectory(resourcesFolder);
        Html.createHTMLFromImagesAndFolders(resourcesFolder, folder);
    }
}
