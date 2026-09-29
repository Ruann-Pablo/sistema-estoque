import java.util.Locale;

public abstract class Product implements Vendavel {
    private final String nome;
    private double preco;
    private int quantidade;

    public Product(String nome, double preco, int quantidade)
            throws QuantidadeInvalidaException {
        if (!Double.isFinite(preco) || preco < 0 || quantidade < 0) {
            throw new QuantidadeInvalidaException(
                    "Preço deve ser finito e não negativo; quantidade não pode ser negativa.");
        }
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public int getQuantidade() { return quantidade; }

    public abstract double calcularValorTotal();

    public String getDescricao() {
        return String.format(new Locale("pt", "BR"),
                "%s | Preço: R$ %.2f | Quantidade: %d", nome, preco, quantidade);
    }

    @Override
    public void vender(int quantidadeDesejada) throws ProdutoIndisponivelException {
        if (quantidadeDesejada <= 0) {
            throw new ProdutoIndisponivelException("A quantidade da venda deve ser positiva.");
        }
        if (quantidadeDesejada > quantidade) {
            throw new ProdutoIndisponivelException(
                    "Estoque insuficiente de " + nome + ": disponível " + quantidade
                    + ", solicitado " + quantidadeDesejada + ".");
        }
        quantidade -= quantidadeDesejada;
    }

    public void aplicarDesconto(double percentual) {
        aplicarDesconto(percentual, preco);
    }

    // O limite monetário é aplicado ao desconto do preço de uma unidade.
    public void aplicarDesconto(double percentual, double descontoMaximo) {
        if (!Double.isFinite(percentual) || percentual < 0 || percentual > 100
                || !Double.isFinite(descontoMaximo) || descontoMaximo < 0) {
            throw new IllegalArgumentException(
                    "Percentual deve estar entre 0 e 100 e limite deve ser finito e não negativo.");
        }
        preco -= Math.min(preco * (percentual / 100.0), descontoMaximo);
    }
}
