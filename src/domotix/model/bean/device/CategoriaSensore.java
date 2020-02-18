package domotix.model.bean.device;

import domotix.logicUtil.StringUtil;

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
