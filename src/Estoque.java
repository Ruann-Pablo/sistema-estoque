import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Estoque {
    private final List<Product> produtos = new ArrayList<>();

    public void adicionarProduto(Product p) {
        produtos.add(Objects.requireNonNull(p, "Produto não pode ser nulo."));
    }

    public List<Product> getProdutos() {
        return Collections.unmodifiableList(produtos);
    }

    public void venderProduto(int indice, int quantidade) throws ProdutoIndisponivelException {
        if (indice < 0 || indice >= produtos.size()) {
            throw new ProdutoIndisponivelException("Índice de produto inexistente: " + indice);
        }
        produtos.get(indice).vender(quantidade);
    }

    public double calcularValorTotalEstoque() {
        double total = 0;
        for (Product produto : produtos) {
            total += produto.calcularValorTotal();
        }
        return total;
    }
}
