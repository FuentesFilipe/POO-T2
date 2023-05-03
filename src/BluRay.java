public class BluRay extends AudioVisual {
    private final int duracao;

    public BluRay(String titulo, double precoBase, int duracao) {
        super(titulo, precoBase);
        this.duracao = duracao;
    }

    public int getDuracao() {
        return duracao;
    }

    @Override
    public double calculaPrecoVenda() {
        return (getPrecoBase() * duracao)/100;
    }

    @Override
    public double calculaImposto() {
        return calculaPrecoVenda() * 0.4;
    }
}
