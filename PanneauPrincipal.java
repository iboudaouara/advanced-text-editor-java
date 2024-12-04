/**
 * Classe 'PanneauPrincipal' - TP2 (Éditeur de texte)
 * Définit un panneau principal général qui servira de gabarit pour l'hiérarchie de classe. Il incorpore une zone de
 * texte scrollable.
 *
 * @author Aberrahim Ganif, Anthony Bassil, Ibrahim Boudaouara et Daoud Ilhan Djennaoui
 * @version Été2024
 */

package blocNoteGUI;

import javax.swing.*;
import java.awt.*;

public class PanneauPrincipal extends JPanel {

    // Attributs du panneau principal
    protected JTextPane textPane;
    protected PanneauBarreDEtat panneauBarreDEtat;

    // Constructeur par défaut
    public PanneauPrincipal() {

        setLayout(new BorderLayout());

        // Créer la zone de texte
        textPane = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(textPane);
        add(scrollPane, BorderLayout.CENTER);

        // Créer la barre d'état
        panneauBarreDEtat = new PanneauBarreDEtat(this);
        add(panneauBarreDEtat, BorderLayout.SOUTH);
    }

    // Accesseurs

    public JTextPane getTextPane() {

        return textPane;
    }

    public PanneauBarreDEtat getPanneauBarreDEtat() {

        return panneauBarreDEtat;
    }
}
