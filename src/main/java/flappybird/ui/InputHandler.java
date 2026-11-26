package flappybird.ui;

import flappybird.game.Difficulty;
import flappybird.game.Game;
import flappybird.game.GameState;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Gerencia as entradas de teclado e mouse do jogador.
 *
 * <p><strong>Separação de Responsabilidades:</strong>
 * Esta classe é responsável unicamente por traduzir eventos brutos de hardware (pressionar tecla,
 * clique do mouse) em comandos de alto nível para o domínio do jogo ({@link Game#flap()},
 * {@link Game#start()}, {@link Game#restart()}). Ela também implementa controle de repetição para
 * garantir que manter a barra de espaço pressionada não gere múltiplos saltos indesejados.</p>
 */
public class InputHandler extends MouseAdapter implements java.awt.event.KeyListener {

    private final Game game;
    private final Runnable onToggleMute;
    private boolean spacePressed;
    private boolean mutePressed;
    private boolean difficultyKeyPressed;

    public InputHandler(Game game) {
        this(game, null);
    }

    public InputHandler(Game game, Runnable onToggleMute) {
        if (game == null) {
            throw new IllegalArgumentException("O jogo não pode ser nulo.");
        }
        this.game = game;
        this.onToggleMute = onToggleMute;
        this.spacePressed = false;
        this.mutePressed = false;
        this.difficultyKeyPressed = false;
    }

    private void handlePrimaryAction() {
        if (game.getState() == GameState.MENU) {
            game.start();
        } else if (game.getState() == GameState.PLAYING) {
            game.flap();
        } else if (game.getState() == GameState.GAME_OVER) {
            game.restart();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            // Ignora disparo contínuo enquanto a tecla estiver fisicamente mantida pressionada
            if (!spacePressed) {
                spacePressed = true;
                handlePrimaryAction();
            }
        } else if (keyCode == KeyEvent.VK_ESCAPE) {
            if (game.getState() == GameState.GAME_OVER) {
                game.backToMenu();
            }
        } else if (keyCode == KeyEvent.VK_M) {
            if (!mutePressed && onToggleMute != null) {
                mutePressed = true;
                onToggleMute.run();
            }
        } else if (isDifficultyToggleKey(keyCode)) {
            if (!difficultyKeyPressed && game.getState() == GameState.MENU) {
                difficultyKeyPressed = true;
                game.toggleDifficulty();
            }
        } else if (keyCode == KeyEvent.VK_1 || keyCode == KeyEvent.VK_NUMPAD1) {
            if (!difficultyKeyPressed && game.getState() == GameState.MENU) {
                difficultyKeyPressed = true;
                game.setDifficulty(Difficulty.MEDIUM);
            }
        } else if (keyCode == KeyEvent.VK_2 || keyCode == KeyEvent.VK_NUMPAD2) {
            if (!difficultyKeyPressed && game.getState() == GameState.MENU) {
                difficultyKeyPressed = true;
                game.setDifficulty(Difficulty.HARD);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            spacePressed = false;
        } else if (keyCode == KeyEvent.VK_M) {
            mutePressed = false;
        } else if (isDifficultyToggleKey(keyCode)
                || keyCode == KeyEvent.VK_1 || keyCode == KeyEvent.VK_NUMPAD1
                || keyCode == KeyEvent.VK_2 || keyCode == KeyEvent.VK_NUMPAD2) {
            difficultyKeyPressed = false;
        }
    }

    private boolean isDifficultyToggleKey(int keyCode) {
        return keyCode == KeyEvent.VK_LEFT
                || keyCode == KeyEvent.VK_RIGHT
                || keyCode == KeyEvent.VK_A
                || keyCode == KeyEvent.VK_D;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Não utilizado
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Dispara ação primária apenas com o botão esquerdo do mouse
        if (e.getButton() == MouseEvent.BUTTON1) {
            handlePrimaryAction();
        }
    }
}
