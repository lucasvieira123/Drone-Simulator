package view.hospital;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import model.Hospital;
import view.CellView;

public class HospitalImpl extends HospitalView {
    public static  int COUNT_HOSPITAL = 0;
    public static double width = 64;
    public static double height = 64;
    private final Hospital hospital;
    double positionI = 0;
    double positionJ =0;
    private Rectangle selectedRetangle;


    public HospitalImpl(CellView cellViewSelected) {

        COUNT_HOSPITAL++;

         hospital = new Hospital(COUNT_HOSPITAL, cellViewSelected.getI(), cellViewSelected.getJ());

        Label label = new Label();
        label.setText(String.valueOf(COUNT_HOSPITAL));
        label.setTextFill(Color.RED);
        label.setTextAlignment(TextAlignment.CENTER);

        ImageView imageView = new ImageView();
        Image image = new Image("/view/res/hospital.png");
        imageView.setImage(image);

        this.getChildren().addAll(imageView, label);

        cellViewSelected.getChildren().add(this);
    }



    @Override
    public Object getHospital() {
        return hospital;
    }

    @Override
    public Node getNode() {
        return this;
    }

    @Override
    public void removeStyleSelected() {
        if(selectedRetangle!= null){
            this.getChildren().remove(selectedRetangle);

            selectedRetangle = null;
        }
    }

    @Override
    public void applyStyleSelected() {
        if(selectedRetangle == null){
            selectedRetangle = new Rectangle(30,30);
            selectedRetangle.setFill(Color.TRANSPARENT);
            selectedRetangle.setStrokeWidth(3);
            selectedRetangle.setStroke(Color.BLUE);
            this.getChildren().add(selectedRetangle);

        }
    }
}


