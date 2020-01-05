package dungeon;

public abstract class Space implements java.io.Serializable {

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
