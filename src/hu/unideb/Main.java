package hu.unideb;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1 || args[0].contains(".")) {
            System.out.println("Error: provide the path of a directory");
            System.exit(1);
        }
        String resourcesFolder = args[0];
        FileUtils.deleteResultFolder(resourcesFolder);
        Folder folder = FileUtils.readDirectory(resourcesFolder, new Folder("", 0), 0); // a Folder-t példányosítjuk egy üres névvel és 0 mélységgel, mivel ez a gyökér mappa
        Html.createHTMLFromImagesAndFolders(resourcesFolder, folder);
    }
}
