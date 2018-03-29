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
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Galo Xavier Figueroa Villacreses
 */
public class SettingsScreen extends VBox{
    
    private final int HOURS_OF_DAY = 24;
    private final int MINUTES_OF_HOUR = 60;
    private ArrayList<String> timeOptionsList;
    
    private Text instruction1, instruction2;
    private ComboBox periodOptions, startTimeOptions, endTimeOptions;
    private Button nextScreen;
    
    public SettingsScreen(){
        
        createContents();
        setListeners();
        this.getChildren().addAll(instruction1, periodOptions, instruction2, startTimeOptions, endTimeOptions, nextScreen);
        this.setAlignment(Pos.CENTER);
    }
    
    private void createContents(){
        
        instruction1 = new Text("Primero tienes que establecer la unidad de división mínima de tiempo que tendrá cada "
                + "día de todos los horarios. ¿Cómo elegir?\n"
                + "Si alguna actividad en alguno de los horarios comienza o termina en una hora de la forma HH:15 o HH:45, "
                + "entonces debes elegir 15 minutos.\n"
                + "Si alguna actividad en alguno de los horarios comienza o termina en una hora de la forma HH:30, entonces "
                + "debes elegir 30 minutos.\n"
                + "Si todas las actividades de todos los horarios comienzan y terminan "
                + "en una hora en punto (HH:00), elige 60 minutos.");
        instruction1.setWrappingWidth(500);
        
        periodOptions = new ComboBox();
        
        periodOptions.getItems().addAll("10", "15", "30", "60");
        
        instruction2 = new Text("De entre todos los horarios, selecciona la hora de inicio de la actividad más tempranera y "
                + "la hora de finalización de la actividad más tardía.");
        
        instruction2.setWrappingWidth(500);
        
        startTimeOptions = new ComboBox();
        endTimeOptions = new ComboBox();
        
        nextScreen = new Button("Seguir");
        
    }
    
    private void setListeners(){
        
        periodOptions.setOnAction(new periodSelectionHandler());
        startTimeOptions.setOnAction(new startTimeSelectionHandler());
        nextScreen.setOnAction(new nextButtonHandler());
        
    }
    
    private class periodSelectionHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            
            int selectedPeriod = Integer.parseInt((String) periodOptions.getValue());
            
            createTimeOptionsList(selectedPeriod);
            
            startTimeOptions.setItems(FXCollections.observableArrayList(timeOptionsList));
           
        }
    }
    
    private class startTimeSelectionHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            
            String selectedStartTime = (String) startTimeOptions.getValue();
            
            if(selectedStartTime != null){
            
                int startOfEndTimeOptions = timeOptionsList.indexOf(selectedStartTime) + 1;

                List endTimeOptionsList = timeOptionsList.subList(startOfEndTimeOptions, timeOptionsList.size());

                endTimeOptions.setItems(FXCollections.observableList(endTimeOptionsList));
            }
           
        }
    }
    
    private class nextButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            
            boolean periodIsSelected = periodOptions.getValue() != null;
            boolean startTimeIsSelected = startTimeOptions.getValue() != null;
            boolean endTimeIsSelected = endTimeOptions.getValue() != null;
            
            if(periodIsSelected && startTimeIsSelected && endTimeIsSelected){
                
                Stage stage = (Stage) SettingsScreen.this.getScene().getWindow();
                
                ScheduleScreen nextScreen = new ScheduleScreen(getPeriodList());
                
                stage.setScene(new Scene(nextScreen, 900, 600));
                
            } else {
                
                Alert fieldEmptyAlert = new Alert(Alert.AlertType.INFORMATION);
                
                fieldEmptyAlert.setTitle("Información");
                fieldEmptyAlert.setHeaderText("¡Campo vacío!");
                fieldEmptyAlert.setContentText("Hay un campo vacío");
                
                fieldEmptyAlert.showAndWait();
                
            }
           
        }
    }
    
    private void createTimeOptionsList(int period){
        
        this.timeOptionsList = new ArrayList();
        
        for(int i = 0; i<=HOURS_OF_DAY;i++){
            
            String hour = String.format("%02d",i);
            
            if(i != HOURS_OF_DAY){
                
                for(int j = 0; j<MINUTES_OF_HOUR/period;j++) {

                    String minute = String.format("%02d",period*j);
                    
                    this.timeOptionsList.add(hour+":"+minute);
                }
                
            } else {
                this.timeOptionsList.add(hour+":00");
            }
            
        }
    }
    
    private ArrayList<String> getPeriodList(){
        
        String selectedStartTime = (String)startTimeOptions.getValue();
        String selectedEndTime = (String)endTimeOptions.getValue();
        int startIndex = timeOptionsList.indexOf(selectedStartTime);
        int endIndex = timeOptionsList.indexOf(selectedEndTime);
        
         ArrayList<String> periodsList = new ArrayList();
        
        for(int i = startIndex;i<endIndex;i++){
            
            String startHour = timeOptionsList.get(i);
            String endHour = timeOptionsList.get(i + 1);
            
            String period = startHour + " - " + endHour;
            
            periodsList.add(period);
            
        }
        
        return periodsList;
        
    }
    
}
