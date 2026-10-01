package zteam.characters.player;
import zteam.characters.GameCharacter;

public class lancer extends GameCharacter {

    //Constructor
    /**
     * Creates a lancer with fixed attributes and full health.
     *
     * @param name the archer's name
     * @throws IllegalArgumentException if the name is null or blank
     */
    public lancer(String name){
        super(name, 180, 23, 18);
    }

    /**
     * Performs an attack that ignores target's defense
     *
     * @param target the character receiving the attack
     * @return the health actually removed from the target
     * @throws IllegalArgumentException if the target is null or this warrior
     * @throws IllegalStateException if either character is defeated
     */
    @Override
    public int useSpecialAbility(GameCharacter target) {
        if (target == null) {
            throw new IllegalArgumentException("The special ability must have a target.");
        }
        if (target == this) {
            throw new IllegalArgumentException("Lancer cannot attack itself." );
        }
        if (!isAlive()) {
            throw new IllegalStateException( "Defeated characters cannot use abilities.");
        }
        int ignoreDefense = target.getDefense() / 2;
        int specialDamage = getAttack() + ignoreDefense;

        return target.takeDamage(specialDamage);
    }
}

