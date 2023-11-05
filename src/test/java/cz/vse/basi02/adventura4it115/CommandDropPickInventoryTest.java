package cz.vse.basi02.adventura4it115;

import cz.vse.basi02.adventura4it115.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída pro komplexní otestování třídy {@link basi02.commands.CommandPick}, {@link basi02.commands.CommandDrop} a {@link basi02.commands.CommandInventory}.
 *
 * @author Ivan Bassov
 * @version ZS-2022-2023, 2023-01-11
 */
public class CommandDropPickInventoryTest
{
    private Game game;

    @BeforeAll
    public void setUp()
    {
        game = new Game();
    }

    @Test
    public void testIncorrectParameters()
    {
        assertEquals("Tomu nerozumím, musíš mi říct, co mám položit.", game.processCommand("polož"));

        assertEquals("Tomu nerozumím, musíš mi říct, co mám sebrat.", game.processCommand("seber"));


        assertEquals("Tomu nerozumím, neumím sebrat více věcí současně. Hezky po jednom!", game.processCommand("seber a b"));
        assertEquals("Tomu nerozumím, neumím položit více věcí současně. Hezky po jednom!", game.processCommand("polož a b"));

        assertEquals("Tomu nerozumím. Zkus napsat 'inventář' bez parametrů.",game.processCommand("inventář a"));

        assertEquals("Tomu nerozumím. Předmět 'neco' nebyl nalezen v inventáři.", game.processCommand("polož neco"));
        assertEquals("Tomu nerozumím. Předmět 'neco' nebyl nalezen.", game.processCommand("seber neco"));
        assertEquals("Předmět 'dveře' nelze vzít, ale můžeš ho za jistých okolností použít.", game.processCommand("seber dveře"));
    }

    @Test
    public void testCorrectParameters()
    {
        assertTrue(game.processCommand("jdi chodba").contains("Jsi v lokaci 'chodba'."));
        assertTrue(game.processCommand("seber koště").contains("Sebral jsi předmět 'koště'"));
        assertTrue(game.processCommand("inventář").contains("V inventáři máš: koště"));
        assertNull(game.getGameWorld().getCurrentLocation().getItem("koště"));
        assertEquals("koště", game.getGameWorld().getInventory().getItem("koště").getName());
        assertTrue(game.processCommand("polož koště").contains("Předmět 'koště' byl položen"));
        assertEquals("koště", game.getGameWorld().getCurrentLocation().getItem("koště").getName());
        assertNull(game.getGameWorld().getInventory().getItem("koště"));
    }
}
