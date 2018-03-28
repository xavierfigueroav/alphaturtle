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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Galo Xavier Figueroa Villacreses
 */
public class ScheduleScreen extends HBox {
    
    private Schedule schedule;
    private TextField nameField;
    private Button addButton, nextButton;
    private VBox mainContainer;
    private HBox buttonsContainer;
    
    public ScheduleScreen(ArrayList<String> periodsList){
        
        createContents(periodsList);
        
        buttonsContainer.getChildren().addAll(addButton, nextButton);
        
        mainContainer.getChildren().addAll(nameField, this.schedule.getSchedule(), buttonsContainer);
        
        
        setListeners();
        setStyles();
        this.getChildren().add(mainContainer);
        
        
    }
    
    private void createContents(ArrayList<String> periodsList){
        
        schedule = new Schedule(periodsList);
        nameField = new TextField();
        addButton = new Button("Add schedule");
        nextButton = new Button("Finish");
        mainContainer = new VBox();
        buttonsContainer = new HBox();
        
    }
    
    private void setListeners(){
        
    }
    
    private void setStyles(){
        this.setAlignment(Pos.CENTER);
        
        nameField.setMinSize(300,30);
        nameField.setMaxSize(300,30);
        nameField.setAlignment(Pos.CENTER);
        nameField.setFocusTraversable(false);
        
        
        
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.setSpacing(20);
        
        
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setSpacing(20);
        
        
    }
    
}
