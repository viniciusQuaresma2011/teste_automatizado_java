package testes;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pages.LoginPage;
import pages.ProdutoPage;

public class HomePage {
	
	
	private WebDriver driver;
	
	List<WebElement> listaProdutos = new ArrayList();
	//#_desktop_user_info span.hidden-sm-down 
	private By textoProdutosNoCarrinho = By.className("cart-products-count");
	private By produtos = By.className("product-description");
	private By descricoesDosProdutos = By.cssSelector(".product-description a");
	private By precoDosProdutos = By.className("price");
	private By botaoSignIn = By.xpath("//*[@id=\"_desktop_user_info\"]/div/a/span");
	//verifica de o usuario est� logado no site (empresa)
	//private By usuarioLogado = By.cssSelector("p.name");
	private By usuarioLogado = By.cssSelector("#_desktop_user_info span.hidden-sm-down");
	private By tamanhoProduto = By.id("group_1");
	private By fazerLogout = By.cssSelector("a.logout");
//	private By verificaDashboard = By.cssSelector("i.fas fa-2x fa-tachometer-alt");
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	public int contarProdutos() {
		carregarListaProdutos();
//		if(listaProdutos == null) {
//			return 0;
//		}
		return listaProdutos.size();
	}
	
	private void carregarListaProdutos() {
		listaProdutos = driver.findElements(produtos);
	}
	
	
	public int obterQuantidadeProdutos() {
		String quantidadeProdutosNoCarrinho = driver.findElement(textoProdutosNoCarrinho).getText();
		
		quantidadeProdutosNoCarrinho = quantidadeProdutosNoCarrinho.replace("(", "");
		quantidadeProdutosNoCarrinho = quantidadeProdutosNoCarrinho.replace(")", "");
		
		int qtdProdutosNoCarrinho = Integer.parseInt(quantidadeProdutosNoCarrinho);
		return qtdProdutosNoCarrinho;
		
		//return Integer.parseInt(quantidadeProdutosNoCarrinho.replaceAll("\\D+",""));
	}
	
	//return driver.findElement(descricoesDosProdutos).get(indice).getText();  
	public String obterNomeProduto(int indice) {
		return driver.findElement(descricoesDosProdutos).getText();
	}
	
	public String obterPrecoProduto(int indice) {
		return driver.findElement(precoDosProdutos).getText();
	}
	
	public ProdutoPage clicarProduto(int indice) {
		driver.findElement(descricoesDosProdutos).click();
		return new ProdutoPage(driver);
	}
	
	public void selecionarOpcaoDropDown(String opcao) {
		encontrarDropdownSize().deselectByVisibleText(opcao);
	}
	
	public List<String> obterOpcoesSelecionadas(){
		List<WebElement> elementosSelecionados =
		encontrarDropdownSize().getAllSelectedOptions();
		
		List<String> ListaOpcoesSelecionadas = new ArrayList();
		
		for(WebElement elemento : elementosSelecionados) {
			ListaOpcoesSelecionadas.add(elemento.getText());
		}
		return ListaOpcoesSelecionadas;
	
	}
	
	public Select encontrarDropdownSize() {
		return new Select (driver.findElement(tamanhoProduto));
	}
	
	
	
	public LoginPage clicarBotaoSignIn() {
		driver.findElement(botaoSignIn).click();
		return new LoginPage(driver);
	}
	
//	public Dashboards  clicarNoDashBoard() {
//		driver.findElement(verificaDashboard).click();
//		
//	}
	
	public boolean estaLogado(String texto) {
		return texto.contentEquals(driver.findElement(usuarioLogado).getText());
	}
	
	//ATENCAO AQUI 
	public void clicarBotaoSignOut() {
		driver.findElement(fazerLogout).click();
	}

	public void carregarPaginaInicial() {
		driver.get("https://marcelodebittencourt.com/demoprestashop/");
	}

	public Object obterTituloPagina() {
		return driver.getTitle();
	}
	
	public boolean estaLogado() {
		//  ! = false
		return !"Sign in".contentEquals(driver.findElement(usuarioLogado).getText());
	}
}
