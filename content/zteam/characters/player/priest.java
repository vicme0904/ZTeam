package zteam.characters.player;
import zteam.characters.GameCharacter;

public class priest extends GameCharacter {

    // Max mana
    private static final int MAX_MANA = 100;

    // Heal cost
    private static final int HEALING_COST = 20;

    // Heal amount
    private static final int HEALING_AMOUNT = 40;

    // Curent mana
    private int currMana;

    //Constructor
    /**
     * Creates a wizard  with fixed attributes and full health.
     *
     * @param name the archer's name
     * @throws IllegalArgumentException if the name is null or blank
     */

    public priest(String name){
        super(name, 100, 12, 8);
        currMana = MAX_MANA;
    }

/**
 * Returns the current mana.
 *
 * @return the current mana
 */
public int getCurrMana() {
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
 * Restores up to forty health to a living character.
 *
 * @param target the character receiving the healing
 * @return the health actually restored
 * @throws IllegalArgumentException if the target is null
 * @throws IllegalStateException if either character is defeated,
 *         the target has full health, or mana is insufficient
 */

    @Override
    public int useSpecialAbility(GameCharacter target) {
    if (target == null) {
        throw new IllegalArgumentException("The healing ability must have a target.");
    }
    if (!isAlive()) {
        throw new IllegalStateException("Defeated characters cannot use abilities.");
    }
    if (target.getCurrHealth() == target.getMaxHealth()) {
        throw new IllegalStateException("The target already has full health.");
    }
    if (currMana < HEALING_COST) {
        throw new IllegalStateException("Not enough mana to heal.");
    }

    int restoredHealth = target.heal(HEALING_AMOUNT);
    currMana -= HEALING_COST;

    return restoredHealth;
}

}
