package dungeon;


import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;

public class Level implements java.io.Serializable {
    /**
     * Stores the list of doors in the level and its related chamber.
     **/
    private HashMap<Door, Chamber> doors;
    /**
     * Stores the list of chambers in the level.
     **/
    private HashMap<Chamber, Integer> chambers;
    /**
     * Stores the list of chambers in the level.
     **/
    private HashMap<Integer, Chamber> chambersOrdered;
    /**
     * Stores the list of doors and its connected door from other chamber.
     **/
    private HashMap<Door, Door> doorToDoor;
    /**
     * Stores the list of doors and its connected passages.
     **/
    private HashMap<Door, ArrayList<Passage>> doorToPassage;
    /**
     * Stores the list of passages.
     **/
    private HashMap<Passage, Integer> passages;
    /**
     * Stores the list of passages.
     **/
    private HashMap<Integer, Passage> passageOrdered;
    /**
     * Stores the list of doors and their connections in the level.
     **/
    private HashMap<Door, ArrayList<Chamber>> doorConnections;
    /**
     * Stores the list of chambers and their possible chamber connections.
     **/
    private HashMap<Chamber, ArrayList<Chamber>> chamberConnections;
    /**
     * Stores the list of doors and their door connections.
     **/
    private HashMap<Door, ArrayList<Door>> doorToAllDoor;
    /**
     * Stores the level description.
     **/
    private String levelDescription = "";
    /**
     * Stores the num of chambers.
     **/
    private int chamberCount = 1;


    /**
     * Creates a level object.
     **/
    public Level() {
        doors = new HashMap<Door, Chamber>();
        chambers = new HashMap<Chamber, Integer>();
        chambersOrdered = new HashMap<Integer, Chamber>();
        doorConnections = new HashMap<Door, ArrayList<Chamber>>();
        chamberConnections = new HashMap<Chamber, ArrayList<Chamber>>();
        doorToDoor = new HashMap<Door, Door>();
        doorToPassage = new HashMap<Door, ArrayList<Passage>>();
        doorToAllDoor = new HashMap<Door, ArrayList<Door>>();
        passages = new HashMap<>();
        passageOrdered = new HashMap<>();
        init();
    }

    private void init() {
        loadChambers();
        loadChamberConnectionTable();
        connectDoors();
        addPassages();
    }

    /* Generates 5 chambers and loads them to the hashes */
    private void loadChambers() {
        while (chamberCount <= 5) {
            Chamber chamber = new Chamber();
            chambers.put(chamber, chamberCount);
            chambersOrdered.put(chamberCount, chamber);
            loadDoorsToHash(chamber);
            chamberCount++;
        }
    }

    /* Loads the doors for a chamber to the hashmap */
    private void loadDoorsToHash(Chamber chamber) {
        if (chamber.getDoors().size() == 5) {
            System.out.println("ERROR: Got 5 exits for chamber: " + chambers.get(chamber));
        }
        /* Creates the doors and door connection hashmaps  */
        for (int i = 0; i < chamber.getDoors().size(); i++) {
            doors.put(chamber.getDoors().get(i), chamber);
            doorConnections.put(chamber.getDoors().get(i), new ArrayList<Chamber>());
            doorToAllDoor.put(chamber.getDoors().get(i), new ArrayList<Door>());
            /*System.out.println("Door: " + chamber.getDoors().get(i) + " of Chamber: " + chambers.get(chamber));*/
        }
    }

    /* Loads each chamber and an array of chambers it isn't connect to into a hashmap*/
    private void loadChamberConnectionTable() {
        int currInt;
        for (Chamber chamber : chambers.keySet()) {
            currInt = chambers.get(chamber);
            ArrayList<Chamber> chambersToAdd = new ArrayList<Chamber>();
            for (Chamber chamberConnect : chambers.keySet()) {
                if (chambers.get(chamberConnect) != currInt) {
                    chambersToAdd.add(chamberConnect);
                }
            }
            chamberConnections.put(chamber, chambersToAdd);
        }
    }

    /* Gets list of other doors that could be connected to a door */
    private ArrayList<Door> getListOfDoorsToConnect(Door doorConnect) {
        ArrayList<Door> possibleDoors = new ArrayList<Door>();
        Chamber chamberOfDoor = doors.get(doorConnect);
        Random rand = new Random();
        boolean foundUnlinked = false;
        for (Chamber chamberTargets : chamberConnections.get(chamberOfDoor)) {
            for (Door door : chamberTargets.getDoors()) {
                if (!door.isLinked()) {
                    possibleDoors.add(door);
                    foundUnlinked = true;
                }
            }
            if (!foundUnlinked) {
                possibleDoors.add(chamberTargets.getDoors().get(rand.nextInt(chamberTargets.getDoors().size())));
            }
        }
        if (possibleDoors.size() == 0) {
            System.out.println("ERROR: no possible door targets found");
        }
        return possibleDoors;
    }

