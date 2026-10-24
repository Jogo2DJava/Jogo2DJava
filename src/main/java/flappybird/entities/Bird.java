package flappybird.entities;

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
    private int wingTick;

    // Paleta de cores do pássaro (vetorial, estilo clássico)
    private static final Color BODY_YELLOW = new Color(245, 196, 44);
    private static final Color BELLY_CREAM = new Color(255, 235, 160);
    private static final Color OUTLINE_BROWN = new Color(50, 35, 15);
    private static final Color BEAK_ORANGE = new Color(235, 90, 30);
    private static final Color WING_WHITE = new Color(255, 255, 255, 220);
    private static final Color CHEEK_PINK = new Color(255, 150, 150, 160);

    public Bird() {
        super(GameConfig.BIRD_START_X, GameConfig.BIRD_START_Y,
                GameConfig.BIRD_WIDTH, GameConfig.BIRD_HEIGHT);
        this.velocityY = 0.0;
        this.wingTick = 0;
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
        // Aplica aceleração gravitacional a cada tick
        velocityY += GameConfig.GRAVITY;

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

        // 1. Corpo principal (oval amarela com contorno escuro)
        g.setColor(OUTLINE_BROWN);
        g.fillOval(px - 1, py - 1, getWidth() + 2, getHeight() + 2);
        g.setColor(BODY_YELLOW);
        g.fillOval(px, py, getWidth(), getHeight());

        // 2. Barriga clara
        g.setColor(BELLY_CREAM);
        g.fillOval(px + 4, py + 8, getWidth() - 14, getHeight() - 10);

        // 3. Bochecha rosada
        g.setColor(CHEEK_PINK);
        g.fillOval(px + 14, py + 12, 8, 6);

        // 4. Asa animada (bate conforme o tempo)
        int wingOffsetY = (int) (Math.sin(wingTick * 0.4) * 3);
        g.setColor(OUTLINE_BROWN);
        g.fillRoundRect(px + 4, py + 7 + wingOffsetY, 14, 10, 6, 6);
        g.setColor(WING_WHITE);
        g.fillRoundRect(px + 5, py + 8 + wingOffsetY, 12, 8, 4, 4);

        // 5. Olho grande clássico
        g.setColor(OUTLINE_BROWN);
        g.fillOval(px + 18, py + 2, 11, 11);
        g.setColor(Color.WHITE);
        g.fillOval(px + 19, py + 3, 9, 9);
        // Pupila
        g.setColor(Color.BLACK);
        g.fillOval(px + 23, py + 5, 4, 5);
        // Brilho no olhar
        g.setColor(Color.WHITE);
        g.fillOval(px + 24, py + 6, 2, 2);

        // 6. Bico alaranjado
        g.setColor(OUTLINE_BROWN);
        int[] beakX = {px + 26, px + 35, px + 26};
        int[] beakY = {py + 10, py + 15, py + 19};
        g.fillPolygon(beakX, beakY, 3);
        g.setColor(BEAK_ORANGE);
        int[] beakInnerX = {px + 26, px + 33, px + 26};
        int[] beakInnerY = {py + 11, py + 15, py + 18};
        g.fillPolygon(beakInnerX, beakInnerY, 3);

        // Restaura a transformação gráfica original
        g.setTransform(originalTransform);
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }
}
