package flappybird.game;

import flappybird.entities.Bird;
import flappybird.entities.Ground;
import flappybird.utils.GameConfig;

/**
 * Representa a lógica central e o estado do jogo.
 *
 * <p><strong>Pilar da POO — Encapsulamento:</strong>
 * O estado da partida, as entidades ({@link Bird}, {@link Ground}, {@link PipeManager})
 * e os subsistemas ({@link CollisionDetector}) são mantidos privados.
 * O controle de fluxo ocorre exclusivamente por meio de métodos públicos controlados.</p>
 */
public class Game {

    private GameState state;
    private int score;
    private int bestScore;

    private final Bird bird;
    private final Ground ground;
    private final PipeManager pipeManager;
    private final CollisionDetector collisionDetector;

    private int menuTick;

    public Game() {
        this(new PipeManager(), new CollisionDetector());
    }

    public Game(PipeManager pipeManager) {
        this(pipeManager, new CollisionDetector());
    }

    public Game(PipeManager pipeManager, CollisionDetector collisionDetector) {
        if (pipeManager == null) {
            throw new IllegalArgumentException("PipeManager não pode ser nulo.");
        }
        if (collisionDetector == null) {
            throw new IllegalArgumentException("CollisionDetector não pode ser nulo.");
        }
        this.state = GameState.MENU;
        this.score = 0;
        this.bestScore = 0;
        this.bird = new Bird();
        this.ground = new Ground();
        this.pipeManager = pipeManager;
        this.collisionDetector = collisionDetector;
        this.menuTick = 0;
    }

    /**
     * Atualiza a lógica da partida a cada passo fixo (60 vezes por segundo).
     */
    public void update() {
        // O chão rola continuamente tanto no menu quanto na partida
        ground.update();

        if (state == GameState.MENU) {
            // No menu, o pássaro realiza flutuação suave
            bird.reset();
            menuTick++;
        } else if (state == GameState.PLAYING) {
            // Atualiza física do pássaro e deslocamento dos obstáculos
            bird.update();
            pipeManager.update();

            // Verifica colisão imediatamente após as atualizações de posição
            if (collisionDetector.checkCollision(bird, ground, pipeManager.getPipePairs())) {
                this.state = GameState.GAME_OVER;
            }
        }
    }

    /**
     * Inicia a partida a partir do menu, aplicando um primeiro impulso ao pássaro.
     */
    public void start() {
        if (state == GameState.MENU) {
            this.state = GameState.PLAYING;
            pipeManager.reset();
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
        pipeManager.reset();
        bird.reset();
        bird.flap();
    }

    /**
     * Retorna ao menu inicial.
     */
    public void backToMenu() {
        this.score = 0;
        this.state = GameState.MENU;
        pipeManager.reset();
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

    public Ground getGround() {
        return ground;
    }

    public PipeManager getPipeManager() {
        return pipeManager;
    }

    public CollisionDetector getCollisionDetector() {
        return collisionDetector;
    }
}
