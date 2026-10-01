package zteam.game;
import zteam.characters.GameCharacter;

import java.util.ArrayList;
import java.util.List;

public class Party {

    // Max size party
    private static final int MAX_MEMBERS = 4;

    // What gets to be in the party
     private final List<GameCharacter> members;

     // Creates empty party
    public Party() {
        members = new ArrayList<>();
    }


    /**
     * Adds a character to the party.
     * @param member the character to add
     * @throws IllegalArgumentException if the character is null
     *         or already belongs to this party
     * @throws IllegalStateException if the party is full
     */
    public void addMember(GameCharacter member) {
        if (member == null) {
            throw new IllegalArgumentException("The party member cannot be null.");
        }
        if (members.contains(member)) {
            throw new IllegalArgumentException("This character already belongs to the party.");
        }
        if (members.size() >= MAX_MEMBERS) {
            throw new IllegalStateException("The party is full.");
        }

        members.add(member);
    }

    /**
     * Returns the number of party members.
     * @return the number of members
     */
    public int getSize() {
        return members.size();
    }

    /**
     * Returns a read-only snapshot of all party members.
     * @return the party members
     */
    public List<GameCharacter> getMembers() {
        return List.copyOf(members);
    }

    /**
     * Returns a read-only snapshot of the living party members.
     * @return the characters whose health is greater than zero
     */
    public List<GameCharacter> getAliveMembers() {
        List<GameCharacter> aliveMembers = new ArrayList<>();
        for (GameCharacter member : members) {
            if (member.isAlive()) {
                aliveMembers.add(member);
            }
        }

        return List.copyOf(aliveMembers);
    }

    /**
     * Checks whether the party has no living members.
     * An empty party also returns true.
     * @return true if there are no living members
     */
    public boolean isDefeated() {
        return getAliveMembers().isEmpty();
    }
}
