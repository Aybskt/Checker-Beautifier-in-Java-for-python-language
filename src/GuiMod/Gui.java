package GuiMod;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import FileExplorer.DirectoryManager;
import FileAnalyser.FileAnalyser;
import FileAnalyser.FileModify;




/**
 ** Cette classe represente l interface graphique principale d une application destinee a analyser, modifier et visualiser le contenu de fichiers Python. 
 * @author Ayoub ABDELLI
 * @since 20-09-2025
 */

public class Gui extends JFrame {

	 
		private static final long serialVersionUID = -607462800115298684L;
		private JPanel bottomPanel, metaDataContainer;
	    private JList<File> fileList;
	    private JScrollPane fileTreeScroll, fileContentScroll;
	    private JTextArea fileContentArea;
	    private JTextField pathTextField;
	    private JButton browseButton, editButton, addShebangUtf8Button, addPydocButton, saveButton;
	    private JLabel typeAnnotationLabel, shebangLabel, utfLabel, pydocLabel;
	    private File currentFile;
	    private FileAnalyser fileAnalyser;
	    private FileModify fileModify;
	    private JSplitPane horizontalSplitPane, verticalSplitPane;
	    private JPanel treePanel, fileContentPanel, analysisResultsPanel;
		private JButton customPydocButton;
		private String customAuthor = ""; // Valeur par defaut
		private String customVersion = ""; // Valeur par defaut
		private String lastPathUsed = "";




	    

    /**
     * Constructeur qui initialise l'interface utilisateur avec un titre specifique,
     * configure l'apparence de l'interface et prepare les composants de l'interface.
     *
     * @param title Le titre de la fenetre de l'interface utilisateur.
     */

