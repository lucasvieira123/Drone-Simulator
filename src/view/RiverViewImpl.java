package view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.River;

;


public class RiverViewImpl extends Group implements RiverView {



    private final River river;

    public RiverViewImpl(Cell cell) {

        river = new River(cell.getI(),cell.getJ());
        Rectangle rectangle = new Rectangle( 30, 30);
        rectangle.setStroke(Color.BLUE);
        rectangle.setStrokeWidth(1);
        rectangle.setFill(Color.BLUE);
        cell.getChildren().add(rectangle);


    }

    @Override
    public Object getRiver() {
        return river;
    }

    @Override
    public Node getNode() {
        return this;
    }
}
