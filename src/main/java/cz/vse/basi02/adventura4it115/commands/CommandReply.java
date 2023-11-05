package cz.vse.basi02.adventura4it115.commands;

import cz.vse.basi02.adventura4it115.Plot;

/**
 * Třída implementující příkaz pro odpovězení na hádanku.
 *
 * @author Ivan Bassov
 * @version ZS-2022-2023, 2023-01-04
 */

public class CommandReply implements ICommand {
    private final Plot plot;

    /**
     * Konstruktor třídy.
     *
     * @param plot příběhová část hry
     */
    public CommandReply(Plot plot) {
        this.plot = plot;
    }
    /**
     * Metoda vrací název příkazu tj slovo <b>odpověz</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "odpověz";
    }

    /**
     * Metoda vrací reakci na hráčovu odpověď.
     * Pokud nebyl zadán žádný parametr <i>(tj. neznáme variantu odpovědi)</i>,
     * nebo bylo zadáno dva a více parametrů <i>(tj. hráč chce odpovědět
     * vícero variant současně)</i>, vrátí chybové hlášení.
     * Pokud byl zadán právě jeden parametr, zkontroluje, zda
     * hráč v dané chvíli má komu odpovídat <i>(tj. je v aktivní instanci hádanky)</i>,
     * dále zda odpovídá správným způsobem <i>(tj. odpovídá číslovkou 1-4)</i>.
     * Pokud všechny kontroly proběhnou v pořádku,
     * vrátí reakci na hráčovu odpověď <i>(tj. zda uhodl, či ne)</i>.
     *
     * @param parameters parametry příkazu <i>(očekává se pole s jedním prvkem)</i>
     * @return informace pro hráče, které hra vypíše na konzoli,
     */
    @Override
    public String execute(String[] parameters) {
        if (plot.getRiddleIndex() == 0) {
            return "Tomu nerozumím. V tuto chvíli nemáš komu odpovídat.";
        }
        if (parameters.length < 2) {
            return "Tomu nerozumím, musíš mi říct co chceš odpovědět.";
        }
        if (parameters.length > 2) {
            return "Tomu nerozumím, nemůžeš odpovědět více variant!";
        }
        String answer = parameters[1];
        if (!answer.equals("1") && !answer.equals("2") && !answer.equals("3") && !answer.equals("4")) {
            return "Tomu nerozumím, odpověz číslovkou 1 až 4.";
        }
        return plot.riddleAnswerOutput(Integer.parseInt(answer));
    }
}
