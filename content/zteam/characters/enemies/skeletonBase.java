package zteam.characters.enemies;
import zteam.characters.GameCharacter;

public class skeletonBase extends GameCharacter {

    /**
     * Creates a skeleton with its default combat attributes.
     * @param name the character's name
     */
    public skeletonBase(String name) {
        this(name, 80, 18, 12);
    }

    /**
     * Creates a skeleton variation with specific combat attributes.
     *
     * @param name the character's name
     * @param maxHealth the maximum health
     * @param attackPower the normal attack power
     * @param defense the damage reduction
     * @throws IllegalArgumentException if the name or attributes
     *         are invalid
     */
    protected skeletonBase(String name, int maxHealth,
                       int attackPower, int defense) {
        super(name, maxHealth, attackPower, defense);
    }

    /**
     * Uses Bone to attack with twice the normal attack power.
     *
     * @param target the character receiving the attack
     * @return the actual health removed from the target
     * @throws IllegalArgumentException if the target is null
     *         or is the skeleton itself
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
                    "A skeleton cannot attack itself."
            );
        }

        if (!isAlive()) {
            throw new IllegalStateException(
                    "Defeated characters cannot use abilities."
            );
        }

        int damage = getAttack() * 2;

        return target.takeDamage(damage);
    }
}


