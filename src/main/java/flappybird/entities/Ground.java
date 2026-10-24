package flappybird.entities;

import flappybird.utils.GameConfig;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Representa o chão móvel no rodapé do cenário do jogo.
 *
 * <p><strong>Pilar da POO — Herança:</strong>
 * Herda de {@link GameObject}, delimitando a superfície inferior transitável e fornecendo
 * a hitbox de colisão para encerramento de partida em caso de queda.</p>
 */
public class Ground extends GameObject {

    private double scrollOffset;

    // Paleta de cores do chão
    private static final Color GRASS_TOP = new Color(115, 191, 46);
    private static final Color GRASS_ACCENT = new Color(156, 230, 89);
    private static final Color DIRT_BASE = new Color(222, 216, 149);
    private static final Color DIRT_STRIPE = new Color(205, 190, 120);
    private static final Color OUTLINE_COLOR = new Color(50, 40, 15);

    private static final int STRIPE_SPACING = 20;

    public Ground() {
        super(0, GameConfig.WINDOW_HEIGHT - GameConfig.GROUND_HEIGHT,
                GameConfig.WINDOW_WIDTH, GameConfig.GROUND_HEIGHT);
        this.scrollOffset = 0.0;
    }

    @Override
    public void update() {
        // Incrementa o deslocamento visual sincronizado com a velocidade dos canos
        scrollOffset = (scrollOffset + GameConfig.PIPE_SPEED) % STRIPE_SPACING;
    }

    @Override
    public void draw(Graphics2D g) {
        int px = (int) Math.round(getX());
        int py = (int) Math.round(getY());
        int pw = getWidth();
        int ph = getHeight();

        // 1. Base de terra
        g.setColor(DIRT_BASE);
        g.fillRect(px, py, pw, ph);

        // 2. Faixas diagonais animadas (efeito de rolagem contínua)
        g.setColor(DIRT_STRIPE);
        for (int x = -STRIPE_SPACING; x < pw + STRIPE_SPACING; x += STRIPE_SPACING) {
            int currentX = (int) (x - scrollOffset);
            int[] polyX = {currentX, currentX + 8, currentX - 4, currentX - 12};
            int[] polyY = {py + 14, py + 14, py + ph, py + ph};
            g.fillPolygon(polyX, polyY, 4);
        }

        // 3. Faixa de grama no topo
        g.setColor(GRASS_TOP);
        g.fillRect(px, py, pw, 12);
        g.setColor(GRASS_ACCENT);
        g.fillRect(px, py + 1, pw, 3);

        // 4. Linha de contorno superior
        g.setColor(OUTLINE_COLOR);
        g.drawLine(px, py, px + pw, py);
    }
}
