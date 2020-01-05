package gui;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import dnd.models.Monster;
import dnd.models.Treasure;
import dungeon.Chamber;
import dungeon.Level;
import dungeon.Passage;

public class Controller implements Serializable {
    /**Gui.**/
    private Gui myGui;
    /**Level.**/
    private Level level;
    /**Chambers.**/
    private HashMap<Integer, Chamber> chambers;
    /**Passages.**/
    private HashMap<Integer, Passage> passages;
    /**monsters.**/
    private HashMap<String, Integer> monsterList;
    /**treasures.**/
    private HashMap<String, Integer> treasureList;

    /**Constructor for Controller.
     * @param theGui gui
     **/
    public Controller(Gui theGui) {
        myGui = theGui;
        level = new Level();
        init();
    }

    private void init() {
        chambers = level.getChamberList();
        passages = level.getPassageList();
        monsterList = new HashMap<>();
        treasureList = new HashMap<>();
        setupMonsterTable();
        setupTreasureTable();
    }

    /**Gets description space.
     * @param type space type
     * @param no space no
     * @return desc
     **/
    public String getDesc(int type, int no) {
        String desc = "";
        if (type == 1) {
            desc = "========= Chamber " + no + " =========\n" + chambers.get(no).getDescription();
        } else {
            desc = "========= Passage " + no + " =========\n" + passages.get(no).getDescription();
        }
        /*System.out.println(desc);*/
        return desc;
    }

