package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
	private WebDriver driver;
	//submit-login- aqui sao os atributos necessarios para acessar a pag da empresa
//	private By email = By.name("usuario.login");
//	private By password = By.name("usuario.senha");
//	private By botaoSignIn = By.id("form_0");
//	private By dashBoard = By.id("fas fa-2x fa-tachometer-alt");
	
	private By email = By.name("email");
	private By password = By.name("password");
	private By botaoSignIn = By.id("submit-login");
	//private By dashBoard = By.id("fas fa-2x fa-tachometer-alt");
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void preencherEmail(String texto) {
		//metodo que preenche o login com email
		driver.findElement(email).sendKeys(texto);
	}
	
	public void preencherPassword(String texto) {
		driver.findElement(password).sendKeys(texto);
	}
	
	public void clicarBotaoSignIn() {
		driver.findElement(botaoSignIn).click();
	}
//	public void DashBoard() {
//		driver.findElement(dashBoard).click();
//	}
	
}
