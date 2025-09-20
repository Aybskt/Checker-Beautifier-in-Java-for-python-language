<div align="center">
  <img src="https://raw.githubusercontent.com/Aybskt/Checker-Beautifier-in-Java-for-python-language/main/assets/logo.png" width="120px" />
  <h1 align="center">🐍 Python Code Checker & Beautifier</h1>
  <p align="center">
    <strong>Un outil de bureau développé en Java permettant d'analyser, de modifier et de gérer des fichiers de code source Python. Il offre à la fois une interface en ligne de commande (CLI) pour l'automatisation et une interface graphique (GUI) pour une utilisation interactive et conviviale.</strong>
    <br />
    <br />
    <a href="https://aybskt.github.io/Checker-Beautifier-in-Java-for-python-language/"><strong>🎓 Consulter la Javadoc</strong></a>
    ·
    <a href="https://github.com/Aybskt/Checker-Beautifier-in-Java-for-python-language/releases"><strong>🚀 Télécharger la dernière version</strong></a>
    ·
    <a href="https://github.com/Aybskt/Checker-Beautifier-in-Java-for-python-language/issues">Signaler un Bug</a>
  </p>
</div>

<div align="center">
  <img src="https://img.shields.io/github/last-commit/Aybskt/Checker-Beautifier-in-Java-for-python-language?style=for-the-badge&logo=github&color=blue" />
  <img src="https://img.shields.io/github/repo-size/Aybskt/Checker-Beautifier-in-Java-for-python-language?style=for-the-badge&logo=github" />
  <img src="https://img.shields.io/github/languages/top/Aybskt/Checker-Beautifier-in-Java-for-python-language?style=for-the-badge&logo=java" />
  <img src="https://img.shields.io/github/license/Aybskt/Checker-Beautifier-in-Java-for-python-language?style=for-the-badge&color=green&v=1" />
</div>
<br>

---
### ## Table des matières

