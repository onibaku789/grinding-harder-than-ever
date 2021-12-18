package hu.unideb;

import java.util.List;

public class Html {

    public static void createHTMLFromImagesAndFolders(String path, Folder rootFolder) {
        createHTMLFromFolders(path, rootFolder);
        createHTMLFromImages(path, rootFolder);
    }

    private static void createHTMLFromImages(String path, Folder rootFolder) {
        String previousImage = ""; // kezdetben nincs előző kép
        List<String> files = rootFolder.getFiles();
        for (int i = 0; i < files.size(); i++) {
            String actualImage = files.get(i); // aktuális kép neve kiterjesztéssel
            String imageName = actualImage.split("\\.")[0]; // aktuális kép neve kiterjesztés nélkül
            String nextImage = ""; // ha nincs több kép akkor a következő kép is üres
            if (i + 1 < files.size()) {
                nextImage = files.get(i + 1).split("\\.")[0] + ".html"; // ha a következő elem kisebb mint a lista mérete = van következő kép-> levesszük a kiterjesztés átrakjjuk htmlre ( ezt átírtahod jobbra)
            }
            if (i > 0) {
                previousImage = files.get(i - 1).split("\\.")[0] + ".html"; // ha nem az első képnél vagyunk akkor beállítjuk a kép nevét az előző képre (hasonlóan a fentinél)
            }

            writeHtmlFile(path, imageName, getImagePage(actualImage, previousImage, nextImage, rootFolder.getDepth()));
        }
    }

    private static void createHTMLFromFolders(String path, Folder rootFolder) {
        writeHtmlFile(path, "index", getDirectoryPage(rootFolder));
        for (Folder subFolder : rootFolder.getSubFolders()) {
            createHTMLFromImagesAndFolders(path + "\\" + subFolder.getFolderName(), subFolder);
        }
    }

    private static void writeHtmlFile(String path, String fileName, String fileContent) {
        if (fileName.isBlank()) {
            FileUtils.writeFile(path, "index.html", fileContent);
        } else {
            FileUtils.writeFile(path, fileName + ".html", fileContent);
        }
    }

    private static String getDirectoryPage(Folder folder) {
        String backToParentFolder = "href=\"..\\index.html\"";
        if (folder.getDepth() == 0) {
            backToParentFolder = "href=\"index.html\"";
        }
        return "<!DOCTYPE html>" +
                "<html>" +
                "<body>" +
                "<h1><a href=" +
                "..\\".repeat(folder.getDepth()) +
                "index.html" +
                ">Start Page</a></h1>" +
                "<hr>" +
                "<p><a " + backToParentFolder + " >^^ </a></p>" +
                "<h2>Directories:</h2>" +
                "<p><ul>" +
                getFoldersAsHTMLList(folder) +
                "</ul></p>" +
                "<hr>" +
                "<h2>Images:</h2>" +
                "<p><ul>" +
                getImagesAsHTMLList(folder) +
                "</ul></p>" +
                "</body>" +
                "</html>";
    }

    private static String getImagePage(String fileName, String previousImage, String nextImage, int depth) {

        return "<!DOCTYPE html>" +
                "<html>" +
                "<style>" +
                "img {width: 35%;}" +
                "</style>" +
                "<body>" +
                "<h1><a href=" +
                "..\\".repeat(depth) + // annyiszor ismételjük meg a ..\\-t amilyen mélyen van a mappastruktúrában
                "index.html" +
                ">Start Page</a></h1>" +
                "<hr>" +
                "<div>" +
                "<p><a href=\"index.html\">^^</a></p>" +
                "<a href=\"" + previousImage + "\" > << </a>" + // hrefet beállítjuk az előző kép html oldalára
                "<b>" + fileName + "</b>" +  // aktuális kép neve kiterjesztéssel
                "<a href=\"" + nextImage + "\" > >> </a>" +// hrefet beállítjuk az következő kép html oldalára
                "</div>" +
                "<br><a href=" + nextImage + "><img src=\"" + fileName + "\"></a>" +
                "</body>" +
                "</html>";

    }

    private static String getFoldersAsHTMLList(Folder folder) {
        StringBuilder sb = new StringBuilder();
        for (Folder folder1 : folder.getSubFolders()) {
            sb.append("<li><a href =")
                    .append(folder1.getFolderName())
                    .append("/")
                    .append("index")
                    .append(".html >")
                    .append(folder1.getFolderName())
                    .append("</a></li>");
        }
        return sb.toString();
    }

    private static String getImagesAsHTMLList(Folder folder) {
        StringBuilder sb = new StringBuilder();
        for (String file : folder.getFiles()) {
            String filename = file.split("\\.")[0];
            sb.append("<li><a href =")
                    .append(filename)
                    .append(".html >")
                    .append(file)
                    .append("</a></li>");
        }
        return sb.toString();
    }
}
