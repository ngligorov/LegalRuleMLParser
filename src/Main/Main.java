package Main;


import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import Model.LegalRuleML;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		File file = new File("src/xml/dadsa.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(LegalRuleML.class);
		
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		LegalRuleML legalRuleML = (LegalRuleML) jaxbUnmarshaller.unmarshal(file);
		
		System.out.println(legalRuleML.toString());
	}

}
