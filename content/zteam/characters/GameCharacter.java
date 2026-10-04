package zteam.characters;

public abstract class GameCharacter {

private final String name;
private final int maxHealth;
private int currHealth;
private final int attack;
private final int defense;

/** Constructor for GameCharacter
 * @param name the character's name
 * @param maxHealth the maximum health, greater than zero
 * @param attack is the attack, zero or greater
 * @param defense the defense value, zero or greater
 * @throws IllegalArgumentException if any attribute is invalid
 */
protected GameCharacter(String name, int maxHealth, int attack, int defense){
    if (name == null || name.isBlank()) {
        throw new IllegalArgumentException("Character name cannot be empty.");
    }
    if (maxHealth <= 0) {
        throw new IllegalArgumentException("Maximum health must be greater than zero");
    }
    if (attack < 0 || defense < 0){
        throw new IllegalArgumentException("Attack nor defense cannot be negative.");
    }

    this.name = name.trim();
    this.maxHealth = maxHealth;
    this.currHealth = maxHealth;
    this.attack = attack;
    this.defense = defense;
}

// getters here
/**
 * @return the character's name
 */
public String getName(){
    return name;
}

/**
 * @return the maximum health
 */
public int getMaxHealth(){
    return maxHealth;
}

/**
 * @return the current health
 */
    public int getCurrHealth(){
    return currHealth;
}

/**
 * @return the defense value
 */
public int getDefense(){
    return defense;
}

/**
 * @return the attack power
 */
public int getAttack(){
    return attack;
}

/**
 * @return true if current health is greater than zero;
 * false otherwise
 */
public boolean isAlive(){
    return currHealth > 0;
}

//do some ruling to damage and health
    /**
     * @param incomingDamage the damage before defense, zero or greater
     * @return the health actually lost
     * @throws IllegalArgumentException if the damage is negative
     * @throws IllegalStateException if the character is defeated
     */
    public int takeDamage(int incomingDamage) {
        if (incomingDamage < 0) {
            throw new IllegalArgumentException(
                    "Damage cannot be negative."
            );
        }
        if (!isAlive()) {
            throw new IllegalStateException(
                    "The character is already defeated."
            );
        }
        int damageAfterDefense = Math.max(0, incomingDamage - defense);
        int actualDamage = Math.min(currHealth, damageAfterDefense);
        currHealth -= actualDamage;
        return actualDamage;
    }

    /**
     * @param amount the healing amount, zero or greater
     * @return the health actually restored
     * @throws IllegalArgumentException if the amount is negative
     * @throws IllegalStateException if the character is defeated
     */
    public int heal(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(
                    "Healing amount cannot be negative."
            );
        }

        if (!isAlive()) {
            throw new IllegalStateException(
                    "Defeated characters cannot be healed."
            );
        }

        int missingHealth = maxHealth - currHealth;
        int actualHealing = Math.min(amount, missingHealth);

        currHealth += actualHealing;

        return actualHealing;
    }

// Attack and unique attack
    /**
     * @param target the character receiving the attack
     * @return the health actually removed from the target
     * @throws IllegalArgumentException if the target is null or this character
     * @throws IllegalStateException if either character is defeated
     */
    public int attack(GameCharacter target) {
        if (target == null) {
            throw new IllegalArgumentException(
                    "The attack must have a target."
            );
        }
        if (target == this) {
            throw new IllegalArgumentException(
                    "A character cannot attack itself."
            );
        }
        if (!isAlive()) {
            throw new IllegalStateException(
                    "Defeated characters cannot attack."
            );
        }
        return target.takeDamage(attack);
    }

    /**
     * @param target the character affected by the ability
     * @return the actual damage dealt or health restored or number of summons
     * @throws IllegalArgumentException if the target is invalid
     * @throws IllegalStateException if either character is defeated
     */
    public abstract int useSpecialAbility(GameCharacter target);
}