		public Gui(String title) {
	        super(title);
	        try {
	            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        fileAnalyser = new FileAnalyser();
	        fileModify = new FileModify();
	        initComponents();
	        setIcons();
	        setupLayout();
	        attachListeners();
	        loadPreferences(); // Load saved preferences
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(800, 600);
	        setLocationRelativeTo(null);
	    }	
    /**
     * Initialise et configure les composants de l'interface utilisateur, tels que les champs de texte,
     * les boutons, et les zones d'affichage de contenu. Definit egalement les comportements de base
     * pour certains composants interactifs.
     */

    private void initComponents() {
        pathTextField = new JTextField("Veuillez ecrire le chemin ou ouvrir l'exploration avec le bouton ");
        pathTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Supprime le message si l'utilisateur tape un caractere
                if (e.getKeyChar() != KeyEvent.CHAR_UNDEFINED) {
                    pathTextField.setText("");
                    pathTextField.removeKeyListener(this); // Enlevez le KeyListener apres la premiere utilisation
                }
            }
        });
        browseButton = new JButton("Explorer/Analyser");
        fileList = new JList<>(new DefaultListModel<>());
        fileTreeScroll = new JScrollPane(fileList);
        fileContentArea = new JTextArea();
        fileContentArea.setEditable(false);
        fileContentScroll = new JScrollPane(fileContentArea);
        editButton = new JButton("Modifier");
        addShebangUtf8Button = new JButton("Ajouter les Shebang/UTF-8");
        addPydocButton = new JButton("Ajouter le Pydoc");
        saveButton = new JButton("Enregistrer");

        typeAnnotationLabel = new JLabel("Le typage: ");
        shebangLabel = new JLabel("La declaration Shebang: ");
        utfLabel = new JLabel("La declaration UTF-8: ");
        pydocLabel = new JLabel("Le squelette Pydoc: ");
       
        metaDataContainer = new JPanel(new GridLayout(1, 4));
        metaDataContainer.add(typeAnnotationLabel);
        metaDataContainer.add(shebangLabel);
        metaDataContainer.add(utfLabel);
        metaDataContainer.add(pydocLabel);

        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Use FlowLayout for bottomPanel
        bottomPanel.add(editButton);
        bottomPanel.add(addShebangUtf8Button);
        bottomPanel.add(addPydocButton);
        bottomPanel.add(saveButton);

        treePanel = new JPanel(new BorderLayout());
        treePanel.add(new JLabel("Arborescence", JLabel.CENTER), BorderLayout.NORTH);
        treePanel.add(fileTreeScroll, BorderLayout.CENTER);

        fileContentPanel = new JPanel(new BorderLayout());
        fileContentPanel.add(new JLabel("Contenu du fichier", JLabel.CENTER), BorderLayout.NORTH);
        fileContentPanel.add(fileContentScroll, BorderLayout.CENTER);

        analysisResultsPanel = new JPanel(new BorderLayout(0,4));
        JPanel resultsPanel = new JPanel(new GridLayout(1, 4));
        resultsPanel.add(typeAnnotationLabel);
        resultsPanel.add(shebangLabel);
        resultsPanel.add(utfLabel);
        resultsPanel.add(pydocLabel);
        analysisResultsPanel.add(new JLabel("Resultat d'analyse", JLabel.CENTER), BorderLayout.NORTH);
        analysisResultsPanel.add(resultsPanel, BorderLayout.CENTER);
        customPydocButton = new JButton("Personnaliser PyDoc");
        bottomPanel.add(customPydocButton);
    }

    
	/**
	 * Configure la disposition des differents panneaux et composants dans la fenetre principale.
	 * Utilise BorderLayout et JSplitPane pour organiser les composants de maniere structuree.
	 */

    private void setupLayout() {
        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(pathTextField, BorderLayout.CENTER);
        topPanel.add(browseButton, BorderLayout.EAST);
        this.add(topPanel, BorderLayout.NORTH);

        // Create a horizontal split pane that contains the tree panel and the file content panel
        horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, fileContentPanel);
        horizontalSplitPane.setDividerLocation(250); // Adjust as needed

        verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, horizontalSplitPane, analysisResultsPanel);
        verticalSplitPane.setDividerLocation(400); 

        this.add(verticalSplitPane, BorderLayout.CENTER);

        JPanel bottomContainer = new JPanel(new BorderLayout());
        bottomContainer.add(metaDataContainer, BorderLayout.NORTH); 
        bottomContainer.add(bottomPanel, BorderLayout.CENTER); 
        this.add(bottomContainer, BorderLayout.SOUTH); 
    }
    /**
     * Definit les icones pour l'interface utilisateur, en chargeant une image depuis les ressources.
     * Gere les exceptions liees a la lecture des fichiers d'icones.
     * @throws IOException
     */

    private void setIcons() {
        try {
            BufferedImage iconImage = ImageIO.read(getClass().getResource("images/logo.png"));
            setIconImage(iconImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     *Gere les composants interactifs de l'interface utilisateur.
     *Telles que l'exploration de fichiers, l'edition,
     * et l'enregistrement des modifications.
     */


      private void attachListeners() {
    	  browseButton.addActionListener(e -> {
    		    String currentPath = pathTextField.getText();
    		    boolean isDefaultText = currentPath.equals("Veuillez ecrire le chemin ou ouvrir l'exploration avec le bouton ");
    		    boolean isPathChanged = !currentPath.equals(lastPathUsed) && !currentPath.isEmpty() && !isDefaultText;

    		    if (isDefaultText || currentPath.isEmpty() || !isPathChanged) {
    		        openFileChooser();
    		        pathTextField.setText("Veuillez ecrire le chemin ou ouvrir l'exploration avec le bouton ");
    		        lastPathUsed = ""; // Reinitialiser apres ouverture de l'explorateur
    		    } else {
    		        // Traiter le chemin entre
    		        File file = new File(currentPath);
    		        if (file.exists()) {
    		            handleSelectedFile(file);
    		            lastPathUsed = currentPath; // Memoriser le dernier chemin utilise
    		        } else {
    		            JOptionPane.showMessageDialog(this, "Chemin non valide.", "Error", JOptionPane.ERROR_MESSAGE);
    		        }
    		    }
    		});


    	  editButton.addActionListener(e -> editFile());
          addShebangUtf8Button.addActionListener(e -> addShebangUtf8());
          addPydocButton.addActionListener(e -> addPydoc());
          saveButton.addActionListener(e -> saveFile());
          fileList.addListSelectionListener(e -> displaySelectedFile());
          customPydocButton.addActionListener(e -> showCustomPydocDialog());

      }

     
      /**
       * Affiche une boite de dialogue permettant a l'utilisateur de personnaliser le PyDoc.
       * Cette boite de dialogue comprend des champs pour saisir l'auteur et la version du document.
       * L'utilisateur peut enregistrer ces preferences, qui seront utilisees pour generer des squelettes PyDoc.
       * Apres avoir enregistre les preferences, la boite de dialogue se ferme.
       */
      private void showCustomPydocDialog() {
          JDialog dialog = new JDialog(this, "Personnaliser PyDoc", true);
          dialog.setLayout(new GridLayout(3, 2));
          JTextField authorField = new JTextField(customAuthor);
          JTextField versionField = new JTextField(customVersion);
          JButton saveButton = new JButton("Enregistrer");

          dialog.add(new JLabel("Auteur :"));
          dialog.add(authorField);
          dialog.add(new JLabel("Version :"));
          dialog.add(versionField);
          dialog.add(saveButton);

          saveButton.addActionListener(e -> {
              customAuthor = authorField.getText();
              customVersion = versionField.getText();
              savePreferences(); // Save the preferences
              dialog.dispose();
          });

          dialog.pack();
          dialog.setLocationRelativeTo(this);
          dialog.setVisible(true);
      }
      
      /**
       * Enregistre les preferences de l'utilisateur dans un fichier serialise.
       */
      private void savePreferences() {
          try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("preferences.ser"))) {
              oos.writeObject(customAuthor);
              oos.writeObject(customVersion);
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
      /**
       * Charge les preferences de l'utilisateur a partir d'un fichier serialise.
       */
      private void loadPreferences() {
          try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("preferences.ser"))) {
              customAuthor = (String) ois.readObject();
              customVersion = (String) ois.readObject();
          } catch (IOException | ClassNotFoundException e) {
              customAuthor = ""; // Set to default if the preferences file does not exist
              customVersion = "";
          }
      }
      
      /**
       * Ouvre un explorateur de fichiers permettant a l'utilisateur de choisir un fichier ou un dossier.
       * Filtre les fichiers pour afficher uniquement les fichiers Python.
       */
      private void openFileChooser() {
    	    // Ouvre le selecteur de fichiers sans utiliser pathTextField
    	    JFileChooser chooser = new JFileChooser();
    	    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    	    chooser.setAcceptAllFileFilterUsed(false);
    	    chooser.addChoosableFileFilter(new FileNameExtensionFilter("Python files", "py"));
    	    int result = chooser.showOpenDialog(this);
    	    if (result == JFileChooser.APPROVE_OPTION) {
    	        File selectedFile = chooser.getSelectedFile();
    	        handleSelectedFile(selectedFile);
    	    }
    	}
      

      


      /**
       * Traite le fichier choisi, soit en listant les fichiers Python dans un dossier, soit en affichant
       * le contenu d'un fichier Python, ou en affichant un message d'erreur si le fichier n'est pas valide.
       *
       * @param selectedFile Le fichier a traiter.
       */

      private void handleSelectedFile(File selectedFile) {
          File lastSelectedFile = selectedFile; // Mettez a jour lastSelectedFile avec le nouveau fichier selectionne
          pathTextField.setText(selectedFile.getAbsolutePath());
          try {
              if (selectedFile.isDirectory()) {
                  listPythonFiles(selectedFile.getAbsolutePath());
              } else if (DirectoryManager.isPythonFile(selectedFile)) {
                  currentFile = selectedFile;
                  displayFileContent(selectedFile);
              } else {
                  JOptionPane.showMessageDialog(this, "Veuillez choisir un fichier Py ou un dossier.", "Error", JOptionPane.ERROR_MESSAGE);
              }
          } catch (IOException e) {
              JOptionPane.showMessageDialog(this, "Erreur lors du traitement du fichier.", "Error", JOptionPane.ERROR_MESSAGE);
          }
      }

      /**
       * Active le mode pour modifier le fichier voulu, permettant a l'utilisateur de modifier le contenu de ce dernier.
       */

    private void editFile() {
        if (currentFile != null && currentFile.isFile()) {
            fileContentArea.setEditable(true);
            saveButton.setEnabled(true);
        }
    }
    /**
     * Ajoute une declaration Shebang et UTF-8 au fichier Python, si celui-ci est un fichier valide.
     */
    private void addShebangUtf8() {
        if (currentFile != null && currentFile.isFile()) {
            fileModify.addSbut8(currentFile.getAbsolutePath());
            displayFileContent(currentFile); 
        }
    }

    /**
     * Ajoute un squelette Pydoc au fichier Python, si celui-ci est un fichier valide.
     */

    private void addPydoc() {
        if (currentFile != null && currentFile.isFile()) {
            fileModify.addPyDocSkeleton(currentFile, customAuthor, customVersion);
            displayFileContent(currentFile); 
        }
    }

    /**
     * Enregistre les modifications apportees au fichier Python.
     * Affiche des messages d'etat pour indiquer si l'enregistrement a reussi ou echoue.
     */

    private void saveFile() {
        if (currentFile != null && fileContentArea.isEditable()) {
            try {
                Files.writeString(currentFile.toPath(), fileContentArea.getText());
                JOptionPane.showMessageDialog(this, "Le fichier a ete enregistrer avec succes!", "Success", JOptionPane.INFORMATION_MESSAGE);
                fileContentArea.setEditable(false);
                saveButton.setEnabled(false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Impossible d'enregistrer le fichier.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /**
     * Affiche le contenu du fichier specifie dans la zone de texte de l'interface utilisateur.
     * Gere les exceptions liees a la lecture du fichier.
     *
     * @param file Le fichier dont le contenu doit etre affiche.
     */

    private void displayFileContent(File file) {
        try {
            String content = Files.readString(file.toPath());
            fileContentArea.setText(content);
            fileContentArea.setCaretPosition(0); 
            updateMetadataDisplay(file);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Impossible de lire le fichier.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Affiche le contenu du fichier selectionne dans la liste des fichiers.
     * Met a jour egalement les metadonnees affichees en fonction du fichier selectionne.
     */

    private void displaySelectedFile() {
        File selectedFile = fileList.getSelectedValue();
        if (selectedFile != null) {
            currentFile = selectedFile;
            displayFileContent(selectedFile);
            updateMetadataDisplay(currentFile);

        }
    }

    /**
     * Met a jour l'affichage des metadonnees pour le fichier specifie,
     * telles que les annotations de type, Shebang, UTF-8, et Pydoc.
     *
     * @param file Le fichier pour lequel les metadonnees doivent etre affichees.
     */


    private void updateMetadataDisplay(File file) {
        boolean hasTypeAnnotations = fileAnalyser.hasTypageBool(file.getAbsolutePath());
        boolean hasShebang = fileAnalyser.hasShebang(file.getAbsolutePath());
        boolean hasUtf8 = fileAnalyser.hasEncodage(file.getAbsolutePath());
        boolean hasPydoc = fileAnalyser.hasPyDoc(file.getAbsolutePath());

        typeAnnotationLabel.setText("Le Typage: " + hasTypeAnnotations);
        shebangLabel.setText("La declaration Shebang: " + hasShebang);
        utfLabel.setText("La declaration UTF-8: " + hasUtf8);
        pydocLabel.setText("Le squelette Pydoc: " + hasPydoc);
    }
    /**
     * Liste tous les fichiers Python dans le repertoire specifie et les affiche dans la liste de fichiers.
     *
     * @param directoryPath Le chemin du repertoire contenant les fichiers Python a lister.
     */

    private void listPythonFiles(String directoryPath) {
        try {
            List<File> pythonFiles = DirectoryManager.listFiles(directoryPath);
            DefaultListModel<File> model = (DefaultListModel<File>) fileList.getModel();
            model.removeAllElements();
            for (File pythonFile : pythonFiles) {
                model.addElement(pythonFile);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Impossible de lister les fichiers Python.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Point d'entree principal de l'application. Cree et affiche l'interface utilisateur principale.
     *
     * @param args Les arguments de la ligne de commande (non utilises).
     */

    public static void main(String[] args) {
    	Gui gui = new Gui("Checker & Beautifier in Java for python language");
        gui.setVisible(true);
    }
}
