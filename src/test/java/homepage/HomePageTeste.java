package homepage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import base.BaseTests;
import pages.CarrinhoPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ModalProdutoPage;
import pages.PedidoPage;
import pages.ProdutoPage;
import util.Funcoes;

public class HomePageTeste extends BaseTests {

	@Test
	public void testcontagemProdutos() {
		carregarPaginaInicial();
//		assertThat(homePage.contarProdutos(), is(8));
	}

	@Test
	public void testValidarCarrinhoZerado_ZeroItensNoCarrinho() throws InterruptedException {
		int produtosNoCarrinho = homePage.obterQuantidadeProdutos();
		assertThat(produtosNoCarrinho, is(0));
		Thread.sleep(8000);

	}

	ProdutoPage produtoPage;

	@Test
	public void testValidarDetalhesDoProduto_DescricaoEValorIguais() {
		int indice = 0;
		String nomeProduto_HomePage = homePage.obterNomeProduto(indice);
		String precoProduto_HomePage = homePage.obterPrecoProduto(indice);

		System.out.println(nomeProduto_HomePage);
		System.out.println(precoProduto_HomePage);

		produtoPage = homePage.clicarProduto(indice);

		String nomeProduto_ProdutoPage = produtoPage.obterNomeProduto();
		String precoProduto_ProdutoPage = produtoPage.obterPrecoProduto();

		System.out.println(nomeProduto_ProdutoPage);
		System.out.println(precoProduto_ProdutoPage);

		assertThat(nomeProduto_HomePage.toUpperCase(), is(nomeProduto_ProdutoPage));
		assertThat(precoProduto_HomePage, is(precoProduto_ProdutoPage));
	}

	LoginPage loginpage;

