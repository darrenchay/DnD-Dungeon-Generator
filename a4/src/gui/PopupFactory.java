package gui;

import dnd.models.Monster;
import dnd.models.Treasure;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;


public class PopupFactory {
    /**Popup edit.**/
    private Popup editPopup;
    /**Popup confirm.**/
    private Popup confirmPopup;
    /**Controller.**/
    private Controller controller;
    /**Treasure List.**/
    private ComboBox<String> treasureList;
    /**Monster List.**/
    private ComboBox<String> monsterList;
    /**Space Monster list.**/
    private ComboBox<Integer> spaceMonsterList;
    /**Space treasure list.**/
    private ComboBox<Integer> spaceTreasureList;
    /**Popup header.**/
    private Label popupHeader;
    /**Gui.**/
    private Gui gui;
    /**SpaceNo.**/
    private int spaceNo = 1;
    /**Type.**/
    private int type = 1;
    /**Action No.**/
    private int action = 0;

    /**Constructor for editPopup.
     * @param theGui gui
     * @param theController controller**/
    public PopupFactory(Controller theController, Gui theGui) {
        controller = theController;
        gui = theGui;
        editPopup = new Popup();
        createMonsterList();
        createTreasureList();
        createConfirmPopup();
        setupEditPopup();

    }

    /**Setup space no.
     * @param space spaceNo
     * @param theType type**/
    public void setPopupSpace(int space, int theType) {
        this.spaceNo = space;
        this.type = theType;
        if (type == 2) {
            popupHeader.setText("Passage " + spaceNo);
        } else {
            popupHeader.setText("Chamber " + spaceNo);
        }
        setupEditPopup();
    }

    /**Returns edit popup.
     * @return popup**/
    public Popup getEditPopup() {
        return editPopup;
    }

    private void setupEditPopup() {
        editPopup.setX(900);
        editPopup.setY(200);
        BorderPane popupBox = new BorderPane();
        popupBox.setId("content-section-popup");
        popupBox.setMinWidth(400);
        popupBox.setMinHeight(500);

        VBox buttonSection = setupButtonSection();

        popupHeader = new Label("Space");
        popupHeader.setId("header-label-popup");

        Button close = new Button("Close");
        close.setId("space-buttons");
        close.setOnAction((ActionEvent event) -> {
            editPopup.hide();
        });

        close.setAlignment(Pos.BOTTOM_RIGHT);

        popupBox.setTop(popupHeader);
        popupBox.setCenter(buttonSection);
        popupBox.setBottom(close);

        editPopup.getContent().addAll(popupBox);
    }

    private VBox setupButtonSection() {
        VBox buttonSection = new VBox(10);

        Label labelAddMonster = new Label("To add monster, select monster from list and click add monster");
        labelAddMonster.setId("popup-labels");
        HBox addMonsterSection = setupAddMonsterSection();

        Label labelAddTreasure = new Label("To add treasure, select treasure from list and click add treasure");
        labelAddTreasure.setId("popup-labels");
        HBox addTreasureSection = setupAddTreasureSection();


        VBox removeMonsterSection = setupRemoveMonsterSection();

        VBox removeTreasureSection = setupRemoveTreasureSection();

        buttonSection.getChildren().addAll(labelAddMonster, addMonsterSection, labelAddTreasure, addTreasureSection, removeMonsterSection, removeTreasureSection);

        return buttonSection;
    }

    private HBox setupAddMonsterSection() {
        HBox addMonsterSection = new HBox(10);

        Button addMonster = new Button("Add Monster");
        addMonster.setId("space-buttons");
        addMonster.setOnAction((ActionEvent event) -> {
            action = 1;
            confirmPopup.show(gui.getPrimaryStage());
        });

        addMonsterSection.getChildren().addAll(monsterList, addMonster);
        return addMonsterSection;
    }

    private HBox setupAddTreasureSection() {
        HBox addTreasureSection = new HBox(10);

        Button addTreasure = new Button("Add Treasure");
        addTreasure.setId("space-buttons");
        addTreasure.setOnAction((ActionEvent event) -> {
            action = 2;
            confirmPopup.show(gui.getPrimaryStage());
        });

        addTreasureSection.getChildren().addAll(treasureList, addTreasure);
        return addTreasureSection;
    }

    private VBox setupRemoveMonsterSection() {
        VBox removeMonsterSection = new VBox(10);
        HBox contentSec = new HBox(10);

        Label labelRemoveMonster = new Label("To remove a monster, select the monster's index(or section)\n"
                + "from list and click remove monster");
        labelRemoveMonster.setId("popup-labels");

        createSpaceMonstersList();
        Button removeMonster = new Button("Remove Monster");
        removeMonster.setId("space-buttons");
        removeMonster.setOnAction((ActionEvent event) -> {
            action = 3;
            confirmPopup.show(gui.getPrimaryStage());
        });
        if (controller.getSpaceMonsterList(spaceNo, type).size() > 0) {
            removeMonsterSection.getChildren().add(labelRemoveMonster);
            contentSec.getChildren().add(spaceMonsterList);
            contentSec.getChildren().add(removeMonster);
            removeMonsterSection.getChildren().add(contentSec);
        }
        return removeMonsterSection;
    }

