package src.Dungeon;

import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.Exit;
import dnd.models.Monster;
import dnd.models.Treasure;
import dnd.models.Stairs;
import dnd.models.Trap;
import dnd.die.D20;
import dnd.exceptions.NotProtectedException;
import dnd.exceptions.UnusualShapeException;

import java.util.ArrayList;

public class Chamber extends Space {

    /**
     * Contains the content of the chamber.
     **/
    private ChamberContents myContents;
    /**
     * Contains the dimensions of the chamber.
     **/
    private ChamberShape mySize;
    /**
     * Contains the monsters.
     **/
    private ArrayList<Monster> monsters;
    /**
     * Contains the treasures.
     **/
    private ArrayList<Treasure> treasures;
    /**
     * Contains the doors in chamber.
     **/
    private ArrayList<Door> doors;
    /**
     * Contains the exits.
     **/
    private ArrayList<Exit> exits;
    /**
     * Contains the description of the chamber.
     **/
    private String description;
    /**
     * Contains the entrance door.
     **/
    private Door entranceDoor;
    /**
     * Contains the roll generated for chamber contents.
     **/
    private int roll;
    /**
     * Contains the doors generated for junit tests.
     **/
    private ArrayList<Door> doorsJunit;


    /*
     Required Methods for that we will test during grading
     note:Some of these methods would normally be protected or private, but because we
     don't want to dictate how you set up your packages we need them to be public
     for the purposes of running an automated test suite (junit) on your code.*/

    /**
     * Creates a chamber object with no params.
     **/
    public Chamber() {
        myContents = new ChamberContents();
        mySize = new ChamberShape();
        monsters = new ArrayList<Monster>();
        treasures = new ArrayList<Treasure>();
        doors = new ArrayList<Door>();
        doorsJunit = new ArrayList<Door>();
        description = "";
        D20 dice = new D20();
        roll = dice.roll();
        myContents.setDescription(roll);
        exits = new ArrayList<Exit>();
        createDescription();
    }

    /**
     * Creates a chamber object with specific shape and contents.
     * @param theContents contents
     * @param theShape shape
     **/
    public Chamber(ChamberShape theShape, ChamberContents theContents) {
        mySize = theShape;
        myContents = theContents;
        monsters = new ArrayList<Monster>();
        treasures = new ArrayList<Treasure>();
        doors = new ArrayList<Door>();
        doorsJunit = new ArrayList<Door>();
        description = "";
        D20 dice = new D20();
        roll = dice.roll();
        myContents.setDescription(roll);
        exits = new ArrayList<Exit>();
        getChamberSizeDescriptionJunit();
        getChamberContentDescription();
        getExitsDesc();
    }

    /**
     * Sets the shape of the chamber using param the shape.
     * @param theShape the shape
     **/
    public void setShape(ChamberShape theShape) {
        //theShape.setShape();
        //theShape.setNumExits();
        mySize = theShape;
    }

    /**
     * Gets the doors in that chamber.
     * @return doors
     **/
    public ArrayList<Door> getDoors() {
        /*        mySize.setNumExits();*/
        /*generateDoorsForJunit();*/
        //doors.add(entranceDoor);
        return doorsJunit;
    }

    /**
     * Adds a monster to chamber.
     * @param theMonster the monster
     **/
    public void addMonster(Monster theMonster) {
        if (theMonster != null) {
            theMonster.setType();
            monsters.add(theMonster);
        }
    }

    /**
     * Gets all monsters.
     * @return  monsters
     **/
    public ArrayList<Monster> getMonsters() {
        return monsters;
    }


    /**
     * Adds a treasure.
     * @param theTreasure the treasure
     **/
    public void addTreasure(Treasure theTreasure) {
        if (theTreasure != null) {
            theTreasure.setDescription();
            theTreasure.setContainer();
            treasures.add(theTreasure);
        }
    }

    /**
     * Returns treasures.
     * @return treasures
     **/
    public ArrayList<Treasure> getTreasureList() {
        return treasures;
    }

    /**
     * Returns description of the chamber.
     * @return description
     **/
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Sets the door link to the room.
     * @param newDoor door
     **/
    @Override
    public void setDoor(Door newDoor) {
        //should add a door connection to this room
        if  (newDoor != null) {
            doorsJunit.add(0, newDoor);
        }
    }

