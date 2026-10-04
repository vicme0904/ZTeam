package zteam.game;
import zteam.characters.GameCharacter;
import java.util.ArrayList;
import java.util.List;

public class Battle {

    // Player's party
    private final Party party;

    // Enemies in battle
    private final List<GameCharacter> enemies;

    //Constructor
    /**
     * Creates a battle with a player party and an empty enemy list.
     * @param party the player's party
     * @throws IllegalArgumentException if the party is null
     *         or has no living members
     */
    public Battle(Party party) {
        if (party == null) {
            throw new IllegalArgumentException(
                    "The battle must have a player party."
            );
        }

        if (party.isDefeated()) {
            throw new IllegalArgumentException(
                    "The party must have at least one living member."
            );
        }

        this.party = party;
        this.enemies = new ArrayList<>();
    }

    /**
     * Party getter
     * @return the party participating in this battle
     */
    public Party getParty() {
        return party;
    }

    /**
     * Adds enemy to the battle.
     * @param enemy the character joining the enemy group
     * @throws IllegalArgumentException if the enemy is null,
     *         defeated, already registered, or a party member
     */
    public void addEnemy(GameCharacter enemy) {
        if (enemy == null) {
            throw new IllegalArgumentException("The enemy cannot be null.");
        }

        if (!enemy.isAlive()) {
            throw new IllegalArgumentException("A defeated enemy cannot join the battle.");
        }

        if (enemies.contains(enemy)) {
            throw new IllegalArgumentException("This enemy already belongs to the battle.");
        }

        if (party.getMembers().contains(enemy)) {
            throw new IllegalArgumentException("A party member cannot join the enemy group.");
        }

        enemies.add(enemy);
    }

    /**
     * Getter for all enemies
     * @return the enemies participating in battle
     */
    public List<GameCharacter>getEnemies(){
        return List.copyOf(enemies);
    }

    /**
     * Read-only getter of enimies alive
     * @return the enemies that are still alive
     */
    public List<GameCharacter>getAliveEnemies(){
        List<GameCharacter> aliveEnemies = new ArrayList<>();
        for (GameCharacter enemy : enemies) {
            if (enemy.isAlive()) {
                aliveEnemies.add(enemy);
            }
        }

        return List.copyOf(aliveEnemies);
    }
}
