package src.Dungeon;

import dnd.die.Die;
import dnd.models.Monster;

import java.util.HashMap;

public class PassageSection {
    /**
     * Stores the passage section it is.
     **/
    private HashMap<Integer, String> passageSection = new HashMap<Integer, String>();
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
    /**
     * Int for roll.
     **/
    private int roll;


    /**
     * Creates a 10ft passage section object with no params.
     **/
    public PassageSection() {
        roll = Die.d20();
        setUpPassageSection();
        createPassageSection();
        createSectionDesc();
    }

    /**
     * Creates a specific 10ft passage section object with a roll param.
     *
     * @param rollVal val between 1-20
     **/
    public PassageSection(int rollVal) {
        roll = rollVal;
        setUpPassageSection();
        createPassageSection();
        createSectionDesc();
    }

    /**
     * Gets the door of the section.
     *
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
     *
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
     *
     * @return description
     **/
    public String getDescription() {
        return description;
    }

    /**
     * Edit the description of this section (for linking).
     *
     * @param desc description
     **/
    public void editDescription(String desc) {
        description += desc;
    }

    /**
     * Returns boolean isDeadEnd.
     *
     * @return isDeadEnd
     **/
    public boolean isDeadEnd() {
        return isDeadEnd;
    }

    /**
     * Returns boolean endOfPassage.
     *
     * @return endOfPassage
     **/
    public boolean isEndOfPassage() {
        return endOfPassage;
    }

    /**
     * Returns boolean hasDoor.
     *
     * @return hasDoor
     **/
    public boolean hasDoor() {
        return this.hasDoor;
    }

    /**
     * Private Methods.
     **/

    private void addMonster() {
        monster = new Monster();
        monster.setType(Die.percentile());
        hasMonster = true;
    }

    private void addDoor() {
        door = new Door();
        if (roll > 5 && roll < 10 || roll > 13 && roll < 17) {
            door.setArchway(true);
        }
        if (roll > 2 && roll < 6 || roll > 13 && roll < 17) {
            endOfPassage = true;
        }
        hasDoor = true;
    }

    private void createPassageSection() {
        if (roll == 20) {
            addMonster();
        } else if ((roll > 2 && roll < 10) || (roll > 13 && roll < 17)) {
            addDoor();
        } else if (roll == 18 || roll == 19) {
            isDeadEnd = true;
        }
    }

    private void createSectionDesc() {
        if (hasMonster) {
            description = "There are " + monster.getMinNum() + " to " + monster.getMaxNum() + " " + monster.getDescription() + "s in the passage (passage continues straight for 10 ft)";
        } else {
            description = passageSection.get(roll);
        }
    }

    private void setUpPassageSection() {
        this.passageSection.put(1, "passage goes straight for 10 ft");
        this.passageSection.put(2, "passage goes straight for 10 ft");
        this.passageSection.put(3, "passage ends in Door to a Chamber");
        this.passageSection.put(4, "passage ends in Door to a Chamber");
        this.passageSection.put(5, "passage ends in Door to a Chamber");
        this.passageSection.put(6, "archway (door) to right (main passage continues straight for 10 ft)");
        this.passageSection.put(7, "archway (door) to right (main passage continues straight for 10 ft)");
        this.passageSection.put(8, "archway (door) to left (main passage continues straight for 10 ft)");
        this.passageSection.put(9, "archway (door) to left (main passage continues straight for 10 ft)");
        this.passageSection.put(10, "passage turns to left and continues for 10 ft");
        this.passageSection.put(11, "passage turns to left and continues for 10 ft");
        this.passageSection.put(12, "passage turns to right and continues for 10 ft");
        this.passageSection.put(13, "passage turns to right and continues for 10 ft");
        this.passageSection.put(14, "passage ends in archway (door) to chamber");
        this.passageSection.put(15, "passage ends in archway (door) to chamber");
        this.passageSection.put(16, "passage ends in archway (door) to chamber");
        this.passageSection.put(17, "Stairs, (passage continues straight for 10 ft)");
        this.passageSection.put(18, "Dead End");
        this.passageSection.put(19, "Dead End");
        this.passageSection.put(20, "Wandering Monster (passage continues straight for 10 ft)");
    }
}
