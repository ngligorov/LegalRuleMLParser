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
		List<String> partialRules = new ArrayList<>();
		List<String> writtenRules = new ArrayList<>();

		// za ispis pocetnog pravila, svi smeju sve
		for (Statements statement : legalRuleML.getStatements())
			if (statement.getPrescriptiveStatement() != null) {
				// ispisi defeisible pravilo

				if (statement.getPrescriptiveStatement().getRule().getThen().getProhibition() != null) {
					// prohibicija
					if (!writtenRules.contains(statement.getPrescriptiveStatement().getRule().getThen().getProhibition()
							.getAtom().getRel().getIri().substring(1))) {
						writer.write("rule: => ");
						writer.write(statement.getPrescriptiveStatement().getRule().getThen().getProhibition().getAtom()
								.getRel().getIri().substring(1));

						writtenRules.add(statement.getPrescriptiveStatement().getRule().getThen().getProhibition()
								.getAtom().getRel().getIri().substring(1));
						writer.newLine();
					}
				} else if (statement.getPrescriptiveStatement().getRule().getThen().getPermission() != null) {
					// permisija
					if (!writtenRules.contains(statement.getPrescriptiveStatement().getRule().getThen().getPermission()
							.getAtom().getRel().getIri().substring(1))) {
						writer.write("rule: => ");
						writer.write(statement.getPrescriptiveStatement().getRule().getThen().getPermission().getAtom()
								.getRel().getIri().substring(1));

						writtenRules.add(statement.getPrescriptiveStatement().getRule().getThen().getPermission()
								.getAtom().getRel().getIri().substring(1));
						writer.newLine();
					}
				}

				
			}

		// defeateri pocetnog pravila
		for (Statements statement : legalRuleML.getStatements())
			if (statement.getPrescriptiveStatement() != null) {

				// ispisi defeater prethodnog pravila
				// AND u lrml
				if (statement.getPrescriptiveStatement().getRule().getHasStrength() != null) {

					if (statement.getPrescriptiveStatement().getRule().getIf().getAnd().getOr() == null) {
						writer.newLine();

						if (statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom().size() != 1) {

							writer.write(statement.getPrescriptiveStatement().getKey() + ": $@");
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
						} else if (statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom()
								.size() == 1) {

							writer.write(statement.getPrescriptiveStatement().getKey() + ": ");
							writer.write(statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom()
									.get(0).getInd());
						}
					}
					// vise slucajeva
					// OR u lrml
					if (statement.getPrescriptiveStatement().getRule().getIf().getAnd().getOr() != null) {
						int i = 1;

						decomposedRules.add(statement.getPrescriptiveStatement().getKey());

						writer.newLine();

						for (Atom atom : statement.getPrescriptiveStatement().getRule().getIf().getAnd().getOr()
								.getAtom()) {

							writer.newLine();

							partialRules.add(statement.getPrescriptiveStatement().getKey() + "_" + i);

							if (statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom().size() != 1) {
								writer.write(statement.getPrescriptiveStatement().getKey() + "_" + i + ": $@");
								writer.write(statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom()
										.get(0).getRel().getIri().substring(1));

								if (statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom().get(1)
										.getRel().getIri().equals(":lt"))
									writer.write(" < ");
								else if (statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom()
										.get(1).getRel().getIri().equals(":gt"))
									writer.write(" > ");
								else if (statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom()
										.get(1).getRel().getIri().equals(":eq"))
									writer.write(" = ");

								writer.write(statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom()
										.get(1).getInd());

								writer.write("$");
							} else if (statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom()
									.size() == 1) {
								writer.write(statement.getPrescriptiveStatement().getKey() + "_" + i + ": ");
								writer.write(statement.getPrescriptiveStatement().getRule().getIf().getAnd().getAtom()
										.get(0).getInd());
							}
							i++;

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

					// TODO skloni ovo kad zavrsis
					System.out.println(statement.getPrescriptiveStatement().getRule());

				}

			}

		writer.newLine();
		writer.newLine();

		// superiornost pravila
		for (Statements statement : legalRuleML.getStatements())
			if (statement.getOverrideStatement() != null)
				// celo pravilo
				if (!decomposedRules.contains(statement.getOverrideStatement().getOverride().getOver().substring(1))
						&& !decomposedRules
								.contains(statement.getOverrideStatement().getOverride().getUnder().substring(1))) {

					writer.write(statement.getOverrideStatement().getOverride().getOver().substring(1) + " > "
							+ legalRuleML.getStatements().get(0).getOverrideStatement().getOverride().getUnder()
									.substring(1));

				} else {
					// dekomponovano pravilo
					String over = statement.getOverrideStatement().getOverride().getOver().substring(1);
					String under = statement.getOverrideStatement().getOverride().getUnder().substring(1);

					List<String> partialOvers = new ArrayList<>();
					List<String> partialUnders = new ArrayList<>();

					for (String partialRule : partialRules) {
						if (partialRule.contains(over))
							partialOvers.add(partialRule);
						else if (partialRule.contains(under))
							partialUnders.add(partialRule);
					}

					for (String partialOver : partialOvers)
						for (String partialUnder : partialUnders) {

							writer.newLine();
							writer.write(partialOver + " > " + partialUnder);
						}
				}

		writer.close();

	}
}
