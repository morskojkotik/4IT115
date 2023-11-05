package cz.vse.basi02.adventura4it115.commands;

/**
 * Třída implementující příkaz pro zobrazení nápovědy ke hře.
 *
 * @author Ivan Bassov
 * @version @version ZS-2022-2023, 2023-01-04
 */

public class CommandHelp implements ICommand {

    /**
     * Metoda vrací název příkazu tj.&nbsp; slovo <b>nápověda</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName()
    {
        return "nápověda";
    }

    /**
     * Metoda vrací obecnou nápovědu ke hře a informaci o příkazech.
     * Nejprve zkontroluje počet parametrů. Pokud bylo zadáno 1 a více parametrů, vrátí
     * chybové hlášení. Pokud nebyl zadán žádný parametr, vypíše nápovědu.
     *
     * @param parameters parametry příkazu <i>(očekává se prázdné pole)</i>
     * @return nápověda, kterou hra vypíše na konzoli
     */
    @Override
    public String execute(String[] parameters)
    {
        if (parameters.length > 1) {
            return "Tomu nerozumim. Zkus napsat 'nápověda' bez parametrů.";
        }
        return "Musíš najít klíč a dostat se z tohoto děsivého domu. \n" +
                "Seznam dostupných užitečných příkazů : \n" +
                "jdi\n" +
                "inventář\n" +
                "mapa\n" +
                "lokace";
    }
}
