package util.evidence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;

public class Text {
	final Logger logger = LoggerFactory.getLogger(getClass());
	private Document document;
	private Status status;
	
	
	public Text(Document pdfDocument, Status statusEvidence) {
		document = pdfDocument;
		status = statusEvidence; 
	}

	public void add(String text) {
		add(text, 10);
	}
	
	public void add(String text, Object...objects) {
		add(String.format(text, objects), 10);
	}
	
	public void addPassed(String text, int size) {
		try {
			Paragraph paragraph = new Paragraph(text,
					FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, BaseColor.GREEN));
			document.add(paragraph);
			status.addPassed();
		} catch (Exception e) {
			logger.error("Falha ao adcionar o texto passed", e.getMessage());
			throw new RuntimeException(String.format("Falha ao adcionar o texto passed", e.getMessage()));
		}
	}
	
	public void addPassed(String text) {
		addPassed(text, 12);
	}
	
	
	public void addFailed(String text, int size) {
		try {
			Paragraph paragraph = new Paragraph(text,
					FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, BaseColor.RED));
			document.add(paragraph);
			status.addErro();
		} catch (Exception e) {
			logger.error("Falha ao adcionar o texto failed", e.getMessage());
			throw new RuntimeException(String.format("Falha ao adcionar o texto failed", e.getMessage()));
		}
	}
	
	public void addFailed(String text) {
		addFailed(text, 12);
	}
	
	public void addFormated(String text, String name, float size, int style, BaseColor color, int spaceBefore, int spaceAfter, int alignment) {
		try {
			
			Font font = FontFactory.getFont(name, size, style, color);
			Paragraph paragraph = new Paragraph(text, font);
			paragraph.setSpacingBefore(spaceBefore);
			paragraph.setSpacingAfter(spaceAfter);
			paragraph.setAlignment(alignment);
			document.add(paragraph);
			
		} catch (Exception e) {
			logger.error("Falha ao adcionar o texto formatado", e.getMessage());
			throw new RuntimeException(String.format("Falha ao adcionar o texto formatado", e.getMessage()));
		}
	}
	
}
