public enum Categoria {
    // Constantes
    ACAO("Ação"), ESPORTE("Esporte"), ESTRATEGIA("Estratégia"), SIMULACAO("Simulação"), RPG("RPG");

    // atributos
    private final String nome;

    // construtor
    Categoria(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
