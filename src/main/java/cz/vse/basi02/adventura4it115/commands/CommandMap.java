package cz.vse.basi02.adventura4it115.commands;

import cz.vse.basi02.adventura4it115.Game;

/**
 * Třída implementující příkaz pro zobrazení mapy herních lokací.
 *
 * @author Ivan Bassov
 * @version ZS-2022-2023, 2023-01-04
 */

public class CommandMap implements ICommand {
    private final Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandMap(Game game) {
        this.game = game;
    }
    /**
     * Metoda vrací název příkazu tj.&nbsp; slovo <b>mapa</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "mapa";
    }

    /**
     * Metoda vrací mapu lokací.
     * Nejprve zkontroluje počet parametrů. Pokud bylo zadáno 1 a více parametrů, vrátí
     * chybové hlášení. Pokud nebyl zadán žádný parametr, vypíše mapu.
     *
     * @param parameters parametry příkazu <i>(očekává se prázdné pole)</i>
     * @return informace pro hráče, které hra vypíše na konzoli
     */
    @Override
    public String execute(String[] parameters) {
        if (parameters.length > 1) {
            return "Tomu nerozumím. Zkus napsat 'mapa' bez parametrů.";
        }
        return game.getGameWorld().getMap();
    }
}
