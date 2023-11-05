package cz.vse.basi02.adventura4it115;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * Třída představuje lokaci <i>(místo, místnost, prostor)</i> ve scénáři hry.
 * Každá lokace má název, který ji jednoznačně identifikuje. Může mít sousední
 * lokace, do kterých z ní lze odejít. Odkazy na všechny sousední lokace
 * jsou uložené v kolekci. Lokace také může obsahovat předměty. Odkazy na
 * všechny předměty v lokaci jsou uložené také v kolekci.
 *
 * @author Ivan Bassov
 * @version ZS-2022-2023, 2023-01-01
 */
public class Location {
    private final String name;
    private final Set<Item> items= new HashSet<>();
    private final Set<Location> exits;

    /**
     * Konstruktor třídy, vytvoří lokaci se zadaným názvem a popisem.
     *
     * @param name název lokace <i>(jednoznačný identifikátor, musí se jednat o text bez mezer)</i>
     */
    public Location(String name)
    {
        this.name = name;
        exits = new HashSet<>();
    }

    /**
     * Metoda vrací název lokace, který byl zadán při vytváření instance jako
     * parametr konstruktoru. Jedná se o jednoznačný identifikátor lokace
     * <i>(ve hře nemůže existovat více lokací se stejným názvem)</i>.
     *
     * @return název lokace
     */
    public String getName()
    {
        return name;
    }

    /**
     * Metoda vrací kompletní informace o lokaci. Výsledek volání obsahuje:
     * <ul>
     *     <li>název lokace</li>
     *     <li>seznam sousedních lokací, do kterých lze odejít</li>
     *     <li>seznam předmětů v lokaci</li>
     * </ul>
     *
     * @return kompletní informace o lokaci
     */
    public String getFullDescription()
    {
        StringBuilder exitNames = new StringBuilder("Východy:");
        for (Location exit : exits) {
            exitNames.append(" ").append(exit.getName());
        }

        StringBuilder itemNames = new StringBuilder("Předměty:");
        for (Item item : items) {
            itemNames.append(" ").append(item.getName());
       }

       return "Lokace : '" + name + "'.\n"
                   + exitNames + "\n"
              + itemNames;
    }

    public List<String> getExitList()
    {
        List<String> exitNames = new ArrayList<>();
        for (Location exit : exits) {
            exitNames.add(exit.getName());
        }

        return exitNames;
    }

    /**
     * Metoda přidá další východ z této lokace do lokace předané v parametru.
     * <p>
     * @param exit lokace, do které bude vytvořen východ z aktuální lokace
     */
    public void addExit(Location exit)
    {
        exits.add(exit);
    }

    /**
     * Metoda zkontroluje, zda lokace sousedí s lokací s daným názvem.
     *
     * @param exitName název lokace
     * @return {@code true}, pokud lokace sousedí s lokací s daným názvem; jinak {@code false}
     */
    public boolean hasExit(String exitName)
    {
        for (Location exit : exits) {
            if (exit.getName().equals(exitName)) {
                    return true;
            }
        }
        return false;
    }

    /**
     * Metoda vyhledá sousední lokaci s daným názvem a vrátí na ní odkaz.
     *
     * @param exitName název lokace
     * @return lokace s daným názvem; {@code null}, pokud lokace s takto pojmenovanou lokací nesousedí
     */
    public Location getExit(String exitName)
    {
        for (Location exit : exits) {
            if (exit.getName().equals(exitName)) {
                return exit;
            }
        }
        return null;
    }

    /**
     * Metoda přidá předmět <i>(objekt třídy {@link Item})</i> do lokace.
     *
     * @param item předmět, který bude do lokace přidán
     */
    public void addItem(Item item)
    {
        items.add(item);
    }

    /**
     * Metoda vyhledá v lokaci předmět s daným názvem a vrátí na něj odkaz.
     *
     * @param itemName název předmětu
     * @return předmět s daným názvem; {@code null}, pokud v lokaci není
     */
    public Item getItem(String itemName)
    {
        for(Item item: items){
            if(item.getName().equals(itemName)){
                return item;
            }
        }
        return null;
    }

    /**
     * Metoda vyhledá v lokaci předmět s daným názvem a odstraní ho z lokace.
     * @param item předmět, který se odstraní z lokace
     */
    public void removeItem(Item item)
    {
        items.remove(item);
    }

    /**
     * Metoda vyhledá ve východech lokaci s daným názvem a odstraní jí z lokace.
     * @param location lokace, která se odstraní z východů
     */
    public void removeExit(Location location)
    {
        exits.remove(location);
    }

    /**
     * Metoda vrací odkaz na kolekci předmětů v lokaci.
     *
     * @return kolekce předmětů
     */
    public Set<Item> getItems()
    {
        return items;
    }
}
