package view;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import model.Antenna;
import view.res.EnvironmentView;

import java.util.*;

public class AntenaViewImpl extends Group implements AntenaView {


    public static int COUNT_ANTENNA = 0;
    private final Antenna antena;
    private final ImageView imageView2;
    private ImageView imageView;
    private List<Cell> cellList = new ArrayList<>();

    public AntenaViewImpl(Cell cellSelected) {
        EnvironmentView environmentView = cellSelected.getEnvironmentView();

        getBadConnectionArea(cellSelected);


        COUNT_ANTENNA++;

        antena = new Antenna(COUNT_ANTENNA, cellSelected.getI(), cellSelected.getJ());

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

        cellSelected.getChildren().add(this);

        addbadConnectionInSpecificArea();
    }

    private void getBadConnectionArea( Cell cellSelected) {
        EnvironmentView environmentView = cellSelected.getEnvironmentView();

        int i = cellSelected.getI();
        int j = cellSelected.getJ();

        cellList.add(environmentView.getCellFrom(i-1,j-1));
        cellList.add(environmentView.getCellFrom(i-1,j));
        cellList.add(environmentView.getCellFrom(i-1,j+1));
        cellList.add(environmentView.getCellFrom(i,j-1));
        cellList.add(environmentView.getCellFrom(i,j));
        cellList.add(environmentView.getCellFrom(i,j+1));
        cellList.add(environmentView.getCellFrom(i+1,j-1));
        cellList.add(environmentView.getCellFrom(i+1,j));
        cellList.add(environmentView.getCellFrom(i+1,j+1));
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
                        for(Cell cell : cellList){
                            cell.setBadConnection(true);
                        }

                        imageView2.setVisible(true);
                    }else {
                        for(Cell cell : cellList){
                            cell.setBadConnection(false);
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


}