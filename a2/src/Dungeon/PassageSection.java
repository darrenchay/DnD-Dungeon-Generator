package src.Dungeon;

import dnd.die.D20;
import dnd.models.Monster;

import java.util.HashMap;

/* Represents a 10 ft section of passageway */

public class PassageSection {
    /**
     * Stores the passage section it is.
     **/
    private HashMap<Integer, String> passage = new HashMap<Integer, String>();
    /**
     * Stores the monster in section.
     **/
    private Monster monster;
    /**
     * Stores the door in section.
     **/
    private Door door;
    /**
     * Stores the description of the passage section.
     **/
    private String description;
    /**
     * Boolean whether section has door.
     **/
    private boolean hasDoor = false;
    /**
     * Boolean whether section has monster.
     **/
    private boolean hasMonster = false;
    /**
     * Boolean whether section is a dead end.
     **/
    private boolean isDeadEnd = false;
    /**
     * Boolean whether section is the end of a passage.
     **/
    private boolean endOfPassage = false;

    /*
    Required Methods for that we will test during grading
    note:  Some of these methods would normally be protected or private, but because we
    don't want to dictate how you set up your packages we need them to be public
    for the purposes of running an automated test suite (junit) on your code.  */
    /**
     * Creates a passage section object with no params.
     **/
    public PassageSection() {
        //sets up the 10 foot section with default settings
        setUpPassage();
        createPassage();
    }

    /**
     * Creates a passage section object with a description.
     * @param desc description
     **/
    public PassageSection(String desc) {
        setUpPassage();

        //TO EDIT
        if (desc.toLowerCase().contains("monster")) {
            createPassage(20);
        } else if (desc.toLowerCase().contains("door")) {
            if (desc.toLowerCase().contains("archway")) {
                createPassage(8);
            } else {
                createPassage(14);
            }
        } else {
            createPassage(1);
        }
        this.description = desc;
        //sets up a specific passage based on the values sent in from
        //modified table 1
    }

    /**
     * Gets the door of the section.
     * @return door
     **/
    public Door getDoor() {
        //returns the door that is in the passage section, if there is one
        if (hasDoor) {
            return door;
        } else {
            return null;
        }
    }

    /**
     * Gets the monster in the section.
     * @return monster
     **/
    public Monster getMonster() {
        //returns the monster that is in the passage section, if there is one
        if (hasMonster) {
            return monster;
        } else {
            return null;
        }
    }

    /**
     * Gets the description of the section.
     * @return description
     **/
    public String getDescription() {
        return description;
    }

    /*
     * Helper functions
     */

    /**
     * Edit the description of this section (for linking).
     * @param desc description
     **/
    public void editDescription(String desc) {
        description += desc;
    }

    /**
     * Adds a monster to the passage section.
     * @param theMonster monster
     **/
    public void addMonster(Monster theMonster) {
        monster = theMonster;
        hasMonster = true;
        description = "There are " + monster.getMinNum() + " to " + monster.getMaxNum() + " " + monster.getDescription() + "s in the passage (passage continues straight for 10 ft)";
    }

    /**
     * Returns boolean hasDoor.
     * @return hasDoor
     **/
    public boolean hasDoor() {
        return hasDoor;
    }

    /**
     * Returns boolean isDeadEnd.
     * @return isDeadEnd
     **/
    public boolean isDeadEnd() {
        return isDeadEnd;
    }

    /**
     * Returns boolean endOfPassage.
     * @return endOfPassage
     **/
    public boolean isEndOfPassage() {
        return endOfPassage;
    }

    private void createPassage(int... i) {
        D20 die = new D20();
        int roll = die.roll();
        if (i.length > 0) {
            roll = i[0];
        }
        if (roll == 20) {
            monster = new Monster();
            monster.setType();
            description = "There are " + monster.getMinNum() + " to " + monster.getMaxNum() + " " + monster.getDescription() + "s in the passage (passage continues straight for 10 ft)";
            hasMonster = true;
        } else if ((roll > 2 && roll < 10) || (roll > 13 && roll < 17)) {
            door = new Door();
            if (roll > 5 && roll < 10 || roll > 13 && roll < 17) {
                door.setArchway(true);
            }
            if (roll > 2 && roll < 6 || roll > 13 && roll < 17) {
                endOfPassage = true;
            }
            description = passage.get(roll);
            hasDoor = true;
        } else if (roll == 17) {
            //add stairs
            description = passage.get(roll);
        } else if (roll == 18 || roll == 19) {
            description = passage.get(roll);
            isDeadEnd = true;
        } else {
            description = passage.get(roll);
        }
    }

    private void setUpPassage() {
        this.passage.put(1, "passage goes straight for 10 ft");
        this.passage.put(2, "passage goes straight for 10 ft");
        this.passage.put(3, "passage ends in Door to a Chamber");
        this.passage.put(4, "passage ends in Door to a Chamber");
        this.passage.put(5, "passage ends in Door to a Chamber");
        this.passage.put(6, "archway (door) to right (main passage continues straight for 10 ft)");
        this.passage.put(7, "archway (door) to right (main passage continues straight for 10 ft)");
        this.passage.put(8, "archway (door) to left (main passage continues straight for 10 ft)");
        this.passage.put(9, "archway (door) to left (main passage continues straight for 10 ft)");
        this.passage.put(10, "passage turns to left and continues for 10 ft");
        this.passage.put(11, "passage turns to left and continues for 10 ft");
        this.passage.put(12, "passage turns to right and continues for 10 ft");
        this.passage.put(13, "passage turns to right and continues for 10 ft");
        this.passage.put(14, "passage ends in archway (door) to chamber");
        this.passage.put(15, "passage ends in archway (door) to chamber");
        this.passage.put(16, "passage ends in archway (door) to chamber");
        this.passage.put(17, "Stairs, (passage continues straight for 10 ft)");
        this.passage.put(18, "Dead End");
        this.passage.put(19, "Dead End");
        this.passage.put(20, "Wandering Monster (passage continues straight for 10 ft)");
    }
}
