package view.river;

import javafx.scene.Group;
import javafx.scene.Node;
import view.SelectableView;

public abstract class RiverView  extends Group implements SelectableView {
    Object getRiver() {
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
