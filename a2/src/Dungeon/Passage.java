package src.Dungeon;

import dnd.models.Monster;

import java.util.ArrayList;
import java.util.HashMap;
/*
A passage begins at a door and ends at a door.  It may have many other doors along
the way

You will need to keep track of which door is the "beginning" of the passage
so that you know how to
*/

public class Passage extends Space {
    //these instance variables are suggestions only
    //you can change them if you wish.

    /**
     * Stores the whole array of sections (passage).
     **/
    private ArrayList<PassageSection> thePassage;
    /**
     * Stores the sections with their doors.
     **/
    private HashMap<Door, PassageSection> doorMap;
    /**
     * Stores the doors in passage.
     **/
    private ArrayList<Door> doors;
    /**
     * Stores the doors (junit test).
     **/
    private ArrayList<Door> addedDoors;
    /**
     * Stores the entry door connection.
     **/
    private Door entryDoor;
    /**
     * Stores the num of passage sections.
     **/
    private int passageSize;
    /**
     * Stores the description of the passage.
     **/
    private String description = "";

    /*
     Required Methods for that we will test during grading
    note:  Some of these methods would normally be protected or private, but because we
    don't want to dictate how you set up your packages we need them to be public
    for the purposes of running an automated test suite (junit) on your code.  */
    /**
     * Creates a passage object.
     **/
    public Passage() {
        thePassage = new ArrayList<PassageSection>();
        doorMap = new HashMap<Door, PassageSection>();
        doors = new ArrayList<Door>();
        addedDoors = new ArrayList<Door>();
        passageSize = 0;
    }

    /**
     * Returns the list of doors (junit).
     * @return addedDoors
     **/
    public ArrayList getDoors() {
        return addedDoors;
    }

    /**
     * Gets door object at index i in array.
     * @param i index
     * @return door object
     **/
    public Door getDoor(int i) {
        //returns the door in section 'i'. If there is no door, returns null
        if (i >= addedDoors.size() || addedDoors.get(i) == null) {
            return null;
        } else {
            return addedDoors.get(i);
        }
    }

    /**
     * Adds a monster at index i in passage.
     * @param i index
     * @param theMonster monster
     **/
    public void addMonster(Monster theMonster, int i) {
        // adds a monster to section 'i' of the passage
        PassageSection section = thePassage.get(i);
        section.addMonster(theMonster);
        description += section.getDescription();
    }

    /**
     * Gets a monster at index i in passage.
     * @param i index
     * @return monster
     **/
    public Monster getMonster(int i) {
        //returns Monster door in section 'i'. If there is no Monster, returns null
        PassageSection section = thePassage.get(i);
        Monster monster = section.getMonster();
        return monster;
    }

    /**
     * Adds a passage section.
     * @param toAdd passage section
     **/
    public void addPassageSection(PassageSection toAdd) {
        //adds the passage section to the passageway
        if (toAdd != null) {
            thePassage.add(toAdd);
            passageSize++;
        }
        description += toAdd.getDescription();
    }

    /**
     * Adds a door to the passage section.
     * @param newDoor new door
     **/
    @Override
    public void setDoor(Door newDoor) {
        //should add a door connection to the current Passage Section
        addedDoors.add(0, newDoor);
    }

    /**
     * Gets description of passage. (Junit)
     * @return description of passage
     **/
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Sets entry door of passage.
     * @param door entryDoor
     **/
    public void setEntryDoor(Door door) {
        this.entryDoor = door;
    }

    /*You can write your own methods too, you aren't limited to the required ones */
    /*public String getExitDoorDesc() { return exitDoors.get(exitDoors.size() - 1).getDescription(); }*/

    /**
     * Gets description of passage.
     * @return description of passage
     **/
    public String getPassageDescription() {
        return createPassageDesc();
    }
    /**
     * Gets the doors used for generation of the level.
     * @return doors
     **/
    public ArrayList<Door> getGoodDoors() {
        return doors;
    }

    /**
     * Gets the last door of the passage.
     * @return lastDoor
     **/
    public Door getLastDoor() {
        Door lastDoor = doors.get(doors.size() - 1);
        return lastDoor;
    }

    /**
     * Updates the door linked to a chamber description.
     * @param lastDoor last door
     * @param chamberNo chamber num
     **/
    public void updateLastDoorDescription(Door lastDoor, int chamberNo) {
        doorMap.get(lastDoor).editDescription(" which leads to CHAMBER " + chamberNo);
    }

    /**
     * Gets the passage size.
     * @return passageSize
     **/
    public int getPassageSize() {
        return passageSize;
    }

    private String createPassageDesc() {
        String desc = "";
        for (PassageSection section : thePassage) {
            desc += section.getDescription() + "\n";
        }
        description = desc;
        return desc;
    }

    /**
     * Gets the passage section at index i.
     * @param i index
     * @return passageSection at i
     **/
    public PassageSection getPassageSection(int i) {
        return thePassage.get(i);
    }

    /**
     * Adds a door connection to a passage.
     * @param theDoor door
     * @param section passage section
     **/
    public void addDoor(Door theDoor, PassageSection section) {
        doors.add(theDoor);
        doorMap.put(theDoor, section);
    }
}
