package src.Dungeon;

public abstract class Space {

    /**
     * Gets description of the space.
     * @return description string
     **/
    public abstract String getDescription();

    /**
     * Sets door of space.
     * @param theDoor door
     **/
    public abstract void setDoor(Door theDoor);

}
