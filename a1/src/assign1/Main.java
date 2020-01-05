package src.assign1;

import java.util.Scanner;
import dnd.models.Treasure;
import dnd.models.ChamberShape;
import dnd.models.ChamberContents;
import dnd.models.Exit;
import dnd.models.Monster;
import dnd.models.Stairs;
import dnd.models.Trap;
import dnd.die.D20;
import dnd.exceptions.NotProtectedException;
import dnd.exceptions.UnusualShapeException;

public class Main {

    /*
     * Function checks whether user wants to generate treasure specifically or randomly
     * and calls the treasure object and generates it. It returns the description of the
     * treasure
     */
    private String getTreasureDescription(int isSpecific, Scanner input) {
        String treasureDescription;
        Treasure newTreasure = new Treasure();

        //Sets treasure content
        if (isSpecific != 0) {
            System.out.println("Generating treasure content:");
            if (isSpecific == 2) {
                if (generateRandom(input)) {
                    newTreasure.setDescription(getDiceRoll(100, input));
                } else {
                    newTreasure.setDescription();
                }
            } else {
                newTreasure.setDescription(getDiceRoll(100, input));
            }
        } else {
            newTreasure.setDescription();
        }

        treasureDescription = "The treasure is " + newTreasure.getDescription();
        System.out.println(treasureDescription);

        //Generates container for treasure
        if (isSpecific != 0) {
            System.out.println("Generating treasure container");
            if (isSpecific == 2) {
                if (generateRandom(input)) {
                    newTreasure.setContainer(getDiceRoll(20, input));
                } else {
                    newTreasure.setContainer();
                }
            } else {
                newTreasure.setContainer(getDiceRoll(20, input));
            }
        } else {
            newTreasure.setContainer();
        }
        treasureDescription += ", contained in " + newTreasure.getContainer();
        System.out.println("Container for treasure is: " + newTreasure.getContainer());

        //Generates protection if applicable
        try {
            String protection = newTreasure.getProtection();
            treasureDescription += ", and is guarded by " + protection;
            System.out.println("Protection for treasure is: " + protection);
        } catch (Exception NotProtectedException) {
            treasureDescription += ", but is not protected";
            System.out.println("Treasure not protected");
        }
        return treasureDescription;
    }

    /*
     * Function checks if user wants to generate chamber dimensions specifically and calls the
     * ChamberShape object. Generates the chamber dimensions and returns it's description
     */
    private String generateChamberShapeAndSizeDescription(int isSpecific, Scanner input) {
        String shapeDescription;
        int length = 0;
        int width = 0;
        int area = 0;
        ChamberShape chamberShape = new ChamberShape();

        //Sets chamber shape
        if (isSpecific != 0) {
            System.out.println("Setting chamber shape");
            if (isSpecific == 2) {
                if (generateRandom(input)) {
                    chamberShape.setShape(getDiceRoll(20, input));
                } else {
                    chamberShape.setShape();
                }
            } else {
                chamberShape.setShape(getDiceRoll(20, input));
            }
        } else {
            chamberShape.setShape();
        }
        shapeDescription = "Chamber shape and size:\nThe chamber is a " + chamberShape.getShape();

        //Gets length and width of chamber if applicable
        try {
            shapeDescription += " of size " + chamberShape.getLength() + " x " + chamberShape.getWidth();
        } catch (UnusualShapeException e) {
        }

        shapeDescription += " with an area of " + chamberShape.getArea() + "\n";
        System.out.print(shapeDescription);

        //Sets chamber exits
        if (isSpecific != 0) {
            System.out.println("Setting number of exits");
            if (isSpecific == 2) {
                if (generateRandom(input)) {
                    chamberShape.setNumExits(getDiceRoll(20, input));
                } else {
                    chamberShape.setNumExits();
                }
            } else {
                chamberShape.setNumExits(getDiceRoll(20, input));
            }
        } else {
            chamberShape.setNumExits();
        }

        //Gets exits description
        if (chamberShape.getNumExits() > 0) {
            shapeDescription += generateExits(chamberShape);
        } else {
            System.out.println("Chamber has no exits");
            shapeDescription += "Chamber has no exits";
        }
        return shapeDescription;
    }

    /*
     * Function generates the description of that chamber's exits
     * and returns its description
     */
    private String generateExits(ChamberShape chamberShape) {
        String exitsDescription;
        int numExits = 0;
        int counter = 0;

        numExits = chamberShape.getNumExits();
        exitsDescription = "The chamber has " + numExits + " exit(s):\n";

        //Gets description of each exit
        for (Exit exit: chamberShape.getExits()) {
            counter++;
            exitsDescription += "Exit " + counter + " is found at the ";
            exitsDescription += exit.getLocation() + " and you need to go " +  exit.getDirection() + " to exit.\n";
        }
        System.out.println(exitsDescription);
        return exitsDescription;
    }

