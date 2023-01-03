# language: pt
Funcionalidade: Visualizar produtos
  Como um usuario logado
  Eu quero visualizar produtos nao disponiveis
  Para poder escolher qual eu vou comprar

  @validacaoinicial
  Cenario: Deve mostrar uma lista de oitos produtos na pagina inicial
    Dado que estou na pagina inicial
    Quando nao estou logado
    Entao visualizo 8 produtos disponiveis
    E carrinho esta zerado

  @fluxopadrao
  Esquema do Cenario: Deve mostrar produto escolhido confirmado
    Dado que estou na pagina inicial
    Quando estou logado
    E seleciono um produto na posicao <posicao>
    E nome do produto na tela principal eh <nome>
    E preco do produto na tela principal e <preco>
    E adiciono o produto no carrinho com tamanho <tamanho> cor <cor> e quantidade <quantidade>
    Entao o produto aparece na confirmacao com nome <nome> preco <preco> tamanho <tamanho> cor <cor> e quantidade <quantidade>

    Exemplos: 
      | posicao | nome                          | preco    | tamanho | cor     | quantidade |
      |       0 | "Hummingbird Printed T-Shirt" | "$19.12" | "M"     | "Black" |          2 |
      |       1 | "Hummingbird Printed Sweater" | "$28.72" | "XL"    | "N/A"   |          3 |
