public class ProdutoPerecivel extends Product {
    private final int diasParaVencer;

    public ProdutoPerecivel(String nome, double preco, int quantidade, int diasParaVencer)
            throws QuantidadeInvalidaException {
        super(nome, preco, quantidade);
        this.diasParaVencer = diasParaVencer;
    }

    public int getDiasParaVencer() { return diasParaVencer; }

    @Override
    public double calcularValorTotal() {
        double total = getPreco() * getQuantidade();
        return diasParaVencer <= 3 ? total * 0.8 : total;
    }

    @Override
    public String getDescricao() {
        return super.getDescricao() + " | Dias para vencer: " + diasParaVencer;
    }
}
