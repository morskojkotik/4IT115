package cz.vse.basi02.adventura4it115;

import cz.vse.basi02.adventura4it115.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída pro komplexní otestování třídy {@link basi02.commands.CommandMove}.
 *
 * @author Ivan Bassov
 * @version ZS-2022-2023, 2023-01-11
 */
public class CommandMoveTest
{
    private Game game;
    
    @BeforeEach
    public void setUp()
    {
        game = new Game();
    }

    @Test
    public void testIncorrectParameters()
    {
        assertEquals("Tomu nerozumím, musíš mi říct, kam mám jít.", game.processCommand("jdi"));
        assertEquals("hala", game.getGameWorld().getCurrentLocation().getName());

        assertEquals("Tomu nerozumím, neumím jít do více lokací současně.", game.processCommand("jdi a b"));
        assertEquals("hala", game.getGameWorld().getCurrentLocation().getName());

        assertEquals("Tomu nerozumím, tato místnost neexistuje.", game.processCommand("jdi nekam"));
        assertEquals("hala", game.getGameWorld().getCurrentLocation().getName());
    }
    
    @Test
    public void testCorrectParameters()
    {
        assertTrue(game.processCommand("jdi chodba").contains("Jsi v lokaci 'chodba'."));
        assertEquals("chodba", game.getGameWorld().getCurrentLocation().getName());

        assertTrue(game.processCommand("jdi hala").contains("Jsi v lokaci 'hala'."));
        assertEquals("hala", game.getGameWorld().getCurrentLocation().getName());
    }
}
