package controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Drone;
import model.Hospital;
import view.Cell;
import view.*;
import view.res.EnvironmentView;
import util.DroneAnalyzerLog;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MainController extends Application {

    @FXML
    AnchorPane environmentAnchorPane;

    @FXML
    AnchorPane settingsAnchorPane;

    @FXML
    AnchorPane loggerAnchorPane;

    @FXML
    TextField initialBatteryTextView, consumptionPerBlockTextView, consumptionPerSecondTextView, currentDroneTextField;

    @FXML
    Label initialBatteryLabel, consumptionPerBlockLabel, consumptionPerSecondLabel/*, badConectionLabel*/, currentDroneLabel, sourceLabel, targetLabel;

    @FXML
    ToggleButton startToggleButton, restartToggleButton;

    @FXML
    ToggleButton riverToggleButton, hospitalToggleButton, droneToggleButton, antennaToggleButton;

    /*@FXML
    RadioButton trueBadConnectionRadioButton*//*, randomBadConnectionRadioButton*//*, noBadConnectionRadioButton;
     */
    @FXML
    RadioButton trueStrongWindRadioButton, randomStrongWindRadioButton, noStrongWindRadioButton;

    @FXML
    TextArea loggerTextArea;

    @FXML
    CheckBox automaticCheckBox, wrapperCheckBox;

    @FXML
    Button saveButton;

    @FXML
    ComboBox<String> sourceComboBox, targetComboBox;


    private AnchorPane rootAnchorPane;

    public List<RiverView> riverViews = new ArrayList<>();
    public List<DroneView> droneViews = new ArrayList<>();
    public List<HospitalView> hospitalViews = new ArrayList<>();
    private List<AntenaViewImpl> antennaViews = new ArrayList<>();
    private boolean running = false;
    private DroneViewImpl droneViewSelected;
    private boolean droneToggleButtonIsSelected = false;
    private boolean badConnection;
    private LoggerController loggerController = LoggerController.getInstance();
    private EnvironmentView environmentView;
    private Timer ramdomStrongWind;
    private ScheduledExecutorService executor;


    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/res/MainPanes.fxml"));
        loader.setController(this);
        rootAnchorPane = loader.load();
        primaryStage.setTitle("Drone Simulator");

        MenuBar menuBar = new MenuBar();
        VBox vBox = new VBox(menuBar);
        Menu menuExamples = new Menu("Menu");
        MenuItem exampleLodonMenuItem = new MenuItem("London Example");
        menuExamples.getItems().add(exampleLodonMenuItem);
        menuBar.getMenus().add(menuExamples);
        rootAnchorPane.getChildren().add(menuBar);

        exampleLodonMenuItem.setOnAction(event -> {
         clearEnverionment();

            running = false;

            enableEnvironmentSettingViews();

            addExampleLondon();
        });


        Scene scene = new Scene(rootAnchorPane, 903, 683);

        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private void clearEnverionment() {
        for(Cell cell : environmentView.getCells()){
            cell.getChildren().clear();
        }



        for (DroneView droneView : droneViews) {
            droneView.notifyReset();
        }

        droneViewSelected = null;
        droneViews.clear();
        hospitalViews.clear();
        riverViews.clear();
        antennaViews.clear();

        loggerController.clear();

        DroneViewImpl.COUNT_DRONE = 0;

        HospitalImpl.COUNT_HOSPITAL =0;

        AntenaViewImpl.COUNT_ANTENNA =0;
    }

    private void addExampleLondon() {

        AntenaViewImpl antenaView1 = new AntenaViewImpl(environmentView.getCellFrom(3, 15));
        antennaViews.add(antenaView1);

        HospitalImpl hospitalView = new HospitalImpl(environmentView.getCellFrom(3, 0));
        hospitalViews.add(hospitalView);

        hospitalView = new HospitalImpl(environmentView.getCellFrom(3, 29));
        hospitalViews.add(hospitalView);

        RiverViewImpl riverView1 = null;
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 2));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 3));
        riverViews.add(riverView1);

        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 2));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 3));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 2));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 3));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 4));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 5));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 6));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 7));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(5, 3));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(5, 4));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(5, 5));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 6));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 7));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 8));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 7));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 8));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 9));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 10));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 11));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 12));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(1, 8));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(1, 9));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(1, 10));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(1, 11));
        riverViews.add(riverView1);


        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 11));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 12));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 13));
        riverViews.add(riverView1);


        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 12));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 13));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 14));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 15));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 16));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 17));
        riverViews.add(riverView1);


        riverView1 = new RiverViewImpl(environmentView.getCellFrom(5, 13));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(5, 14));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(5, 15));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(5, 16));
        riverViews.add(riverView1);

        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 16));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 17));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 18));
        riverViews.add(riverView1);

        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 17));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 18));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 19));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 20));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 21));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 22));
        riverViews.add(riverView1);


        riverView1 = new RiverViewImpl(environmentView.getCellFrom(1, 18));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(1, 19));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(1, 20));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(1, 21));
        riverViews.add(riverView1);

        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 21));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 22));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 23));
        riverViews.add(riverView1);

        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 22));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 23));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 24));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 25));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 26));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 27));
        riverViews.add(riverView1);


        riverView1 = new RiverViewImpl(environmentView.getCellFrom(5, 23));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(5, 24));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(5, 25));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(5, 26));
        riverViews.add(riverView1);

        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 26));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 27));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(3, 28));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(4, 28));
        riverViews.add(riverView1);

        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 26));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 27));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(2, 28));
        riverViews.add(riverView1);

    }

    @FXML
    private void initialize() {


        loggerController.setTextArea(loggerTextArea);

        ContextMenu contextMenu = new ContextMenu();
        MenuItem logAnalyzerMenuItem = new MenuItem("Drone Logs Analyzer");
        contextMenu.getItems().add(logAnalyzerMenuItem);
        loggerTextArea.setContextMenu(contextMenu);
        logAnalyzerMenuItem.setOnAction(event -> {

            try {

                new DroneAnalyzerLog().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        ToggleGroup toggleGroup1 = new ToggleGroup();
        riverToggleButton.setToggleGroup(toggleGroup1);
        hospitalToggleButton.setToggleGroup(toggleGroup1);
        droneToggleButton.setToggleGroup(toggleGroup1);
        antennaToggleButton.setToggleGroup(toggleGroup1);

        ToggleGroup toggleGroup3 = new ToggleGroup();
        trueStrongWindRadioButton.setToggleGroup(toggleGroup3);
        noStrongWindRadioButton.setToggleGroup(toggleGroup3);
        randomStrongWindRadioButton.setToggleGroup(toggleGroup3);

        ToggleGroup toggleGroup4 = new ToggleGroup();
        startToggleButton.setToggleGroup(toggleGroup4);
        restartToggleButton.setToggleGroup(toggleGroup4);


        environmentView = new EnvironmentView(12, 30, environmentAnchorPane);


        trueStrongWindRadioButton.setOnMouseClicked(event -> {
            stopRandomStrongWind();
            environmentView.addStrongWind();

            for (DroneView droneView : droneViews) {
                droneView.notifyStrongWind();
            }
        });

        noStrongWindRadioButton.setOnMouseClicked(event -> {
            stopRandomStrongWind();
            environmentView.removeStrongWind();

            for (DroneView droneView : droneViews) {
                droneView.notifyNoStrongWind();
            }
        });

        randomStrongWindRadioButton.setOnAction(event -> {
            ramdomStrongWind = new Timer();
            ramdomStrongWind.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Random random = new Random();
                    boolean bool = random.nextBoolean();

                    if (bool) {

                        Platform.runLater(() -> {
                            environmentView.addStrongWind();
                        });


                        for (DroneView droneView : droneViews) {
                            droneView.notifyStrongWind();
                        }

                    } else {


                        Platform.runLater(() -> {
                            environmentView.removeStrongWind();
                        });

                        for (DroneView droneView : droneViews) {
                            droneView.notifyNoStrongWind();
                        }
                    }

                }
            }, 0, 1000);
        });

        droneToggleButton.setOnMouseClicked(event -> droneToggleButtonIsSelected = !droneToggleButtonIsSelected);

        /*    trueBadConnectionRadioButton.setOnAction(event -> {

         *//*    if(running){
                if(droneViewSelected!= null){
                    droneViewSelected.notifyBadConnection();
                }
            }else {
                badConnection = true;
            }*//*


        });

        noBadConnectionRadioButton.setOnAction(event -> {
           *//* if(running){

                if(droneViewSelected!= null){
                    droneViewSelected.notifyNormalConnection();
                    badConnection = false;
                }else {
                    badConnection = false;
                }
            }*//*

        });*/


        saveButton.setOnAction(event -> {

            startToggleButton.setDisable(false);
            restartToggleButton.setDisable(false);

            saveAttributesDrone();
            updateDroneSettingsViews();


            disableDroneSettingsViews();

            droneToggleButton.setSelected(false);

            environmentView.getGridpane().requestFocus();
        });

        startToggleButton.setOnAction(event -> {

            disableEnvironmentSettingViews();


            for (DroneView droneView : droneViews) {
                droneView.notifyRunEnviroment();
            }


            running = true;

            environmentView.getGridpane().requestFocus();

        });

        restartToggleButton.setOnAction(event -> {

            for (DroneView droneView : droneViews) {
                droneView.notifyReset();
            }

            loggerController.clear();


            running = false;

            enableEnvironmentSettingViews();


        });


        environmentView.getGridpane().setOnMouseClicked(event1 -> {

            if (riverToggleButton.isSelected()) {

                createRiver();

            } else if (hospitalToggleButton.isSelected()) {
                createHospital();

            } else if (antennaToggleButton.isSelected()) {
                createAntenna();
            } else if (droneToggleButton.isSelected()) {

                if(hospitalViews.size()<2){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You must add at 2 hospitals \n(first as target and second as target)", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }



                DroneViewImpl droneView = (DroneViewImpl) createDrone();
                updateSelectedDrone(droneView);
                updateDroneSettingsViews();
                enableDroneSettingsViews();

            }


            DroneView droneView = getDroneViewFromCell(environmentView.getCellSelected());

            for (DroneView droneView1 : droneViews) {
                droneView1.removeStyleSelected();
            }

            if (droneView != null) {
                updateSelectedDrone((DroneViewImpl) droneView);
                updateDroneSettingsViews();

                enableDroneSettingsViews();

            } else {
                droneViewSelected = null;
                updateDroneSettingsViews();
                disableDroneSettingsViews();

            }


        });

        environmentView.getGridpane().setOnKeyPressed(event -> {
            if (!running) {
                return;
            }

            if (droneViewSelected != null) {

                droneViewSelected.eventKey(event.getCode());


            }

        });



    }


    private void stopRandomStrongWind() {
        try {
            ramdomStrongWind.cancel();
        } catch (Exception e) {

        }

    }


    private void saveAttributesDrone() {

        Drone selectedDrone = (Drone) droneViewSelected.getDrone();

        if (!running) {
            selectedDrone.setInitialBattery(Double.parseDouble(initialBatteryTextView.getText()));

            selectedDrone.setCurrentBattery(Double.parseDouble(initialBatteryTextView.getText()));

            selectedDrone.setBatteryPerBlock(Double.parseDouble(consumptionPerBlockTextView.getText()));
            selectedDrone.setBatteryPerSecond(Double.parseDouble(consumptionPerSecondTextView.getText()));

            selectedDrone.setAspect(wrapperCheckBox.isSelected());

            selectedDrone.setSourceHospital((Hospital) hospitalViews.get(sourceComboBox.getSelectionModel().getSelectedIndex()).getHospital());
            selectedDrone.setDestinyHopistal((Hospital) hospitalViews.get(targetComboBox.getSelectionModel().getSelectedIndex()).getHospital());

        }


        if (!randomStrongWindRadioButton.isSelected()) {
            selectedDrone.setStrongWind(trueStrongWindRadioButton.isSelected());
        }


        selectedDrone.setIsAutomatic(automaticCheckBox.isSelected());
        selectedDrone.setIsManual(!selectedDrone.isAutomatic());

        /* selectedDrone.setBadConnection(trueBadConnectionRadioButton.isSelected());*/


        if (running) {
            if (droneViewSelected != null) {
                if (selectedDrone.isBadConnection()) {
                    droneViewSelected.notifyBadConnection();
                } else {
                    droneViewSelected.notifyNormalConnection();
                }


            }
        }
    }

    private void updateDroneSettingsViews() {

        if (droneViewSelected == null) {
            consumptionPerBlockTextView.setText("");
            consumptionPerSecondTextView.setText("");
            initialBatteryTextView.setText("");
            currentDroneTextField.setText("");
            automaticCheckBox.setSelected(false);
            wrapperCheckBox.setSelected(false);
            sourceComboBox.getSelectionModel().clearSelection();
            targetComboBox.getSelectionModel().clearSelection();

            /* trueBadConnectionRadioButton.setSelected(false);*/
        } else {
            Drone selectedDrone = (Drone) droneViewSelected.getDrone();
            currentDroneTextField.setText(String.valueOf(selectedDrone.getId()));
            consumptionPerBlockTextView.setText(String.valueOf(selectedDrone.getBatteryPerBlock()));
            consumptionPerSecondTextView.setText(String.valueOf(selectedDrone.getBatteryPerSecond()));
            initialBatteryTextView.setText(String.valueOf(selectedDrone.getInitialBattery()));
            automaticCheckBox.setSelected(selectedDrone.isAutomatic());
            wrapperCheckBox.setSelected(selectedDrone.isAspect());

            List<String> nameHospitals = new ArrayList<>(hospitalViews.size());
            for(HospitalView hospitalView : hospitalViews){
                nameHospitals.add(String.valueOf(((Hospital)hospitalView.getHospital()).getId()));
            }

            ObservableList<String> options =
                    FXCollections.observableArrayList(nameHospitals);
            sourceComboBox.setItems(options);


            targetComboBox.setItems(options);

            sourceComboBox.getSelectionModel().select(String.valueOf(selectedDrone.getSourceHospital().getId()));



            targetComboBox.getSelectionModel().select(String.valueOf(selectedDrone.getDestinyHopistal().getId()));





          /*  trueBadConnectionRadioButton.setSelected(selectedDrone.isBadConnection());

            noBadConnectionRadioButton.setSelected(!selectedDrone.isBadConnection());*/
        }


    }

    private void updateSelectedDrone(DroneViewImpl droneView) {
        droneViewSelected = droneView;
        droneView.setStyleSelected();
    }

    private DroneView createDrone() {
        DroneViewImpl droneView = new DroneViewImpl((Cell) environmentView.getCellSelected(),
                (Hospital) hospitalViews.get(0).getHospital(),
                (Hospital) hospitalViews.get(1).getHospital());

        droneViews.add(droneView);

        Drone drone = (Drone) droneView.getDrone();

        System.out.println("Drone" + drone.getId() + ": Source Hospital " + drone.getSourceHospital().getId() + " Destiny Hospital " + drone.getDestinyHopistal().getId());

        return droneView;
    }

    private HospitalView createHospital() {
        HospitalImpl hospitalView = new HospitalImpl((Cell) environmentView.getCellSelected());
        hospitalViews.add(hospitalView);
        return hospitalView;
    }

    private RiverView createRiver() {
        RiverViewImpl riverView = new RiverViewImpl((Cell) environmentView.getCellSelected());
        riverViews.add(riverView);
        return riverView;
    }

    private AntenaView createAntenna() {
        AntenaViewImpl antenaView = new AntenaViewImpl((Cell) environmentView.getCellSelected());
        antennaViews.add(antenaView);
        return antenaView;
    }

    private void disableEnvironmentSettingViews() {
        riverToggleButton.setDisable(true);
        hospitalToggleButton.setDisable(true);
        droneToggleButton.setDisable(true);
        antennaToggleButton.setDisable(true);
    }

    private void enableEnvironmentSettingViews() {
        settingsAnchorPane.requestFocus();
        riverToggleButton.setDisable(false);
        hospitalToggleButton.setDisable(false);
        droneToggleButton.setDisable(false);
        antennaToggleButton.setDisable(false);
    }

    private void disableDroneSettingsViews() {

        consumptionPerBlockLabel.setDisable(true);
        consumptionPerBlockTextView.setDisable(true);

        consumptionPerSecondLabel.setDisable(true);
        consumptionPerSecondTextView.setDisable(true);

        initialBatteryLabel.setDisable(true);
        initialBatteryTextView.setDisable(true);
        wrapperCheckBox.setDisable(true);
        saveButton.setDisable(true);
        sourceComboBox.setDisable(true);
        sourceLabel.setDisable(true);
        targetComboBox.setDisable(true);
        targetComboBox.setDisable(true);
        /*       badConectionLabel.setDisable(true);*/
   /*     trueBadConnectionRadioButton.setDisable(true);
        noBadConnectionRadioButton.setDisable(true);
       *//* randomBadConnectionRadioButton.setDisable(true);*/

        automaticCheckBox.setDisable(true);

    }

    private void enableDroneSettingsViews() {


        if (!running) {
            consumptionPerBlockLabel.setDisable(false);
            consumptionPerBlockTextView.setDisable(false);
            consumptionPerBlockTextView.requestFocus();

            consumptionPerSecondLabel.setDisable(false);
            consumptionPerSecondTextView.setDisable(false);

            initialBatteryLabel.setDisable(false);
            initialBatteryTextView.setDisable(false);
            wrapperCheckBox.setDisable(false);
            sourceComboBox.setDisable(false);
            sourceLabel.setDisable(false);
            targetLabel.setDisable(false);
            targetComboBox.setDisable(false);
        }

        if (droneViewSelected != null && ((Drone) droneViewSelected.getDrone()).isTookOff()) {
            /* badConectionLabel.setDisable(false);*/
          /*  trueBadConnectionRadioButton.setDisable(false);
            noBadConnectionRadioButton.setDisable(false);*/
            /*  randomBadConnectionRadioButton.setDisable(false);*/
        }


        automaticCheckBox.setDisable(false);
        wrapperCheckBox.setDisable(false);

        saveButton.setDisable(false);

    }


    private DroneViewImpl getDroneViewFromCell(Pane cellSelected) {
        if (cellSelected.getChildren().isEmpty()) {
            return null;
        }

        Node node = cellSelected.getChildren().get(cellSelected.getChildren().size() - 1);

        if (node instanceof DroneView) {
            return (DroneViewImpl) node;
        }

        return null;

    }


}