    private void connectDoors() {
        Random random = new Random();

        for (Door door : doors.keySet()) {
            if (!door.isLinked()) {
                /* Gets door's chamber */
                Chamber doorsChamber = doors.get(door);
                /* Gets list of possible targets */
                Door targetDoor = getListOfDoorsToConnect(door).get(random.nextInt(getListOfDoorsToConnect(door).size()));

                Chamber targetChamber = doors.get(targetDoor);

                /* Adds the associated chambers to those 2 doors  */
                doorConnections.get(door).add(targetChamber);
                doorConnections.get(targetDoor).add(doorsChamber);
                doorToAllDoor.get(door).add(targetDoor);
                doorToAllDoor.get(targetDoor).add(door);

                /* adds the door-door relationship to the doorToDoor hashmap */
                doorToDoor.put(door, targetDoor);
                doorToDoor.put(targetDoor, door);
                doorToPassage.put(door, new ArrayList<>());
                doorToPassage.put(targetDoor, new ArrayList<>());

                /* Update door and chamberConnections status */
                targetDoor.setLinked(true);
                door.setLinked(true);

                /* remove the chambers that have been added as targets from the chamberConnections hash */
                chamberConnections.get(doorsChamber).remove(targetChamber);
                chamberConnections.get(targetChamber).remove(doorsChamber);
            }
        }
    }

    private void printDoors() {
        for (Door door : doorConnections.keySet()) {
            System.out.print("Door: " + door + "from chamber " + chambers.get(doors.get(door)) + " is connected to Chambers: ");
            for (Chamber chamber : doorConnections.get(door)) {
                System.out.print(chambers.get(chamber) + " by door: " + doorToDoor.get(door) + ", ");
            }
            System.out.println();
        }
    }

    private void addPassages() {
        int count = 1;
        HashMap<Door, ArrayList<Door>> cloneDoorConnections = new HashMap<>();
        for (Door door : doorToAllDoor.keySet()) {
            cloneDoorConnections.put(door, doorToAllDoor.get(door));
        }
        for (Door door : cloneDoorConnections.keySet()) {
            while (cloneDoorConnections.get(door).size() > 0) {
                Door otherDoor = cloneDoorConnections.get(door).get(0);
                Passage passage = createPassage();

                /* Adds the passage link to the 2 doors */
                if (!door.getSpaces().contains(passage)) {
                    passage.addDoor(door);
                }
                if (!otherDoor.getSpaces().contains(passage)) {
                    passage.addDoor(otherDoor);
                }

                doorToPassage.get(door).add(passage);
                doorToPassage.get(otherDoor).add(passage);

                cloneDoorConnections.get(door).remove(otherDoor);
                cloneDoorConnections.get(otherDoor).remove(door);
                passages.put(passage, count);
                passageOrdered.put(count, passage);
                count++;

            }
        }
    }

    /* Loads the description of the level. */
    private void loadLevelDescription() {
        for (Integer i : chambersOrdered.keySet()) {
            levelDescription += "****************************\n";
            levelDescription += "CHAMBER: " + i + "\n";
            levelDescription += chambersOrdered.get(i).getDescription();
            levelDescription += printExitsDescription(chambersOrdered.get(i));
            levelDescription += "****************************\n";
        }
        levelDescription += printPassageDescription();
    }

    private String printExitsDescription(Chamber chamber) {
        String exitsDesc = "\n";
        int count = 1;
        for (Door door : chamber.getDoors()) {
            exitsDesc += "Exit " + count + " leads to passage(s): ";
            for (int i = 0; i < doorToPassage.get(door).size(); i++) {
                if (i == doorToPassage.get(door).size() - 1) {
                    exitsDesc += passages.get(doorToPassage.get(door).get(i)) + "\n";
                } else {
                    exitsDesc += passages.get(doorToPassage.get(door).get(i)) + ", ";
                }
            }
            count++;
        }
        return exitsDesc;
    }

    /**
     * Gets list of chamber exits as hashmaps.
     *
     * @param chamber chamber
     * @return exits
     **/
    public HashMap<Integer, String> getChamberExits(Chamber chamber) {
        HashMap<Integer, String> exits = new HashMap<>();
        int i = 1;
        for (Door door : chamber.getDoors()) {
            String desc = "Door is a(n) " + door.getDescription() + "\nDoor leads to ";
            for (Space space : door.getSpaces()) {
                if (space instanceof Passage) {
                    desc += "passage " + passages.get(space) + " ";
                }
            }
            exits.put(i, desc);
            i++;
        }

        return exits;
    }

