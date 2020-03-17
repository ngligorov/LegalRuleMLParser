package Main;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import runReasoner.RunReasoner;
import view.MainView;

public class Main {

	public static Map<String, List<String>> factsFileMap = new HashMap<>();
	
	public static void main(String[] args) throws Exception {
		
		Application.launch(MainView.class, args); 
		
//		RunReasoner rr = new RunReasoner();
//		rr.setFileName("Clan88.dfl");
//		rr.transformer(new File("src/xml/testPrvi.xml"));
//		rr.runReasoner();

		 
	}

}
