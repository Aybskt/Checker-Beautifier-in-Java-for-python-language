package FileAnalyser;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import FileExplorer.DirectoryManager;


/**
 *Classe FileAnalyser contenant des methodes pour analyser les fichiers Python.
 *@author Ayoub ABDELLI
 *@since 06-09-2025
 */
public class FileAnalyser {

    private static final String ENCODAGE = "# -*- coding: utf-8 -*-";
    private static final String ENCODAGE2 = "#coding: utf-8";
    private static final String SHEBANG_PYTHON3 = "#!/usr/bin/python3";
    private static final String SHEBANG_PYTHON3_2 = "#!python3";

    /**
     * Analyse le fichier Python specifie pour determiner le nombre total de fonctions
     * et le nombre total de fonctions avec annotations de type.
     *
     * @param link Le chemin du fichier Python a analyser.
     */
    public void hasTypage(String link) {
        int functionCount = 0;
        int typeAnnotationCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(link))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("def") && !line.startsWith("#")) {
                    functionCount++;

                    // Verifier l'annotation de type
                    if (line.contains("->")) {
                        typeAnnotationCount++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Nombre total de fonctions : " + functionCount);
        System.out.println("Nombre total d'annotations de type : " + typeAnnotationCount);
    }
    /**
     * Verifie si le fichier Python specifie contient des annotations de type.
     *
     * @param link Le chemin du fichier Python a analyser.
     * @return true si des annotations de type sont presentes, false sinon.
     */
    public boolean hasTypageBool(String link) {
        try (BufferedReader br = new BufferedReader(new FileReader(link))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("def")&& !line.startsWith("#") &&  line.contains("->")) {
                    return true; // Retourne true si une annotation de type est trouvee
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Retourne false si aucune annotation de type n'est trouvee
    }
    

    /**
     * Verifie si le shebang est present dans le fichier Python specifie.
     *
     * @param link Le chemin du fichier Python a analyser.
     * @return true si le shebang est present, false sinon.
     */
    
    public boolean hasShebang(String link) {
        try (BufferedReader br = new BufferedReader(new FileReader(link))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith(SHEBANG_PYTHON3) ||line.trim().startsWith(SHEBANG_PYTHON3_2) ) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Verifie si l'encodage UTF-8 est present dans le fichier Python specifie.
     *
     * @param link Le chemin du fichier Python a analyser.
     * @return true si l'encodage UTF-8 est present, false sinon.
     */
    public boolean hasEncodage(String link) {
        try (BufferedReader br = new BufferedReader(new FileReader(link))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith(ENCODAGE) || line.trim().startsWith(ENCODAGE2)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Verifie si un fichier Python specifie contient des commentaires de type Pydoc.
     *
     * @param link Le chemin du fichier Python a analyser.
     * @return true si des commentaires Pydoc sont trouves, false sinon.
     */
    public boolean hasPyDoc(String link) {
        try (BufferedReader br = new BufferedReader(new FileReader(link))) {
            String line;
            boolean insideFunction = false;
            while ((line = br.readLine()) != null) {
            	// Verifie d'abord si nous sommes a l'interieur de la fonction
                if (line.contains("def") && !line.startsWith("#")) {
                    insideFunction = true;
                }

                if (insideFunction && (line.contains("@brief") || line.contains("@param") || line.contains("@return"))) {
                    return true; // On a trouvé un PyDoc, on peut retourner true immédiatement.
                }

                // Si on sort d'une fonction (par ex. ligne vide ou début d'une autre), on réinitialise.
                if (line.trim().isEmpty()) {
                    insideFunction = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Aucun PyDoc trouvé dans tout le fichier
    }
    

    /**
     * Compte le nombre de fonctions dans un fichier Python specifie.
     *
     * @param file Le fichier Python a analyser.
     * @return Le nombre de fonctions dans le fichier.
     */
    public static int CountFunction(File file) {
        int numFunc = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while (line != null) {
                if (line.startsWith("def") && !line.startsWith("#")) {
                    numFunc++;
                }
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numFunc;
    }

    /**
     * Compte le nombre de fonctions dans un fichier Python specifie qui ont des annotations de type.
     *
     * @param file Le fichier Python a analyser.
     * @return Le nombre de fonctions avec des annotations de type dans le fichier.
     */
    public static int CountAnnotatedFunctions(File file) {
        int numFunc = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while (line != null) {
                if (line.startsWith("def") && !line.startsWith("#")) {
                    if (line.contains("->")) {
                        numFunc++;
                    }
                }
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numFunc;
    }

    /**
     * Compte le nombre de fonctions dans un fichier Python specifie qui ont des commentaires Pydoc.
     *
     * @param file Le fichier Python a analyser.
     * @return Le nombre de fonctions avec des commentaires Pydoc dans le fichier.
     */
    public static int CountPydocFunc(File file) {
        int numFunc = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while (line != null) {
                if (line.startsWith("def")) {
                    // Ce test est fait pour ignorer les lignes vides
                    while ((line = br.readLine()) != null && line.trim().isEmpty()) {
                    }
                    
                    if (line != null && (line.trim().startsWith("\"\"\"!") || line.trim().startsWith("\"\"\""))) {
                        while ((line = br.readLine()) != null && !line.isEmpty()) {
                            if (line.contains("@brief") || line.contains("@param") || line.contains("@return")
                                    || line.contains("@author") || line.contains("@version")) {
                                numFunc++;
                                break;
                            }
                        }
                    }
                }
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numFunc;
    }

    /**
     * Compte le nombre de fichiers Python, parmi une liste donnee, qui possedent un shebang et une déclaration d encodage.
     *
     * @param allFilesPyStats La liste de fichiers a analyser.
     * @return Le nombre total de fichiers conformes (avec shebang et encodage).
     * @throws IOException Si une erreur survient lors de la lecture d'un fichier.
     */
    public static int calculateShebangAndEncoding(List<File> allFilesPyStats) throws IOException {
        int numFilesWithShebangAndEncoding = 0;
        FileAnalyser fileAnalyser = new FileAnalyser(); // Créer l'objet ici

        for (File file : allFilesPyStats) {
            if (DirectoryManager.isPythonFile(file)) {
                String path = file.getAbsolutePath();
                // Ne pas recréer l'objet ici
                boolean hasShebang = fileAnalyser.hasShebang(path);
                boolean hasEncoding = fileAnalyser.hasEncodage(path);
                if (hasShebang && hasEncoding) {
                    numFilesWithShebangAndEncoding++;
                }
            }
        }
        return numFilesWithShebangAndEncoding;
    }


    /**
     * Affiche les statistiques du repertoire, y compris le nombre de fichiers analyses,
     * le nombre total de fonctions, le pourcentage des fonctions avec annotations de type,
     * le pourcentage des fichiers avec shebang et encodage, et le pourcentage des fonctions avec commentaires Pydoc.
     *
     * @param link Le chemin du repertoire a analyser.
     * @throws IOException En cas d'erreur lors de la lecture des fichiers.
     */
    public static void stats(String link) throws IOException {
        File directory = new File(link);

        if (directory.isDirectory()) {
            List<File> allFilesPyStats = new ArrayList<>();
            getAllFilesForStats(directory, allFilesPyStats);

            int numFilesAnalyzed = 0;
            int numFunctions = 0;
            int numTypeAnnotatedFunctions = 0;
            int numFunctionsPydoc = 0;
            int numFilesWithShebang =0;
            int numFilesWithUtf8 = 0;



            for (File file : allFilesPyStats) {
                if (DirectoryManager.isPythonFile(file)) {
                    System.out.println("Analyse du fichier : " + file.getName());
                    numFilesAnalyzed++;

                    numFunctions += CountFunction(file);
                    numTypeAnnotatedFunctions += CountAnnotatedFunctions(file);
                    numFunctionsPydoc += CountPydocFunc(file);
                    FileAnalyser fileAnalyser = new FileAnalyser();
                    boolean hasShebang = fileAnalyser.hasShebang(file.getAbsolutePath());
                    boolean hasUtf8 = fileAnalyser.hasEncodage(file.getAbsolutePath());

                    if (hasShebang) {
						numFilesWithShebang++;
                    }

                    if (hasUtf8) {
						numFilesWithUtf8++;
                    }
                }
            }

            int numFilesWithShebangAndEncoding = calculateShebangAndEncoding(allFilesPyStats);

            // Verifier que le nombre total de fichiers n'est pas zero pour eviter la division par zero
            if (numFilesAnalyzed > 0) {
                double percentageTypeAnnotatedFunctions = percentage(numTypeAnnotatedFunctions, numFunctions);
                double percentageFilesWithShebangAndEncoding = percentage(numFilesWithShebangAndEncoding, numFilesAnalyzed);
                double percentageFunctionsPydoc = percentage(numFunctionsPydoc, numFunctions);

                System.out.println("Statistiques du repertoire : \"" + directory.getName() + "\"");
                System.out.println("- Nombre de fichiers analyses : " + numFilesAnalyzed);
                System.out.println("- Nombre total de fonctions : " + numFunctions);
                System.out.println("- Pourcentage des fonctions avec annotations de typage : " + percentageTypeAnnotatedFunctions + "%");
                System.out.println("- Nombre de fichiers avec shebang : " + numFilesWithShebang);
                System.out.println("- Nombre de fichiers avec encodage UTF-8 : " + numFilesWithUtf8);
                System.out.println("- Pourcentage des fichiers avec shebang et encodage presents en meme temps : " + percentageFilesWithShebangAndEncoding + "%");
                System.out.println("- Pourcentage des fonctions avec commentaires Pydoc : " + percentageFunctionsPydoc + "%");
            } else {
                System.out.println("Aucun fichier Python trouve dans le repertoire specifie.");
            }
        } else {
            System.out.println("Le repertoire est vide.");
        }
    }

    /**
     * Recupere tous les fichiers dans un repertoire et ses sous-repertoires et les ajoute a une liste.
     *
     * @param directory Le repertoire a parcourir.
     * @param fileList La liste des fichiers a laquelle les fichiers trouves seront ajoutes.
     */
    private static void getAllFilesForStats(File directory, List<File> fileList) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Recursion pour traiter les sous-repertoires
                    getAllFilesForStats(file, fileList);
                } else {
                    // Ajouter le fichier a la liste
                    fileList.add(file);
                }
            }
        }
    }

    /**
     * Calcule le pourcentage d'un nombre par rapport a un total.
     *
     * @param part La partie a calculer le pourcentage.
     * @param total Le total par rapport auquel le pourcentage sera calcule.
     * @return Le pourcentage de la partie par rapport au total.
     */
    public static double percentage(int part, int total) {
        if (total == 0) {
            return 0.0;
        }
        return ((double) part / total) * 100;
    }

}
