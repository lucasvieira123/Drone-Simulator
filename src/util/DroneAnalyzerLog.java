package util;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DroneAnalyzerLog extends Application {
    private static final String ARRIVED_AT_DESTINATION = "Arrived at destination";
    private static final String ARRIVED_AT_DESTINATION_ASPECT = "Arrived at destination aspect";

    private static final String KEEP_FLYING_ASPECT = "keep Flying aspect";
    private static final String MOVE_ASIDE_ASPECT = "Move aside aspect";

    private static final String GLIDE_ASPECT = "Glide aspect";
    private static final String RETURN_TO_HOME = "Return to Home";
    private static final String RETURN_TO_HOME_COMPLETED_SUCCESSFULLY = "Return to home completed successfully";

    private static final String CONTINUE_NORMAL_MODE_ASPECT = "Continue Normal Mode aspect";
    private static final String START_ECONOMY_MODE = "Start Economy Mode";


    private static final String SAFE_LAND_ASPECT = "SafeLand aspect";
    private static final String SAFE_LAND = "SafeLand";

    private static final String DRONE_LANDED_ON_WATER = "Drone landed on water";
    private static final String DRONE_LANDED_SUCCESSFULLY = "Drone landed successfully";
    private static final String DRONE_LANDED_SUCCESSFULLY_ASPECT = " Drone landed successfully aspect";




    FileChooser fileChooser = new FileChooser();
    File selectedFile;
    @FXML
    TextField pathLogsFileTxtFild;
    @FXML
    Button chooseBtn, clearButton;
    @FXML
    TextArea answerTextArea;
    private Stage primaryStage;

    private int landedAtDestinationByKeepFlyingCount = 0;

    private Map<Integer, LinkedList<String>> logsMap = new HashMap<>();

   /* private int safelandNormalCount = 0;
    private int landedOnGroundAfterMovingAsideCount = 0;
    private int landedOnWaterCount = 0;
    private int safeLandedWhileReturningToHomeCount;
    private int landedOnGroundCount;
    private int glideAndSafeLandCount;
    private int glideAndLandedAtDestinationNormallyCount;
    private int glidedAndLandedAtDestinationByKeepFlyingCount;*/

    private int Landed_at_Destination_Normally;
    private int Landed_at_Destination_by_Keep_Flying;
    private int Landed_on_ground;
    private int Landed_on_ground_after_moving_aside;
    private int Landed_on_Water;
    private int Returned_to_Home;
    private int SafeLanded_while_Returning_to_Home;
    private int Glided_and_SafeLanded;
    private int Glided_and_Landed_at_Destination_Normally;
    private int Glided_and_Landed_at_Destination_by_Keep_Flying;
    private int Activated_Economy_Mode;
    private int Activated_Economy_Mode_and_SafeLanded;


  /*  private int arrivedAtDestination_withoutExceptionalScenaries;

    private int returnToHome_returntoHomeCompletedSuccessfully;

    private int arrivedAtDestination_keepFlying;

    private int arrivedAtDestination_glide;

    private int arrivedAtDestination_continueNormalMode;

    private int arrivedAtDestination_glide_keepFlying;

    private int arrivedAtDestination_continueNormalMode_keepFlying;

    private int arrivedAtDestination_continueNormalMode_glide;

    private int arrivedAtDestination_keepFlying_glide_continueNormalMode_;

    private int returnToHome_safeLand_droneLandedSucessfully;

    private int returnToHome_safeLand_droneLandedOnWater;

    private int returnToHome_moveASide_safeLand_droneLandedSucessfully;

    private int glide_arrivedAtDestination;

    private int glide_safeLand_droneLandedSucessfully;

    private int glide_safeLand_droneLandedOnWater;

    private int glide_keepFlying_arrivedAtDestination;

    private int glide_moveAside_droneLandedSucessfully;

    private int continueNormalMode_glide_arrivedAtDestination;

    private int continueNormalMode_glide_safeLand_droneLandedSucessfully;

    private int continueNormalMode_glide_safeLand_DroneLandedOnWater;

    private int continueNormalMode_glide_keepFlying_arrivedAtDestination;

    private int safeLand_droneLandedSuccessfully;

    private int arrivedAtDestination_startEconomyMode;

    private int arrivedAtDestination_keepFlying_glide_startEconomyMode;

    private int arrivedAtDestination_startEconomyMode_glide_keepFlying;

    private int arrivedAtDestination_startEconomyMode_glide;

    private int arrivedAtDestination_startEconomyMode_keepFlying;

    private int returnToHome_continueNormalMode_returntoHomeCompletedSuccessfully;

    private int returnToHome_startEconomyMode_returntoHomeCompletedSuccessfully;

    private int returnToHome_safeLand_continueNormalMode_droneLandedSucessfully;

    private int returnToHome_safeLand_startEconomyMode_droneLandedSucessfully;

    private int returnToHome_continueNormalMode_safeLand_droneLandedOnWater;

    private int returnToHome_startEconomyMode_safeLand_droneLandedOnWater;

    private int returnToHome_continueNormalMode_moveASide_safeLand_droneLandedSucessfully;

    private int returnToHome_moveASide_startEconomyMode_safeLand_droneLandedSucessfully
            ;
    private int glide_startEconomyMode_moveAside_droneLandedSucessfully;

    private int safeLand_continueNormalMode_droneLandedSuccessfully;

    private int safeLand_startEconomyMode_droneLandedSuccessfully;

    private int keepFlying_safeLand_arrivedAtDestination;

    private int keepFlying_continueNormalMode_safeLand_arrivedAtDestination;

    private int keepFlying_startEconomyMode_safeLand_arrivedAtDestination;

    private int moveAside_safeLande_DroneLandedSucessfully;

    private int moveAside_continueNormalMode_safeLande_DroneLandedSucessfully;

    private int moveAside_startEconomyMode_safeLande_DroneLandedSucessfully;

    private int glide_continueNormalMode_safeLand_droneLandedSucessfully;

    private int glide_startEconomyMode_safeLand_droneLandedSucessfully;

    private int glide_continueNormalMode_safeLand_droneLandedOnWater;

    private int glide_startEconomyMode_safeLand_droneLandedOnWater;

    private int glide_continueNormalMode_keepFlying_arrivedAtDestination;

    private int glide_startEconomyMode_keepFlying_arrivedAtDestination;

    private int glide_continueNormalMode_moveAside_droneLandedSucessfully;

*/

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("res/droneAnalyzerLog.fxml"));
        primaryStage.setTitle("Drone Logs Analyzer");
        primaryStage.setScene(new Scene(root, 500, 425));
        primaryStage.show();

        fileChooser.setTitle("Choose text file with logs (logs.txt)");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));


    }

    public void initialize() {
        chooseBtn.setOnAction(event -> {
            try {
                showChooserAndSetPathAction();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        clearButton.setOnAction(event -> {
            selectedFile = null;
            pathLogsFileTxtFild.setText("");
            answerTextArea.setText("");
            logsMap.clear();

            clearVariables();
        });
    }

    private void clearVariables() {

        for( Field field:this.getClass().getDeclaredFields()){
            if(field.getName().contains("_")){
                try {
                    field.set(this, 0);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showChooserAndSetPathAction() throws IOException {
        selectedFile = openChooser();

        if(pathLogsFileTxtFild != null){
            pathLogsFileTxtFild.setText(selectedFile.getPath());
        }


        if (checkExistFile()) {
            readFile();
        } else {
            pathLogsFileTxtFild.setText("ERROR PATH!");
        }
    }

    private File openChooser() {
        return fileChooser.showOpenDialog(primaryStage);
    }

    private void readFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(selectedFile));

        String st;
        while ((st = br.readLine()) != null) {

            if (st.isEmpty()) {
                continue;
            }
            int initialIndex = st.indexOf("[");
            int finalIndex = st.indexOf("]");

            int currentDroneIdentifier = Integer.parseInt(st.substring(initialIndex + 1, finalIndex));

            String currentLog = st.substring(finalIndex + 2, st.length());

            if (logsMap.containsKey(currentDroneIdentifier)) {
                //remove log Flying and Current Battery
                if (currentLog.equals("Flying") || currentLog.contains("Current Battery")
                        /*|| currentLog.equals("Continue Normal Mode aspect")*/) {
                    continue;
                }

              // removeLogsWithBugThread(currentLog,currentDroneIdentifier);

                logsMap.get(currentDroneIdentifier).add(currentLog);


            } else {
                logsMap.put(currentDroneIdentifier, new LinkedList<>());
                //remove log Flying and Current Battery
                if (currentLog.equals("Flying") || currentLog.contains("Current Battery")
                        /*|| currentLog.equals("Continue Normal Mode aspect")*/) {
                    continue;
                }
                logsMap.get(currentDroneIdentifier).add(currentLog);
            }
        }


        answerTextArea.appendText("Removed logs:\nCurrent Battery,\nFlying\n\n");
        answerTextArea.appendText("Amount of Drone:" + logsMap.size() + "\n");

        for (Map.Entry entry : logsMap.entrySet()) {
            if ((int) entry.getKey() >= 1 && (int) entry.getKey() <= 5) {

                LinkedList<String> logs = (LinkedList<String>) entry.getValue();
                countScenaries2(logs);

            }

        }

        answerTextArea.appendText("\n WITHOUT ASPECT\n");

        printVariables();

        clearVariables();


        for (Map.Entry entry : logsMap.entrySet()) {
            if ((int) entry.getKey() >= 6 && (int) entry.getKey() <= 10) {

                LinkedList<String> logs = (LinkedList<String>) entry.getValue();
               /* countScenaries(logs);*/


            }
        }

        answerTextArea.appendText("\n WITH ASPECT\n");

       printVariables();

        clearVariables();

        //traces logs
        for (Map.Entry entry : logsMap.entrySet()) {

            answerTextArea.appendText("\n\n");

            answerTextArea.appendText(("Drone[" + entry.getKey() + "]" + "\n"));
            LinkedList<String> logs = (LinkedList<String>) entry.getValue();

            for (String log : logs) {
                answerTextArea.appendText(log + "\n");
            }


        }


    }

    /*private void removeLogsWithBugThread(String currentLog, int currentDroneIdentifier) {
        *//*remove log bug thread*//*

        if(currentLog.equals("keep Flying aspect") && logsMap.get(currentDroneIdentifier).contains("Return to home completed successfully")){
            logsMap.get(currentDroneIdentifier).remove("Landing");
            logsMap.get(currentDroneIdentifier).remove("Landed");
            logsMap.get(currentDroneIdentifier).remove("shutdown");
            logsMap.get(currentDroneIdentifier).remove("Return to home completed successfully");
        }

        if(currentLog.equals("keep Flying aspect") && logsMap.get(currentDroneIdentifier).contains("Arrived at destination")){
            logsMap.get(currentDroneIdentifier).remove("Landing");
            logsMap.get(currentDroneIdentifier).remove("Landed");
            logsMap.get(currentDroneIdentifier).remove("shutdown");
            logsMap.get(currentDroneIdentifier).remove("Arrived at destination");
        }

        if(currentLog.equals("Move aside aspect") && logsMap.get(currentDroneIdentifier).contains("Return to home completed successfully")){
            logsMap.get(currentDroneIdentifier).remove("Landing");
            logsMap.get(currentDroneIdentifier).remove("Landed");
            logsMap.get(currentDroneIdentifier).remove("shutdown");
            logsMap.get(currentDroneIdentifier).remove("Return to home completed successfully");
        }

        if(currentLog.equals("Move aside aspect") && logsMap.get(currentDroneIdentifier).contains("Arrived at destination")){
            logsMap.get(currentDroneIdentifier).remove("Landing");
            logsMap.get(currentDroneIdentifier).remove("Landed");
            logsMap.get(currentDroneIdentifier).remove("shutdown");
            logsMap.get(currentDroneIdentifier).remove("Arrived at destination");
        }

        *//*remove log bug thread*//*
    }*/

    private void printVariables() {
        answerTextArea.appendText("\n");
      for( Field field:this.getClass().getDeclaredFields()){
          if(field.getName().contains("_")){
              try {
                  answerTextArea.appendText(field.getName()+ " "+field.get(this)+"\n");
              } catch (IllegalAccessException e) {
                  e.printStackTrace();
              }
          }
      }

    }


    private void countScenaries2(LinkedList<String> logs){
        if(logs.contains(ARRIVED_AT_DESTINATION)){
            Landed_at_Destination_Normally++;
        }

        if(logs.contains(ARRIVED_AT_DESTINATION_ASPECT)){
            Landed_at_Destination_by_Keep_Flying++;
        }

        if(logs.contains(DRONE_LANDED_SUCCESSFULLY)
                ||logs.contains(DRONE_LANDED_SUCCESSFULLY_ASPECT)){
             Landed_on_ground++;
        }

        if(logs.contains(MOVE_ASIDE_ASPECT)){
            Landed_on_ground_after_moving_aside++;
        }

        if(logs.contains(DRONE_LANDED_ON_WATER)){
            Landed_on_Water++;
        }

        if(logs.contains(RETURN_TO_HOME_COMPLETED_SUCCESSFULLY)){
            Returned_to_Home++;
        }

        if(logs.contains(RETURN_TO_HOME)
                && (logs.contains(SAFE_LAND) || (logs.contains(SAFE_LAND_ASPECT) ))){
            SafeLanded_while_Returning_to_Home++;
        }

        if(logs.contains(GLIDE_ASPECT)
                && (logs.contains(SAFE_LAND) || (logs.contains(SAFE_LAND_ASPECT) ))){
            Glided_and_SafeLanded++;
        }

        if(logs.contains(GLIDE_ASPECT) && logs.contains(ARRIVED_AT_DESTINATION)){
            Glided_and_Landed_at_Destination_Normally++;
        }

        if(logs.contains(GLIDE_ASPECT) && logs.contains(ARRIVED_AT_DESTINATION_ASPECT)){
            Glided_and_Landed_at_Destination_by_Keep_Flying++;
        }

        if(logs.contains(START_ECONOMY_MODE)){
            Activated_Economy_Mode++;
        }

        if(logs.contains(START_ECONOMY_MODE) && (logs.contains(SAFE_LAND) || logs.contains(SAFE_LAND_ASPECT))){
            Activated_Economy_Mode_and_SafeLanded++;
        }



    }

  /*  private void countScenaries(LinkedList<String> logs) {



        *//*Arrived at destination*//*
        if (logs.contains("Arrived at destination")
                && !logs.contains("keep Flying aspect")
                && !logs.contains("Glide aspect")
                && !logs.contains("Continue Normal Mode aspect")) {
            arrivedAtDestination_withoutExceptionalScenaries++;

        }

        if (logs.contains("Arrived at destination") && logs.contains("Start Economy Mode")
                && !logs.contains("keep Flying aspect")
                && !logs.contains("Glide aspect")
                && !logs.contains("Continue Normal Mode aspect")) {

            arrivedAtDestination_startEconomyMode++;

        }

        if (logs.contains("Arrived at destination aspect") && logs.contains("keep Flying aspect")
                && !logs.contains("Glide aspect")
                && !logs.contains("Continue Normal Mode aspect")) {

            arrivedAtDestination_keepFlying++;

        }

        if (logs.contains("Arrived at destination aspect") && logs.contains("keep Flying aspect") && logs.contains("Start Economy Mode")
                && !logs.contains("Glide aspect")
                && !logs.contains("Continue Normal Mode aspect")) {

            arrivedAtDestination_startEconomyMode_keepFlying++;

        }

        if (logs.contains("Arrived at destination") && logs.contains("Glide aspect")
                && !logs.contains("keep Flying aspect")
                && !logs.contains("Continue Normal Mode aspect")) {
            arrivedAtDestination_glide++;

        }


        if (logs.contains("Arrived at destination") && logs.contains("Glide aspect") && logs.contains("Start Economy Mode")
                && !logs.contains("keep Flying aspect")
                && !logs.contains("Continue Normal Mode aspect")) {
            arrivedAtDestination_startEconomyMode_glide++;

        }

        if (logs.contains("Arrived at destination")  && logs.contains("Continue Normal Mode aspect")
                && !logs.contains("keep Flying aspect")
                && !logs.contains("Glide aspect")) {
            arrivedAtDestination_continueNormalMode++;

        }

        if (logs.contains("Arrived at destination aspect") && logs.contains("keep Flying aspect") && logs.contains("Glide aspect")
                && !logs.contains("Continue Normal Mode aspect")) {
            arrivedAtDestination_glide_keepFlying++;

        }

        if (logs.contains("Arrived at destination aspect") && logs.contains("keep Flying aspect") && logs.contains("Glide aspect") && logs.contains("Start Economy Mode")
                && !logs.contains("Continue Normal Mode aspect")) {
            arrivedAtDestination_startEconomyMode_glide_keepFlying++;

        }

        if (logs.contains("Arrived at destination aspect") && logs.contains("keep Flying aspect") && logs.contains("Continue Normal Mode aspect")
                && !logs.contains("Glide aspect") ) {
            arrivedAtDestination_continueNormalMode_keepFlying++;

        }

        if (logs.contains("Arrived at destination")  && logs.contains("Glide aspect") && logs.contains("Continue Normal Mode aspect")
                && !logs.contains("keep Flying aspect")) {
            arrivedAtDestination_continueNormalMode_glide++;

        }

        if (logs.contains("Arrived at destination") || logs.contains("Arrived at destination aspect") && logs.contains("keep Flying aspect") && logs.contains("Glide aspect") && logs.contains("Continue Normal Mode aspect")) {
            arrivedAtDestination_keepFlying_glide_continueNormalMode_++;

        }

        if (logs.contains("Arrived at destination") || logs.contains("Arrived at destination aspect") && logs.contains("keep Flying aspect") && logs.contains("Glide aspect") && logs.contains("Start Economy Mode")) {
            arrivedAtDestination_keepFlying_glide_startEconomyMode++;

        }



        *//*returnToHome*//*
        if (logs.contains("Return to Home") && logs.contains("Return to home completed successfully")
                &&!logs.contains("Continue Normal Mode aspect")
                &&!logs.contains("Start Economy Mode")){

            returnToHome_returntoHomeCompletedSuccessfully++;

        }

        if (logs.contains("Return to Home") && logs.contains("Return to home completed successfully") && logs.contains("Continue Normal Mode aspect")
                &&!logs.contains("Start Economy Mode")) {
            returnToHome_continueNormalMode_returntoHomeCompletedSuccessfully++;

        }

        if (logs.contains("Return to Home") && logs.contains("Return to home completed successfully") && logs.contains("Start Economy Mode")
                && !logs.contains("Continue Normal Mode aspect")) {
            returnToHome_startEconomyMode_returntoHomeCompletedSuccessfully++;

        }




        if (logs.contains("Return to Home") && logs.contains("SafeLand") && logs.contains("Drone landed successfully")
                &&!logs.contains("Continue Normal Mode aspect")
                &&!logs.contains("Start Economy Mode")) {
            returnToHome_safeLand_droneLandedSucessfully++;

        }

        if (logs.contains("Return to Home") && logs.contains("SafeLand") && logs.contains("Drone landed successfully") && logs.contains("Continue Normal Mode aspect")
                &&!logs.contains("Start Economy Mode")) {
            returnToHome_safeLand_continueNormalMode_droneLandedSucessfully++;

        }

        if (logs.contains("Return to Home") && logs.contains("SafeLand") && logs.contains("Drone landed successfully") && logs.contains("Start Economy Mode")
                &&!logs.contains("Continue Normal Mode aspect")
                ) {
            returnToHome_safeLand_startEconomyMode_droneLandedSucessfully++;

        }



        if (logs.contains("Return to Home") && logs.contains("SafeLand") && logs.contains("Drone landed on water")
                &&!logs.contains("Continue Normal Mode aspect")
                &&!logs.contains("Start Economy Mode")) {

            returnToHome_safeLand_droneLandedOnWater++;

        }


        if (logs.contains("Return to Home") && logs.contains("SafeLand") && logs.contains("Drone landed on water") && logs.contains("Continue Normal Mode aspect")
                &&!logs.contains("Start Economy Mode")) {

            returnToHome_continueNormalMode_safeLand_droneLandedOnWater++;

        }

        if (logs.contains("Return to Home") && logs.contains("SafeLand") && logs.contains("Drone landed on water") && logs.contains("Start Economy Mode")
                &&!logs.contains("Continue Normal Mode aspect")) {

            returnToHome_startEconomyMode_safeLand_droneLandedOnWater++;

        }




        if (logs.contains("Return to Home") && logs.contains("Move aside aspect") && logs.contains("SafeLand aspect") && logs.contains("Drone landed successfully")
                &&!logs.contains("Continue Normal Mode aspect")
                &&!logs.contains("Start Economy Mode")) {

            returnToHome_moveASide_safeLand_droneLandedSucessfully++;

        }

        if (logs.contains("Return to Home") && logs.contains("Move aside aspect") && logs.contains("SafeLand aspect") && logs.contains("Drone landed successfully") && logs.contains("Continue Normal Mode aspect")
                &&!logs.contains("Start Economy Mode")) {

            returnToHome_continueNormalMode_moveASide_safeLand_droneLandedSucessfully++;

        }

        if (logs.contains("Return to Home") && logs.contains("Move aside aspect") && logs.contains("SafeLand aspect") && logs.contains("Drone landed successfully") && logs.contains("Start Economy Mode")
                &&!logs.contains("Continue Normal Mode aspect")) {

            returnToHome_moveASide_startEconomyMode_safeLand_droneLandedSucessfully++;

        }





        *//*SafeLand*//*
        if ( logs.contains("SafeLand") && logs.contains("Drone landed successfully")
                && !logs.contains("Return to Home")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Glide aspect")
                && !logs.contains("Continue Normal Mode aspect")
                && !logs.contains("Start Economy Mode")) {
                safeLand_droneLandedSuccessfully++;

        }

        if ( logs.contains("SafeLand") && logs.contains("Drone landed successfully")  && logs.contains("Continue Normal Mode aspect")
                && !logs.contains("Return to Home")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Glide aspect")
                && !logs.contains("Start Economy Mode")) {
            safeLand_continueNormalMode_droneLandedSuccessfully++;

        }

        if ( logs.contains("SafeLand") && logs.contains("Drone landed successfully") && logs.contains("Start Economy Mode")
                && !logs.contains("Return to Home")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Glide aspect")
                && !logs.contains("Continue Normal Mode aspect")) {
            safeLand_startEconomyMode_droneLandedSuccessfully++;

        }

        *//*KeepFlying*//*
        if (logs.contains("keep Flying aspect") && logs.contains("SafeLand aspect") && logs.contains("Arrived at destination aspect")
                && !logs.contains("Return to Home")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Glide aspect")
                && !logs.contains("Continue Normal Mode aspect")
                && !logs.contains("Start Economy Mode")) {
            keepFlying_safeLand_arrivedAtDestination++;

        }

        if (logs.contains("keep Flying aspect") && logs.contains("SafeLand aspect") && logs.contains("Arrived at destination aspect")  && logs.contains("Continue Normal Mode aspect")
                && !logs.contains("Return to Home")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Glide aspect")
                && !logs.contains("Start Economy Mode")) {
            keepFlying_continueNormalMode_safeLand_arrivedAtDestination++;

        }

        if (logs.contains("keep Flying aspect") && logs.contains("SafeLand aspect") && logs.contains("Arrived at destination aspect") && logs.contains("Start Economy Mode")
                && !logs.contains("Return to Home")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Glide aspect")
                && !logs.contains("Continue Normal Mode aspect")) {
            keepFlying_startEconomyMode_safeLand_arrivedAtDestination++;

        }

        *//*moveAside*//*
        if ( logs.contains("Move aside aspect") && logs.contains("SafeLand aspect") && logs.contains("Drone landed successfully")
                && !logs.contains("Return to Home")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Glide aspect")
                && !logs.contains("Continue Normal Mode aspect")
                && !logs.contains("Start Economy Mode")) {
            moveAside_safeLande_DroneLandedSucessfully++;

        }

        if ( logs.contains("Move aside aspect") && logs.contains("SafeLand aspect") && logs.contains("Drone landed successfully") && logs.contains("Continue Normal Mode aspect")
                && !logs.contains("Return to Home")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Glide aspect")
                && !logs.contains("Start Economy Mode")) {
            moveAside_continueNormalMode_safeLande_DroneLandedSucessfully++;

        }

        if ( logs.contains("Move aside aspect") && logs.contains("SafeLand aspect") && logs.contains("Drone landed successfully") && logs.contains("Start Economy Mode")
                && !logs.contains("Return to Home")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Glide aspect")
                && !logs.contains("Continue Normal Mode aspect")) {
            moveAside_startEconomyMode_safeLande_DroneLandedSucessfully++;

        }






        *//*glade*//*

        if (logs.contains("Glide aspect")  && logs.contains("Arrived at destination")
                && !logs.contains("SafeLand")
                && !logs.contains("keep Flying aspect")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Continue Normal Mode aspect")
                && !logs.contains("Start Economy Mode")) {

            glide_safeLand_droneLandedSucessfully++;

        }

        if (logs.contains("Glide aspect") && logs.contains("SafeLand") && logs.contains("Drone landed successfully")
                && !logs.contains("keep Flying aspect")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Continue Normal Mode aspect")
                && !logs.contains("Start Economy Mode")) {

            glide_safeLand_droneLandedSucessfully++;

        }

        if (logs.contains("Glide aspect") && logs.contains("SafeLand") && logs.contains("Drone landed successfully")  && logs.contains("Continue Normal Mode aspect")
                && !logs.contains("keep Flying aspect")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Start Economy Mode")) {

            glide_continueNormalMode_safeLand_droneLandedSucessfully++;

        }

        if (logs.contains("Glide aspect") && logs.contains("SafeLand") && logs.contains("Drone landed successfully") && logs.contains("Start Economy Mode")
                && !logs.contains("keep Flying aspect")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Continue Normal Mode aspect")) {

            glide_startEconomyMode_safeLand_droneLandedSucessfully++;

        }

        if (logs.contains("Glide aspect") && logs.contains("SafeLand") && logs.contains("Drone landed on water")
                && !logs.contains("keep Flying aspect")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Continue Normal Mode aspect")
                && !logs.contains("Start Economy Mode")) {
            glide_safeLand_droneLandedOnWater++;

        }

        if (logs.contains("Glide aspect") && logs.contains("SafeLand") && logs.contains("Drone landed on water")  && logs.contains("Continue Normal Mode aspect")
                && !logs.contains("keep Flying aspect")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Start Economy Mode")) {
            glide_continueNormalMode_safeLand_droneLandedOnWater++;

        }

        if (logs.contains("Glide aspect") && logs.contains("SafeLand") && logs.contains("Drone landed on water") && logs.contains("Start Economy Mode")
                && !logs.contains("keep Flying aspect")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Continue Normal Mode aspect")) {
            glide_startEconomyMode_safeLand_droneLandedOnWater++;

        }

        if (logs.contains("Glide aspect") && logs.contains("SafeLand aspect") && logs.contains("Arrived at destination aspect")
                && logs.contains("keep Flying aspect")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Continue Normal Mode aspect")
                && !logs.contains("Start Economy Mode")) {
            glide_keepFlying_arrivedAtDestination++;

        }

        if (logs.contains("Glide aspect") && logs.contains("SafeLand aspect") && logs.contains("Arrived at destination aspect") && logs.contains("Continue Normal Mode aspect")
                && logs.contains("keep Flying aspect")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Start Economy Mode")) {
            glide_continueNormalMode_keepFlying_arrivedAtDestination++;

        }

        if (logs.contains("Glide aspect") && logs.contains("SafeLand aspect") && logs.contains("Arrived at destination aspect") && logs.contains("Start Economy Mode")
                && logs.contains("keep Flying aspect")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Continue Normal Mode aspect")) {
            glide_startEconomyMode_keepFlying_arrivedAtDestination++;

        }

        if (logs.contains("Glide aspect") && logs.contains("SafeLand aspect") && logs.contains("Drone landed successfully aspect")
                && !logs.contains("keep Flying aspect")
                && logs.contains("Move aside aspect")
                && !logs.contains("Continue Normal Mode aspect")
                && !logs.contains("Start Economy Mode")) {
            glide_moveAside_droneLandedSucessfully++;

        }

        if (logs.contains("Glide aspect") && logs.contains("SafeLand aspect") && logs.contains("Drone landed successfully aspect")  && logs.contains("Continue Normal Mode aspect")
                && !logs.contains("keep Flying aspect")
                && logs.contains("Move aside aspect")
                && !logs.contains("Start Economy Mode")) {
            glide_continueNormalMode_moveAside_droneLandedSucessfully++;

        }

        if (logs.contains("Glide aspect") && logs.contains("SafeLand aspect") && logs.contains("Drone landed successfully aspect") && logs.contains("Start Economy Mode")
                && !logs.contains("keep Flying aspect")
                && logs.contains("Move aside aspect")
                && !logs.contains("Continue Normal Mode aspect")) {
            glide_startEconomyMode_moveAside_droneLandedSucessfully++;

        }


        if (logs.contains("Glide aspect") && !logs.contains("SafeLand") && !logs.contains("keep Flying aspect") && logs.contains("Continue Normal Mode aspect") && logs.contains("Arrived at destination")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Start Economy Mode")) {

            continueNormalMode_glide_arrivedAtDestination++;

        }

        if (logs.contains("Glide aspect") && logs.contains("SafeLand") && logs.contains("Drone landed successfully") && logs.contains("Continue Normal Mode aspect")
                && !logs.contains("keep Flying aspect")
                && !logs.contains("Move aside aspect")) {

            continueNormalMode_glide_safeLand_droneLandedSucessfully++;

        }

        if (logs.contains("Glide aspect") && logs.contains("SafeLand") && logs.contains("Drone landed on water")  && logs.contains("Continue Normal Mode aspect")
                && !logs.contains("keep Flying aspect")
                && !logs.contains("Move aside aspect")) {
            continueNormalMode_glide_safeLand_DroneLandedOnWater++;

        }

        if (logs.contains("Glide aspect") && logs.contains("SafeLand aspect") && logs.contains("Arrived at destination aspect") && logs.contains("Continue Normal Mode aspect")
                && logs.contains("keep Flying aspect")
                && !logs.contains("Move aside aspect")) {
            continueNormalMode_glide_keepFlying_arrivedAtDestination++;

        }















*//*
        if (logs.contains("keep Flying aspect") && logs.contains("Arrived at destination aspect") && !logs.contains("Glide aspect")) {
            landedAtDestinationByKeepFlyingCount++;

        }   if (logs.contains("SafeLand")  && !logs.contains("Glide aspect") && !logs.contains("keep Flying aspect")) {
            safelandNormalCount++;

        }  if (logs.contains("Move aside aspect") && logs.contains("Drone landed successfully aspect")) {
            landedOnGroundAfterMovingAsideCount++;

        }  if (logs.contains("SafeLand") && logs.contains("Drone landed on water") && !logs.contains("Glide aspect")) {
            landedOnWaterCount++;

        } if(logs.contains("SafeLand") && logs.contains("Drone landed successfully") && !logs.contains("Glide aspect")){
            landedOnGroundCount++;
        }

         if(logs.contains("Return to home completed successfully" ) && !logs.contains("Glide aspect")){
            returnToHome_returntoHomeCompletedSuccessfully++;

        } if(logs.contains("Return to Home") && logs.contains("SafeLand") && !logs.contains("Glide aspect")){
            safeLandedWhileReturningToHomeCount++;

        } if(logs.contains("Glide aspect") && logs.contains("SafeLand")){
            glideAndSafeLandCount++;
        }
        if(logs.contains("Glide aspect") && logs.contains("Arrived at destination") && !logs.contains("keep Flying aspect")){
            glideAndLandedAtDestinationNormallyCount++;
        }
        if(logs.contains("Glide aspect") && logs.contains("keep Flying aspect")&& logs.contains("Arrived at destination")){
            glidedAndLandedAtDestinationByKeepFlyingCount++;
        }*//*
    }*/


    private boolean checkExistFile() {
        return selectedFile != null;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
