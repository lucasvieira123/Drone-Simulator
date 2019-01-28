package view.drone;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import view.SelectableView;

public abstract class DroneView extends Group implements SelectableView {


    @Override
    public Node getNode() {
        return null;
    }

    @Override
    public void removeStyleSelected() {

    }

    @Override
    public void applyStyleSelected() {

    }

    public Object getDrone() {
        return null;
    }

    public void notifyRunEnviroment() {

    }

    public void notifyBadConnection() {

    }

    public void notifyNormalConnection() {

    }

    void notifyStopEnviroment() {

    }

    public void notifyStrongWind() {

    }

    public void notifyNoStrongWind() {

    }

    public void notifyReset() {

    }

    public void eventKey(KeyCode code) {

    }
}
