package flappybird.game;

import flappybird.entities.Bird;
import flappybird.entities.Ground;
import flappybird.entities.PipePair;

import java.awt.Rectangle;
import java.util.List;

/**
 * Responsável pela detecção geométrica de colisões entre as entidades do jogo.
 *
 * <p><strong>Separação de Responsabilidades e Polimorfismo:</strong>
 * Isola completamente a lógica matemática de interseção da interface gráfica e das próprias
 * entidades. Utiliza o método polimórfico {@link Bird#getBounds()} para obter a hitbox ajustada
 * e compara com os retângulos dos obstáculos, chão e teto.</p>
 */
public class CollisionDetector {

    /**
     * Verifica se o pássaro colidiu com algum elemento fatal do cenário (canos, chão ou teto).
     *
     * @param bird o pássaro controlado pelo jogador.
     * @param ground o chão do cenário.
     * @param pipePairs lista dos obstáculos ativos.
     * @return {@code true} se houver qualquer colisão; {@code false} caso a trajetória esteja desobstruída.
     */
    public boolean checkCollision(Bird bird, Ground ground, List<PipePair> pipePairs) {
        if (bird == null) {
            return false;
        }

        // 1. Colisão com o teto (impede voar para fora da tela)
        if (checkCeilingCollision(bird)) {
            return true;
        }

        // 2. Colisão com o chão
        if (checkGroundCollision(bird, ground)) {
            return true;
        }

        // 3. Colisão com obstáculos (canos)
        return checkPipeCollision(bird, pipePairs);
    }

    /**
     * Verifica colisão com o limite superior da janela (teto).
     */
    public boolean checkCeilingCollision(Bird bird) {
        return bird.getY() <= 0;
    }

    /**
     * Verifica colisão com o chão.
     */
    public boolean checkGroundCollision(Bird bird, Ground ground) {
        if (ground == null) {
            return false;
        }
        return bird.getBounds().intersects(ground.getBounds());
    }

    /**
     * Verifica se a hitbox do pássaro intercepta qualquer um dos canos ativos.
     */
    public boolean checkPipeCollision(Bird bird, List<PipePair> pipePairs) {
        if (pipePairs == null || pipePairs.isEmpty()) {
            return false;
        }

        Rectangle birdBounds = bird.getBounds();

        for (PipePair pair : pipePairs) {
            Rectangle topBounds = pair.getTopPipe().getBounds();
            Rectangle bottomBounds = pair.getBottomPipe().getBounds();

            if (birdBounds.intersects(topBounds) || birdBounds.intersects(bottomBounds)) {
                return true;
            }
        }

        return false;
    }
}
