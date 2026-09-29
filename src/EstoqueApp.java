import java.util.Locale;

public class EstoqueApp {
    private static final Locale BR = new Locale("pt", "BR");

    public static void main(String[] args) {
        Estoque estoque = new Estoque();
        try {
            ProdutoComum arroz = new ProdutoComum("Arroz", 25, 10);
            ProdutoComum feijao = new ProdutoComum("Feijão", 10, 20);
            arroz.aplicarDesconto(10);
            feijao.aplicarDesconto(20, 1);
            estoque.adicionarProduto(arroz);
            estoque.adicionarProduto(feijao);
            estoque.adicionarProduto(new ProdutoPerecivel("Leite", 5, 12, 2));
            estoque.adicionarProduto(new ProdutoPerecivel("Iogurte", 8, 6, 7));
        } catch (QuantidadeInvalidaException e) {
            System.out.println("Erro no cadastro inicial: " + e.getMessage());
            return;
        }

        System.out.println("PRODUTOS CADASTRADOS (após descontos manuais)");
        for (Product produto : estoque.getProdutos()) {
            System.out.printf(BR, "%s | Total: R$ %.2f%n",
                    produto.getDescricao(), produto.calcularValorTotal());
        }
        System.out.printf(BR, "Valor total inicial do estoque: R$ %.2f%n",
                estoque.calcularValorTotalEstoque());

        try {
            estoque.adicionarProduto(new ProdutoComum("Produto inválido", 10, -1));
        } catch (QuantidadeInvalidaException e) {
            System.out.println("QuantidadeInvalidaException capturada: " + e.getMessage());
        }

        try {
            estoque.venderProduto(0, 2);
            System.out.println("Venda bem-sucedida: 2 unidades de Arroz. Restam "
                    + estoque.getProdutos().get(0).getQuantidade() + " unidades.");
            estoque.venderProduto(0, 100);
        } catch (ProdutoIndisponivelException e) {
            System.out.println("ProdutoIndisponivelException capturada: " + e.getMessage());
        }
        System.out.printf(BR, "Valor total final do estoque: R$ %.2f%n",
                estoque.calcularValorTotalEstoque());
    }
}
