/**
 * Classe 'JTextPaneCtrlYZ' - TP2 (Éditeur de texte)
 * C'est une sous-classe de 'JTextPaneCtrlFYZ' avec des fonctionnalités (raccourcis clavier) limités à:.
 *  - Défaire (ctrl+Z)
 *  - Refaire (ctrl+Y)
 *
 * @author Aberrahim Ganif, Anthony Bassil, Ibrahim Boudaouara et Daoud Ilhan Djennaoui
 * @version Été2024
 */

package blocNoteGUI;

public class JTextPaneCtrlYZ extends JTextPaneCtrlFYZ {

    public JTextPaneCtrlYZ() {
        super();
    }

    @Override
    protected void creerEcouteurCtrlF(PanneauPrincipal panneauPrincipal) {}

}
