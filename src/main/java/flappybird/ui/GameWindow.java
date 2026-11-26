package flappybird.ui;

import flappybird.game.Game;
import flappybird.utils.GameConfig;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Janela principal do jogo.
 *
 * <p><strong>Pilar da POO — Herança:</strong>
 * Herda de {@link JFrame} (Swing), reaproveitando a infraestrutura de janelas do sistema operacional
 * e configurando os elementos específicos da aplicação.</p>
 */
public class GameWindow extends JFrame {

    private final GamePanel gamePanel;

    public GameWindow() {
        super(GameConfig.TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        Game game = new Game();
        this.gamePanel = new GamePanel(game);
        add(gamePanel);

        pack();
        setLocationRelativeTo(null);

        // Garante foco no painel ao abrir e libera recursos de áudio ao fechar a janela
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                gamePanel.requestFocusInWindow();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                gamePanel.stopGameLoop();
                gamePanel.disposeAudio();
            }
        });

        // Inicia o Game Loop após a criação da interface
        gamePanel.startGameLoop();
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
