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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author Galo Xavier Figueroa Villacreses
 */
public class WelcomeScreen extends BorderPane {
    
    VBox centerContainer;
    HBox bottomContainer;
    ImageView logo;
    Label license;
    Button startButton;
    
    public WelcomeScreen(){
        
        logo = new ImageView(WelcomeScreen.class.getResource("/images/logo.png").toExternalForm());
        startButton = new Button("Empezar");
        startButton.setOnAction(new nextButtonHandler());
        license = new Label("Copyright (C) 2018 Galo Xavier Figueroa Villacreses\nGNU General Public License v3.0");
        centerContainer = new VBox();
        bottomContainer = new HBox();
        
        setStyles();
        
        //this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setCenter(centerContainer);
        this.setBottom(bottomContainer);
    }
    
    private void setStyles(){
        
        startButton.setFocusTraversable(false);
        startButton.setMinSize(100, 35);
        startButton.setStyle("-fx-background-radius: 0");
        
        license.setFont(Font.font(null, FontWeight.EXTRA_BOLD, 12));
        license.setTextFill(Color.GRAY);
        license.setTextAlignment(TextAlignment.CENTER);
        
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setSpacing(20);
        centerContainer.getChildren().addAll(logo, startButton);
        
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.getChildren().add(license);
        bottomContainer.setPadding(new Insets(0, 0, 10, 0));
        
    }
    
    private class nextButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            
            SettingsScreen settingsScreen = new SettingsScreen();
            
            Stage stage = (Stage)WelcomeScreen.this.getScene().getWindow();
            stage.close();
            stage = new Stage();
            stage.setScene(new Scene(settingsScreen,500,300));
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();
        }
    
    }
    
}
