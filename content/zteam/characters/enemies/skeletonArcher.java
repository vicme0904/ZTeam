package zteam.characters.enemies;

import zteam.characters.GameCharacter;

public class skeletonArcher extends skeletonBase  {
    /**
     * Creates a skeleton archer with its initial combat attributes.
     * @param name the character's name
     */
public skeletonArcher(String name) {
    super(name, 70, 20, 10);
}

/**
 * Attacks the target twice if it survives the first arrow.
 * The target's defense applies separately to each arrow.
 *
 * @param target the character receiving the attacks
 * @return the total health removed from the target
 * @throws IllegalArgumentException if the target is null
 *         or is the skeleton archer itself
 * @throws IllegalStateException if either character is
 *         defeated before the first attack
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
