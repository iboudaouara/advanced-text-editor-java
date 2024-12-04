package blocNoteGUI;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

public class PanneauBarreDEtat extends JPanel {

    // Attributs
    private PanneauPrincipal panneauPrincipal;
    private JLabel lbBarreDEtat;
    private int zoomPourcentage = 100; // Par défaut, 100%

    // Constructeur
    public PanneauBarreDEtat(PanneauPrincipal panneauPrincipal) {
        this.panneauPrincipal = panneauPrincipal;
        configurerBarreDEtat();
    }

    /**
     * Procédure 'configurerBarreDEtat'
     * Cette procédure configure la barre d'état (Layout, composants et écouteurs)
     */
    private void configurerBarreDEtat() {
        setLayout(new BorderLayout());
        ajouterLabel();
        ajouterEcouteurCurseur();
    }

    /**
     * Procédure 'ajouterEcouteurCurseur'
     * Cette procédure configure un écouteur pour prendre la position du curseur dans la zone de texte de l'éditeur
     */
    private void ajouterEcouteurCurseur() {
        panneauPrincipal.getTextPane().addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                mettreAJourBarre();
            }
        });
    }

    /**
     * Procédure 'ajouterLabel'
     * Cette procédure ajoute un label pour afficher la position du curseur (ligne et colonne) dans la barre d'état
     */
    private void ajouterLabel() {
        lbBarreDEtat = new JLabel("Ligne: 1, Colonne: 1 | Zoom: 100%");
        lbBarreDEtat.setPreferredSize(new Dimension(300, 35)); // Augmentation de la taille pour inclure le zoom
        add(lbBarreDEtat, BorderLayout.EAST);
    }

    /**
     * Procédure 'mettreAJourBarre'
     * Cette procédure met à jour la barre d'état pour afficher la position actuelle du curseur et le pourcentage de zoom
     */
    private void mettreAJourBarre() {
        int positionCurseur = panneauPrincipal.getTextPane().getCaretPosition();
        int ligne = 1;
        int colonne = 1;

        try {
            // Déterminer position de la ligne
            int decalage = positionCurseur;
            ligne = panneauPrincipal.getTextPane().getDocument().getDefaultRootElement().getElementIndex(decalage) + 1;

            // Déterminer position de la colonne
            int ligneDebutDecalage = panneauPrincipal.getTextPane().getDocument().getDefaultRootElement().
                    getElement(ligne - 1).getStartOffset();
            colonne = decalage - ligneDebutDecalage + 1;

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Mettre à jour le texte du label avec la position du curseur et le zoom
        lbBarreDEtat.setText("Ligne: " + ligne + ", Colonne: " + colonne + " | Zoom: " + zoomPourcentage + "%");
    }

    /**
     * Méthode 'setZoomPourcentage'
     * Met à jour le pourcentage de zoom et rafraîchit l'affichage de la barre d'état
     *
     * @param zoomPourcentage Le nouveau pourcentage de zoom
     */
    public void setZoomPourcentage(int zoomPourcentage) {
        this.zoomPourcentage = zoomPourcentage;
        mettreAJourBarre();
    }
}
