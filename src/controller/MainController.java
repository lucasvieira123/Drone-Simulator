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
    private ScheduledExecutorService executor;

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


        // ANTENAS CENÁRIO 1 E CENÁRIO 2
        /*AntenaViewImpl antenaView1 = new AntenaViewImpl(environmentView.getCellFrom(3, 15));
        antennaViews.add(antenaView1);
        AntenaViewImpl antenaView2 = new AntenaViewImpl(environmentView.getCellFrom(9, 15));
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
        }, 15000, 1000);*/
        // CENÁRIO 1

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

        // CENÁRIO 2

        HospitalImpl hospitalView1 = new HospitalImpl(environmentView.getCellFrom(9, 0));
        hospitalViews.add(hospitalView1);
        hospitalView1 = new HospitalImpl(environmentView.getCellFrom(9, 29));
        hospitalViews.add(hospitalView1);

        RiverViewImpl riverView2 = null;
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 2));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 3));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 2));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 3));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 2));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 3));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 4));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 5));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 6));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 7));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(11, 3));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(11, 4));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(11, 5));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 6));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 7));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 8));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 7));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 8));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 9));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 10));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 11));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 12));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(7, 8));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(7, 9));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(7, 10));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(7, 11));
        riverViews.add(riverView2);


        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 11));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 12));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 13));
        riverViews.add(riverView2);


        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 12));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 13));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 14));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 15));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 16));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 17));
        riverViews.add(riverView2);


        riverView2 = new RiverViewImpl(environmentView.getCellFrom(11, 13));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(11, 14));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(11, 15));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(11, 16));
        riverViews.add(riverView2);

        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 16));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 17));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 18));
        riverViews.add(riverView2);

        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 17));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 18));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 19));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 20));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 21));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 22));
        riverViews.add(riverView2);


        riverView2 = new RiverViewImpl(environmentView.getCellFrom(7, 18));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(7, 19));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(7, 20));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(7, 21));
        riverViews.add(riverView2);

        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 21));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 22));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 23));
        riverViews.add(riverView2);

        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 22));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 23));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 24));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 25));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 26));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 27));
        riverViews.add(riverView2);


        riverView2 = new RiverViewImpl(environmentView.getCellFrom(11, 23));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(11, 24));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(11, 25));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(11, 26));
        riverViews.add(riverView2);

        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 26));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 27));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 28));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 28));
        riverViews.add(riverView2);

        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 26));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 27));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 28));
        riverViews.add(riverView2);



        // CENÁRIO 2

       /* HospitalImpl hospitalView2 = new HospitalImpl(environmentView.getCellFrom(9, 0));
        hospitalViews.add(hospitalView2);
        hospitalView2 = new HospitalImpl(environmentView.getCellFrom(9, 29));
        hospitalViews.add(hospitalView2);

        RiverViewImpl riverView2 = null;
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 2));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 3));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 2));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 3));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 4));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 5));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 6));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 7));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(7, 3));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(7, 4));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(7, 5));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 6));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 7));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 8));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 7));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 8));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 9));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 10));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 11));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 12));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(11, 8));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(11, 9));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(11, 10));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(11, 11));
        riverViews.add(riverView2);


        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 11));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 12));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 13));
        riverViews.add(riverView2);


        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 12));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 13));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 14));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 15));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 16));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 17));
        riverViews.add(riverView2);


        riverView2 = new RiverViewImpl(environmentView.getCellFrom(7, 13));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(7, 14));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(7, 15));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(7, 16));
        riverViews.add(riverView2);

        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 16));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 17));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 18));
        riverViews.add(riverView2);

        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 17));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 18));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 19));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 20));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 21));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(10, 22));
        riverViews.add(riverView2);


        riverView2 = new RiverViewImpl(environmentView.getCellFrom(11, 18));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(11, 19));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(11, 20));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(11, 21));
        riverViews.add(riverView2);

        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 21));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 22));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 23));
        riverViews.add(riverView2);

        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 22));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 23));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 24));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 25));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 26));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(8, 27));
        riverViews.add(riverView2);


        riverView2 = new RiverViewImpl(environmentView.getCellFrom(7, 23));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(7, 24));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(7, 25));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(7, 26));
        riverViews.add(riverView2);

        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 26));
        riverViews.add(riverView2);
        riverView2 = new RiverViewImpl(environmentView.getCellFrom(9, 27));
        riverViews.add(riverView2);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(9, 28));
        riverViews.add(riverView1);
        riverView1 = new RiverViewImpl(environmentView.getCellFrom(8, 28));
        riverViews.add(riverView1);*/


        Integer initialBattery = 67;
        final int[] initialIdExecution1 = {1};
        final int[] initialIdExecution2 = {51};
        AtomicInteger quantidadeDeVezes = new AtomicInteger();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {

                if(quantidadeDeVezes.get()==9){
                    executor.shutdownNow();
                    cancel();
                }

                quantidadeDeVezes.getAndIncrement();
                int maxValue = 70;
                int minValue = 66;

                for (int i = 0; i < 1; i++) {

                    int battery1 = minValue + (int) (Math.random() * ((maxValue - minValue) + 1));
                    int battery2 = minValue + (int) (Math.random() * ((maxValue - minValue) + 1));
                    int battery3 = minValue + (int) (Math.random() * ((maxValue - minValue) + 1));
                    int battery4 = minValue + (int) (Math.random() * ((maxValue - minValue) + 1));
                    int battery5 = minValue + (int) (Math.random() * ((maxValue - minValue) + 1));

                    DroneViewImpl dv1 = new DroneViewImpl(initialIdExecution1[0]++, environmentView.getCellFrom(1, 0), (Hospital) hospitalViews.get(0).getHospital(), (Hospital) hospitalViews.get(1).getHospital());
                    ((Drone) dv1.getDrone()).setAspect(false);
                    ((Drone) dv1.getDrone()).setIsAutomatic(true);
                    ((Drone) dv1.getDrone()).setInitialBattery(battery1);
                    ((Drone) dv1.getDrone()).setCurrentBattery(battery1);


                    DroneViewImpl dv2 = new DroneViewImpl(initialIdExecution1[0]++, environmentView.getCellFrom(2, 0), (Hospital) hospitalViews.get(0).getHospital(), (Hospital) hospitalViews.get(1).getHospital());
                    ((Drone) dv2.getDrone()).setAspect(false);
                    ((Drone) dv2.getDrone()).setIsAutomatic(true);
                    ((Drone) dv2.getDrone()).setInitialBattery(battery2);
                    ((Drone) dv2.getDrone()).setCurrentBattery(battery2);


                    DroneViewImpl dv3 = new DroneViewImpl(initialIdExecution1[0]++, environmentView.getCellFrom(3, 0), (Hospital) hospitalViews.get(0).getHospital(), (Hospital) hospitalViews.get(1).getHospital());
                    ((Drone) dv3.getDrone()).setAspect(false);
                    ((Drone) dv3.getDrone()).setIsAutomatic(true);
                    ((Drone) dv3.getDrone()).setInitialBattery(battery3);
                    ((Drone) dv3.getDrone()).setCurrentBattery(battery3);


                    DroneViewImpl dv4 = new DroneViewImpl(initialIdExecution1[0]++, environmentView.getCellFrom(4, 0), (Hospital) hospitalViews.get(0).getHospital(), (Hospital) hospitalViews.get(1).getHospital());
                    ((Drone) dv4.getDrone()).setAspect(false);
                    ((Drone) dv4.getDrone()).setIsAutomatic(true);
                    ((Drone) dv4.getDrone()).setInitialBattery(battery4);
                    ((Drone) dv4.getDrone()).setCurrentBattery(battery4);


                    DroneViewImpl dv5 = new DroneViewImpl(initialIdExecution1[0]++, environmentView.getCellFrom(5, 0), (Hospital) hospitalViews.get(0).getHospital(), (Hospital) hospitalViews.get(1).getHospital());
                    ((Drone) dv5.getDrone()).setAspect(false);
                    ((Drone) dv5.getDrone()).setIsAutomatic(true);
                    ((Drone) dv5.getDrone()).setInitialBattery(battery5);
                    ((Drone) dv5.getDrone()).setCurrentBattery(battery5);


                    droneViews.add(dv1);
                    droneViews.add(dv2);
                    droneViews.add(dv3);
                    droneViews.add(dv4);
                    droneViews.add(dv5);


                    DroneViewImpl dv6 = new DroneViewImpl(initialIdExecution2[0]++, environmentView.getCellFrom(7, 0), (Hospital) hospitalViews.get(2).getHospital(), (Hospital) hospitalViews.get(3).getHospital());
                    ((Drone) dv6.getDrone()).setAspect(true);
                    ((Drone) dv6.getDrone()).setIsAutomatic(true);
                    ((Drone) dv6.getDrone()).setInitialBattery(battery1);
                    ((Drone) dv6.getDrone()).setCurrentBattery(battery1);


                    DroneViewImpl dv7 = new DroneViewImpl(initialIdExecution2[0]++, environmentView.getCellFrom(8, 0), (Hospital) hospitalViews.get(2).getHospital(), (Hospital) hospitalViews.get(3).getHospital());
                    ((Drone) dv7.getDrone()).setAspect(true);
                    ((Drone) dv7.getDrone()).setIsAutomatic(true);
                    ((Drone) dv7.getDrone()).setInitialBattery(battery2);
                    ((Drone) dv7.getDrone()).setCurrentBattery(battery2);


                    DroneViewImpl dv8 = new DroneViewImpl(initialIdExecution2[0]++, environmentView.getCellFrom(9, 0), (Hospital) hospitalViews.get(2).getHospital(), (Hospital) hospitalViews.get(3).getHospital());
                    ((Drone) dv8.getDrone()).setAspect(true);
                    ((Drone) dv8.getDrone()).setIsAutomatic(true);
                    ((Drone) dv8.getDrone()).setInitialBattery(battery3);
                    ((Drone) dv8.getDrone()).setCurrentBattery(battery3);


                    DroneViewImpl dv9 = new DroneViewImpl(initialIdExecution2[0]++, environmentView.getCellFrom(10, 0), (Hospital) hospitalViews.get(2).getHospital(), (Hospital) hospitalViews.get(3).getHospital());
                    ((Drone) dv9.getDrone()).setAspect(true);
                    ((Drone) dv9.getDrone()).setIsAutomatic(true);
                    ((Drone) dv9.getDrone()).setInitialBattery(battery4);
                    ((Drone) dv9.getDrone()).setCurrentBattery(battery4);


                    DroneViewImpl dv10 = new DroneViewImpl(initialIdExecution2[0]++, environmentView.getCellFrom(11, 0), (Hospital) hospitalViews.get(2).getHospital(), (Hospital) hospitalViews.get(3).getHospital());
                    ((Drone) dv10.getDrone()).setAspect(true);
                    ((Drone) dv10.getDrone()).setIsAutomatic(true);
                    ((Drone) dv10.getDrone()).setInitialBattery(battery5);
                    ((Drone) dv10.getDrone()).setCurrentBattery(battery5);

                    droneViews.add(dv6);
                    droneViews.add(dv7);
                    droneViews.add(dv8);
                    droneViews.add(dv9);
                    droneViews.add(dv10);


                    Platform.runLater(() -> startToggleButton.fire());

                }




                });
            };

        };

        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(timerTask, 1000, 12000, TimeUnit.MILLISECONDS);









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
        DroneViewImpl droneView = new DroneViewImpl(999999999, (Cell) environmentView.getCellSelected(),
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