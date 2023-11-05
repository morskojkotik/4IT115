package cz.vse.basi02.adventura4it115;

import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Třida představuje sebou herní zápletku,všechny scénáře, které se mohou během hry vyskytnout
 * @author Ivan Bassov
 * @version ZS-2022-2023, 2023-01-01
 */
public class Plot {
    private final Game game;
    private int riddleIndex;
    public boolean isAnswered;
    boolean lampUsed;
    public boolean shouldBeDead;

    public Plot(Game game) {
        this.game = game;
        riddleIndex = 0;
        isAnswered = false;
        shouldBeDead = false;
        lampUsed = false;
    }

    /**
     *  Následující tři metody nastaví scenař, při kterém hrač musí
     *  odpovedět na otazku. Hráč má na výběr ze 4 možných odpovědí, ale pouze jedna z nich je správná.
     *  Pokud je odpověď správná, dochází ke změnám v zápletce hry nebo hráč najde předmět pro další rozvoj hry.
     *  Také pokud hráč odpoví na otázku špatně a jeho úroveň stresu je vyšší než 75, hra skončí,
     *  protože úroveň stresu bude vyšší než 100.
     */
    public String firstRiddle(Item item){
        Location currentLocation = game.getGameWorld().getCurrentLocation();
        String riddleText = "\"Když jsi se dotknul truhly, tak jsi uslyšel tajemný hlas:\\n\" +\n" +
                "                \"\\\"Jsem číslo. Když mnou vynásobíte jiné číslo, \" +\n" +
                "                \"tak ciferný součet výsledku je mnou dělitelný.\\n\" +\n" +
                "                \"Co jsem za číslo?\\\"\\n\" +\n" +
                "                \"   1) sedm\\n\" +\n" +
                "                \"   2) osm\\n\" +\n" +
                "                \"   3) devět\\n\" +\n" +
                "                \"   4) pět\\n\" +\n" +
                "                \"Odpovězte příkazem 'odpověď' a číslovkou 1 až 4 reprezuntující vaší odpověď.\\n\" +\n" +
                "                \"Dostupné příkazy: odpověz, stres, použij, inventář, konec.\"" ;
        riddleIndex=1;
        System.out.println(riddleText);
        readInput();
        if(game.isGameOver()){
            if(shouldBeDead){
                return game.getEpilogue();
            }
            return "";
        }
        currentLocation.addItem(new Item("lampa", true));
        currentLocation.removeItem(item);
        isAnswered = false;
        riddleIndex = 0;
        return "Hlas se rozloučil a zmizel.\n" +
                "Otevřel jsi truhlu a v ní jsi našel lampu!\n" +
                "Předměty v místnosti: " +
                game.getGameWorld().getCurrentLocation().getItems().stream().map(Item::getName).collect(Collectors.joining(", "));
    }
    public String secondRiddle(Item item){
        Location currentLocation = game.getGameWorld().getCurrentLocation();
        riddleIndex=2;
        System.out.println("Když jsi se dotknul páky, tak jsi znovu uslyšel tajemný hlas:\n" +
                "\"Jsem muž a nemám sourozence. Doma na stěně mi visí podobizna.\n" +
                "Otec muže na obrazu je synem mého otce. Kdo je na obrazu?\"\n" +
                "   1) tvůj dědeček\n" +
                "   2) tvůj otec\n" +
                "   3) ty\n" +
                "   4) tvůj syn\n" +
                "Odpovězte příkazem 'odpověď' a číslovkou 1 až 4 reprezuntující vaší odpověď.\n" +
                "Dostupné příkazy: odpověz, stres, použij, inventář, konec.");
        currentLocation.removeItem(item);
        readInput();
        if(game.isGameOver()){
            if(shouldBeDead){
                return game.getEpilogue();
            }
            return "";
        }
        riddleIndex=0;
        game.getGameWorld().changeMapTwo();
        currentLocation.addExit(game.getGameWorld().getLocation("chodba"));
        game.getGameWorld().getLocation("chodba").addExit(currentLocation);
        isAnswered = false;
        return("Hlas se rozloučil a zmizel.\n" +
                "Zatáhnul jsi za páku, ozvali se krachtivé zvuky.\n" +
                "Po chvilce se otevřel tajný průchod do chodby. Obnovila se ti mapa.");
    }
    public String thirdRiddle(Item item){
        if(!game.getGameWorld().getCurrentLocation().getName().equals("hala")){
            return "V této místnosti není nic, co by se dalo klíčem otevřít.\n";
        }
        riddleIndex=3;
        System.out.println("Když jsi vložil klíč do klíčové dírky, tak jsi znovu uslyšel tajemný hlas:\n" +
                "\"Ve stáji, kde jsou pouze lidé a koně, je celkem 11 hlav a 36 nohou.\n" +
                "Kolik je ve stáji lidí a koní?\"\n" +
                "   1) 6 koní a 5 lidí\n" +
                "   2) 7 koní a 4 lidé\n" +
                "   3) 5 koní a 6 lidí\n" +
                "   4) 4 koně a 7 lidí\n" +
                "Odpovězte příkazem 'odpověď' a číslovkou 1 až 4 reprezuntující vaší odpověď.\n" +
                "Dostupné příkazy: odpověz, stres, použij, inventář, konec.");
        game.getGameWorld().getInventory().removeItemFromInventory(item);
        readInput();
        if(game.isGameOver()){
            if(shouldBeDead){
                return game.getEpilogue();
            }
            return "";
        }
        riddleIndex=4;
        isAnswered = false;
        return("Hlas se s tebou rozloučil a zmizel.\n" +
                "Klíčema jsi otevřel dveře!\n" +
                "Předměty v místnosti: "
                + game.getGameWorld().getCurrentLocation().getItems().stream().map(Item::getName).collect(Collectors.joining(", ")));
    }

