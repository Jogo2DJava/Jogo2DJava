package flappybird.ui;

import flappybird.game.Game;
import flappybird.utils.DrawUtils;
import flappybird.utils.GameConfig;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 * Tela exibida após a derrota do jogador (Game Over).
 *
 * <p><strong>Pilar da POO — Polimorfismo:</strong>
 * Implementa {@link Screen}, desenhando o painel estilizado de resultados, pontuação final,
 * melhor recorde e instruções para reinício imediato ou retorno ao menu.</p>
 */
public class GameOverScreen implements Screen {

    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 42);
    private static final Font CARD_TITLE_FONT = new Font("SansSerif", Font.BOLD, 18);
    private static final Font CARD_VALUE_FONT = new Font("SansSerif", Font.BOLD, 22);
    private static final Font INSTRUCTION_FONT = new Font("SansSerif", Font.BOLD, 15);
    private static final Font ESC_FONT = new Font("SansSerif", Font.PLAIN, 13);

    private static final Color TITLE_COLOR = new Color(235, 60, 45); // Vermelho vibrante
    private static final Color OUTLINE_COLOR = new Color(30, 20, 10);
    private static final Color CARD_BG = new Color(245, 230, 175);
    private static final Color CARD_BORDER = new Color(85, 65, 30);
    private static final Color TEXT_DARK = new Color(60, 45, 20);
    private static final Color SCORE_HIGHLIGHT = new Color(230, 90, 20);
    private static final Color BEST_HIGHLIGHT = new Color(40, 140, 40);

    @Override
    public void draw(Graphics2D g, Game game) {
        // 1. Título "GAME OVER"
        DrawUtils.drawCenteredOutlinedText(g, "GAME OVER", 140, TITLE_FONT, TITLE_COLOR, OUTLINE_COLOR);

        // 2. Cartão de pontuação centralizado
        int cardW = 280;
        int cardH = 150;
        int cardX = (GameConfig.WINDOW_WIDTH - cardW) / 2;
        int cardY = 180;

        // Sombra do cartão
        g.setColor(new Color(0, 0, 0, 60));
        g.fillRoundRect(cardX + 4, cardY + 4, cardW, cardH, 16, 16);

        // Fundo do cartão
        g.setColor(CARD_BG);
        g.fillRoundRect(cardX, cardY, cardW, cardH, 16, 16);

        // Borda grossa do cartão
        g.setColor(CARD_BORDER);
        g.drawRoundRect(cardX, cardY, cardW, cardH, 16, 16);
        g.drawRoundRect(cardX + 1, cardY + 1, cardW - 2, cardH - 2, 16, 16);

        // Textos dentro do cartão
        g.setFont(CARD_TITLE_FONT);
        g.setColor(TEXT_DARK);
        g.drawString("PONTUAÇÃO", cardX + 30, cardY + 45);
        g.drawString("RECORDE", cardX + 30, cardY + 105);

        // Valores numéricos alinhados à direita do cartão
        g.setFont(CARD_VALUE_FONT);
        g.setColor(SCORE_HIGHLIGHT);
        String scoreStr = String.valueOf(game.getScore());
        g.drawString(scoreStr, cardX + cardW - 30 - g.getFontMetrics().stringWidth(scoreStr), cardY + 45);

        g.setColor(BEST_HIGHLIGHT);
        String bestStr = String.valueOf(game.getBestScore());
        g.drawString(bestStr, cardX + cardW - 30 - g.getFontMetrics().stringWidth(bestStr), cardY + 105);

        // Medalha / Reconhecimento com base na pontuação obtida
        drawMedal(g, cardX + 30, cardY + 60, game.getScore());

        // 3. Instruções para reiniciar ou voltar ao menu
        DrawUtils.drawCenteredOutlinedText(g, "Pressione ESPAÇO para Jogar Novamente", 380,
                INSTRUCTION_FONT, Color.WHITE, OUTLINE_COLOR);

        DrawUtils.drawCenteredOutlinedText(g, "[ESC] Voltar ao Menu Principal", 415,
                ESC_FONT, new Color(240, 240, 240), OUTLINE_COLOR);
    }

    private void drawMedal(Graphics2D g, int x, int y, int score) {
        if (score < 5) return;

        Color medalColor;
        String medalLabel;

        if (score >= 30) {
            medalColor = new Color(255, 215, 0); // Ouro
            medalLabel = "★ OURO";
        } else if (score >= 15) {
            medalColor = new Color(200, 205, 215); // Prata
            medalLabel = "★ PRATA";
        } else {
            medalColor = new Color(205, 127, 50); // Bronze
            medalLabel = "★ BRONZE";
        }

        g.setColor(medalColor);
        g.setFont(ESC_FONT);
        g.drawString(medalLabel, x, y + 10);
    }
}
