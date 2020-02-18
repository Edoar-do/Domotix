package domotix.model.bean.device;

import domotix.logicUtil.StringUtil;

import java.util.ArrayList;

/** @author Edoardo Coppola */
public class CategoriaAttuatore {

    private String nome;
    private String testoLibero;
    private ArrayList<Modalita> elencoModalita;

    public CategoriaAttuatore(String nome, String testoLibero){
        this.nome = nome;
        this.testoLibero = testoLibero;
        this.elencoModalita = new ArrayList<>();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setTestoLibero(String testoLibero) {
        this.testoLibero = testoLibero;
    }

    public String getTestoLibero(){
        return testoLibero;
    }

    public Modalita getModalita(int index){
        return elencoModalita.get(index);
    }

    public ArrayList<Modalita> getElencoModalita(){
        return elencoModalita;
    }

    public void addModalita(Modalita mode){
        elencoModalita.add(mode);
    }

    public void removeModalita(Modalita mode){
        elencoModalita.remove(mode);
    }

    public void removeModalita(String nomeMode){
        for (Modalita m : elencoModalita) {
            if(nomeMode.equalsIgnoreCase(m.getNome()))
                elencoModalita.remove(m);
        }
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getNome() + ":\n");
        buffer.append("\tTESTO LIBERO:\n");
        buffer.append(StringUtil.indent(getTestoLibero() + "\n", 2));
        buffer.append("\tELENCO MODALITA':");
        elencoModalita.forEach((e) -> {
            buffer.append(StringUtil.indent("\n" + e.toString(), 2));
        });
        return buffer.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof CategoriaSensore)) return false;
        CategoriaAttuatore other = (CategoriaAttuatore) obj;
        return other.nome.equals(this.nome);
    }
}
