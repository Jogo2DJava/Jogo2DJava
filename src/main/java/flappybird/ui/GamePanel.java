package flappybird.ui;

import flappybird.game.Game;
import flappybird.game.GameLoop;
import flappybird.game.GameState;
import flappybird.utils.GameConfig;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Painel principal do jogo onde ocorrem o desenho e a renderização gráfica via Java2D.
 *
 * <p><strong>Pilar da POO — Herança e Polimorfismo:</strong>
 * Herda de {@link JPanel} e sobrescreve {@code paintComponent(Graphics)}. Delega a renderização
 * de cada tela polimorficamente através da interface {@link Screen}.</p>
 */
public class GamePanel extends JPanel {

    private final Game game;
    private final GameLoop gameLoop;
    private final Screen menuScreen;
    private final InputHandler inputHandler;

    public GamePanel(Game game) {
        if (game == null) {
            throw new IllegalArgumentException("O jogo não pode ser nulo.");
        }
        this.game = game;
        this.menuScreen = new MenuScreen();

        setPreferredSize(new Dimension(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT));
        setDoubleBuffered(true);
        setFocusable(true);

        // Registra listeners de teclado e mouse
        this.inputHandler = new InputHandler(game);
        addKeyListener(inputHandler);
        addMouseListener(inputHandler);

        // Inicializa o Game Loop repassando o repaint deste painel
        this.gameLoop = new GameLoop(game, this::repaint);
    }

    /**
     * Inicia o ciclo de execução e atualização do jogo.
     */
    public void startGameLoop() {
        gameLoop.start();
        requestFocusInWindow();
    }

    /**
     * Interrompe o ciclo do jogo.
     */
    public void stopGameLoop() {
        gameLoop.stop();
    }

    public Game getGame() {
        return game;
    }

    public GameLoop getGameLoop() {
        return gameLoop;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Ativa suavização de bordas (Anti-Aliasing) para visual refinado
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // 1. Céu com gradiente e nuvens de fundo
        drawBackground(g2d);

        // 2. Obstáculos (canos)
        game.getPipeManager().draw(g2d);

        // 3. Chão móvel com textura de rolagem contínua
        game.getGround().draw(g2d);

        // 4. Pássaro controlado pelo jogador
        game.getBird().draw(g2d);

        // 5. Interface da tela atual (Polimorfismo via Screen)
        if (game.getState() == GameState.MENU) {
            menuScreen.draw(g2d, game);
        }
    }

    /**
     * Desenha o gradiente de céu e nuvens decorativas ao fundo.
     */
    private void drawBackground(Graphics2D g) {
        GradientPaint skyGradient = new GradientPaint(
                0, 0, new Color(112, 197, 206),
                0, getHeight(), new Color(200, 235, 245)
        );
        g.setPaint(skyGradient);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Desenha nuvens suaves decorativas
        g.setColor(new Color(255, 255, 255, 120));
        g.fillOval(40, 100, 70, 35);
        g.fillOval(70, 90, 80, 45);
        g.fillOval(110, 100, 60, 35);

        g.fillOval(240, 160, 60, 30);
        g.fillOval(270, 150, 75, 40);
        g.fillOval(310, 160, 50, 30);
    }
}
