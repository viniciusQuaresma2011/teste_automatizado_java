package steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import pages.LoginPage;
import pages.ModalProdutoPage;
import pages.ProdutoPage;
import testes.HomePage;

public class ComprarProdutoSteps {

	private static WebDriver driver;
	private HomePage homePage = new HomePage(driver);

	@Before
	public static void inicializar() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "C:\\webdriver\\chromedriver\\91\\chromedriver.exe");
		driver = new ChromeDriver();
		Thread.sleep(6000);
	}

	@Dado("que estou na pagina inicial")
	public void que_estou_na_pagina_inicial() {
		homePage.carregarPaginaInicial();
		assertThat(homePage.obterTituloPagina(), is("Loja de Teste"));

	}

	@Quando("nao estou logado")
	public void nao_estou_logado() {
		assertThat(homePage.estaLogado(), is(false));
	}

	@Entao("visualizo {int} produtos disponiveis")
	public void visualizo_produtos_disponiveis(Integer int1) {
		assertThat(homePage.contarProdutos(), is(int1));
	}

	@Entao("carrinho esta zerado")
	public void carrinho_esta_zerado() {
		assertThat(homePage.obterQuantidadeProdutos(), is(0));
	}
	
	LoginPage loginPage;
	@Quando("estou logado")
	public void estou_logado() throws InterruptedException {
		
		loginPage = homePage.clicarBotaoSignIn();
		
		loginPage.preencherEmail("viniciusquaresma14@hotmail.com");

		loginPage.preencherPassword("123456");
		Thread.sleep(6000);
		// aqui faz o login no site da empresa
//		loginpage.preencherEmail("suporte@toolbit.com.br");
//		loginpage.preencherPassword("1qa2ws3ed!");

		// aqui clica no botao
		loginPage.clicarBotaoSignIn();

		// confere se o usuario est� logado
		assertThat(homePage.estaLogado("vinicius quaresma"), is(true));
		
		homePage.carregarPaginaInicial();
	}
	
	ProdutoPage produtoPage;
	String nomeProduto_HomePage;
	String precoProduto_HomePage;
	
	String nomeProduto_ProdutoPage;
	String precoProduto_ProdutoPage;
	
	@Quando("seleciono um produto na posicao {int}")
	public void seleciono_um_produto_na_posicao(Integer indice) {
		nomeProduto_HomePage = homePage.obterNomeProduto(indice);
		precoProduto_HomePage = homePage.obterPrecoProduto(indice);

		System.out.println(nomeProduto_HomePage);
		System.out.println(precoProduto_HomePage);

		produtoPage = homePage.clicarProduto(indice);

		nomeProduto_ProdutoPage = produtoPage.obterNomeProduto();
		precoProduto_ProdutoPage = produtoPage.obterPrecoProduto();
	}

	@Quando("nome do produto na tela principal eh {string}")
	public void nome_do_produto_na_tela_principal_eh(String nomeProduto) {
		System.out.println(nomeProduto);
//		assertThat(nomeProduto_HomePage.toUpperCase(), is (nomeProduto.toUpperCase()));
//		assertThat(nomeProduto_ProdutoPage.toUpperCase(), is (nomeProduto.toUpperCase()));
	}

	@E("preco do produto na tela principal e {string}")
	public void preco_do_produto_na_tela_princiap_e(String precoProduto) {
		System.out.println(precoProduto);
//		assertThat(precoProduto_HomePage, is(precoProduto.toUpperCase()));
//		assertThat(precoProduto_ProdutoPage, is(precoProduto.toUpperCase()));
	}

	
	ModalProdutoPage modalProdutoPage;
	@Quando("adiciono o produto no carrinho com tamanho {string} cor {string} e quantidade {int}")
	public void adiciono_o_produto_no_carrinho_com_tamanho_cor_e_quantidade(String tamanhoProduto, String corProduto, Integer quantidadeProduto) throws InterruptedException {
		List<String> listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		System.out.println(listaOpcoes.get(0));
		System.out.println("Tamanho da lista: " + listaOpcoes.size());

		produtoPage.selecionarOpcaoDropDown(tamanhoProduto);

		listaOpcoes = produtoPage.obterOpcoesSelecionadas();
		System.out.println(listaOpcoes.get(0));
		System.out.println("Tamanho da lista: " + listaOpcoes.size());

		// seleciona cor
		
		produtoPage.selecionarCorPreta();

		// seleciona a quantidade
		produtoPage.alterarQuantidade(quantidadeProduto);

		modalProdutoPage = produtoPage.clicarBotaoAddToCart();

		// vai verificar se a mensagem retornada � igual a especificada
		Thread.sleep(8000);
		assertThat(modalProdutoPage.obterMensagemProdutoAdicionado()
				.endsWith("Product successfully added to your shopping cart"), is(true));  
	}
	
	@Entao("o produto aparece na confirmacao com nome {string} preco {string} tamanho {string} cor {string} e quantidade {int}")
	public void o_produto_aparece_na_confirmacao_com_nome_preco_tamanho_cor_e_quantidade(String nomeProduto, String precoProduto, String tamanhoProduto, String corProduto, Integer quantidadeProduto) throws InterruptedException {
		
			assertThat(modalProdutoPage.obterDescricaoProduto().toUpperCase(), is (nomeProduto_ProdutoPage.toUpperCase())); 
			
			Double precoProdutoDoubleEncontrado = Double.parseDouble(modalProdutoPage.obterPrecoProduto().replace("$", ""));
			Double precoProdutoDoubleEsperado = Double.parseDouble(precoProduto.replace("$", ""));
//			
			System.out.println(modalProdutoPage.obterDescricaoProduto());
			System.out.println(modalProdutoPage.obterPrecoProduto());

			assertThat(modalProdutoPage.obterTamanhoProduto(), is(tamanhoProduto));
			if(!corProduto.equals("N/A"))
				assertThat(modalProdutoPage.obterCorProduto(), is(corProduto));
			assertThat(modalProdutoPage.obterQuantidadeProduto(), is(Integer.toString(quantidadeProduto)));
			// is(Integer.toString(quantidadeProduto)));

//			System.out.println(modalProdutoPage.obterSubtotal());
////			
			String subtotalString = modalProdutoPage.obterSubtotal();
			subtotalString = subtotalString.replace("$", "");
			Double subtotalEncontrado = Double.parseDouble(subtotalString);

			Double subtotalCalculadoEsperado = quantidadeProduto * precoProdutoDoubleEsperado;
			Thread.sleep(5000);
			//assertThat(subtotalEncontrado, is(subtotalCalculadoEsperado));
	}


	@After
	public static void finalizar() {
		driver.quit();
	}

}
