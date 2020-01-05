package src.Dungeon;

import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.Monster;
import dnd.models.Treasure;
import dnd.models.Stairs;
import dnd.models.Trap;
import dnd.die.Die;
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
     * Contains the description of a trap.
     **/
    private Trap trap;
    /**
     * Contains the description of stairs.
     **/
    private Stairs stairs;
    /**
     * Contains the roll generated for chamber contents.
     **/
    private int roll;
    /**
     * Contains the description of the chamber.
     **/
    private String description;


    /**
     * Creates a chamber object with no params.
     **/
    public Chamber() {
        myContents = new ChamberContents();
        roll = Die.d20();
        monsters = new ArrayList<Monster>();
        treasures = new ArrayList<Treasure>();
        doors = new ArrayList<Door>();
        init();
    }

    private void init() {
        mySize = ChamberShape.selectChamberShape(roll);
        mySize.setNumExits(roll % 9 + 1);
        myContents.chooseContents(roll);
        loadRoom();
        description = "";
        createDescription();
    }

    /**
     * Gets the doors in that chamber.
     *
     * @return doors
     **/
    public ArrayList<Door> getDoors() {
        return doors;
    }

    /**
     * Adds a monster to chamber.
     *
     * @param theMonster the monster
     **/
    public void addMonster(Monster theMonster) {
        if (theMonster != null) {
            theMonster.setType(Die.percentile());
            monsters.add(theMonster);
        }
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
     * @param theTreasure the treasure
     **/
    public void addTreasure(Treasure theTreasure) {
        if (theTreasure != null) {
            theTreasure.chooseTreasure(Die.percentile());
            theTreasure.setContainer(Die.d20());
            treasures.add(theTreasure);
        }
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
     * Returns description of the chamber.
     *
     * @return description
     **/
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Adds door to room.
     *
     * @param newDoor door
     **/
    @Override
    public void addDoor(Door newDoor) {
        //should add a door connection to this room
        if (newDoor != null) {
            newDoor.addSpace(this);
            doors.add(newDoor);
        }
        /*System.out.println(newDoor.getSpaces().get(0).getDescription());*/
    }

    /* Refactored from getChamberContentDescription */
    private void loadRoom() {
        if (roll > 12 && roll < 18) {
            addMonster(new Monster());
            if (roll > 14) {
                addTreasure(new Treasure());
            }
        }
        if (roll == 18) {
            stairs = new Stairs();
            stairs.setType(Die.d20());
        }
        if (roll == 19) {
            trap = new Trap();
            trap.chooseTrap(Die.d20());
        }
        if (roll == 20) {
            addTreasure(new Treasure());
        }
        generateDoors();
    }

    /* links the doors to the exits based on the indexes of the arraylists */
    private void generateDoors() {
        for (int i = 0; i < mySize.getNumExits(); i++) {
            Door door = new Door();
            door.addSpace(this);
            addDoor(door);
        }
    }

    /* creates the description of the room */
    private void createDescription() {
        getChamberSizeDescription();
        getChamberContentDescription();
        getDoorsDesc();
    }

    /* Creates the description of the chamber shape and size */
    private void getChamberSizeDescription() {
        description += "This chamber is a " + mySize.getShape();
        //Gets length and width of chamber if applicable
        try {
            description += " of size " + mySize.getLength() + " x " + mySize.getWidth();
        } catch (UnusualShapeException e) {
        }
        description += " with an area of " + mySize.getArea() + "\n";
    }

    /* Creates the description of the contents of the chamber */
    private void getChamberContentDescription() {
        if (roll < 13) {
            description += "As you enter the chamber, you see that it's empty!\n";
        }
        if (roll > 12 && roll < 18) {
            description += "As you enter the chamber, you see " + getMonstersDescription(getMonsters().get(0));
            if (roll > 14) {
                description += "There is also " + getTreasuresDescription(treasures.get(0));
            }
        }
        if (roll == 18) {
            description += "As you enter the chamber, you see Stairs: " + stairs.getDescription() + "\n";
        }
        if (roll == 19) {
            description += "As you enter the chamber, you see a trap! It's (a) " + trap.getDescription() + "\n";
        }
        if (roll == 20) {
            description += "As you enter the chamber, you see treasure!\n" + getTreasuresDescription(treasures.get(0));
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

    private void getDoorsDesc() {
        if (mySize.getNumExits() == 0) {
            description += "\nThere are no exits for this room\n";
        } else {
            for (int i = 0; i < mySize.getNumExits(); i++) {
                description += "Exit " + (i + 1) + ": is a(n) " + doors.get(i).getDescription() + "\n";
            }
        }
    }
}
