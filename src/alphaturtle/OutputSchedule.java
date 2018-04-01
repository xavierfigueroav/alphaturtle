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

import static alphaturtle.Schedule.WEEKDAYS;
import static alphaturtle.Schedule.periodsList;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Galo Xavier Figueroa Villacreses
 */
public class OutputSchedule {
    
    private final ArrayList<String>[][] mirrorSchedule;
    private final ArrayList<String> namesList;
    private GridPane schedule;
    
    public OutputSchedule(ArrayList<String>[][] mirrorSchedule, ArrayList<String> namesList) {
        
        this.mirrorSchedule =  mirrorSchedule;
        this.namesList = namesList;
        
        createSchedule();
        
    }
    
    private void createSchedule(){
        
        schedule = new GridPane();
        
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
                    
                    HBox cell = new HBox();
                    
                    //Label cell = new Label();
                    cell.setMinSize(100, 400/periodsList.size());
                    cell.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                    
                    ArrayList<String> namesInCell = mirrorSchedule[row - 1][column - 1];
                    
                    if(namesInCell == null){
                
                        cell.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY)));
                    
                        schedule.add(cell, column, row);
                        
                    } else if(namesInCell.size() == namesList.size()){
                        
                        cell.setBackground(new Background(new BackgroundFill(Color.DEEPPINK, CornerRadii.EMPTY, Insets.EMPTY)));
                    
                        schedule.add(cell, column, row);
                        
                    } else {
                        
                        Text numberOfNames = new Text();
                        numberOfNames.setText(Integer.toString(namesList.size() - namesInCell.size()));
                        numberOfNames.setFont(Font.font(null, FontWeight.BOLD, 14));
                        numberOfNames.setFill(Color.GRAY);
                        
                        cell.setAlignment(Pos.CENTER);
                       
                        cell.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
                        
                        cell.getChildren().add(numberOfNames);
                        
                        Tooltip.install(cell, new Tooltip(getTooltipText(namesInCell)));
                    
                        schedule.add(cell, column, row);
                        
                    }
                }
            }
        }
        
    }
    
    private String getTooltipText(ArrayList<String> namesInCell){
        
        ArrayList<String> names = (ArrayList<String>)namesList.clone();
        names.removeAll(namesInCell);
        
        String tooltipText = "";
        
        for(String name : names) tooltipText += name.toUpperCase() + "\n";
        
        return tooltipText;
    }
    
    public GridPane getAsNode(){
        return this.schedule;
    }
    
}
