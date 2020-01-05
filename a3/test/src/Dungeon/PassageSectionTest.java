package src.Dungeon;

import dnd.models.Exit;
import dnd.models.Monster;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;


public class PassageSectionTest {
    private PassageSection section1;
    private PassageSection section2;
    private boolean resultOutput = false;

    public PassageSectionTest() {

    }
    @Before
    public void setup() {
        section1 = new PassageSection();
    }

    /**
     * Test of getDoor method, of class PassageSection and constructor 2.
     */
    @Test
    public void testGetDoorOne() {
        System.out.println("testGetDoorOne");
        section2 = new PassageSection(7);
        Door d =section2.getDoor();

        if(d != null){
            resultOutput = true;
        }
        assertEquals(true, resultOutput);
    }

    /**
     * Test of getDoor method, of class PassageSection and constructor 2.
     */
    @Test
    public void testGetDoorTwo() {
        System.out.println("testGetDoorTwo");
        section2 = new PassageSection(18);
        Door d =section2.getDoor();

        if(d == null){
            resultOutput = true;
        }
        assertEquals(true, resultOutput);
    }

    /**
     * Test of getMonster method, of class PassageSection.
     */
    @Test
    public void testGetMonsterOne() {
        System.out.print("getMonster ->");
        section2 = new PassageSection(20);
        Monster m = section2.getMonster();
        System.out.println(section2.getDescription());
        boolean result = ( m != null);
        boolean expResult = true;
        assertEquals(expResult, result);
    }
    /**
     * Test of getMonster method, of class PassageSection.
     * Shouldn't have a monster and should return null
     */
    @Test
    public void testGetMonsterTwo() {
        System.out.println("getMonster");
        section2 = new PassageSection(10);
        Monster m = section2.getMonster();
        boolean result = ( m != null);
        boolean expResult = false;
        assertEquals(expResult, result);
    }


    /**
     * Test of getDescription method, of class PassageSection.
     */
    @Test
    public void testGetDescription() {
        System.out.print("getDescription: ");
        section2 = new PassageSection(14);
        String expResult = "archway";
        String result = section2.getDescription();
        System.out.println(result);
        assertTrue(result.contains(expResult));

    }

    /**
     * Test of getDescription method, of class PassageSection.
     */
    @Test
    public void testGetDescriptionWithMonster() {
        System.out.print("getDescriptionWithMonster: ");
        section2 = new PassageSection(20);
        String expResult = "There are";
        String result = section2.getDescription();
        System.out.println(result);
        assertTrue(result.contains(expResult));

    }

    /**
     * Test of editDescription method, of class PassageSection.
     */
    @Test
    public void testEditDescription() {
        System.out.print("editDescription: ");
        String result = section1.getDescription();
        String desc = "testing adding a desc";
        section1.editDescription(desc);
        result = section1.getDescription();
        System.out.println(result);
        assertTrue(result.contains(desc));

    }

    /**
     * Test of isDeadEnd method, of class PassageSection.
     */
    @Test
    public void testDeadEndOne() {
        System.out.println("isDeadEnd One");
        section2 = new PassageSection(18);
        assertEquals(true, section2.isDeadEnd());
    }

    /**
     * Test of isDeadEnd method, of class PassageSection.
     */
    @Test
    public void testDeadEndTwo() {
        System.out.println("isDeadEnd Two");
        section2 = new PassageSection(5);
        assertEquals(false, section2.isDeadEnd());
    }

    /**
     * Test of isEndOfPassage method, of class PassageSection.
     */
    @Test
    public void testEndPassageOne() {
        System.out.println("isEndOfPassageOne");
        section2 = new PassageSection(5);
        assertEquals(true, section2.isEndOfPassage());
    }

    /**
     * Test of isEndOfPassage method, of class PassageSection.
     */
    @Test
    public void testEndPassageTwo() {
        System.out.println("isEndOfPassageTwo");
        section2 = new PassageSection(20);
        assertEquals(false, section2.isEndOfPassage());
    }

    /**
     * Test of hasDoor method, of class PassageSection.
     * Prints out door description
     */
    @Test
    public void testhasDoorOne() {
        System.out.print("hasDoorOne: ->");
        section2 = new PassageSection(5);
        System.out.println(section2.getDoor().getDescription());
        assertEquals(true, section2.hasDoor());
    }

    /**
     * Test of hasDoor method, of class PassageSection.
     */
    @Test
    public void testhasDoorTwo() {
        System.out.println("hasDoor");
        section2 = new PassageSection(18);
        assertEquals(false, section2.hasDoor());
    }
    
}
