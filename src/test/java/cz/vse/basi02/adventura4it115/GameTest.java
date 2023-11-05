package cz.vse.basi02.adventura4it115;

import cz.vse.basi02.adventura4it115.Game;
import cz.vse.basi02.adventura4it115.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída pro komplexní otestování celé hry. Otestovány
 * všechny herní scénáře <i>(způsoby, kterými lze hru projít a ukončit, ať už
 * výhrou, nebo prohrou)</i>.
 *
 * @author Ivan Bassov
 * @version ZS-2022-2023, 2023-04-10
 */
public class GameTest
{
    public Game game;

    @BeforeEach
    public void setUp()
    {
        game = new Game();
    }

    @Test
    public void testBasicScenario()
    {
        assertEquals("hala", game.getGameWorld().getCurrentLocation().getName());
        assertFalse(game.isGameOver());
        assertFalse(game.getGameWorld().isVictorious());

        game.processCommand("jdi chodba");
        game.processCommand("jdi kuchyň");
        game.getGameWorld().getCurrentLocation().addItem(new Item("lampa",true));

        game.processCommand("seber lampa");
        assertNotNull(game.getGameWorld().getInventory().getItem("lampa"));
        assertEquals(game.getGameWorld().getInventory().getItem("lampa").getName(),"lampa");

        game.processCommand("jdi chodba");
        game.processCommand("jdi obývák");
        assertEquals("obývák", game.getGameWorld().getCurrentLocation().getName());

        game.processCommand("jdi sklad");
        game.processCommand("jdi suterén");
        game.processCommand("použij lampa");
        assertNull(game.getGameWorld().getInventory().getItem("lampa"));

        game.processCommand("seber klíč");
        assertFalse(game.isGameOver());
        assertNotNull(game.getGameWorld().getInventory().getItem("klíč"));
        assertEquals(game.getGameWorld().getInventory().getItem("klíč").getName(),"klíč");

        game.processCommand("jdi sklad");
        game.processCommand("jdi knihovna");
        game.processCommand("jdi koridor");
        game.processCommand("jdi ložnice");
        assertFalse(game.isGameOver());
        assertEquals("ložnice", game.getGameWorld().getCurrentLocation().getName());

        game.getGameWorld().getCurrentLocation().addExit(game.getGameWorld().getLocation("chodba"));
        game.processCommand("jdi chodba");
        game.processCommand("jdi hala");
        assertFalse(game.isGameOver());
        assertFalse(game.getGameWorld().isVictorious());

        game.getPlot().setRiddleIndex(4);
        game.processCommand("použij dveře");
        assertTrue(game.isGameOver());
        assertTrue(game.getGameWorld().isVictorious());
    }
    @Test
    public void testLoosingByStress()
    {
        assertEquals("hala", game.getGameWorld().getCurrentLocation().getName());
        assertFalse(game.isGameOver());
        assertFalse(game.getGameWorld().isVictorious());
        game.getGameWorld().setStressMeter(96);

        game.processCommand("jdi chodba");
        game.processCommand("jdi hala");

        assertTrue(game.isGameOver());
        assertFalse(game.getGameWorld().isVictorious());
    }
}
