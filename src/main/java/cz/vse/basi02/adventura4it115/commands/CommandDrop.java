package cz.vse.basi02.adventura4it115.commands;

import cz.vse.basi02.adventura4it115.Game;
import cz.vse.basi02.adventura4it115.Item;

/**
 * Třída implementující příkaz pro odebrání předmětu z inventáře a jeho položení na zem.
 *
 * @author Ivan Bassov
 * @version ZS-2022-2023, 2023-01-04
 */

public class CommandDrop implements ICommand {
    private final Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandDrop(Game game) {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu tj.&nbsp; slovo <b>nápověda</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "polož";
    }

    /**
     * Metoda se pokusí odebrat předmět z inventáře a uložit ho do součastné herní lokace.
     * Nejprve zkontroluje počet parametrů. Pokud nebyl zadán žádný
     * parametr <i>(tj. neznáme požadovaný předmět)</i>, nebo bylo zadáno dva a
     * více parametrů <i>(tj. hráč chce odebrat více předmětů současně)</i>, vrátí
     * chybové hlášení. Pokud byl zadán právě jeden parametr, zkontroluje, zda
     * se předmět nachází v inventáři. Pokud ne, vrátí chybové hlášení.
     * Pokud kontrola proběhne v pořádku, provede
     * přesun předmětu z inventáře do lokace a vrátí informaci o položení předmětu.
     *
     * @return název informace pro hráče, které hra vypíše na konzoli
     */
    @Override
    public String execute(String[] parameters) {
        if (parameters.length < 2) {
            return "Tomu nerozumím, musíš mi říct, co mám položit.";
        }
        if (parameters.length > 2) {
            return "Tomu nerozumím, neumím položit více věcí současně. Hezky po jednom!";
        }
        String itemName= parameters[1];
        Item item = game.getGameWorld().getInventory().getItem(itemName);
        if (item == null) {
            return ("Tomu nerozumím. Předmět '" + itemName + "' nebyl nalezen v inventáři.");
        }
        game.getGameWorld().getInventory().removeItemFromInventory(item);
        game.getGameWorld().getCurrentLocation().addItem(item);
        return "Předmět '" + item.getName() + "' byl položen a odebrán z inventáře.";
    }
}