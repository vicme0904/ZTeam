package zteam.characters.player;
import zteam.characters.GameCharacter;

public class archer extends GameCharacter {

    //Constructor
    /**
     * Creates an archer with fixed attributes and full health.
     *
     * @param name the archer's name
     * @throws IllegalArgumentException if the name is null or blank
     */
    public archer(String name){
        super(name, 100, 30, 10);
    }

    /**
     * Attacks twice, if the target survives the first shot.
     *
     * @param target the character receiving the shots
     * @return the total health actually removed from the target
     * @throws IllegalArgumentException if the target is null or this archer
     * @throws IllegalStateException if either character is defeated
     */
    @Override
    public int useSpecialAbility(GameCharacter target) {
        int totalDamage = attack(target);

        if (target.isAlive()) {
            totalDamage += attack(target);
        }

        return totalDamage;
    }

}
