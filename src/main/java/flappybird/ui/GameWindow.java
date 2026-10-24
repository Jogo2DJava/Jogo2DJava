package flappybird.ui;

import flappybird.utils.GameConfig;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * Janela principal do jogo.
 *
 * <p>Herda de {@link JFrame} (herança da biblioteca Swing): reaproveitamos
 * tudo o que uma janela já sabe fazer e apenas configuramos o que é
 * específico do nosso jogo.</p>
 */
public class GameWindow extends JFrame {

    /** Cor de céu provisória, até o painel de jogo ser criado (Issue #2). */
    private static final Color SKY_COLOR = new Color(112, 197, 206);

    public GameWindow() {
        super(GameConfig.TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Define o tamanho da ÁREA INTERNA (sem bordas e barra de título)
        // e deixa o pack() calcular o tamanho total da janela.
        getContentPane().setPreferredSize(
                new Dimension(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT));
        getContentPane().setBackground(SKY_COLOR);
        pack();

        // Centraliza a janela na tela.
        setLocationRelativeTo(null);
    }
}
