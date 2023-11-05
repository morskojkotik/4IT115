package cz.vse.basi02.adventura4it115.commands;

import cz.vse.basi02.adventura4it115.Game;
import cz.vse.basi02.adventura4it115.Item;

import java.util.stream.Collectors;

/**
 * Třída implementující příkaz pro zobrazení předmětů, které se nachází v inventáři.
 *
 * @author Ivan Bassov
 * @version  ZS-2022-2023, 2023-01-04
 */

public class CommandInventory implements ICommand {
    private final Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandInventory(Game game) {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu tj.&nbsp; slovo <b>inventář</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "inventář";
    }

    /**
     * Metoda vrátí názvy všech předmětů, které se nachází v inventáři.
     * Nejprve zkontroluje počet parametrů. Pokud bylo zadáno 1 a více parametrů, vrátí
     * chybové hlášení. Pokud nebyl zadán žádný parametr, vypíše obsah inventáře.
     *
     * @param parameters parametry příkazu <i>(očekává se prázdné pole)</i>
     * @return informace pro hráče, které hra vypíše na konzoli
     */
    @Override
    public String execute(String[] parameters) {
        if (parameters.length > 1) {
            return "Tomu nerozumím. Zkus napsat 'inventář' bez parametrů.";
        }
        return "V inventáři máš: " + game.getGameWorld().getInventory().getItems().stream().map(Item::getName).collect(Collectors.joining(", "));
    }
}
