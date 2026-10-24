package flappybird.entities;

import flappybird.utils.GameConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes unitários para a entidade Pipe (deslocamento e detecção de saída da tela).
 */
class PipeTest {

    @Test
    @DisplayName("Deve deslocar o cano para a esquerda de acordo com a velocidade do jogo")
    void deveMoverParaEsquerdaNaVelocidadeConfigurada() {
        Pipe pipe = new Pipe(200, 0, 70, 200, true);
        pipe.update();

        assertEquals(200 - GameConfig.PIPE_SPEED, pipe.getX(), 0.001);
    }

    @Test
    @DisplayName("Deve identificar corretamente quando o cano saiu da tela à esquerda")
    void deveIdentificarQuandoSaiuDaTela() {
        Pipe pipe = new Pipe(10, 0, 70, 200, true);
        assertFalse(pipe.isOffScreen(), "O cano ainda está parcialmente visível");

        Pipe offScreenPipe = new Pipe(-71, 0, 70, 200, true);
        assertTrue(offScreenPipe.isOffScreen(), "O cano ultrapassou completamente o canto esquerdo da tela");
    }
}
