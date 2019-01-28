package view.hospital;

import javafx.scene.Group;
import javafx.scene.Node;
import view.SelectableView;

public abstract class HospitalView  extends Group implements SelectableView {
    public Object getHospital() {
        return null;
    }

    public Node getNode() {
        return null;
    }

    @Override
    public void removeStyleSelected() {

    }

    @Override
    public void applyStyleSelected() {

    }
}
