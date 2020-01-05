package dungeon;


import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class PassageTest {
    //you don't have to use instance variables but it is easier
    // in many cases to have them and use them in each test
    private Passage testerOne;
    private Passage testerTwo;
    private PassageSection sectionOne;
    private PassageSection sectionTwo;
    private Door door;

    public PassageTest() {

    }

    @Before
    public void setup() {
        //set up any instance variables here so that they have fresh values for every test
        door = new Door();
        /*System.out.println(door.getDescription());*/
        testerOne = new Passage();
        testerTwo = new Passage(door);
        sectionOne = new PassageSection();
        sectionTwo = new PassageSection(3); //section with door
    }

    @Test
    /* Tests constructor 2 and getDoors methods */
    public void testGetDoors() {
        System.out.println("testGetDoors");
        ArrayList<Door> doors = testerTwo.getDoors();
        assertEquals(door.getDescription(),doors.get(0).getDescription());
    }

    @Test
    /* Tests constructor 2 and getDoor method */
    public void testGetDoor() {
        System.out.println("testGetDoor");
        Door door2 = new Door();
        testerTwo.addDoor(door2);
        assertEquals(door2,testerTwo.getDoor(1));
    }

    @Test
    /* Tests the add door method */
    public void testAddDoor() {
        System.out.println("testAddDoor");
        testerTwo.addDoor(new Door());
        ArrayList<Door> doors = testerTwo.getDoors();
        assertEquals(2, doors.size());
    }

    @Test
    /* test the add passage section method and getDoors (passage section has a door so checks if that door is also added)*/
    public void testAddPassageSectionOne() {
        System.out.println("testAddPassageSectionOne");
        testerTwo.addPassageSection(sectionTwo);
        ArrayList<Door> doors = testerTwo.getDoors();
        assertEquals(2, doors.size());
    }

    @Test
    /* test the add passage section method */
    public void testAddPassageSectionTwo() {
        System.out.println("testAddPassageSectionTwo");
        testerTwo.addPassageSection(sectionOne);
        int num = testerTwo.getPassageSize();
        assertEquals(1, num);
    }

    @Test
    /* test the add passage section method */
    public void testAddPassageSectionThree() {
        System.out.println("testAddPassageSectionThree");
        testerTwo.addPassageSection(sectionOne);
        testerTwo.addPassageSection(sectionTwo);
        int num = testerTwo.getPassageSize();
        assertEquals(2, num);
    }

    @Test
    /* Tests getPassageSection */
    public void testgetPassageSection() {
        System.out.println("testgetPassageSection");
        testerTwo.addPassageSection(sectionOne);
        testerTwo.addPassageSection(sectionTwo);
        PassageSection temp = testerTwo.getPassageSection(1);
        assertEquals(sectionTwo, temp);
    }

    @Test
    /* Tests getDescription */
    public void testGetDescription() {
        System.out.print("testGetDescription ->");
        testerTwo.addPassageSection(sectionTwo);
        String desc = sectionTwo.getDescription();
        System.out.println(desc);
        String passageDesc = testerTwo.getDescription();
        assertEquals(true, passageDesc.contains(desc));
    }
}
