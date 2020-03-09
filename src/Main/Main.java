package Main;

import javafx.application.Application;
import runReasoner.RunReasoner;
import view.MainView;

public class Main {

	public static void main(String[] args) throws Exception {
		
		//Application.launch(MainView.class, args); 
		
		RunReasoner rr = new RunReasoner();
		rr.runReasoner();

		 
	}

}
