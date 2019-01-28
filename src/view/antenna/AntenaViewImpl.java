package view.antenna;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import model.Antenna;
import view.CellView;
import view.res.EnvironmentView;

import java.util.*;

public class AntenaViewImpl extends AntenaView {


    public static int COUNT_ANTENNA = 0;
    private final Antenna antena;
    private final ImageView imageView2;
    private final CellView cellViewSelected;
    private ImageView imageView;
    private List<CellView> cellViewList = new ArrayList<>();
    private Rectangle selectedRetangle;


    public AntenaViewImpl(CellView cellViewSelected) {
        this.cellViewSelected =  cellViewSelected;
        EnvironmentView environmentView = cellViewSelected.getEnvironmentView();

        getBadConnectionArea(cellViewSelected);


        COUNT_ANTENNA++;

        antena = new Antenna(COUNT_ANTENNA, cellViewSelected.getI(), cellViewSelected.getJ());

        Label label = new Label();
        label.setText(String.valueOf(COUNT_ANTENNA));
        label.setTextFill(Color.RED);
        label.setTextAlignment(TextAlignment.CENTER);

        ImageView imageView1 = new ImageView();
        Image image1 = new Image("/view/res/antenna.png");
        imageView1.setImage(image1);
        this.getChildren().addAll(imageView1, label);

        imageView2 = new ImageView();
        Image image2 = new Image("/view/res/signal.png");
        imageView2.setImage(image2);

        this.getChildren().addAll(imageView2);
        imageView2.setVisible(false);

        cellViewSelected.getChildren().add(this);

        addbadConnectionInSpecificArea();
    }

    private void getBadConnectionArea( CellView cellViewSelected) {
        EnvironmentView environmentView = cellViewSelected.getEnvironmentView();

        int i = cellViewSelected.getI();
        int j = cellViewSelected.getJ();

        cellViewList.add(environmentView.getCellFrom(i-1,j-1));
        cellViewList.add(environmentView.getCellFrom(i-1,j));
        cellViewList.add(environmentView.getCellFrom(i-1,j+1));
        cellViewList.add(environmentView.getCellFrom(i,j-1));
        cellViewList.add(environmentView.getCellFrom(i,j));
        cellViewList.add(environmentView.getCellFrom(i,j+1));
        cellViewList.add(environmentView.getCellFrom(i+1,j-1));
        cellViewList.add(environmentView.getCellFrom(i+1,j));
        cellViewList.add(environmentView.getCellFrom(i+1,j+1));
    }


    private void addbadConnectionInSpecificArea() {


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Random random = new Random();
                double randomDouble = random.nextDouble();



                Platform.runLater(() -> {

                    if(randomDouble>0.6){
                        for(CellView cellView : cellViewList){
                            cellView.setBadConnection(true);
                        }

                        imageView2.setVisible(true);
                    }else {
                        for(CellView cellView : cellViewList){
                            cellView.setBadConnection(false);
                        }

                        imageView2.setVisible(false);
                    }




                });






            }
        }, 0, 2000);
    }



    @Override
    public Object getAntenna() {
        return antena;
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