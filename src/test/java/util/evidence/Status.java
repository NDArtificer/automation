package util.evidence;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Status {

	final Logger logger = LoggerFactory.getLogger(getClass());
	private static List<String> messageErrors = new ArrayList<>();
	private int errors, passeds;

	public int getTotalErrors() {
		return errors;
	}

	public int getTotalPasseds() {
		return passeds;
	}

	public void addErro() {
		errors++;
	}

	public void addPassed() {
		passeds++;
	}

	public static String getMessagesErrors() {
		StringBuffer sb = new StringBuffer();

		messageErrors.forEach(msg -> {
			sb.append(msg);
		});

		return sb.toString();
	}

	public String getMessagesErrorsToHTML() {
		if (messageErrors.isEmpty()) {
			return "";
		}
		;
		StringBuilder sb = new StringBuilder();
		messageErrors.forEach(msg -> {
			sb.append(msg + "<br>");
		});
		sb.setLength(sb.length() - 4);
		return sb.toString();

	}

	public void clear() {
		messageErrors.clear();
		errors = 0;
		passeds = 0;
	}

}
