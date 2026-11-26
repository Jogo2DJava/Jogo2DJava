package flappybird.game;

import flappybird.utils.GameConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes unitários para o enum {@link Difficulty} e validação de seus parâmetros físicos.
 */
class DifficultyTest {

    @Test
    @DisplayName("Modo MEDIUM deve corresponder exatamente às constantes clássicas do GameConfig")
    void modoMediumDeveUsarValoresPadraoDoGameConfig() {
        assertEquals("MÉDIO", Difficulty.MEDIUM.getLabel());
        assertEquals(GameConfig.PIPE_SPEED, Difficulty.MEDIUM.getPipeSpeed(), 0.001);
        assertEquals(GameConfig.PIPE_GAP, Difficulty.MEDIUM.getPipeGap());
        assertEquals(GameConfig.PIPE_SPAWN_INTERVAL, Difficulty.MEDIUM.getPipeSpawnInterval());
        assertEquals(GameConfig.GRAVITY, Difficulty.MEDIUM.getGravity(), 0.001);
    }

    @Test
    @DisplayName("Modo HARD deve possuir parâmetros mais desafiadores que o modo MEDIUM")
    void modoHardDeveSerMaisDesafiadorQueMedium() {
        assertEquals("DIFÍCIL", Difficulty.HARD.getLabel());
        assertTrue(Difficulty.HARD.getPipeSpeed() > Difficulty.MEDIUM.getPipeSpeed(),
                "Velocidade dos canos no modo HARD deve ser maior");
        assertTrue(Difficulty.HARD.getPipeGap() < Difficulty.MEDIUM.getPipeGap(),
                "Vão vertical entre canos no modo HARD deve ser menor");
        assertTrue(Difficulty.HARD.getPipeSpawnInterval() < Difficulty.MEDIUM.getPipeSpawnInterval(),
                "Intervalo de geração de canos no modo HARD deve ser menor");
        assertTrue(Difficulty.HARD.getGravity() > Difficulty.MEDIUM.getGravity(),
                "Gravidade no modo HARD deve ser mais acentuada");
    }

    @Test
    @DisplayName("Deve alternar ciclicamente entre MEDIUM e HARD com toggle() e next()")
    void deveAlternarCiclicamenteEntreModos() {
        assertEquals(Difficulty.HARD, Difficulty.MEDIUM.toggle());
        assertEquals(Difficulty.MEDIUM, Difficulty.HARD.toggle());
        assertEquals(Difficulty.HARD, Difficulty.MEDIUM.next());
        assertEquals(Difficulty.MEDIUM, Difficulty.HARD.next());
    }
}
