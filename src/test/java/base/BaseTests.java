package base;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.google.common.io.Files;

import testes.HomePage;

public class BaseTests {
	
	private static WebDriver driver;
	protected HomePage homePage;
	
	@BeforeAll
	//esse metodo vai ser executado antes de todas as classes, ou seja, tem prioridade
	public static void inicializar() {
		System.setProperty("webdriver.chrome.driver", "C:\\webdriver\\chromedriver\\108\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
//	//carregarPaginaInicial https://marcelodebittencourt.com/demoprestashop/
	@BeforeEach
	public void carregarPaginaInicial() {
		driver.manage().window().maximize();
		//driver.get("https://patrol.audax.mobi/app/home/logout");
		driver.get("https://marcelodebittencourt.com/demoprestashop/");
		homePage = new HomePage(driver);
	}
	
	public void capturarTela(String nomeTeste, String resultado) {
		var camera = (TakesScreenshot) driver;
		File capturaDeTela = camera.getScreenshotAs(OutputType.FILE);
		try {
			Files.move(capturaDeTela, new File("resources/screenshots/" + nomeTeste + "_" + resultado + ".png" ));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	
//	//min 26:17
//	
	@AfterAll
	public static void finalizar() {
		driver.quit();
	}
}
