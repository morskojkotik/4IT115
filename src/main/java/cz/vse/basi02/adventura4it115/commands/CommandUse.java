package cz.vse.basi02.adventura4it115.commands;

import cz.vse.basi02.adventura4it115.Game;
import cz.vse.basi02.adventura4it115.Plot;
import cz.vse.basi02.adventura4it115.Location;
import cz.vse.basi02.adventura4it115.Item;
import cz.vse.basi02.adventura4it115.Inventory;
import cz.vse.basi02.adventura4it115.main.RootController;
import cz.vse.basi02.adventura4it115.main.Start;

/**
 * Třída implementující příkaz pro použití předmětu ze země či inventáře.
 *
 * @author Ivan Bassov
 * @version ZS-2022-2023, 2023-01-04
 */
public class CommandUse implements ICommand {
    private final Game game;
    private final Plot plot;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     * @param plot příběhová část hry
     */
    public CommandUse(Game game, Plot plot) {
        this.game = game;
        this.plot = plot;
    }

    /**
     * Metoda vrací název příkazu tj.&nbsp; slovo <b>použij</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "použij";
    }

    /**
     * Metoda se pokusí použít předmět z inventáře, či lokace.
     * Nejprve zkontroluje počet parametrů. Pokud nebyl zadán žádný
     * parametr <i>(tj. neznáme požadovaný předmět)</i>, nebo bylo zadáno dva a
     * více parametrů <i>(tj. hráč chce použit více předmětů současně)</i>.
     * Pokud byl zadán právě jeden parametr, zkontroluje, zda
     * se předmět nachází v inventáři, či v lokaci. Pokud ne, vrátí
     * chybové hlášení.
     * Dále zkontroluje, zda se jedná o příběhový předmět.
     * Pokud ne, vrátí o tom hlášení. Pokud ano, vrátí informaci o následcích
     * použití předmětu a dle scénáře ovlivní(posune) stav hry.
     */
    @Override
    public String execute(String[] parameters) {
        if (parameters.length < 2) {
            return "Tomu nerozumím, musíš mi říct, co mám použít.";
        }
        if (parameters.length > 2) {
            return "Tomu nerozumím, neumím použít více věcí současně. Hezky po jednom!";
        }
        String itemName=parameters[1];
        Location currentLocation = game.getGameWorld().getCurrentLocation();
        Inventory inventory = game.getGameWorld().getInventory();
        boolean inLocation = currentLocation.getItem(itemName)!=null;
        boolean inInventory = inventory.getItem(itemName)!=null;
        if(!inLocation && !inInventory){
            return "Tomu nerozumím. Předmět '" + itemName + "' nebyl nalezen.";
        }
        Item item;
        item = inInventory ? inventory.getItem(itemName) : currentLocation.getItem(itemName);
        if(itemName.equals("vodka") || itemName.equals("pilulky") || itemName.equals("antistres")){
            itemName="antistress";
        }
        switch(itemName){
            case "lampa":
                return plot.lampUse();
            case "truhla":
                if(Start.grafickeRozhrani == false) {
                    return plot.firstRiddle(item);
                } else {
                    return RootController.getRootController().firstRiddle();
                }
            case "páka":
                return plot.secondRiddle(item);
            case "klíč":
                return plot.thirdRiddle(item);
            case "antistress":
                return plot.antistressUse(item);
            case "dveře":
                return plot.doorUse();
            default:
                return "Tento předmět nelze smysluplně použít.";
        }
    }
}