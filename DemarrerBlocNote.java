/**
 * Classe 'DemarrerBlocNote' - TP2 (Éditeur de texte)
 * Ceci est le programme principal depuis lequel l'application est lancée. Elle ouvre la fenêtre de l'éditeur de texte
 * en plein écran avec tous ses menus.
 *
 * @author Aberrahim Ganif, Anthony Bassil, Ibrahim Boudaouara et Daoud Ilhan Djennaoui
 * @version Été 2024
 */

package blocNoteGUI;

import javax.swing.*;

public class DemarrerBlocNote {

    public static void main(String[] args) {

        // Démarrer l'application dans un thread
        SwingUtilities.invokeLater(new CadreBlocNote(new JTextPaneCtrlFYZ()));
    }
}