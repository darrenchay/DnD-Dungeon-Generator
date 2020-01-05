package src.Dungeon;

import java.util.Random;
import java.util.ArrayList;

public class Level {
    /**
     * Stores the list of spaces in the level.
     **/
    private ArrayList<Space> spaces;
    /**
     * Stores the level description.
     **/
    private String levelDescription;
    /**
     * Stores the num of chambers.
     **/
    private int chamberCount;

    /**
     * Creates a level object.
     **/
    public Level() {
        spaces = new ArrayList<Space>();
        levelDescription = "";
        chamberCount = 0;
        generateLevel();
    }

    /**
     * Generates a whole passagee space.
     * @param entryDoor entryDoor
     * @return passage
     **/
    private Passage createPassage(Door entryDoor) {
        Passage passage = new Passage();

        /* Sets the entry door of the passage */
        passage.setDoor(entryDoor);
        /* Loop till the passage is 10ft long or meet a dead end */
        while (passage.getPassageSize() < 10) {
            /* Create a section and add a door to the array of doors if there is one */
            PassageSection section = new PassageSection();
            passage.addPassageSection(section);
            if (section.hasDoor()) {
                passage.addDoor(section.getDoor(), section);
                if (section.isEndOfPassage()) {
                    break;
                }
            } else if (section.isDeadEnd()) {
                break;
            }
            /*System.out.println("num doors in passage: " + passage.getDoors().size());
            System.out.println(passage.getDescription());*/
        }
        return passage;
    }


    /**
     * Generates the whole level until 5 chambers are created.
     **/
    private void generateLevel() {
        /* Create first door */
        Door entryDoor = new Door(true);
        levelDescription = "You enter an " + entryDoor.getDescription() + " into a passage\n";

        /* Create a good passage which leads to a chamber and add is to spaces */
        Passage passage;
        do {
            passage = createPassage(entryDoor);
        } while (passage.getGoodDoors().size() < 1);
        spaces.add(passage);

        /* Loop until chamber count is reached */
        while (chamberCount < 5) {
            Chamber chamber;
            //Create a chamber with at least 1 exit
            do {
                chamber = new Chamber();
                //System.out.println(chamber.getDescription());
            } while (chamber.getExits().size() == 0);

            spaces.add(chamber);
            chamberCount++;

            //link chamber to passage with door
            linkSpaces(chamber, passage, 0, chamberCount);

            //adds description of the full passage, with the link to the chamber
            levelDescription += "PASSAGE:\n" + passage.getPassageDescription() + "\n";

            //create the next passage which has at least 1 door
            do {
                passage = createPassage(entryDoor);
            } while (passage.getGoodDoors().size() < 1);
            spaces.add(passage);

            //link a chamber exit to the passage
            linkSpaces(chamber, passage, 1, 1);

            //adds description of the full chamber with the link to the passage
            levelDescription += "CHAMBER " + chamberCount + ":\n" + chamber.getDescription() + "\n";

        }

    }

    /* Links the a chamber and a passage through a door */
    private void linkSpaces(Chamber chamber, Passage passage, int type, int chamberNo) {
        //if type = 0, link a passage exit to chamber entrance else its chamber exit to passage entrance
        if (type == 0) {
            chamber.setDoor(passage.getLastDoor());
            //updates description of door that links to chamber
            passage.updateLastDoorDescription(passage.getLastDoor(), chamberNo);

        } else {
            /* Uses one exit of chamber to link to passage */
            Random random = new Random();
            int rand = random.nextInt(chamber.getDoors().size());
            passage.setEntryDoor(chamber.getDoors().get(rand));

            //updates the description of the chamber to add description of link to the passage
            chamber.editExitDescription(rand + 1);
        }


    }

    /**
     * Returns the description of the level.
     * @return levelDescription
     **/
    public String getLevelDescription() {
        if (chamberCount == 5) {
            levelDescription += "LEVEL COMPLETED\n";
        } else {
            levelDescription += "LEVEL NOT COMPLETE\n";
        }
        return levelDescription;
    }

}
