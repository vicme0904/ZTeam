package zteam.characters.enemies;
import zteam.characters.GameCharacter;
import zteam.game.Battle;
public class necromancer extends GameCharacter{

    /** Maximum mana */
    private static final int MAX_MANA = 100;
    /** Mana amount for casting */
    private static final int MANA_COST = 50;
    /** Skeletons per cast */
    private static final int SKELETONS_SUMMONED = 3;
    /** The battle receiving the summoned skeletons.*/
    private final Battle battle;
    /** The remaining mana. */
    private int currMana;
    /** The total summoned, used to give skeletons distinct names. */
    private int totalSummoned;


    /**
     * Creates a necromancer.
     * @param name the character's name
     * @param battle the battle receiving its summoned skeletons
     * @throws IllegalArgumentException if the name is invalid
     *         or the battle is null
     */
    public necromancer(String name, Battle battle) {
        super(name, 120, 20, 10);

        if (battle == null) {
            throw new IllegalArgumentException(
                    "The necromancer must be in a battle."
            );
        }

        this.battle = battle;
        this.currMana = MAX_MANA;
        this.totalSummoned = 0;
    }

    /**
     * Getter for remaining mana.
     * @return the current mana
     */
    public int getCurrentMana() {
        return currMana;
    }

    /**
     * Getter for maximum mana.
     * @return the maximum mana
     */
    public int getMaxMana() {
        return MAX_MANA;
    }

    /**
     * Spends mana to add three skeletons to battle.
     * @param target unused because summoning requires no target
     * @return the number of skeletons summoned
     * @throws IllegalStateException if the necromancer is defeated,
     *         or is not registered in battle, or the party is defeated,
     *         or there is not enough mana
     */
    @Override
    public int useSpecialAbility(GameCharacter target) {
        if (!isAlive()) {
            throw new IllegalStateException("Defeated characters cannot use abilities.");
        }

        if (!battle.getEnemies().contains(this)) {
            throw new IllegalStateException("The necromancer must belong to its battle.");
        }

        if (battle.getParty().isDefeated()) {
            throw new IllegalStateException("The player party is already defeated.");
        }

        if (currMana < MANA_COST) {
            throw new IllegalStateException(
                    "Not enough mana to summon skeletons."
            );
        }

        for (int i = 0; i < SKELETONS_SUMMONED; i++) {
            int skeletonNumber = totalSummoned + 1;

            skeletonBase skeleton = new skeletonBase(
                    getName() + " - Skeleton " + skeletonNumber
            );

            battle.addEnemy(skeleton);
            totalSummoned++;
        }

        currMana -= MANA_COST;

        return SKELETONS_SUMMONED;
    }
}
