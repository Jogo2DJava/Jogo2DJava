package flappybird.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Classe base abstrata que modela qualquer entidade presente no mundo do jogo.
 *
 * <p><strong>Pilar da POO — Abstração e Herança:</strong>
 * Define a estrutura comum (coordenadas bidimensionais, dimensões e retângulo delimitador)
 * e o contrato comportamental ({@link #update()} e {@link #draw(Graphics2D)}) compartilhado por
 * todas as entidades concretas (pássaro, canos e chão), sem impor detalhes de implementação.</p>
 */
public abstract class GameObject {

    /** Posição horizontal no plano 2D (suporte a coordenadas sub-pixel). */
    private double x;

    /** Posição vertical no plano 2D. */
    private double y;

    /** Largura da entidade em pixels. */
    private final int width;

    /** Altura da entidade em pixels. */
    private final int height;

    /**
     * Construtor da entidade com validação de dimensões.
     *
     * @param x posição horizontal inicial.
     * @param y posição vertical inicial.
     * @param width largura da entidade (deve ser positiva).
     * @param height altura da entidade (deve ser positiva).
     */
    protected GameObject(double x, double y, int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("As dimensões do GameObject devem ser estritamente positivas.");
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Atualiza o estado físico ou comportamental da entidade a cada tick lógico.
     */
    public abstract void update();

    /**
     * Renderiza a entidade no contexto gráfico bidimensional.
     *
     * @param g contexto gráfico Java2D.
     */
    public abstract void draw(Graphics2D g);

    /**
     * Retorna a caixa delimitadora retangular (hitbox) padrão da entidade.
     *
     * @return {@link Rectangle} correspondente à área ocupada pelo objeto.
     */
    public Rectangle getBounds() {
        return new Rectangle((int) Math.round(x), (int) Math.round(y), width, height);
    }

    /**
     * Desloca a posição da entidade por deltas relativos.
     *
     * @param dx deslocamento horizontal.
     * @param dy deslocamento vertical.
     */
    protected void moveBy(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    /**
     * Define uma nova posição absoluta para a entidade.
     *
     * @param x nova coordenada horizontal.
     * @param y nova coordenada vertical.
     */
    protected void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
