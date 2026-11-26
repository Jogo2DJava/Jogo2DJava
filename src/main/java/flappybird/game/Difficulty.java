package flappybird.game;

import flappybird.utils.GameConfig;

/**
 * Define os modos de dificuldade disponíveis no jogo e encapsula seus parâmetros físicos.
 *
 * <p><strong>Pilar da POO — Encapsulamento e Abstração:</strong>
 * Cada constante do enum encapsula de forma imutável os parâmetros de velocidade horizontal
 * dos obstáculos, vão vertical livre, intervalo de geração e gravidade, expondo apenas
 * métodos de consulta e alternância segura.</p>
 */
public enum Difficulty {

    /**
     * Modo clássico equilibrado (padrão inicial do jogo).
     */
    MEDIUM(
            "MÉDIO",
            GameConfig.PIPE_SPEED,
            GameConfig.PIPE_GAP,
            GameConfig.PIPE_SPAWN_INTERVAL,
            GameConfig.GRAVITY
    ),

    /**
     * Modo desafiador com canos mais rápidos, menor abertura vertical, menor espaçamento e gravidade mais intensa.
     */
    HARD(
            "DIFÍCIL",
            GameConfig.HARD_PIPE_SPEED,
            GameConfig.HARD_PIPE_GAP,
            GameConfig.HARD_PIPE_SPAWN_INTERVAL,
            GameConfig.HARD_GRAVITY
    );

    private final String label;
    private final double pipeSpeed;
    private final int pipeGap;
    private final int pipeSpawnInterval;
    private final double gravity;

    Difficulty(String label, double pipeSpeed, int pipeGap, int pipeSpawnInterval, double gravity) {
        this.label = label;
        this.pipeSpeed = pipeSpeed;
        this.pipeGap = pipeGap;
        this.pipeSpawnInterval = pipeSpawnInterval;
        this.gravity = gravity;
    }

    /**
     * Alterna ciclicamente para o próximo nível de dificuldade.
     *
     * @return {@link #HARD} se atual for {@link #MEDIUM}; {@link #MEDIUM} caso contrário.
     */
    public Difficulty toggle() {
        return this == MEDIUM ? HARD : MEDIUM;
    }

    /**
     * Atalho semântico equivalente a {@link #toggle()} para avançar ao próximo modo.
     *
     * @return próxima dificuldade disponível.
     */
    public Difficulty next() {
        return toggle();
    }

    public String getLabel() {
        return label;
    }

    public double getPipeSpeed() {
        return pipeSpeed;
    }

    public int getPipeGap() {
        return pipeGap;
    }

    public int getPipeSpawnInterval() {
        return pipeSpawnInterval;
    }

    public double getGravity() {
        return gravity;
    }
}