    public int getRiddleIndex() {
        return riddleIndex;
    }

    /** Metoda kontroluji, zda hrač spravně odpovida na otazky nebo
     * v případě špatné odpovědi vrátí chybové hlášení
     */
    public String riddleAnswerOutput(int answer){
        switch (riddleIndex) {
            case 1:
                if (answer==3) isAnswered = true;
                return answer == 3 ? "Správně!" : badAnswer();
            case 2:
                if (answer==4) isAnswered = true;
                return answer == 4 ? "Správně!" : badAnswer();
            case 3:
                if (answer==2) isAnswered = true;
                return answer == 2 ? "Správně!" : badAnswer();
            default:
                return "Tomu nerozumím, musíš mi říct co chceš odpovědět.";
        }
    }

    /**
     *  Metoda implementuje, zda v případě nesprávné odpovědi na otázku se stres postavy zvýší o 25
     * @return informace pro hráče, které hra vypíše na konzoli
     */
    private String badAnswer(){
        game.getGameWorld().setStressMeter(game.getGameWorld().getStressMeter()+25);
        if(game.getGameWorld().getStressMeter()>=100){
            shouldBeDead = true;
        }
        return "Špatně! Hladina stresu se zvýšila o 25%.";
    }

    /**
     */
    private void readInput(){
        Scanner scanner = new Scanner(System.in);
        while (!isAnswered) {
            System.out.print("\n> ");
            String line = scanner.nextLine();
            String commandName = line.split(" ")[0];
            String[] possibleCommands = new String[5];
            possibleCommands[0] = game.getCommand("stres").getName();
            possibleCommands[1] = game.getCommand("inventář").getName();
            possibleCommands[2] = game.getCommand("použij").getName();
            possibleCommands[3] = game.getCommand("odpověz").getName();
            possibleCommands[4] = game.getCommand("konec").getName();
            boolean commandFound = false;
            for (int i = 0; i <= 4; i = i + 1) {
                if(possibleCommands[i].equals(commandName)){
                    commandFound= true;
                    break;
                }
            }
            if(commandFound){
                System.out.println(game.processCommand(line));
            }
            else{
                System.out.println("Tomu teď nerozumím, nejdříve odpověz na hádanku!");
            }
            if (commandName.equals("konec")|| shouldBeDead){
                game.setGameOver(true);
                break;
            }
        }
    }
    /**
     *  Metoda implementuje použiti předmetu lampy a antistress
     * @return informace pro hráče, které hra vypíše na konzoli
     */

