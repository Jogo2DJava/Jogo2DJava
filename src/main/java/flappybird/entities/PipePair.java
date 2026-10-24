package flappybird.entities;

import flappybird.utils.GameConfig;

import java.awt.Graphics2D;

/**
 * Representa um par de obstáculos (cano superior e cano inferior) com vão de passagem livre.
 *
 * <p><strong>Pilar da POO — Composição vs. Herança:</strong>
 * Esta classe é um exemplo clássico de <em>Composição</em> ("tem um"). {@code PipePair}
 * <strong>não</strong> herda de {@link GameObject}, pois um par de canos não possui uma única
 * caixa de colisão ou coordenada singular; em vez disso, ele é <strong>composto</strong> por dois
 * objetos {@link Pipe} independentes e gerencia o estado de pontuação através do sinalizador {@link #passed}.</p>
 */
public class PipePair {

    private final Pipe topPipe;
    private final Pipe bottomPipe;
    private boolean passed;

    /**
     * Constrói um par de canos calculando as alturas com base na abertura.
     *
     * @param x posição horizontal inicial do par.
     * @param topHeight altura do cano superior.
     * @param gap abertura livre entre os dois canos em pixels.
     */
    public PipePair(double x, int topHeight, int gap) {
        int groundY = GameConfig.WINDOW_HEIGHT - GameConfig.GROUND_HEIGHT;
        int bottomY = topHeight + gap;
        int bottomHeight = groundY - bottomY;

        this.topPipe = new Pipe(x, 0, GameConfig.PIPE_WIDTH, topHeight, true);
        this.bottomPipe = new Pipe(x, bottomY, GameConfig.PIPE_WIDTH, bottomHeight, false);
        this.passed = false;
    }

    /**
     * Atualiza a posição de ambos os canos simultaneamente.
     */
    public void update() {
        topPipe.update();
        bottomPipe.update();
    }

    /**
     * Renderiza os dois canos no contexto gráfico.
     *
     * @param g contexto gráfico Java2D.
     */
    public void draw(Graphics2D g) {
        topPipe.draw(g);
        bottomPipe.draw(g);
    }

    /**
     * Verifica se ambos os canos já saíram da tela pela esquerda.
     *
     * @return {@code true} se estiverem fora da tela; {@code false} caso contrário.
     */
    public boolean isOffScreen() {
        return topPipe.isOffScreen();
    }

    public Pipe getTopPipe() {
        return topPipe;
    }

    public Pipe getBottomPipe() {
        return bottomPipe;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public double getX() {
        return topPipe.getX();
    }
}
