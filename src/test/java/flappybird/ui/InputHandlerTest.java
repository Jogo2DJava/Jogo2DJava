package flappybird.ui;

import flappybird.game.Difficulty;
import flappybird.game.Game;
import flappybird.game.GameState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testes unitários para o controlador de entradas (teclado e cliques nos botões de dificuldade).
 */
class InputHandlerTest {

    private final JPanel dummyPanel = new JPanel();

    @Test
    @DisplayName("Deve alternar dificuldade no MENU via setas e teclas 1/2")
    void deveSelecionarDificuldadeNoMenuViaTeclado() {
        Game game = new Game();
        InputHandler handler = new InputHandler(game);

        assertEquals(Difficulty.MEDIUM, game.getDifficulty());

        // Pressiona seta direita -> muda para HARD
        handler.keyPressed(new KeyEvent(dummyPanel, KeyEvent.KEY_PRESSED, 0L, 0, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED));
        handler.keyReleased(new KeyEvent(dummyPanel, KeyEvent.KEY_RELEASED, 0L, 0, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED));
        assertEquals(Difficulty.HARD, game.getDifficulty());

        // Pressiona tecla 1 -> seleciona MEDIUM
        handler.keyPressed(new KeyEvent(dummyPanel, KeyEvent.KEY_PRESSED, 0L, 0, KeyEvent.VK_1, '1'));
        handler.keyReleased(new KeyEvent(dummyPanel, KeyEvent.KEY_RELEASED, 0L, 0, KeyEvent.VK_1, '1'));
        assertEquals(Difficulty.MEDIUM, game.getDifficulty());

        // Pressiona tecla 2 -> seleciona HARD
        handler.keyPressed(new KeyEvent(dummyPanel, KeyEvent.KEY_PRESSED, 0L, 0, KeyEvent.VK_2, '2'));
        handler.keyReleased(new KeyEvent(dummyPanel, KeyEvent.KEY_RELEASED, 0L, 0, KeyEvent.VK_2, '2'));
        assertEquals(Difficulty.HARD, game.getDifficulty());
    }

    @Test
    @DisplayName("Clicar nos botões MÉDIO e DIFÍCIL no menu deve alterar a dificuldade sem iniciar a partida")
    void clicarNosBotoesDeDificuldadeDeveApenasSelecionarModo() {
        Game game = new Game();
        InputHandler handler = new InputHandler(game);

        int hardCenterX = MenuScreen.HARD_BUTTON_BOUNDS.x + (MenuScreen.HARD_BUTTON_BOUNDS.width / 2);
        int hardCenterY = MenuScreen.HARD_BUTTON_BOUNDS.y + (MenuScreen.HARD_BUTTON_BOUNDS.height / 2);

        handler.mousePressed(new MouseEvent(dummyPanel, MouseEvent.MOUSE_PRESSED, 0L, 0,
                hardCenterX, hardCenterY, 1, false, MouseEvent.BUTTON1));

        assertEquals(Difficulty.HARD, game.getDifficulty(), "Deve selecionar modo HARD ao clicar no botão DIFÍCIL");
        assertEquals(GameState.MENU, game.getState(), "Não deve iniciar a partida ao clicar no botão de dificuldade");

        int mediumCenterX = MenuScreen.MEDIUM_BUTTON_BOUNDS.x + (MenuScreen.MEDIUM_BUTTON_BOUNDS.width / 2);
        int mediumCenterY = MenuScreen.MEDIUM_BUTTON_BOUNDS.y + (MenuScreen.MEDIUM_BUTTON_BOUNDS.height / 2);

        handler.mousePressed(new MouseEvent(dummyPanel, MouseEvent.MOUSE_PRESSED, 0L, 0,
                mediumCenterX, mediumCenterY, 1, false, MouseEvent.BUTTON1));

        assertEquals(Difficulty.MEDIUM, game.getDifficulty(), "Deve selecionar modo MEDIUM ao clicar no botão MÉDIO");
        assertEquals(GameState.MENU, game.getState());
    }
}
