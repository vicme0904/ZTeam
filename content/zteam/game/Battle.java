package zteam.game;
import zteam.characters.GameCharacter;
import zteam.characters.player.*;
import zteam.characters.enemies.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Battle {

    // Player's party
    private final Party party;
    // Enemies in battle
    private final List<GameCharacter> enemies;
    /** The characters scheduled for the current round. */
    private final List<GameCharacter> turnOrder;
    /** The position of the current character in the turn order. */
    private int currentTurnIndex;
    /** The current round number, or zero before the battle starts. */
    private int roundNumber;
    /** Random choices for enemies actions */
    private final Random enemyAction = new Random();

    //Constructor
    /**
     * Creates a battle with a player party and an empty enemy list.
     * @param party the player's party
     * @throws IllegalArgumentException if the party is null
     *         or has no living members
     */
    public Battle(Party party) {
        if (party == null) {
            throw new IllegalArgumentException("The battle must have a player party.");
        }

        if (party.isDefeated()) {
            throw new IllegalArgumentException("The party must have at least one living member.");
        }

        this.party = party;
        this.enemies = new ArrayList<>();
        this.turnOrder = new ArrayList<>();
        this.currentTurnIndex = 0;
        this.roundNumber = 0;
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
     * @throws IllegalStateException if the battle has already finished
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
        if (isFinished()) {
            throw new IllegalStateException("Enemies cannot join a finished battle.");
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

    // Turn order specific
    /**
     * Starts the battle's first round.
     *
     * @throws IllegalStateException if the battle has already started
     *         or either side has no living characters
     */
    public void start() {
        if (roundNumber > 0) {
            throw new IllegalStateException("The battle has already started.");
        }

        if (party.isDefeated() || getAliveEnemies().isEmpty()) {
            throw new IllegalStateException("Both sides must have living characters.");
        }

        beginRound();
    }

    /**
     * Getter for current round number.
     *
     * @return the current round, or zero before the battle starts
     */
    public int getRoundNumber() {
        return roundNumber;
    }

    /**
     * Checks whether a started battle has ended.
     *
     * @return true if either side has no living characters
     *         after the battle starts
     */
    public boolean isFinished() {
        return roundNumber > 0 && (party.isDefeated() || getAliveEnemies().isEmpty());
    }

    /**
     * Getter for character owner of the turn
     *
     * @return the character that can act
     * @throws IllegalStateException if the battle has not started
     *         or has already finished
     */
    public GameCharacter getCurrentCharacter() {
        if (roundNumber == 0) {
            throw new IllegalStateException("The battle has not started.");
        }

        if (isFinished()) {
            throw new IllegalStateException("The battle has already finished.");
        }

        skipDefeatedCharacters();

        return turnOrder.get(currentTurnIndex);
    }

    /**
     * Checks whether the current turn belongs to the player's party.
     *
     * @return true for a party member's turn, false for an enemy's turn
     * @throws IllegalStateException if the battle has not started
     *                               or has already finished
     */
    public boolean isPlayerTurn() {
        GameCharacter character = getCurrentCharacter();

        return party.getMembers().contains(character);
    }

    /**
     * Ends the current turn and advances to the next character.
     * Starts a new round after the last turn.
     * Has no effect after the battle finishes.
     * @throws IllegalStateException if the battle has not started
     */
    public void endTurn() {
        if (roundNumber == 0) {
            throw new IllegalStateException("The battle has not started.");
        }

        if (isFinished()) {
            return;
        }

        currentTurnIndex++;
        skipDefeatedCharacters();
    }

    /**
     * Get the characters for a new round.
     */
    private void beginRound() {
        turnOrder.clear();

        turnOrder.addAll(party.getAliveMembers());
        turnOrder.addAll(getAliveEnemies());

        currentTurnIndex = 0;
        roundNumber++;
    }

    /**
     * Skips defeated characters and begins another round when necessary.
     */
    private void skipDefeatedCharacters() {
        while (currentTurnIndex < turnOrder.size() && !turnOrder.get(currentTurnIndex).isAlive()) {
            currentTurnIndex++;
        }

        if (currentTurnIndex >= turnOrder.size()) {
            beginRound();
        }
    }
    /**
     * Getter for living targets allowed for the current character's ability.
     * @return allies for healing, opponents for damage,
     *         or an empty list for summoning
     * @throws IllegalStateException if the battle is not active
     */
    public List<GameCharacter> getSpecialAbilityTargets() {
        GameCharacter character = getCurrentCharacter();

        if (character instanceof necromancer) {
            return List.of();
        }

        boolean partyMember = party.getMembers().contains(character);

        if (character instanceof priest) {
            if (partyMember) {
                return party.getAliveMembers();
            }

            return getAliveEnemies();
        }

        if (partyMember) {
            return getAliveEnemies();
        }

        return party.getAliveMembers();
    }

    /**
     * Uses the current character's ability and advances the turn on success.
     * @param target the affected character, or null for summoning
     * @return actual damage, healing, or number of summons
     * @throws IllegalArgumentException if the target is invalid
     * @throws IllegalStateException if the battle is not active
     *                               or the ability cannot be used
     */
    public int useSpecialAbility(GameCharacter target) {
        GameCharacter character = getCurrentCharacter();

        if (character instanceof necromancer) {
            if (target != null) {
                throw new IllegalArgumentException("Summoning does not use a target.");
            }
        } else if (target == null || !getSpecialAbilityTargets().contains(target)) {
            throw new IllegalArgumentException("Choose a valid living target for this ability.");
        }

        int amount = character.useSpecialAbility(target);

        endTurn();

        return amount;
    }

    /**
     * Executes the current enemy's turn automatically.
     * A necromancer attempts to summon; other enemies randomly choose between a special ability and a normal attack.
     * An unavailable ability falls back to a normal attack.
     *
     * @return a message describing the action and its result
     * @throws IllegalStateException if the battle is not active
     *                               or the current turn belongs to the party
     */
    public String performEnemyTurn() {
        GameCharacter enemy = getCurrentCharacter();

        if (party.getMembers().contains(enemy)) {
            throw new IllegalStateException(
                    "It is not an enemy's turn."
            );
        }

        List<GameCharacter> targets = party.getAliveMembers();

        GameCharacter attackTarget =
                targets.get(enemyAction.nextInt(targets.size()));

        if (enemy instanceof necromancer || enemyAction.nextBoolean()) {
            List<GameCharacter> specialTargets = getSpecialAbilityTargets();

            GameCharacter specialTarget = specialTargets.isEmpty()
                    ? null
                    : specialTargets.get(enemyAction.nextInt(specialTargets.size()));

            try {
                int amount = useSpecialAbility(specialTarget);

                if (enemy instanceof necromancer) {
                    return enemy.getName() + " summoned " + amount + " skeletons.";
                }

                if (enemy instanceof priest) {
                    return enemy.getName() + " restored " + amount + " HP to " + specialTarget.getName() + ".";
                }

                String message = enemy.getName() + " used a special ability on " + specialTarget.getName() + " for " + amount + " damage.";

                if (!specialTarget.isAlive()) {
                    message += "\n" + specialTarget.getName() + " was defeated.";
                }

                return message;
            } catch (IllegalStateException exception) {
                return performEnemyAttack(enemy, attackTarget);
            }
        }

        return performEnemyAttack(enemy, attackTarget);
    }

    /**
     * Makes the current character attack a living opponent and advances the turn after a successful attack.
     *
     * @param target the opponent to attack
     * @return the actual damage dealt
     * @throws IllegalArgumentException if the target is invalid
     * @throws IllegalStateException if the battle is not active
     */
    public int attack(GameCharacter target) {
        GameCharacter attacker = getCurrentCharacter();

        List<GameCharacter> opponents;

        if (party.getMembers().contains(attacker)) {
            opponents = getAliveEnemies();
        } else {
            opponents = party.getAliveMembers();
        }

        if (target == null || !opponents.contains(target)) {
            throw new IllegalArgumentException("The target must be a living opponent.");
        }

        int damage = attacker.attack(target);

        endTurn();

        return damage;
    }

    /**
     * Executes a normal enemy attack and describes the result.
     *
     * @param enemy the current enemy
     * @param target the living party member to attack
     * @return a message describing damage and defeat
     */
    private String performEnemyAttack(GameCharacter enemy, GameCharacter target) {
        int damage = attack(target);

        String message = enemy.getName() + " attacked " + target.getName() + " for " + damage + " damage.";

        if (!target.isAlive()) {
            message += "\n" + target.getName() + " was defeated.";
        }

        return message;
    }

}
