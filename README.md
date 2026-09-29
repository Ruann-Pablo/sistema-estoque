# Sistema de estoque de produtos

Solução do exercício da primeira avaliação, em Java puro, sem dependências externas.

## Executar

Requer um JDK 8 ou superior, com `java` e `javac` disponíveis no terminal.
Abra o terminal nesta pasta e execute:

```powershell
javac -encoding UTF-8 -Xlint:all -d out src/*.java tests/*.java
java -cp out EstoqueApp
java -cp out EstoqueTest
```

## Organização e conceitos

- `Product`: classe abstrata, atributos privados, implementação de `Vendavel` e sobrecarga de `aplicarDesconto`.
- `ProdutoComum`: herda de `Product` e calcula preço vezes quantidade.
- `ProdutoPerecivel`: sobrescreve cálculo e descrição; aplica 20% de desconto quando faltam 3 dias ou menos.
- `Vendavel`: contrato de venda.
- `Estoque`: contém uma lista de `Product` e soma valores por polimorfismo, sem testar subtipos.
- `EstoqueException`: exceção base das exceções de quantidade inválida e produto indisponível.
- `EstoqueApp`: demonstra cadastro, descontos, venda e captura das duas exceções.
- `EstoqueTest`: verifica cálculos, limites de validade, descontos, vendas e preservação do estoque após erros.

## Decisões sobre regras não detalhadas no enunciado

O percentual usa a escala de 0 a 100. Os descontos manuais alteram o preço unitário atual. Na sobrecarga com limite, `descontoMaximo` é o máximo em reais descontado por unidade. O desconto automático dos perecíveis é aplicado no cálculo do total, sem alterar o preço armazenado, e pode acumular com o desconto manual.

Vendas de quantidade zero ou negativa são rejeitadas com `ProdutoIndisponivelException`, preservando a assinatura da interface. Índices começam em zero; índices inexistentes também geram essa exceção. Preços não finitos e descontos inválidos são rejeitados. A regra de validade segue literalmente `diasParaVencer <= 3`; o enunciado não estabelece bloqueio de produtos vencidos.

## Resultado esperado da demonstração

| Produto | Preço após desconto manual | Quantidade inicial | Total inicial |
| --- | ---: | ---: | ---: |
| Arroz | R$ 22,50 | 10 | R$ 225,00 |
| Feijão | R$ 9,00 | 20 | R$ 180,00 |
| Leite (2 dias) | R$ 5,00 | 12 | R$ 48,00 |
| Iogurte (7 dias) | R$ 8,00 | 6 | R$ 48,00 |

Total inicial: **R$ 501,00**. O cadastro com quantidade negativa falha e é tratado. A venda de 2 unidades de arroz deixa 8 unidades; a tentativa de vender 100 falha e é tratada. Total final: **R$ 456,00**.

O programa imprime os produtos, os totais e as mensagens das exceções. Para salvar a saída após executar:

```powershell
java -cp out EstoqueApp | Out-File -Encoding utf8 saida.txt
```
