package runner;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		snippets = SnippetType.CAMELCASE,
		features = { "src/test/resources/Features/.."},
		glue = "artificeSteps", 
		plugin = {"pretty","html:target/report-html","json:target/report.json"},
		dryRun = false,
		strict = false,
		monochrome = true,
		tags= {"@Aula-30, @Aula-31, @Aula-32"}
		)
public class RunTest {

		@BeforeClass
		public static void reset() throws InterruptedException {
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\Denilson\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe");
			WebDriver driver = new ChromeDriver();
			driver.get("https://srbarriga.herokuapp.com");
			Thread.sleep(2000);
			
			driver.findElement(By.id("email")).sendKeys("artificer@hotmail.com");
			Thread.sleep(2000);
			
			driver.findElement(By.id("senha")).sendKeys("a");
			Thread.sleep(2000);
			
			driver.findElement(By.tagName("button")).click();
			
			driver.findElement(By.linkText("reset")).click();
			Thread.sleep(2000);
			   
			driver.quit();
		}
}
