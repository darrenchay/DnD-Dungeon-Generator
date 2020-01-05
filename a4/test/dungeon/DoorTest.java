package dungeon;

import dnd.models.Trap;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class DoorTest {
    private Door doorOne;
    private Door doorTwo;
    private boolean outputResult;

    public DoorTest() {
    }

    @Before
    public void setup() {
        doorOne = new Door();
        doorTwo = new Door(true);
    }

    /**
     * Test of addTrap method to archway, of class Door.
     */
    @Test
    public void testAddTrapToArchway() {
        System.out.println("add trap to archway");
        doorTwo.addTrap();
        assertEquals(false, doorTwo.isTrapped());
    }

    /**
     * Test of addTrap method, of class Door.
     */
    @Test
    public void testAddTrapped() {
        System.out.println("add trap");
        doorOne.setArchway(false);
        doorOne.addTrap();
        assertEquals(true, doorOne.isTrapped());
    }

    /**
     * Test of isArchway and setArchway method, of class Door.
     * Check case archway is true;
     */
    @Test
    public void testIsArchway() {
        System.out.println("isArchway");
        doorOne.setArchway(true);
        boolean result = doorOne.isArchway();
        assertEquals(true, result);
    }

    /**
     * Test of isOpen and setOpen method, of class Door.
     * Check case isOpen is true;
     */
    @Test
    public void testIsOpen() {
        System.out.println("isOpen");
        doorOne.setOpen(true);
        boolean result = doorOne.isOpen();
        assertEquals(true, result);
    }

    /**
     * Test of isLinked method, of class Door.
     * Check case isLinked is true;
     */
    @Test
    public void testIsLinked() {
        System.out.println("isLinked");
        doorOne.setLinked(true);
        boolean result = doorOne.isLinked();
        assertEquals(true, result);
    }

    /**
     * Test of constructor with parameter of class Door.
     * Check case archway is true;
     */
    @Test
    public void testDoorConstructorWithParameter() {
        System.out.println("Door Constructor Archway");
        boolean result = doorTwo.isArchway();
        assertEquals(true, result);
    }

    /**
     * Test of getTrapDescription method, of class Door.
     */
    @Test
    public void testGetTrapDescription() {
        System.out.print("getTrapDescription: Desc ->");
        doorOne.setArchway(false);
        doorOne.addTrap();
        String result = doorOne.getTrapDescription();
        if (result != null) {
            outputResult = true;
        }
        System.out.println(result);
        assertTrue(outputResult);
    }

    /**
     * Test of addSpace and getSpaces method, of class Door.
     * Case: Adds 2 chambers and a passage
     */
    @Test
    public void testAddSpaceOne() {
        System.out.println("addSpaceOne");
        Chamber c = new Chamber();
        Chamber c1 = new Chamber();
        Passage p = new Passage();
        doorOne.addSpace(c);
        doorOne.addSpace(c1);
        doorOne.addSpace(p);

        ArrayList<Space> result = doorOne.getSpaces();
        /*for(Space space : result) {
            System.out.println("Space: " + space.getDescription());
        }*/
        int expResult = 3;
        assertEquals(expResult, result.size());
    }

    /**
     * Test of getSpaces method, of class Door.
     */
    @Test
    public void testAddSpaceTwo() {
        System.out.println("addSpaceTwo");
        doorOne.addSpace(null);
        ArrayList<Space> result = doorOne.getSpaces();
        int expResult = 0;
        assertEquals(expResult, result.size());

    }


    /**
     * Test of getDescription method, of class Door.
     */
    @Test
    public void testGetDescriptionOne() {
        System.out.print("getDescriptionOne: Desc ->");
        String expResult = "archway";
        String result = doorTwo.getDescription();
        System.out.println(result);
        assertTrue(result.contains(expResult));

    }

    /**
     * Test of getDescription method, of class Door.
     */
    @Test
    public void testGetDescriptionTwo() {
        System.out.print("getDescriptionTwo: Desc ->");
        String result = doorOne.getDescription();
        if (result != null) {
            outputResult = true;
        }
        System.out.println(result);
        assertTrue(outputResult);

    }

    @Test
    public void testEditDescription() {
        System.out.print("testEditDescription ->");
        String description = " Testing \n";
        doorOne.editDescription(description);
        String desc = doorOne.getDescription();
        System.out.println(desc);
        assertEquals(true, desc.contains(desc));
    }
}
