public class Game extends AudioVisual {
    private final Categoria categoria;

    public Game(String titulo, double precoBase, Categoria categoria) {
        super(titulo, precoBase);
        this.categoria = categoria;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    @Override
    public double calculaPrecoVenda() {
        double preco = getPrecoBase();
        if (categoria == Categoria.ACAO) {
            preco *= 1.2;
        } else if (categoria == Categoria.ESPORTE) {
            preco *= 1.3;
        } else if (categoria == Categoria.ESTRATEGIA) {
            preco *= 1.4;
        } else if (categoria == Categoria.SIMULACAO) {
            preco *= 1.5;
        } else if (categoria == Categoria.RPG) {
            preco *= 1.7;
        }
        return preco;
    }


    @Override
    public double calculaImposto() {
        return calculaPrecoVenda() * 0.5;
    }
}
