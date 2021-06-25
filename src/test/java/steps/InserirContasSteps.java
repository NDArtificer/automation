package steps;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

public class InserirContasSteps {
	
	private WebDriver driver;
	

	@Dado("^que tenha acessado a aplicacao$")
	public void queTenhaAcessadoAAplicacao() throws Throwable {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Denilson\\Downloads\\chromedriver_win32 (1)\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://srbarriga.herokuapp.com");
		Thread.sleep(2000);
	}

	@Quando("^informo o usuario \"([^\"]*)\"$")
	public void informoOUsuario(String arg1) throws Throwable {
		driver.findElement(By.id("email")).sendKeys(arg1);
		Thread.sleep(2000);
	}

	@Quando("^a senha \"([^\"]*)\"$")
	public void aSenha(String arg1) throws Throwable {
		driver.findElement(By.id("senha")).sendKeys(arg1);
		Thread.sleep(2000);
	}
	
	@Quando("^seleciono entrar$")
	public void selecionoEntrar() throws Throwable {
		  driver.findElement(By.tagName("button")).click();
	}

	@Entao("^visualizo a pagina inicial$")
	public void visualizoAPaginaInicial() throws Throwable {
		 String texto = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
		 Assert.assertEquals("Bem vindo, artificer!", texto);
		 Thread.sleep(2000);
	}

	@Quando("^seleciono Contas$")
	public void selecionoContas() throws Throwable {
	   driver.findElement(By.linkText("Contas")).click();
	   Thread.sleep(2000);
	}

	@Quando("^seleciono adicionar$")
	public void selecionoAdicionar() throws Throwable {
		driver.findElement(By.linkText("Adicionar")).click();
		Thread.sleep(2000);
	}

	@Quando("^informo a conta \"([^\"]*)\"$")
	public void informoAConta(String arg1) throws Throwable {
		driver.findElement(By.id("nome")).sendKeys(arg1);
		Thread.sleep(2000);
	}

	@Quando("^seleciono salvar$")
	public void selecionoSalvar() throws Throwable {
		  driver.findElement(By.tagName("button")).click();
	}

	@Entao("^a conta e inserida com sucesso\\.$")
	public void aContaInseridaComSucesso() throws Throwable {
		String texto = driver.findElement(By.xpath("//div[@class='alert alert-success']")).getText();
		 Assert.assertEquals("Conta adicionada com sucesso!", texto);
		 Thread.sleep(2000);
	}
	
	@Entao("^sou notificado que o nome da conta e obrigatorio$")
	public void souNotificadoQueONomeDaContaObrigatorio() throws Throwable {
		String texto = driver.findElement(By.xpath("//div[@class='alert alert-danger']")).getText();
		Assert.assertEquals("Informe o nome da conta", texto);
		Thread.sleep(2000);
	}

	@Entao("^sou notificado que o nome da conta ja existe$")
	public void souNotificadoQueONomeDaContaJaExiste() throws Throwable {
		String texto = driver.findElement(By.xpath("//div[@class='alert alert-danger']")).getText();
		Assert.assertEquals("J� existe uma conta com esse nome!", texto);
		Thread.sleep(2000);
	}
	
	@Entao("^recebo a mensagem \"([^\"]*)\"$")
	public void receboAMensagem(String mensagem) throws Throwable {
		String texto = driver.findElement(By.xpath("//div[starts-with(@class, 'alert alert-')]")).getText();
		Assert.assertEquals(mensagem, texto);
		Thread.sleep(2000);
	}
	
	
	@After(order = 1)
	public void screenShot(Scenario cenario) {
		
		File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(file, new File("target/screenShot/"+cenario.getSourceTagNames()+".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	@After(order = 0)
	public void closeBrowser() {
		driver.quit();
	} 
	
}