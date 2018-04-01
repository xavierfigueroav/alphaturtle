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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Galo Xavier Figueroa Villacreses
 */
public class OutputScreen extends HBox{
    
    private final ArrayList<InputSchedule> schedules;
    private final OutputSchedule schedule;
    private ArrayList<String>[][] namesArray;
    private ArrayList<String> namesList;
    
    private VBox mainContainer;
    private HBox labelsContainer, buttonsContainer;
    private Button startButton;
    
    public OutputScreen(ArrayList<InputSchedule> schedules){
        
        this.schedules = schedules;
        
        namesArray = new ArrayList[Schedule.periodsList.size()][Schedule.WEEKDAYS.length];
        namesList = new ArrayList();
        
        setNames();
        
        schedule = new OutputSchedule(namesArray, namesList);
        
        createContents();
        setStyles();
        
        this.getChildren().add(mainContainer);
        
    }
    
    private void setNames(){
        
        for(InputSchedule schedule : schedules) {
            
            namesList.add(schedule.getOwnerName());
            
            boolean[][] mirrorSchedule = schedule.getMirrorSchedule();
        
            for(int i = 0; i < mirrorSchedule.length; i++){
                
                for(int j = 0; j < mirrorSchedule[i].length; j++){
                    
                    if(mirrorSchedule[i][j]){
                        
                        if(namesArray[i][j] == null){
                            namesArray[i][j] = new ArrayList();
                            namesArray[i][j].add(schedule.getOwnerName());
                        } else {
                            namesArray[i][j].add(schedule.getOwnerName());
                        }
                        
                    }
                }
            }
        }
    }
    
    private void createContents(){
        
        startButton = new Button("Volver a empezar");
        startButton.setOnAction(new StartButtonHandler());
        
        mainContainer = new VBox();
        labelsContainer = new HBox();
        buttonsContainer = new HBox();
        setLabels();
        
        buttonsContainer.getChildren().addAll(startButton);
        mainContainer.getChildren().addAll(labelsContainer, schedule.getAsNode(), buttonsContainer);
    }
    
    private void setStyles(){
        
        this.setAlignment(Pos.CENTER);
        
        labelsContainer.setAlignment(Pos.CENTER);
        labelsContainer.setSpacing(20);
        
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.setSpacing(20);
        
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setSpacing(20);
    }
    
    private void setLabels(){
        
        Rectangle everyoneToken = new Rectangle(15, 15, Color.AQUAMARINE);
        Rectangle nobodyToken = new Rectangle(15, 15, Color.DEEPPINK);
        Rectangle someoneToken = new Rectangle(15, 15, Color.GREY);
        
        Label everyone = new Label("TODOS ESTÁN DISPONIBLES");
        Label nobody = new Label("TODOS ESTÁN OCUPADOS");
        Label someone = new Label("CANTIDAD DE PERSONAS DISPONIBLES");
        
        everyone.setGraphic(everyoneToken);
        nobody.setGraphic(nobodyToken);
        someone.setGraphic(someoneToken);
        
        labelsContainer.getChildren().addAll(everyone, nobody, someone);
    }
    
    private class StartButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            
            Stage stage = (Stage) OutputScreen.this.getScene().getWindow();
                
            SettingsScreen nextScreen = new SettingsScreen();

            stage.setScene(new Scene(nextScreen, 900, 600));
        
        }
    }
    
}
