package flappybird.entities;

import flappybird.game.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.Rectangle;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes unitários para comprovação dos pilares da POO (Polimorfismo e Encapsulamento).
 */
class PolymorphismTest {

    @Test
    @DisplayName("Deve manipular diferentes entidades de forma uniforme através da referência base GameObject")
    void deveTratarEntidadesDeFormaPolimorfica() {
        Game game = new Game();
        game.start();
        game.update(); // gera canos

        List<GameObject> objects = game.getGameObjects();
        assertFalse(objects.isEmpty(), "A lista polimórfica deve conter entidades");

        for (GameObject obj : objects) {
            // Demonstração de polimorfismo: qualquer entidade responde a getBounds() e update()
            Rectangle bounds = obj.getBounds();
            assertNotNull(bounds, "Toda entidade do jogo possui caixa delimitadora retangular");
            assertTrue(obj.getWidth() > 0, "A largura da entidade deve ser positiva");
            assertTrue(obj.getHeight() > 0, "A altura da entidade deve ser positiva");

            // Chamada polimórfica de atualização
            obj.update();
        }
    }

    @Test
    @DisplayName("Deve impedir adulteração externa da lista de entidades (Encapsulamento defensivo)")
    void deveRespeitarEncapsulamentoDaListaDeObjetos() {
        Game game = new Game();
        List<GameObject> objects = game.getGameObjects();

        // Tentar inserir elemento diretamente na lista deve lançar exceção
        assertThrows(UnsupportedOperationException.class, () -> {
            objects.add(new Bird());
        }, "A lista exposta por getGameObjects() deve ser estritamente imutável");
    }
}
