package view.river;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.River;
import view.CellView;

;


public class RiverViewImpl extends RiverView {



    private final River river;

    public RiverViewImpl(CellView cellView) {

        river = new River(cellView.getI(), cellView.getJ());
        Rectangle rectangle = new Rectangle( 30, 30);
        rectangle.setStroke(Color.BLUE);
        rectangle.setStrokeWidth(1);
        rectangle.setFill(Color.BLUE);

        this.getChildren().add(rectangle);

        cellView.getChildren().add(this);


    }

    @Override
    public void removeStyleSelected() {
        ((Rectangle)this.getChildren().get(0)).setStroke(Color.BLUE);
        ((Rectangle)this.getChildren().get(0)).setStrokeWidth(1);
    }

    @Override
    public void applyStyleSelected() {
        /*String style = "-fx-stroke: " + "black" + ";);";
        style+= "-fx-stroke-width: "+"50"+";";*/

        ((Rectangle)this.getChildren().get(0)).setStroke(Color.BLACK);
        ((Rectangle)this.getChildren().get(0)).setStrokeWidth(3.0);
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
