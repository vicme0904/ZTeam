package zteam.console;

import zteam.characters.GameCharacter;
import zteam.characters.player.*;
import zteam.game.Party;

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

        GameCharacter lancer = new lancer("Big pointy stick");
        GameCharacter lancerTarget = new lancer("Target");

        int piercingDamage = lancer.useSpecialAbility(lancerTarget);

        System.out.println("Piercing damage: " + piercingDamage);
        System.out.println("Lancer target health: "
                + lancerTarget.getCurrHealth());

        wizard mage = new wizard("Studying Guide to Fireball");
        GameCharacter mageTarget = new archer("Target");

        int fireballDamage = mage.useSpecialAbility(mageTarget);

        System.out.println("Fireball damage: " + fireballDamage);
        System.out.println("Mage target health: " + mageTarget.getCurrHealth());
        System.out.println("Remaining mana: " + mage.getCurrentMana());


        priest priest = new priest("praying for us");
        GameCharacter ally = new archer("archer down");

        ally.takeDamage(50);

        int restoredHealth = priest.useSpecialAbility(ally);

        System.out.println("Health restored: " + restoredHealth);
        System.out.println("Ally health: " + ally.getCurrHealth());
        System.out.println("Priest mana: " + priest.getCurrMana());

        try {
            priest.useSpecialAbility(ally);
        } catch (IllegalStateException exception) {
            System.out.println("Priest healing rejected: " + exception.getMessage());
        }

        System.out.println("Mana after rejected healing: " + priest.getCurrMana());

        Party party = new Party();

        party.addMember(archer);
        party.addMember(lancer);
        party.addMember(mage);
        party.addMember(priest);

        System.out.println("Party size: " + party.getSize());
        System.out.println("Living members: " + party.getAliveMembers().size());

        lancer.takeDamage(1000);

        System.out.println("Living members after defeat: " + party.getAliveMembers().size());
        System.out.println("Party defeated: " + party.isDefeated());

    }
}