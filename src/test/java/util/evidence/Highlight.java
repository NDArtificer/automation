package util.evidence;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import selenium.WebDriverFactory;

public class Highlight {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private Map<WebElement, String> styles;

	public Highlight() {
		styles = new HashMap<WebElement, String>();
	}

	private void include(WebElement webElement) {
		JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.getInstance();
		String id = "";

		try {
			id = webElement.getAttribute("id");
			if (styles.containsKey(webElement)) {
				return;
			}

			styles.put(webElement, webElement.getAttribute("style"));
			String style = webElement.getAttribute("style");
			js.executeAsyncScript("arguments[0].setAttribute('style', 'border: 3px solid green;" + style + "' );",
					webElement);
		} catch (Exception e) {
			logger.error("não foi possível incluir o highlight para o elemento com o ID: ", id);
		}
	}

	public void include(List<WebElement> webElements) {
		webElements.forEach(webElement -> {
			include(webElement);
		});
	}

	public void include(WebElement... webElements) {
		include(Arrays.asList(webElements));
	}

	private void remove(WebElement webElement) {
		JavascriptExecutor js = (JavascriptExecutor) WebDriverFactory.getInstance();
		String id = "";

		try {
			id = webElement.getAttribute("id");
			if (styles.containsKey(webElement)) {
				return;
			}
			js.executeAsyncScript("arguments[0].setAttribute('style', '" + styles.get(webElement) + "' );", webElement);
			styles.remove(webElement);
		} catch (Exception e) {
			logger.error("não foi possível remover o highlight para o elemento com o ID: ", id);
		}
	}

	public void remove(List<WebElement> webElements) {
		webElements.forEach(webElement -> {
			remove(webElement);
		});
	}

	public void remove(WebElement... webElements) {
		remove(Arrays.asList(webElements));
	}

	public void clear() {
		try {
			styles.keySet().forEach(style -> {
				remove(style);
			});
			styles.clear();
		} catch (Exception e) {
			logger.error("Falha ao limpar a lista de WebElements!");
		}
	}
}
