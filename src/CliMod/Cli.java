package CliMod;

import java.io.IOException;
import java.io.File;
import FileAnalyser.ElementNotFoundException;
import FileAnalyser.FileAnalyser;
import FileAnalyser.FileModify;
import FileExplorer.DirectoryManager;

/**
 * Classe permettant l'execution du programme en mode console
  * @author Ayoub ABDELLI 
  * @since 06-09-2025
 */
public class Cli {

    private static String link; // Sauvegarde le lien du dossier a explorer

    /**
     * Constructeur de la classe Cli
     */
    public Cli() {
        super();
    }

    /**
     * Affiche des indications sur le fonctionnement du programme.
     * Se declenche a la demande de l'utilisateur ou lorsque l'utilisateur utilise une commande
     * differente de celle parametree dans le programme.
     */
    public void help() {
        System.out.println("Vous avez acces a differents arguments:\n"
                + "1) Le premier '-d' est de parcourir les differents dossiers pour afficher tous les fichiers py \n"
                + "2) Le second '-f' est d'analyser les fichiers accompagne de plusieurs options :\n"
                + "\t 1/ La premiere option '--type' pour verifier les annotations de type\n"
                + "\t 2/ La deuxieme option '--head' pour verifier les deux premieres lignes de commentaire\n"
                + "\t 3/ La troisieme option '--pydoc' pour verifier les commentaires de fonction\n"
                + "\t 4/ La quatrieme option '--sbutf8' pour ajouter les deux premieres lignes du commentaire manquantes\n"
                + "\t 5/ La cinquieme option '--comment' pour ajouter un squelette de commentaire 'pydoc' sur les fonctions qui ne possedent pas de ce dernier\n"
                + "3) Pour afficher les statistiques d'un ensemble de fichier dans le dossier, utilisez le premier argument suivi de '-stat'\n"
                + "Veuillez relancer la console pour pouvoir acceder aux fonctionnalites");
    }

    /**
     * Methode principale pour la saisie des arguments au debut du programme.
     *
     * @param args Les arguments passes en ligne de commande
     * @throws IOException Exception liee aux operations d'entree/sortie
     */
    public static void main(String[] args) throws IOException {
        Cli cli = new Cli();

        if (args.length == 0) {
            System.out.println("Veuillez relancer le programme en saisissant la commande -h pour obtenir de l'aide");
        }

        for (int i = 0; i < args.length; i++) {// boucle de parcours du tableau args
		    if (args[i].equals("-h")||args[i].equals("--help")) {
		        cli.help(); // Appel la methode help donnant des indications sur le programme
		    } else if (args[i].equals("-d")) {
		        if (i + 2 < args.length && args[i + 2].equals("-stat")) {
		            String link = args[i + 1];
		            FileAnalyser.stats(link);
		        } else {
		            link = args[i + 1];
		            DirectoryManager.listFolders(link); // parcours du dossier et sous-dossier
		        }
		    }
		    else if (args[i].equals("-f") && i + 2 < args.length) {
		        String link = args[i + 1];
		        boolean hasType = false;
		        boolean hasHead = false;
		        boolean hasPydoc = false;
		        boolean hasSbutf8 = false;
		        boolean hasComment = false;
	       		FileAnalyser fileAnalyser = new FileAnalyser();

	       		FileModify fileModify = new FileModify();

	       		 String customAuthor = ""; // Valeur par defaut
	    		 String customVersion = ""; // Valeur par defaut
		        // Verifier les options associees a -f
		        for (int j = i + 2; j < args.length; j++) {
		            if (args[j].equals("--type")) {
		                hasType = true;
		            } else if (args[j].equals("--head")) {
		                hasHead = true;
		            } else if (args[j].equals("--pydoc")) {
		                hasPydoc = true;
		            } else if (args[j].equals("--sbutf8")) {
		                hasSbutf8 = true;
		            } else if (args[j].equals("--comment")) {
		                hasComment = true;
		            }
		        }


		        // Executer les actions en fonction des options
		        if (hasType) {
		        	fileAnalyser.hasTypage(link);
		        }
		        if (hasHead) {
		            boolean shebang = fileAnalyser.hasShebang(link);
		            boolean encodage = fileAnalyser.hasEncodage(link);


		            if (shebang && encodage == true) {
		                System.out.println("Les deux premieres lignes sont des commentaires(Declaration Shebang et UTF-8).");
		            }else if(shebang) {
		                System.out.println("La premire ligne est un commentaire (Shebang)");

		            }else if(encodage) {
		                System.out.println("La deuxieme ligne est un commentaire (UTF-8)");

		            }
		            else {
		                throw new ElementNotFoundException("Les deux premieres lignes ne contienent pas des commentaires.");
		            }
		        }
		        if (hasPydoc) {
		        	fileAnalyser.hasPyDoc(link);
		        }
		        if (hasSbutf8) {
		        	fileModify.addSbut8(link);
		        }
		        if (hasComment) {
		            fileModify.addPyDocSkeleton(new File(link), customAuthor, customVersion);
		        }

		    }
		    
		    
        }
    }
    
}
