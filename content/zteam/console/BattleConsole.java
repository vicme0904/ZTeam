package zteam.console;
import zteam.characters.player.*;
import zteam.characters.enemies.*;
import zteam.game.*;
import zteam.characters.GameCharacter;


import java.util.Scanner;
import java.util.List;

/** Battle menu and reading player's choice  */
public class BattleConsole {

    /**
     * Battle is controlled through this console inteface
     */
    private final Battle battle;

    /**
     * Scanner to read console input
     */
    private final Scanner scanner;

    /**
     * Creates a console interface for a battle.
     * @param battle  the battle to control
     * @param scanner the scanner used to read input
     * @throws IllegalArgumentException if either argument is null
     */
    public BattleConsole(Battle battle, Scanner scanner) {
        if (battle == null || scanner == null) {
            throw new IllegalArgumentException("Battle and scanner required");
        }
        this.battle = battle;
        this.scanner = scanner;
    }

    /**
     * Starts the battle and runs it until completion or exit.
     *
     * @throws IllegalStateException if the battle cannot start
     */
    public void play() {
        if (battle.getRoundNumber() == 0) {
            battle.start();
        }

        while (!battle.isFinished()) {
            if (!battle.isPlayerTurn()) {
                System.out.println("\nEnemy turn: " + battle.getCurrentCharacter().getName());

                System.out.println(battle.performEnemyTurn());

                continue;
            }

            try {
                int action = readAction();

                switch (action) {
                    case 1:
                        performPlayerAttack();
                        break;

                    case 2:
                        performPlayerSpecialAbility();
                        break;

                    case 3:
                        System.out.println(
                                battle.getCurrentCharacter().getName() + " passed the turn."
                        );

                        battle.endTurn();
                        break;

                    case 4:
                        showStatus();
                        break;

                    case 0:
                        System.out.println("Battle closed.");
                        return;

                    default:
                        throw new IllegalArgumentException("Invalid action.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Action failed: " + e.getMessage());
            }
        }

        showStatus();

        if (battle.getParty().isDefeated()) {
            System.out.println("\nDefeat! Your party was defeated.");
        } else {
            System.out.println("\nVictory! All enemies were defeated.");
        }
    }

    /**
     * Displays the menu and reads a valid action.
     * @return 1 to attack, 2 to use an ability, 3 to pass, 4 to view status, or 0 to exit
     * @throws IllegalStateException if the battle is not active
     */
    public int readAction() {
        GameCharacter character = battle.getCurrentCharacter();

        System.out.println("\nRound: " + battle.getRoundNumber());
        System.out.println("What's the plan?");

        printCharacterStatus(character);

        System.out.println("1 - Attack");
        System.out.println("2 - Special ability");
        System.out.println("3 - Pass turn");
        System.out.println("4 - View status");
        System.out.println("0 - Flee");

        return readChoice(4);
    }

    /**
     * Reads a number between zero and the specified maximum.
     * Returns zero if the input stream ends.
     * @param maximum the largest accepted choice
     * @return the selected number, or zero when input ends
     */
    private int readChoice(int maximum) {
        while (true) {
            System.out.print("Enter the number of your choice");

            if (!scanner.hasNextLine()) {
                return 0;
            }

            String input = scanner.nextLine().trim();

            try {
                int choice = Integer.parseInt(input);

                if (choice >= 0 && choice <= maximum) {
                    return choice;
                }

                System.out.println(
                        "Choose a number from 0 to " + maximum + "."
                );
            } catch (NumberFormatException e) {
                System.out.println("Enter a whole number.");
            }
        }
    }

    /**
     * Displays the available targets and reads a selection.
     * @param targets the available characters
     * @return the selected target, or null when canceled,
     *         unavailable, or input ends
     */
    private GameCharacter chooseTarget(List<GameCharacter> targets) {
        if (targets.isEmpty()) {
            System.out.println("No living targets are available.");
            return null;
        }

        System.out.println("\nChoose a target:");

        for (int i = 0; i < targets.size(); i++) {
            System.out.print((i + 1) + " - ");
            printCharacterStatus(targets.get(i));
        }

        System.out.println("0 - Cancel");

        int choice = readChoice(targets.size());

        if (choice == 0) {
            return null;
        }

        return targets.get(choice - 1);
    }

    /** Selects an enemy and performs the current player's normal attack. */
    private void performPlayerAttack() {
        GameCharacter attacker = battle.getCurrentCharacter();

        GameCharacter target = chooseTarget(battle.getAliveEnemies());

        if (target == null) {
            System.out.println("Attack canceled.");
            return;
        }

        int damage = battle.attack(target);

        printDamageResult(attacker, target, damage, " attacked ");
    }

    /** Selects an appropriate target and performs the player's ability. */
    private void performPlayerSpecialAbility() {
        GameCharacter character = battle.getCurrentCharacter();
        GameCharacter target = null;

        if (!(character instanceof necromancer)) {
            target = chooseTarget(battle.getSpecialAbilityTargets());

            if (target == null) {
                System.out.println("Ability canceled.");
                return;
            }
        }

        int amount = battle.useSpecialAbility(target);

        if (character instanceof priest) {
            System.out.println(
                    character.getName() + " restored " + amount + " HP to " + target.getName() + "."
            );
        } else if (character instanceof necromancer) {
            System.out.println(
                    character.getName() + " summoned " + amount + " skeletons."
            );
        } else {
            printDamageResult(character, target, amount, " used a special ability on ");
        }
    }

    /**
     * Displays the result of a damaging action.
     * @param actor the attacking character
     * @param target the affected character
     * @param damage the actual damage dealt
     * @param action the action description
     */
    private void printDamageResult(GameCharacter actor, GameCharacter target, int damage, String action) {
        System.out.println(actor.getName() + action + target.getName() + " for " + damage + " damage.");

        if (!target.isAlive()) {
            System.out.println(target.getName() + " was defeated.");
        }
    }

    /** Displays the current condition of both groups. */
    private void showStatus() {
        System.out.println("\n--- PARTY ---");

        for (GameCharacter member : battle.getParty().getMembers()) {printCharacterStatus(member);
        }

        System.out.println("\n--- ENEMIES ---");

        for (GameCharacter enemy : battle.getEnemies()) {printCharacterStatus(enemy);
        }
    }

    /**
     * Displays health, attack, defense, mana, and defeated status.
     * @param character the character to display
     */
    private void printCharacterStatus(GameCharacter character) {
        String line = character.getName() + " | " + character.getCurrHealth() + "/" + character.getMaxHealth() + " HP" + " | ATK: " + character.getAttack() + " | DEF: " + character.getDefense();

        if (character.getMaxMana() > 0) {line += " | " + character.getCurrMana() + "/" + character.getMaxMana() + " MP";
        }

        if (!character.isAlive()) {
            line += " | Defeated";
        }

        System.out.println(line);
    }
}