package flappybird.entities;

import flappybird.utils.GameConfig;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Representa um único cano (superior ou inferior) que atua como obstáculo no jogo.
 *
 * <p><strong>Pilar da POO — Herança:</strong>
 * Herda de {@link GameObject}, reutilizando a gestão de coordenadas e hitbox retangular
 * para colisão precisa com o pássaro.</p>
 */
public class Pipe extends GameObject {

    private final boolean top;

    // Paleta de cores do cano (verde clássico com sombreamento tridimensional)
    private static final Color PIPE_GREEN = new Color(115, 191, 46);
    private static final Color PIPE_LIGHT_GREEN = new Color(156, 230, 89);
    private static final Color PIPE_DARK_GREEN = new Color(85, 140, 34);
    private static final Color PIPE_OUTLINE = new Color(45, 87, 15);

    private static final int LIP_HEIGHT = 24;
    private static final int LIP_EXTEND = 3; // borda sobressalente da tampa

    /**
     * Constrói um cano com posição e dimensões definidas.
     *
     * @param x posição horizontal inicial.
     * @param y posição vertical inicial.
     * @param width largura do cano.
     * @param height altura do cano.
     * @param top {@code true} se for o cano pendurado no teto; {@code false} se surgir do chão.
     */
    public Pipe(double x, double y, int width, int height, boolean top) {
        super(x, y, width, height);
        this.top = top;
    }

    @Override
    public void update() {
        // Desloca o obstáculo em direção à esquerda na velocidade configurada
        moveBy(-GameConfig.PIPE_SPEED, 0);
    }

    /**
     * Verifica se o cano já ultrapassou completamente o canto esquerdo da tela.
     *
     * @return {@code true} se estiver fora do campo de visão; {@code false} caso contrário.
     */
    public boolean isOffScreen() {
        return getX() + getWidth() < 0;
    }

    @Override
    public void draw(Graphics2D g) {
        int px = (int) Math.round(getX());
        int py = (int) Math.round(getY());
        int pw = getWidth();
        int ph = getHeight();

        // 1. Corpo principal do cano com contorno
        g.setColor(PIPE_OUTLINE);
        g.fillRect(px, py, pw, ph);

        g.setColor(PIPE_GREEN);
        g.fillRect(px + 2, py, pw - 4, ph);

        // Faixa de luz (destaque à esquerda)
        g.setColor(PIPE_LIGHT_GREEN);
        g.fillRect(px + 6, py, 6, ph);

        // Faixa de sombra (à direita)
        g.setColor(PIPE_DARK_GREEN);
        g.fillRect(px + pw - 12, py, 8, ph);

        // 2. Tampa (lip/colarinho) do cano
        int lipY = top ? (py + ph - LIP_HEIGHT) : py;
        int lipX = px - LIP_EXTEND;
        int lipW = pw + (LIP_EXTEND * 2);

        // Contorno da tampa
        g.setColor(PIPE_OUTLINE);
        g.fillRect(lipX, lipY, lipW, LIP_HEIGHT);

        // Preenchimento da tampa
        g.setColor(PIPE_GREEN);
        g.fillRect(lipX + 2, lipY + 2, lipW - 4, LIP_HEIGHT - 4);

        // Destaque de luz na tampa
        g.setColor(PIPE_LIGHT_GREEN);
        g.fillRect(lipX + 6, lipY + 2, 7, LIP_HEIGHT - 4);

        // Sombra na tampa
        g.setColor(PIPE_DARK_GREEN);
        g.fillRect(lipX + lipW - 14, lipY + 2, 9, LIP_HEIGHT - 4);
    }

    public boolean isTop() {
        return top;
    }
}
