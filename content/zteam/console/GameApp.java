package zteam.console;

import zteam.characters.player.*;
import zteam.characters.enemies.*;
import zteam.game.*;

import java.util.Scanner;


/** The beginning */
public class GameApp {

    /**
     * Creates a fresh party and starts an interactive battle.
     * @param args command-line arguments, currently unused
     */
    public static void main(String[] args) {
        Party party = new Party();

        party.addMember(new warrior("Warrior"));
        party.addMember(new archer("Archer"));
        party.addMember(new wizard("Mage"));
        party.addMember(new priest("Priest"));

        Battle battle = new Battle(party);

        battle.addEnemy(new skeletonBase("Skeleton"));
        battle.addEnemy(new orcBase("Orc"));
        battle.addEnemy(new necromancer("Necromancer", battle));

        try (Scanner scanner = new Scanner(System.in)) {
            BattleConsole console = new BattleConsole(battle, scanner);

            console.play();
        }
    }



}
