package blocNoteGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;

public class CadreBlocNote extends JFrame implements Runnable {

    // Constantes
    private static final int TAILLE_POLICE_PAR_DEFAUT = 20;         /* Taille du texte par défaut */
    private int taillePoliceActuelle = TAILLE_POLICE_PAR_DEFAUT;    /* Taille actuelle de la police */

    // Attributs du cadre
    private PanneauPrincipal panneauPrincipal;                      /* Panneau principal associé au cadre */

    // Constructeur par attributs
    public CadreBlocNote(PanneauPrincipal panneauPrincipal) {
        this.panneauPrincipal = panneauPrincipal;
    }

    /**
     * Méthode 'run'
     * Démarre un thread associé à l'instance du cadre pour assurer la mise en place des composants
     */
    public void run() {
        
        configCadre();
        initComposants();
    }

    /**
     * Méthode 'configCadre'
     * Définit les propriétés du cadre, soit son titre, sa visibilité, son type de lancement et son affichage
     */
    private void configCadre() {
        
        // Initialiser titre du cadre, rendre cadre visible, arrêter programme sur fermeture de fenêtre, plein écran
        setTitle("Bloc-notes - Éditeur de texte");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
    }

    /**
     * Méthode 'initComposants'
     * Initialise les composants du cadre et indique la manière dont le cadre est lancé et affiché
     */
    private void initComposants() {
        
        // Associer panneau, créer barre de menus, initaliser taille de police
        setContentPane(panneauPrincipal);
        setJMenuBar(creerBarreMenu());
        appliquerTaillePolice(taillePoliceActuelle);
    }

    /**
     * Méthode 'creerBarreMenu'
     * Créer une barre de menus et implémentes les fonctionnalités courantes
     * telles que 'Nouveau', 'Sauvegarder' et 'Quitter'
     *
     * @return Retourne une barre avec les menus 'Fichier' et 'Affichage'
     */
    private JMenuBar creerBarreMenu() {
        
        // Créer une barre avec les menus 'Fichier' et 'Affichage'
        JMenuBar barreMenu = new JMenuBar();
        creerMenuFichier(barreMenu);
        creerMenuAffichage(barreMenu);

        return barreMenu;
    }

    /**
     * Méthode 'creerMenuFichier'
     * Crée un menu Fichier avec les items (Nouveau, Ouvrir, Sauvegarder et Quitter)
     *
     * @param barreMenu Barre de menus que l'on souhaite configurer
     */
    private void creerMenuFichier(JMenuBar barreMenu) {
        
        // Menu Fichier (Nouveau, Ouvrir, Sauvegarder, Quitter)
        JMenu menuFichier = new JMenu("Fichier");
        JMenuItem nouveau = new JMenuItem("Nouveau");
        JMenuItem ouvrir = new JMenuItem("Ouvrir");
        JMenuItem sauvegarder = new JMenuItem("Sauvegarder");
        JMenuItem quitter = new JMenuItem("Quitter");

        menuFichier.add(nouveau);
        menuFichier.add(ouvrir);
        menuFichier.add(sauvegarder);
        menuFichier.addSeparator();
        menuFichier.add(quitter);

        barreMenu.add(menuFichier);

        creerEcouteurQuitter(quitter);
        creerEcouteurNouveau(nouveau);
        creerEcouteurOuvrir(ouvrir);
        creerEcouteurSauvegarder(sauvegarder);
    }

     /**
     * Crée un écouteur pour le menu item 'Nouveau' qui réinitialise le contenu du JTextPane,
     * effaçant tout le texte actuellement affiché.
     */
    
