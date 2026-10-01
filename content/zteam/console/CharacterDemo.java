package zteam.console;

import zteam.characters.GameCharacter;
import zteam.characters.player.archer;
import zteam.characters.player.warrior;

/**
 * Demo to check if character attacks, heals and check health limits.
 */
public class CharacterDemo {

    /**
     * Runs a character demonstration.
     *
     * @param args command-line arguments, unused
     */
    public static void main(String[] args) {
        GameCharacter warrior = new warrior("Bright boy");
        GameCharacter target = new warrior("Bully boy");

        System.out.println("Initial health: " + target.getCurrHealth());

        int damage = warrior.attack(target);
        System.out.println("Basic damage: " + damage);
        System.out.println("Health after attack: " + target.getCurrHealth());

        int specialDamage = warrior.useSpecialAbility(target);
        System.out.println("Special damage: " + specialDamage);
        System.out.println("Health after special: " + target.getCurrHealth());

        int healing = target.heal(100);
        System.out.println("Healing: " + healing);
        System.out.println("Health after healing: " + target.getCurrHealth());

        int finalDamage = target.takeDamage(1000);
        System.out.println("Final damage: " + finalDamage);
        System.out.println("Final health: " + target.getCurrHealth());
        System.out.println("Alive: " + target.isAlive());

        try {
            target.heal(20);
        } catch (IllegalStateException exception) {
            System.out.println("Healing rejected: " + exception.getMessage());
        }

        GameCharacter archer = new archer("Sniper man");
        GameCharacter archerTarget = new archer("Someone far");

        int doubleShotDamage = archer.useSpecialAbility(archerTarget);

        System.out.println("Double shot damage: " + doubleShotDamage);
        System.out.println("Archer target health: " + archerTarget.getCurrHealth());

    }
}