    private VBox setupRemoveTreasureSection() {
        VBox removeTreasureSection = new VBox(10);
        HBox contentSec = new HBox(10);

        Label labelRemoveTreasure = new Label("To remove treasure, select the treasure's index(or section)\n"
                + "from list and click remove treasure");
        labelRemoveTreasure.setId("popup-labels");

        createSpaceTreasuresList();
        Button removeTreasure = new Button("Remove Treasure");
        removeTreasure.setId("space-buttons");
        removeTreasure.setOnAction((ActionEvent event) -> {
            action = 4;
            confirmPopup.show(gui.getPrimaryStage());
        });
        if (controller.getSpaceTreasureList(spaceNo, type).size() > 0) {
            removeTreasureSection.getChildren().add(labelRemoveTreasure);
            contentSec.getChildren().add(spaceTreasureList);
            contentSec.getChildren().add(removeTreasure);
            removeTreasureSection.getChildren().add(contentSec);
        }
        return removeTreasureSection;
    }

    private void createMonsterList() {
        monsterList = new ComboBox<String>();
        for (String string : controller.getMonsterList().keySet()) {
            monsterList.getItems().add(string);
        }
        monsterList.setValue("Orc");
    }

    private void createTreasureList() {
        treasureList = new ComboBox<String>();
        for (String string : controller.getTreasureList().keySet()) {
            treasureList.getItems().add(string);
        }
        treasureList.setValue("Jewellery");
    }

    private void createSpaceMonstersList() {
        spaceMonsterList = new ComboBox<Integer>();
        int i = 1;
        for (Monster monster : controller.getSpaceMonsterList(spaceNo, type)) {
            spaceMonsterList.getItems().add(i);
            i++;
        }
        spaceMonsterList.setValue(1);
    }

    private void createSpaceTreasuresList() {
        spaceTreasureList = new ComboBox<Integer>();
        int i = 1;
        for (Treasure treasure : controller.getSpaceTreasureList(spaceNo, type)) {
            spaceTreasureList.getItems().add(i);
            i++;
        }
        spaceTreasureList.setValue(1);
    }

    private void createConfirmPopup() {
        confirmPopup = new Popup();
        confirmPopup.setX(900);
        confirmPopup.setY(200);

        VBox confirmPane = new VBox(50);
        confirmPane.setId("content-section-popup");
        confirmPane.setMinSize(150, 150);

        Label confirmMsg = new Label("Do you want to apply those changes?");
        confirmMsg.setId("popup-labels");
        confirmPane.getChildren().add(confirmMsg);

        HBox buttons = createConfirmButtons();
        buttons.setAlignment(Pos.BOTTOM_CENTER);

        confirmPane.getChildren().add(buttons);
        confirmPopup.getContent().add(confirmPane);
    }

    private HBox createConfirmButtons() {
        HBox buttons = new HBox(50);
        Button confirmBtn = new Button("Confirm");
        confirmBtn.setId("space-buttons");
        confirmBtn.setOnAction((ActionEvent event) -> {
            confirmAction(action);
            confirmPopup.hide();
        });
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setId("space-buttons");
        cancelBtn.setOnAction((ActionEvent event) -> {
            confirmPopup.hide();
        });
        buttons.getChildren().addAll(confirmBtn, cancelBtn);
        return buttons;
    }


    private void confirmAction(int actionType) {
        if (actionType == 1) {
            String selectedMonster = (String) monsterList.getValue();
            controller.addMonster(selectedMonster, spaceNo, type);
            if (type == 1) {
                gui.getDisplay().createChamberView();
            } else {
                gui.getDisplay().createPassageView();
            }
            /*System.out.println(selectedMonster);*/
        } else if (actionType == 2) {
            String selectedTreasure = (String) treasureList.getValue();
            controller.addTreasure(selectedTreasure, spaceNo, type);
            if (type == 1) {
                gui.getDisplay().createChamberView();
            } else {
                gui.getDisplay().createPassageView();
            }
            /*System.out.println(treasureList);*/
        } else if (actionType == 3) {
            controller.removeMonster((int) spaceMonsterList.getValue(), spaceNo, type);
            if (type == 1) {
                gui.getDisplay().createChamberView();
            } else {
                gui.getDisplay().createPassageView();
            }
        } else {
            controller.removeTreasure((int) spaceTreasureList.getValue(), spaceNo, type);
            if (type == 1) {
                gui.getDisplay().createChamberView();
            } else {
                gui.getDisplay().createPassageView();
            }
        }
        setupEditPopup();
    }


}
