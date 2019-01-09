package controller;


import javafx.application.Platform;
import model.Drone;
import view.DroneViewImpl;

import java.util.Timer;
import java.util.TimerTask;

public aspect Aspect {
    pointcut safeLanding() : call (void view.DroneViewImpl.safeLanding());


    void around() : safeLanding() && if(
            ((Drone)((DroneViewImpl) thisJoinPoint.getTarget()).getDrone()).getCurrentBattery()>5
            &&
            ((Drone)((DroneViewImpl) thisJoinPoint.getTarget()).getDrone()).getDistanceHospitalDestiny() <=60
            &&
            ((Drone)((DroneViewImpl) thisJoinPoint.getTarget()).getDrone()).isStrongWind()

            ){

        System.out.println(" inicio Aspect around");
        DroneViewImpl droneView = (DroneViewImpl) thisJoinPoint.getTarget();
        Drone drone = (Drone) droneView.getDrone();

        System.out.println("drone.isOnWater() "+drone.isOnWater());
        System.out.println("getDistanceHospitalDestiny() "+drone.getDistanceHospitalDestiny());
        System.out.println("drone.isStrongWind() "+drone.isStrongWind());
        System.out.println("drone.isAutomatic() "+drone.isAutomatic());


        //  if(drone.isOnWater() && drone.getDistanceHospitalDestiny()<=60 && drone.isStrongWind()){
        keepFlying(droneView);

        // }

        System.out.println("fim Aspect");
    }


   void around() : safeLanding() && if(
            ((Drone)((DroneViewImpl) thisJoinPoint.getTarget()).getDrone()).isOnWater()){

        System.out.println(" inicio Aspect around");
        DroneViewImpl droneView = (DroneViewImpl) thisJoinPoint.getTarget();
        Drone drone = (Drone) droneView.getDrone();


        System.out.println("drone.isOnWater() "+drone.isOnWater());
        System.out.println("getDistanceHospitalDestiny() "+drone.getDistanceHospitalDestiny());
        System.out.println("drone.isStrongWind() "+drone.isStrongWind());
        System.out.println("drone.isAutomatic() "+drone.isAutomatic());

      //  if(drone.isOnWater() && drone.getDistanceHospitalDestiny()>=60) {
            moveASide(droneView);
      //  }

        System.out.println("fim Aspect");

    }






    private void moveASide(DroneViewImpl droneView) {



        Drone drone = (Drone) droneView.getDrone();

        if(drone.isGoingAutomaticToDestiny()  || drone.isAutomatic()){
            droneView.stopGoAutomaticDestiny();
        }

        if(drone.isReturningToHome() || drone.isBadConnection()){
            droneView.stopReturnToHome();
        }


        System.out.println("Drone[" + drone.getId() + "] " + "Move aside");
        droneView.loggerController.print("Drone[" + drone.getId() + "] " + "Move aside");

        Timer timer = new Timer();



        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                Platform.runLater(() -> {
                    System.out.println("");
                    drone.setCurrentPositionI(drone.getCurrentPositionI() + 1);

                    droneView.updadePositionDroneView();
                    droneView.updateItIsOver();



                    if (!drone.isOnWater()) {
                        safeLandingAspect(droneView);

                       /* synchronized (mainThread){
                            mainThread.notify();
                        }*/

                        cancel();
                        return;
                    }
                });
            }
        };

        timer.scheduleAtFixedRate(timerTask, 0, 1000);

      /*  try {
            synchronized (mainThread){
                mainThread.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/


    }

    private void safeLandingAspect(DroneViewImpl droneView) {
        Drone drone = (Drone) droneView.getDrone();

        drone.setTookOff(false);

        System.out.println("Drone["+drone.getId()+"] "+"SafeLand");
        droneView.loggerController.print("Drone["+drone.getId()+"] "+"SafeLand");

        droneView.applyStyleLanded();

        System.out.println("Drone["+drone.getId()+"] "+"LandedASpect");
        droneView.loggerController.print("Drone["+drone.getId()+"] "+"Landed");

        droneView.shutDown();

        droneView.checkAndPrintIfLostDrone();
    }


    private void keepFlying(DroneViewImpl droneView) {

       /* Thread mainThread = Thread.currentThread();*/

         Drone drone = (Drone) droneView.getDrone();

         if(drone.isGoingAutomaticToDestiny()){
             droneView.stopGoAutomaticDestiny();
         }


        System.out.println("Drone[" + drone.getId() + "] " + "keep Flying");
        droneView.loggerController.print("Drone[" + drone.getId() + "] " + "keep Flying");


        Timer timer = new Timer();
        TimerTask goAutomaticDestinyTimerTask = new TimerTask() {
            @Override
            public void run() {

                if (!drone.isAutomatic()) {
                    cancel();
                    return;
                }

                if (drone.isBadConnection()) {
                    droneView.stopReturnToHome();

                }

                if (drone.getDistanceHospitalDestiny() <= 0) {
                    droneView.applyStyleLanded();
                    droneView.shutDown();
                    System.out.println("Drone["+drone.getId()+"] "+"Arrived at destination");
                    droneView.loggerController.print("Drone["+drone.getId()+"] "+"Arrived at destination");
                    cancel();
                    return;

                }

                Platform.runLater(() -> {
                    try {


                        int oldI = drone.getCurrentPositionI();
                        int oldJ = drone.getCurrentPositionJ();
                        double newDistanceDestiny = 999999;
                        String mustGO = null;

                        double tempDistance = droneView.distanceDroneWentRight(drone.getDestinyHopistal());

                        if (tempDistance < newDistanceDestiny) {
                            newDistanceDestiny = tempDistance;
                            mustGO = "->";
                        }

                        drone.setCurrentPositionI(oldI);
                        drone.setCurrentPositionJ(oldJ);

                        tempDistance = droneView.distanceDroneWentLeft(drone.getDestinyHopistal());

                        if (tempDistance < newDistanceDestiny) {
                            newDistanceDestiny = tempDistance;
                            mustGO = "<-";
                        }

                        drone.setCurrentPositionI(oldI);
                        drone.setCurrentPositionJ(oldJ);

                        tempDistance = droneView.distanceDroneWentUp(drone.getDestinyHopistal());

                        if (tempDistance < newDistanceDestiny) {
                            newDistanceDestiny = tempDistance;
                            mustGO = "/\\";

                        }

                        drone.setCurrentPositionI(oldI);
                        drone.setCurrentPositionJ(oldJ);

                        tempDistance = droneView.distanceDroneWentDown(drone.getDestinyHopistal());

                        if (tempDistance < newDistanceDestiny) {
                            newDistanceDestiny = tempDistance;
                            mustGO = "\\/";

                        }

                        drone.setCurrentPositionI(oldI);
                        drone.setCurrentPositionJ(oldJ);


                        if (drone.getDistanceHospitalDestiny() != 0) {
                            droneView.goTo(mustGO);
                            droneView.updadePositionDroneView();

                            droneView.updateBattery();

                            droneView.updateItIsOver();


                        }

                        if (drone.getDistanceHospitalDestiny() <= 0) {
                            droneView.applyStyleLanded();
                            droneView.shutDown();

                            System.out.println("Drone["+drone.getId()+"] "+"Arrived at destination");
                            droneView.loggerController.print("Drone["+drone.getId()+"] "+"Arrived at destination");

                            cancel();
                            return;
                        }


                    } catch (Exception e) {
                        System.out.println();
                    }
                });
            }
        };

        timer.scheduleAtFixedRate(goAutomaticDestinyTimerTask,0,1000);

      /*  try {
            synchronized (mainThread){
                mainThread.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }



    pointcut applyEconimicMode() : call (void view.DroneViewImpl.applyEconomyMode());
    void around() : applyEconimicMode()
            {


        System.out.println(" Inicio Aspect");
        DroneViewImpl droneView = (DroneViewImpl) thisJoinPoint.getTarget();
        Drone drone = (Drone) droneView.getDrone();
        System.out.println("drone.isOnWater() "+drone.isOnWater());
        System.out.println("getDistanceHospitalDestiny() "+drone.getDistanceHospitalDestiny());
        System.out.println("drone.isStrongWind() "+drone.isStrongWind());
        System.out.println("drone.isAutomatic() "+drone.isAutomatic());

        normalMode(droneView);

        System.out.println(" Fim Aspect");
    }

    private void normalMode(DroneViewImpl droneView) {
        Drone drone = (Drone) droneView.getDrone();

        System.out.println("Drone["+drone.getId()+"] "+"Continue Normal Mode");
        droneView.loggerController.print("Drone["+drone.getId()+"] "+"Continue Normal Mode");
        drone.setEconomyMode(false);

    }




    pointcut returnToHome() : call (void view.DroneViewImpl.returnToHome());
    void around() : returnToHome() &&
    if(
            (
            ((Drone)((DroneViewImpl) thisJoinPoint.getTarget()).getDrone()).getDistanceHospitalDestiny()
            <
            ((Drone)((DroneViewImpl) thisJoinPoint.getTarget()).getDrone()).getDistanceHospitalSource()
            )
            &&
            ((Drone)((DroneViewImpl) thisJoinPoint.getTarget()).getDrone()).getCurrentBattery()
            >
            10

     ) {

        DroneViewImpl droneView = (DroneViewImpl) thisJoinPoint.getTarget();

        glide(droneView);


    }

    private void glide(DroneViewImpl droneView) {

        Drone drone = (Drone) droneView.getDrone();
        System.out.println("Drone["+drone.getId()+"] "+" Glide");
        droneView.loggerController.print("Drone["+drone.getId()+"] "+"Glide");

        if(drone.isGoingAutomaticToDestiny()){
            droneView.stopGoAutomaticDestiny();
            drone.setGoingAutomaticToDestiny(false);
        }
    }
}
