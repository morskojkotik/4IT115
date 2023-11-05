package cz.vse.basi02.adventura4it115;

import cz.vse.basi02.adventura4it115.commands.*;
import cz.vse.basi02.adventura4it115.main.GameChange;
import cz.vse.basi02.adventura4it115.main.ObserveEntity;
import cz.vse.basi02.adventura4it115.main.Observer;
import cz.vse.basi02.adventura4it115.main.RootController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * Hlavní třída hry. Vytváří a uchovává odkaz na instanci třídy {@link GameWorld},
 * informaci o stavu hry {@link #gameOver} a kolekci s dostupnými příkazy {@link #commands}.
 * Dále také vytváří scénář hry a také množinu platných příkazů a instance tříd provádějících jednotlivé
 * příkazy.
 * <p>
 * Během hry třída vyhodnocuje jednotlivé příkazy zadané uživatelem.
 *
 * @author Ivan Bassov
 * @version ZS-2022-2023, 2023-01-01
 */
public class Game {
    private boolean gameOver;
    private final GameWorld world;
    private final Set<ICommand> commands;
    private final Plot plot;
    private Map<GameChange , Set<Observer>> observerSet = new HashMap<>();

    /**
     * Konstruktor třídy, vytvoří hru, scénář a množinu platných příkazů. Hra po
     * vytvoření již běží a je připravená zpracovávat herní příkazy.
     */
    public Game()
    {
        gameOver = false;
        world = new GameWorld();
        commands = new HashSet<>();
        plot = new Plot(this);
        commands.add(new CommandUse(this, plot));
        commands.add(new CommandReply(plot));
        commands.add(new CommandDrop(this));
        commands.add(new CommandMap(this));
        commands.add(new CommandStress(this));
        commands.add(new CommandHelp());
        commands.add(new CommandTerminate(this));
        commands.add(new CommandLocationInfo(this));
        commands.add(new CommandMove(this, plot));
        commands.add(new CommandPick(this));
        commands.add(new CommandInventory(this));

        for(GameChange gameChange : GameChange.values()){
            observerSet.put(gameChange, new HashSet<>());
        }
    }

    public void observe(GameChange gameChange, Observer observer) {
        observerSet.get(gameChange).add(observer);
    }

    public void notifyObserver(GameChange gameChange){
        for(Observer observer : observerSet.get(gameChange)){
            observer.update();
        }
    }

    /**
     * Metoda vrací informaci, zda hra již skončila <i>(je jedno, jestli
     * výhrou, prohrou nebo příkazem 'konec')</i>.
     *
     * @return {@code true}, pokud hra již skončila; jinak {@code false}
     */
    public boolean isGameOver()
    {
        return gameOver;
    }

    /**
     * Metoda nastaví příznak indikující, zda hra skončila. Metodu
     * využívá třída {@link CommandTerminate}, mohou ji ale použít
     * i další implementace rozhraní {@link ICommand}.
     *
     * @param gameOver příznak indikující, zda hra již skončila
     */
    public void setGameOver(boolean gameOver)
    {
        this.gameOver = gameOver;
        notifyObserver(GameChange.GAME_END);
    }

    /**
     * Metoda vrací odkaz na mapu prostorů herního světa.
     *
     * @return mapa prostorů herního světa
     */
    public GameWorld getGameWorld()
    {
        return world;
    }

    /**
     * Metoda zpracuje herní příkaz <i>(jeden řádek textu zadaný na konzoli)</i>.
     * Řetězec uvedený jako parametr se rozdělí na slova. První slovo je považováno
     * za název příkazu, další slova za jeho parametry.
     * <p>
     * Metoda nejprve ověří, zda první slovo odpovídá hernímu příkazu <i>(např.
     * 'napoveda', 'konec', 'jdi' apod.)</i>. Pokud ano, spustí obsluhu tohoto
     * příkazu a zbývající slova předá jako parametry.
     *
     * @param message text, který hráč zadal na konzoli jako příkaz pro hru
     * @return výsledek zpracování <i>(informace pro hráče, které se vypíšou na konzoli)</i>
     */
    public String processCommand(String message)
    {
        String commandName = message.split(" ")[0];
        ICommand command = getCommand(commandName);
        if (getCommand(commandName)!=null) {
            return command.execute(message.split(" "));
        }else return "Tomu nerozumím. Neznámý příkaz.";
    }

    /**
     * Metoda vrací úvodní text pro hráče, který se vypíše na konzoli ihned po
     * zahájení hry.
     *
     * @return úvodní text
     */
    public String getPrologue()
    {
        return "Probudil jsi se v nějaké místnosti, vůbec nic si nepamatuješ.\n" +
                "Silně jsi se vynervoval a začalo tě píchat u srdce.\n" +
                "Asi nemáš silné zdraví. Najednou jsi uslyšel tajemný hlas.\n" +
                "Tajemný hlas ti oznámil, že jediná věc která tě nyní má zajímat, je jak se dostat pryč.\n" +
                "Poté se ti prý vrátí paměť.\n" +
                "Dále ti hlas napověděl, že hlavní dveře jdou zavřené a měl bys najít klíč.\n" ;// + getGameWorld().getCurrentLocation().getFullDescription();
    }

    /**
     * Metoda vrací závěrečný text pro hráče, který se vypíše na konzoli po ukončení
     * hry. Metoda se zavolá pro ukončení hry kdy hráč vyhrál, či prohrál.
     *
     * @return závěrečný text
     */
    public String getEpilogue()
    {
        String epilogue = "Bohužel tvoje srdce nevydrželo tak vysoký stres a zastavilo se. GAMEOVER.\n\n";

        if (world.isVictorious()) {
            epilogue = "Vyhrál jsi, úspěšně jsi opustil toto divné a strašidelné místo.\n";
        }

        return epilogue;
    }

    /**
     * Metoda vyhledá v kolekci příkazů příkaz s daným názvem a vrátí na něj odkaz.
     *
     * @param commName název příkazu
     * @return příkaz s daným názvem; {@code null}, pokud příkaz v kolekci není
     */
    public ICommand getCommand(String commName){
        ICommand foundCommand = null;
        for (ICommand command : commands) {
            if (command.getName().equals(commName)){
                foundCommand = command;
            }
        }
        return foundCommand;
    }

    /**
     * Metoda vrací příběh hry.
     *
     * @return příběh hry
     */
    public Plot getPlot() {
        return plot;
    }
}
