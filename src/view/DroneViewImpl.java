package view;


import controller.LoggerController;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import model.Drone;
import model.Hospital;
import view.res.EnvironmentView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class DroneViewImpl extends Group implements DroneView {

    private final EnvironmentView environmentView;
    private Cell currentCell;
    private Drone drone;
    public static double WIDTH = 24;
    public static double HEIGHT = 24;
    public static int COUNT_DRONE = 0;

    ImageView imageViewBadConnection = new ImageView(new Image("/view/res/lostConnection.png"));
    ImageView imageViewDrone = new ImageView( new Image("/view/res/notSelectedDrone.png"));
    public LoggerController loggerController = LoggerController.getInstance();

    private TimerTask batteryDecrementertimerTask;
    private TimerTask goAutomaticDestinyTimerTask;
    private TimerTask returnToHomeTimerTask;
    private KeyCode currentCommand;


    public DroneViewImpl(Cell currentCell, Hospital sourceHospital, Hospital destinyHopistal) {


        imageViewBadConnection.setVisible(false);



        environmentView = currentCell.getEnvironmentView();
        this.currentCell = currentCell;
        COUNT_DRONE++;

        drone = new Drone(COUNT_DRONE, currentCell.getI(), currentCell.getJ(), sourceHospital, destinyHopistal);

        double distanceHospitalSource = calculeteDistanceFrom(drone.getSourceHospital());
        double distanceHospitalDestiny = calculeteDistanceFrom(drone.getDestinyHopistal());


        drone.setDistanceHospitalSource(distanceHospitalSource);
        drone.setDistanceHospitalDestiny(distanceHospitalDestiny);

        Label label = new Label();
        label.setText(String.valueOf(COUNT_DRONE));
        label.setTextFill(Color.RED);
        label.setTextAlignment(TextAlignment.CENTER);


        getChildren().addAll(imageViewDrone,label, imageViewBadConnection);

        currentCell.getChildren().add(this);


    }


    public void eventKey(KeyCode keyCode) {
        if(!isValidKeyCode(keyCode)){
            return;
        }

        if(drone.isBadConnection()){
            return;
        }

        if(drone.isAutomatic()){
            return;
        }

        currentCommand = keyCode;

        checkStatus();
    }

    private boolean isValidKeyCode(KeyCode keyCode) {
        if(keyCode == KeyCode.A || keyCode == KeyCode.W || keyCode == KeyCode.S || keyCode == KeyCode.D || keyCode == KeyCode.R || keyCode.getName().contains("Space") ){
            return true;
        }
        return false;
    }

    synchronized private void checkStatus() {

    /*    if(drone.isSafeLand()){
            return;
        }
*/

        if(drone.getCurrentBattery()>10 && drone.getDistanceHospitalDestiny()>0 && commandWasPerformed()
                && drone.isManual() && !drone.isGoingManualToDestiny()&& !drone.isReturningToHome()
                && !drone.isBadConnection()
                && !drone.isSafeLand()){

            executeCommandsFromKeyBoard();
            currentCommand = null;
        }

        if(drone.getCurrentBattery()>10 && drone.getDistanceHospitalDestiny()>0 && drone.isAutomatic() && !drone.isGoingAutomaticToDestiny()
                && !drone.isReturningToHome() && !drone.isBadConnection() && !drone.isSafeLand()){

            goDestinyAutomatic();

           return;
        }

        if(!drone.isAutomatic()){
            if(drone.isGoingAutomaticToDestiny()){
                stopGoAutomaticDestiny();
                drone.setGoingAutomaticToDestiny(false);
            }


        }

        if(!drone.isTookOff()){
            return;
        }

        if(currentCell.isBadConnection() && !drone.isReturningToHome() && !drone.isBadConnection()){
            notifyBadConnection();

            System.out.println("Drone["+drone.getId()+"] "+"Bad Connection");
            loggerController.print("Drone["+drone.getId()+"] "+"Bad Connection");

            if(drone.isGoingAutomaticToDestiny()){
                stopGoAutomaticDestiny();
                drone.setGoingAutomaticToDestiny(false);
            }

            applyStyleBadConnection();
            removeStyleSelected();

            returnToHome();

        }if(!currentCell.isBadConnection() && !drone.isReturningToHome() && drone.isBadConnection()){
            applyStyleNormalConnection();

            notifyNormalConnection();

            System.out.println("Drone["+drone.getId()+"] "+"Normal Connection");
            loggerController.print("Drone["+drone.getId()+"] "+"Normal Connection");
        }

        if(drone.isBadConnection() && drone.isReturningToHome() && drone.getDistanceHospitalSource() == 0){
            stopReturnToHome();
            stopBatteryDecrementer();

            landing();

            shutDown();

            drone.setGoingAutomaticToDestiny(false);
            drone.setGoingManualToDestiny(false);

            checkAndPrintIfLostDrone();

        }

        if(drone.getCurrentBattery() <= 15){
            applyEconomyMode();
        }

        if(drone.getCurrentBattery() <= 10 && drone.getDistanceHospitalDestiny()>0 && !drone.isSafeLand()){
            stopGoAutomaticDestiny();
            stopBatteryDecrementer();
            stopReturnToHome();


            drone.setGoingAutomaticToDestiny(false);
            drone.setGoingManualToDestiny(false);


            safeLanding();





        }

        if(drone.getDistanceHospitalDestiny() == 0){
            stopBatteryDecrementer();

            stopGoAutomaticDestiny();

            drone.setGoingAutomaticToDestiny(false);
            drone.setGoingManualToDestiny(false);

            landing();

            shutDown();

            checkAndPrintIfLostDrone();
        }


    }

    private void executeCommandsFromKeyBoard() {
        if(currentCommand == KeyCode.R){
            if(!drone.isTookOff()){

                if(drone.isStarted()){

                    shutDown();

                    stopBatteryDecrementer();
                    stopReturnToHome();
                    stopGoAutomaticDestiny();

                }else {
                    start();

                    stopBatteryDecrementer();
                    startBatteryDecrementer();

                    updateItIsOver();
                }
            }


        }else if(isDirectionKeys(currentCommand)){
            if(drone.isTookOff()){

                flyingFromKeyBoard(currentCommand);

                updadePositionDroneView();
                updateBattery();
                updateItIsOver();


            }
        }else if(currentCommand.getName().contains("Space")){

            if(drone.isStarted()){

                if(drone.isTookOff()){
                    landing();

                }else {
                    takeOff();

                }
            }

        }
    }

    private boolean commandWasPerformed() {
        return currentCommand !=null;
    }

    private boolean isDirectionKeys(KeyCode keyCode) {
        if(keyCode == KeyCode.A || keyCode == KeyCode.W || keyCode == KeyCode.S || keyCode == KeyCode.D){
            return true;
        }
        return false;
    }

    public void checkAndPrintIfLostDrone() {

        if(drone.isReturningToHome() && drone.getDistanceHospitalSource()==0){
            System.out.println("Drone["+drone.getId()+"] "+"Return to home completed successfully ");
            loggerController.print("Drone["+drone.getId()+"] "+"Return to home completed successfully ");
            return;
        }
        if(drone.isGoingAutomaticToDestiny() && drone.getDistanceHospitalDestiny() ==0){
            System.out.println("Drone["+drone.getId()+"] "+"Arrived at destination");
            loggerController.print("Drone["+drone.getId()+"] "+"Arrived at destination");
            return;
        }

        if(drone.isGoingManualToDestiny()){
            System.out.println("Drone["+drone.getId()+"] "+"Arrived at destination");
            loggerController.print("Drone["+drone.getId()+"] "+"Arrived at destination");
            return;
        }

        if(drone.isOnWater()){
            System.out.println("Drone["+drone.getId()+"] "+"Drone landed on water");
            loggerController.print("Drone["+drone.getId()+"] "+"Drone landed on water");
        }else {
            System.out.println("Drone["+drone.getId()+"] "+"Drone landed sucessfully");
            loggerController.print("Drone["+drone.getId()+"] "+"Drone landed sucessfully");
        }


    }

    public void flyingFromKeyBoard(KeyCode keyCode) {

        System.out.println("Drone["+drone.getId()+"] "+"Flying");
        loggerController.print("Drone["+drone.getId()+"] "+"Flying");

        // irregular moviments
        if(drone.isEconomyMode()){
            Random random = new Random();
             double value = random.nextDouble();

             // right moviments
             if(value>0.8){
                 if(keyCode == KeyCode.D){
                     flyingRight();
                 }
                 else if(keyCode == KeyCode.A){
                     flyingLeft();
                 }
                 else if(keyCode == KeyCode.W){
                     flyingUp();
                 }
                 else if(keyCode == KeyCode.S){
                     flyingDown();
                 }
             }else {
                 //wrong moviments

                 int randomNum = 0 + (int) (Math.random() * 4);
                 System.out.println("Random number " + randomNum);

                 if(randomNum==0){
                     flyingRight();
                 }
                 else if(randomNum==1){
                     flyingLeft();
                 }
                 else if(randomNum==2){
                     flyingUp();
                 }
                 else if( randomNum==3){
                     flyingDown();
                 }
             }


        }else {
            // normal moviment
            if(keyCode == KeyCode.D){
                flyingRight();
            }
            else if(keyCode == KeyCode.A){
                flyingLeft();
            }
            else if(keyCode == KeyCode.W){
                flyingUp();
            }
            else if(keyCode == KeyCode.S){
                flyingDown();
            }
        }


    }

    public void flyingDown( ) {
        int newI =  drone.getCurrentPositionI();
        int newJ = drone.getCurrentPositionJ();
        newI = newI+1;

        if(newI>7 || newI <0){
            return;
        }


        drone.setCurrentPositionI(newI);
    }

    public void flyingUp( ) {
        int newI =  drone.getCurrentPositionI();
        int newJ = drone.getCurrentPositionJ();
        newI = newI-1;

        if(newI>7 || newI <0){
            return;
        }

        drone.setCurrentPositionI(newI);
    }

    public void flyingLeft( ) {
        int newI =  drone.getCurrentPositionI();
        int newJ = drone.getCurrentPositionJ();
        newJ = newJ -1;

        if(newJ>19 || newJ <0){
            return;
        }
        drone.setCurrentPositionJ(newJ);
    }

    public void flyingRight( ) {
        int newI =  drone.getCurrentPositionI();
        int newJ = drone.getCurrentPositionJ();

        newJ = newJ +1;

        if(newJ>19 || newJ <0){
            return;
        }
        drone.setCurrentPositionJ(newJ);
    }

   synchronized public void updateDistances() {
        updateDistanceHospitalSource();
        updateDistanceHospitalDestiny();
    }

    synchronized public void updateDistanceHospitalDestiny() {
        double distanceHospitalDestiny = calculeteDistanceFrom(drone.getDestinyHopistal());
        /*  System.out.println("distanceHospitalDestiny"+ distanceHospitalDestiny);*/


        drone.setDistanceHospitalDestiny(distanceHospitalDestiny);
    }

    synchronized public void updateDistanceHospitalSource() {
        /*  System.out.println("distanceHospitalSource"+ distanceHospitalSource);*/
        double distanceHospitalSource = calculeteDistanceFrom(drone.getSourceHospital());
        drone.setDistanceHospitalSource(distanceHospitalSource);
    }

    public void takeOff() {
        if(drone.isTookOff()){
            return;
        }

        drone.setTookOff(true);
        applyStyleTakeOff();

        System.out.println("Drone["+drone.getId()+"] "+"Take Off");
        loggerController.print("Drone["+drone.getId()+"] "+"Take Off");
    }

    public void landing() {

        drone.setTookOff(false);

        System.out.println("Drone["+drone.getId()+"] "+"Landing");
        loggerController.print("Drone["+drone.getId()+"] "+"Landing");

        applyStyleLanded();

        System.out.println("Drone["+drone.getId()+"] "+"Landed");
        loggerController.print("Drone["+drone.getId()+"] "+"Landed");

    }

    public void shutDown(){
        drone.setStarted(false);

        removeStyleSelected();

        System.out.println("Drone["+drone.getId()+"] "+"shutdown");
        loggerController.print("Drone["+drone.getId()+"] "+"shutdown");

    }

    public void start(){

        if(drone.isStarted()){
            return;
        }


        drone.setStarted(true);

        System.out.println("Drone["+drone.getId()+"] "+"start");
        loggerController.print("Drone["+drone.getId()+"] "+"start");

    }

    public void safeLanding() {
        drone.setSafeland(true);
        drone.setTookOff(false);

        System.out.println("Drone["+drone.getId()+"] "+"SafeLand");
        loggerController.print("Drone["+drone.getId()+"] "+"SafeLand");

        applyStyleLanded();

        System.out.println("Drone["+drone.getId()+"] "+"Landed");
        loggerController.print("Drone["+drone.getId()+"] "+"Landed");

        shutDown();

        checkAndPrintIfLostDrone();

        }

    synchronized public void updateBattery() {
        double newValueBattery;
        if(drone.isEconomyMode()){
            newValueBattery = drone.getCurrentBattery()-(drone.getBatteryPerBlock()/2);
        }else {
            newValueBattery = drone.getCurrentBattery()-(drone.getBatteryPerBlock());
        }

        drone.setCurrentBattery(newValueBattery);
    }

    public double calculeteDistanceFrom(Hospital hospital) {

       /* System.out.println((drone.getCurrentPositionI()+1)+" "+(drone.getCurrentPositionJ()+1)+" "+ (hospital.getiPosition()+1) +" "+ (hospital.getjPosition()+1));*/
        int xInitial = (drone.getCurrentPositionJ()+1)*30,
                xFinal= (hospital.getjPosition()+1)*30,
                yInitial = (drone.getCurrentPositionI()+1)*30,
                yFinal =(hospital.getiPosition()+1)*30;

        return Math.sqrt(((xFinal-xInitial)*(xFinal-xInitial)) + ((yFinal- yInitial)*(yFinal- yInitial)));





    }

   synchronized public void updadePositionDroneView() {
        currentCell.getChildren().remove(this);

         System.out.println((drone.getCurrentPositionI()+" "+drone.getCurrentPositionJ()));
        Cell newCell = environmentView.getCellFrom(drone.getCurrentPositionI(),drone.getCurrentPositionJ());
        currentCell = newCell;

        if(!currentCell.getChildren().contains(this)){
            currentCell.getChildren().add(this);
        }

        updateDistances();


    }

    public void applyStyleLanded() {

        drone.setTookOff(false);
        setScaleX(1);
        setScaleY(1);



    }

    public void applyStyleTakeOff() {

        drone.setTookOff(true);
        setScaleX(1.4);
        setScaleY(1.4);
    }

     synchronized public Node updateItIsOver() {

        drone.getOnTopOfList().clear();

        for(Node node :currentCell.getChildren()){

            if(node==this){
                continue;
            }

           drone.addOnTopOfDroneList(node);
        }
        if(!drone.getOnTopOfList().isEmpty()){
            //System.out.println(drone.getOnTopOfList().get(drone.getOnTopOfList().size()-1));
        }


        return null;
    }

    public void startBatteryDecrementer() {

        System.out.println("Start startBatteryDecrementer");

        Timer timer = new Timer();
         batteryDecrementertimerTask = new TimerTask() {
            @Override
            public void run() {
                if(drone.isStarted()){

                    double batteryPerSecond = drone.getBatteryPerSecond();
                    double newValueBattery;

                    if(drone.isEconomyMode()){
                        newValueBattery = drone.getCurrentBattery()-(batteryPerSecond/2);
                    }else {
                        newValueBattery = drone.getCurrentBattery()-(batteryPerSecond);
                    }

                    drone.setCurrentBattery(newValueBattery);


                    System.out.println("Drone["+drone.getId()+"] "+drone.getCurrentBattery()+"%");
                    loggerController.print("Drone["+drone.getId()+"] "+"Current Battery "+ drone.getCurrentBattery()+"%");


                    checkStatus();
                }


            }
        };

        timer.scheduleAtFixedRate(batteryDecrementertimerTask,0,1000);


    }

    public void applyEconomyMode() {

        if(drone.isEconomyMode()){
            return;
        }

        drone.setEconomyMode(true);

        System.out.println("Drone["+drone.getId()+"] "+"Start Economy Mode");
        loggerController.print("Drone["+drone.getId()+"] "+"Start Economy Mode");
    }

    public void goDestinyAutomatic() {
        drone.setGoingAutomaticToDestiny(true);

        start();
        stopBatteryDecrementer();
        startBatteryDecrementer();
        takeOff();

        updateItIsOver();

        Timer timer = new Timer();
        goAutomaticDestinyTimerTask = new TimerTask() {
            @Override
            public void run() {

                Platform.runLater(() -> {
                    try {


                        System.out.println("drone.getDistanceHospitalDestiny()"+ drone.getDistanceHospitalDestiny());

                        System.out.println("Drone["+drone.getId()+"] "+"Flying");
                        loggerController.print("Drone["+drone.getId()+"] "+"Flying");


                        int oldI = drone.getCurrentPositionI();
                        int oldJ = drone.getCurrentPositionJ();
                        double newDistanceDestiny = 999999;
                        String mustGO = null;

                        double tempDistance = distanceDroneWentRight(drone.getDestinyHopistal());

                        if(tempDistance < newDistanceDestiny){
                            newDistanceDestiny = tempDistance;
                            mustGO ="->";
                        }

                        drone.setCurrentPositionI(oldI);
                        drone.setCurrentPositionJ(oldJ);

                        tempDistance = distanceDroneWentLeft(drone.getDestinyHopistal());

                        if(tempDistance<newDistanceDestiny){
                            newDistanceDestiny = tempDistance;
                            mustGO ="<-";
                        }

                        drone.setCurrentPositionI(oldI);
                        drone.setCurrentPositionJ(oldJ);

                        tempDistance = distanceDroneWentUp(drone.getDestinyHopistal());

                        if(tempDistance<newDistanceDestiny){
                            newDistanceDestiny = tempDistance;
                            mustGO ="/\\";

                        }

                        drone.setCurrentPositionI(oldI);
                        drone.setCurrentPositionJ(oldJ);

                        tempDistance = distanceDroneWentDown(drone.getDestinyHopistal());

                        if(tempDistance<newDistanceDestiny){
                            newDistanceDestiny = tempDistance;
                            mustGO ="\\/";

                        }

                        drone.setCurrentPositionI(oldI);
                        drone.setCurrentPositionJ(oldJ);



                        goTo(mustGO);

                        updadePositionDroneView();

                        updateBattery();

                        updateItIsOver();

                        checkStatus();


                    }catch (Exception e){
                        System.out.println();
                    }
                });
            }
        };


        timer.scheduleAtFixedRate(goAutomaticDestinyTimerTask,0,1000);


    }

    @Override
    public Object getDrone() {
        return drone;
    }

    @Override
    public Node getNode() {
        return this;
    }

    @Override
    public void notifyRunEnviroment() {
       /* drone.setReturnToHome(false);
        drone.setGoingManualToDestiny(true);

        if(drone.isBadConnection()){
            return;
        }*/

        checkStatus();

      /*  if(drone.isAutomatic()){
            stopGoAutomaticDestiny();
            goDestinyAutomatic();
        }*/
    }

    @Override
    public void notifyBadConnection(){

        if(drone.isTookOff()){
            drone.setBadConnection(true);

        }

    }



    public void applyStyleBadConnection() {
        imageViewBadConnection.setVisible(true);
    }


    @Override
    public void notifyNormalConnection(){

            drone.setBadConnection(false);


    }



    public void applyStyleNormalConnection() {
        imageViewBadConnection.setVisible(false);
    }


    @Override
    public void notifyStopEnviroment() {
        stopBatteryDecrementer();
    }


    public void stopBatteryDecrementer() {
        try {
            System.out.println("stop BatteryDecrementertimerTask");
            batteryDecrementertimerTask.cancel();
        }catch (Exception e){

        }


    }

    public void stopGoAutomaticDestiny() {

        try {
            System.out.println("stop goAutomaticDestinyTimerTask");
            goAutomaticDestinyTimerTask.cancel();
        }catch (Exception e){

        }


    }

    public void stopReturnToHome() {
        try {
            returnToHomeTimerTask.cancel();
        }catch (Exception e){

        }

    }


    public void setStyleSelected() {
        imageViewDrone.setImage(new Image("/view/res/selectedDrone.png"));
    }

    public void removeStyleSelected(){
        imageViewDrone.setImage(new Image("/view/res/notSelectedDrone.png"));
    }

    @Override
    public void notifyStrongWind() {
        drone.setStrongWind(true);
    }

    @Override
    public void notifyNoStrongWind() {
        drone.setStrongWind(false);
    }

    @Override
    public void notifyReset() {
        drone.setCurrentBattery(drone.getInitialBattery());
        drone.setCurrentPositionI(drone.getInitialPosistionI());
        drone.setCurrentPositionJ(drone.getInitialPositionJ());
        drone.setBadConnection(false);
        applyStyleNormalConnection();
        drone.setEconomyMode(false);
        drone.setReturningToHome(false);
        drone.setGoingAutomaticToDestiny(false);
        drone.setGoingManualToDestiny(false);
        drone.setSafeland(false);
        drone.setTookOff(false);
        applyStyleLanded();

        drone.setStarted(false);

        stopBatteryDecrementer();
        stopGoAutomaticDestiny();
        stopReturnToHome();

        updadePositionDroneView();
        updateItIsOver();
        removeStyleSelected();




    }


    public void returnToHome(){


        drone.setReturningToHome(true);
        System.out.println("Drone["+drone.getId()+"] "+"Return to Home");
        loggerController.print("Drone["+drone.getId()+"] "+"Return to Home");

       Timer timer = new Timer();
       returnToHomeTimerTask = new TimerTask() {
           @Override
           public void run() {

               Platform.runLater(() -> {


                   int oldI = drone.getCurrentPositionI();
                   int oldJ = drone.getCurrentPositionJ();
                   double newDistanceSource = 999999;
                   String mustGO = null;

                   double tempDistance = distanceDroneWentRight(drone.getSourceHospital());

                   if(tempDistance < newDistanceSource){
                       newDistanceSource = tempDistance;
                       mustGO ="->";
                   }

                   drone.setCurrentPositionI(oldI);
                   drone.setCurrentPositionJ(oldJ);

                   tempDistance = distanceDroneWentLeft(drone.getSourceHospital());

                   if(tempDistance<newDistanceSource){
                       newDistanceSource = tempDistance;
                       mustGO ="<-";
                   }

                   drone.setCurrentPositionI(oldI);
                   drone.setCurrentPositionJ(oldJ);

                   tempDistance = distanceDroneWentUp(drone.getSourceHospital());

                   if(tempDistance<newDistanceSource){
                       newDistanceSource = tempDistance;
                       mustGO ="/\\";

                   }

                   drone.setCurrentPositionI(oldI);
                   drone.setCurrentPositionJ(oldJ);

                   tempDistance = distanceDroneWentDown(drone.getSourceHospital());

                   if(tempDistance<newDistanceSource){
                       newDistanceSource = tempDistance;
                       mustGO ="\\/";

                   }

                   drone.setCurrentPositionI(oldI);
                   drone.setCurrentPositionJ(oldJ);


                   goTo(mustGO);

                   updadePositionDroneView();
                   updateBattery();
                   checkStatus();



               });

           }
       };


        timer.scheduleAtFixedRate(returnToHomeTimerTask,0,1000);


    }

    public void goTo(String mustGO) {

        //irregular moviments
        if(drone.isEconomyMode()){
            Random random = new Random();
            double value = random.nextDouble();

            // right moviments
            if(value>0.8){
                switch (mustGO){
                    case "->":
                        flyingRight();
                        break;

                    case "<-":
                        flyingLeft();
                        break;

                    case "/\\":
                        flyingUp();
                        break;

                    case "\\/":
                        flyingDown();
                        break;
                }
            }else {
            //wrong moviments

            int randomNum = 0 + (int) (Math.random() * 4);

                switch (randomNum){
                    case 0:
                        flyingRight();
                        break;

                    case 1:
                        flyingLeft();
                        break;

                    case 2:
                        flyingUp();
                        break;

                    case 3:
                        flyingDown();
                        break;
                }

        }

       // normal moviments
    }else {
            switch (mustGO){
                case "->":
                    flyingRight();
                    break;

                case "<-":
                    flyingLeft();
                    break;

                case "/\\":
                    flyingUp();
                    break;

                case "\\/":
                    flyingDown();
                    break;
            }
    }
    }

    public double distanceDroneWentUp(Hospital hospital) {
        drone.setCurrentPositionI(drone.getCurrentPositionI()-1);

        if(drone.getCurrentPositionI()<0){
            return 999999;
        }

        return calculeteDistanceFrom(hospital);
    }

    public double distanceDroneWentDown(Hospital hospital) {
        drone.setCurrentPositionI(drone.getCurrentPositionI()+1);

        if(drone.getCurrentPositionI()<0){
            return 999999;
        }

        return calculeteDistanceFrom(hospital);
    }

    public double distanceDroneWentLeft(Hospital hospital) {
        drone.setCurrentPositionJ(drone.getCurrentPositionJ()-1);

        if(drone.getCurrentPositionJ()<0){
            return 999999;
        }

        return calculeteDistanceFrom(hospital);
    }

    public double distanceDroneWentRight(Hospital hospital) {

        if(drone.getCurrentPositionJ()<0){
            return 999999;
        }

        drone.setCurrentPositionJ(drone.getCurrentPositionJ()+1);

        return calculeteDistanceFrom(hospital);
    }
}