    /**Saves level.
     * @param file file
     **/
    public void save(File file) {
        try {
            String absolute = file.getCanonicalPath();
            FileOutputStream outfile = new FileOutputStream(absolute);
            ObjectOutputStream output = new ObjectOutputStream(outfile);
            output.writeObject(level);
            output.close();
            outfile.close();
            System.out.println("Saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**Loads level.
     * @param file file
     **/
    public void load(File file) {
        try {
            String absolute = file.getCanonicalPath();
            System.out.println(absolute);
            FileInputStream infile = new FileInputStream(absolute);
            ObjectInputStream input = new ObjectInputStream(infile);
            level = (Level) input.readObject();
            init();
            myGui.setupScene(this, myGui.getPrimaryStage());
            /*myGui.start(myGui.getPrimaryStage());*/
            /*myGui.setupScene(this, myGui.getPrimaryStage());*/

            /*myGui.start(myGui.getPrimaryStage());
            myGui.setUpRoot();*/
            input.close();
            infile.close();
            System.out.println("Loaded");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**Loads door desc.
     * @param no spaceNo
     * @param type space type
     * @return hashmap desc
     **/
    public HashMap<Integer, String> loadDoorDesc(int no, int type) {
        HashMap<Integer, String> exits;
        if (type == 1) {
            Chamber chamber = chambers.get(no);
            exits = level.getChamberExits(chamber);
        } else {
            Passage passage = passages.get(no);
            exits = level.getPassagesExits(passage);
        }
        return exits;
    }

    /**Gets List of exit directions for space.
     * @param spaceNo chamber/passage
     * @param type type
     * @return Hashmap list**/
    public HashMap<Integer, ArrayList<Integer>> getSpaceDoors(int spaceNo, int type) {
        if (type == 1) {
            return level.getChamberExitDirection(chambers.get(spaceNo));
        } else {
            return level.getPassageExitsDirection(passages.get(spaceNo));
        }
    }


    /**Gets list of monsters.
     * @return descMonsters
     **/
    public HashMap<String, Integer> getMonsterList() {
        return monsterList;
    }

    /**Gets list of treasures.
     * @return treasList
     **/
    public HashMap<String, Integer> getTreasureList() {
        return treasureList;
    }

    private void setupMonsterTable() {
        monsterList.put("Giant Ants", 1);
        monsterList.put("Badger", 4);
        monsterList.put("Fire Beetle", 12);
        monsterList.put("Mane Demons", 15);
        monsterList.put("Dwarf", 17);
        monsterList.put("Ear Seeker", 18);
        monsterList.put("Elf", 19);
        monsterList.put("Gnome", 21);
        monsterList.put("Goblin", 25);
        monsterList.put("Hafling", 28);
        monsterList.put("Hobgoblin", 32);
        monsterList.put("Human Bandit", 45);
        monsterList.put("Kobold", 50);
        monsterList.put("Orc", 65);
        monsterList.put("Piercer", 70);
        monsterList.put("Giant Rat", 80);
        monsterList.put("Rot Grub", 85);
        monsterList.put("Shrieker", 95);
        monsterList.put("Skeleton", 98);
        monsterList.put("Zombie", 100);
    }

    private void setupTreasureTable() {
        treasureList.put("Copper Pieces", 25);
        treasureList.put("Silver Pieces", 30);
        treasureList.put("Electrum Pieces", 60);
        treasureList.put("Gold Pieces", 70);
        treasureList.put("Platinum Pieces", 85);
        treasureList.put("Gems", 93);
        treasureList.put("Jewellery", 97);
        treasureList.put("Magic Item", 99);
    }

    /**Adds monster to space.
     * @param monster monster
     * @param spaceNo spaceNo
     * @param type type
     **/
    public void addMonster(String monster, int spaceNo, int type) {
        int roll = monsterList.get(monster);
        if (type == 1) {
            chambers.get(spaceNo).addMonster(roll);
        } else {
            passages.get(spaceNo).addMonster(roll);
        }
        myGui.updateDescription();
    }

    /**Adds treasure to space.
     * @param treasure treasure
     * @param spaceNo spaceNo
     * @param type type
     **/
    public void addTreasure(String treasure, int spaceNo, int type) {
        int roll = treasureList.get(treasure);
        if (type == 1) {
            chambers.get(spaceNo).addTreasure(roll);
        } else {
            passages.get(spaceNo).addTreasure(roll);
        }
        myGui.updateDescription();
    }

    /**Gets list of monsters in space space.
     * @param spaceNo spaceNo
     * @param type type
     * @return monsterlist
     **/
    public ArrayList<Monster> getSpaceMonsterList(int spaceNo, int type) {
        if (type == 1) {
            return chambers.get(spaceNo).getMonsters();
        } else {
            return passages.get(spaceNo).getMonsters();
        }
    }

    /**Gets list of treasures in space.
     * @param spaceNo spaceNo
     * @param type type
     * @return treasureList
     **/
    public ArrayList<Treasure> getSpaceTreasureList(int spaceNo, int type) {
        if (type == 1) {
            return chambers.get(spaceNo).getTreasures();
        } else {
            return passages.get(spaceNo).getTreasures();
        }
    }


    /**Removes monster in space.
     * @param spaceNo spaceNo
     * @param type type
     * @param index index
     **/
    public void removeMonster(int index, int spaceNo, int type) {
        if (type == 1) {
            chambers.get(spaceNo).removeMonster(index - 1);
        } else {
            passages.get(spaceNo).removeMonster(index - 1);
        }
        myGui.updateDescription();
    }

    /**Removes treasure in space.
     * @param spaceNo spaceNo
     * @param type type
     * @param index index
     **/
    public void removeTreasure(int index, int spaceNo, int type) {
        if (type == 1) {
            chambers.get(spaceNo).removeTreasure(index - 1);
        } else {
            passages.get(spaceNo).removeTreasure(index - 1);
        }
        myGui.updateDescription();
    }


    /**Gets num passages.
     * @return passageNum
     **/
    public int getNumPassages() {
        return level.getPassageList().size();
    }

    /**Gets space dimensions.
     * @param spaceNo spaceNo
     * @param flag 1 == length, 2 == width
     * @return len/width
     **/
    public int getSpaceDimension(int spaceNo, int flag) {
        if (spaceNo <= 5) {
            if (flag == 1) {
                /*System.out.println("Len: " + chambers.get(spaceNo).getShape().getLength() / 10);*/
                return chambers.get(spaceNo).getShape().getLength() / 10;
            } else {
                /*System.out.println("Width: " + chambers.get(spaceNo).getShape().getWidth() / 10);*/
                return chambers.get(spaceNo).getShape().getWidth() / 10;
            }
        } else {
            return 0;
        }
    }

    /**Gets contents of chamber.
     * @param spaceNo spaceNo
     * @param type type
     * @return numContent
     **/
    public int getChamberContent(int spaceNo, int type) {
        /*(1 = doors, 2 = monsters, 3 = treasures) */
        if (type == 1) {
            return chambers.get(spaceNo).getDoors().size();
        } else if (type == 2) {
            return chambers.get(spaceNo).getMonsters().size();
        } else {
            return chambers.get(spaceNo).getTreasures().size();
        }
    }

    /**Gets type of passageSection.
     * @param passageNo spaceNo
     * @param sectionNo sectionNo
     * @return section type
     **/
    public int getPassageSection(int passageNo, int sectionNo) {
        return passages.get(passageNo).getPassageSection(sectionNo).getRoll();
    }

    /**Gets boolean hasMonster of passageSection.
     * @param passageNo spaceNo
     * @param sectionNo sectionNo
     * @return hasMonster
     **/
    public boolean hasMonsterPassage(int passageNo, int sectionNo) {
        return passages.get(passageNo).getPassageSection(sectionNo).isHasMonster();
    }

    /**Gets boolean hasTreasure of passageSection.
     * @param passageNo spaceNo
     * @param sectionNo sectionNo
     * @return hasTreasure
     **/
    public boolean hasTreasurePassage(int passageNo, int sectionNo) {
        return passages.get(passageNo).getPassageSection(sectionNo).isHasTreasure();
    }
}