1.  [🌟 À propos du projet](#-à-propos-du-projet)
2.  [✨ Fonctionnalités](#-fonctionnalités)
3.  [📸 Aperçu de l'application](#-aperçu-de-lapplication)
4.  [🛠️ Environnement de Développement](#️-environnement-de-développement)
5.  [🚀 Démarrage rapide](#-démarrage-rapide)
6.  [💻 Utilisation](#-utilisation)
    * [Interface Graphique (GUI)](#interface-graphique-gui)
    * [Ligne de Commande (CLI)](#ligne-de-commande-cli)
7.  [📚 Documentation](#-documentation)
8.  [📜 Licence](#-licence)
---
### ## 🌟 À propos du projet

Ce projet est un outil d'analyse statique de code, développé en **Java** dans le cadre d'un projet universitaire de Programmation Orientée Objet. Il a pour but d'aider les développeurs Python à maintenir une haute qualité de code en automatisant la vérification et la correction de plusieurs aspects essentiels :
* La conformité des en-têtes de fichiers (Shebang, Encodage).
* La présence de documentation (Pydoc).
* L'utilisation des annotations de type.

L'application offre une double interface (Graphique et Ligne de Commande) pour une flexibilité maximale. L'interface graphique a été modernisée grâce à la bibliothèque externe **FlatLaf**, offrant une expérience utilisateur fluide et agréable.

---

### ## ✨ Fonctionnalités

* 🖥️ **Double Interface :** Une interface graphique (GUI) intuitive pour une utilisation visuelle et une interface en ligne de commande (CLI) pour l'automatisation.
* 📂 **Exploration de Fichiers :** Parcourt récursivement les dossiers pour trouver et analyser tous les fichiers `.py`.
* 🔍 **Analyse Statique :**
    * Vérifie la présence et la validité du **shebang** (`#!/usr/bin/python3`).
    * Contrôle la déclaration d'encodage **UTF-8**.
    * Détecte la présence de **Pydoc** pour les classes et fonctions.
    * Vérifie l'utilisation des **annotations de type** dans les fonctions.
* ✍️ **Correction Automatique :**
    * Ajoute les en-têtes manquants (shebang, encodage).
    * Insère des squelettes de Pydoc prêts à être remplis.
* 🎨 **Interface Moderne :** Une expérience utilisateur soignée grâce à la bibliothèque **FlatLaf**.
* 🛡️ **Gestion d'Erreurs Robuste :** Utilisation d'exceptions personnalisées comme `ElementNotFoundException` pour une meilleure gestion des cas d'erreur.

---

### ## 📸 Aperçu de l'application

*(💡 **Conseil :** Remplacez ces images par vos propres captures d'écran !)*

<table>
  <tr>
    <td><img src="https://raw.githubusercontent.com/Aybskt/Checker-Beautifier-in-Java-for-python-language/main/assets/img1.png" alt="Vue principale de l'explorateur de fichiers"></td>
    <td><img src="https://raw.githubusercontent.com/Aybskt/Checker-Beautifier-in-Java-for-python-language/main/assets/img2.png" alt="Analyse d'un fichier et résultats"></td>
  </tr>
  <tr>
    <td align="center"><em>Vue principale de l'explorateur de fichiers</em></td>
    <td align="center"><em>Analyse d'un fichier et résultats</em></td>
  </tr>
</table>

---

### ## 🛠️ Environnement de Développement

* **Langage :** Java 17
* **IDE :** Eclipse IDE for Java Developers
* **Interface Graphique :** Java Swing
* **Bibliothèque Externe :** [FlatLaf](https://www.formdev.com/flatlaf/) (pour le thème de l'interface)

---

### ## 🚀 Démarrage rapide

Pour lancer l'application, assurez-vous que Java est installé sur votre système.

#### **Prérequis**
* Java Runtime Environment (JRE) 11 ou supérieur.
    * Vérifiez votre version avec la commande :
        ```sh
        java --version
        ```

#### **Installation**
1.  Rendez-vous sur la page **[Releases](https://github.com/Aybskt/Checker-Beautifier-in-Java-for-python-language/releases)**.
2.  Téléchargez les fichiers `gui.jar` et `cli.jar` de la dernière version.

---

### ## 💻 Utilisation

#### **Interface Graphique (GUI)**

La manière la plus simple d'utiliser l'outil.
```sh
java -jar gui.jar
```
#### **Ligne de Commande (CLI)**

Parfait pour l'intégration dans des scripts ou pour des analyses rapides.
Pour découvrir les commandes, lancez d'abord le JAR sans argument (ou avec `-h`).

1.  **Afficher l'aide :**
    #### Ces deux commandes affichent le menu d'aide avec toutes les options.
    ```sh
    java -jar cli.jar
    ```
    #### Ou
    ```sh
    java -jar cli.jar -h
    ```

2.  **Exemples d'utilisation :**
    * **Lister tous les fichiers `.py` d'un dossier :**
        ```sh
        java -jar cli.jar -d /chemin/vers/votre/projet
        ```
    * **Analyser un fichier spécifique (vérifie tout : type, head, pydoc...) :**
        ```sh
        java -jar cli.jar -f /chemin/vers/votre/fichier.py --type --head --pydoc
        ```
    * **Appliquer des corrections (ajoute shebang, utf-8 et squelettes Pydoc) à un fichier :**
        ```sh
        java -jar cli.jar -f /chemin/vers/votre/fichier.py --sbutf8 --comment
        ```

---

### ## 📚 Documentation

La documentation technique complète du code source (Javadoc) est disponible en ligne et hébergée via GitHub Pages.

➡️ **[Accéder à la Javadoc](https://aybskt.github.io/Checker-Beautifier-in-Java-for-python-language/)**

---

### ## 📜 Licence

Ce projet est distribué sous la Licence MIT. Voir le fichier `LICENSE` pour plus de détails.

Copyright (c) 2025 - Ayoub ABDELLI (Aybskt)
