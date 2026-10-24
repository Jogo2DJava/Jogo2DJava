package flappybird.game;

import flappybird.utils.GameConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes unitários para a máquina de estados e fluxo lógico do jogo.
 */
class GameTest {

    @Test
    @DisplayName("Deve inicializar no estado MENU com pontuação zerada e pássaro criado")
    void deveInicializarNoEstadoMenu() {
        Game game = new Game();
        assertEquals(GameState.MENU, game.getState());
        assertEquals(0, game.getScore());
        assertEquals(0, game.getBestScore());
        assertNotNull(game.getBird(), "O pássaro deve ser instanciado junto com o jogo");
    }

    @Test
    @DisplayName("Deve transitar de MENU para PLAYING ao chamar start e aplicar primeiro pulo")
    void deveTransitarParaPlayingAoIniciar() {
        Game game = new Game();
        game.start();

        assertEquals(GameState.PLAYING, game.getState());
        assertEquals(GameConfig.JUMP_FORCE, game.getBird().getVelocityY(), 0.001,
                "O pássaro deve receber impulso ascendente ao iniciar a partida");
    }

    @Test
    @DisplayName("Deve permitir bater asas durante a partida alterando velocidade do pássaro")
    void deveBaterAsasDurantePartida() {
        Game game = new Game();
        game.start();
        game.update(); // avança um tick, pássaro acelera para baixo
        game.flap();

        assertEquals(GameConfig.JUMP_FORCE, game.getBird().getVelocityY(), 0.001);
    }

    @Test
    @DisplayName("Deve atualizar o melhor placar quando a pontuação atual for incrementada")
    void deveAtualizarMelhorPlacar() {
        Game game = new Game();
        game.start();
        game.getScoreManager().increment();
        game.getScoreManager().increment();

        assertEquals(2, game.getScore());
        assertEquals(2, game.getBestScore());
    }

    @Test
    @DisplayName("Deve bloquear reinício durante o tempo de cooldown de proteção no Game Over")
    void deveBloquearReinicioDuranteCooldown() {
        Game game = new Game();
        game.start();

        // Derruba o pássaro até o chão para disparar o Game Over
        for (int i = 0; i < 80; i++) {
            game.update();
        }

        assertEquals(GameState.GAME_OVER, game.getState());
        assertTrue(game.getGameOverCooldown() > 0, "O cooldown deve ser ativado no Game Over");
        assertFalse(game.canRestart(), "Não deve permitir reinício imediato");

        // Tenta reiniciar antes do término do cooldown (deve ser ignorado)
        game.restart();
        assertEquals(GameState.GAME_OVER, game.getState(), "Deve permanecer em GAME_OVER");

        // Simula a passagem de ticks até esgotar o cooldown
        while (game.getGameOverCooldown() > 0) {
            game.update();
        }

        assertTrue(game.canRestart(), "Agora deve permitir o reinício");
        game.restart();
        assertEquals(GameState.PLAYING, game.getState(), "Deve reiniciar para PLAYING");
        assertEquals(0, game.getScore(), "Pontuação deve zerar");
    }

    @Test
    @DisplayName("Deve retornar ao estado MENU e resetar pontuação mantendo recorde ao chamar backToMenu")
    void deveRetornarAoMenu() {
        Game game = new Game();
        game.start();
        game.getScoreManager().increment();
        game.getScoreManager().increment();

        game.backToMenu();
        assertEquals(GameState.MENU, game.getState());
        assertEquals(0, game.getScore());
        assertEquals(2, game.getBestScore(), "O recorde deve ser mantido mesmo voltando ao menu");
        assertEquals(GameConfig.BIRD_START_X, game.getBird().getX(), 0.001);
    }
}
