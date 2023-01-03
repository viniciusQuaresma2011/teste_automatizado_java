package pages;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ModalProdutoPage {
	private WebDriver driver;
	//-  By.xpath("/html/body/div[7]/div/div/div[1]/h4");
//	private By mensagemProdutoAdicionado = By.id("myModalLabel");
//	private By descricaoProduto = By.className("product-name");
//	private By precoProduto = By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[1]/div/div[2]/p");
//	private By tamanhoDoProduto = By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[1]/div/div[2]/span[1]");
//	private By corDoProduto = By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[1]/div/div[2]/span[2]");
//	private By listaValoresInformados = By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[1]/div/div[2]/span[1]/strong");
//	private By subtotal = By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[2]/div/p[2]/span[2]");
//	private By quantidadeDoProduto = By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[1]/div/div[2]/span[3]");
	
	//By.xpath("//*[@id=\"myModalLabel\"]/text()");
	private By mensagemProdutoAdicionado = By.id("myModalLabel");
	
	private By descricaoProduto = By.className("product-name");
	
	private By precoProduto = By.cssSelector("div.modal-body p.product-price");
	
	private By listaValoresInformados = By.cssSelector("div.divide-right .col-md-6:nth-child(2) span strong");
	
	private By subTotal = By.cssSelector(".cart-content p:nth-child(2) span.value");
	
	
	// .cart-content-btn a //*[@id="blockcart-modal"]/div/div/div[2]/div/div[2]/div/div/a
	private By botaoProceedToCheckout = By.cssSelector(".cart-content-btn a");
	
	private By botaoProceedToCheckoutSegundo = By.cssSelector(".text-sm-center a");
	
	public ModalProdutoPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String obterMensagemProdutoAdicionado() {
		FluentWait wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(5))
				.pollingEvery(Duration.ofSeconds(6))
				.ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(mensagemProdutoAdicionado));
		return driver.findElement(mensagemProdutoAdicionado).getText();
			
	}
	
	public String obterDescricaoProduto() {
		return driver.findElement(descricaoProduto).getText();
	}
	public String obterPrecoProduto() {
		return driver.findElement(precoProduto).getText();
	}
	
	public String obterTamanhoProduto() {
		return driver.findElements(listaValoresInformados).get(0).getText();	
	}
	public String obterCorProduto() {
		if(driver.findElements(listaValoresInformados).size() == 3)
			return driver.findElements(listaValoresInformados).get(1).getText();
		else
			return "N/A";
	}
	public String obterQuantidadeProduto() {
		if(driver.findElements(listaValoresInformados).size() == 3)
		return driver.findElements(listaValoresInformados).get(2).getText();
		else
			return driver.findElements(listaValoresInformados).get(1).getText();
	}
	public String obterSubtotal() {
		return driver.findElement(subTotal).getText();
	}
//	
	public CarrinhoPage clicarBotaoProceedToCheckout() throws InterruptedException {
		driver.findElement(botaoProceedToCheckout).click();
		Thread.sleep(5000);
		return new CarrinhoPage(driver);
	}

	public CarrinhoPage clicarBotaoProceedToCheckoutSegundo () throws InterruptedException {
		driver.findElement(botaoProceedToCheckoutSegundo).click();
		Thread.sleep(5000);
		return new CarrinhoPage(driver);
	}}


	
