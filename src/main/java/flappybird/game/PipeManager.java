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
    private int spawnTimer;

    private static final int MIN_PIPE_HEIGHT = 60;

    public PipeManager() {
        this(new Random());
    }

    public PipeManager(Random random) {
        if (random == null) {
            throw new IllegalArgumentException("O gerador aleatório não pode ser nulo.");
        }
        this.random = random;
        this.pipePairs = new ArrayList<>();
        this.spawnTimer = 0;
    }

    /**
     * Atualiza a movimentação dos obstáculos, gera novos pares e descarta os que saíram da tela.
     */
    public void update() {
        // Geração periódica de novos pares de canos
        if (spawnTimer <= 0) {
            spawnPipePair();
            spawnTimer = GameConfig.PIPE_SPAWN_INTERVAL;
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
        int maxTopHeight = groundY - GameConfig.PIPE_GAP - MIN_PIPE_HEIGHT;
        int availableRange = maxTopHeight - MIN_PIPE_HEIGHT;

        int topHeight = MIN_PIPE_HEIGHT + (availableRange > 0 ? random.nextInt(availableRange) : 0);

        PipePair pair = new PipePair(GameConfig.WINDOW_WIDTH, topHeight, GameConfig.PIPE_GAP);
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
}
