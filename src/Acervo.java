import java.util.ArrayList;

public class Acervo {
    private ArrayList<AudioVisual> colecao;

    public Acervo() {
        colecao = new ArrayList<>(20);
    }

    public ArrayList<AudioVisual> getColecao() {
        return colecao;
    }

    public boolean addAudioVisual(AudioVisual av) {
        return colecao.add(av);
    }
}
