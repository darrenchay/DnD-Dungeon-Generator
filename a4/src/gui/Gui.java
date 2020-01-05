package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;

import javafx.scene.control.ScrollPane;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;


public class Gui<toReturn> extends Application {
    /**
     * Controller.
     **/
    private Controller controller;
    /**
     * Stage.
     **/
    private Stage primaryStage;
    /**
     * Display.
     **/
    private Display display;
    /**
     * Description.
     **/
    private Text descriptionSec;
    /**
     * Accordion.
     **/
    private VBox accordion;
    /**
     * Center.
     **/
    private VBox center;
    /**
     * Space Name.
     **/
    private Label spaceName;
    /**
     * File Chooser.
     **/
    private FileChooser fileChooser;
    /**
     * Popup Factory.
     **/
    private PopupFactory popupFactory;
    /**
     * Edit popup.
     **/
    private Popup editPopup;
    /**
     * Space No.
     **/
    private int spaceNo;
    /**
     * Type.
     **/
    private int type;
    /**
     * Edit button.
     **/
    private Button editBtn;
    /**
     * Root pane.
     **/
    private BorderPane rootPane;

    /**
     * Start stage.
     *
     * @param assignedStage stage
     **/
    @Override
    public void start(Stage assignedStage) {
        primaryStage = assignedStage;
        setController(new Controller(this));
        setupFileChooser();
        setupScene(controller, primaryStage);
    }

    /**Sets scene again (for loading controller).
     * @param theController controller
     * @param assignedStage stage**/
    public void setupScene(Controller theController, Stage assignedStage) {
        primaryStage = assignedStage;
        setController(theController);
        initGui();
        setUpRoot();
        Scene scene = new Scene(rootPane, 1000, 800);
        setupSceneStyleSheet(scene);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dungeon Generator");
        primaryStage.show();
    }

    private void setController(Controller theController) {
        controller = theController;
    }

    private void initGui() {
        popupFactory = new PopupFactory(controller, this);
        editPopup = popupFactory.getEditPopup();
    }

    private void setUpRoot() {
        rootPane = new BorderPane();
        Node menuSec = setupMenuSection();
        Node contentSec = setupContentSection();
        rootPane.setTop(menuSec);
        rootPane.setCenter(contentSec);
    }

    private void setupFileChooser() {
        fileChooser = new FileChooser();
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        fileChooser.setInitialDirectory(new File(currentPath));
    }

    /**
     * Gets stage.
     *
     * @return primaryStage
     **/
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Gets display.
     *
     * @return display
     **/
    public Display getDisplay() {
        return display;
    }

    private void setupSceneStyleSheet(Scene scene) {
        URL uri = getClass().getResource("/res/style.css");
        String path = uri.toString();
        scene.getStylesheets().add(path);
    }

    private Node setupMenuSection() {
        MenuBar menu = setupMenu();
        HBox menuSection = new HBox(menu);
        menu.prefWidthProperty().bind(primaryStage.widthProperty());
        return menuSection;
    }

    private MenuBar setupMenu() {
        MenuBar menuBar = new MenuBar();

        Menu file = new Menu("File");

        MenuItem save = new MenuItem("Save");
        save.setOnAction((ActionEvent event) -> {
            controller.save(fileChooser.showSaveDialog(primaryStage));
        });
        MenuItem load = new MenuItem("Load");
        load.setOnAction((ActionEvent event) -> {
            controller.load(fileChooser.showOpenDialog(primaryStage));
        });

        file.getItems().add(save);
        file.getItems().add(load);

        menuBar.getMenus().add(file);
        return menuBar;
    }


    private Node setupContentSection() {
        VBox spacesList = setupSpacesList();
        VBox displaySec = setupDisplaySection();
        VBox doorsSec = setupRightSection();
        HBox contentSec = new HBox(spacesList, displaySec, doorsSec);
        contentSec.setSpacing(20);
        contentSec.setId("content-section");
        HBox.setHgrow(spacesList, Priority.ALWAYS);
        HBox.setHgrow(displaySec, Priority.ALWAYS);
        HBox.setHgrow(contentSec, Priority.ALWAYS);
        return contentSec;

    }

    private VBox setupSpacesList() {
        VBox spacesList = new VBox(10);
        Label header = new Label("Spaces List");
        header.setAlignment(Pos.TOP_CENTER);
        header.setId("header-label");
        spacesList.getChildren().add(header);
        createButtons(spacesList);
        spacesList.setMinWidth(150);
        spacesList.setId("spaces-list");
        return spacesList;
    }

    private VBox createButtons(VBox leftSec) {
        for (int i = 1; i <= 5; i++) {
            ToggleButton button = new ToggleButton("Chamber " + i);
            button.setId("space-buttons");
            int finalI = i;
            button.setOnAction((ActionEvent event) -> {
                triggerButtonAction(finalI, 1);
            });
            leftSec.getChildren().add(button);
        }
        for (int i = 1; i <= controller.getNumPassages(); i++) {
            ToggleButton button = new ToggleButton("Passage " + i);
            button.setId("space-buttons");
            int finalI = i;
            button.setOnAction((ActionEvent event) -> {
                triggerButtonAction(finalI, 2);
            });
            leftSec.getChildren().add(button);
        }
        return leftSec;
    }

