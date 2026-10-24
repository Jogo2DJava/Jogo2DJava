package flappybird.game;

/**
 * Representa a lógica central e o estado do jogo.
 *
 * <p><strong>Pilar da POO — Encapsulamento:</strong>
 * O estado da partida (máquina de estados), pontuações e objetos do mundo
 * são mantidos privados. O controle de fluxo ocorre exclusivamente por meio
 * de métodos públicos controlados como {@link #start()}, {@link #restart()} e {@link #update()}.</p>
 */
public class Game {

    private GameState state;
    private int score;
    private int bestScore;

    public Game() {
        this.state = GameState.MENU;
        this.score = 0;
        this.bestScore = 0;
    }

    /**
     * Atualiza a lógica da partida a cada passo fixo (60 vezes por segundo).
     */
    public void update() {
        if (state == GameState.PLAYING) {
            // A atualização física das entidades será conectada nas próximas issues.
        }
    }

    /**
     * Inicia a partida a partir do menu.
     */
    public void start() {
        if (state == GameState.MENU) {
            this.state = GameState.PLAYING;
        }
    }

    /**
     * Reinicia a partida após o Game Over.
     */
    public void restart() {
        this.score = 0;
        this.state = GameState.PLAYING;
    }

    /**
     * Retorna ao menu inicial.
     */
    public void backToMenu() {
        this.score = 0;
        this.state = GameState.MENU;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public int getScore() {
        return score;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setScore(int score) {
        this.score = score;
        if (score > this.bestScore) {
            this.bestScore = score;
        }
    }
}
