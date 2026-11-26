package flappybird.game;

import flappybird.entities.PipePair;
import flappybird.utils.GameConfig;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Gerencia o ciclo de vida, geração periódica e descarte dos obstáculos do jogo.
 *
 * <p><strong>Boas Práticas e POO:</strong>
 * <ul>
 *   <li><strong>Injeção de Dependência:</strong> Recebe uma instância de {@link Random} no construtor,
 *   permitindo testes unitários determinísticos com sementes fixas.</li>
 *   <li><strong>Encapsulamento defensivo:</strong> O método {@link #getPipePairs()} retorna uma
 *   lista não modificável através de {@link Collections#unmodifiableList(List)}, impedindo adulterações
 *   externas na coleção interna.</li>
 *   <li><strong>Gerenciamento de recursos:</strong> Remove automaticamente canos fora da tela para
 *   evitar vazamento de memória.</li>
 * </ul>
 * </p>
 */
public class PipeManager {

    private final List<PipePair> pipePairs;
    private final Random random;
    private Difficulty difficulty;
    private int spawnTimer;

    private static final int MIN_PIPE_HEIGHT = 60;

    public PipeManager() {
        this(new Random(), Difficulty.MEDIUM);
    }

    public PipeManager(Random random) {
        this(random, Difficulty.MEDIUM);
    }

    public PipeManager(Random random, Difficulty difficulty) {
        if (random == null) {
            throw new IllegalArgumentException("O gerador aleatório não pode ser nulo.");
        }
        if (difficulty == null) {
            throw new IllegalArgumentException("A dificuldade não pode ser nula.");
        }
        this.random = random;
        this.difficulty = difficulty;
        this.pipePairs = new ArrayList<>();
        this.spawnTimer = 0;
    }

    /**
     * Atualiza a movimentação dos obstáculos, gera novos pares e descarta os que saíram da tela.
     */
    public void update() {
        // Geração periódica de novos pares de canos conforme o intervalo da dificuldade ativa
        if (spawnTimer <= 0) {
            spawnPipePair();
            spawnTimer = difficulty.getPipeSpawnInterval();
        } else {
            spawnTimer--;
        }

        // Atualiza a posição de cada par
        for (PipePair pair : pipePairs) {
            pair.update();
        }

        // Remove da lista os canos que saíram da tela (evita vazamento de memória)
        pipePairs.removeIf(PipePair::isOffScreen);
    }

    private void spawnPipePair() {
        int groundY = GameConfig.WINDOW_HEIGHT - GameConfig.GROUND_HEIGHT;
        int gap = difficulty.getPipeGap();
        int maxTopHeight = groundY - gap - MIN_PIPE_HEIGHT;
        int availableRange = maxTopHeight - MIN_PIPE_HEIGHT;

        int topHeight = MIN_PIPE_HEIGHT + (availableRange > 0 ? random.nextInt(availableRange) : 0);

        PipePair pair = new PipePair(GameConfig.WINDOW_WIDTH, topHeight, gap, difficulty.getPipeSpeed());
        pipePairs.add(pair);
    }

    /**
     * Renderiza todos os obstáculos ativos na tela.
     *
     * @param g contexto gráfico Java2D.
     */
    public void draw(Graphics2D g) {
        for (PipePair pair : pipePairs) {
            pair.draw(g);
        }
    }

    /**
     * Remove todos os obstáculos da partida e reinicia o contador de geração.
     */
    public void reset() {
        pipePairs.clear();
        spawnTimer = 0;
    }

    /**
     * Retorna a lista não modificável dos pares de canos ativos.
     *
     * @return lista imutável de {@link PipePair}.
     */
    public List<PipePair> getPipePairs() {
        return Collections.unmodifiableList(pipePairs);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        if (difficulty == null) {
            throw new IllegalArgumentException("A dificuldade não pode ser nula.");
        }
        this.difficulty = difficulty;
    }
}
