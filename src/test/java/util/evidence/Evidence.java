package util.evidence;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import javax.swing.text.StyledEditorKit.FontFamilyAction;
import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import selenium.WebDriverFactory;
import util.DataTableUtils;

public class Evidence {

	private static ArrayList<String> styles = new ArrayList<String>();
	private static Document document;
	private static String fileName;
	private static String filePath;
	private static PDFTable pdfTable;
	private static Status status;
	private static Highlight highlight;
	private static Text pdfText;

	private static int counter = 0;
	static JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.getInstance();

	public static PDFTable table() {
		return pdfTable;
	}

	public static Status status() {
		return status;
	}

	public static Text text() {
		return pdfText;
	}

	public static Highlight highlight() {
		return highlight;
	}

	public Evidence(String objective, List<String> stepScenario) {
		document = new Document();
		objective = objective.replaceAll("[\\/:*?\"<>|]", "");
		setFileNameFromTags();

	}

	private void setFileNameFromTags() {
		StringBuilder tags = new StringBuilder();
		for (String tag : DataTableUtils.getAllTags()) {
			if (!tag.contains("ND") && !tag.startsWith("@ID")) {
				tags.append(tag.replace("@", "_"));
			}
		}
	}

	public static void addStepText(String stepText) {
		try {
			Paragraph paragraph = new Paragraph(stepText,
					FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLDITALIC, BaseColor.BLUE));
			paragraph.setSpacingBefore(10);
			paragraph.setSpacingAfter(10);
			document.add(paragraph);
		} catch (Exception e) {
			throw new RuntimeException(String.format("Falha ao adicionar o texto passed: ", e.getMessage()));
		}
	}

	public static void setPathToSaveEvidence(String status) throws Exception {
		try {
			String path = String.format("%s%s_%s.pdf", filePath, fileName, status);
			PdfWriter.getInstance(document, new FileOutputStream(path));
		} catch (Exception e) {
			throw new Exception(String.format("Falha ao adicionar o texto passed: ", e.getMessage()));
		}
	}

	private static void upadateNamePathTo(boolean status) {
		String statusName = status ? "PASSED" : "FAILED";
		String filePathStatus = String.format("%s/%s/", filePath, statusName);
		createEvidenceFolder(filePathStatus);
		String pathOld = String.format("%s%s_%s.pdf", filePath, fileName, "RUN");
		String pathNew = String.format("%s%s_%s.pdf", filePathStatus, fileName, statusName);
		addStatusInfirstPage(pathOld, pathNew, status);
	}

	private static void createEvidenceFolder(String folderPath) {
		File file = new File(folderPath);
		file.mkdir();
	}

	private static void addStatusInfirstPage(String pathOld, String pathNew, boolean status) {
		try {
			PdfReader reader = new PdfReader(pathOld);
			PdfDictionary dictionary = reader.getPageN(1);
			PdfObject obj = dictionary.getDirectObject(PdfName.CONTENTS);
			if (obj instanceof PRStream) {
				PRStream stream = (PRStream) obj;
				byte[] text = PdfReader.getStreamBytes(stream);
				stream.setData(new String(text).replace(status ? "FAILED" : "PASSED", "").getBytes());
			}
			if (Config.isEvidenceGenerationActive()) {
				PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(pathNew));
				stamper.close();
			} else {
				System.out.println("GeraÃ§Ã£o de Evidencia desativada para esta execuÃ§Ã£o");
			}
			reader.close();
			Files.delete(Paths.get(pathOld));
		} catch (Exception e) {
			System.err.println("Falha ao tentar adicionar o status!" + e.getMessage());
		}
	}

	public static void addExternalImage(String path, int align, float ySize, float xSize) {
		try {
			Image image = Image.getInstance(path);
			image.setAlignment(align);
			image.scaleAbsolute(ySize, xSize);
			document.add(image);
		} catch (Exception e) {
			throw new RuntimeException(String.format("Falha ao adicionar o texto passed: ", e.getMessage()));
		}

	}

	public static Image redenScreenToImage(float ySize, float xSize) throws Exception {
		if (WebDriverFactory.getInstance() == null) {
			return null;
		}
		TakesScreenshot ts = (TakesScreenshot) WebDriverFactory.getInstance();
		byte[] imagem = ts.getScreenshotAs(OutputType.BYTES);
		Image image = Image.getInstance(imagem);
		image.scaleToFit(ySize, xSize);
		return image;
	}

	public static void addJsonPretty(JsonElement json) {
		pdfText.add(new GsonBuilder().setPrettyPrinting().create().toJson(json), 10);
	}

	public static void printScreen() {
		printScreen("");
	}

	private static void printScreen(String text) {
		try {
			Image image = redenScreenToImage(520f, 520f);
			if (image != null) {
				document.add(image);
			}
			if (text != null) {
				addText(text);
			}
			highlight.clear();
			document.newPage();
		} catch (Exception e) {
			throw new RuntimeException(String.format("Falha ao adicionar o printScreen e texto: ", e.getMessage()));
		}

	}

	public static void printScreen(WebElement... listElements) {
		document.newPage();
		Evidence.highlight.include(listElements);
		printScreen();
		Evidence.highlight.remove(listElements);
		highlight.clear();
	}

	public static void printScreen(List<WebElement> listElements) {
		document.newPage();
		Evidence.highlight.include(listElements);
		printScreen();
		Evidence.highlight.remove(listElements);
		highlight.clear();
	}

	public static void addNewPage() {
		document.newPage();
	}

	public static void addNewPage(String text) {
		addNewPage();
		pdfText.add(text, 12);
	}

	public static boolean isErrors() {
		return getErrors() > 0;
	}

	public static int getErrors() {
		return status.getTotalErrors();
	}

	public static void setErrors(int errors) {
		status.clear();
	}

	public static void addText(String text, Object... args) {
		pdfText.add(text, 10);
	}

	public static void addText(String text, int size) {
		pdfText.add(text, size);
	}

	public static void addPassedText(String text) {
		pdfText.addPassed(text);
	}

	public static void addPassedText(String text, int size) {
		pdfText.addPassed(text, size);
	}

	public static void addFailedText(String text) {
		pdfText.addFailed(text);
	}

	public static void addFailedText(String text, int size) {
		pdfText.addFailed(text, size);
	}

	public static void addFormatedText(String text, String name, float size, int style, BaseColor color,
			int spaceBefore, int spaceAfter, int align) {
		pdfText.addFormated(text, name, size, style, color, spaceBefore, spaceAfter, align);
	}

	public static String getListMessagesErrors() {
		return Status.getMessagesErrors();
	}

	public static void finishEvidence() {
		Boolean resultStatus = false;
		String message = "";

		try {
			if (status.getTotalErrors() > 0) {
				message = "Total de erros no no cenÃ¡rio: " + status.getTotalErrors();
				printScreen();
				pdfText.addFailed(message);
			} else if (status.getTotalPasseds() != 0) {
				pdfText.addPassed("\nCaso de teste concluÃ­do com sucesso!");
				resultStatus = true;

			} else {
				printScreen();
				message = "NÃ£o foi adicionando nenhuma validaÃ§Ã£o de status passed, teste falhou!";
				pdfText.addFailed(message);
				throw new Exception(message);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			document.close();
			upadateNamePathTo(resultStatus);
			status.clear();
		}
	}

	public static void insertHiglight(List<WebElement> listElements) {
		JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.getInstance();
		listElements.forEach(webElement -> {
			styles.add(webElement.getAttribute("style"));
			js.executeScript("arguments[0].setAttribute('style', 'border: 3px solid green;"
					+ webElement.getAttribute("style") + "');", webElement);

		});

	}

	public static void insertHiglight(WebElement... listElements) {
		for (WebElement webElement : listElements) {
			styles.add(webElement.getAttribute("style"));
			js.executeScript("arguments[0].setAttribute('style', 'border: 3px solid green;"
					+ webElement.getAttribute("style") + "');", webElement);
		}

	}

	public static void removeHiglight(List<WebElement> listElements) {
		counter = 0;
		listElements.forEach(webElement -> {
			styles.add(webElement.getAttribute("style"));
			js.executeScript("arguments[0].setAttribute('style', '" + styles.get(counter) + "');", webElement);
			counter++;

		});

		styles.clear();

	}

	public static void removeHiglight(WebElement... listElements) {
		counter = 0;
		for (WebElement webElement : listElements) {
			styles.add(webElement.getAttribute("style"));
			js.executeScript("arguments[0].setAttribute('style', '" + styles.get(counter) + "');", webElement);
			counter++;
		}
		styles.clear();
	}

}
