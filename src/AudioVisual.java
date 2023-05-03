public abstract class AudioVisual implements Cobravel {
    private String titulo;
    private double precoBase;

    public AudioVisual(String titulo, double precoBase) {
        this.titulo = titulo;
        this.precoBase = precoBase;
    }

    public String getTitulo() {
        return titulo;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public abstract double calculaImposto();
    public abstract double calculaPrecoVenda();
}
