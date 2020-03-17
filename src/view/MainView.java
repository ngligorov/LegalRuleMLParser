package view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;

import javax.swing.plaf.FileChooserUI;
import javax.xml.bind.JAXBException;

import org.controlsfx.control.CheckListView;

import Main.Main;
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
import runReasoner.RunReasoner;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.Parent;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

public class MainView extends Application {

	private Stage stage;
	private Scene scene, scene1, scene2;
	private Button btn1, btn2, btn1w2, btn2w2;
	private Pane vBox;
	private static TextArea textArea1;
	private static TextArea textArea2;
	private Label lbl1, lbl2;
	private File selectedFile;

	RunReasoner rr = new RunReasoner();

	@SuppressWarnings("restriction")
	@Override
	public void start(Stage primaryStage) throws Exception {

		parseXmlFiles();

		primaryStage.setTitle("LRMLtoDFL");
		primaryStage.setResizable(false);

		// TOOLBAR
		ToolBar toolbar = new ToolBar();
		btn1 = new Button("Button 1");
		toolbar.getItems().add(btn1);
		btn2 = new Button("Button 2");
		toolbar.getItems().add(btn2);

		textArea1 = new TextArea("TEST");
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

		vBox = new VBox(toolbar, lbl1, textArea1, lbl2, textArea2);

		// scene
		scene = new Scene(vBox, 1000, 660);
		primaryStage.setScene(scene);

		// ***************DIALOG WIZARD***************//
		btn1.setOnAction(e -> wizardWindow1());

		btn2.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			selectedFile = fileChooser.showOpenDialog(stage);
			try {
				rr.transformer(selectedFile);
			} catch (JAXBException | IOException e1) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("You can't select that file!");
				alert.setContentText("File you've selected is not of the right type!");
				alert.showAndWait();
			}
		});

		primaryStage.show();
	}

	public void wizardWindow1() {
		List<String> choices = new ArrayList<>(); // test primer

		for (Entry<String, List<String>> keyValue : Main.factsFileMap.entrySet())
			choices.add(keyValue.getKey());

		ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
		dialog.setTitle("Choice Dialog");
		dialog.setHeaderText("Look, a Choice Dialog");
		dialog.setContentText("Choose your letter:");

		Optional<String> result = dialog.showAndWait();

		result.ifPresent(chosenFile -> {
			System.out.println("Your choice: " + chosenFile);
			wizardWindow2(chosenFile);
		});

	}

	public void wizardWindow2(String chosenFile) {
		ObservableList<String> choices = FXCollections.observableArrayList();
//
		choices.addAll(Main.factsFileMap.get(chosenFile));
//
//		ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
//		dialog.setTitle("Choice Dialog");
//		dialog.setHeaderText("Look, a Choice Dialog");
//		dialog.setContentText("Choose your number:");
//
//		Optional<String> result = dialog.showAndWait();
//		result.ifPresent(number -> System.out.println("Your choice: " + number));
		
		//Rectangle rect = new Rectangle(100, 100, 200, 300);
		
		// create the data to show in the CheckListView 
//		 final ObservableList<String> strings = FXCollections.observableArrayList();
//		 for (int i = 0; i <= 100; i++) {
//		     strings.add("Item " + i);
//		 }
		 
		 // Create the CheckListView with the data 
		 final CheckListView<String> checkListView = new CheckListView<>(choices);
		       
		 // and listen to the relevant events (e.g. when the selected indices or 
		 // selected items change).
		 checkListView.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
		     public void onChanged(ListChangeListener.Change<? extends String> c) {
		         System.out.println(checkListView.getCheckModel().getCheckedItems());
		     }
		 });
		 
		Stage window = new Stage();
		 
		btn1w2 = new Button("Ok");
		btn2w2 = new Button("Cancel");
		
		btn2w2.setOnAction(e -> window.close());
		btn1w2.setOnAction(e -> {
			System.out.println("TEST TEST TEST");
		});
		
		VBox vbCenter = new VBox();
		vbCenter.getChildren().add(checkListView);
		HBox hbButtons = new HBox();
		hbButtons.getChildren().add(btn1w2);
		hbButtons.getChildren().add(btn2w2);
	    hbButtons.setAlignment(Pos.CENTER_RIGHT);
		
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(20));
		root.setCenter(vbCenter);
	    root.setBottom(hbButtons);
		
		Parent content = root;

		// create scene containing the content
		Scene scene = new Scene(content);
		window.setScene(scene);

		// make window visible
		window.show();
	}

	public TextArea getTextArea1() {
		return textArea1;
	}

	public void setTextArea1(TextArea textArea1) {
		this.textArea1 = textArea1;
	}

	public TextArea getTextArea2() {
		return textArea2;
	}

	public void setTextArea2(TextArea textArea2) {
		this.textArea2 = textArea2;
	}

	private void parseXmlFiles() throws Exception {
		String[] files;

		File folder = new File("src/xml");
		files = folder.list();

		for (String filePath : files) {
			System.out.println("src/xml/" + filePath);
			rr.transformer(new File("src/xml/" + filePath));
		}
	}

}
