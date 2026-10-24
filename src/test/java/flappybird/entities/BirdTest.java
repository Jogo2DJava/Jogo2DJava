package flappybird.entities;

import flappybird.utils.GameConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.Rectangle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes unitários para a entidade Bird (física, pulo e limites).
 */
class BirdTest {

    @Test
    @DisplayName("Deve inicializar na posição padrão e velocidade vertical nula")
    void deveIniciarNaPosicaoPadraoComVelocidadeZero() {
        Bird bird = new Bird();
        assertEquals(GameConfig.BIRD_START_X, bird.getX(), 0.001);
        assertEquals(GameConfig.BIRD_START_Y, bird.getY(), 0.001);
        assertEquals(0.0, bird.getVelocityY(), 0.001);
    }

    @Test
    @DisplayName("Deve acelerar para baixo sob efeito da gravidade")
    void deveAcelerarComAGravidade() {
        Bird bird = new Bird();
        bird.update();

        assertEquals(GameConfig.GRAVITY, bird.getVelocityY(), 0.001);
        assertEquals(GameConfig.BIRD_START_Y + GameConfig.GRAVITY, bird.getY(), 0.001);
    }

    @Test
    @DisplayName("Deve limitar a velocidade de queda à velocidade terminal máxima")
    void deveRespeitarVelocidadeMaximaDeQueda() {
        Bird bird = new Bird();
        // Simula 100 ticks de queda livre
        for (int i = 0; i < 100; i++) {
            bird.update();
        }

        assertEquals(GameConfig.MAX_FALL_SPEED, bird.getVelocityY(), 0.001);
    }

    @Test
    @DisplayName("Deve aplicar força de pulo ascendente ao bater asas (flap)")
    void deveAplicarImpulsoAoBaterAsas() {
        Bird bird = new Bird();
        bird.update(); // deixa cair um pouco
        bird.flap();

        assertEquals(GameConfig.JUMP_FORCE, bird.getVelocityY(), 0.001);
        assertTrue(bird.getVelocityY() < 0, "A velocidade para cima deve ser negativa no sistema de coordenadas de tela");
    }

    @Test
    @DisplayName("Deve restaurar coordenadas e velocidade ao resetar")
    void deveRestaurarEstadoAoResetar() {
        Bird bird = new Bird();
        bird.flap();
        for (int i = 0; i < 10; i++) {
            bird.update();
        }

        bird.reset();
        assertEquals(GameConfig.BIRD_START_X, bird.getX(), 0.001);
        assertEquals(GameConfig.BIRD_START_Y, bird.getY(), 0.001);
        assertEquals(0.0, bird.getVelocityY(), 0.001);
    }

    @Test
    @DisplayName("Deve fornecer caixa delimitadora ajustada (hitbox polimórfica justa)")
    void deveFornecerHitboxAjustadaPolimorficamente() {
        Bird bird = new Bird();
        Rectangle bounds = bird.getBounds();

        assertEquals(GameConfig.BIRD_WIDTH - 6, bounds.width);
        assertEquals(GameConfig.BIRD_HEIGHT - 6, bounds.height);
    }
}
