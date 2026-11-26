package flappybird.utils;

/**
 * Reúne as constantes globais de configuração e física do jogo em um único lugar.
 *
 * <p><strong>Pilar da POO — Encapsulamento:</strong>
 * A classe é {@code final} com construtor privado, servindo exclusivamente como repositório
 * central imutável de parâmetros para calibração de jogabilidade.</p>
 */
public final class GameConfig {

    /** Título exibido na barra da janela. */
    public static final String TITLE = "Flappy Bird Java";

    /** Largura da área de jogo, em pixels. */
    public static final int WINDOW_WIDTH = 400;

    /** Altura da área de jogo, em pixels. */
    public static final int WINDOW_HEIGHT = 600;

    /** Altura do chão em pixels na base da tela. */
    public static final int GROUND_HEIGHT = 80;

    // --- Parâmetros do Pássaro ---
    public static final int BIRD_WIDTH = 34;
    public static final int BIRD_HEIGHT = 24;
    public static final double BIRD_START_X = 80.0;
    public static final double BIRD_START_Y = 250.0;

    // --- Física e Movimento (em pixels por tick lógico de 1/60s) ---
    public static final double GRAVITY = 0.45;
    public static final double JUMP_FORCE = -8.0;
    public static final double MAX_FALL_SPEED = 10.0;

    // --- Parâmetros dos Obstáculos (Modo Médio / Padrão) ---
    public static final int PIPE_WIDTH = 70;
    public static final int PIPE_GAP = 150;
    public static final double PIPE_SPEED = 2.5;
    public static final int PIPE_SPAWN_INTERVAL = 90; // a cada 1.5 segundo (90 ticks)

    // --- Parâmetros do Modo Difícil (HARD) ---
    public static final double HARD_GRAVITY = 0.52;
    public static final int HARD_PIPE_GAP = 120;
    public static final double HARD_PIPE_SPEED = 3.6;
    public static final int HARD_PIPE_SPAWN_INTERVAL = 68;

    private GameConfig() {
        // Impede a instanciação externa.
    }
}
