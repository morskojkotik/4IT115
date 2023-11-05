package cz.vse.basi02.adventura4it115;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testovací třída pro komplexní otestování třídy {@link cz.vse.basi02.adventura4it115.commands.CommandUse}.
 *
 * @author Ivan Bassov
 * @version @version ZS-2022-2023, 2023-01-11
 */
public class CommandUseTest {
    private Game game;

    @BeforeEach
    public void setUp()
    {
        game = new Game();
    }

    @Test
    public void testIncorrectParameters()
    {
        assertEquals("Tomu nerozumím, musíš mi říct, co mám použít.", game.processCommand("použij"));

        assertEquals("Tomu nerozumím, neumím použít více věcí současně. Hezky po jednom!", game.processCommand("použij a b"));

        assertEquals("Tomu nerozumím. Předmět 'neco' nebyl nalezen.", game.processCommand("použij neco"));
    }

    @Test
    public void testCorrectParameters()
    {
        assertTrue(game.processCommand("použij dveře").contains("Dveře jsou zamčené. Potřebuješ klíč."));

        game.processCommand("jdi chodba");

        assertTrue(game.processCommand("použij koště").contains("Tento předmět nelze smysluplně použít."));
        game.processCommand("jdi obývák");
        assertEquals("obývák", game.getGameWorld().getCurrentLocation().getName());
        game.processCommand("jdi sklad");
        game.getGameWorld().getInventory().addItemToInventory(new Item("lampa",true));
        assertTrue(game.processCommand("použij lampa").contains("V této místnosti je světlo a lampa není potřeba."));
        game.processCommand("jdi suterén");
        assertTrue(game.processCommand("použij lampa").contains("Rozsvítíl jsi lampu a zavěsil ji uprostřed místnosti."));
    }
}
