/**
 * Classe 'JTextPaneCtrlFYZ' - TP2 (Éditeur de texte)
 * Définit un panneau principal qui incorpore toutes les fonctionnalités de raccourci clavier:
 *  - Défaire (ctrl+Z)
 *  - Refaire (ctrl+Y)
 *  - Rechercher/Remplacer (ctrl+F)
 *
 * @author Aberrahim Ganif, Anthony Bassil, Ibrahim Boudaouara et Daoud Ilhan Djennaoui
 * @version Été2024
 */

package blocNoteGUI;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Classe JTextPaneCtrlFYZ qui intègre les fonctionnalités
 * de raccourcis clavier pour défaire (Ctrl+Z),
 * refaire (Ctrl+Y) et rechercher/remplacer (Ctrl+F) dans un éditeur de texte.
 */

public class JTextPaneCtrlFYZ extends PanneauPrincipal {

    private UndoManager undoManager = new UndoManager();            /* Pour défaire et refaire */

    public JTextPaneCtrlFYZ() {

        setLayout(new BorderLayout());

        // Créer la zone de texte
        textPane = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(textPane);
        add(scrollPane, BorderLayout.CENTER);

        // Créer la barre d'état
        panneauBarreDEtat = new PanneauBarreDEtat(this);
        add(panneauBarreDEtat, BorderLayout.SOUTH);

        initComposants();
    }

    /**
     * Initialise les écouteurs pour les actions de défaire, refaire, et rechercher/remplacer,
     * ainsi que le suivi des modifications pour permettre ces actions.
     */
    
    private void initComposants() {
        // Ajouter les actions d'annulation et de rétablissement
        Document doc = this.getTextPane().getDocument();
        doc.addUndoableEditListener(undoManager);

        // Créer un écouteur pour la fonctionnalité 'défaire' ou 'ctrl+z'
        creerEcouteurCtrlZ(this);

        // Créer un écouteur pour la fonctionnalité 'refaire' ou 'ctrl+y'
        creerEcouteurCtrlY(this);

        // Créer un écouteur pour la fonctionnalité 'rechercher/remplacer' ou 'ctrl+f'
        creerEcouteurCtrlF(this);
    }

    /**
     * Crée l'écouteur pour la combinaison de touches Ctrl+F qui active la recherche/remplacement.
     */
    
    protected void creerEcouteurCtrlF(PanneauPrincipal panneauPrincipal) {

        panneauPrincipal.getTextPane().getActionMap().put("Rechercher", new AbstractAction("Rechercher") {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                PanneauControlF d = new PanneauControlF(getParentFrame(JTextPaneCtrlFYZ.this),
                        "Rechercher/Remplacer");

                // setsize of dialog
                d.setSize(400, 300);

                // Center the dialog on the screen
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Dimension screenSize = toolkit.getScreenSize();
                int x = (screenSize.width - d.getWidth()) / 2;
                int y = (screenSize.height - d.getHeight()) / 2;
                d.setLocation(x, y);

                // set visibility of dialog
                d.setVisible(true);
            }
        });
        panneauPrincipal.getTextPane().getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_F,
                KeyEvent.CTRL_DOWN_MASK), "Rechercher");
    }


    /**
     * Crée l'écouteur pour la combinaison de touches Ctrl+Y qui active la fonction de refaire.
     */
    
    protected void creerEcouteurCtrlY(PanneauPrincipal panneauPrincipal) {

        panneauPrincipal.getTextPane().getActionMap().put("Redo", new AbstractAction("Redo") {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                    if (undoManager.canRedo()) {
                        undoManager.redo();
                    }
                    
                } catch (CannotRedoException ex) {
                    ex.printStackTrace();
                }
            }
        });

        panneauPrincipal.getTextPane().getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
                KeyEvent.CTRL_DOWN_MASK), "Redo");
    }

    /**
     * Crée l'écouteur pour la combinaison de touches Ctrl+Z qui active la fonction de défaire.
     */
    
    protected void creerEcouteurCtrlZ(PanneauPrincipal panneauPrincipal) {

        panneauPrincipal.getTextPane().getActionMap().put("Undo", new AbstractAction("Undo") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (undoManager.canUndo()) {
                        undoManager.undo();
                    }
                } catch (CannotUndoException ex) {
                    ex.printStackTrace();
                }
            }
        });

        panneauPrincipal.getTextPane().getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                KeyEvent.CTRL_DOWN_MASK), "Undo");
    }

    /**
     * Récupère la référence du JFrame parent d'un JPanel.
     * @param panel Le JPanel dont on souhaite obtenir le JFrame parent.
     * @return Le JFrame parent ou null si non trouvé.
     */
    
    public static JFrame getParentFrame(JPanel panel) {
        // Traverse up the component hierarchy
        Container parent = panel.getParent();
        while (parent != null && !(parent instanceof JFrame)) {
            parent = parent.getParent();
        }
        return (JFrame) parent;
    }



}
