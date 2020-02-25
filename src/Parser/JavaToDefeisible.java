package Parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Model.LegalRuleML;
import Model.Statements;

public class JavaToDefeisible {

	public static void parse(LegalRuleML legalRuleML) throws IOException {
		// TODO parsiraj

		File file = new File("src/x_defeisible/" + legalRuleML.getComment() + ".dfl");
		file.createNewFile();

		BufferedWriter writer = new BufferedWriter(new FileWriter(file));

		for (Statements statement : legalRuleML.getStatements())
			if (statement.getPrescriptiveStatement() != null) {

				// ispisi defeisible pravilo
				writer.write(statement.getPrescriptiveStatement().getKey() + ": => ");

				if (statement.getPrescriptiveStatement().getRule().getThen().getProhibition() != null) {
					// prohibicija
					writer.write(statement.getPrescriptiveStatement().getRule().getThen().getProhibition().getAtom()
							.getRel().getIri().substring(1));
				} else if (statement.getPrescriptiveStatement().getRule().getThen().getPermission() != null) {
					// permisija
					writer.write(statement.getPrescriptiveStatement().getRule().getThen().getPermission().getAtom()
							.getRel().getIri().substring(1));
				}

				writer.newLine();

				// ispisi defeater prethodnog pravila
				if (statement.getPrescriptiveStatement().getRule().getHasStrength() != null) {
					
					writer.write(statement.getPrescriptiveStatement().getKey() + "_def1: ");
					writer.write(statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom().get(0).getVar().get(0) + " -> ");
					writer.write(statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom().get(0).getRel().getIri().substring(1) + "(");
					writer.write(statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom().get(0).getVar().get(1) + ")");
					
					writer.newLine();
					writer.write(statement.getPrescriptiveStatement().getKey() + "_def2: $");
					writer.write(statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom().get(0).getRel().getIri().substring(1) + "(");
					writer.write(statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom().get(0).getVar().get(1) + ")");
					
					if(statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom().get(1).getRel().getIri().equals(":lt"))
						writer.write(" < ");
					else if(statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom().get(1).getRel().getIri().equals(":gt"))
						writer.write(" > ");
					else if(statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom().get(1).getRel().getIri().equals(":eq"))
						writer.write(" = ");
						
					writer.write(statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom().get(1).getInd());
					
					writer.write("$ ~> ");
									
					if (statement.getPrescriptiveStatement().getRule().getThen().getProhibition() != null) {
						// prohibicija
						writer.write("-");
						writer.write(statement.getPrescriptiveStatement().getRule().getThen().getProhibition().getAtom()
								.getRel().getIri().substring(1));
					} else if (statement.getPrescriptiveStatement().getRule().getThen().getPermission() != null) {
						// permisija
						writer.write(statement.getPrescriptiveStatement().getRule().getThen().getPermission().getAtom()
								.getRel().getIri().substring(1));
					}
				}

				System.out.println(statement.getPrescriptiveStatement().getRule());

				writer.newLine();
				writer.newLine();
			}

		writer.close();

	}
}
