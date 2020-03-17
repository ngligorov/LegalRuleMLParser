package runReasoner;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.app.utils.TextUtilities;

import Model.LegalRuleML;
import parser.JavaToDefeisible;
import spindle.Reasoner;
import spindle.core.dom.Conclusion;
import spindle.core.dom.Theory;
import spindle.engine.ReasoningEngineFactory;
import spindle.engine.TheoryNormalizer;
import spindle.io.IOManager;

public class RunReasoner {
	
	private String fileName;	
	
	public void runReasoner() throws Exception {
		
		  Theory theory = IOManager.getTheory(new File("src/x_defeisible/" + fileName),null); 
		  TheoryNormalizer theoryNormalizer = ReasoningEngineFactory.getTheoryNormalizer(theory.getTheoryType());
		  theoryNormalizer.setTheory(theory); 
		  theoryNormalizer.removeDefeater(); 
		  theory = theoryNormalizer.getTheory(); 
		  Reasoner reasoner = new Reasoner();
		  reasoner.loadTheory(theory); 
		  reasoner.getConclusions(); 
		  List<Conclusion> conclusions = reasoner.getConclusionsAsList();
		  
		  StringBuilder sb = new StringBuilder(TextUtilities.repeatStringPattern("-", 30)); 
		  sb.append("\nConclusions\n==========="); 
		  for (Conclusion conclusion : conclusions) { 
			  sb.append("\n").append(conclusion.toString()); 
		  }
		  
		  
		  System.out.println(sb.toString());
		  //System.out.println(fileName); //provera da li je uhvatio ime fajla
	}	
	
	
	public void transformer(File selectedFile) throws JAXBException, IOException {
		  //File file = new File("src/xml/" + fileName); 
		  JAXBContext jaxbContext = JAXBContext.newInstance(LegalRuleML.class);
		  
		  Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller(); LegalRuleML
		  legalRuleML = (LegalRuleML) jaxbUnmarshaller.unmarshal(selectedFile);
		  
		  JavaToDefeisible.parse(legalRuleML);
	}
}
