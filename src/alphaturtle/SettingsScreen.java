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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Galo Xavier Figueroa Villacreses
 */
public class SettingsScreen extends BorderPane{
    
    private final int HOURS_OF_DAY = 24;
    private final int MINUTES_OF_HOUR = 60;
    private ArrayList<String> timeOptionsList;
    
    private GridPane settingsContainer;
    private VBox topContainer;
    private HBox bottomContainer;
    private ComboBox periodOptions, startTimeOptions, endTimeOptions;
    private Button nextScreen;
    private Label title, description, instruction1, instruction2, instruction3, alertLabel, example;
    
    public SettingsScreen(){
        
        createContents();
        setStyles();
        setListeners();
        this.setTop(topContainer);
        this.setCenter(settingsContainer);
        this.setBottom(bottomContainer);
    }
    
    private void createContents(){
        
        title = new Label("Configura los horarios");
        
        description = new Label("Con esta configuración se crearán todos los horarios");
        
        instruction1 = new Label("Mínima de división del día");
        periodOptions = new ComboBox();
        periodOptions.getItems().addAll("10 min", "15 min", "30 min", "60 min");
        
        instruction2 = new Label("Hora de inicio de la actividad más tempranera");
        startTimeOptions = new ComboBox();
        
        instruction3 = new Label("Hora de finalización de la actividad más tardía");
        endTimeOptions = new ComboBox();
        
        nextScreen = new Button("Seguir");
        
        alertLabel = new Label("¡Dejaste algo sin seleccionar!");
        
        topContainer = new VBox();
        topContainer.getChildren().addAll(title, description);
        
        settingsContainer = new GridPane();
        settingsContainer.addRow(0, instruction1, periodOptions);
        settingsContainer.addRow(1, instruction2, startTimeOptions);
        settingsContainer.addRow(2, instruction3, endTimeOptions);
        
        bottomContainer = new HBox();
        bottomContainer.getChildren().addAll(alertLabel, nextScreen);
        
    }
    
    private void setStyles(){
        
        title.setStyle("-fx-font-weight: bold");
        description.setPadding(new Insets(5, 0, 0, 15));
        
        instruction1.setGraphic(new ImageView(SettingsScreen.class.getResource("/images/help.png").toExternalForm()));
        instruction1.setContentDisplay(ContentDisplay.RIGHT);
        instruction1.setGraphicTextGap(10);
        instruction1.setTooltip(generateHelpTooltip());
        
        periodOptions.setMinWidth(85);
        periodOptions.setStyle("-fx-background-radius: 0");
        
        startTimeOptions.setMinWidth(85);
        startTimeOptions.setStyle("-fx-background-radius: 0");
        
        endTimeOptions.setMinWidth(85);
        endTimeOptions.setStyle("-fx-background-radius: 0");
        
        nextScreen.setMinWidth(75);
        nextScreen.setStyle("-fx-background-radius: 0");
        
        alertLabel.setTextFill(Color.RED);
        alertLabel.setStyle("-fx-font-weight: bold");
        alertLabel.setVisible(false);
        
        topContainer.setPadding(new Insets(5, 15, 15, 15));
        topContainer.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        topContainer.setBorder(new Border(new BorderStroke(Color.DARKGRAY, Color.TRANSPARENT, Color.DARKGRAY, Color.TRANSPARENT, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));

        settingsContainer.setHgap(20);
        settingsContainer.setVgap(20);
        settingsContainer.setAlignment(Pos.CENTER);
        
        bottomContainer.setAlignment(Pos.CENTER_RIGHT);
        bottomContainer.setSpacing(20);
        bottomContainer.setPadding(new Insets(15, 15, 15, 0));
        bottomContainer.setBorder(new Border(new BorderStroke(Color.DARKGRAY, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY)));
    }
    
    private Tooltip generateHelpTooltip(){
        
        Tooltip helpTooltip = new Tooltip();
        
        helpTooltip.setText("- Si alguna actividad en alguno de los horarios comienza o termina en una hora de la forma HH:15 o HH:45, "
                + "entonces debes elegir 15 minutos.\n"
                + "- Si alguna actividad en alguno de los horarios comienza o termina en una hora de la forma HH:30, entonces "
                + "debes elegir 30 minutos.\n"
                + "- Si todas las actividades de todos los horarios comienzan y terminan "
                + "en una hora en punto (HH:00), elige 60 minutos.");
        helpTooltip.setWrapText(true);
        helpTooltip.setMaxWidth(350);
        helpTooltip.setStyle("-fx-background-color: BEIGE;-fx-background-radius:0;-fx-text-fill:#000000");
        
        return helpTooltip;
    }
    
    private void setListeners(){
        
        periodOptions.setOnAction(new periodSelectionHandler());
        startTimeOptions.setOnAction(new startTimeSelectionHandler());
        nextScreen.setOnAction(new nextButtonHandler());
        
    }
    
    private class periodSelectionHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            
            int selectedPeriod = Integer.parseInt(periodOptions.getValue().toString().substring(0, 2));
            
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
                
                initializePeriodsList();
                
                InputScreen nextScreen = new InputScreen();
                Stage stage = (Stage) SettingsScreen.this.getScene().getWindow();
                stage.setScene(new Scene(nextScreen, 900, 600));
                stage.centerOnScreen();
                
            } else if(!alertLabel.isVisible()) alertLabel.setVisible(true);
           
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
    
    private void initializePeriodsList(){
        
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
        
        Schedule.periodsList = periodsList;
        
    }
    
}
