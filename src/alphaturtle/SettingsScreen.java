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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    private ArrayList<String> hoursList;
    
    private Text step1, step2;
    private Label from, to;
    private ComboBox period, fromOptions, toOptions;
    private Button nextScreen;
    
    public SettingsScreen(){
        
        step1 = new Text("Primero tienes que establecer la unidad de división mínima de tiempo que tendrá cada "
                + "día de todos los horarios. ¿Cómo elegir?\n"
                + "Si alguna actividad en alguno de los horarios comienza o termina en una hora de la forma HH:15 o HH:45, "
                + "entonces debes elegir 15 minutos.\n"
                + "Si alguna actividad en alguno de los horarios comienza o termina en una hora de la forma HH:30, entonces "
                + "debes elegir 30 minutos.\n"
                + "Si todas las actividades de todos los horarios comienzan y terminan "
                + "en una hora en punto (HH:00), elige 60 minutos.");
        step1.setWrappingWidth(500);
        
        period = new ComboBox();
        
        period.getItems().addAll("10", "15", "30", "60");
        
        step2 = new Text("De entre todos los horarios, selecciona la hora de inicio de la actividad más tempranera y "
                + "la hora de finalización de la actividad más tardía.");
        
        step2.setWrappingWidth(500);
        
        period.setOnAction(new selectedPeriodHandler());
        
        
        fromOptions = new ComboBox();
        toOptions = new ComboBox();
        
        fromOptions.setOnAction(new selectedStartTimeHandler());
        
        nextScreen = new Button("Seguir");
        nextScreen.setOnAction(new nextButtonHandler());
        
        this.getChildren().addAll(step1, period, step2, fromOptions, toOptions, nextScreen);
        this.setAlignment(Pos.CENTER);
        
        
    }
    
    private class selectedPeriodHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            
            int selectedPeriod = Integer.parseInt( (String) period.getValue());
            
            createTime(selectedPeriod);
            fromOptions.setItems(FXCollections.observableArrayList(hoursList));
           
        }
    }
    
    private class selectedStartTimeHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            
            String selectedStartTime = (String)fromOptions.getValue();
            
            if(selectedStartTime != null){
            
                int startOfNewHoursList = hoursList.indexOf(selectedStartTime) + 1;

                List newHoursList = hoursList.subList(startOfNewHoursList, hoursList.size()-1);

                toOptions.setItems(FXCollections.observableList(newHoursList));
            }
           
        }
    }
    
    private class nextButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            
            boolean periodIsSelected = period.getValue() != null;
            boolean startTimeIsSelected = fromOptions.getValue() != null;
            boolean endTimeIsSelected = toOptions.getValue() != null;
            
            if(periodIsSelected && startTimeIsSelected && endTimeIsSelected){
                Stage stage = (Stage)SettingsScreen.this.getScene().getWindow();
                
                ScheduleScreen nextScreen = new ScheduleScreen();
                
                stage.setScene(new Scene(nextScreen, 600, 400));
            } else {
                
                Alert fieldEmptyAlert = new Alert(Alert.AlertType.INFORMATION);
                
                fieldEmptyAlert.setContentText("Hay un campo vacío");
                
                fieldEmptyAlert.showAndWait();
                
            }
           
        }
    }
    
    private void createTime(int period){
        
        this.hoursList = new ArrayList();
        
        for(int i = 0; i<=HOURS_OF_DAY;i++){
            
            String hour = String.format("%02d",i);
            
            if(i != HOURS_OF_DAY){
                
                for(int j = 0; j<MINUTES_OF_HOUR/period;j++) {

                    String minute = String.format("%02d",period*j);
                    
                    this.hoursList.add(hour+":"+minute);
                }
                
            } else {
                this.hoursList.add(hour+":00");
            }
            
        }
    }
    
}