    /*
     * Function generates what elements will be in the chamber and generates
     * a description for those elements and returns the chamber content description
     */
    private String generateChamberContents(int isSpecific, Scanner input) {
        int diceVal = 0;
        String chamberContentDescription = "Chamber Contents:\n";
        D20 dice = new D20();
        ChamberContents chamberContent = new ChamberContents();

        //Setting the chamber content elements
        diceVal = dice.roll();
        if (isSpecific == 1) {
            System.out.println("Generating chamber contents");
            diceVal = getDiceRoll(20, input);
        } else if (isSpecific == 2) {
            System.out.println("Generating chamber contents");
            if (generateRandom(input)) {
                diceVal = getDiceRoll(20, input);
            }
        }
        chamberContent.setDescription(diceVal);

        //Generating the description of those contents
        if (diceVal <= 12) {
            //empty chamber
            chamberContentDescription += "Chamber is empty\n";
            System.out.print(chamberContentDescription);
        }
        if (diceVal > 12 && diceVal < 18) {
            //generate monster
            chamberContentDescription += "- Monsters:\n";
            Monster monster = new Monster();
            if (isSpecific != 0) {
                System.out.print(chamberContentDescription);
                System.out.println("Generating monster");
                if (isSpecific == 2) {
                    if (generateRandom(input)) {
                        monster.setType(getDiceRoll(100, input));
                    } else {
                        monster.setType();
                    }
                } else {
                    monster.setType(getDiceRoll(100, input));
                }
            } else {
                monster.setType();
            }
            chamberContentDescription += "There are from " + monster.getMinNum() + " to " + monster.getMaxNum() + " " + monster.getDescription() + "s\n";
            if (diceVal <= 14) {
                //if treasure will not be generated, print out monster description
                System.out.println(chamberContentDescription);
            }
        }
        if ((diceVal > 14 && diceVal < 18) || diceVal == 20) {
            //generate treasure
            chamberContentDescription += "- Treasure:\n";
            System.out.print(chamberContentDescription);
            chamberContentDescription += getTreasureDescription(isSpecific, input) + "\n";
        }
        if (diceVal == 18) {
            //generate stairs
            chamberContentDescription += "- Stairs:\n";
            Stairs stairs = new Stairs();
            if (isSpecific != 0) {
                System.out.print(chamberContentDescription);
                System.out.println("Generating stairs");
                if (isSpecific == 2) {
                    if (generateRandom(input)) {
                        stairs.setType(getDiceRoll(20, input));
                    } else {
                        stairs.setType();
                    }
                } else {
                    stairs.setType(getDiceRoll(20, input));
                }
            } else {
                stairs.setType();
            }
            chamberContentDescription += "There are stairs which go/is a " + stairs.getDescription() + "\n";
            System.out.println(chamberContentDescription);
        }
        if (diceVal == 19) {
            //generate trap
            chamberContentDescription += "- Traps:\n";
            Trap trap = new Trap();
            if (isSpecific != 0) {
                System.out.print(chamberContentDescription);
                System.out.println("Generating trap");
                if (isSpecific == 2) {
                    if (generateRandom(input)) {
                        trap.setDescription(getDiceRoll(20, input));
                    } else {
                        trap.setDescription();
                    }
                } else {
                    trap.setDescription(getDiceRoll(20, input));
                }
            } else {
                trap.setDescription();
            }
            chamberContentDescription += "There is a trap! It's (a) " + trap.getDescription() + "\n";
            System.out.println(chamberContentDescription);
        }

        return chamberContentDescription + "\n";
    }

    /**
     * Main method which prints out menu, takes in the user input and performs specified action
     * that the dungeon master (user) wants.
     * @param args there are no arguments
     **/
    public static void main(String[] args) {
        Main main = new Main();
        Scanner input = new Scanner(System.in);
        String buffer;
        String description = "No chamber created yet";
        int inputNum = -1;

        System.out.println("=====================================================================================================");
        System.out.println("=====================================================================================================");
        System.out.println(" *                                                                                                 * ");
        System.out.println(" *                                      Welcome Dungeon Master!                                    * ");
        System.out.println(" *                                                                                                 * ");
        System.out.println("=====================================================================================================");
        System.out.println("=====================================================================================================\n");
        do {

            do {
                main.printMenu();
                buffer = input.next();
                System.out.println("=====================================================================================================");
                try {
                    inputNum = Integer.parseInt(buffer);
                    if (!main.inputIsCorrect(inputNum)) {
                        System.out.println(" Error: Input is not within range. Please try again ");
                        main.printMenu();
                        buffer = input.next();
                    }
                } catch (Exception e) {
                    System.out.println(" Error: Input is not a number. Please try again ");
                }
            } while ((!main.inputIsCorrect(inputNum)) && (inputNum != -1));

            if (inputNum == 0) {
                System.out.println("Exiting program...");
            } else if (inputNum < 9 && inputNum != -1) {
                description = main.getDescription(inputNum, input);

                do {
                    System.out.println("Press any key to continue");
                    try {
                        input.nextLine();
                        input.nextLine();
                    } catch (Exception e) {
                    }
                } while (buffer.equals(""));

                inputNum = -1;
            } else if (inputNum == 9 && inputNum != -1) {
                System.out.println(description);
                do {
                    System.out.println("Press any key to continue");
                    try {
                        input.nextLine();
                        input.nextLine();
                    } catch (Exception e) {
                    }
                } while (buffer.equals(""));
                inputNum = -1;
            }

            buffer = "";
        } while (inputNum != 0);

        input.close();
    }

