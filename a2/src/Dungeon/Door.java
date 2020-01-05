package src.Dungeon;

import dnd.die.D20;
import dnd.models.Exit;
import dnd.models.Trap;

import java.util.Random;

import java.util.ArrayList;

public class Door {
    /**
     * Stores the spaces linked to door.
     **/
    private ArrayList<Space> spaces;
    /**
     * Boolean whether door is open.
     **/
    private boolean isOpen;
    /**
     * Boolean whether door is trapped.
     **/
    private boolean isTrapped;
    /**
     * Boolean whether door is an archway.
     **/
    private boolean isArchway;
    /**
     * Boolean whether door is locked.
     **/
    private boolean isLocked;
    /**
     * Stores description of the trap.
     **/
    private String trapDesc;
    /**
     * Stores description of door.
     **/
    private String doorDesc;
    /**
     * Stores exit of door.
     **/
    private Exit exit;

    /**
     * Creates a random door object.
     **/
    public Door() {
        //needs to set defaults
        exit = new Exit();
        spaces = new ArrayList<Space>();
        createDoor();
    }

    /**
     * Creates a random door object and stores exit.
     *
     * @param theExit exit
     **/
    public Door(Exit theExit) {
        //sets up the door based on the Exit from the tables
        if (theExit == null) {
            exit = new Exit();
        } else {
            exit = theExit;
        }
        spaces = new ArrayList<Space>();
        createDoor();
    }

    /**
     * Creates a door object which is an archway.
     *
     * @param isAnArchway isArchway
     **/
    public Door(boolean isAnArchway) {
        setArchway(isAnArchway);
        exit = new Exit();
        spaces = new ArrayList<Space>();
    }

    /* note:  Some of these methods would normally be protected or private, but because we
    don't want to dictate how you set up your packages we need them to be public
    for the purposes of running an automated test suite (junit) on your code.  */

    /**
     * Sets a trap if flag is true.
     *
     * @param flag trapped or not
     * @param roll roll
     **/
    public void setTrapped(boolean flag, int... roll) {
        // true == trapped.  Trap must be rolled if no integer is given
        if (flag) {
            isTrapped = true;
            Trap trap = new Trap();
            if (roll.length == 0) {
                trap.setDescription();
                trapDesc = trap.getDescription();
            } else {
                trap.setDescription(roll[0]);
                trapDesc = trap.getDescription();
            }
        } else {
            isTrapped = false;
        }

    }

    /**
     * Sets door open if flag is true.
     *
     * @param flag flag
     **/
    public void setOpen(boolean flag) {
        //true == open
        if (flag || isArchway) {
            isOpen = true;
        } else {
            isOpen = false;
        }
    }

    /**
     * Sets door to archway if flag is true.
     *
     * @param flag flag
     **/
    public void setArchway(boolean flag) {
        //true == is archway
        if (flag) {
            isArchway = true;
            isOpen = true;
            isTrapped = false;
            isLocked = false;
        } else {
            isArchway = false;
        }
    }

    /**
     * Returns boolean isTrapped.
     *
     * @return isTrapped
     **/
    public boolean isTrapped() {
        return isTrapped;
    }

    /**
     * Returns boolean isOpen.
     *
     * @return isOpen
     **/
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Returns boolean isArchway.
     *
     * @return isArchway
     **/
    public boolean isArchway() {
        return isArchway;
    }

    /**
     * Returns trap description.
     *
     * @return trapDesc
     **/
    public String getTrapDescription() {
        return trapDesc;
    }

    /**
     * Returns spaces linked to door.
     *
     * @return spaces
     **/
    public ArrayList<Space> getSpaces() {
        //returns the two spaces that are connected by the door
        return spaces;
    }

    /**
     * Sets the 2 spaces linked to door.
     *
     * @param spaceOne space one
     * @param spaceTwo space two
     **/
    public void setSpaces(Space spaceOne, Space spaceTwo) {
        //identifies the two spaces with the door
        //this method should also call the addDoor method from Space
        spaceOne.setDoor(this);
        spaces.add(spaceOne);
        spaceTwo.setDoor(this);
        spaces.add(spaceTwo);
    }

    /**
     * Returns description of door.
     *
     * @return doorDesc
     **/
    public String getDescription() {
        createDoorDesc();
        return doorDesc;
    }

    /***********
     You can write your own methods too, you aren't limited to the required ones.
     *************/

    private void createDoorDesc() {
        if (isArchway) {
            doorDesc = "archway";
        } else {
            if (isLocked) {
                doorDesc = "locked door ";
                if (isTrapped) {
                    doorDesc += "but there is a trap! It's (a) " + trapDesc;
                }
            } else {
                doorDesc = "unlocked door";
                if (isOpen) {
                    doorDesc += " that is open ";
                    if (isTrapped) {
                        doorDesc += "but there is a trap! It's (a) " + trapDesc;
                    }
                } else {
                    doorDesc += " that is closed ";
                    if (isTrapped) {
                        doorDesc += "but there is a trap! It's (a) " + trapDesc;
                    }
                }
            }
        }
    }

    private void createDoor() {
        D20 die = new D20();
        int roll = die.roll();
        Random random = new Random();
        int rand = random.nextInt(20);

        /*
         * door - locked 1-5 + 20%trapped
         * door - unlocked & closed 6-11 + 20% chance trapped
         * door - unlocked & open 12-18 + 20% chance trapped
         * archway if 19-20
         */
        if (roll < 5) {
            isLocked = true;
            setArchway(false);
            if (rand % 20 == 0) {
                setTrapped(true);
            } else {
                setTrapped(false);
            }
        } else if (roll < 12) {
            isLocked = false;
            setArchway(false);
            setOpen(false);
            if (rand % 20 == 0) {
                setTrapped(true);
            } else {
                setTrapped(false);
            }
        } else if (roll < 19) {
            isLocked = false;
            setArchway(false);
            setOpen(true);
            if (rand % 20 == 0) {
                setTrapped(true);
            } else {
                setTrapped(false);
            }
        } else {
            setArchway(true);
        }
    }
}
