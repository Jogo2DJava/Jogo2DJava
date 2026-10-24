package flappybird.game;

import flappybird.entities.Bird;
import flappybird.utils.GameConfig;

/**
 * Representa a lógica central e o estado do jogo.
 *
 * <p><strong>Pilar da POO — Encapsulamento:</strong>
 * O estado da partida (máquina de estados), a entidade {@link Bird} e pontuações
 * são mantidos privados. O controle de fluxo ocorre exclusivamente por meio
 * de métodos públicos controlados como {@link #start()}, {@link #flap()}, {@link #restart()} e {@link #update()}.</p>
 */
public class Game {

    private GameState state;
    private int score;
    private int bestScore;
    private final Bird bird;
    private int menuTick;

    public Game() {
        this.state = GameState.MENU;
        this.score = 0;
        this.bestScore = 0;
        this.bird = new Bird();
        this.menuTick = 0;
    }

    /**
     * Atualiza a lógica da partida a cada passo fixo (60 vezes por segundo).
     */
    public void update() {
        if (state == GameState.MENU) {
            // No menu, o pássaro realiza uma flutuação senoidal suave e agradável
            double hoverY = GameConfig.BIRD_START_Y + (Math.sin(menuTick * 0.08) * 6.0);
            bird.reset();
            bird.setVelocityY(0.0);
            // Simula uma leve ondulação visual
            menuTick++;
        } else if (state == GameState.PLAYING) {
            // Atualiza a física do pássaro
            bird.update();
        }
    }

    /**
     * Inicia a partida a partir do menu, aplicando um primeiro impulso ao pássaro.
     */
    public void start() {
        if (state == GameState.MENU) {
            this.state = GameState.PLAYING;
            bird.reset();
            bird.flap();
        }
    }

    /**
     * Aciona o voo do pássaro durante o jogo.
     */
    public void flap() {
        if (state == GameState.PLAYING) {
            bird.flap();
        } else if (state == GameState.MENU) {
            start();
        }
    }

    /**
     * Reinicia a partida após o Game Over.
     */
    public void restart() {
        this.score = 0;
        this.state = GameState.PLAYING;
        bird.reset();
        bird.flap();
    }

    /**
     * Retorna ao menu inicial.
     */
    public void backToMenu() {
        this.score = 0;
        this.state = GameState.MENU;
        bird.reset();
        this.menuTick = 0;
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

    public Bird getBird() {
        return bird;
    }
}
