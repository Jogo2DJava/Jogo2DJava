package flappybird.ui;

import flappybird.game.Game;
import flappybird.utils.DrawUtils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Tela do HUD (Heads-Up Display) exibida durante a partida.
 *
 * <p><strong>Pilar da POO — Polimorfismo:</strong>
 * Implementa {@link Screen}, renderizando a pontuação corrente no topo da tela.</p>
 */
public class HudScreen implements Screen {

    private static final Font SCORE_FONT = new Font("SansSerif", Font.BOLD, 48);
    private static final Color SCORE_COLOR = Color.WHITE;
    private static final Color OUTLINE_COLOR = new Color(20, 20, 20);

    @Override
    public void draw(Graphics2D g, Game game) {
        String scoreText = String.valueOf(game.getScore());
        DrawUtils.drawCenteredOutlinedText(g, scoreText, 80, SCORE_FONT, SCORE_COLOR, OUTLINE_COLOR);
    }
}
