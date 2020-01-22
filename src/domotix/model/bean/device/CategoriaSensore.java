package domotix.model.bean.device;
/** @author Edoardo Coppola */
public class CategoriaSensore {

    private String nome;
    private String testoLibero;
    private String informazioneRilevabile;

    public CategoriaSensore(String nome, String testoLibero, String informazioneRilevabile){
        this.informazioneRilevabile = informazioneRilevabile;
        this.nome = nome;
        this.testoLibero = testoLibero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTestoLibero() {
        return testoLibero;
    }

    public void setTestoLibero(String testoLibero) {
        this.testoLibero = testoLibero;
    }

    public String getInformazioneRilevabile() {
        return informazioneRilevabile;
    }
}
