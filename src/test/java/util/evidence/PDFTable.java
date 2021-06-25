package util.evidence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.gson.JsonArray;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;

public class PDFTable {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private PdfPTable table;
	private Document document;
	private List<String> removeColumns = new ArrayList<>();
	boolean tableCreate;
	int i;

	public PDFTable(Document pdfDocument) {
		document = pdfDocument;
	}

	public void include(String title, String... headerColumns) {
		int[] proportion = null;
		include(title, proportion, headerColumns);
	}

	public void include(String title, int[] proportion, String... headerColumns) {
		close();
		Evidence.text().add("\n");
		;
		table = new PdfPTable(1);
		table.setKeepTogether(true);
		table.getDefaultCell().setBorder(0);
		table.setFooterRows(0);
		table.setWidthPercentage(100f);
		PdfPTable innerTable = new PdfPTable(headerColumns.length);
		table.addCell(title);

		for (String column : headerColumns) {
			Phrase phrase = new Phrase(column,
					FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.BLACK));
			innerTable.addCell(phrase);
		}
		table.addCell(innerTable);
	}

	public void include(String title, JsonArray jsonArray, String... removeColumns) {
		include(title, jsonArray, null, removeColumns);
	}

	public void include(String title, JsonArray jsonArray, int[] proportion, String... removeColumnName) {
		tableCreate = false;
		removeColumns = Arrays.asList(removeColumnName);

		jsonArray.forEach(jsonElement -> {
			String[] headerColumnsName = new String[jsonElement.getAsJsonObject().keySet().size()
					- removeColumns.size()];
			i = 0;
			jsonElement.getAsJsonObject().keySet().forEach(key -> {
				if (isValidColumn(key)) {
					headerColumnsName[i++] = key;
				}
			});

			include(title, proportion, headerColumnsName);
			tableCreate = true;

		});

		if (tableCreate) {
			jsonArray.forEach(jsonElement -> {
				String[] fields = new String[jsonElement.getAsJsonObject().keySet().size() - removeColumns.size()];
				i = 0;
				jsonElement.getAsJsonObject().keySet().forEach(key -> {
					if (isValidColumn(key) && i < fields.length) {
						try {
							fields[i++] = jsonElement.getAsJsonObject().get(key).isJsonNull() ? "NULL"
									: jsonElement.getAsJsonObject().get(key).getAsString();
						} catch (ArrayIndexOutOfBoundsException e) {
							e.printStackTrace();
						}
					}
				});
				addRow(proportion, fields);
			});
		}
		close();
		removeColumnName = null;
	}

	public void addRow(String fieldName, String value1, String value2, boolean validate) {
		PdfPTable innerTable = new PdfPTable(4);
		innerTable.getDefaultCell().setBorder(2);
		innerTable.addCell(getPhraseWithStyle(fieldName));
		innerTable.addCell(getPhraseWithStyle(value1));
		innerTable.addCell(getPhraseWithStyle(value2));
		Phrase phrase = new Phrase();

		if (!validate) {
			Evidence.status().addErro();
			phrase.setFont(FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.RED));
		} else {
			Evidence.status().addPassed();
			phrase.setFont(FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.GREEN));
		}
		phrase.add(validate ? "PASSED" : "FAILED");
		innerTable.addCell(phrase);
		table.addCell(innerTable);
	}

	public void addRow(String... strings) {
		PdfPTable innerTable = new PdfPTable(strings.length);
		innerTable.getDefaultCell().setBorder(2);
		for (int i = 0; i < strings.length; i++) {
			innerTable.addCell(getPhraseWithStyle(strings[i]));
		}
		table.addCell(innerTable);
	}

	public void addRow(int[] proportion, String... strings) {
		PdfPTable innerTable = new PdfPTable(strings.length);
		innerTable.getDefaultCell().setBorder(2);
		setWidthsCells(innerTable, proportion);
		for (String string : strings) {
			innerTable.addCell(getPhraseWithStyle(string));
		}
		table.addCell(innerTable);
	}

	private Phrase getPhraseWithStyle(String string) {
		float fontSize = 7;
		Phrase phrase = new Phrase();
		switch (Strings.nullToEmpty(string)) {

		case "PASSED":
			phrase.setFont(FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD, BaseColor.GREEN));
			break;

		case "FAILED":
			phrase.setFont(FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD, BaseColor.RED));
			break;

		default:
			phrase.setFont(FontFactory.getFont(FontFactory.HELVETICA, fontSize, Font.BOLD, BaseColor.BLACK));
		}
		phrase.add(string);
		return phrase;
	}

	private void setWidthsCells(PdfPTable innerTable, int[] proportion) {
		try {
			if (proportion == null) {
				return;
			}
			table.setWidths(proportion);
		} catch (Exception e) {
			logger.error("falha ao definir o tamanho da celula!", e.getMessage());
		}
	}

	private boolean isValidColumn(String key) {
		for (String columns : removeColumns) {
			if (key.toUpperCase().trim().equals(columns.toUpperCase().trim())) {
				return false;
			}
		}
		return true;
	}

	private void close() {
		try {
			if (table != null) {
				document.add(table);
				table = null;
			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("falha ao incluir a table no documento PDF", e.getMessage()));
		}
	}

}
