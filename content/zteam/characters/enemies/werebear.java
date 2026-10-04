package zteam.characters.enemies;
import zteam.characters.GameCharacter;

public class werebear extends GameCharacter {

    //Constructor
    /**
     * Creates a werebear with its initial combat attributes.
     * @param name the enemy's name
     */
    public werebear(String name){
        super(name, 180, 15, 12);
    }

    @Override
    public int useSpecialAbility(GameCharacter target) {
        if (target == null) {
            throw new IllegalArgumentException("Must have a target.");
        }
        if (!isAlive()) {
            throw new IllegalArgumentException("Defeated characters cannot use abilities.");
        }
        if (target == this){
            throw new IllegalArgumentException("It cannot attack itself");
        }

       int damage = getAttack() * 2;
       return target.takeDamage(damage);
    }
}
