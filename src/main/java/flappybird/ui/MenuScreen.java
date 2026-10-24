package flappybird.ui;

import flappybird.game.Game;
import flappybird.utils.DrawUtils;
import flappybird.utils.GameConfig;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Tela de menu inicial do jogo.
 *
 * <p>Exibe o título estilizado do jogo, orientações de comandos e o recorde atual da sessão.</p>
 */
public class MenuScreen implements Screen {

    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 42);
    private static final Font SUBTITLE_FONT = new Font("SansSerif", Font.BOLD, 18);
    private static final Font INSTRUCTION_FONT = new Font("SansSerif", Font.PLAIN, 14);
    private static final Font FOOTER_FONT = new Font("SansSerif", Font.ITALIC, 12);

    private static final Color TITLE_COLOR = new Color(255, 215, 0);     // Dourado
    private static final Color OUTLINE_COLOR = new Color(30, 30, 30);     // Preto carvão
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color ACCENT_COLOR = new Color(255, 230, 100);

    @Override
    public void draw(Graphics2D g, Game game) {
        // Título estilizado
        DrawUtils.drawCenteredOutlinedText(g, "FLAPPY BIRD", 170, TITLE_FONT, TITLE_COLOR, OUTLINE_COLOR);

        // Subtítulo de ação
        DrawUtils.drawCenteredOutlinedText(g, "Pressione ESPAÇO ou Clique", 290, SUBTITLE_FONT, TEXT_COLOR, OUTLINE_COLOR);
        DrawUtils.drawCenteredOutlinedText(g, "para Iniciar", 320, SUBTITLE_FONT, TEXT_COLOR, OUTLINE_COLOR);

        // Exibição do recorde da sessão (se houver)
        if (game.getBestScore() > 0) {
            String bestText = "Melhor Placar: " + game.getBestScore();
            DrawUtils.drawCenteredOutlinedText(g, bestText, 380, INSTRUCTION_FONT, ACCENT_COLOR, OUTLINE_COLOR);
        }

        // Instruções de controle
        DrawUtils.drawCenteredOutlinedText(g, "Comandos: [ESPAÇO] ou [MOUSE] para voar", 450, INSTRUCTION_FONT, TEXT_COLOR, OUTLINE_COLOR);

        // Rodapé acadêmico
        DrawUtils.drawCenteredOutlinedText(g, "Análise e Desenvolvimento de Sistemas • Java 2D POO", 550, FOOTER_FONT, new Color(240, 240, 240), OUTLINE_COLOR);
    }
}
