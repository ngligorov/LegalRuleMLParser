package Main;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.app.utils.Utilities.ProcessStatus;

import Model.LegalRuleML;
import Parser.JavaToDefeisible;
import spindle.Reasoner;
import spindle.core.ReasonerBase;
import spindle.core.ReasonerException;
import spindle.core.dom.Conclusion;
import spindle.core.dom.ConclusionType;
import spindle.core.dom.Literal;
import spindle.core.dom.Theory;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		File file = new File("src/xml/testPrvi.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(LegalRuleML.class);
		
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		LegalRuleML legalRuleML = (LegalRuleML) jaxbUnmarshaller.unmarshal(file);
		
		JavaToDefeisible.parse(legalRuleML);
		
		BufferedReader br = new BufferedReader(new FileReader("src/x_defeisible/test.dfl"));
		
		Reasoner reasoner = new Reasoner();
		
		ArrayList<String> list = new ArrayList<>();
		Map<spindle.core.dom.Literal, Map<spindle.core.dom.ConclusionType, spindle.core.dom.Conclusion>> map = new HashMap<>();
		
		String st;
		while((st = br.readLine()) != null)
		{
			list.add(st);
		}
		
		Object[] rulesObject = list.toArray();
		String[] rules = new String[list.size()];
		int i = 0;
		for(Object o : rulesObject) {
            String s = (String) o;
            rules[i++] = s;
		}
		
		System.out.println(rules[1]);
		
		//????????????????
		//reasoner.loadTheory(rules);
		//System.out.println(reasoner.transformTheoryToRegularForm());

		//map = reasoner.getConclusions();
		
	}

}
