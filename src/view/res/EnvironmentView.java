package view.res;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import view.Cell;

import java.util.ArrayList;
import java.util.List;

public class EnvironmentView {

    private final GridPane gridpane;
    private final AnchorPane environmentAnchorPane;
    private List<Cell> cells = new ArrayList<>();
    private Cell cellSelected;
    private KeyCode keySelected;
    private ImageView windImagemView = new ImageView(new Image("/view/res/wind.png"));

    public EnvironmentView(int countRow, int countColunm, AnchorPane environmentAnchorPane) {

         gridpane = new GridPane();
       // gridpane.setMinSize(808, 234);

        for(int i = 0; i < countColunm; i++) {
            ColumnConstraints column = new ColumnConstraints(30);
            gridpane.getColumnConstraints().add(column);
        }

        for (int i = 0; i < countRow; i++) {
            RowConstraints row = new RowConstraints(30);
            gridpane.getRowConstraints().add(row);
        }

        for (int i = 0 ; i < countColunm ; i++) {
            for (int j = 0; j < countRow; j++) {
                addPane(i, j);
            }
        }


        gridpane.setGridLinesVisible(true);
        this.environmentAnchorPane = environmentAnchorPane;
        environmentAnchorPane.getChildren().addAll(gridpane);

    }

    private void addPane(int colIndex, int rowIndex) {

        Cell cell = new Cell(rowIndex, colIndex, this);


        cell.setOnMouseClicked(e -> {
           /* System.out.printf("Mouse enetered cell [%d, %d]%n", rowIndex, colIndex);*/
            cellSelected = cell;
        });

         cell.setOnKeyPressed(event -> {
            keySelected = event.getCode();
         });


        gridpane.add(cell, colIndex, rowIndex);
        cells.add(cell);
    }

    public List<Cell> getCells() {
        return cells;
    }

    public Pane getCellSelected() {
        return cellSelected;
    }

    public GridPane getGridpane() {
        return gridpane;
    }

    public KeyCode getKeySelected() {
        return keySelected;
    }

    public Cell getCellFrom(int i, int j){
        for(Cell cell : cells){
            if(cell.getI() == i && cell.getJ() == j){
                return cell;
            }
        }
        return null;
    }

    public void addStrongWind() {
        if(!environmentAnchorPane.getChildren().contains(windImagemView)){
            environmentAnchorPane.getChildren().add(windImagemView);
        }

    }

    public void removeStrongWind() {
        if(environmentAnchorPane.getChildren().contains(windImagemView)){
            environmentAnchorPane.getChildren().remove(windImagemView);
        }
    }
}
