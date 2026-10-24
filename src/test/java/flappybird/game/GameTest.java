package flappybird.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes unitários para a máquina de estados e fluxo lógico do jogo.
 */
class GameTest {

    @Test
    @DisplayName("Deve inicializar no estado MENU com pontuação zerada")
    void deveInicializarNoEstadoMenu() {
        Game game = new Game();
        assertEquals(GameState.MENU, game.getState());
        assertEquals(0, game.getScore());
        assertEquals(0, game.getBestScore());
    }

    @Test
    @DisplayName("Deve transitar de MENU para PLAYING ao chamar start")
    void deveTransitarParaPlayingAoIniciar() {
        Game game = new Game();
        game.start();
        assertEquals(GameState.PLAYING, game.getState());
    }

    @Test
    @DisplayName("Deve atualizar o melhor placar quando a pontuação atual for maior")
    void deveAtualizarMelhorPlacar() {
        Game game = new Game();
        game.setScore(5);
        assertEquals(5, game.getScore());
        assertEquals(5, game.getBestScore());

        game.setScore(3);
        assertEquals(3, game.getScore());
        assertEquals(5, game.getBestScore(), "O recorde anterior deve ser preservado");
    }

    @Test
    @DisplayName("Deve retornar ao estado MENU ao chamar backToMenu")
    void deveRetornarAoMenu() {
        Game game = new Game();
        game.start();
        game.setScore(10);

        game.backToMenu();
        assertEquals(GameState.MENU, game.getState());
        assertEquals(0, game.getScore());
        assertEquals(10, game.getBestScore(), "O recorde deve ser mantido mesmo voltando ao menu");
    }
}
