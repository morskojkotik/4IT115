package cz.vse.basi02.adventura4it115.commands;

import cz.vse.basi02.adventura4it115.Game;

/**
 * Třída implementující příkaz pro předčasné ukončení hry.
 *
 * @author Ivan Bassov
 * @version ZS-2022-2023, 2023-01-04
 */

public class CommandTerminate implements ICommand {
    private final Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandTerminate(Game game)
    {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu tj.&nbsp; slovo <b>konec</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName()
    {
        return "konec";
    }

    /**
     * Metoda ukončí hru.
     * Nejprve zkontroluje počet parametrů. Pokud bylo zadáno 1 a více parametrů, vrátí
     *chybové hlášení. Pokud nebyl zadán žádný parametr, ukončí hru.
     *
     * @param parameters parametry příkazu <i>(očekává se prázdné pole)</i>
     * @return informace pro hráče, které hra vypíše na konzoli
     */
    @Override
    public String execute(String[] parameters)
    {
        if (parameters.length > 1) {
            return "Tomu nerozumím. Zkus napsat 'konec' bez parametrů.";
        }
        game.setGameOver(true);
        return "Hra byla ukončena příkazem KONEC.";
    }
}