    private void triggerButtonAction(int space, int theType) {
        this.spaceNo = space;
        this.type = theType;

        center.getChildren().remove(1);
        display = new Display(controller.getSpaceDimension(spaceNo, 1), controller.getSpaceDimension(spaceNo, 2), controller, spaceNo, type, this);
        display.alignmentProperty().setValue(Pos.CENTER);
        center.getChildren().add(1, display);
        updateDescription();
        accordion = loadAccordion();
        popupFactory.setPopupSpace(spaceNo, type);
        editBtn.setVisible(true);
    }

    /**
     * Updates description.
     **/
    public void updateDescription() {
        descriptionSec.setText(controller.getDesc(type, spaceNo));
        if (type == 1) {
            spaceName.setText("Chamber " + spaceNo);
        } else {
            spaceName.setText("Passage " + spaceNo);
        }
    }

    private VBox setupDisplaySection() {
        spaceName = new Label("Dungeon Generator");
        spaceName.setAlignment(Pos.TOP_LEFT);
        spaceName.setId("space-name-header");

        display = new Display(0, 0, controller, 0, 0, this);
        display.alignmentProperty().setValue(Pos.CENTER);
        /* Gets description of first chamber as init display */
        ScrollPane scroll = new ScrollPane();
        descriptionSec = new Text("Welcome to the dungeon generator!\n"
                + "Click on a space to see its contents and description here!\n"
                + "You can also save or load dungeon levels using the save menu at the top\n");
        descriptionSec.setTextAlignment(TextAlignment.CENTER);
        descriptionSec.setWrappingWidth(500);
        descriptionSec.setId("description-sec");
        scroll.setContent(descriptionSec);
        Separator separator = new Separator(Orientation.HORIZONTAL);
        center = new VBox(spaceName, display, separator, scroll);
        center.setId("center-section");
        return center;

    }

    private VBox setupRightSection() {

        VBox doorsDesc = setupDoorsSection();
        doorsDesc.setMinHeight(320);
        Label label = new Label("Doors List");
        label.setId("header-label");
        Separator separator = new Separator(Orientation.HORIZONTAL);
        VBox editBtns = setupEditSection();
        VBox right = new VBox(label, doorsDesc, separator, editBtns);
        VBox.setVgrow(label, Priority.ALWAYS);
        VBox.setVgrow(doorsDesc, Priority.ALWAYS);
        VBox.setVgrow(separator, Priority.ALWAYS);
        VBox.setVgrow(editBtns, Priority.ALWAYS);

        right.setSpacing(20);

        return right;
    }

    private VBox setupDoorsSection() {
        accordion = new VBox();
        accordion.setMinWidth(200);
        return accordion;
    }

    private VBox loadAccordion() {
        accordion.getChildren().clear();

        HashMap doors = controller.loadDoorDesc(spaceNo, type);
        int size = doors.size();
        for (int i = 1; i <= size; i++) {
            Label label = new Label((String) doors.get(i));
            label.setWrapText(true);
            label.setMaxWidth(200);

            VBox content = new VBox(10);
            content.getChildren().add(label);
            ArrayList<Integer> doorConnections = controller.getSpaceDoors(spaceNo, type).get(i);
            /*System.out.println("Size doorConnections: " + doorConnections.size());*/
            for (int j = 0; j < doorConnections.size(); j++) {
                ToggleButton button;
                int localType;
                if (type == 1) {
                    button = new ToggleButton("Passage " + doorConnections.get(j));
                    localType = 2;
                } else {
                    button = new ToggleButton("Chamber " + doorConnections.get(j));
                    localType = 1;
                }
                button.setId("buttons-smaller");
                int finalJ = j;
                button.setOnAction((ActionEvent event) -> {
                    triggerButtonAction(doorConnections.get(finalJ), localType);
                });
                content.getChildren().add(button);
            }
            TitledPane pane = new TitledPane("Door " + i, content);
            pane.setExpanded(false);
            accordion.getChildren().add(pane);
        }
        return accordion;
    }

    private VBox setupEditSection() {
        editBtn = new Button("Edit");
        editBtn.setOnAction((ActionEvent event) -> {
            popupFactory.setPopupSpace(spaceNo, type);
            showPopup();
        });
        editBtn.setVisible(false);
        editBtn.setId("space-buttons");
        return new VBox(editBtn);
    }

    private void showPopup() {
        if (type == 1) {
            /*editPopup = setupPopupMonster();*/
            editPopup.show(primaryStage);
        } else {
            /*editPopup = setupPopupTreasure();*/
            editPopup.show(primaryStage);
        }
    }

    /**
     * Launch.
     *
     * @param args args
     **/
    public static void main(String[] args) {
        launch(args);
    }

}
