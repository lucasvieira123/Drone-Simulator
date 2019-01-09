package view;

import javafx.scene.Node;

public interface DroneView {
    Object getDrone();
    Node getNode();

    void notifyRunEnviroment();

    void notifyBadConnection();

    void notifyNormalConnection();

    void notifyStopEnviroment();

    void removeStyleSelected();

    void notifyStrongWind();

    void notifyNoStrongWind();

    void notifyReset();
}
