package cz.vse.basi02.adventura4it115;

import cz.vse.basi02.adventura4it115.commands.CommandMove;
import cz.vse.basi02.adventura4it115.main.GameChange;
import cz.vse.basi02.adventura4it115.main.ObserveEntity;
import cz.vse.basi02.adventura4it115.main.Observer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Třída představující mapu lokací herního světa. V datovém atributu
 * {@link #currentLocation} uchovává odkaz na aktuální lokaci, ve které
 * se hráč právě nachází. Z aktuální lokace je možné se prostřednictvím
 * jejích sousedů dostat ke všem přístupným lokacím ve hře.
 * V datovém atributu {@link #map} uchovává "grafické" zobrazení mapy.
 * Dále třídá uchovává odkazy na inventář, úrověň stresu, kolekci všech lokací
 * a stav hry.
 *
 * @author Ivan Bassov
 * @version ZS-2023-2024, 2023-11-01
 */
public class GameWorld implements ObserveEntity {
    private Location currentLocation;
    private String map;
    private  Inventory inventory;
    private int stressMeter;
    private Set<Location> locations;
    private boolean isVictorious;
    private Map<GameChange, Set<Observer>> observerSet = new HashMap<>();


    public void observe(GameChange gameChange, Observer observer) {
        observerSet.get(gameChange).add(observer);
    }

    /**
     * Konstruktor třídy, vytvoří mapu, dále jednotlivé lokace, které propojí
     * pomocí východů a vloží do nich předměty.
     */
    public GameWorld() {
        CreateGameWorld();
        for(GameChange gameChange : GameChange.values()) {
            observerSet.put(gameChange, new HashSet<>());
        }
    }

    public void CreateGameWorld() {
        isVictorious = false;
        map = "              Hala ----- Východ\n" +
                "               |\n" +
                "  Kuchyň --- Chodba     Ložnice\n" +
                "               |           |\n" +
                "             Obývák --- Koridor\n" +
                "               |           |\n" +
                "             Sklad ---- Knihovna\n" +
                "               |\n" +
                "            Suterén";

        inventory = new Inventory();
        stressMeter = 0;
        Location hall = new Location("východ");
        Location kitchen = new Location("kuchyn");
        Location hallWay = new Location("hala");
        Location bedroom = new Location("ložnice");
        Location livingRoom = new Location("obývák");
        Location corridor = new Location("koridor");
        Location warehouse = new Location("sklad");
        Location library = new Location("knihovna");
        Location basement = new Location("suterén");
        locations = new HashSet<>();
        locations.add(hall);
        locations.add(kitchen);
        locations.add(hallWay);
        locations.add(bedroom);
        locations.add(livingRoom);
        locations.add(corridor);
        locations.add(warehouse);
        locations.add(library);
        locations.add(basement);

        hall.addExit(hallWay);
        hallWay.addExit(hall);
        hallWay.addExit(kitchen);
        hallWay.addExit(livingRoom);
        kitchen.addExit(hallWay);
        livingRoom.addExit(hallWay);
        livingRoom.addExit(corridor);
        livingRoom.addExit(warehouse);
        corridor.addExit(livingRoom);
        corridor.addExit(bedroom);
        corridor.addExit(library);
        library.addExit(corridor);
        library.addExit(warehouse);
        bedroom.addExit(corridor);
        warehouse.addExit(livingRoom);
        warehouse.addExit(library);
        warehouse.addExit(basement);
        basement.addExit(warehouse);

        Item door = new Item("dveře", false);
        Item vodka = new Item("vodka", true);
        Item antistress = new Item("antistres", true);
        Item chest = new Item("truhla", false);
        Item piluls = new Item("pilulky", true);
        Item brawl = new Item("koště", true);
        Item stick = new Item("klacek", true);
        Item shoe = new Item("bota", true);
        Item rag = new Item("hadr", true);
        kitchen.addItem(rag);
        bedroom.addItem(shoe);
        hall.addItem(door);
        warehouse.addItem(vodka);
        kitchen.addItem(chest);
        library.addItem(piluls);
        livingRoom.addItem(antistress);
        hallWay.addItem(brawl);
        corridor.addItem(stick);
        currentLocation = hall;
    }


    /**
     * Metoda vrací odkaz na aktuální lokaci, ve které se hráč právě nachází.
     *hráč obdrží informace na konzoli
     * @return aktuální lokace
     */
    public Location getCurrentLocation()
    {
        return currentLocation;
    }

    /**
     * Metoda nastaví aktuální lokaci. Používá ji příkaz {@link CommandMove}
     * při přechodu mezi lokacemi.
     *
     * @param currentLocation lokace, která bude nastavena jako aktuální
     */
    public void setCurrentLocation(Location currentLocation)
    {
        this.currentLocation = getLocation(currentLocation.getName());
        notifyObserver(GameChange.LOCATION_CHANGE);

    }

    public void notifyObserver(GameChange gameChange){
        for(Observer observer : observerSet.get(gameChange)){
            observer.update();
        }
    }

    /**
     * Metoda vrací informaci, zda hráč vyhrál <i>(zda jsou splněné všechny
     * úkoly a podmínky nutné pro výhru)</i>.
     *
     * @return {@code true}, pokud hráč vyhrál; jinak {@code false}
     */
    public boolean isVictorious()
    {
        return isVictorious;
    }

    /**
     * Metoda nastaví příznak indikující, zda hráč vyhrál, či ne. Metodu
     * využívá třída {@link Plot}.
     */
    public void setVictorious()
    {
        isVictorious = true;
    }

    /**
     * Metoda vrací grafické zobrazení mapy lokací.
     *
     * @return mapa lokací
     */
    public String getMap()
    {
        return map;
    }

    /**
     * Metoda upraví grafické zobrazení mapy. Metodu
     * využívá třída {@link Plot}.
     */
    public void changeMapOne(){
        this.map =
                "              Hala ----- Východ\n" +
                        "               |\n" +
                        "  Kuchyň --- Chodba     Ložnice\n" +
                        "                           |\n" +
                        "                        Koridor\n" +
                        "                           |\n" +
                        "             Sklad ---- Knihovna\n" +
                        "               |\n" +
                        "            Suterén";
    }

    public void changeMapTwo(){
        this.map =
                "              Hala ----- Východ\n" +
                        "               |\n" +
                        "  Kuchyň --- Chodba --- Ložnice\n" +
                        "                          |\n" +
                        "                        Koridor\n" +
                        "                           |\n" +
                        "             Sklad ---- Knihovna\n" +
                        "               |\n" +
                        "            Suterén";
    }
    /**
     * Metoda vrací odkaz na hráčův inventář.
     *
     * @return inventář
     */
    public Inventory getInventory()
    {
        return inventory;
    }

    /**
     * Metoda vrací odkaz na hráčovo úroveň stresu.
     *
     * @return úroveň stresu
     */
    public int getStressMeter()
    {
        return stressMeter;
    }

    /**
     * Metoda nastaví úroveň stresu a jeho minimální a maximální hodnoty.
     * Metodu využívá třída {@link Plot} a třída {@link CommandMove}.
     *
     * @param stressMeter nastavovaný uroveň stresu
     */
    public void setStressMeter(int stressMeter) {
        if (stressMeter>=100){
            this.stressMeter = stressMeter;
        }
        else this.stressMeter = Math.max(stressMeter, 0);
    }

    /**
     * Metoda vyhledá v kolekci lokací lokaci s daným názvem a vrátí na ní odkaz.
     *
     * @param locationName název lokace
     * @return lokace s daným názvem; {@code null}, pokud lokace v kolekci není
     */
    public Location getLocation(String locationName){
        for (Location location : locations) {
            if(location.getName().equals(locationName)){
                return location;
            }
        }
        return null;
    }

    /**
     * Metoda odstraňuje východy do lokace "obývák". Metodu
     * využívá třída {@link Plot}.
     */
    public void changeExits() {
        getLocation("chodba").removeExit(getLocation("obývák"));
        getLocation("sklad").removeExit(getLocation("obývák"));
        getLocation("koridor").removeExit(getLocation("obývák"));
    }
}
