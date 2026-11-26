package flappybird.entities;

import flappybird.game.Difficulty;
import flappybird.utils.GameConfig;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

/**
 * Representa o pássaro controlado pelo jogador no mundo do jogo.
 *
 * <p><strong>Pilares da POO aplicados:</strong>
 * <ul>
 *   <li><strong>Herança:</strong> {@code Bird extends GameObject}, reutilizando coordenadas,
 *   dimensões e integrando-se à coleção polimórfica de entidades.</li>
 *   <li><strong>Encapsulamento:</strong> A velocidade vertical {@code velocityY} e a rotação
 *   são estritamente privadas; o voo só pode ser acionado por meio do método público {@link #flap()}.</li>
 *   <li><strong>Polimorfismo:</strong> Sobrescreve {@link #getBounds()} fornecendo uma caixa de
 *   colisão mais justa (menor que o desenho visual), além da implementação própria de {@link #update()}
 *   e {@link #draw(Graphics2D)}.</li>
 * </ul>
 * </p>
 */
public class Bird extends GameObject {

    private double velocityY;
    private double gravity;
    private int wingTick;

    // Paleta de cores do dragãozinho (verde escuro com detalhes de fogo)
    private static final Color BODY_GREEN = new Color(36, 118, 62);
    private static final Color BODY_SHADE = new Color(22, 82, 42);
    private static final Color BELLY_CREAM = new Color(210, 232, 168);
    private static final Color OUTLINE_DARK = new Color(14, 36, 20);
    private static final Color SPIKE_RED = new Color(220, 75, 28);
    private static final Color HORN_IVORY = new Color(248, 240, 210);
    private static final Color WING_MEMBRANE = new Color(62, 158, 88);
    private static final Color WING_BONE = new Color(155, 218, 120);
    private static final Color EYE_IRIS = new Color(240, 150, 20);
    private static final Color FLAME_RED = new Color(235, 55, 15);
    private static final Color FLAME_OUTER = new Color(255, 125, 20);
    private static final Color FLAME_INNER = new Color(255, 235, 70);

    public Bird() {
        this(GameConfig.GRAVITY);
    }

    public Bird(Difficulty difficulty) {
        this(difficulty != null ? difficulty.getGravity() : GameConfig.GRAVITY);
    }

    public Bird(double gravity) {
        super(GameConfig.BIRD_START_X, GameConfig.BIRD_START_Y,
                GameConfig.BIRD_WIDTH, GameConfig.BIRD_HEIGHT);
        if (gravity <= 0) {
            throw new IllegalArgumentException("A gravidade deve ser positiva.");
        }
        this.gravity = gravity;
        this.velocityY = 0.0;
        this.wingTick = 0;
    }

    /**
     * Aplica os parâmetros físicos correspondentes à dificuldade informada.
     *
     * @param difficulty modo de dificuldade ativo.
     */
    public void applyDifficulty(Difficulty difficulty) {
        if (difficulty == null) {
            throw new IllegalArgumentException("A dificuldade não pode ser nula.");
        }
        this.gravity = difficulty.getGravity();
    }

    /**
     * Aplica o impulso de voo ao pássaro, substituindo a velocidade vertical
     * pela força instantânea de pulo.
     */
    public void flap() {
        this.velocityY = GameConfig.JUMP_FORCE;
    }

    /**
     * Restaura o pássaro à posição e velocidade iniciais da partida.
     */
    public void reset() {
        setPosition(GameConfig.BIRD_START_X, GameConfig.BIRD_START_Y);
        this.velocityY = 0.0;
        this.wingTick = 0;
    }

    @Override
    public void update() {
        // Aplica aceleração gravitacional configurada para a dificuldade atual a cada tick
        velocityY += gravity;

        // Limita a velocidade terminal de queda
        if (velocityY > GameConfig.MAX_FALL_SPEED) {
            velocityY = GameConfig.MAX_FALL_SPEED;
        }

        // Desloca verticalmente
        moveBy(0, velocityY);

        wingTick++;
    }

    /**
     * Sobrescreve polimorficamente o retângulo delimitador para fornecer uma
     * margem de tolerância (hitbox 3 pixels menor que o contorno visual),
     * tornando a jogabilidade justa e recompensadora.
     */
    @Override
    public Rectangle getBounds() {
        int insetX = 3;
        int insetY = 3;
        return new Rectangle(
                (int) Math.round(getX()) + insetX,
                (int) Math.round(getY()) + insetY,
                getWidth() - (insetX * 2),
                getHeight() - (insetY * 2)
        );
    }

