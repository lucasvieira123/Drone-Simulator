package view.antenna;

import javafx.scene.Group;
import javafx.scene.Node;
import view.SelectableView;

public abstract class AntenaView extends Group implements SelectableView {
    Object getAntenna() {
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
