package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainView extends Application {
	
	Stage stage;
	Scene scene, scene1, scene2;
	Button btn1;
	Pane vBox;
	TextArea textArea1, textArea2;
	Label lbl1, lbl2;

	@SuppressWarnings("restriction")
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("LRMLtoDFL");
		primaryStage.setResizable(false);
		
		//TOOLBAR
		ToolBar toolbar = new ToolBar();		
		btn1 = new Button("Button 1");
		toolbar.getItems().add(btn1);
		
		textArea1 = new TextArea();
		textArea2 = new TextArea();
	  	textArea1.setEditable(false);
	  	textArea2.setEditable(false);
	  	textArea1.setPrefHeight(300);
	  	textArea2.setPrefHeight(300);
	  	textArea1.setWrapText(true);
	  	textArea2.setWrapText(true);
	  	
	  	lbl1 = new Label("LABELA 1");
	  	lbl2 = new Label("LABELA 2");
	  	lbl1.setStyle("-fx-font-weight: bold;");
	  	lbl2.setStyle("-fx-font-weight: bold;");
		
		vBox = new VBox(toolbar,lbl1, textArea1, lbl2, textArea2);
		
		//scene
		scene = new Scene(vBox, 1000, 660);
		primaryStage.setScene(scene);
		
		//***************DIALOG WIZARD***************//
		btn1.setOnAction(e -> wizardWindow1());
		
		primaryStage.show();
	}
	
	public void wizardWindow1() {
		List<String> choices = new ArrayList<>(); //test primer
		choices.add("Vozac se ukljucuje sa zemljanog puta");
		choices.add("b");
		choices.add("c");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("b", choices);
		dialog.setTitle("Choice Dialog");
		dialog.setHeaderText("Look, a Choice Dialog");
		dialog.setContentText("Choose your letter:");
		
		Optional<String> result = dialog.showAndWait();

		result.ifPresent(letter -> {
			System.out.println("Your choice: " + letter);
			wizardWindow2();
		});

	}
	
	public void wizardWindow2() {
		List<String> choices = new ArrayList<String>();
		choices.add("1");
		choices.add("2");
		choices.add("3");
		
		ChoiceDialog<String> dialog = new ChoiceDialog<>("1", choices);
		dialog.setTitle("Choice Dialog");
		dialog.setHeaderText("Look, a Choice Dialog");
		dialog.setContentText("Choose your number:");
		
		Optional<String> result = dialog.showAndWait();
		result.ifPresent(number -> System.out.println("Your choice: " + number));	
	}

}
