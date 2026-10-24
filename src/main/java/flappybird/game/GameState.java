package flappybird.game;

/**
 * Representa os possíveis estados do jogo durante o ciclo de vida da aplicação.
 *
 * <p>O enum encapsula os estados da máquina de estados finita do jogo:
 * <ul>
 *   <li>{@link #MENU}: Tela inicial de boas-vindas com título e instruções.</li>
 *   <li>{@link #PLAYING}: Partida ativa, onde a física e as entradas do jogador são processadas.</li>
 *   <li>{@link #GAME_OVER}: Partida encerrada por colisão, aguardando reinício ou retorno ao menu.</li>
 * </ul>
 * </p>
 */
public enum GameState {
    /** Menu inicial com título e aguardando comando do jogador para iniciar. */
    MENU,

    /** Partida em andamento: pássaro, obstáculos e pontuação ativos. */
    PLAYING,

    /** Fim de jogo: colisão detectada, pontuação congelada e exibição do placar final. */
    GAME_OVER
}
