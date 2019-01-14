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
import view.Cell;
import view.*;
import view.res.EnvironmentView;
import util.DroneAnalyzerLog;

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
        primaryStage.setTitle("Drone Simulator");


        Scene scene = new Scene(rootAnchorPane, 903, 650);

        primaryStage.setScene(scene);
        primaryStage.show();






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


        environmentView = new EnvironmentView(12, 30, environmentAnchorPane);


        // CENÁRIO 1

        HospitalImpl hospitalView = new HospitalImpl(environmentView.getCellFrom(2,0));
        hospitalViews.add(hospitalView);
        hospitalView = new HospitalImpl(environmentView.getCellFrom(2,19));
        hospitalViews.add(hospitalView);

        RiverViewImpl riverView = new RiverViewImpl(environmentView.getCellFrom(2,1));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(2,2));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(2,3));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(2,4));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(2,5));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(3,5));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(3,6));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(3,7));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(3,8));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(2,8));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(2,9));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(2,10));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(2,11));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(2,12));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(2,13));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(2,14));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(3,14));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(3,15));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(3,16));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(3,17));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(2,17));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(2,18));
        riverViews.add(riverView);

        boolean isAspect = false;
        boolean isAutomatic = false;

        for(int i =0; i<1; i++){
            DroneViewImpl dv1 = new DroneViewImpl(environmentView.getCellFrom(2,1),(Hospital) hospitalViews.get(0).getHospital(), (Hospital)hospitalViews.get(1).getHospital());
            ((Drone)dv1.getDrone()).setAspect(isAspect);
            ((Drone)dv1.getDrone()).setIsAutomatic(isAutomatic);
            DroneViewImpl dv2 = new DroneViewImpl(environmentView.getCellFrom(1,1),(Hospital) hospitalViews.get(0).getHospital(), (Hospital)hospitalViews.get(1).getHospital());
            ((Drone)dv2.getDrone()).setAspect(isAspect);
            ((Drone)dv2.getDrone()).setIsAutomatic(isAutomatic);
            DroneViewImpl dv3 = new DroneViewImpl(environmentView.getCellFrom(3,1),(Hospital) hospitalViews.get(0).getHospital(), (Hospital)hospitalViews.get(1).getHospital());
            ((Drone)dv3.getDrone()).setAspect(isAspect);
            ((Drone)dv3.getDrone()).setIsAutomatic(isAutomatic);
            DroneViewImpl dv4 = new DroneViewImpl(environmentView.getCellFrom(1,0),(Hospital) hospitalViews.get(0).getHospital(), (Hospital)hospitalViews.get(1).getHospital());
            ((Drone)dv4.getDrone()).setAspect(isAspect);
            ((Drone)dv4.getDrone()).setIsAutomatic(isAutomatic);
            DroneViewImpl dv6 = new DroneViewImpl(environmentView.getCellFrom(3,0),(Hospital) hospitalViews.get(0).getHospital(), (Hospital)hospitalViews.get(1).getHospital());
            ((Drone)dv6.getDrone()).setAspect(isAspect);
            ((Drone)dv6.getDrone()).setIsAutomatic(isAutomatic);

            droneViews.add(dv1);
            droneViews.add(dv2);
            droneViews.add(dv3);
            droneViews.add(dv4);
            droneViews.add(dv6);
        }


        // FIM CENÁRIO 1
        AntenaViewImpl antenaView1 = new AntenaViewImpl(environmentView.getCellFrom(3,10));
        antennaViews.add(antenaView1);
        AntenaViewImpl antenaView2 = new AntenaViewImpl(environmentView.getCellFrom(5,10));
        antennaViews.add(antenaView2);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Random random = new Random();
                double randomDouble = random.nextDouble();
                antenaView1.addbadConnectionInSpecificArea(randomDouble);
                antenaView2.addbadConnectionInSpecificArea(randomDouble);

            }
        }, 0, 2000);

        // CENÁRIO 2

        hospitalView = new HospitalImpl(environmentView.getCellFrom(6,0));
        hospitalViews.add(hospitalView);
        hospitalView = new HospitalImpl(environmentView.getCellFrom(6,19));
        hospitalViews.add(hospitalView);

        riverView = new RiverViewImpl(environmentView.getCellFrom(6,1));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(6,2));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(6,3));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(6,4));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(6,5));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(5,5));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(5,6));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(5,7));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(5,8));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(6,8));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(6,9));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(6,10));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(6,11));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(6,12));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(6,13));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(6,14));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(5,14));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(5,15));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(5,16));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(5,17));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(6,17));
        riverViews.add(riverView);
        riverView = new RiverViewImpl(environmentView.getCellFrom(6,18));
        riverViews.add(riverView);


        isAspect = true;

        for(int i =0; i<1; i++) {
            DroneViewImpl dv7 = new DroneViewImpl(environmentView.getCellFrom(6, 1), (Hospital) hospitalViews.get(2).getHospital(), (Hospital) hospitalViews.get(3).getHospital());
            ((Drone) dv7.getDrone()).setAspect(isAspect);
            ((Drone) dv7.getDrone()).setIsAutomatic(isAutomatic);
            DroneViewImpl dv8 = new DroneViewImpl(environmentView.getCellFrom(7, 1), (Hospital) hospitalViews.get(2).getHospital(), (Hospital) hospitalViews.get(3).getHospital());
            ((Drone) dv8.getDrone()).setAspect(isAspect);
            ((Drone) dv8.getDrone()).setIsAutomatic(isAutomatic);
            DroneViewImpl dv9 = new DroneViewImpl(environmentView.getCellFrom(5, 1), (Hospital) hospitalViews.get(2).getHospital(), (Hospital) hospitalViews.get(3).getHospital());
            ((Drone) dv9.getDrone()).setAspect(isAspect);
            ((Drone) dv9.getDrone()).setIsAutomatic(isAutomatic);
            DroneViewImpl dv10 = new DroneViewImpl(environmentView.getCellFrom(7, 0), (Hospital) hospitalViews.get(2).getHospital(), (Hospital) hospitalViews.get(3).getHospital());
            ((Drone) dv10.getDrone()).setAspect(isAspect);
            ((Drone) dv10.getDrone()).setIsAutomatic(isAutomatic);

            DroneViewImpl dv12 = new DroneViewImpl(environmentView.getCellFrom(5, 0), (Hospital) hospitalViews.get(2).getHospital(), (Hospital) hospitalViews.get(3).getHospital());
            ((Drone) dv12.getDrone()).setAspect(isAspect);
            ((Drone) dv12.getDrone()).setIsAutomatic(isAutomatic);

            droneViews.add(dv7);
            droneViews.add(dv8);
            droneViews.add(dv9);
            droneViews.add(dv10);

            droneViews.add(dv12);
        }

        // FIM DO CENÁRIO 2

        Integer initialBattery = 48;
        int batteryPerBlock =1;
        for (DroneView currentDV : droneViews) {
            ((Drone)currentDV.getDrone()).setInitialBattery(initialBattery);
            ((Drone)currentDV.getDrone()).setCurrentBattery(initialBattery);
            ((Drone)currentDV.getDrone()).setBatteryPerBlock(batteryPerBlock);
        }

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


        randomStrongWindRadioButton.fire();





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