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
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Galo Xavier Figueroa Villacreses
 */
public class ScheduleScreen extends HBox {
    
    private final ArrayList<String> periodsList;
    private Schedule schedule;
    private final ArrayList<Schedule> schedules;
    private VBox mainContainer;
    private HBox scheduleContainer, buttonsContainer;
    private TextField nameField;
    private Button backButton, addButton, nextButton, startButton;
    
    public ScheduleScreen(ArrayList<String> periodsList){
        
        this.periodsList = periodsList;
        
        schedules = new ArrayList();
        
        createContents();
        setStyles();
        setListeners();
        
        this.getChildren().add(mainContainer);
        
    }
    
    private void createContents(){
        
        schedule = new Schedule(periodsList);
        schedules.add(schedule);
        
        nameField = new TextField();
        startButton = new Button("Empezar de nuevo");
        backButton = new Button("Anterior");
        addButton = new Button("Agregar otro horario");
        nextButton = new Button("Agregar y finalizar");
        scheduleContainer = new HBox();
        mainContainer = new VBox();
        buttonsContainer = new HBox();
        
        scheduleContainer.getChildren().add(this.schedule.getAsNode());
        buttonsContainer.getChildren().addAll(startButton, backButton, addButton, nextButton);
        mainContainer.getChildren().addAll(nameField, scheduleContainer, buttonsContainer);
        
    }
    
    private void setListeners(){
        
        startButton.setOnAction(new StartButtonHandler());
        backButton.setOnAction(new BackButtonHandler());
        addButton.setOnAction(new AddButtonHandler());
        nextButton.setOnAction(new NextButtonHandler());
        
    }
    
    private void setStyles(){
        this.setAlignment(Pos.CENTER);
        
        nameField.setMinSize(300,30);
        nameField.setMaxSize(300,30);
        nameField.setAlignment(Pos.CENTER);
        nameField.setPromptText("Nombre del propietario del horario");
        nameField.setFocusTraversable(false);
        
        backButton.setDisable(true);
        nextButton.setDisable(true);
        
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.setSpacing(20);
        
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setSpacing(20);
        
    }

    private class StartButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            
            Stage stage = (Stage) ScheduleScreen.this.getScene().getWindow();
                
            SettingsScreen nextScreen = new SettingsScreen();

            stage.setScene(new Scene(nextScreen, 600, 400));
        
        }
    }

    private class BackButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            
            askPermission();
            
        }

        private void askPermission() {
            
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            
            confirmationAlert.setTitle("Confirmación");
            confirmationAlert.setContentText("Al regresar perderás la información del horario actual");
            confirmationAlert.setHeaderText("¿Estás seguro de regresar?");

            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if(result.get() == ButtonType.OK){

                yield();

            }
        }
        
        private void yield(){
            
            int previusScheduleIndex = ScheduleScreen.this.schedules.size() - 2;
            
            ScheduleScreen.this.schedule = ScheduleScreen.this.schedules.get(previusScheduleIndex);
            ScheduleScreen.this.schedules.remove(previusScheduleIndex + 1);
            
            ScheduleScreen.this.scheduleContainer.getChildren().clear();
            ScheduleScreen.this.scheduleContainer.getChildren().add(ScheduleScreen.this.schedule.getAsNode());
            
        }
    }

    private class AddButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            
            String ownerName = nameField.getText();
            
            if(ownerName.isEmpty()){
                
                Alert fieldEmptyAlert = new Alert(Alert.AlertType.INFORMATION);
                
                fieldEmptyAlert.setTitle("Información");
                fieldEmptyAlert.setHeaderText("¡Campo vacío!");
                fieldEmptyAlert.setContentText("Olvidaste ingresar un nombre");
                
                fieldEmptyAlert.showAndWait();
                
            } else {
                
                ScheduleScreen.this.schedule.setOwnerName(ownerName);
                
                nameField.setText("");
                if(backButton.isDisabled()) backButton.setDisable(false);
                if(nextButton.isDisabled()) nextButton.setDisable(false);
                
                ScheduleScreen.this.schedule = new Schedule(periodsList);
                ScheduleScreen.this.schedules.add(ScheduleScreen.this.schedule);
                
                ScheduleScreen.this.scheduleContainer.getChildren().clear();
                ScheduleScreen.this.scheduleContainer.getChildren().add(ScheduleScreen.this.schedule.getAsNode());
            }
        
        }
    }

    private class NextButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            
            String ownerName = nameField.getText();
            
            if(ownerName.isEmpty()){
                
                Alert fieldEmptyAlert = new Alert(Alert.AlertType.INFORMATION);
                
                fieldEmptyAlert.setTitle("Información");
                fieldEmptyAlert.setHeaderText("¡Campo vacío!");
                fieldEmptyAlert.setContentText("Olvidaste ingresar un nombre");
                
                fieldEmptyAlert.showAndWait();
                
            } else {
                
                ScheduleScreen.this.schedule.setOwnerName(ownerName);
                
                Stage stage = (Stage)ScheduleScreen.this.getScene().getWindow();
            
                ResultsScreen nextScreen = new ResultsScreen();

                stage.setScene(new Scene(nextScreen,900,600));
                
            }
            
        }
    }
    
}