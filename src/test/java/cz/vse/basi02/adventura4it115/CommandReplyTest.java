package cz.vse.basi02.adventura4it115;

import cz.vse.basi02.adventura4it115.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída pro komplexní otestování třídy {@link basi02.commands.CommandReply}.
 *
 * @author Ivan Bassov
 * @version ZS-2022-2023, 2023-01-11
 */

public class CommandReplyTest {

    private Game game;

    @BeforeAll
    public void setUp()
    {
        game = new Game();
    }

   @Test
    public void testIncorrectParameters()
   {
       assertEquals("Tomu nerozumím. V tuto chvíli nemáš komu odpovídat.", game.processCommand("odpověz"));

       game.getPlot().setRiddleIndex(1);

       assertEquals("Tomu nerozumím, nemůžeš odpovědět více variant!", game.processCommand("odpověz 1 3"));

       assertEquals("Tomu nerozumím, musíš mi říct co chceš odpovědět.", game.processCommand("odpověz"));

       assertEquals("Tomu nerozumím, odpověz číslovkou 1 až 4.", game.processCommand("odpověz a"));
   }

   @Test
    public void testCorrectParameters()
   {

       game.getPlot().setRiddleIndex(1);
       assertEquals("Správně!", game.processCommand("odpověz 3"));

       game.getPlot().setRiddleIndex(2);
       assertEquals("Správně!", game.processCommand("odpověz 4"));

       game.getPlot().setRiddleIndex(3);
       assertEquals("Správně!", game.processCommand("odpověz 2"));

   }


   @Test
    public void testAnswerStress()
   {
       game.getPlot().setRiddleIndex(1);
       game.getGameWorld().setStressMeter(0);
       assertEquals("Špatně! Hladina stresu se zvýšila o 25%.", game.processCommand("odpověz 1"));
       assertEquals(25, game.getGameWorld().getStressMeter());

       game.getGameWorld().setStressMeter(80);
       game.processCommand("odpověz 1");
       assertTrue(game.getPlot().getShouldBeDead());

   }

}
