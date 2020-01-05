package dungeon;

import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.DnDElement;
import dnd.models.Monster;
import dnd.models.Stairs;
import dnd.models.Trap;
import dnd.models.Treasure;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChamberTest {
    private Chamber chamber;
    private ChamberShape size;

    public ChamberTest() {
    }

    @Before
    public void setup() {
        chamber = new Chamber();
        size = ChamberShape.selectChamberShape(5);
    }

    @Test
    /* Tests getDoors by seeing if it can get the description of the first door */
    public void testGetDoorsOne() {
        System.out.print("testGetDoors->");
        ArrayList<Door> doors = chamber.getDoors();
        String desc = doors.get(0).getDescription();
        System.out.println(desc);
        Boolean result = false;
        if (desc != "") {
            result = true;
        }
        assertTrue(result);
    }

    @Test
    /* Tests getMonster and addMonster by seeing if it can get the description of the last monster */
    public void testGetMonster() {
        System.out.print("testGetMonster->");
        Monster monster = new Monster();
        String expString = monster.getDescription();
        chamber.addMonster(1);
        String desc = chamber.getMonsters().get(chamber.getMonsters().size() - 1).getDescription();
        System.out.println(desc);
        assertEquals(true, desc.contains(expString));
    }

    @Test
    /* Tests getTreasure and addTreasure by seeing if it can get the description of the last treasure */
    public void testGetTreasure() {
        System.out.print("testGetTreasure->");
        Treasure treasure = new Treasure();
        chamber.addTreasure(1);
        String desc = chamber.getTreasures().get(chamber.getTreasures().size() - 1).getDescription();
        Boolean result = false;
        if (desc != "") {
            System.out.println(desc);
            result = true;
        }
        assertTrue(result);
    }

    @Test
    /* Tests addDoor and getDoor and checks the size */
    public void testAddDoor() {
        System.out.println("testAddDoor");
        int size = chamber.getDoors().size();
        chamber.addDoor(new Door());
        ArrayList<Door> doors = chamber.getDoors();
        assertEquals(size + 1, doors.size());
    }

    @Test
    /* Tests getDescription and prints it out */
    public void testGetDescription() {
        System.out.print("testGetDescription ->");
        String desc = chamber.getDescription();
        System.out.println(desc);
        assertEquals(true, desc.contains("chamber"));
    }

}