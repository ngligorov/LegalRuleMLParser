package Parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Model.Atom;
import Model.LegalRuleML;
import Model.Statements;

public class JavaToDefeisible {

	public static void parse(LegalRuleML legalRuleML) throws IOException {

		File file = new File("src/x_defeisible/" + legalRuleML.getComment() + ".dfl");
		file.createNewFile();

		BufferedWriter writer = new BufferedWriter(new FileWriter(file));

		List<String> decomposedRules = new ArrayList<>();

		// za ispis pocetnog pravila, svi smeju sve
		for (Statements statement : legalRuleML.getStatements())
			if (statement.getPrescriptiveStatement() != null) {
				// ispisi defeisible pravilo
				writer.write("rule: => ");

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

				break;
			}

		// defeateri pocetnog pravila
		for (Statements statement : legalRuleML.getStatements())
			if (statement.getPrescriptiveStatement() != null) {

				// ispisi defeater prethodnog pravila
				// AND u lrml
				if (statement.getPrescriptiveStatement().getRule().getHasStrength() != null) {

					if (statement.getPrescriptiveStatement().getRule().getIf().getAnd().getOr() == null) {

						writer.newLine();
						writer.write(statement.getPrescriptiveStatement().getKey() + ": $@");
						writer.write(statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom().get(0)
								.getRel().getIri().substring(1));

						if (statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom().get(1).getRel()
								.getIri().equals(":lt"))
							writer.write(" < ");
						else if (statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom().get(1)
								.getRel().getIri().equals(":gt"))
							writer.write(" > ");
						else if (statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom().get(1)
								.getRel().getIri().equals(":eq"))
							writer.write(" = ");

						writer.write(statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom().get(1)
								.getInd());

						writer.write("$");
					}
					// vise slucajeva
					// OR u lrml
					if (statement.getPrescriptiveStatement().getRule().getIf().getAnd().getOr() != null) {
						decomposedRules.add(statement.getPrescriptiveStatement().getKey());

						int i = 0;

						writer.newLine();

						for (Atom atom : statement.getPrescriptiveStatement().getRule().getIf().getAnd().getOr()
								.getAtom()) {
							i++;
							writer.newLine();

							writer.write(statement.getPrescriptiveStatement().getKey() + i + ": $@");
							writer.write(statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom()
									.get(0).getRel().getIri().substring(1));

							if (statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom().get(1)
									.getRel().getIri().equals(":lt"))
								writer.write(" < ");
							else if (statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom().get(1)
									.getRel().getIri().equals(":gt"))
								writer.write(" > ");
							else if (statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom().get(1)
									.getRel().getIri().equals(":eq"))
								writer.write(" = ");

							writer.write(statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom()
									.get(1).getInd());

							writer.write("$");

							writer.write(", ");
							writer.write(atom.getInd());

							writer.write(" ~> ");

							if (statement.getPrescriptiveStatement().getRule().getThen().getProhibition() != null) {
								// prohibicija
								writer.write("-");
								writer.write(statement.getPrescriptiveStatement().getRule().getThen().getProhibition()
										.getAtom().getRel().getIri().substring(1));
							} else if (statement.getPrescriptiveStatement().getRule().getThen()
									.getPermission() != null) {
								// permisija
								writer.write(statement.getPrescriptiveStatement().getRule().getThen().getPermission()
										.getAtom().getRel().getIri().substring(1));
							}

							// ispisi superiornost dekomponovanog pravila
							for (Statements s : legalRuleML.getStatements())
								if (s.getOverrideStatement() != null)
									if (s.getOverrideStatement().getOverride().getOver().substring(1)
											.equals(statement.getPrescriptiveStatement().getKey())) {

										writer.newLine();

										writer.write(s.getOverrideStatement().getOverride().getOver().substring(1) + i
												+ " > " + legalRuleML.getStatements().get(0).getOverrideStatement()
														.getOverride().getUnder().substring(1));

										writer.newLine();
									} else if (s.getOverrideStatement().getOverride().getUnder().substring(1)
											.equals(statement.getPrescriptiveStatement().getKey())) {

										writer.newLine();

										writer.write(s.getOverrideStatement().getOverride().getOver().substring(1)
												+ " > " + legalRuleML.getStatements().get(0).getOverrideStatement()
														.getOverride().getUnder().substring(1)
												+ i);

										writer.newLine();
									}

						}
					} else {
						writer.write(" ~> ");

						if (statement.getPrescriptiveStatement().getRule().getThen().getProhibition() != null) {
							// prohibicija
							writer.write("-");
							writer.write(statement.getPrescriptiveStatement().getRule().getThen().getProhibition()
									.getAtom().getRel().getIri().substring(1));
						} else if (statement.getPrescriptiveStatement().getRule().getThen().getPermission() != null) {
							// permisija
							writer.write(statement.getPrescriptiveStatement().getRule().getThen().getPermission()
									.getAtom().getRel().getIri().substring(1));
						}
					}

					// /OR

					System.out.println(statement.getPrescriptiveStatement().getRule());

				}

			}

		writer.newLine();
		writer.newLine();

		// superiornost pravila
		for (Statements statement : legalRuleML.getStatements())
			if (statement.getOverrideStatement() != null)
				if (!decomposedRules.contains(statement.getOverrideStatement().getOverride().getOver().substring(1))
						&& !decomposedRules
								.contains(statement.getOverrideStatement().getOverride().getUnder().substring(1)))

					writer.write(statement.getOverrideStatement().getOverride().getOver().substring(1) + " > "
							+ legalRuleML.getStatements().get(0).getOverrideStatement().getOverride().getUnder()
									.substring(1));

		writer.close();

	}
}
