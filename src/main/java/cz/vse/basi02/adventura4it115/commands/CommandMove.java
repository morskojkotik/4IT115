package cz.vse.basi02.adventura4it115.commands;

import cz.vse.basi02.adventura4it115.Game;
import cz.vse.basi02.adventura4it115.Location;
import cz.vse.basi02.adventura4it115.Plot;

/**
 * Třída implementující příkaz pro pohyb mezi herními lokacemi.
 *
 * @author Ivan Bassov
 * @version ZS-2022-2023, 2023-01-04
 */

public class CommandMove implements ICommand {
    private final Game game;
    private final Plot plot;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     * @param plot příběhová část hry
     */
    public CommandMove(Game game, Plot plot)
    {
        this.game = game;
        this.plot = plot;
    }

    /**
     * Metoda vrací název příkazu tj.&nbsp; slovo <b>jdi</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName()
    {
        return "jdi";
    }

    /**
     * Metoda se pokusí přesunout hráče do jiné lokace. Nejprve zkontroluje počet
     * parametrů. Pokud nebyl zadán žádný parametr <i>(tj. neznáme cíl cesty)</i>,
     * nebo bylo zadáno dva a více parametrů <i>(tj. hráč chce jít na více míst
     * současně)</i>, vrátí chybové hlášení. Pokud byl zadán právě jeden parametr,
     * zkontroluje, zda s aktuální lokací sousedí lokace s daným názvem <i>(tj.
     * zda z aktuální lokace lze jít do požadovaného cíle)</i>. Pokud ne, vrátí
     * chybové hlášení. Pokud všechny kontroly proběhnou v pořádku, provede přesun
     * hráče do cílové lokace a vrátí její popis.
     *
     * @param parameters parametry příkazu <i>(očekává se pole s jedním prvkem)</i>
     * @return informace pro hráče, které hra vypíše na konzoli
     */
    @Override
    public String execute(String[] parameters)
    {
        if (parameters.length < 2 ) {
            return "Tomu nerozumím, musíš mi říct, kam mám jít.";
        }

        if (parameters.length > 2) {
            return "Tomu nerozumím, neumím jít do více lokací současně.";
        }

        String exitName = parameters[1];
        Location currentLocation = game.getGameWorld().getCurrentLocation();
        if(game.getGameWorld().getLocation(exitName) == null){
            return "Tomu nerozumím, tato místnost neexistuje.";
        }
        if (exitName.equals(currentLocation.getName())){
            return "Tomu nerozumím, už se v této místnosti nacházíš.";
        }
        if (!currentLocation.hasExit(exitName)) {
            return "Do lokace '" + exitName + "' se odsud jít nedá.";
        }
        Location exit = game.getGameWorld().getLocation(exitName);
        game.getGameWorld().setCurrentLocation(exit);
        game.getGameWorld().setStressMeter(game.getGameWorld().getStressMeter()+3);
        if(game.getGameWorld().getStressMeter()>=100){
            game.setGameOver(true);
            return game.getEpilogue();
        }
        if(exitName.equals("suterén")){
            return plot.dark() + exit.getFullDescription();
        }
        return exit.getFullDescription();
    }
}
