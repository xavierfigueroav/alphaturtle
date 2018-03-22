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

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Galo Xavier Figueroa Villacreses
 */
public class WelcomeScreen extends VBox {
    
    Text appName, appDescription;
    Button startButton;
    
    public WelcomeScreen(){
        
        appName = new Text("AlphaTurtle");
        appDescription = new Text("Esta aplicación te permitirá \"superponer\" varios horarios y de esa forma encontrar "
                + "el \"horario intersección\" de ellos, haciendo énfasis en el tiempo libre y sin tomar "
                + "en cuenta las actividades planificadas en ellos.\n");
        
        appDescription.setWrappingWidth(500);
        startButton = new Button("¡Empecemos!");
        startButton.setOnAction(new nextSceneHandler());
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(appName, appDescription, startButton);
        
    }
    
    private class nextSceneHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            
            Stage stage = (Stage)WelcomeScreen.this.getScene().getWindow();
            
            SettingsScreen nextScreen = new SettingsScreen();
            
            stage.setScene(new Scene(nextScreen,600,400));
            
        }
    
    }
    
}
