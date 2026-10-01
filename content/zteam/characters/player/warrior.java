package zteam.characters.player;
import zteam.characters.GameCharacter;

public class warrior extends GameCharacter {

//Constructor
    /**
     * Creates a warrior with fixed attributes and full health.
     *
     * @param name the warrior's name
     * @throws IllegalArgumentException if the name is null or blank
     */
    public warrior (String name) {
        super(name, 150, 25, 12);
    }

    /**
     * Performs an attack with twice the base attack power.
     *
     * @param target the character receiving the attack
     * @return the health actually removed from the target
     * @throws IllegalArgumentException if the target is null or this warrior
     * @throws IllegalStateException if either character is defeated
     */
    @Override
    public int useSpecialAbility(GameCharacter target) {
        if (target == null) {
            throw new IllegalArgumentException(
                    "The special ability must have a target."
            );
        }
        if (target == this) {
            throw new IllegalArgumentException(
                    "A warrior cannot attack itself."
            );
        }

        if (!isAlive()) {
            throw new IllegalStateException(
                    "Defeated characters cannot use abilities."
            );
        }

        int powerAttack = getAttack() * 2;
        return target.takeDamage(powerAttack);
    }
}

