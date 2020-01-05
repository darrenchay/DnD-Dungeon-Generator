package src.Dungeon;

import java.util.List;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import username.Door;

public class TestRunner {
	public static void main(String [] args) {
        Result result;
        List<Failure> failedList;

        /* CHAMBER TEST */
        System.out.println("\n===============ChamberTest====================");
		result = JUnitCore.runClasses(ChamberTest.class);
        System.out.println("\n*****Failed Test Report****\n");
        failedList = result.getFailures();
        failedList.forEach(f -> {
            System.out.println(f);
        });
        System.out.println("Number of Failed Tests = " + result.getFailureCount());
        System.out.println("===========================================");

        /* DOOR TEST */
        System.out.println("\n===============DoorTest====================");
        result = JUnitCore.runClasses(DoorTest.class);
        System.out.println("\n*****Failed Test Report****\n");
        failedList = result.getFailures();
        failedList.forEach(f -> {
            System.out.println(f);
        });
        System.out.println("Number of Failed Tests = " + result.getFailureCount());
            System.out.println("===========================================");

        /* PASSAGE SECTION TESTS */
            System.out.println("\n===============PassageSectionTest====================");
        result = JUnitCore.runClasses(PassageSectionTest.class);
        System.out.println("\n*****Failed Test Report****\n");
        failedList = result.getFailures();
        failedList.forEach(f -> {
                System.out.println(f);
        });
        System.out.println("Number of Failed Tests = " + result.getFailureCount());
            System.out.println("\n=====================================================");

        /* PASSAGE TESTS */
        System.out.println("\n====================PassageTest=========================");
        result = JUnitCore.runClasses(PassageTest.class);
        System.out.println("\n*****Failed Test Report****\n");
        failedList = result.getFailures();
        failedList.forEach(f -> {
            System.out.println(f);
        });
        System.out.println("Number of Failed Tests = " + result.getFailureCount());
        System.out.println("\n=====================================================");


    }
}