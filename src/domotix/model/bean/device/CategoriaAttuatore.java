package domotix.model.bean.device;

import domotix.controller.util.StringUtil;

import java.util.ArrayList;

/** @author Edoardo Coppola */
public class CategoriaAttuatore implements Visitable {

    private String nome;
    private String testoLibero;
    private ArrayList<Modalita> elencoModalita;

    public CategoriaAttuatore(String nome, String testoLibero){
        this.nome = nome;
        this.testoLibero = testoLibero;
        this.elencoModalita = new ArrayList<>();
    }

    /**
     * Metodo per impostare il nome della CategoriaAttuatore.
     * @param nome Il nome da impostare
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo che recupera il nome della CategoriaAttuatore.
     * @return Il nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo per impostare il testo libero descrittivo della CategoriaAttuatore.
     * @param testoLibero Il testo libero
     */
    public void setTestoLibero(String testoLibero) {
        this.testoLibero = testoLibero;
    }

    /**
     * Metodo che recupera il testo libero descrittivo della CategoriaAttuatore.
     * @return Il testo libero
     */
    public String getTestoLibero(){
        return testoLibero;
    }

    /**
     * Metodo che recupera una tra le modalita' operative di cui dispone la CategoriaAttuatore.
     * In particolare, viene recuperata la modalita' che siede all'indice specificato.
     * @param index L'indice specificato
     * @return La modalita'
     */
    public Modalita getModalita(int index){
        return elencoModalita.get(index);
    }

    /**
     * Metodo che recupera una tra le modalita' operative di cui dispone la CategoriaAttuatore.
     * In particolare, viene recuperata la modalita' con il nome specificato.
     * @param nome Il nome specificato
     * @return La modalita' se presente, null altrimenti
     */
    public Modalita getModalita(String nome) {
        for (Modalita modalita : elencoModalita) {
            if (modalita.getNome().equals(nome)) return modalita;
        }
        return null;
    }

    /**
     * Metodo che recupera la modalita' di default della CategoriaAttuatore.
     * @return La modalita' di default
     */
    public Modalita getModalitaDefault() { return elencoModalita.get(0); }

    /**
     * Metodo che recupera la lista completa di tutte le modalita' disponibili alla CategoriaAttuatore.
     * @return La lista di modalita'
     */
    public ArrayList<Modalita> getElencoModalita(){
        return elencoModalita;
    }

    /**
     * Metodo di aggiunta di una nuova modalita' operativa per la CategoriaAttuatore.
     * @param mode La modalita' da aggiungere
     */
    public void addModalita(Modalita mode){
        elencoModalita.add(mode);
    }

    /**
     * Metodo di rimozione di una modalita' operativa dalla CategoriaAttuatore
     * @param mode La modalita' da rimuovere
     */
    public void removeModalita(Modalita mode){
        elencoModalita.remove(mode);
    }

    /**
     * Metodo di rimozione di una modalita' operativa dalla CategoriaAttuatore
     * @param nomeMode Il nome della modalita' da rimuovere
     */
    public void removeModalita(String nomeMode){
        for (Modalita m : elencoModalita) {
            if(nomeMode.equalsIgnoreCase(m.getNome()))
                elencoModalita.remove(m);
        }
    }

    /**
     * Metodo che verifica se la CategoriaAttuatore contiene
     * gia' la modalita specificata.
     * @param mod La modalita'
     * @return True se modalita e' interna a CategoriaAttuatore
     */
    public boolean hasModalita(Modalita mod) {
        return hasModalita(mod.getNome());
    }

    public boolean hasModalita(String nomeMod) {
        for (Modalita modalita : elencoModalita) {
            if (modalita.getNome().equals(nomeMod)) return true;
        }
        return false;
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

    @Override
    public Object fattiVisitare(Visitor v) {
        return v.visitaCategoriaAttuatore(this);
    }
}
