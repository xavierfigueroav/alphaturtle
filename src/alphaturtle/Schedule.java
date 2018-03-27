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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Galo Xavier Figueroa Villacreses
 */
public class Schedule {
    
    private final int DAYS_OF_WEEK = 7;
    private GridPane schedule;
    private int[][] mirrorSchedule;
    
    public Schedule(int scheduleSize){
        
        createSchedule(scheduleSize);
        createPsuedoSchedule(scheduleSize);
    }
    
    private void createPsuedoSchedule(int scheduleSize){
        
        this.mirrorSchedule = new int[scheduleSize][DAYS_OF_WEEK];
        
    }
    
    private void createSchedule(int scheduleSize){
        
        this.schedule = new GridPane();
        this.schedule.setGridLinesVisible(true);
        this.mirrorSchedule = new int[scheduleSize][DAYS_OF_WEEK];
        
        for(int row = 0; row<scheduleSize;row++){
            for(int column = 0; column<DAYS_OF_WEEK;column++){
                
                Rectangle activity = new Rectangle(100,40);
                activity.setFill(Paint.valueOf("White"));
                activity.setStroke(Paint.valueOf("Grey"));
                
                activity.setOnMouseClicked(new ClickHandler(activity, row, column));
                
                schedule.add(activity, column, row);
            }
        }
        
    }

    private class ClickHandler implements EventHandler<MouseEvent> {

        private final Rectangle activity;
        private final int row;
        private final int column;

        public ClickHandler(Rectangle activity, int row, int column) {
            this.activity = activity;
            this.row = row;
            this.column = column;
        }

        @Override
        public void handle(MouseEvent e) {
            if(activity.getFill().equals(Paint.valueOf("White"))){
                
                activity.setFill(Paint.valueOf("Green"));
                mirrorSchedule[row][column] = 1;
                
            } else {
                
                activity.setFill(Paint.valueOf("White"));
                mirrorSchedule[row][column] = 0;
            }
        }
    }
    
    public GridPane getSchedule(){
        return this.schedule;
    }
    
}
