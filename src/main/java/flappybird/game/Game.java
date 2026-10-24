package flappybird.game;

import flappybird.entities.Bird;
import flappybird.entities.Ground;
import flappybird.entities.PipePair;
import flappybird.utils.GameConfig;

/**
 * Representa a lógica central e o estado do jogo.
 *
 * <p><strong>Pilar da POO — Encapsulamento:</strong>
 * O estado da partida, as entidades ({@link Bird}, {@link Ground}, {@link PipeManager})
 * e os subsistemas ({@link CollisionDetector}, {@link ScoreManager}) são mantidos privados.
 * O controle de fluxo ocorre exclusivamente por meio de métodos públicos controlados.</p>
 */
public class Game {

    private GameState state;

    private final Bird bird;
    private final Ground ground;
    private final PipeManager pipeManager;
    private final CollisionDetector collisionDetector;
    private final ScoreManager scoreManager;

    private int menuTick;
    private int gameOverCooldown;

    public Game() {
        this(new PipeManager(), new CollisionDetector(), new ScoreManager());
    }

    public Game(PipeManager pipeManager) {
        this(pipeManager, new CollisionDetector(), new ScoreManager());
    }

    public Game(PipeManager pipeManager, CollisionDetector collisionDetector) {
        this(pipeManager, collisionDetector, new ScoreManager());
    }

    public Game(PipeManager pipeManager, CollisionDetector collisionDetector, ScoreManager scoreManager) {
        if (pipeManager == null) {
            throw new IllegalArgumentException("PipeManager não pode ser nulo.");
        }
        if (collisionDetector == null) {
            throw new IllegalArgumentException("CollisionDetector não pode ser nulo.");
        }
        if (scoreManager == null) {
            throw new IllegalArgumentException("ScoreManager não pode ser nulo.");
        }
        this.state = GameState.MENU;
        this.bird = new Bird();
        this.ground = new Ground();
        this.pipeManager = pipeManager;
        this.collisionDetector = collisionDetector;
        this.scoreManager = scoreManager;
        this.menuTick = 0;
        this.gameOverCooldown = 0;
    }

    /**
     * Atualiza a lógica da partida a cada passo fixo (60 vezes por segundo).
     */
    public void update() {
        // O chão rola continuamente no menu e na partida
        if (state != GameState.GAME_OVER) {
            ground.update();
        }

        if (state == GameState.MENU) {
            // No menu, o pássaro realiza flutuação suave
            bird.reset();
            menuTick++;
        } else if (state == GameState.PLAYING) {
            // Atualiza física do pássaro e deslocamento dos obstáculos
            bird.update();
            pipeManager.update();

            // Verifica se o pássaro ultrapassou novos obstáculos para pontuar
            for (PipePair pair : pipeManager.getPipePairs()) {
                if (!pair.isPassed() && bird.getX() > (pair.getX() + pair.getTopPipe().getWidth())) {
                    pair.setPassed(true);
                    scoreManager.increment();
                }
            }

            // Verifica colisão imediatamente após as atualizações de posição
            if (collisionDetector.checkCollision(bird, ground, pipeManager.getPipePairs())) {
                triggerGameOver();
            }
        } else if (state == GameState.GAME_OVER) {
            // Decrementa o tempo de espera de proteção antes de aceitar reinício
            if (gameOverCooldown > 0) {
                gameOverCooldown--;
            }
        }
    }

    private void triggerGameOver() {
        this.state = GameState.GAME_OVER;
        this.gameOverCooldown = 30; // 30 ticks = 0.5s de proteção contra cliques acidentais
    }

    /**
     * Inicia a partida a partir do menu, aplicando um primeiro impulso ao pássaro.
     */
    public void start() {
        if (state == GameState.MENU) {
            this.state = GameState.PLAYING;
            scoreManager.reset();
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
     * Reinicia a partida após o Game Over (após decorrido o tempo de proteção).
     */
    public void restart() {
        if (state == GameState.GAME_OVER && gameOverCooldown > 0) {
            // Ignora toques acidentais imediatamente após a colisão
            return;
        }
        this.state = GameState.PLAYING;
        scoreManager.reset();
        pipeManager.reset();
        bird.reset();
        bird.flap();
    }

    /**
     * Retorna ao menu inicial.
     */
    public void backToMenu() {
        this.state = GameState.MENU;
        scoreManager.reset();
        pipeManager.reset();
        bird.reset();
        this.menuTick = 0;
        this.gameOverCooldown = 0;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public int getScore() {
        return scoreManager.getScore();
    }

    public int getBestScore() {
        return scoreManager.getBestScore();
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

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public int getGameOverCooldown() {
        return gameOverCooldown;
    }

    public boolean canRestart() {
        return state == GameState.GAME_OVER && gameOverCooldown <= 0;
    }
}
