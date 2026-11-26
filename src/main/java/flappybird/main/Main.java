package flappybird.main;

import flappybird.ui.GameWindow;

import javax.swing.SwingUtilities;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CountDownLatch;

/**
 * Ponto de entrada da aplicação.
 */
public final class Main {

    private Main() {
        // Classe usada apenas pelo método main.
    }

    public static void main(String[] args) {
        CountDownLatch windowClosedLatch = new CountDownLatch(1);

        try {
            // O Swing exige que a interface seja criada e alterada na sua própria
            // thread (Event Dispatch Thread). O invokeAndWait garante que a janela
            // esteja totalmente criada e visível antes do método main prosseguir.
            SwingUtilities.invokeAndWait(() -> {
                GameWindow window = new GameWindow();
                window.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        windowClosedLatch.countDown();
                    }

                    @Override
                    public void windowClosed(WindowEvent e) {
                        windowClosedLatch.countDown();
                    }
                });
                window.setVisible(true);
                window.getGamePanel().requestFocusInWindow();
            });

            // Mantém a thread principal viva enquanto a janela estiver aberta
            // (necessário para execução via mvnw exec:java não encerrar a JVM prematuramente).
            windowClosedLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            System.err.println("Erro ao iniciar a janela do jogo: " + cause.getMessage());
        }
    }
}