    //Checks if user input for choice of action is correct
    private boolean inputIsCorrect(int inputNum) {
        boolean isCorrect;
        if (inputNum >= 0 && inputNum < 10) {
            isCorrect = true;
        } else {
            isCorrect = false;
        }
        return isCorrect;
    }

    private String getDescription(int inputNum, Scanner input) {
        String description = " ";
        /* Gets the appropriate action based on user input
         * if isSpecific is 0 - generate randomly
         * if isSpecific is 1 - generate based on dice roll input
         * if isSpecific is 2 - ask whether user wants to generate each element randomly or not
         */
        switch (inputNum) {
            case 1:  description = getTreasureDescription(0, input);
                break;
            case 2:  description = getTreasureDescription(1, input);
                break;
            case 3:  description = generateChamberShapeAndSizeDescription(0, input);
                break;
            case 4:  description = generateChamberShapeAndSizeDescription(1, input);
                break;
            case 5:  description = generateChamberContents(0, input);
                break;
            case 6:  description = generateChamberContents(1, input);
                break;
            case 7:
                description = generateChamberContents(0, input);
                description += generateChamberShapeAndSizeDescription(0, input);
                break;
            case 8:
                description = generateChamberContents(2, input);
                description += generateChamberShapeAndSizeDescription(2, input);
                break;
            default: System.out.println("Invalid input error");
                break;
        }
        return description;
    }

    //gets the value of the dice roll input from user
    private int getDiceRoll(int diceNo, Scanner input) {
        int diceRoll = 0;
        String buffer;

        while (diceRoll < 1 || diceRoll > diceNo) {
            System.out.print("Enter a value between 1 - " + diceNo + ": ");
            buffer = input.next();
            try {
                diceRoll = Integer.parseInt(buffer);
                if (diceRoll < 1 || diceRoll > diceNo) {
                    System.out.println("Error: invalid input ");
                }
            } catch (Exception e) {
                System.out.println("Error: invalid input ");
            }
        }
        return diceRoll;
    }

    //checks to see if user wants to generate this element randomly
    private boolean generateRandom(Scanner input) {
        int isRandom = -1;
        String buffer;
        Boolean generateSpecific = true;
        boolean isInputCorrect = false;
        do {
            System.out.println("Do you want to generate this section randomly?\n [0] No\t [1] Yes ");
            buffer = input.next();
            try {
                isRandom = Integer.parseInt(buffer);
                if (isRandom > 1 || isRandom < 0) {
                    System.out.println("Error: invalid input ");
                } else {
                    isInputCorrect = true;
                }
            } catch (Exception e) {
                System.out.println("Error: invalid input ");
            }
        } while (!isInputCorrect || isRandom == -1);

        if (isRandom == 0) {
            return generateSpecific;
        } else {
            return !generateSpecific;
        }
    }

    //prints out the menu
    private void printMenu() {
        System.out.println("=====================================================================================================");
        System.out.println(" *                  To perform an action input the associated number next to it                    * ");
        System.out.println("=====================================================================================================");
        System.out.println(" [1] Generate treasure description randomly");
        System.out.println(" [2] Select the specific treasure description to be generated");
        System.out.println(" [3] Generate the chamber shape and size description randomly");
        System.out.println(" [4] Select the specific chamber shape and size description to be generated");
        System.out.println(" [5] Generate the chamber contents description randomly");
        System.out.println(" [6] Select the specific chamber contents description to be generated");
        System.out.println(" [7] Randomly generate an entire chamber description");
        System.out.println(" [8] Generate the whole chamber step by step (generating each element randomly or specifically)");
        System.out.println(" [9] Print out current description");
        System.out.println(" [0] Exit");
        System.out.println("=====================================================================================================");
        System.out.print(" Input: ");
    }
}
