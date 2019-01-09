package controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Drone;
import model.Hospital;
import model.River;
import view.Cell;
import view.*;
import view.res.EnvironmentView;

import java.util.*;

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
    Label initialBatteryLabel, consumptionPerBlockLabel, consumptionPerSecondLabel/*, badConectionLabel*/, currentDroneLabel;

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
    CheckBox automaticCheckBox;

    @FXML
    Button saveButton;


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


    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/res/MainPanes.fxml"));
        loader.setController(this);
        rootAnchorPane = loader.load();


        Scene scene = new Scene(rootAnchorPane, 604, 700);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    @FXML
    private void initialize() {

        loggerController.setTextArea(loggerTextArea);

        ToggleGroup toggleGroup1 = new ToggleGroup();
        riverToggleButton.setToggleGroup(toggleGroup1);
        hospitalToggleButton.setToggleGroup(toggleGroup1);
        droneToggleButton.setToggleGroup(toggleGroup1);
        antennaToggleButton.setToggleGroup(toggleGroup1);

      /*  ToggleGroup toggleGroup2 = new ToggleGroup();
        trueBadConnectionRadioButton.setToggleGroup(toggleGroup2);
        noBadConnectionRadioButton.setToggleGroup(toggleGroup2);*/
       /* randomBadConnectionRadioButton.setToggleGroup(toggleGroup2);*/

        ToggleGroup toggleGroup3 = new ToggleGroup();
        trueStrongWindRadioButton.setToggleGroup(toggleGroup3);
        noStrongWindRadioButton.setToggleGroup(toggleGroup3);
        randomStrongWindRadioButton.setToggleGroup(toggleGroup3);

        ToggleGroup toggleGroup4 = new ToggleGroup();
        startToggleButton.setToggleGroup(toggleGroup4);
        restartToggleButton.setToggleGroup(toggleGroup4);


        environmentView = new EnvironmentView(8, 20, environmentAnchorPane);


     /*   HospitalImpl hospitalView = new HospitalImpl(environmentView.getCellFrom(3,0));
        hospitalViews.add(hospitalView);
        hospitalView = new HospitalImpl(environmentView.getCellFrom(3,19));
        hospitalViews.add(hospitalView);

        RiverViewImpl riverView = new RiverViewImpl(environmentView.getCellFrom(3,1));
        riverViews.add(riverView);

        riverView = new RiverViewImpl(environmentView.getCellFrom(3,2));
        riverViews.add(riverView);

        riverView = new RiverViewImpl(environmentView.getCellFrom(3,3));
        riverViews.add(riverView);

        riverView = new RiverViewImpl(environmentView.getCellFrom(3,4));
        riverViews.add(riverView);

        riverView = new RiverViewImpl(environmentView.getCellFrom(3,5));
        riverViews.add(riverView);

        riverView = new RiverViewImpl(environmentView.getCellFrom(4,5));
        riverViews.add(riverView);



        riverView = new RiverViewImpl(environmentView.getCellFrom(4,6));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(4,7));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(4,8));
        riverViews.add(riverView);

        riverView = new RiverViewImpl(environmentView.getCellFrom(3,8));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(3,9));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(3,10));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(3,11));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(3,12));
        riverViews.add(riverView);

        riverView = new RiverViewImpl(environmentView.getCellFrom(3,13));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(3,14));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(4,14));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(4,15));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(4,16));
        riverViews.add(riverView);

        riverView = new RiverViewImpl(environmentView.getCellFrom(4,17));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(3,17));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(3,18));
        riverViews.add(riverView);


        AntenaViewImpl antenaView1 = new AntenaViewImpl(environmentView.getCellFrom(2,11));
        antennaViews.add(antenaView1);

        DroneViewImpl dv1 = new DroneViewImpl(environmentView.getCellFrom(3,0),(Hospital) hospitalViews.get(0).getHospital(), (Hospital)hospitalViews.get(1).getHospital());
        ((Drone)dv1.getDrone()).setIsAutomatic(false);*/


      /*  DroneViewImpl dv2 = new DroneViewImpl(environmentView.getCellFrom(2,0),(Hospital) hospitalViews.get(0).getHospital(), (Hospital)hospitalViews.get(1).getHospital());
        ((Drone)dv2.getDrone()).setAspect(false);
        ((Drone)dv2.getDrone()).setIsAutomatic(true);
        DroneViewImpl dv3 = new DroneViewImpl(environmentView.getCellFrom(4,0),(Hospital) hospitalViews.get(0).getHospital(), (Hospital)hospitalViews.get(1).getHospital());
        ((Drone)dv3.getDrone()).setAspect(false);
        ((Drone)dv3.getDrone()).setIsAutomatic(true);
        DroneViewImpl dv4 = new DroneViewImpl(environmentView.getCellFrom(2,1),(Hospital) hospitalViews.get(0).getHospital(), (Hospital)hospitalViews.get(1).getHospital());
        ((Drone)dv4.getDrone()).setAspect(false);
        ((Drone)dv4.getDrone()).setIsAutomatic(true);
        DroneViewImpl dv5 = new DroneViewImpl(environmentView.getCellFrom(4,1),(Hospital) hospitalViews.get(0).getHospital(), (Hospital)hospitalViews.get(1).getHospital());
        ((Drone)dv5.getDrone()).setAspect(false);
        ((Drone)dv5.getDrone()).setIsAutomatic(true);*/


//        droneViews.add(dv1);
       /* droneViews.add(dv2);
        droneViews.add(dv3);
        droneViews.add(dv4);
        droneViews.add(dv5);*/


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

        randomStrongWindRadioButton.setOnMouseClicked(event -> {
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

            }else if (antennaToggleButton.isSelected()){
                createAntenna();
            }

            else if (droneToggleButton.isSelected()) {

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
        }catch (Exception e){

        }

    }


    private void saveAttributesDrone() {

        Drone selectedDrone = (Drone) droneViewSelected.getDrone();

        if (!running) {
            selectedDrone.setInitialBattery(Double.parseDouble(initialBatteryTextView.getText()));

            selectedDrone.setCurrentBattery(Double.parseDouble(initialBatteryTextView.getText()));

            selectedDrone.setBatteryPerBlock(Double.parseDouble(consumptionPerBlockTextView.getText()));
            selectedDrone.setBatteryPerSecond(Double.parseDouble(consumptionPerSecondTextView.getText()));
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
           /* trueBadConnectionRadioButton.setSelected(false);*/
        } else {
            Drone selectedDrone = (Drone) droneViewSelected.getDrone();
            currentDroneTextField.setText(String.valueOf(selectedDrone.getId()));
            consumptionPerBlockTextView.setText(String.valueOf(selectedDrone.getBatteryPerBlock()));
            consumptionPerSecondTextView.setText(String.valueOf(selectedDrone.getBatteryPerSecond()));
            initialBatteryTextView.setText(String.valueOf(selectedDrone.getInitialBattery()));
            automaticCheckBox.setSelected(selectedDrone.isAutomatic());


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
        saveButton.setDisable(true);

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
        }

        if (droneViewSelected != null && ((Drone) droneViewSelected.getDrone()).isTookOff()) {
           /* badConectionLabel.setDisable(false);*/
          /*  trueBadConnectionRadioButton.setDisable(false);
            noBadConnectionRadioButton.setDisable(false);*/
          /*  randomBadConnectionRadioButton.setDisable(false);*/
        }


        automaticCheckBox.setDisable(false);

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
