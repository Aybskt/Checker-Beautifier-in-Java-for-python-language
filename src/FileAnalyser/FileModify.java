package FileAnalyser;

import java.io.*;




/**
 * Cette classe contient des methodes permettant de modifier et analyser des fichiers Python.
 * @author Ayoub ABDELLI
 * @since 06-09-2025
 */
public class FileModify {

	private static final String ENCODAGE = "# -*- coding: utf-8 -*-";
    private static final String SHEBANG_PYTHON3 = "#!/usr/bin/python3";

    
    
    /**
     * Ajoute les declarations shebang et UTF-8 au debut du fichier Python specifie.
     *
     * @param link Le chemin du fichier Python a analyser et modifier.
     */
    public void addSbut8(String link) {
        try {
            FileAnalyser fileAnalyser = new FileAnalyser();
            boolean hasShebang = fileAnalyser.hasShebang(link);
            boolean hasEncodage = fileAnalyser.hasEncodage(link);

            if (!hasShebang || !hasEncodage) {
                // Lire le contenu du fichier
                StringBuilder content = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new FileReader(link))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                }

                if (!hasShebang) {
                    // Ajouter la declaration shebang a la premiere ligne
                    content.insert(0, SHEBANG_PYTHON3 + "\n");
                }

                if (!hasEncodage) {
                    // Ajouter la declaration d'encodage a la deuxieme ligne
                    content.insert(content.indexOf("\n") + 1, ENCODAGE + "\n");
                }

                // ecrire le nouveau contenu dans le fichier
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(link))) {
                    bw.write(content.toString());
                    System.out.println("Les deux lignes du shebang et de l'encodage UTF-8 ont ete ajoutees avec succes.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                throw new RuntimeException("Les declarations shebang et UTF-8 existent deja dans le fichier.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Ajoute un squelette de commentaire de type docstring Python aux fonctions du fichier specifie.
     * Le squelette est ajoute seulement si la fonction n'a pas deja un commentaire de type docstring Python.
     *
     * @param file Le fichier contenant les fonctions a traiter.
     * @param author Le nom de l'auteur de la documentation.
     * @param version La version de la documentation.
     */
    public void addPyDocSkeleton(File file, String author, String version) {
    	  try {
              Boolean insideFunction = false;
              BufferedReader br = new BufferedReader(new FileReader(file));
              StringBuilder fileContent = new StringBuilder();
              String line;
              while ((line = br.readLine()) != null) {
                  if (insideFunction) {
                      if (!FuncHasPyDoc(line)) {
                          // Ajouter un squelette de commentaire de type docstring Python
                    	  String pydoc = "\t\"\"\"!\n" + "\t\t@brief\n" + "\t\t@version " + version + "\n" + "\t\t@author " + author + "\n"
                                  + "\t\t@param\n" + "\t\t@return\n" + "\t\"\"\"\n";
                          fileContent.append(pydoc);
                          fileContent.append(line).append(System.lineSeparator());
                          insideFunction = false;
                          System.out.println("Le PyDoc a ete ajoutees avec succes.");

                      } else {
                          fileContent.append(line).append(System.lineSeparator());
                          insideFunction = false;
                      }
                  } else {
                      fileContent.append(line).append(System.lineSeparator());
                      if (line.contains("def") && (!line.startsWith("#"))) {
                          insideFunction = true;
                      }
                  }

              }
              br.close();
              try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                  bw.write(fileContent.toString());
                  bw.close();
              }
          } catch (IOException e) {
              e.printStackTrace();

          }
      }
   
    /**
     * Verifie si une ligne contient la declaration du pydoc de type docstring Python.
     *
     * @param line La ligne de code a verifier.
     * @return {@code true} si la ligne contient la declaration du pydoc, {@code false} sinon.
     * @throws IOException Si une erreur d'entree/sortie se produit lors de la verification.
     */
    public boolean FuncHasPyDoc(String line) throws IOException {
        // Verifie si la ligne (apres avoir retire les espaces) commence par """ ou """!
        return line.trim().startsWith("\"\"\"!") || line.trim().startsWith("\"\"\"");
    }

   

}
