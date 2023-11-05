package cz.vse.basi02.adventura4it115;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Třída představující inventář, ve kterém jsou umístěny
 * hráčovo sebrané předmety.Třídá uchovává sebrané předměty
 * v datovém atributu - kolekce {@link #items}.
 *
 * @author Ivan Bassov
 * @version ZS-2022-2023, 2023-01-01
 */
public class Inventory {
    private final Set<Item> items = new HashSet<>();

    /**
     * Metoda přidá předmět <i>(objekt třídy {@link Item})</i> do inventáře.
     *
     * @param item předmět, který bude do inventáře přidán
     */
    public void addItemToInventory(Item item)
    {
        items.add(item);
    }

    /**
     * Metoda vyhledá v inventáři předmět s daným názvem a odstraní ho z něj.
     *
     * @param item předmět, který bude odebrán
     */
    public void removeItemFromInventory(Item item)
    {
        items.remove(item);
    }

    /**
     * Metoda vrací odkaz na kolekci předmětů v inventáři.
     *
     * @return kolekce předmětů
     */
    public Set<Item> getItems()
    {
        return items;
    }

    /**
     * Metoda vyhledá v kolekci předmětů předmět s daným názvem a vrátí na něj odkaz.
     *
     * @param name název předmětu
     * @return předmět s daným názvem; {@code null}, pokud předmět v kolekci není
     */
    public Item getItem(String name){
        for(Item item: items){
            if(item.getName().equals(name)){
                return item;
            }
        }
        return null;
    }

    public List<String> getInventoryList()
    {
        List<String> itemNames = new ArrayList<>();
        for (Item item : items) {
            itemNames.add(item.getName());
        }

        return itemNames;
    }
}
