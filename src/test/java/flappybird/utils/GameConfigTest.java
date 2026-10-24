package flappybird.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Teste mínimo para confirmar que o JUnit 5 está configurado no build.
 * Os testes de lógica do jogo serão criados junto com cada funcionalidade.
 */
class GameConfigTest {

    @Test
    void dimensoesDaJanelaDevemSerPositivas() {
        assertTrue(GameConfig.WINDOW_WIDTH > 0);
        assertTrue(GameConfig.WINDOW_HEIGHT > 0);
    }

    @Test
    void janelaDeveSerVertical() {
        // O Flappy Bird é jogado em uma tela "em pé" (retrato).
        assertTrue(GameConfig.WINDOW_HEIGHT > GameConfig.WINDOW_WIDTH);
    }

    @Test
    void tituloNaoDeveSerVazio() {
        assertFalse(GameConfig.TITLE.isBlank());
    }
}
