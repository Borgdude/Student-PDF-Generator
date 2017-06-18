package com.borgdude.generator.window;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.borgdude.generator.model.*;

public class Window extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Student PDF Generator");
		Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("Window.fxml"));
		Scene myScene = new Scene(myPane);
	    primaryStage.setScene(myScene);
	    primaryStage.show();
	}
	
	public static void main(String[] args){
		launch(args);
	}

}