    public String lampUse() {
        Location currentLocation = game.getGameWorld().getCurrentLocation();
        Inventory inventory = game.getGameWorld().getInventory();
        if(currentLocation.getName().equals("suterén")){
            Item lamp = inventory.getItem("lampa");
            inventory.removeItemFromInventory(lamp);
            currentLocation.addItem(new Item("klíč", true));
            game.getGameWorld().getLocation("ložnice").addItem(new Item("páka", false));
            lampUsed=true;
            game.getGameWorld().changeExits();
            game.getGameWorld().changeMapOne();
            return "Rozsvítíl jsi lampu a zavěsil ji uprostřed místnosti.\n" +
                    "Celá místnost je nyní osvícena! Na stole v místnosti jsi našel klíč!\n" +
                    "Uslyšel jsi nějaký rámus.\n" +
                    "Otočil jsi se a uviděl, že se prolomila střecha nad obývákem.\n" +
                    "Celá místnost se zavalila a už jí nemůžeš projít.\n" +
                    "Najdi nějaký způsob tuto místnost obejít. Intuice ti říká, že bys měl jít do ložnice.\n" +
                    "Předměty v místnosti: " + currentLocation.getItems().stream().map(Item::getName).collect(Collectors.joining(", "));
        }else{
            return "V této místnosti je světlo a lampa není potřeba.\n" +
                    "Ale někde jinde by se určitě mohla hodit!";
        }
    }
    public String antistressUse(Item item){
        String itemName = item.getName();
        Inventory inventory = game.getGameWorld().getInventory();
        switch(itemName){
            case "vodka":
                game.getGameWorld().setStressMeter(game.getGameWorld().getStressMeter()-25);
                inventory.removeItemFromInventory(item);
                return "Dopil jsi poslední zbytky vodky ze dna flašky.\n" +
                        "Po chvilce se ti lehce přitupily smysly.\n" +
                        "Úroveň stresu se snížila o 25%.";
            case "pilulky":
                game.getGameWorld().setStressMeter(game.getGameWorld().getStressMeter()-25);
                inventory.removeItemFromInventory(item);
                return "Snědl jsi poslední dvě pilulky z balení prášků na zklidnění.\n"+
                        "Úroveň stresu se snížila o 25%.";
            case "antistres":
                game.getGameWorld().setStressMeter(game.getGameWorld().getStressMeter()-25);
                inventory.removeItemFromInventory(item);
                return "Taková blbost, antistresová hračka. K čemu to je?\n" +
                        "Ovšem po chvilce hraní jsi si opravdu začal připadat klidnější.\n" +
                        "Úroveň stresu se snížila o 25%.";
            default: return null;
        }
    }

    /**
     *  Metody nastaví připady při kterích je nutně použit předmety
     * @return informace pro hráče, které hra vypíše na konzoli
     */

    public String doorUse() {
        if (riddleIndex==4){
            game.getGameWorld().setVictorious();
            game.setGameOver(true);
            return game.getEpilogue();
        }
        else{
            return "Dveře jsou zamčené. Potřebuješ klíč.";
        }
    }

    public String dark() {
        if(!lampUsed){
            return "Je tady strašná tma, není nic vidět!\n" +
                    "Hodila by se nějaká lampa, svíčka nebo jiný zdroj světla.\n";
        }
        return "";
    }
    public void setRiddleIndex(int index) {
        riddleIndex=index;
    }

    public boolean getShouldBeDead(){return shouldBeDead;}

    public String getFirstRiddleText(){
        return "\"Když jsi se dotknul truhly, tak jsi uslyšel tajemný hlas:\\n\" +\n" +
                "                \"\\\"Jsem číslo. Když mnou vynásobíte jiné číslo, \" +\n" +
                "                \"tak ciferný součet výsledku je mnou dělitelný.\\n\" +\n" +
                "                \"Co jsem za číslo?\\\"\\n\" +\n" +
                "                \"   1) sedm\\n\" +\n" +
                "                \"   2) osm\\n\" +\n" +
                "                \"   3) devět\\n\" +\n" +
                "                \"   4) pět\\n\" +\n" +
                "                \"Odpovězte příkazem 'odpověď' a číslovkou 1 až 4 reprezuntující vaší odpověď.\\n\" +\n" +
                "                \"Dostupné příkazy: odpověz, stres, použij, inventář, konec.\"" ;
    }

}
