package cz.vse.basi02.adventura4it115;

import cz.vse.basi02.adventura4it115.Inventory;
import cz.vse.basi02.adventura4it115.Game;
import cz.vse.basi02.adventura4it115.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída pro komplexní otestování třídy {@link Inventory}.
 *
 * @author Ivan Bassov
 * @version @version ZS-2022-2023, 2023-01-11
 */
public class InventoryTest {
    private Game game;

    @BeforeEach
    public void setUp()
    {
        game = new Game();

    }
    @Test
    public void testInventory(){
        Item item1 = new Item("kul",true);
        Item item2 = new Item("neco",true);
        Inventory inventory = game.getGameWorld().getInventory();
        assertNull(inventory.getItem("neco"));
        inventory.addItemToInventory(item1);
        inventory.addItemToInventory(item2);
        assertNotNull(inventory.getItems());
        assertEquals("kul", inventory.getItem("kul").getName());
        assertEquals("neco", inventory.getItem("neco").getName());
        inventory.removeItemFromInventory(item1);
        inventory.removeItemFromInventory(item2);
        assertNull(inventory.getItem("neco"));
    }
}
