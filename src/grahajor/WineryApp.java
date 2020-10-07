/*
    Name:  Jordan Graham
    Assignment:  A6_JordanGraham
    Program: WineryApp
    Date:  April 11th, 2020
    
    Description:
    WineryApp is an application that allows the user to manage wine orders, the
    user has the capability to add. modify, or remove records from a file.
*/
package grahajor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author H.D
 */
public class WineryApp extends Application{
    
    public static void main(String[] args) {
        launch(args);        
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLMain.fxml"));
        stage.setTitle("Winery Application");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
