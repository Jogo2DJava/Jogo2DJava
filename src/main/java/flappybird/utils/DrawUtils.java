package flappybird.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 * Utilitários auxiliares para renderização gráfica de textos com efeitos visuais.
 */
public final class DrawUtils {

    private DrawUtils() {
        // Construtor privado para classe utilitária.
    }

    /**
     * Desenha uma string centralizada horizontalmente na largura total do jogo,
     * com efeito de contorno/sombra para máximo contraste e legibilidade.
     *
     * @param g contexto gráfico.
     * @param text texto a ser desenhado.
     * @param y coordenada vertical da linha de base do texto.
     * @param font fonte a ser utilizada.
     * @param textColor cor principal do texto.
     * @param outlineColor cor do contorno/sombra.
     */
    public static void drawCenteredOutlinedText(Graphics2D g, String text, int y, Font font,
                                               Color textColor, Color outlineColor) {
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics(font);
        int x = (GameConfig.WINDOW_WIDTH - fm.stringWidth(text)) / 2;

        drawOutlinedText(g, text, x, y, textColor, outlineColor);
    }

    /**
     * Desenha um texto com efeito de contorno (4 direções) e cor de preenchimento.
     *
     * @param g contexto gráfico.
     * @param text texto a desenhar.
     * @param x posição horizontal.
     * @param y posição vertical.
     * @param textColor cor do texto.
     * @param outlineColor cor do contorno.
     */
    public static void drawOutlinedText(Graphics2D g, String text, int x, int y,
                                        Color textColor, Color outlineColor) {
        // Desenha o contorno deslocando 2 pixels em cruz
        g.setColor(outlineColor);
        g.drawString(text, x - 2, y);
        g.drawString(text, x + 2, y);
        g.drawString(text, x, y - 2);
        g.drawString(text, x, y + 2);
        g.drawString(text, x - 2, y - 2);
        g.drawString(text, x + 2, y + 2);
        g.drawString(text, x - 2, y + 2);
        g.drawString(text, x + 2, y - 2);

        // Desenha o texto principal
        g.setColor(textColor);
        g.drawString(text, x, y);
    }
}
