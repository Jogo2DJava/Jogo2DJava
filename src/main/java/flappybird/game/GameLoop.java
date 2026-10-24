package flappybird.game;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Responsável pelo ciclo principal de execução do jogo (Game Loop) com passo de tempo fixo
 * (fixed timestep).
 *
 * <p><strong>Funcionamento técnico:</strong>
 * O loop utiliza um {@link javax.swing.Timer} executado na thread de despacho de eventos do Swing (EDT),
 * eliminando problemas de concorrência entre atualização de dados e renderização na tela.
 * A física é sempre processada em fatias exatas de 1/60 de segundo, independentemente de oscilações
 * no tempo de renderização do computador.</p>
 */
public class GameLoop implements ActionListener {

    /** Frequência alvo da lógica física: 60 atualizações por segundo. */
    public static final int TARGET_TICKS_PER_SECOND = 60;

    /** Duração em nanossegundos de um único passo lógico (~16.666.666 ns). */
    private static final long TIME_STEP_NS = 1_000_000_000L / TARGET_TICKS_PER_SECOND;

    /** Intervalo de disparo do timer do Swing em milissegundos (~60 FPS). */
    private static final int TIMER_INTERVAL_MS = 16;

    /** Limite de atualizações lógicas consecutivas por quadro para evitar a "espiral da morte". */
    private static final int MAX_UPDATES_PER_FRAME = 5;

    private final Game game;
    private final Runnable renderCallback;
    private final Timer timer;

    private long lastTimeNs;
    private long accumulatorNs;
    private boolean running;

    /**
     * Inicializa o loop de jogo desacoplado do painel visual.
     *
     * @param game instância do jogo a ser atualizada.
     * @param renderCallback ação de repintura (ex.: {@code panel::repaint}).
     */
    public GameLoop(Game game, Runnable renderCallback) {
        if (game == null) {
            throw new IllegalArgumentException("O jogo não pode ser nulo.");
        }
        if (renderCallback == null) {
            throw new IllegalArgumentException("O callback de renderização não pode ser nulo.");
        }

        this.game = game;
        this.renderCallback = renderCallback;
        this.timer = new Timer(TIMER_INTERVAL_MS, this);
        this.running = false;
        this.accumulatorNs = 0L;
    }

    /**
     * Inicia o ciclo do jogo.
     */
    public void start() {
        if (!running) {
            this.lastTimeNs = System.nanoTime();
            this.accumulatorNs = 0L;
            this.running = true;
            this.timer.start();
        }
    }

    /**
     * Interrompe o ciclo do jogo.
     */
    public void stop() {
        if (running) {
            this.running = false;
            this.timer.stop();
        }
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!running) {
            return;
        }

        long now = System.nanoTime();
        long elapsed = now - lastTimeNs;
        lastTimeNs = now;

        // Limita o tempo transcorrido para evitar saltos gigantescos caso o sistema congele
        if (elapsed > 200_000_000L) { // 200 ms
            elapsed = 200_000_000L;
        }

        accumulatorNs += elapsed;

        int updates = 0;
        while (accumulatorNs >= TIME_STEP_NS && updates < MAX_UPDATES_PER_FRAME) {
            game.update();
            accumulatorNs -= TIME_STEP_NS;
            updates++;
        }

        // Solicita a repintura na tela após as atualizações físicas
        renderCallback.run();
    }
}
