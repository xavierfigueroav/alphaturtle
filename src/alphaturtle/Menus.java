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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 *
 * @author Galo Xavier Figueroa Villacreses
 */
public class Menus{
    
    private final MenuBar menuBar;
    
    public Menus(){
        
        menuBar = new MenuBar();
        
        Menu menuHelp = new Menu("Help");
        
        MenuItem howTo = new MenuItem("How to use?");
        MenuItem about = new MenuItem("About");
        
        about.setOnAction(new HandlerClickOnAbout());
        
        menuHelp.getItems().addAll(howTo, about);
        
        menuBar.getMenus().add(menuHelp);
        
    }
    
    private class HandlerClickOnAbout implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            
            Alert aboutWindow = new Alert(AlertType.INFORMATION);
            
            aboutWindow.setTitle("About");
            aboutWindow.setHeaderText("AlphaTurtle");
            aboutWindow.setContentText("Copyright (C) 2018 Galo Xavier Figueroa Villacreses\n"
                    + "GNU General Public License v3.0");
            aboutWindow.showAndWait();
        
        }
    }
    
    public MenuBar getAsNode(){
        return menuBar;
    }
    
}