    /***********
     You can write your own methods too, you aren't limited to the required ones.
     *************/
    private void generateDoorsForJunit() {
        generateExits();
        for (Exit exit: exits) {
            Door door = new Door();
            doorsJunit.add(door);
        }
    }

    /**
     * Gets all the exits in the chamber.
     * @return exits
     **/
    public ArrayList<Exit> getExits() {
        return exits;
    }

    /**
     * Edits description of exit that leads to a passage.
     * @param exitNo exit num
     **/
    public void editExitDescription(int exitNo) {
        description += "Exit " + exitNo + " leads to the next passage\n";
    }

    /* creates the description of the room */
    private void createDescription() {
        getChamberSizeDescription();
        getChamberContentDescription();
        getExitsDesc();
    }

    private void setShape2(ChamberShape theShape) {
        theShape.setShape();
        theShape.setNumExits();
        mySize = theShape;
    }

    /* Creates the description of the chamber shape and size */
    private void getChamberSizeDescription() {
        setShape2(mySize);
        description = "This chamber is a " + mySize.getShape();

        //Gets length and width of chamber if applicable
        try {
            description += " of size " + mySize.getLength() + " x " + mySize.getWidth();
        } catch (UnusualShapeException e) {
        }

        description += " with an area of " + mySize.getArea() + "\n";
    }
    /* Creates the description of the chamber shape and size */
    private void getChamberSizeDescriptionJunit() {
        description = "This chamber is a " + mySize.getShape();

        //Gets length and width of chamber if applicable
        try {
            description += " of size " + mySize.getLength() + " x " + mySize.getWidth();
        } catch (UnusualShapeException e) {
        }

        description += " with an area of " + mySize.getArea() + "\n";
    }

    /* Creates the description of the contents of the chamber */
    private void getChamberContentDescription() {
        description += "As you enter the chamber, you see ";
        if (roll < 13) {
            description += "that it's empty!\n";
        }
        if (roll > 12 && roll < 18) {
            Monster monster = new Monster();
            addMonster(monster);
            description += getMonstersDescription(monster);
            if (roll > 14) {
                Treasure treasure = new Treasure();
                addTreasure(treasure);
                description += "There is also ";
                description += getTreasuresDescription(treasure);
            }
        }
        if (roll == 18) {
            Stairs stairs = new Stairs();
            stairs.setType();
            description += "Stairs: " + stairs.getDescription() + "\n";
        }
        if (roll == 19) {
            Trap trap = new Trap();
            trap.setDescription();
            description += "a trap! It's (a) " + trap.getDescription() + "\n";
        }
        if (roll == 20) {
            Treasure treasure = new Treasure();
            addTreasure(treasure);
            description += "treasure!\n";
            description += getTreasuresDescription(treasure);
        }
    }

    /* Gets the description of a monster */
    private String getMonstersDescription(Monster monster) {
        return "around " + monster.getMinNum() + " to " + monster.getMaxNum() + " " + monster.getDescription() + "s\n";
    }

    /* Gets the description of the treasure */
    private String getTreasuresDescription(Treasure treasure) {
        String treasureDesc = treasure.getDescription() + " stored in " + treasure.getContainer();
        try {
            treasureDesc += " and guarded by " + treasure.getProtection() + "\n";
        } catch (Exception NotProtectedException) {
            treasureDesc += " and is left unguarded\n";
        }
        return treasureDesc;
    }

    private void generateExits() {
        exits = mySize.getExits();
    }

    /* links the doors to the exits based on the indexes of the arraylists */
    private void generateDoors() {
        for (Exit exit : exits) {
            Door door = new Door(exit);
            doors.add(door);
            doorsJunit.add(door);
        }
    }

    private void getExitsDesc() {
        generateExits();
        generateDoors();
        if (exits.size() == 0) {
            description += "\nThere are no exits for this room\n";
        } else {
            if (exits.size() == 1) {
                description += "\nThere is " + exits.size() + " exit for this room:\n";
            } else {
                description += "\nThere are " + exits.size() + " exits for this room:\n";
            }
            for (int i = 0; i < exits.size(); i++) {
                description += "Exit " + (i + 1) + ": is found at the " + exits.get(i).getLocation() + " and goes " + exits.get(i).getDirection();
                description += ". The door is a(n) " + doors.get(i).getDescription() + "\n";
            }
        }
    }
}
