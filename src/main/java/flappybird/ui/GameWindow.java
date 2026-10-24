package flappybird.ui;

import flappybird.game.Game;
import flappybird.utils.GameConfig;

import javax.swing.JFrame;

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

        // Inicia o Game Loop após a criação da interface
        gamePanel.startGameLoop();
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
