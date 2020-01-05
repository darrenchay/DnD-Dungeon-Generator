package dungeon;

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

public class Chamber extends Space implements java.io.Serializable {
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
        roll += 2;
        if (roll >= 18) {
            roll = roll % 18 + 3;
        }
        mySize = ChamberShape.selectChamberShape(roll);
        mySize.setNumExits(roll % 9 + 1);
        myContents.chooseContents(roll);
        loadRoom();
        description = "";
        createDescription();
    }

    /**
     * Gets the shape in that chamber.
     *
     * @return mySize
     **/
    public ChamberShape getShape() {
        return mySize;
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
     * @param theRoll the monster
     **/
    public void addMonster(int theRoll) {
        Monster monster = new Monster();
        monster.setType(theRoll);
        monsters.add(monster);
    }

    /**
     * Removes a monster from chamber.
     *
     * @param index the monster index
     **/
    public void removeMonster(int index) {
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
     * @param theRoll the treasure
     **/
    public void addTreasure(int theRoll) {
        Treasure treasure = new Treasure();
        treasure.chooseTreasure(theRoll);
        treasure.setContainer(Die.d20());
        treasures.add(treasure);
    }

    /**
     * Removes a treasure from chamber.
     *
     * @param index the treasure index
     **/
    public void removeTreasure(int index) {
        treasures.remove(index);
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
        createDescription();
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
            addMonster(Die.percentile());
            if (roll > 14) {
                addTreasure(Die.percentile());
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
            addTreasure(Die.percentile());
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
        description = "";
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
        description += "\nChamber Contents: \n";
        if (roll < 13 && (monsters.size() == 0) && (treasures.size() == 0)) {
            description += "The chamber is empty!\n";
        }
        if (roll == 18) {
            description += "As you enter the chamber, you see Stairs: " + stairs.getDescription() + "\n";
        }
        if (roll == 19) {
            description += "As you enter the chamber, you see a trap! It's (a) " + trap.getDescription() + "\n";
        }
        if (monsters.size() > 0) {
            description += "\n========= Monsters! =========\n" + getMonstersDescription();
        }
        if (treasures.size() > 0) {
            description += "\n========= Treasure! =========\n" + getTreasuresDescription();
        }
    }

    /* Gets the description of a monster */
    private String getMonstersDescription() {
        String monsterDesc = "";
        int i = 1;
        for (Monster monster : monsters) {
            monsterDesc += i + ". There are around " + monster.getMinNum() + " to " + monster.getMaxNum() + " " + monster.getDescription() + "s\n";
            i++;
        }
        return monsterDesc;
    }

    /* Gets the description of the treasure */
    private String getTreasuresDescription() {
        String treasureDesc = "";
        int i = 1;
        for (Treasure treasure : treasures) {
            treasureDesc += i + ". " + treasure.getDescription() + " stored in " + treasure.getContainer();
            try {
                treasureDesc += " and guarded by " + treasure.getProtection() + "\n";
            } catch (Exception NotProtectedException) {
                treasureDesc += " and is left unguarded\n";
            }
            i++;
        }
        return treasureDesc;
    }

    private void getDoorsDesc() {
        if (mySize.getNumExits() == 0) {
            description += "\nThere are no exits for this room\n";
        } else {
            description += "========= Exits =========\n";
            for (int i = 0; i < mySize.getNumExits(); i++) {
                description += "Exit " + (i + 1) + ": is a(n) " + doors.get(i).getDescription() + "\n";
            }
        }
    }
}
