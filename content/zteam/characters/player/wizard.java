package zteam.characters.player;
import zteam.characters.GameCharacter;

public class wizard extends GameCharacter {

    // Max mana
    private static final int MAX_MANA = 100;

    // Fireball cost
    private static final int FIREBALL_COST = 20;

    // Current mana
    private int currMana;

    //Constructor
    /**
     * Creates a wizard with fixed attributes and full health.
     *
     * @param name the archer's name
     * @throws IllegalArgumentException if the name is null or blank
     */
    public wizard(String name) {
        super(name, 80, 30, 6);
        currMana = MAX_MANA;
    }

    /**
     * Returns the current mana.
     *
     * @return the current mana
     */
    public int getCurrentMana() {
        return currMana;
    }

    /**
     * Returns the maximum mana.
     *
     * @return the maximum mana
     */
    public int getMaxMana() {
        return MAX_MANA;
    }

    /**
     * Casts a fireball with three times the base attack power.
     *
     * @param target the character receiving the fireball
     * @return the health actually removed from the target
     * @throws IllegalArgumentException if the target is null or this mage
     * @throws IllegalStateException if either character is defeated
     *         or the mage has insufficient mana
     */

    @Override
    public int useSpecialAbility(GameCharacter target) {
        if (target == null) {
            throw new IllegalArgumentException("The special ability must have a target.");
        }
        if (target == this) {
            throw new IllegalArgumentException("A mage cannot attack itself.");
        }
        if (!isAlive()) {
            throw new IllegalStateException("Defeated characters cannot use abilities.");
        }
        if (currMana < FIREBALL_COST) {
            throw new IllegalStateException("Not enough mana to cast Fireball.");
        }

        int damageDealt = target.takeDamage(getAttack() * 2);

        currMana -= FIREBALL_COST;

        return damageDealt;
    }
}

