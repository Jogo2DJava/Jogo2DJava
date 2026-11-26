package flappybird.ui;

import flappybird.game.Difficulty;
import flappybird.game.Game;
import flappybird.utils.DrawUtils;
import flappybird.utils.GameConfig;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 * Tela de menu inicial do jogo.
 *
 * <p>Exibe o título estilizado do jogo, seletor visual de modo de dificuldade,
 * orientações de comandos e o recorde atual da sessão.</p>
 */
public class MenuScreen implements Screen {

    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 42);
    private static final Font SUBTITLE_FONT = new Font("SansSerif", Font.BOLD, 17);
    private static final Font SELECTOR_FONT = new Font("SansSerif", Font.BOLD, 15);
    private static final Font INSTRUCTION_FONT = new Font("SansSerif", Font.PLAIN, 13);
    private static final Font FOOTER_FONT = new Font("SansSerif", Font.ITALIC, 12);

    private static final Color TITLE_COLOR = new Color(255, 215, 0);     // Dourado
    private static final Color OUTLINE_COLOR = new Color(30, 30, 30);     // Preto carvão
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color ACCENT_COLOR = new Color(255, 230, 100);
    private static final Color HARD_COLOR = new Color(255, 110, 80);      // Coral/vermelho vibrante
    private static final Color INACTIVE_TEXT = new Color(200, 210, 215);
    private static final Color BOX_ACTIVE_BG = new Color(30, 45, 55, 200);
    private static final Color BOX_INACTIVE_BG = new Color(20, 30, 35, 110);

    @Override
    public void draw(Graphics2D g, Game game) {
        // 1. Título estilizado
        DrawUtils.drawCenteredOutlinedText(g, "FLAPPY BIRD", 150, TITLE_FONT, TITLE_COLOR, OUTLINE_COLOR);

        // 2. Seletor visual de Dificuldade (MÉDIO / DIFÍCIL)
        drawDifficultySelector(g, game.getDifficulty());

        // 3. Subtítulo de ação principal
        DrawUtils.drawCenteredOutlinedText(g, "Pressione ESPAÇO ou Clique", 385, SUBTITLE_FONT, TEXT_COLOR, OUTLINE_COLOR);
        DrawUtils.drawCenteredOutlinedText(g, "para Iniciar", 408, SUBTITLE_FONT, TEXT_COLOR, OUTLINE_COLOR);

        // 4. Exibição do recorde da sessão (se houver)
        if (game.getBestScore() > 0) {
            String bestText = "Melhor Placar: " + game.getBestScore();
            DrawUtils.drawCenteredOutlinedText(g, bestText, 438, INSTRUCTION_FONT, ACCENT_COLOR, OUTLINE_COLOR);
        }

        // 5. Instruções de controle
        DrawUtils.drawCenteredOutlinedText(g, "[←/→] ou [1/2] Dificuldade • [ESPAÇO] Jogar", 468, INSTRUCTION_FONT, TEXT_COLOR, OUTLINE_COLOR);
        DrawUtils.drawCenteredOutlinedText(g, "[M] Ligar / Silenciar Música", 490, INSTRUCTION_FONT, TEXT_COLOR, OUTLINE_COLOR);

        // 6. Rodapé acadêmico
        DrawUtils.drawCenteredOutlinedText(g, "Análise e Desenvolvimento de Sistemas • Java 2D POO", 555, FOOTER_FONT, new Color(240, 240, 240), OUTLINE_COLOR);
    }

    private void drawDifficultySelector(Graphics2D g, Difficulty current) {
        boolean isMedium = (current == Difficulty.MEDIUM);
        Color activeHighlight = isMedium ? ACCENT_COLOR : HARD_COLOR;

        String headerText = "MODO: < " + current.getLabel() + " >";
        DrawUtils.drawCenteredOutlinedText(g, headerText, 298, SELECTOR_FONT, activeHighlight, OUTLINE_COLOR);

        int boxW = 120;
        int boxH = 34;
        int gap = 16;
        int totalW = (boxW * 2) + gap;
        int startX = (GameConfig.WINDOW_WIDTH - totalW) / 2;
        int boxY = 312;

        drawOptionBox(g, startX, boxY, boxW, boxH, "[1] MÉDIO", isMedium, ACCENT_COLOR);
        drawOptionBox(g, startX + boxW + gap, boxY, boxW, boxH, "[2] DIFÍCIL", !isMedium, HARD_COLOR);
    }

    private void drawOptionBox(Graphics2D g, int x, int y, int w, int h,
                               String label, boolean selected, Color highlightColor) {
        g.setColor(selected ? BOX_ACTIVE_BG : BOX_INACTIVE_BG);
        g.fillRoundRect(x, y, w, h, 12, 12);

        g.setColor(selected ? highlightColor : new Color(90, 110, 120, 160));
        g.drawRoundRect(x, y, w, h, 12, 12);
        if (selected) {
            g.drawRoundRect(x + 1, y + 1, w - 2, h - 2, 10, 10);
        }

        g.setFont(SELECTOR_FONT);
        FontMetrics fm = g.getFontMetrics(SELECTOR_FONT);
        int textX = x + (w - fm.stringWidth(label)) / 2;
        int textY = y + ((h - fm.getHeight()) / 2) + fm.getAscent();

        DrawUtils.drawOutlinedText(g, label, textX, textY,
                selected ? highlightColor : INACTIVE_TEXT, OUTLINE_COLOR);
    }
}