	@Test
	public void testLoginComSucesso_UsuarioLogado() throws InterruptedException {
		loginpage = homePage.clicarBotaoSignIn();

		// preenche o usuario e senha
		loginpage.preencherEmail("viniciusquaresma14@hotmail.com");

		loginpage.preencherPassword("123456");
		Thread.sleep(6000);
		// aqui faz o login no site da empresa
//		loginpage.preencherEmail("suporte@toolbit.com.br");
//		loginpage.preencherPassword("1qa2ws3ed!");

		// aqui clica no botao
		loginpage.clicarBotaoSignIn();

		// confere se o usuario est� logado
		assertThat(homePage.estaLogado("vinicius quaresma"), is(true));
		Thread.sleep(6000);
		carregarPaginaInicial();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/massaTeste_loginOK.csv", numLinesToSkip = 1, delimiter = ';')
	public void testLogin_UsuarioLogadoComDadosValidos(String nomeTeste, String email, String password, String nomeUsuario, String resultado) throws InterruptedException {
		loginpage = homePage.clicarBotaoSignIn();
		
		//preenche o usuario e senha
		loginpage.preencherEmail(email);
		loginpage.preencherPassword(password);
		//Thread.sleep(6000);
		//aqui faz o login no site da empresa
	//	loginpage.preencherEmail("suporte@toolbit.com.br");
	//	loginpage.preencherPassword("1qa2ws3ed!");
		
		//aqui clica no botao
		loginpage.clicarBotaoSignIn();
		
		
		boolean esperado_loginOK;
		if(resultado.equals("positivo"))
			esperado_loginOK= true;
		else
			esperado_loginOK= false;
			
		//confere se o usuario est� logado
		assertThat(homePage.estaLogado(nomeUsuario), is(esperado_loginOK));
		
		//aqui vai tirar um PRINT da tela
		capturarTela(nomeTeste, resultado);
		
		
		if (esperado_loginOK)
			homePage.clicarBotaoSignOut();
		
		carregarPaginaInicial();
		
		//https://iterasys.com.br/lesson/detail/36/1233/   26:19
	}
	
	
	ModalProdutoPage modalProdutoPage;

	@Test
	public void incluirProdutoNoCarrinho_ProdutoIncluidoComSucesso() throws InterruptedException {

		String tamanhoProduto = "M";
		String corProduto = "Black";
		int quantidadeProduto = 2;
		// desvio condicional
		if (!homePage.estaLogado("vinicius quaresma")) {
			testLoginComSucesso_UsuarioLogado();
		}

		// seleciona o produto
		testValidarDetalhesDoProduto_DescricaoEValorIguais();

		// seleciona o tamanho
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
//			
		String precoProdutoString = modalProdutoPage.obterPrecoProduto();
		precoProdutoString = precoProdutoString.replace("$", "");
		Double precoProduto = Double.parseDouble(precoProdutoString);
//		
		System.out.println(modalProdutoPage.obterDescricaoProduto());
		System.out.println(modalProdutoPage.obterPrecoProduto());

		assertThat(modalProdutoPage.obterTamanhoProduto(), is(tamanhoProduto));
		assertThat(modalProdutoPage.obterCorProduto(), is(corProduto));
		//assertThat(modalProdutoPage.obterQuantidadeProduto(), is(quantidadeProduto));
		// is(Integer.toString(quantidadeProduto)));

//		System.out.println(modalProdutoPage.obterSubtotal());
////		
//		String subtotalString = modalProdutoPage.obterSubtotal();
//		subtotalString = subtotalString.replace("$", "");
//		Double subtotal = Double.parseDouble(subtotalString);

		// Double subtotalCalculado = quantidadeProduto * precoProduto;
		Thread.sleep(5000);
		// assertThat(subtotal, is(subtotalCalculado));
	}

//	@Test
//	public void testarDashBoard() {
//		
//	}

	@Test
	public void IrParaCarrinho_InformacoesPersistidas() throws InterruptedException {
		incluirProdutoNoCarrinho_ProdutoIncluidoComSucesso();
		CarrinhoPage carrinhoPage = modalProdutoPage.clicarBotaoProceedToCheckout();
		System.out.println("funcionou!!");
//		if(!mensagemProdutoAdicionado.obterMensagemProdutoAdicionado("Product successfully added to your shopping cart")) {
//			IrParaCarrinho_InformacoesPersistidas();
//		}
	}

	@Test
	public void RealizarEtapaDeCompra() throws InterruptedException {
		IrParaCarrinho_InformacoesPersistidas();
		CarrinhoPage carrinhoPage = modalProdutoPage.clicarBotaoProceedToCheckoutSegundo();

		System.out.println("funcionou denovo!!");

	}

	String esperado_nomeProduto = "Hummingbird printed t-shirt";
	Double esperado_precoProduto = 19.12;
	String esperado_tamanhoProduto = "M";
	String esperado_corProduto = "Black";
	int esperado_input_quantidadeProduto = 2;
	Double esperado_subtotalProduto = esperado_precoProduto * esperado_input_quantidadeProduto;

	int esperado_numeroItensTotal = esperado_input_quantidadeProduto;
	Double esperado_subtotalTotal = esperado_subtotalProduto;
	Double esperado_shippingTotal = 7.00;
	Double esperado_totalTaxExclTotal = esperado_subtotalTotal + esperado_shippingTotal;
	Double esperado_totalTaxIncTotal = esperado_totalTaxExclTotal;
	Double esperado_taxasTotal = 0.00;

	String esperado_nomeCliente = "vinicius quaresma";

	CarrinhoPage carrinhoPage;

	@Test
	public void Concluir_compra() throws InterruptedException {

		incluirProdutoNoCarrinho_ProdutoIncluidoComSucesso();

		carrinhoPage = modalProdutoPage.clicarBotaoProceedToCheckout();

		System.out.println(carrinhoPage.obter_nomeProduto());
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()));
		System.out.println(carrinhoPage.obter_tamanhoProduto());
		System.out.println(carrinhoPage.obter_corProduto());
		System.out.println(carrinhoPage.obter_input_quantidadeProduto());
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subTotalProduto()));
		System.out.println(Funcoes.removeTextoItemsDevolveInt(carrinhoPage.obter_numeroItensTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subTotalProduto()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxExclTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxIncTotal()));
		System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxasValor()));

		assertThat(carrinhoPage.obter_nomeProduto(), is(esperado_nomeProduto));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()), is(esperado_precoProduto));
		assertThat(carrinhoPage.obter_tamanhoProduto(), is(esperado_tamanhoProduto));
		assertThat(carrinhoPage.obter_corProduto(), is(esperado_corProduto));
		// assertThat(Integer.parseInt(carrinhoPage.obter_input_quantidadeProduto()),
		// is(esperado_input_quantidadeProduto));
		// assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subTotalProduto()),
		// is(esperado_subtotalProduto));

		// assertThat(Funcoes.removeTextoItemsDevolveInt(carrinhoPage.obter_numeroItensTotal()),
		// is (esperado_numeroItensTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subTotalProduto()), is(esperado_subtotalTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingTotal()), is(esperado_shippingTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxExclTotal()),
				is(esperado_totalTaxExclTotal));
		assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxIncTotal()),
				is(esperado_totalTaxIncTotal));

		assertEquals(esperado_nomeProduto, carrinhoPage.obter_nomeProduto());
		assertEquals(esperado_precoProduto, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()));
		assertEquals(esperado_tamanhoProduto, carrinhoPage.obter_tamanhoProduto());
		assertEquals(esperado_corProduto, carrinhoPage.obter_corProduto());
		assertEquals(esperado_input_quantidadeProduto, Integer.parseInt(carrinhoPage.obter_input_quantidadeProduto()));
		assertEquals(esperado_subtotalProduto, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subTotalProduto()));

		assertEquals(esperado_numeroItensTotal,
				Funcoes.removeTextoItemsDevolveInt(carrinhoPage.obter_numeroItensTotal()));
		assertEquals(esperado_subtotalTotal, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subTotalProduto()));
		assertEquals(esperado_shippingTotal, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingTotal()));
		assertEquals(esperado_totalTaxExclTotal,
				Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxExclTotal()));
		assertEquals(esperado_totalTaxIncTotal,
				Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_totalTaxIncTotal()));
		assertEquals(esperado_taxasTotal, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxasValor()));

	}

	CheckoutPage checkoutPage;

	@Test
	public void IrParaCheckout_FreteMeioPagamentoEnderecoListadosOk() throws InterruptedException {
		// IrParaCarrinho_InformacoesPersistidas();
		Concluir_compra();

		checkoutPage = carrinhoPage.clicarBotaoProceedToCheckout();
		// ok
		assertThat(Funcoes.removeCifraoDevolveDouble(checkoutPage.obter_totalTaxIncTotal()),
				is(esperado_totalTaxIncTotal));
		assertTrue(checkoutPage.obter_nomeCliente().startsWith(esperado_nomeCliente));

		checkoutPage.clicarBotaoContinueAddress();

		// aqui vai pegar o valor e remover o cifrao , e transforma-lo em double
		String encontrado_shippingValor = checkoutPage.obter_shippingValor();
		encontrado_shippingValor = Funcoes.removeTexto(encontrado_shippingValor, " tax excl.");
		Double encontrado_shippingValor_Double = Funcoes.removeCifraoDevolveDouble(encontrado_shippingValor);

		assertThat(encontrado_shippingValor_Double, is(esperado_shippingTotal));

		checkoutPage.clicarBotaoContinueShipping();

		// selecionar a opcao de pagamento
		checkoutPage.selecionarRadioPayByCheck();

		String encontrado_amountPayByCheck = checkoutPage.obter_amountPayByCheck();
		encontrado_amountPayByCheck = Funcoes.removeTexto(encontrado_amountPayByCheck, " (tax incl.)");
		Double encontrado_amountPayByCheck_Double = Funcoes.removeCifraoDevolveDouble(encontrado_amountPayByCheck);

		assertThat(encontrado_amountPayByCheck_Double, is(esperado_totalTaxIncTotal));

		checkoutPage.selecionarCheckboxIAgree();

		assertTrue(checkoutPage.estaSelecionadoCheckboxIAgree());

		// https://iterasys.com.br/lesson/detail/36/1227/ min: 5:19

	}

	@Test
	public void finalizarPedido_pedidoFinalizadoComSucesso() throws InterruptedException {
		IrParaCheckout_FreteMeioPagamentoEnderecoListadosOk();

		// clicar no botao confirma pedido
		PedidoPage pedidoPage = checkoutPage.clicarBotaoConfirmaPedido();

		assertTrue(pedidoPage.obter_textoPedidoConfirmado().endsWith("YOUR ORDER IS CONFIRMED"));

		assertThat(pedidoPage.obter_email(), is("viniciusquaresma14@hotmail.com"));

		assertThat(pedidoPage.obter_totalProdutos(), is(esperado_subtotalProduto));

		assertThat(pedidoPage.obter_totalTaxIncl(), is(esperado_totalTaxIncTotal));

		assertThat(pedidoPage.obter_metodoPagamento(), is("check"));

		// assertThat(pedidoPage.obter_textoPedidoConfirmado().toUpperCase(), is("YOUR
		// ORDER IS CONFIRMED"));

		// espera implicita
		// esse drive serve para esperar um determinado tempo para encontar um elemento
		// especifico na pagina
		// porem , se o elemento for encontrado antes dos 10 segundos, o teste segue
		// normalmente
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// espera explicita
		// quando algum metodo tenta achar algum elemento que nao esteja visivel por
		// conta do carregamento dele na pag,
		// � fornecido uma especie de fila ou seja, assim que o elemento for encontrado
		// , seguir� com a operacao
		// a espera vai ser de 10 segundos, ate que o elemento mensagemProdutoAdicionado
		// esteja visivel
		// webDriverWait wait = new WebDriverWait(driver, 10);
		// wait.until(ExpectedConditions.visibilityOfElementLocated(mensagemProdutoAdicionado));

		// FluentWait fluentWait = new FluentWait(driver)
		// .whithTimeout(Duration.ofSeconds(10))
		// .pollingEvery(Duration.ofSeconds(1))
		// .ignoring(NoSuchElementException.class);
		// fluentWait.until(ExpectedConditions.visibilityOfElementLocated(mensagemProdutoAdicionado))
	}

}
