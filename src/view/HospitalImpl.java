package view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import model.Hospital;

public class HospitalImpl extends Group implements HospitalView {
    public static  int COUNT_HOSPITAL = 0;
    public static double width = 64;
    public static double height = 64;
    private final Hospital hospital;
    double positionI = 0;
    double positionJ =0;

    public HospitalImpl(Cell cellSelected) {

        COUNT_HOSPITAL++;

         hospital = new Hospital(COUNT_HOSPITAL, cellSelected.getI(), cellSelected.getJ());

        Label label = new Label();
        label.setText(String.valueOf(COUNT_HOSPITAL));
        label.setTextFill(Color.RED);
        label.setTextAlignment(TextAlignment.CENTER);

        ImageView imageView = new ImageView();
        Image image = new Image("/view/res/hospital.png");
        imageView.setImage(image);
        this.getChildren().addAll(imageView, label);

        cellSelected.getChildren().add(this);
    }


    @Override
    public Object getHospital() {
        return hospital;
    }

    @Override
    public Node getNode() {
        return this;
    }
}
