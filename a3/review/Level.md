| Method                                                    | Description                                                                                           | Instance Variables                                                                                                        | Class Methods                                                       | Other Methods                                                                                                                                                                                                  | Line count |
|-----------------------------------------------------------|-------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------|
| Level()                                                   | Creates a level object.                                                                               | doors, chambers, chambersOrdered, doorConnections, chamberConnections, doorToDoor, doorToPassage, doorToAllDoor, passages | init                                                                |                                                                                                                                                                                                                | 10         |
| void init()                                               | Initializes the level completely                                                                      |                                                                                                                           | loadChambers, loadChamberConnectionTable, connectDoors, addPassages |                                                                                                                                                                                                                | 4          |
| void loadChambers()                                       | Generates 5 chambers and loads them to the hashes                                                     | chamberCount, chambers, chambersOrdered                                                                                   | loadDoorsToHash                                                     | chambers.put, chambersOrdered.put                                                                                                                                                                              | 6          |
| void loadDoorsToHash(Chamber chamber)                     | Loads the doors for a chamber to the hashmaps                                                         | chambers,  doors, doorConnections, doorToAllDoor                                                                          |                                                                     | chambers.get, chamber.getDoors, doors.put, doorConnections.put, doorToAllDoor.put                                                                                                                              | 6          |
| void loadChamberConnectionTable()                         | Loads each chamber and an array of chambers it isn't connect to into a hashmap                        | currInt, chambers, chamber,  chambersToAdd, chamberConnect, chamberConnections                                            |                                                                     | chambers.keySet, chambers.get, chambersToAdd.add, chamberConnections.put                                                                                                                                       | 8          |
| ArrayList<Door> getListOfDoorsToConnect(Door doorConnect) | Gets list of other doors that could be connected to a door                                            | foundUnlinked, possibleDoors, chamberOfDoor, rand, chamberTargets, door, doors, chamberConnections                        |                                                                     | doors.get, chamberConnections.get, chamberTargets.getDoors, possibleDoors.add, rand.nextInt, possibleDoors.size                                                                                                | 14         |
| void connectDoors()                                       | Creates the hashmap of door and array of target chambers. It also creates the door - door connections | random, door, doorsChamber, doors, targetDoor, targetChamber, doorConnections, doorToDoor, chamberConnections             | getListOfDoorsToConnect                                             | doors.keySet, door.isLinked, doors.get, random.nextInt, doorConnections.get, doorToDoor.put, doorToPassage.put, doorToAllDoor, targetDoor.setLinked, door.setLinked, chamberConnections.get                    | 18         |
| void printDoors()                                         | prints the door and the door it is connected to (Debug method)                                        | door, doorConnections, chambers, doors, doorToDoor                                                                        | connectDoors                                                        | doorConnections.keySet, chambers.get, doorConnections.get,doorToDoor.getSystem.out.println,                                                                                                                    | 6          |
| void addPassages()                                        | Creates the passages of the level                                                                     | door, section1, section2, passage, doorToPassage, count, cloneDoorConnections, doorToAllDoor, otherDoor                   |                                                                     | doorToAllDoor.keySet, doorToAllDoor.get, cloneDoorConnections.keySet, cloneDoorConnections.get,passage.addPassageSection, passage.addDoor, doorToPassage.get.add,cloneDoorConnections.get.remove, passages.put | 20         |
| void loadLevelDescription()                               | Creates the description of the level                                                                  | levelDescription, chambersOrdered                                                                                         | printExitsDescription, printPassageDescription                      | chambersOrdered.keySet, chambersOrdered.get, Chamber.getDescription                                                                                                                                            | 7          |
| String printExitsDescription(Chamber chamber)             | Creates the description of the chamber exits to their passage                                         | exitsDesc, count,  door, doorToPassage, passages                                                                          |                                                                     | chamber.getDoors, doorToPassage.get.size, doorToPassage.get.get, passages.get                                                                                                                                  | 11         |
| String printPassageDescription(Chamber chamber)           | Creates the description of the passage and the chamber it leads to                                    | passageDesc,  passage, passages                                                                                           |                                                                     | passages.keySet, passages.get, passage.getDescription                                                                                                                                                          | 4          |
| String getLevelDescription()                              | Returns the description of the level.                                                                 | levelDescription                                                                                                          | loadLevelDescription                                                |                                                                                                                                                                                                                | 2          |