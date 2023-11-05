package cz.vse.basi02.adventura4it115.ui;
import cz.vse.basi02.adventura4it115.Game;

import java.util.Scanner;

/**
 * Třída představující uživatelské rozhraní aplikace. Zajišťuje načítání
 * příkazů z konzole a výpis reakcí hry.
 *
 * @author Ivan Bassov
 * @version ZS-2022-2023, 2023-01-04
 */
public class TextInterface
{
    private final Game game;
    private final Scanner scanner;

    /**
     * Konstruktor třídy, vytvoří uživatelské rozhraní pro danou hru.
     *
     * @param game hra
     */
    public TextInterface(Game game) {
        this.game = game;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Metoda zajišťuje hraní hry. Nejprve vypíše úvodní text. Poté v cyklu
     * načítá zadané příkazy z konzole, předává je hře ke zpracování a vypisuje
     * reakce hry. To se neustále opakuje, dokud hra prostřednictvím metody
     * {@link Game#isGameOver() isGameOver} neoznámí, že skončila.
     */
    public void play() {
        System.out.println(game.getPrologue());
        while (!game.isGameOver()) {
            System.out.print("\n> ");
            String line = scanner.nextLine();
            System.out.println(game.processCommand(line));
        }
    }
}
