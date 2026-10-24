package flappybird.game;

/**
 * Gerencia a pontuação da partida atual e o recorde da sessão de jogo.
 *
 * <p><strong>Pilar da POO — Encapsulamento:</strong>
 * Protege os dados de pontuação contra manipulação externa indevida. A pontuação
 * só pode ser incrementada de forma unitária através do método {@link #increment()},
 * garantindo a integridade dos dados e a sincronização automática do recorde ({@link #bestScore}).</p>
 */
public class ScoreManager {

    private int score;
    private int bestScore;

    public ScoreManager() {
        this.score = 0;
        this.bestScore = 0;
    }

    /**
     * Incrementa a pontuação em 1 ponto e atualiza o recorde se a nova marca for superior.
     */
    public void increment() {
        this.score++;
        if (this.score > this.bestScore) {
            this.bestScore = this.score;
        }
    }

    /**
     * Zera a pontuação da partida atual, preservando o melhor placar da sessão.
     */
    public void reset() {
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public int getBestScore() {
        return bestScore;
    }
}