    private void creerEcouteurNouveau(JMenuItem nouveau) {
        
        nouveau.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                panneauPrincipal.getTextPane().setText(""); // Effacer le texte
            }
        });
    }

     /**
     * Crée un écouteur pour le menu item 'Ouvrir' qui permet à l'utilisateur de choisir un fichier à ouvrir
     * et charge son contenu dans le JTextPane.
     */
    
    private void creerEcouteurOuvrir(JMenuItem ouvrir) {
        
        ouvrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(CadreBlocNote.this);
                
                if (result == JFileChooser.APPROVE_OPTION) {
                    
                    File file = fileChooser.getSelectedFile();
                    try {
                        
                        panneauPrincipal.getTextPane().setText(new String(Files.readAllBytes(file.toPath())));
                    } catch (IOException ex) {
                        
                        JOptionPane.showMessageDialog(CadreBlocNote.this, "Erreur lors de l'ouverture du fichier", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

     /**
     * Crée un écouteur pour le menu item 'Sauvegarder' qui permet à l'utilisateur de sauvegarder le contenu actuel
     * du JTextPane dans un fichier.
     */ 
    
    private void creerEcouteurSauvegarder(JMenuItem sauvegarder) {
        
        sauvegarder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showSaveDialog(CadreBlocNote.this);
                
                if (result == JFileChooser.APPROVE_OPTION) {
                    
                    File file = fileChooser.getSelectedFile();
                    try {
                        
                        Files.write(file.toPath(), panneauPrincipal.getTextPane().getText().getBytes());
                        JOptionPane.showMessageDialog(CadreBlocNote.this, "Fichier sauvegardé avec succès", "Sauvegarde", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        
                        JOptionPane.showMessageDialog(CadreBlocNote.this, "Erreur lors de la sauvegarde du fichier", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

     /**
     * Crée un écouteur pour le menu item 'Quitter' qui ferme l'application.
     */
    
    private void creerEcouteurQuitter(JMenuItem quitter) {
        
        quitter.addActionListener(e -> System.exit(0));
    }

    /**
    * Crée et configure le menu 'Affichage' dans la barre de menu principale du cadre.
    * Ce menu inclut des options pour ajuster le zoom du texte et pour contrôler la visibilité de la barre d'état.
    * 
    * @param barreMenu La barre de menu à laquelle le menu 'Affichage' sera ajouté.
    */
    
    private void creerMenuAffichage(JMenuBar barreMenu) {
        // Menu Affichage (Zoom +, Zoom -, Barre d'état)
        JMenu menuAffichage = new JMenu("Affichage");

        JMenuItem zoomPlus = new JMenuItem("Zoom +");
        JMenuItem zoomMoins = new JMenuItem("Zoom -");
        JCheckBoxMenuItem barreEtat = new JCheckBoxMenuItem("Barre d'état");

        menuAffichage.add(zoomPlus);
        menuAffichage.add(zoomMoins);
        menuAffichage.add(barreEtat);

        barreMenu.add(menuAffichage);

        creerEcouteurZoomPlus(zoomPlus);
        creerEcouteurZoomMoins(zoomMoins);
        creerEcouteurBarreEtat(barreEtat);
    }

     /**
     * Crée les écouteurs pour les boutons de zoom 
     * (+) et (-) afin d'augmenter ou de réduire la taille de la police du texte,
     * et met à jour le pourcentage de zoom affiché dans la barre d'état.
     */
    
    private void creerEcouteurZoomPlus(JMenuItem zoomPlus) {
        
        zoomPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                taillePoliceActuelle += 2;
                appliquerTaillePolice(taillePoliceActuelle);

                // Mise à jour du pourcentage de zoom dans la barre d'état
                PanneauBarreDEtat pBarreEtat = panneauPrincipal.getPanneauBarreDEtat();
                int zoomPourcentage = (taillePoliceActuelle * 100) / TAILLE_POLICE_PAR_DEFAUT;
                pBarreEtat.setZoomPourcentage(zoomPourcentage);
            }
        });
    }

    private void creerEcouteurZoomMoins(JMenuItem zoomMoins) {
        
        zoomMoins.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (taillePoliceActuelle > 2) { // Pour éviter des tailles de police trop petites
                    taillePoliceActuelle -= 2;
                    appliquerTaillePolice(taillePoliceActuelle);

                    // Mise à jour du pourcentage de zoom dans la barre d'état
                    PanneauBarreDEtat pBarreEtat = panneauPrincipal.getPanneauBarreDEtat();
                    int zoomPourcentage = (taillePoliceActuelle * 100) / TAILLE_POLICE_PAR_DEFAUT;
                    pBarreEtat.setZoomPourcentage(zoomPourcentage);
                }
            }
        });
    }

    /**
     * Crée un écouteur pour le menu item de la barre d'état pour afficher ou masquer la barre d'état
     * en fonction de l'état du menu à cocher.
     */
    
    private void creerEcouteurBarreEtat(JCheckBoxMenuItem barreEtat) {
        
        PanneauBarreDEtat pBarreEtat = panneauPrincipal.getPanneauBarreDEtat();
        pBarreEtat.setVisible(barreEtat.isSelected());

        barreEtat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                pBarreEtat.setVisible(barreEtat.isSelected());
            }
        });
    }

    private void appliquerTaillePolice(int nouvelleTaille) {
        
        panneauPrincipal.getTextPane().setFont(new Font("SansSerif", Font.PLAIN, nouvelleTaille));
    }
}
