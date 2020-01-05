package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Display extends GridPane {
    /**
     * Floor.
     **/
    private String floor;
    /**
     * LeftWall.
     **/
    private String leftWall;
    /**
     * Treasure.
     **/
    private String treasure;
    /**
     * Corner.
     **/
    private String corner;
    /**
     * Door.
     **/
    private String door;
    /**
     * Monster.
     **/
    private String monster;
    /**
     * PassageMons.
     **/
    private String passageMons;
    /**
     * Treasure.
     **/
    private String passageTreas;
    /**
     * Straight.
     **/
    private String straight;
    /**
     * Left.
     **/
    private String left;
    /**
     * Right.
     **/
    private String right;
    /**
     * Height.
     **/
    private int height;
    /**
     * Width.
     **/
    private int width;
    /**
     * Controller.
     **/
    private Controller controller;
    /**
     * Space No.
     **/
    private int spaceNo;
    /**
     * Type.
     **/
    private int type;
    /**
     * Gui.
     **/
    private Gui gui;


    /**
     * Constructor for display.
     *
     * @param theType       type
     * @param space         spaceNo
     * @param theController controller
     * @param len           length
     * @param wid           width
     * @param theGui        gui
     **/
    public Display(int len, int wid, Controller theController, int space, int theType, Gui theGui) {
        floor = "/res/floor-tile.png";
        leftWall = "/res/wall.png";
        treasure = "/res/treasure.png";
        door = "/res/door.png";
        corner = "/res/corner-wall.png";
        monster = "/res/monster.png";
        passageMons = "/res/passage-mons.png";
        passageTreas = "/res/passage-treas.png";
        right = "/res/right.png";
        straight = "/res/straight.png";
        left = "/res/left.png";
        height = len + 2;
        width = wid + 2;

        this.controller = theController;
        this.spaceNo = space;
        this.type = theType;
        this.gui = theGui;

        /*System.out.println("Height: " + height + " Width: " + width);*/
        if (spaceNo != 0) {
            if (type == 1) {
                createChamberView();
            } else {
                createPassageView();
            }
        }
        addHandler();

    }

    /**
     * Creates a chamber display.
     **/
    public void createChamberView() {
        Node[][] tiles = makeTiles();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                add(tiles[i][j], j, i, 1, 1);
            }
        }
        addMonsterTile();
        addTreasureTile();
    }

    /* Creates an array of nodes with all image components */
    private Node[][] makeTiles() {
        /* ArrayList<Node> toReturn = new ArrayList<>();*/
        ArrayList<Node> doorNodes = new ArrayList<>();
        int size = controller.getChamberContent(spaceNo, 1);
        Node[][] toReturn = new Node[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0) {
                    if (j == 0) { /* Top left corner tile */
                        toReturn[i][j] = imageFactory(corner, 2);
                    } else if (j == (width - 1)) { /* Top right corner */
                        toReturn[i][j] = imageFactory(corner, 3);
                    } else { /* top wall */
                        if (j == width / 2 && size > 0) {
                            toReturn[i][j] = imageFactory(door);
                        } else {
                            toReturn[i][j] = imageFactory(leftWall, 1);
                        }
                    }
                } else if (i == (height - 1)) {
                    if (j == 0) { /* bottom left corner */
                        toReturn[i][j] = imageFactory(corner, 1);
                    } else if (j == width - 1) { /* bottom right corner */
                        toReturn[i][j] = imageFactory(corner);
                    } else { /* Bottom wall */
                        if (j == width / 2 && size > 1) {
                            toReturn[i][j] = imageFactory(door, 2);
                        } else {
                            toReturn[i][j] = imageFactory(leftWall, 3);
                        }
                    }
                } else {
                    if (j == 0 && i == height / 2 && size > 3) {
                        toReturn[i][j] = imageFactory(door, 3);
                    } else if (j == width - 1 && i == height / 2 && size > 2) {
                        toReturn[i][j] = imageFactory(door, 1);
                    } else if (j == 0) { /* left wall */
                        toReturn[i][j] = imageFactory(leftWall);
                    } else if (j == width - 1) { /* right wall */
                        toReturn[i][j] = imageFactory(leftWall, 2);
                    } else { /* floor tile */
                        toReturn[i][j] = imageFactory(floor);
                    }
                }
            }
        }

        return toReturn;
    }

    /**
     * Creates a passage display.
     **/
    public void createPassageView() {
        int colIndex = 0;
        int rowIndex = 2;
        int straightType = 0;
        int flag = -1;
        int rotate = 0;
        for (int i = 0; i < 5; i++) {
            int roll = controller.getPassageSection(spaceNo, i);
            if (roll == 1 || roll == 3) {
                add(imageFactory(straight, straightType), colIndex, rowIndex, 1, 1);
                addPassageContent(i, colIndex, rowIndex);
                if (straightType == 1) {
                    if (flag == 1) {
                        rowIndex--;
                    }
                    if (flag == 2) {
                        rowIndex++;
                    }
                } else {
                    colIndex++;
                }
            } else if (roll == 10) {
                add(imageFactory(left, rotate), colIndex, rowIndex, 1, 1);
                addPassageContent(i, colIndex, rowIndex);
                flag = 1;
                if (straightType == 1) {
                    colIndex++;
                } else {
                    rowIndex--;
                }
                straightType++;
                rotate += 3;
            } else if (roll == 12) {
                add(imageFactory(right, rotate), colIndex, rowIndex, 1, 1);
                addPassageContent(i, colIndex, rowIndex);
                flag = 2;
                if (straightType == 1) {
                    colIndex++;
                } else {
                    rowIndex++;
                }
                straightType++;
                rotate += 1;
            }

        }

    }

    private void addPassageContent(int i, int colIndex, int rowIndex) {
        if (controller.hasMonsterPassage(spaceNo, i)) {
            add(imageFactory(passageMons), colIndex, rowIndex, 1, 1);
        }
        if (controller.hasTreasurePassage(spaceNo, i)) {
            add(imageFactory(passageTreas), colIndex, rowIndex, 1, 1);
        }
    }


    private void addMonsterTile() {
        Random random = new Random();
        for (int i = 0; i < controller.getChamberContent(spaceNo, 2); i++) {
            this.add(imageFactory(monster),
                    random.nextInt(width - 2) + 1,
                    random.nextInt(height - 2) + 1,
                    1, 1);
        }
    }

    private void addTreasureTile() {
        Random random = new Random();
        for (int i = 0; i < controller.getChamberContent(spaceNo, 3); i++) {
            this.add(imageFactory(treasure),
                    random.nextInt(width - 2) + 1,
                    random.nextInt(height - 2) + 1,
                    1, 1);
        }

    }


    private Node imageFactory(String img, int... rotate) {
        int size = 75;
        Image image = new Image(getClass().getResourceAsStream(img));
        Label toReturn = new Label();
        toReturn.setId("display-btn");
        ImageView imageView = new ImageView(image);
        imageView.setPickOnBounds(true);
        /*toReturn.setOnAction((ActionEvent e) -> {
            System.out.println("Tile pressed ");
            createDisplayPopup().show(gui.getPrimaryStage());
        });*/
        if (rotate.length > 0) {
            if (rotate[0] == 0) {
                imageView.setRotate(0);
            } else if (rotate[0] == 1) {
                imageView.setRotate(90);
            } else if (rotate[0] == 2) {
                imageView.setRotate(180);
            } else {
                imageView.setRotate(270);
            }
        }
        if (type == 2) {
            size = 100;
        }
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        toReturn.setGraphic(imageView);
        return toReturn;
    }

    private Popup createDisplayPopup() {
        Popup displayPopup = new Popup();
        displayPopup.setAutoHide(true);
        ScrollPane scroll = new ScrollPane();
        Text descriptionSec = new Text(loadDesc());
        descriptionSec.setTextAlignment(TextAlignment.CENTER);
        descriptionSec.setWrappingWidth(500);
        descriptionSec.setId("description-sec");
        scroll.setContent(descriptionSec);
        Button close = new Button("Close");
        close.setOnAction((ActionEvent event) -> {
            displayPopup.hide();
        });
        close.setId("space-buttons");
        VBox content = new VBox(descriptionSec, scroll, close);
        content.setId("content-section-popup");
        displayPopup.getContent().add(content);
        return displayPopup;
    }

    private String loadDesc() {
        String desc = controller.getDesc(type, spaceNo) + "\n";
        HashMap<Integer, String> doors = controller.loadDoorDesc(spaceNo, type);
        for (int i = 1; i <= doors.size(); i++) {
            desc += "Door " + i + "\n" + doors.get(i) + "\n";
        }
        return desc;
    }

    private void addHandler() {
        this.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                createDisplayPopup().show(gui.getPrimaryStage());
            }
        });
    }


}
