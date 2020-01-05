package dungeon;

import dnd.die.Die;
import dnd.models.Trap;

import java.util.Random;
import java.util.ArrayList;

public class Door implements java.io.Serializable {
    /**
     * Stores the spaces linked to door.
     **/
    private ArrayList<Space> spaces;
    /**
     * Boolean whether door is open.
     **/
    private boolean isOpen;
    /**
     * Boolean whether door is trapped. Set to false by default
     **/
    private boolean isTrapped = false;
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
     * Stores the roll.
     **/
    private int roll;
    /**
     * Stores the random.
     **/
    private int rand;
    /**
     * Stores the random.
     **/
    private boolean isLinked = false;

    /**
     * Creates a random door object.
     **/
    public Door() {
        spaces = new ArrayList<Space>();
        createDoor();
        createDoorDesc();
    }

    /**
     * Creates a door object which is an archway.
     *
     * @param isAnArchway isArchway
     **/
    public Door(boolean isAnArchway) {
        setArchway(isAnArchway);
        spaces = new ArrayList<Space>();
        createDoorDesc();
    }

    /**
     * Sets a trap to the door.
     **/
    public void addTrap() {
        if (!isArchway) {
            isTrapped = true;
            Trap trap = new Trap();
            trap.chooseTrap(Die.d20());
            trapDesc = trap.getDescription();
        }
    }

    /**
     * Sets door open if flag is true.
     *
     * @param flag flag
     **/
    public void setOpen(boolean flag) {
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
     * Sets boolean isLinked.
     *
     * @param flag isLinked
     **/
    public void setLinked(boolean flag) {
        isLinked = flag;
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
     * Returns boolean isLinked.
     *
     * @return isLinked
     **/
    public boolean isLinked() {
        return isLinked;
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
        return spaces;
    }

    /**
     * Adds a single space connection to the door.
     *
     * @param space space to be added
     **/
    public void addSpace(Space space) {
        if (space != null) {
            spaces.add(space);
        }
    }

    /**
     * Returns description of door.
     *
     * @return doorDesc
     **/
    public String getDescription() {
        return doorDesc;
    }

    /**
     * Edits description of chamber for linking purposes.
     *
     * @param desc description
     **/
    public void editDescription(String desc) {
        doorDesc += desc;
    }

    /***********
     Private Methods.
     *************/

    /* refactored that method into sub methods because length was > 10 */
    private void createDoorDesc() {
        if (isArchway) {
            doorDesc = "archway ";
        } else {
            if (isLocked) {
                createLockedDoorDesc();
            } else {
                createUnlockedDoorDesc();
            }
        }
    }

    private void createLockedDoorDesc() {
        doorDesc = "locked door ";
        if (isTrapped) {
            doorDesc += "but there is a trap! It's (a) " + trapDesc;
        }
    }

    private void createUnlockedDoorDesc() {
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

    /* NOTE: Refactored to reduce line count
     * door - locked 1-5 + 20%trapped
     * door - unlocked & closed 6-11 + 20% chance trapped
     * door - unlocked & open 12-18 + 20% chance trapped
     * archway if 19-20
     */
    private void createDoor() {
        setRoll();
        if (roll < 5) {
            createLockedDoor();
        } else if (roll < 12) {
            createUnlockedDoor(true);
        } else if (roll < 19) {
            createUnlockedDoor(false);
        } else {
            setArchway(true);
        }
    }

    private void createLockedDoor() {
        isLocked = true;
        setArchway(false);
        if (rand % 20 == 0) {
            addTrap();
        }
    }

    private void createUnlockedDoor(boolean opened) {
        isLocked = false;
        setArchway(false);
        setOpen(opened);
        if (rand % 20 == 0) {
            addTrap();
        }
    }

    private void setRoll() {
        roll = Die.d20();
        Random random = new Random();
        rand = random.nextInt(20);
    }
}
