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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Galo Xavier Figueroa Villacreses
 */
public class InputScreen extends HBox {
    
    private InputSchedule schedule;
    private final ArrayList<InputSchedule> schedules;
    private VBox mainContainer;
    private HBox fieldContainer, scheduleContainer, buttonsContainer;
    private TextField nameField;
    private Button startButton, backButton, nextButton, finishButton;
    private Label alertLabel;
    
    public InputScreen(){
        
        schedules = new ArrayList();
        
        createContents();
        setStyles();
        setListeners();
        
        this.getChildren().add(mainContainer);
        
    }
    
    private void createContents(){
        
        schedule = new InputSchedule();
        schedules.add(schedule);
        
        nameField = new TextField();
        startButton = new Button("Volver a empezar");
        backButton = new Button("Anterior");
        nextButton = new Button("Siguiente");
        finishButton = new Button("Finalizar");
        
        alertLabel = new Label("¡Olvidaste el nombre!");
        
        fieldContainer = new HBox();
        scheduleContainer = new HBox();
        buttonsContainer = new HBox();
        mainContainer = new VBox();
        
        fieldContainer.getChildren().add(nameField);
        scheduleContainer.getChildren().add(this.schedule.getAsNode());
        buttonsContainer.getChildren().addAll(startButton, backButton, nextButton, finishButton);
        mainContainer.getChildren().addAll(fieldContainer, scheduleContainer, buttonsContainer);
        
    }
    
    private void setListeners(){
        
        startButton.setOnAction(new StartButtonHandler());
        backButton.setOnAction(new BackButtonHandler());
        nextButton.setOnAction(new NextButtonHandler());
        finishButton.setOnAction(new FinishButtonHandler());
        
    }
    
    private void setStyles(){
        this.setAlignment(Pos.CENTER);
        
        nameField.setMinSize(300,30);
        nameField.setMaxSize(300,30);
        nameField.setAlignment(Pos.CENTER);
        nameField.setPromptText("Nombre del propietario del horario");
        nameField.setFocusTraversable(false);
        nameField.setStyle("-fx-background-radius: 0");
        
        backButton.setDisable(true);
        finishButton.setDisable(true);
        
        startButton.setStyle("-fx-background-radius: 0");
        backButton.setStyle("-fx-background-radius: 0");
        nextButton.setStyle("-fx-background-radius: 0");
        finishButton.setStyle("-fx-background-radius: 0");
        
        alertLabel.setTextFill(Color.RED);
        alertLabel.setStyle("-fx-font-weight: bold");
        
        fieldContainer.setAlignment(Pos.CENTER);
        fieldContainer.setSpacing(20);
        
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.setSpacing(20);
        
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setSpacing(20);
        
    }

    private class StartButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            
            Stage stage = (Stage) InputScreen.this.getScene().getWindow();
                
            SettingsScreen nextScreen = new SettingsScreen();

            stage.setScene(new Scene(nextScreen,500,300));
        
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
            
            int previusScheduleIndex = schedules.size() - 2;
            
            schedule = schedules.get(previusScheduleIndex);
            schedules.remove(previusScheduleIndex + 1);
            
            scheduleContainer.getChildren().clear();
            scheduleContainer.getChildren().add(schedule.getAsNode());
            nameField.setText(schedule.getOwnerName());
            
        }
    }

    private class NextButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            
            String ownerName = nameField.getText();
            
            if(ownerName.isEmpty()){
                
                if(!fieldContainer.getChildren().contains(alertLabel)) fieldContainer.getChildren().add(alertLabel);
            
            } else {
                
                schedule.setOwnerName(ownerName);
                
                schedule = new InputSchedule();
                schedules.add(InputScreen.this.schedule);
                
                nameField.clear();
                fieldContainer.getChildren().remove(alertLabel);
                if(backButton.isDisabled()) backButton.setDisable(false);
                if(finishButton.isDisabled()) finishButton.setDisable(false);
                
                scheduleContainer.getChildren().clear();
                scheduleContainer.getChildren().add(schedule.getAsNode());
            }
        
        }
    }

    private class FinishButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            
            String ownerName = nameField.getText();
            
            if(ownerName.isEmpty()){
                
                if(!fieldContainer.getChildren().contains(alertLabel)) fieldContainer.getChildren().add(alertLabel);
                
            } else {
                
                schedule.setOwnerName(ownerName);
                
                Stage stage = (Stage)InputScreen.this.getScene().getWindow();
            
                OutputScreen nextScreen = new OutputScreen(schedules);

                stage.setScene(new Scene(nextScreen,900,600));
                
            }
            
        }
    }
    
}