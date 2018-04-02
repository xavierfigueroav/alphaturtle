/*
 * Copyright (C) 2018 Galo Xavier Figueroa Villacreses
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package alphaturtle;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Galo Xavier Figueroa Villacreses
 */
public class InputSchedule extends Schedule{
    
    private String ownerName;
    private boolean[][] mirrorSchedule;
    private GridPane schedule;
    
    public InputSchedule() {
        mirrorSchedule = new boolean[periodsList.size()][WEEKDAYS.length];
        createSchedule();
    }
    
    private void createSchedule(){
        
        this.schedule = new GridPane();
        
        for(int row = 0; row <= periodsList.size();row++){
            
            for(int column = 0; column <= WEEKDAYS.length;column++){
                
                if(column > 0 && row == 0){
                    
                    Label day = new Label(WEEKDAYS[column - 1]);
                    HBox dayContainer = new HBox();
                    dayContainer.getChildren().add(day);
                    dayContainer.setAlignment(Pos.CENTER);
                    dayContainer.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                    schedule.add(dayContainer, column, row);
                
                } else if(row > 0 && column == 0){
                    
                    Label hour = new Label( (String) periodsList.get(row - 1));
                    HBox hourContainer = new HBox();
                    hourContainer.setMinWidth(100);
                    hourContainer.getChildren().add(hour);
                    hourContainer.setAlignment(Pos.CENTER);
                    hourContainer.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                    schedule.add(hourContainer, column, row);
                
                } else if (row != 0 && column != 0){
                
                    Rectangle cell = new Rectangle(100,400/periodsList.size());
                    cell.setFill(Color.WHITESMOKE);
                    cell.setStroke(Color.WHITE);

                    cell.setOnMouseClicked(new ClickOnCellHandler(cell, row, column));

                    schedule.add(cell, column, row);
                }
            }
        }
        
    }

    private class ClickOnCellHandler implements EventHandler<MouseEvent> {

        private final Rectangle cell;
        private final int row;
        private final int column;

        public ClickOnCellHandler(Rectangle cell, int row, int column) {
            this.cell = cell;
            this.row = row - 1;
            this.column = column - 1;
        }

        @Override
        public void handle(MouseEvent e) {
            if(cell.getFill().equals(Color.WHITESMOKE)){
                
                mirrorSchedule[row][column] = true;
                cell.setFill(Color.AQUAMARINE);
                
            } else {
                
                mirrorSchedule[row][column] = false;
                cell.setFill(Color.WHITESMOKE);
            }
        }
    }
    
    public GridPane getAsNode(){
        return this.schedule;
    }
    
    public boolean[][] getMirrorSchedule(){
        return this.mirrorSchedule;
    }
    
    public String getOwnerName(){
        return this.ownerName;
    }
    
    public void setOwnerName(String ownerName){
        this.ownerName = ownerName;
    }
    
}
