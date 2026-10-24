package flappybird.main;

import flappybird.ui.GameWindow;

import javax.swing.SwingUtilities;

/**
 * Ponto de entrada da aplicação.
 */
public final class Main {

    private Main() {
        // Classe usada apenas pelo método main.
    }

    public static void main(String[] args) {
        // O Swing exige que a interface seja criada e alterada na sua própria
        // thread (Event Dispatch Thread). O invokeLater agenda isso com segurança.
        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow();
            window.setVisible(true);
        });
    }
}
