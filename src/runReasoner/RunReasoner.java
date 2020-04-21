package runReasoner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.app.utils.TextUtilities;

import Main.Main;
import Model.LegalRuleML;
import parser.JavaToDefeisible;
import spindle.Reasoner;
import spindle.core.dom.Conclusion;
import spindle.core.dom.Literal;
import spindle.core.dom.Mode;
import spindle.core.dom.Rule;
import spindle.core.dom.RuleException;
import spindle.core.dom.RuleType;
import spindle.core.dom.Theory;
import spindle.engine.ReasoningEngineFactory;
import spindle.engine.TheoryNormalizer;
import spindle.io.IOManager;

public class RunReasoner {

	private String fileName;
	private List<String> facts = new ArrayList<>();
	private String literal = "";

	public List<Conclusion> runReasoner() throws Exception {
		addFacts();

		if (!literal.equals(""))
			addLiteral();

		Theory theory = IOManager.getTheory(new File("src/x_defeisible/" + fileName), null);

		TheoryNormalizer theoryNormalizer = ReasoningEngineFactory.getTheoryNormalizer(theory.getTheoryType());
		theoryNormalizer.setTheory(theory);
		theoryNormalizer.removeDefeater();
		theoryNormalizer.removeSuperiority();
		theoryNormalizer.transformTheoryToRegularForm();
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

		return conclusions;
	}

	private void addLiteral() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("src/x_defeisible/" + fileName));

		StringBuilder sb = new StringBuilder();
		String line = br.readLine();

		while (line != null) {
			sb.append(line);
			sb.append(System.lineSeparator());
			line = br.readLine();
		}

		String everything = sb.toString();

		br.close();

		sb = new StringBuilder();

		sb.append("SET @" + Main.literalsFileMap.get(fileName) + " = @VAL(" + literal + ")");

		sb.append(System.lineSeparator());
		sb.append(System.lineSeparator());

		sb.append(everything);

		PrintWriter writer = new PrintWriter("src/x_defeisible/" + fileName);
		writer.print(sb.toString());
		writer.close();
	}

	private void addFacts() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("src/x_defeisible/" + fileName));

		StringBuilder sb = new StringBuilder();
		String line = br.readLine();

		while (line != null) {
			sb.append(line);
			sb.append(System.lineSeparator());
			line = br.readLine();
		}

		String everything = sb.toString();

		br.close();

		sb = new StringBuilder();

		for (String fact : facts) {
			sb.append(">>" + fact);
			sb.append(System.lineSeparator());
		}
		sb.append(System.lineSeparator());
		sb.append(System.lineSeparator());

		sb.append(everything);

		PrintWriter writer = new PrintWriter("src/x_defeisible/" + fileName);
		writer.print(sb.toString());
		writer.close();
	}

	public void transformer(InputStream selectedFile) throws JAXBException, IOException {
		JAXBContext jaxbContext = JAXBContext.newInstance(LegalRuleML.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		LegalRuleML legalRuleML = (LegalRuleML) jaxbUnmarshaller.unmarshal(selectedFile);

		JavaToDefeisible.parse(legalRuleML);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<String> getFacts() {
		return facts;
	}

	public void setFacts(List<String> facts) {
		this.facts = facts;
	}

	public String getLiteral() {
		return literal;
	}

	public void setLiteral(String literal) {
		this.literal = literal;
	}
}
