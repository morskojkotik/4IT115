package cz.vse.basi02.adventura4it115;

/**
 * Třída představující herní předměty.
 * Každý předmet má název, který ho jednoznačně identifikuje.
 * V datovém atributu {@link #pickable} uchovává informaci o tom,
 * zda je předmět možné sebrat <i>(a tím pádem ho vložit do inventáře</i>, či nikoliv.
 *
 * @author Ivan Bassov
 * @version ZS-2022-2023, 2023-01-01
 */
public class Item {
    private final String name;
    private final boolean pickable;

    /**
     * Konstruktor třídy.
     *
     * @param name název předmětu
     * @param pickable informace o tom, zda lze předmět sebrat
     */
    public Item(String name, boolean pickable)
    {
        this.name = name;
        this.pickable = pickable;
    }

    /**
     * Metoda vrací název předmětu, který byl zadán při vytváření instance jako
     * parametr konstruktoru. Jedná se o jednoznačný identifikátor předmětu
     * <i>(ve hře nemůže existovat více předmětů se stejným názvem)</i>.
     *
     * @return název předmětu
     */
    public String getName()
    {
        return name;
    }

    /**
     * Metoda vrací informaci o tom, zda lze předmět sebrat.
     * @return {@code true}, pokud předmět lze sebrat; jinak {@code false}
     */
    public boolean isPickable()
    {
        return pickable;
    }
}
