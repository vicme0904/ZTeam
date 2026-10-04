package zteam.characters.enemies;
import zteam.characters.GameCharacter;

public class orcBase extends GameCharacter {

    /**
     * Creates an orc with its default combat attributes.
     *
     * @param name the character's name
     */
    public orcBase(String name) {
        this(name, 140, 20, 15);
    }

    /**
     * Creates an orc variation with specific combat attributes.
     *
     * @param name the character's name
     * @param maxHealth the maximum health
     * @param attackPower the normal attack power
     * @param defense the damage reduction
     * @throws IllegalArgumentException if the name or attributes
     *         are invalid
     */
    protected orcBase(String name, int maxHealth,
                  int attackPower, int defense) {
        super(name, maxHealth, attackPower, defense);
    }

    /**
     * Uses Furious Strike with twice the normal attack power.
     * At half health or less, the attack power is tripled.
     *
     * @param target the character receiving the attack
     * @return the actual health removed from the target
     * @throws IllegalArgumentException if the target is null
     *         or is the orc itself
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
                    "An orc cannot attack itself."
            );
        }

        if (!isAlive()) {
            throw new IllegalStateException(
                    "Defeated characters cannot use abilities."
            );
        }

        int multiplier = 2;

        if (getCurrHealth() <= getMaxHealth() / 2) {
            multiplier = 3;
        }

        int damage = getAttack() * multiplier;

        return target.takeDamage(damage);
    }
}
