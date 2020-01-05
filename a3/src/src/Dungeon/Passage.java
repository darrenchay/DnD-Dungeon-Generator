package src.Dungeon;

import java.util.ArrayList;
import java.util.HashMap;
/*
A passage begins at a door and ends at a door.  It may have many other doors along
the way

You will need to keep track of which door is the "beginning" of the passage
so that you know how to
*/

public class Passage extends Space {

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
     * Creates a passage object.
     **/
    public Passage() {
        thePassage = new ArrayList<PassageSection>();
        doors = new ArrayList<Door>();
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
    public ArrayList getDoors() {
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
        for (PassageSection section : thePassage) {
            description += section.getDescription() + "\n";
        }
    }

}
