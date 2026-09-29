public class EstoqueTest {
    private interface Acao { void executar() throws Exception; }
    private static int verificacoes;

    private static void igual(double esperado, double atual) {
        if (!Double.isFinite(atual) || Math.abs(esperado - atual) > 0.000001) {
            throw new AssertionError("Esperado " + esperado + ", obtido " + atual);
        }
        verificacoes++;
    }

    private static void falha(Class<? extends Exception> tipo, Acao acao) throws Exception {
        try {
            acao.executar();
        } catch (Exception e) {
            if (!tipo.isInstance(e)) { throw e; }
            verificacoes++;
            return;
        }
        throw new AssertionError("Exceção esperada: " + tipo.getSimpleName());
    }

    public static void main(String[] args) throws Exception {
        falha(QuantidadeInvalidaException.class, () -> new ProdutoComum("X", -1, 1));
        falha(QuantidadeInvalidaException.class, () -> new ProdutoComum("X", 1, -1));
        falha(QuantidadeInvalidaException.class, () -> new ProdutoComum("X", Double.NaN, 1));
        igual(0, new ProdutoComum("Grátis", 0, 0).calcularValorTotal());
        Product comum = new ProdutoComum("Comum", 100, 2);
        igual(200, comum.calcularValorTotal());
        comum.aplicarDesconto(10);
        igual(90, comum.getPreco());
        comum.aplicarDesconto(50, 5);
        igual(85, comum.getPreco());
        falha(IllegalArgumentException.class, () -> comum.aplicarDesconto(101));
        falha(IllegalArgumentException.class, () -> comum.aplicarDesconto(10, -1));
        igual(85, comum.getPreco());
        comum.vender(1);
        igual(1, comum.getQuantidade());
        falha(ProdutoIndisponivelException.class, () -> comum.vender(2));
        falha(ProdutoIndisponivelException.class, () -> comum.vender(-1));
        falha(ProdutoIndisponivelException.class, () -> comum.vender(0));
        igual(1, comum.getQuantidade());
        igual(80, new ProdutoPerecivel("P", 10, 10, 3).calcularValorTotal());
        igual(100, new ProdutoPerecivel("P", 10, 10, 4).calcularValorTotal());
        Estoque estoque = new Estoque();
        igual(0, estoque.calcularValorTotalEstoque());
        estoque.adicionarProduto(comum);
        estoque.adicionarProduto(new ProdutoPerecivel("P", 10, 10, 2));
        igual(165, estoque.calcularValorTotalEstoque());
        estoque.venderProduto(1, 2);
        igual(149, estoque.calcularValorTotalEstoque());
        falha(ProdutoIndisponivelException.class, () -> estoque.venderProduto(1, 9));
        igual(149, estoque.calcularValorTotalEstoque());
        falha(ProdutoIndisponivelException.class, () -> estoque.venderProduto(9, 1));
        estoque.venderProduto(0, 1);
        igual(0, comum.getQuantidade());
        comum.aplicarDesconto(100);
        igual(0, comum.getPreco());
        System.out.println(verificacoes + " verificações passaram.");
    }
}
