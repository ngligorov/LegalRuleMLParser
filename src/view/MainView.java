package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;

import javax.swing.plaf.FileChooserUI;
import javax.xml.bind.JAXBException;

import org.controlsfx.control.CheckListView;

import com.app.utils.TextUtilities;

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
import parser.JavaToDefeisible;
import runReasoner.RunReasoner;
import spindle.core.dom.Conclusion;
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
	private String chosenFileBtn1;

	private RunReasoner rr = new RunReasoner();

	@SuppressWarnings("restriction")
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("LRMLtoDFL");
		primaryStage.setResizable(false);

		// TOOLBAR
		ToolBar toolbar = new ToolBar();
		btn1 = new Button("Choose DFL file");
		toolbar.getItems().add(btn1);
		btn2 = new Button("Choose LRML file");
		toolbar.getItems().add(btn2);

		textArea1 = new TextArea();
		textArea2 = new TextArea();
		textArea1.setEditable(false);
		textArea2.setEditable(false);
		textArea1.setPrefHeight(300);
		textArea2.setPrefHeight(300);
		textArea1.setWrapText(true);
		textArea2.setWrapText(true);

		lbl1 = new Label("Theory:");
		lbl2 = new Label("Conclusions:");
		lbl1.setStyle("-fx-font-weight: bold;");
		lbl2.setStyle("-fx-font-weight: bold;");

		vBox = new VBox(toolbar, lbl1, textArea1, lbl2, textArea2);

		// scene
		scene = new Scene(vBox, 1000, 660);
		primaryStage.setScene(scene);

		// ***************DIALOG WIZARD***************//
		btn1.setOnAction(e -> {
			try {
				parseXmlFiles();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			wizardWindow1();

		});

		btn2.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
//			try {
//				selectedFile = fileChooser.showOpenDialog(stage);
//				rr.transformer(selectedFile);
//			} catch (IllegalArgumentException | JAXBException | IOException e1) {
//				Alert alert = new Alert(AlertType.WARNING);
//				alert.setTitle("Warning");
//				alert.setHeaderText("You have to select a file!");
//				alert.setContentText("File you've selected is not of the right type!");
//				alert.showAndWait();
//			}
		});

		primaryStage.show();
	}

	public void wizardWindow1() {
		List<String> choices = new ArrayList<>(); // test primer

		for (Entry<String, List<String>> keyValue : Main.factsFileMap.entrySet())
			choices.add(keyValue.getKey());

		ChoiceDialog<String> dialog = new ChoiceDialog<>(choices.get(0), choices);
		dialog.setTitle("Choice Dialog");
		dialog.setHeaderText("Defeisible logic");
		dialog.setContentText("Choose your DFL file:");

		Optional<String> result = dialog.showAndWait();

		result.ifPresent(chosenFile -> {
			wizardWindow2(chosenFile);
			this.chosenFileBtn1 = chosenFile;
		});

	}

	public void wizardWindow2(String chosenFile) {
		ObservableList<String> choices = FXCollections.observableArrayList();

		choices.addAll(Main.factsFileMap.get(chosenFile));

		// Create the CheckListView with the data
		final CheckListView<String> checkListView = new CheckListView<>(choices);

		// and listen to the relevant events (e.g. when the selected indices or
		// selected items change).
		checkListView.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			public void onChanged(ListChangeListener.Change<? extends String> c) {
				ObservableList<String> observableListFacts = checkListView.getCheckModel().getCheckedItems();
				List<String> facts = new ArrayList<>();

				for (String fact : observableListFacts)
					facts.add(fact);

				rr.setFacts(facts);
			}
		});

		Stage window = new Stage();

		Label lbl = new Label("Labela 1: ");
		TextField txtField = new TextField();

		btn1w2 = new Button("Ok");
		btn2w2 = new Button("Cancel");

		btn2w2.setOnAction(e -> window.close());
		btn1w2.setOnAction(e -> {

			try {
				window.close();

				this.rr.setFileName(chosenFileBtn1);
				this.rr.setLiteral(txtField.getText());
				List<Conclusion> conclusions = this.rr.runReasoner();

				URL url = this.getClass()
						.getResource(File.separator + "x_defeisible" + File.separator + chosenFileBtn1);
				File fajl = new File(url.toURI());

				BufferedReader br = new BufferedReader(new FileReader(fajl));

				String st;
				String fileText = "";
				while ((st = br.readLine()) != null) {
					fileText = fileText + st + "\n";
				}

				textArea1.setText(fileText);

				StringBuilder sb = new StringBuilder(TextUtilities.repeatStringPattern("-", 30));
				sb.append("\nConclusions\n===========");
				for (Conclusion conclusion : conclusions) {
					sb.append("\n").append(conclusion.toString());
				}

				textArea2.setText(sb.toString());

			} catch (Exception exception) {
				exception.printStackTrace();
			}

		});

		VBox vbCenter = new VBox();
		vbCenter.getChildren().add(checkListView);
		if (Main.literalsFileMap.get(chosenFile) != null) {
			lbl.setText("Enter a number for " + Main.literalsFileMap.get(chosenFile) + ":");
			vbCenter.getChildren().add(lbl);
			vbCenter.getChildren().add(txtField);
		}
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

		InputStream inputStream = this.getClass().getResourceAsStream("/xml/biciklisti.xml");
//		System.out.println(url);

//		for (String filePath : files) {
//			url = this.getClass().getResource(File.separator + "xml" + File.separator + filePath);
//			File fajl = new File(url.toURI());
//
//			rr.transformer(fajl);
		
		rr.transformer(inputStream);
		inputStream = this.getClass().getResourceAsStream("/xml/raskrsnica.xml");
		rr.transformer(inputStream);
	}

}
