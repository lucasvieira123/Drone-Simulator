package view;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;

public interface DroneView extends SelectableView {


    @Override
    Node getNode();

    @Override
    void removeStyleSelected();

    @Override
    void applyStyleSelected();

    Object getDrone();

    void notifyRunEnviroment();

    void notifyBadConnection();

    void notifyNormalConnection();

    void notifyStopEnviroment();

    void notifyStrongWind();

    void notifyNoStrongWind();

    void notifyReset();

    void eventKey(KeyCode code);
}
