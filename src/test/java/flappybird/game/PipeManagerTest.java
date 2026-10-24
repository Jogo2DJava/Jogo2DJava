package flappybird.game;

import flappybird.entities.PipePair;
import flappybird.utils.GameConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes unitários para a classe PipeManager com Random determinístico injetado.
 */
class PipeManagerTest {

    @Test
    @DisplayName("Deve gerar novos canos a cada intervalo e respeitar limites de abertura")
    void deveGerarCanosNoIntervalo() {
        // Random com semente fixa para reprodutibilidade dos testes
        PipeManager manager = new PipeManager(new Random(42));
        assertEquals(0, manager.getPipePairs().size());

        // Primeiro tick: dispara geração imediata
        manager.update();
        assertEquals(1, manager.getPipePairs().size());

        PipePair pair = manager.getPipePairs().get(0);
        assertTrue(pair.getTopPipe().getHeight() >= 60, "Altura mínima do cano superior deve ser respeitada");

        int groundY = GameConfig.WINDOW_HEIGHT - GameConfig.GROUND_HEIGHT;
        int expectedGap = pair.getBottomPipe().getBounds().y - pair.getTopPipe().getHeight();
        assertEquals(GameConfig.PIPE_GAP, expectedGap, "A abertura livre deve corresponder exatamente ao PIPE_GAP");
    }

    @Test
    @DisplayName("Deve remover canos que saem da tela evitando acúmulo em memória")
    void deveRemoverCanosForaDaTela() {
        PipeManager manager = new PipeManager(new Random(1));
        manager.update(); // gera 1 par em x = WINDOW_WIDTH
        assertEquals(1, manager.getPipePairs().size());

        // Simula deslocamento até que o cano ultrapasse toda a tela à esquerda
        int stepsToExit = (int) Math.ceil((GameConfig.WINDOW_WIDTH + GameConfig.PIPE_WIDTH + 10) / GameConfig.PIPE_SPEED);
        for (int i = 0; i < stepsToExit; i++) {
            manager.update();
        }

        // Os canos originais devem ter saído e sido descartados
        for (PipePair pair : manager.getPipePairs()) {
            assertFalse(pair.isOffScreen(), "Nenhum cano fora da tela deve permanecer na lista ativa");
        }
    }

    @Test
    @DisplayName("Deve limpar todos os obstáculos ao acionar o reset")
    void deveLimparObstaculosAoResetar() {
        PipeManager manager = new PipeManager(new Random(1));
        manager.update();
        assertFalse(manager.getPipePairs().isEmpty());

        manager.reset();
        assertTrue(manager.getPipePairs().isEmpty(), "A lista de obstáculos deve ser esvaziada no reset");
    }
}
