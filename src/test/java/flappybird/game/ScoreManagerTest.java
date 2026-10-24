package flappybird.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes unitários para a classe ScoreManager.
 */
class ScoreManagerTest {

    @Test
    @DisplayName("Deve inicializar com pontuação atual e recorde zerados")
    void deveInicializarComPontuacaoERecordeZerados() {
        ScoreManager manager = new ScoreManager();
        assertEquals(0, manager.getScore());
        assertEquals(0, manager.getBestScore());
    }

    @Test
    @DisplayName("Deve incrementar pontuação e atualizar o recorde automaticamente")
    void deveIncrementarPontuacaoEAtualizarRecorde() {
        ScoreManager manager = new ScoreManager();
        manager.increment();
        assertEquals(1, manager.getScore());
        assertEquals(1, manager.getBestScore());

        manager.increment();
        assertEquals(2, manager.getScore());
        assertEquals(2, manager.getBestScore());
    }

    @Test
    @DisplayName("Deve zerar pontuação mantendo o melhor placar intacto ao resetar")
    void deveZerarPontuacaoMantendoRecordeAoResetar() {
        ScoreManager manager = new ScoreManager();
        manager.increment();
        manager.increment();
        manager.increment(); // 3 pontos
        assertEquals(3, manager.getScore());
        assertEquals(3, manager.getBestScore());

        manager.reset();
        assertEquals(0, manager.getScore(), "A pontuação da rodada deve zerar");
        assertEquals(3, manager.getBestScore(), "O melhor recorde histórico deve ser preservado");
    }
}