    @Override
    public void draw(Graphics2D g) {
        AffineTransform originalTransform = g.getTransform();

        // Ponto central para rotação
        double centerX = getX() + (getWidth() / 2.0);
        double centerY = getY() + (getHeight() / 2.0);

        // Calcula o ângulo de rotação em função da velocidade vertical (em radianos)
        double angleDeg = velocityY * 3.5;
        if (angleDeg < -25.0) angleDeg = -25.0; // limite subindo
        if (angleDeg > 70.0) angleDeg = 70.0;   // limite mergulhando
        double angleRad = Math.toRadians(angleDeg);

        g.translate(centerX, centerY);
        g.rotate(angleRad);
        g.translate(-centerX, -centerY);

        int px = (int) Math.round(getX());
        int py = (int) Math.round(getY());
        int w = getWidth();
        int h = getHeight();

        int tailOffsetY = (int) Math.round(Math.sin(wingTick * 0.3) * 2);
        int wingOffsetY = (int) Math.round(Math.sin(wingTick * 0.45) * 4);

        // 1. Cauda fininha de dragão (curvada para trás com um foguinho aceso na ponta)
        int ty = py + tailOffsetY;
        int[] tailOuterX = {px + 3, px - 4, px - 10, px - 15, px - 14, px - 9, px - 3, px + 3};
        int[] tailOuterY = {py + 14, ty + 15, ty + 14, ty + 11, ty + 14, ty + 18, ty + 19, py + 19};
        g.setColor(OUTLINE_DARK);
        g.fillPolygon(tailOuterX, tailOuterY, 8);

        int[] tailInnerX = {px + 3, px - 4, px - 10, px - 14, px - 13, px - 9, px - 3, px + 3};
        int[] tailInnerY = {py + 15, ty + 16, ty + 15, ty + 12, ty + 13, ty + 17, ty + 18, py + 18};
        g.setColor(BODY_GREEN);
        g.fillPolygon(tailInnerX, tailInnerY, 8);

        // Foguinho na ponta da cauda (chama subindo com 3 camadas: vermelho, laranja e amarelo)
        int fx = px - 15;
        int fy = ty + 12;
        int[] fireRedX = {fx - 3, fx - 4, fx - 1, fx + 1, fx + 3, fx + 4, fx + 2};
        int[] fireRedY = {fy, fy - 5, fy - 3, fy - 9, fy - 4, fy - 6, fy + 1};
        g.setColor(FLAME_RED);
        g.fillPolygon(fireRedX, fireRedY, 7);
        g.fillOval(fx - 3, fy - 3, 7, 6);

        int[] fireOrangeX = {fx - 2, fx - 2, fx + 1, fx + 3, fx + 2};
        int[] fireOrangeY = {fy, fy - 4, fy - 7, fy - 3, fy + 1};
        g.setColor(FLAME_OUTER);
        g.fillPolygon(fireOrangeX, fireOrangeY, 5);
        g.fillOval(fx - 2, fy - 2, 5, 5);

        g.setColor(FLAME_INNER);
        int[] fireYellowX = {fx - 1, fx + 1, fx + 2};
        int[] fireYellowY = {fy, fy - 4, fy};
        g.fillPolygon(fireYellowX, fireYellowY, 3);
        g.fillOval(fx - 1, fy - 1, 3, 3);

        // 2. Cristas dorsais (espinhos nas costas do dragão)
        for (int i = 0; i < 2; i++) {
            int sx = px + 2 + (i * 6);
            int sy = py + 1 - i;
            int[] spikeOutX = {sx, sx + 3, sx + 7};
            int[] spikeOutY = {sy + 3, sy - 5, sy + 3};
            g.setColor(OUTLINE_DARK);
            g.fillPolygon(spikeOutX, spikeOutY, 3);

            int[] spikeInX = {sx + 1, sx + 3, sx + 6};
            int[] spikeInY = {sy + 3, sy - 3, sy + 3};
            g.setColor(SPIKE_RED);
            g.fillPolygon(spikeInX, spikeInY, 3);
        }

        // 3. Corpo principal do dragãozinho (verde escuro)
        g.setColor(OUTLINE_DARK);
        g.fillRoundRect(px - 1, py, w - 3, h + 1, 18, 18);
        g.setColor(BODY_GREEN);
        g.fillRoundRect(px, py + 1, w - 5, h - 1, 16, 16);

        // 4. Chifrinho de marfim no topo da cabeça (desenhado sobre o contorno da cabeça)
        int[] hornOutX = {px + 13, px + 6, px + 19};
        int[] hornOutY = {py + 4, py - 6, py + 1};
        g.setColor(OUTLINE_DARK);
        g.fillPolygon(hornOutX, hornOutY, 3);

        int[] hornInX = {px + 14, px + 8, px + 18};
        int[] hornInY = {py + 3, py - 4, py + 2};
        g.setColor(HORN_IVORY);
        g.fillPolygon(hornInX, hornInY, 3);

        // Sombra inferior do corpo
        g.setColor(BODY_SHADE);
        g.fillArc(px + 1, py + 5, w - 7, h - 5, 190, 160);

        // 5. Barriga clara com listras de escamas
        g.setColor(BELLY_CREAM);
        g.fillOval(px + 4, py + 9, w - 15, h - 11);
        g.setColor(BODY_SHADE);
        g.drawArc(px + 6, py + 11, 13, 5, 200, 140);
        g.drawArc(px + 6, py + 15, 13, 5, 200, 140);

        // 6. Focinho esculpido de dragão (à frente)
        g.setColor(OUTLINE_DARK);
        g.fillRoundRect(px + 20, py + 6, 16, 13, 10, 10);
        g.setColor(BODY_GREEN);
        g.fillRoundRect(px + 21, py + 7, 14, 11, 8, 8);

        // Sopro de fogo sutil quando o dragãozinho bate asas (subindo)
        if (velocityY < -1.0) {
            int[] flameOutX = {px + 35, px + 44, px + 40, px + 46, px + 35};
            int[] flameOutY = {py + 12, py + 9, py + 14, py + 18, py + 16};
            g.setColor(FLAME_OUTER);
            g.fillPolygon(flameOutX, flameOutY, 5);

            int[] flameInX = {px + 35, px + 41, px + 35};
            int[] flameInY = {py + 13, py + 14, py + 16};
            g.setColor(FLAME_INNER);
            g.fillPolygon(flameInX, flameInY, 3);
        }

        // Narina do dragão
        g.setColor(OUTLINE_DARK);
        g.fillOval(px + 31, py + 9, 3, 3);

        // Boca e presinha branca (dentinho de dragão)
        g.setColor(OUTLINE_DARK);
        g.drawLine(px + 24, py + 15, px + 34, py + 15);
        int[] fangX = {px + 28, px + 30, px + 32};
        int[] fangY = {py + 15, py + 19, py + 15};
        g.setColor(Color.WHITE);
        g.fillPolygon(fangX, fangY, 3);
        g.setColor(OUTLINE_DARK);
        g.drawPolygon(fangX, fangY, 3);

        // 7. Olho expressivo de dragão (íris dourada/laranja e pupila vertical)
        g.setColor(OUTLINE_DARK);
        g.fillOval(px + 17, py + 2, 11, 11);
        g.setColor(Color.WHITE);
        g.fillOval(px + 18, py + 3, 9, 9);
        // Íris dourada/âmbar
        g.setColor(EYE_IRIS);
        g.fillOval(px + 21, py + 4, 5, 7);
        // Pupila em fenda vertical de réptil/dragão
        g.setColor(Color.BLACK);
        g.fillOval(px + 22, py + 4, 3, 7);
        // Brilho no olhar
        g.setColor(Color.WHITE);
        g.fillOval(px + 21, py + 5, 2, 2);
        // Sobrancelha de dragãozinho
        g.setColor(BODY_SHADE);
        int[] browX = {px + 16, px + 27, px + 26, px + 17};
        int[] browY = {py + 2, py + 4, py + 2, py + 1};
        g.fillPolygon(browX, browY, 4);

        // 8. Asa de dragão (membrana recortada estilo morcego/dragão)
        int wy = py + 7 + wingOffsetY;
        int[] wingOutX = {px + 15, px + 2, px - 1, px + 6, px + 11, px + 16};
        int[] wingOutY = {py + 11, wy - 6, wy + 7, wy + 3, wy + 7, py + 14};
        g.setColor(OUTLINE_DARK);
        g.fillPolygon(wingOutX, wingOutY, 6);

        int[] wingMemX = {px + 15, px + 3, px + 1, px + 6, px + 11, px + 15};
        int[] wingMemY = {py + 11, wy - 5, wy + 5, wy + 2, wy + 5, py + 13};
        g.setColor(WING_MEMBRANE);
        g.fillPolygon(wingMemX, wingMemY, 6);

        // Nervura e osso superior da asa
        g.setColor(OUTLINE_DARK);
        g.drawLine(px + 8, wy - 2, px + 6, wy + 3);
        g.setColor(WING_BONE);
        g.drawLine(px + 15, py + 11, px + 3, wy - 5);
        g.drawLine(px + 15, py + 12, px + 3, wy - 4);

        // Restaura a transformação gráfica original
        g.setTransform(originalTransform);
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        if (gravity <= 0) {
            throw new IllegalArgumentException("A gravidade deve ser positiva.");
        }
        this.gravity = gravity;
    }
}
