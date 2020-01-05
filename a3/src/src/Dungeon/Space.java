package src.Dungeon;

public abstract class Space {

    /**
     * Gets description of the space.
     * @return description string
     **/
    public abstract String getDescription();

    /**
     * Adds a door connection to the space.
     * @param theDoor door
     **/
    public abstract void addDoor(Door theDoor);

}
