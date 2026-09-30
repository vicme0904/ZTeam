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
}


