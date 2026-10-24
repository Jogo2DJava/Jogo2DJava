package flappybird.game;

import flappybird.entities.Bird;
import flappybird.entities.Ground;
import flappybird.entities.PipePair;
import flappybird.utils.GameConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes unitários para o módulo CollisionDetector.
 */
class CollisionDetectorTest {

    private CollisionDetector detector;
    private Ground ground;

    @BeforeEach
    void setUp() {
        detector = new CollisionDetector();
        ground = new Ground();
    }

    @Test
    @DisplayName("Deve detectar colisão quando o pássaro toca ou ultrapassa o chão")
    void deveDetectarColisaoComChao() {
        Bird bird = new Bird();
        // Posiciona o pássaro na altura da superfície do chão
        int groundY = GameConfig.WINDOW_HEIGHT - GameConfig.GROUND_HEIGHT;
        bird.reset();
        bird.update();

        // Cria colisão forçada movendo o pássaro para dentro do chão
        Bird lowBird = new Bird() {
            @Override
            public double getY() {
                return groundY;
            }
        };

        assertTrue(detector.checkGroundCollision(lowBird, ground), "Deve detectar colisão com o chão");
    }

    @Test
    @DisplayName("Deve detectar colisão quando o pássaro atinge o teto da tela")
    void deveDetectarColisaoComTeto() {
        Bird highBird = new Bird() {
            @Override
            public double getY() {
                return 0.0;
            }
        };

        assertTrue(detector.checkCeilingCollision(highBird), "Deve detectar colisão com o teto");
    }

    @Test
    @DisplayName("Deve detectar colisão com o cano superior")
    void deveDetectarColisaoComCanoSuperior() {
        // Cano superior de altura 200 no x = 80
        PipePair pair = new PipePair(80, 200, 150);
        List<PipePair> pipes = List.of(pair);

        // Pássaro nas coordenadas x = 80, y = 100 (dentro do cano superior)
        Bird hittingBird = new Bird() {
            @Override
            public double getX() { return 80; }
            @Override
            public double getY() { return 100; }
        };

        assertTrue(detector.checkPipeCollision(hittingBird, pipes), "Deve colidir com o cano superior");
    }

    @Test
    @DisplayName("Deve detectar colisão com o cano inferior")
    void deveDetectarColisaoComCanoInferior() {
        // Cano superior até y = 100, vão de 150 (vão de 100 a 250). Cano inferior de y = 250 em diante.
        PipePair pair = new PipePair(80, 100, 150);
        List<PipePair> pipes = List.of(pair);

        // Pássaro nas coordenadas x = 80, y = 300 (dentro do cano inferior)
        Bird hittingBird = new Bird() {
            @Override
            public double getX() { return 80; }
            @Override
            public double getY() { return 300; }
        };

        assertTrue(detector.checkPipeCollision(hittingBird, pipes), "Deve colidir com o cano inferior");
    }

    @Test
    @DisplayName("Deve permitir passagem limpa pelo vão livre entre os canos")
    void devePermitirPassagemLimpaPeloVaoLivre() {
        // Cano superior até y = 100, vão de 150 (vão livre de y = 100 até y = 250).
        PipePair pair = new PipePair(80, 100, 150);
        List<PipePair> pipes = List.of(pair);

        // Pássaro exatamente no centro do vão (y = 150, altura 24, ocupa de 150 a 174)
        Bird passingBird = new Bird() {
            @Override
            public double getX() { return 80; }
            @Override
            public double getY() { return 150; }
        };

        assertFalse(detector.checkPipeCollision(passingBird, pipes), "Pássaro no centro da abertura não deve colidir");
    }

    @Test
    @DisplayName("Deve transitar para GAME_OVER ao detectar colisão na partida")
    void deveTransitarParaGameOverAoColidir() {
        Game game = new Game();
        game.start();
        assertEquals(GameState.PLAYING, game.getState());

        // Deixa o pássaro cair por tempo suficiente até atingir o chão
        for (int i = 0; i < 80; i++) {
            game.update();
        }

        assertEquals(GameState.GAME_OVER, game.getState(), "A partida deve encerrar com Game Over ao atingir o chão");
    }
}
