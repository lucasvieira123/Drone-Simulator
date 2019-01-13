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

 /*   private int safelandNormalCount = 0;
    private int landedOnGroundAfterMovingAsideCount = 0;
    private int landedOnWaterCount = 0;
    private int safeLandedWhileReturningToHomeCount;
    private int landedOnGroundCount;
    private int glideAndSafeLandCount;
    private int glideAndLandedAtDestinationNormallyCount;
    private int glidedAndLandedAtDestinationByKeepFlyingCount;*/


    private int arrivedAtDestination_withoutExceptionalScenaries = 0;
    private int returnToHome_returntoHomeCompletedSuccessfully = 0;
    private int arrivedAtDestination_keepFlying;
    private int arrivedAtDestination_glide;
    private int arrivedAtDestination_continueNormalMode;
    private int arrivedAtDestination_glide_keepFlying;
    private int arrivedAtDestination_continueNormalMode_keepFlying;
    private int arrivedAtDestination_continueNormalMode_glide;
    private int landedAtDestinationAllSituations;
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


       /* safelandNormalCount = 0;
        landedAtDestinationByKeepFlyingCount = 0;
        landedOnGroundAfterMovingAsideCount = 0;
        landedOnWaterCount = 0;
        safeLandedWhileReturningToHomeCount = 0;
        landedOnGroundCount = 0;
        glideAndSafeLandCount = 0;
        glideAndLandedAtDestinationNormallyCount = 0;
        glidedAndLandedAtDestinationByKeepFlyingCount = 0;
*/



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
                        || currentLog.equals("Continue Normal Mode aspect")) {
                    continue;
                }

                logsMap.get(currentDroneIdentifier).add(currentLog);
            } else {
                logsMap.put(currentDroneIdentifier, new LinkedList<>());
                //remove log Flying and Current Battery
                if (currentLog.equals("Flying") || currentLog.contains("Current Battery")
                        || currentLog.equals("Continue Normal Mode aspect")) {
                    continue;
                }
                logsMap.get(currentDroneIdentifier).add(currentLog);
            }
        }


        answerTextArea.appendText("Removed logs:\nCurrent Battery,\nFlying,\nContinue Normal Mode aspect\n\n");
        answerTextArea.appendText("Amount of Drone:" + logsMap.size() + "\n");

        for (Map.Entry entry : logsMap.entrySet()) {
            if ((int) entry.getKey() >= 1 && (int) entry.getKey() <= 5) {

                LinkedList<String> logs = (LinkedList<String>) entry.getValue();
                countScenaries(logs);

            }

        }

        answerTextArea.appendText("\n WITHOUT ASPECT\n");

        printVariables();

        clearVariables();


        for (Map.Entry entry : logsMap.entrySet()) {
            if ((int) entry.getKey() >= 6 && (int) entry.getKey() <= 10) {

                LinkedList<String> logs = (LinkedList<String>) entry.getValue();
                countScenaries(logs);


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

    private void countScenaries(LinkedList<String> logs) {

        /*Arrived at destination*/
        if (logs.contains("Arrived at destination") && !logs.contains("keep Flying aspect") && !logs.contains("Glide aspect") && !logs.contains("Continue Normal Mode aspect")) {
            arrivedAtDestination_withoutExceptionalScenaries++;

        }

        if (logs.contains("Arrived at destination aspect") && logs.contains("keep Flying aspect") && !logs.contains("Glide aspect") && !logs.contains("Continue Normal Mode aspect")) {
            arrivedAtDestination_keepFlying++;

        }

        if (logs.contains("Arrived at destination") && !logs.contains("keep Flying aspect") && logs.contains("Glide aspect") && !logs.contains("Continue Normal Mode aspect")) {
            arrivedAtDestination_glide++;

        }

        if (logs.contains("Arrived at destination") && !logs.contains("keep Flying aspect") && !logs.contains("Glide aspect") && logs.contains("Continue Normal Mode aspect")) {
            arrivedAtDestination_continueNormalMode++;

        }

        if (logs.contains("Arrived at destination aspect") && logs.contains("keep Flying aspect") && logs.contains("Glide aspect") && !logs.contains("Continue Normal Mode aspect")) {
            arrivedAtDestination_glide_keepFlying++;

        }

        if (logs.contains("Arrived at destination aspect") && logs.contains("keep Flying aspect") && !logs.contains("Glide aspect") && logs.contains("Continue Normal Mode aspect")) {
            arrivedAtDestination_continueNormalMode_keepFlying++;

        }

        if (logs.contains("Arrived at destination") && !logs.contains("keep Flying aspect") && logs.contains("Glide aspect") && logs.contains("Continue Normal Mode aspect")) {
            arrivedAtDestination_continueNormalMode_glide++;

        }

        if (logs.contains("Arrived at destination") || logs.contains("Arrived at destination aspect") && logs.contains("keep Flying aspect") && logs.contains("Glide aspect") && logs.contains("Continue Normal Mode aspect")) {
            landedAtDestinationAllSituations++;

        }


        /*returnToHome*/
        if (logs.contains("Return to Home") && logs.contains("Return to home completed successfully")) {
            returnToHome_returntoHomeCompletedSuccessfully++;

        }

        if (logs.contains("Return to Home") && logs.contains("SafeLand") && logs.contains("Drone landed successfully")) {
            returnToHome_safeLand_droneLandedSucessfully++;

        }

        if (logs.contains("Return to Home") && logs.contains("SafeLand") && logs.contains("Drone landed on water")) {
            returnToHome_safeLand_droneLandedOnWater++;

        }

        if (logs.contains("Return to Home") && logs.contains("Move aside aspect") && logs.contains("SafeLand aspect") && logs.contains("Drone landed successfully")) {
            returnToHome_moveASide_safeLand_droneLandedSucessfully++;

        }





        /*SafeLand*/
        if ( logs.contains("SafeLand") && logs.contains("Drone landed successfully")
                && !logs.contains("Return to Home")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Glide aspect")
                && !logs.contains("Continue Normal Mode aspect")) {
                safeLand_droneLandedSuccessfully++;

        }

        /*KeepFlying*/
        if (logs.contains("keep Flying aspect") && logs.contains("SafeLand aspect") && logs.contains("Arrived at destination aspect")
                && !logs.contains("Return to Home")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Glide aspect")
                && !logs.contains("Continue Normal Mode aspect")) {
            safeLand_droneLandedSuccessfully++;

        }

        /*moveAside*/
        if ( logs.contains("Move aside aspect") && logs.contains("SafeLand aspect") && logs.contains("Drone landed successfully")
                && !logs.contains("Return to Home")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Glide aspect")
                && !logs.contains("Continue Normal Mode aspect")) {
            safeLand_droneLandedSuccessfully++;

        }






        /*glade*/
      /*  if (logs.contains("Glide aspect") && !logs.contains("SafeLand") && !logs.contains("keep Flying aspect")
                && !logs.contains("Move aside aspect") && !logs.contains("Continue Normal Mode aspect") && logs.contains("Arrived at destination")) {

            glide_arrivedAtDestination++;

        }*/

        if (logs.contains("Glide aspect") && logs.contains("SafeLand") && logs.contains("Drone landed successfully")
                && !logs.contains("keep Flying aspect")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Continue Normal Mode aspect")) {

            glide_safeLand_droneLandedSucessfully++;

        }

        if (logs.contains("Glide aspect") && logs.contains("SafeLand") && logs.contains("Drone landed on water")
                && !logs.contains("keep Flying aspect")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Continue Normal Mode aspect")) {
            glide_safeLand_droneLandedOnWater++;

        }

        if (logs.contains("Glide aspect") && logs.contains("SafeLand aspect") && logs.contains("Arrived at destination aspect")
                && logs.contains("keep Flying aspect")
                && !logs.contains("Move aside aspect")
                && !logs.contains("Continue Normal Mode aspect")) {
            glide_keepFlying_arrivedAtDestination++;

        }

        if (logs.contains("Glide aspect") && logs.contains("SafeLand aspect") && logs.contains("Drone landed successfully aspect")
                && !logs.contains("keep Flying aspect")
                && logs.contains("Move aside aspect")
                && !logs.contains("Continue Normal Mode aspect")) {
            glide_moveAside_droneLandedSucessfully++;

        }


        if (logs.contains("Glide aspect") && !logs.contains("SafeLand") && !logs.contains("keep Flying aspect")
                && !logs.contains("Move aside aspect") && logs.contains("Continue Normal Mode aspect") && logs.contains("Arrived at destination")) {

            continueNormalMode_glide_arrivedAtDestination++;

        }

        if (logs.contains("Glide aspect") && logs.contains("SafeLand") && logs.contains("Drone landed successfully")
                && !logs.contains("keep Flying aspect")
                && !logs.contains("Move aside aspect")
                && logs.contains("Continue Normal Mode aspect")) {

            continueNormalMode_glide_safeLand_droneLandedSucessfully++;

        }

        if (logs.contains("Glide aspect") && logs.contains("SafeLand") && logs.contains("Drone landed on water")
                && !logs.contains("keep Flying aspect")
                && !logs.contains("Move aside aspect")
                && logs.contains("Continue Normal Mode aspect")) {
            continueNormalMode_glide_safeLand_DroneLandedOnWater++;

        }

        if (logs.contains("Glide aspect") && logs.contains("SafeLand aspect") && logs.contains("Arrived at destination aspect")
                && logs.contains("keep Flying aspect")
                && !logs.contains("Move aside aspect")
                && logs.contains("Continue Normal Mode aspect")) {
            continueNormalMode_glide_keepFlying_arrivedAtDestination++;

        }















/*
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
        }*/
    }


    private boolean checkExistFile() {
        return selectedFile != null;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
