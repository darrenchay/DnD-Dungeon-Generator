package dungeon;

import dnd.models.Monster;
import dnd.models.Treasure;

import java.util.ArrayList;
import java.util.HashMap;
/*
A passage begins at a door and ends at a door.  It may have many other doors along
the way

You will need to keep track of which door is the "beginning" of the passage
so that you know how to
*/

public class Passage extends Space implements java.io.Serializable {

    /**
     * Stores the whole array of sections (passage).
     **/
    private ArrayList<PassageSection> thePassage;
    /**
     * Stores the doors in passage.
     **/
    private ArrayList<Door> doors;
    /**
     * Stores the num of passage sections.
     **/
    private int passageSize = 0;
    /**
     * Stores the description of the passage.
     **/
    private String description = "";
    /**
     * Contains the monsters.
     **/
    private ArrayList<Monster> monsters;
    /**
     * Contains the treasures.
     **/
    private ArrayList<Treasure> treasures;

    /**
     * List of treasures.
     **/
    private HashMap<Treasure, Integer> treasureMap;

    /**
     * List of monsters.
     **/
    private HashMap<Monster, Integer> monsterMap;


    /**
     * Creates a passage object.
     **/
    public Passage() {
        thePassage = new ArrayList<PassageSection>();
        doors = new ArrayList<Door>();
        monsters = new ArrayList<>();
        treasures = new ArrayList<>();
        treasureMap = new HashMap<>();
        monsterMap = new HashMap<>();
    }

    /**
     * Creates a passage object with the connecting door.
     *
     * @param door entrance door
     **/
    public Passage(Door door) {
        thePassage = new ArrayList<PassageSection>();
        doors = new ArrayList<Door>();
        addDoor(door);
    }

    /**
     * Returns the list of doors.
     *
     * @return addedDoors
     **/
    public ArrayList<Door> getDoors() {
        return doors;
    }

    /**
     * Gets door object at index i in array.
     *
     * @param i index
     * @return door object
     **/
    public Door getDoor(int i) {
        //returns the door in section 'i'. If there is no door, returns null
        if (i >= doors.size() || doors.get(i) == null) {
            return null;
        } else {
            return doors.get(i);
        }
    }

    /**
     * Adds a passage section.
     *
     * @param toAdd passage section
     **/
    public void addPassageSection(PassageSection toAdd) {
        //adds the passage section to the passageway
        if (toAdd != null) {
            thePassage.add(toAdd);
            passageSize++;
        }
        /* Adds door of passage section if it has one */
        if (toAdd.hasDoor()) {
            addDoor(toAdd.getDoor());
        }
    }

    /**
     * Adds a door to the passage section.
     *
     * @param newDoor new door
     **/
    @Override
    public void addDoor(Door newDoor) {
        //should add a door connection to the current Passage Section
        newDoor.addSpace(this);
        doors.add(newDoor);
    }

    /**
     * Adds a monster to passage.
     *
     * @param roll the monster
     **/
    public void addMonster(int roll) {
        int section = 0;
        int flag = 0;
        for (int i = 0; i < passageSize; i++) {
            if (!getPassageSection(i).isHasMonster()) {
                getPassageSection(i).addMonster(roll);
                section = i;
                flag = 1;
                break;
            }
        }
        if (flag == 0) {
            getPassageSection(0).addMonster(roll);
        }
        monsters.add(getPassageSection(section).getMonster());
        monsterMap.put(getPassageSection(section).getMonster(), section);

    }

    /**
     * Removes monster in passage section.
     *
     * @param index index
     **/
    public void removeMonster(int index) {
        getPassageSection(monsterMap.get(monsters.get(index))).removeMonster();
        monsterMap.remove(monsters.get(index));
        monsters.remove(index);
    }

    /**
     * Gets all monsters.
     *
     * @return monsters
     **/
    public ArrayList<Monster> getMonsters() {
        return monsters;
    }


    /**
     * Adds a treasure.
     *
     * @param treasureRoll the treasure
     **/
    public void addTreasure(int treasureRoll) {
        int section = 0;
        int flag = 0;
        for (int i = 0; i < passageSize; i++) {
            if (!getPassageSection(i).isHasTreasure()) {
                getPassageSection(i).addTreasure(treasureRoll);
                section = i;
                flag = 1;
                break;
            }
        }
        if (flag == 0) {
            getPassageSection(0).addTreasure(treasureRoll);
        }
        treasures.add(getPassageSection(section).getTreasure());
        treasureMap.put(getPassageSection(section).getTreasure(), section);

    }

    /**
     * Returns treasures.
     *
     * @return treasures
     **/
    public ArrayList<Treasure> getTreasures() {
        return treasures;
    }

    /**
     * Removes treasures.
     *
     * @param index treasureIdx
     **/
    public void removeTreasure(int index) {
        getPassageSection(treasureMap.get(treasures.get(index))).removeTreasure();
        treasureMap.remove(treasures.get(index));
        treasures.remove(index);
    }

    /**
     * Gets description of passage.
     *
     * @return description of passage
     **/
    @Override
    public String getDescription() {
        createPassageDesc();
        return description;
    }

    /**
     * Gets the passage size.
     *
     * @return passageSize
     **/
    public int getPassageSize() {
        return passageSize;
    }

    /**
     * Gets the passage section at index i.
     *
     * @param i index
     * @return passageSection at i
     **/
    public PassageSection getPassageSection(int i) {
        return thePassage.get(i);
    }

    private void createPassageDesc() {
        description = "";
        int i = 1;
        for (PassageSection section : thePassage) {
            description += "Section " + i + ": " + section.getDescription();
            i++;
        }
    }

}
