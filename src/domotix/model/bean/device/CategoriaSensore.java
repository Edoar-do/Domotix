package domotix.model.bean.device;

import domotix.controller.util.StringUtil;

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

    /**
     * Metodo che recupera il nome della CategoriaSensore.
     * @return Il nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo che imposta il nome della CategoriaSensore.
     * @param nome Il nome da impostare
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo che recupera il testo libero descrittivo della CategoriaSensore.
     * @return Il testo libero
     */
    public String getTestoLibero() {
        return testoLibero;
    }

    /**
     * Metodo che imposta il testo libero descrittivo della CategoriaSensore.
     * @param testoLibero Il testo libero
     */
    public void setTestoLibero(String testoLibero) {
        this.testoLibero = testoLibero;
    }

    /**
     * Metodo che recupera il tipo di informazione rilevabile dal Sensore di CategoriaSensore.
     * @return L'informazione rilevabile
     */
    public String getInformazioneRilevabile() {
        return informazioneRilevabile;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getNome() + ":\n");
        buffer.append("\tTESTO LIBERO:\n");
        buffer.append(StringUtil.indent(getTestoLibero() + "\n", 2));
        buffer.append("\tINFORMAZIONE RILEVABILE:");
        buffer.append(StringUtil.indent("\n" + getInformazioneRilevabile(), 2));
        return buffer.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof CategoriaSensore)) return false;
        CategoriaSensore other = (CategoriaSensore) obj;
        return other.nome.equals(this.nome);
    }
}
