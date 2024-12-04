package blocNoteGUI;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui gère le panneau de contrôle pour la recherche et le remplacement
 * de texte dans un JTextPane.
 */

public class PanneauControlF extends JDialog {

    // Panneaux pour les différentes sections de l'interface utilisateur
    
    private JPanel panneauRechercher;
    private JPanel panneauRemplacer;
    private JPanel panneauBoutons;
    private JPanel panneauOptions;

    // Attributs du panneau de recherche/remplacer
    private JLabel lblRechercher;
    private JLabel lblRemplacer;

    private JTextField edtRechercher;
    private JTextField edtRemplacer;

    // Boutons pour les actions de recherche et de remplacement
    
    private JButton btnRechercher;
    private JButton btnPrecedant;
    private JButton btnSuivant;
    private JButton btnRemplacer;
    private JButton btnRemplacerTout;

    // Options de recherche

    private JCheckBox cbCaseSensitive;

    private JComboBox<String> comboBoxRecherches;  // ComboBox pour les recherches précédentes

    // Gestion des indices trouvés et du surlignage

    private List<Integer> foundIndices = new ArrayList<>();
    private int currentIndex = -1; // Aucun surlignage initialement
    private Highlighter.HighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);

    private JTextPane textPane; // Référence au JTextPane à manipuler

     /**
     * Constructeur qui initialise le dialogue avec la fenêtre parente et le titre.
     * @param panneau La fenêtre parente
     * @param titre Le titre du dialogue
     */
    
    public PanneauControlF(JFrame panneau, String titre) {
        
        super(panneau, titre);
        this.textPane = ((PanneauPrincipal) panneau.getContentPane()).getTextPane();
        configurerPanneau();
    }

     /**
     * Configure le panneau principal en ajoutant tous les sous-panneaux et initialise les interactions.
     */
    
    private void configurerPanneau() {
        
        // Utilisation de BoxLayout pour un alignement vertical
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        panneauRechercher = creerPanneauRechercher();
        panneauBoutons = creerPanneauBoutons();
        panneauRemplacer = creerPanneauRemplacer();
        panneauOptions = creerPanneauOptions();

        // Initialiser le ComboBox pour les recherches précédentes
        comboBoxRecherches = new JComboBox<>();
        comboBoxRecherches.setEditable(true);  // Permettre l'édition du texte

        panneauRechercher.add(comboBoxRecherches);  // Ajouter le ComboBox au panneau de recherche

        ajouterPanneaux();

        creerEcouteurBtnRechercher();
        creerEcouteurBtnSuivant();
        creerEcouteurBtnPrecedant();
        creerEcouteurBtnRemplacer();
        creerEcouteurBtnTout();
    }

    /**
    * Crée l'écouteur pour le bouton "Remplacer tout". Ce bouton remplace toutes les occurrences
    * du texte recherché dans le document par le texte spécifié dans le champ de remplacement.
    */
    private void creerEcouteurBtnTout() {
        
        btnRemplacerTout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remplacerTout();
            }
        });
    }

     /**
     * Crée l'écouteur pour le bouton "Remplacer". Ce bouton remplace le texte actuellement surligné
     * (pointé par le curseur) par le texte spécifié dans le champ de remplacement.
     */
    
    private void creerEcouteurBtnRemplacer() {
        
        btnRemplacer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remplacerTexte();
            }
        });
    }

    /**
    * Crée l'écouteur pour le bouton "Précédent". Ce bouton permet de naviguer à l'occurrence précédente
    * du texte recherché dans le document.
    */

    private void creerEcouteurBtnPrecedant() {
        
        btnPrecedant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chercherPrecedent();
            }
        });
    }


     /**
     * Crée l'écouteur pour le bouton "Suivant". Ce bouton permet de naviguer à l'occurrence suivante
     * du texte recherché dans le document.
     */
    
    private void creerEcouteurBtnSuivant() {
        
        btnSuivant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chercherSuivant();
            }
        });
    }

   /**
   * Crée l'écouteur pour le bouton "Rechercher". Ce bouton déclenche la recherche du texte spécifié
   * dans le champ de recherche et surligne toutes les occurrences trouvées.
   */
    
    private void creerEcouteurBtnRechercher() {
        
        btnRechercher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rechercherEtSurligner();
            }
        });
    }

    /**
    * Ajoute tous les panneaux configurés au contenu principal de ce dialogue.
    */

    private void ajouterPanneaux() {
        
        add(panneauRechercher);
        add(Box.createVerticalStrut(5)); // Espacement de 5 pixels
        add(panneauBoutons);
        add(Box.createVerticalStrut(5)); // Espacement de 5 pixels
        add(panneauRemplacer);
        add(Box.createVerticalStrut(5)); // Espacement de 5 pixels
        add(panneauOptions);
    }

   /**
   * Crée le panneau contenant les options spécifiques pour la recherche,
   * notamment la sensibilité à la casse.
   */
    
    private JPanel creerPanneauOptions() {
        
        JPanel panelOptions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cbCaseSensitive = new JCheckBox("Sensible à la casse");
        panelOptions.add(cbCaseSensitive);

        return panelOptions;
    }

     /**
     * Crée le panneau pour le remplacement de texte, 
     * incluant les champs pour entrer le texte de remplacement
     * et les boutons pour exécuter l'action de remplacement.
     */
    
    private JPanel creerPanneauRemplacer() {
        
        JPanel panelRemplacer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblRemplacer = new JLabel("Remplacer par:");
        edtRemplacer = new JTextField(15);
        btnRemplacer = new JButton("Remplacer");
        btnRemplacerTout = new JButton("Remplacer tout");
        panelRemplacer.add(lblRemplacer);
        panelRemplacer.add(edtRemplacer);
        panelRemplacer.add(btnRemplacer);
        panelRemplacer.add(btnRemplacerTout);

        return panelRemplacer;
    }

      /**
      * Crée le panneau contenant les boutons pour 
      * la navigation et la recherche dans le document.
      */
 
    private JPanel creerPanneauBoutons() {
        
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnRechercher = new JButton("Rechercher");
        btnPrecedant = new JButton("Précédent");
        btnSuivant = new JButton("Suivant");
        panelBoutons.add(btnRechercher);
        panelBoutons.add(btnPrecedant);
        panelBoutons.add(btnSuivant);

        return panelBoutons;
    }

        /**
        * Crée le panneau pour la recherche de texte, incluant 
        * un champ pour entrer le texte recherché
        * et un label pour indiquer la fonction du champ.
        */
    
    private JPanel creerPanneauRechercher() {
        
        JPanel panelRechercher = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblRechercher = new JLabel("Rechercher:");
        edtRechercher = new JTextField(15);
        panelRechercher.add(lblRechercher);
        panelRechercher.add(edtRechercher);

        return panelRechercher;
    }

    /**
     * Recherche le texte spécifié dans le champ de recherche et surligne toutes les occurrences trouvées.
     * Gère également la sensibilité à la casse selon que la case à cocher est activée ou non.
     */
    
    private void rechercherEtSurligner() {
        
        String textToFind = edtRechercher.getText();
        boolean caseSensitive = cbCaseSensitive.isSelected();
        Highlighter highlighter = textPane.getHighlighter();
        highlighter.removeAllHighlights();

        foundIndices.clear();
        currentIndex = -1;

        if (textToFind.isEmpty()) {
            return;
        }

        // Ajouter la recherche au ComboBox si elle n'existe pas déjà
        if (((DefaultComboBoxModel<String>) comboBoxRecherches.getModel()).getIndexOf(textToFind) == -1) {
            comboBoxRecherches.addItem(textToFind);
        }

        try {
            Document doc = textPane.getDocument();
            String text = doc.getText(0, doc.getLength());
            if (!caseSensitive) {
                text = text.toLowerCase();
                textToFind = textToFind.toLowerCase();
            }

            int index = text.indexOf(textToFind);
            while (index >= 0) {
                foundIndices.add(index);
                index = text.indexOf(textToFind, index + textToFind.length());
            }

            if (foundIndices.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun résultat trouvé", "Recherche", JOptionPane.INFORMATION_MESSAGE);
            } else {
                currentIndex = 0;
                surlignerOccurrence(currentIndex);
            }

        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    /**
    * Surligne l'occurrence du texte recherché à l'indice spécifié.
    * @param index L'indice de l'occurrence à surligner.
    */
    
    private void surlignerOccurrence(int index) {
        
        if (index < 0 || index >= foundIndices.size()) {
            return;
        }

        Highlighter highlighter = textPane.getHighlighter();
        highlighter.removeAllHighlights();

        int start = foundIndices.get(index);
        int end = start + edtRechercher.getText().length();

        try {
            highlighter.addHighlight(start, end, new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW));
            textPane.setCaretPosition(end);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

       /**
       * Navigue à l'occurrence suivante du texte recherché et la surligne.
       */
    
    private void chercherSuivant() {
        
        if (foundIndices.isEmpty()) {
            return;
        }

        currentIndex = (currentIndex + 1) % foundIndices.size();  // Boucle au début
        surlignerOccurrence(currentIndex);
    }

    /**
    * Navigue à l'occurrence précédente du texte recherché et la surligne.
    */
    
    private void chercherPrecedent() {
        
        if (foundIndices.isEmpty()) {
            return;
        }

        currentIndex = (currentIndex - 1 + foundIndices.size()) % foundIndices.size();  // Boucle à la fin
        surlignerOccurrence(currentIndex);
    }

    /**
    * Remplace le texte surligné actuellement par le texte spécifié dans le champ de remplacement.
    */
    
    private void remplacerTexte() {
        
        if (currentIndex >= 0 && currentIndex < foundIndices.size()) {
            Document doc = textPane.getDocument();
            int start = foundIndices.get(currentIndex);

            try {
                doc.remove(start, edtRechercher.getText().length());
                doc.insertString(start, edtRemplacer.getText(), null);

                // Mettre à jour la liste des occurrences après le remplacement
                rechercherEtSurligner();
                
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
        }
    }

      /**
      * Remplace toutes les occurrences du texte 
      * recherché par le texte de remplacement spécifié.
      */

    private void remplacerTout() {
        
        if (!foundIndices.isEmpty()) {
            
            try {
                for (int i = foundIndices.size() - 1; i >= 0; i--) { // Remplacer à partir de la fin
                    int start = foundIndices.get(i);
                    textPane.getDocument().remove(start, edtRechercher.getText().length());
                    textPane.getDocument().insertString(start, edtRemplacer.getText(), null);
                }

                // Mettre à jour la liste des occurrences après le remplacement
                rechercherEtSurligner();
                
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
        }
    }
}
