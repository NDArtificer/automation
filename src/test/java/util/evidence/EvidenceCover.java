package util.evidence;

import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;

public class EvidenceCover {

	private Document documentCover;

	public EvidenceCover(Document document) {
		documentCover = document;
	}

	public Document getCover(String objective, List<String> stepScenario) {
		insertObjective(objective);
		insertStatus();
		insertIdJira();
		insertDateNow();
		insertIpClient();
		insertWindowsUser();
		insertPcName();
		insertSteps(stepScenario);
		return documentCover;
	}

	private void insertSteps(List<String> stepScenario) {
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLDITALIC, BaseColor.BLUE);
		documentCover.newPage();
		int i = 0;
		try {
			Paragraph title = new Paragraph("STEPS: ",
					FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLDITALIC, BaseColor.BLUE));
			title.setSpacingAfter(10);
			documentCover.add(title);
			for (String step : stepScenario) {
				documentCover.add(new Paragraph(String.format("%s - %s", i++, step), font));
			}
		} catch (DocumentException e) {
			try {
				throw new Exception("Falha ao adicionar texto " + e.getMessage());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private void insertObjective(String objective) {
		// TODO Auto-generated method stub

	}

	private void insertStatus() {
		// TODO Auto-generated method stub

	}

	private void insertIdJira() {
		// TODO Auto-generated method stub

	}

	private void insertDateNow() {
		// TODO Auto-generated method stub

	}

	private void insertIpClient() {
		// TODO Auto-generated method stub

	}

	private void insertWindowsUser() {
		// TODO Auto-generated method stub

	}

	private void insertPcName() {
		// TODO Auto-generated method stub

	}
	
	
	public void addText(String text) throws Exception {
		try {
			Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLDITALIC, BaseColor.BLUE);
			documentCover.add(new Paragraph(text, font));
		} catch (DocumentException e) {
			throw new Exception(e.getMessage());
		}
	}

}
