package flappybird.ui;

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
    private boolean spacePressed;

    public InputHandler(Game game) {
        if (game == null) {
            throw new IllegalArgumentException("O jogo não pode ser nulo.");
        }
        this.game = game;
        this.spacePressed = false;
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
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            spacePressed = false;
        }
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
