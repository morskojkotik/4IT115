package cz.vse.basi02.adventura4it115.commands;

import cz.vse.basi02.adventura4it115.Game;
import cz.vse.basi02.adventura4it115.Item;
import cz.vse.basi02.adventura4it115.Location;
import cz.vse.basi02.adventura4it115.main.RootController;

/**
 * Třída implementující příkaz pro sebrání předmětu ze země a jeho vložení do inventáře.
 *
 * @author Ivan Bassov
 * @version ZS-2022-2023, 2023-01-04
 */

public class CommandPick implements ICommand {
    private final Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandPick(Game game)
    {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu tj.&nbsp; slovo <b>seber</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName()
    {
        return "seber";
    }

    /**
     * Metoda se pokusí sebrat předmět z aktuální lokace a uložit ho do hráčova
     * inventáře. Nejprve zkontroluje počet parametrů. Pokud nebyl zadán žádný
     * parametr <i>(tj. neznáme požadovaný předmět)</i>, nebo bylo zadáno dva a
     * více parametrů <i>(tj. hráč chce sebrat více předmětů současně)</i>, vrátí
     * chybové hlášení.
     * Pokud byl zadán právě jeden parametr, zkontroluje, zda
     * v aktuální lokaci je předmět s daným názvem, zda je přenositelný a zda
     * na něj hráč má v inventáři místo <i>(tj. zda ho lze sebrat)</i>. Pokud ne,
     * vrátí chybové hlášení. Pokud všechny kontroly proběhnou v pořádku, provede
     * přesun předmětu z lokace do inventáře a vrátí informaci o sebrání předmětu.
     *
     * @param parameters parametry příkazu <i>(očekává se pole s jedním prvkem)</i>
     * @return informace pro hráče, které hra vypíše na konzoli
     */
    @Override
    public String execute(String[] parameters)
    {
        if (parameters.length < 2) {
            return "Tomu nerozumím, musíš mi říct, co mám sebrat.";
        }

        if (parameters.length > 2) {
            return "Tomu nerozumím, neumím sebrat více věcí současně. Hezky po jednom!";
        }

        String itemName = parameters[1];
        Location currentLocation = game.getGameWorld().getCurrentLocation();
        Item item = currentLocation.getItem(itemName);

        if (item == null) {
            return "Tomu nerozumím. Předmět '" + itemName + "' nebyl nalezen.";
        }

        if (!item.isPickable()) {
            return "Předmět '" + itemName + "' nelze vzít, ale můžeš ho za jistých okolností použít.";
        }
        if(game.getGameWorld().getInventory().getItems().size() > 5){
            return "Nemáš místo v inventáři.";
        }
        currentLocation.removeItem(item);
        game.getGameWorld().getInventory().addItemToInventory(item);
        return "Sebral jsi předmět '" + itemName + "' a uložil ho do inventáře.";
    }
}
