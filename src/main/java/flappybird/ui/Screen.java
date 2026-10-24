package flappybird.ui;

import flappybird.game.Game;

import java.awt.Graphics2D;

/**
 * Interface que define o contrato de renderização para as diferentes telas do jogo.
 *
 * <p><strong>Pilar da POO — Abstração e Polimorfismo:</strong>
 * Ao abstrair o conceito de "tela" através desta interface, o {@link GamePanel}
 * não precisa conhecer detalhes específicos de como o Menu, o HUD ou o Game Over
 * são desenhados. Ele simplesmente invoca {@code screen.draw(g, game)} de forma
 * polimórfica com base no estado atual da partida.</p>
 */
public interface Screen {

    /**
     * Renderiza os elementos visuais da tela sobre o contexto gráfico informado.
     *
     * @param g contexto gráfico bidimensional (Java2D).
     * @param game referência ao estado atual do jogo para leitura de pontuação e recordes.
     */
    void draw(Graphics2D g, Game game);
}
