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

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Galo Xavier Figueroa Villacreses
 */
public class Schedule {
    
    private final int DAYS_OF_WEEK = 7;
    private final int scheduleSize;
    private final String[] DAYS = {"LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES", "SÁBADO", "DOMINGO"};
    private final ArrayList<String> periodsList;
    private GridPane schedule;
    private int[][] mirrorSchedule;
    
    public Schedule(ArrayList<String> periodsList){
        this.periodsList = periodsList;
        scheduleSize = periodsList.size();
        createSchedule(scheduleSize);
        createPsuedoSchedule(scheduleSize);
    }
    
    private void createPsuedoSchedule(int scheduleSize){
        
        this.mirrorSchedule = new int[scheduleSize][DAYS_OF_WEEK];
        
    }
    
    private void createSchedule(int scheduleSize){
        
        this.schedule = new GridPane();
        this.mirrorSchedule = new int[scheduleSize + 1][DAYS_OF_WEEK + 1];
        
        for(int row = 0; row<=scheduleSize;row++){
            
            for(int column = 0; column<=DAYS_OF_WEEK;column++){
                
                if(column > 0 && row == 0){
                    
                    Label day = new Label(DAYS[column - 1]);
                    HBox dayContainer = new HBox();
                    dayContainer.getChildren().add(day);
                    dayContainer.setAlignment(Pos.CENTER);
                    dayContainer.setBackground(new Background(new BackgroundFill(Color.HONEYDEW, CornerRadii.EMPTY, Insets.EMPTY)));
                    schedule.add(dayContainer, column, row);
                
                } else if(row > 0 && column == 0){
                    
                    Label hour = new Label( (String) periodsList.get(row - 1));
                    HBox hourContainer = new HBox();
                    hourContainer.setMinWidth(100);
                    hourContainer.getChildren().add(hour);
                    hourContainer.setAlignment(Pos.CENTER);
                    hourContainer.setBackground(new Background(new BackgroundFill(Color.HONEYDEW, CornerRadii.EMPTY, Insets.EMPTY)));
                    schedule.add(hourContainer, column, row);
                
                } else if (row != 0 && column != 0){
                
                    Rectangle cell = new Rectangle(100,400/scheduleSize);
                    cell.setFill(Color.WHITE);
                    cell.setStroke(Color.HONEYDEW);
                    cell.setStrokeWidth(0.20);

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
            if(cell.getFill().equals(Color.WHITE)){
                
                cell.setFill(Color.LIGHTBLUE);
                mirrorSchedule[row][column] = 1;
                
            } else {
                
                cell.setFill(Color.WHITE);
                mirrorSchedule[row][column] = 0;
            }
        }
    }
    
    public GridPane getSchedule(){
        return this.schedule;
    }
    
}
