package FileExplorer;

import java.io.File;

import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


/**
 * Classe contenant la methode permettant de parcourir les repertoires et d'afficher les fichiers Python.
 * @author Ayoub ABDELLI
 * @since 04-08-2025
 */
public class DirectoryManager {

    public DirectoryManager() {
        // Constructeur de la classe DirectoryManager
    }

    /**
     * Methode permettant de lister les fichiers d'un repertoire donne
     * et d'afficher les fichiers Python.
     * 
     * @param link Le chemin du repertoire a explorer.
     * @throws IOException En cas d'erreur d'entree/sortie.

     */
    public static void listFolders(String link) throws IOException{
        try {
            // Cree un objet File representant le repertoire a explorer
            File file = new File(link);

            // Recupere la liste des fichiers et sous-repertoires dans le repertoire
            File[] files = file.listFiles();

            if (files != null) {
                // Parcours de chaque element dans le repertoire
                for (int i = 0; i < files.length; i++) {
                    // Verifie si l'element est un sous-repertoire
                    if (files[i].isDirectory()) {
                       
                        // Appelle recursivement la methode listFolders pour explorer le sous-repertoire
                        listFolders(files[i].getAbsolutePath());
                    } else if (isPythonFile(files[i])) {
                        // Si l'element est un fichier Python, affiche les informations
                        System.out.println("- Dossier courant: " + files[i].getParent());
                        System.out.println("--------------------");
                        System.out.println("- Nom du fichier: " + files[i].getName());
                        System.out.println("--------------------");
                        System.out.println("- Son adresse: " + files[i].getAbsolutePath());
                    }
                }
            }
        } catch (IOException e) {
            // Gere les erreurs d'entree/sortie
            e.printStackTrace();
        }
    }

    /**
     * Verifie si le fichier est de type Python en utilisant le type MIME.
     * 
     * @param file Le fichier a verifier.
     * @return true si le fichier est de type Python, false sinon.
     * @throws IOException En cas d'erreur d'entree/sortie.
     */
    public static boolean isPythonFile(File file) throws IOException {
        if (file != null && file.exists()) {
            // Tente d'obtenir le type MIME du fichier
            String mimeType = Files.probeContentType(file.toPath());

            // Verifie si le type MIME correspond a "text/x-python"
            if (mimeType != null && mimeType.equals("text/x-python")) {
                return true;
            } else {
                // Comme plan de secours, verifie l'extension du fichier
                String fileName = file.getName();
                return fileName.endsWith(".py");
            }
        }
        return false;
    }
    /**
     * Retourne une liste de fichiers Python dans un repertoire.
     *
     * @param cheminDossier Le chemin du repertoire a explorer.
     * @return Une liste de fichiers Python dans le repertoire.
     * @throws IOException Si une erreur d'entree/sortie se produit.
     */
    public static List<File> listFiles(String cheminDossier) throws IOException {
        List<File> pythonFiles = new ArrayList<>();

        try {
            // Cree un objet File representant le repertoire a explorer
            File file = new File(cheminDossier);

            // Recupere la liste des fichiers et sous-repertoires dans le repertoire
            File[] files = file.listFiles();

            if (files != null) {
                // Parcours de chaque element dans le repertoire
                for (int i = 0; i < files.length; i++) {
                    // Verifie si l'element est un sous-repertoire
                    if (files[i].isDirectory()) {
                        // Appelle recursivement la methode listFiles pour explorer le sous-repertoire
                        pythonFiles.addAll(listFiles(files[i].getAbsolutePath()));
                    } else if (isPythonFile(files[i])) {
                        // Si l'element est un fichier Python, ajoute-le a la liste
                        pythonFiles.add(files[i]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pythonFiles;
    }

}