    /**Gets List of exit directions for chamber.
     * @param chamber chamber
     * @return Hashmap list**/
    public HashMap<Integer, ArrayList<Integer>> getChamberExitDirection(Chamber chamber) {
        HashMap<Integer, ArrayList<Integer>> connections = new HashMap<>();
        int i = 1;
        for (Door door : chamber.getDoors()) {
            connections.put(i, new ArrayList<>());
            for (Space space : door.getSpaces()) {
                if (space instanceof Passage) {
                    connections.get(i).add(passages.get(space));
                }
            }
            i++;
        }
        /*for(Integer no : connections.keySet()){
            System.out.print("Door " + no + " goes to passage, ");
            for(int num : connections.get(no)) {
                System.out.println(num + ",");
            }
            System.out.println();
        }
        System.out.println("Size exits: " + connections.size());*/
        return connections;
    }

    /**Gets List of exit directions for passage.
     * @param passage passage
     * @return Hashmap list**/
    public HashMap<Integer, ArrayList<Integer>> getPassageExitsDirection(Passage passage) {
        HashMap<Integer, ArrayList<Integer>> connections = new HashMap<>();
        int i = 1;
        for (Door door : passage.getDoors()) {
            ArrayList<Integer> list = new ArrayList<>();
            for (Space space : door.getSpaces()) {
                if (space instanceof Chamber) {
                    if (i != 1) {
                        list.add(chambers.get(space));
                    }
                    break;
                }
            }
            if (i != 1) {
                connections.put(i - 1, list);
            }
            i++;
        }

        /*for(Integer no : connections.keySet()){
            System.out.print("Door " + no + " goes to chamber, ");
            for(int num : connections.get(no)) {
                System.out.println(num + ",");
            }
            System.out.println();
        }
        System.out.println("Size exits: " + connections.size());*/
        return connections;
    }

    /**
     * Gets list of passage exits as hashmaps.
     *
     * @param passage passage
     * @return hashmap
     **/
    public HashMap<Integer, String> getPassagesExits(Passage passage) {
        HashMap<Integer, String> exits = new HashMap<>();
        int i = 1;
        for (Door door : passage.getDoors()) {
            String desc = "Door is a(n) " + door.getDescription() + "\nDoor leads to: ";
            /* System.out.println("Spaces: " + door.getSpaces().size() + "no Doors: " + passage.getDoors().size());*/
            for (Space space : door.getSpaces()) {
                /* System.out.println(space.getClass());*/
                if (space instanceof Chamber) {
                    desc += "chamber " + chambers.get(space) + " ";
                    break;
                }
            }
            if (i != 1) {
                exits.put(i - 1, desc);
            }
            i++;
        }
        return exits;
    }

    private Passage createPassage() {
        int rand = new Random().nextInt(10);
        Passage passage = new Passage();
        PassageSection section1 = new PassageSection(1);
        PassageSection section2;
        PassageSection section3;
        PassageSection section4;
        PassageSection section5 = new PassageSection(3);

        if (rand == 0) {
            section2 = new PassageSection(1);
            section3 = new PassageSection(1);
            section4 = new PassageSection(1);
        } else if (rand < 4) {
            section2 = new PassageSection(10);
            section3 = new PassageSection(1);
            section4 = new PassageSection(12);
        } else if (rand < 6) {
            section2 = new PassageSection(12);
            section3 = new PassageSection(1);
            section4 = new PassageSection(10);
        } else if (rand < 8) {
            section2 = new PassageSection(1);
            section3 = new PassageSection(1);
            section4 = new PassageSection(10);
        } else {
            section2 = new PassageSection(1);
            section3 = new PassageSection(1);
            section4 = new PassageSection(12);
        }
        passage.addPassageSection(section1);
        passage.addPassageSection(section2);
        passage.addPassageSection(section3);
        passage.addPassageSection(section4);
        passage.addPassageSection(section5);
        return passage;
    }

    private String printPassageDescription() {
        String passageDesc = "";
        for (Passage passage : passages.keySet()) {
            passageDesc += "Passage " + passages.get(passage) + ":\n" + passage.getDescription() + "\n";
        }
        return passageDesc;
    }

    /**
     * Returns the description of the level.
     *
     * @return levelDescription
     **/
    public String getLevelDescription() {
        /*printDoors();*/
        loadLevelDescription();
        return levelDescription;
    }

    /**
     * Returns list of chambers ordered.
     *
     * @return hashmap
     **/
    public HashMap<Integer, Chamber> getChamberList() {
        return chambersOrdered;
    }

    /**
     * Returns list of passages ordered.
     *
     * @return hashmap
     **/
    public HashMap<Integer, Passage> getPassageList() {
        return passageOrdered;
    }

}
