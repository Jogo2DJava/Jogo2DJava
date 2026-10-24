package flappybird.utils;

/**
 * Reúne as constantes de configuração do jogo em um único lugar.
 *
 * <p>A classe é {@code final} (não pode ser herdada) e tem construtor
 * privado (não pode ser instanciada), pois serve apenas para guardar
 * valores fixos. Novas constantes (gravidade, velocidade dos canos etc.)
 * serão adicionadas conforme cada funcionalidade for implementada.</p>
 */
public final class GameConfig {

    /** Título exibido na barra da janela. */
    public static final String TITLE = "Flappy Bird Java";

    /** Largura da área de jogo, em pixels. */
    public static final int WINDOW_WIDTH = 400;

    /** Altura da área de jogo, em pixels. */
    public static final int WINDOW_HEIGHT = 600;

    private GameConfig() {
        // Impede a criação de objetos desta classe.
    }
}
