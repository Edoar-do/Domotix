package domotix.model.bean.device;

/**@author Edoardo Coppola */
public class Attuatore extends Dispositivo {
    private static final String TOSTRING_TEMPLATE = "%s [%s]: %s";
    private CategoriaAttuatore categoria;
    private Modalita modoOp;

    // riceve come nome dell'attuatore solo la prima parte a cui poi affianco la categoria
    public Attuatore(String nome, CategoriaAttuatore categoria, Modalita modoOpIniziale){
        super.setNome(nome);
        this.categoria = categoria;
        this.modoOp = modoOpIniziale;
    }

    /**
     * Metodo che recupera la CategoriaAttuatore dell'Attuatore.
     * @return La CategoriaAttuatore
     */
    public CategoriaAttuatore getCategoria(){
        return categoria;
    }

    /**
     * Metodo che recupera la ModalitaOperativa attuale dell'Attuatore.
     * @return La ModalitaOperativa
     */
    public Modalita getModoOp() {
        return modoOp;
    }

    /**
     * Metodo per impostare la ModalitaOperativa dell'Attuatore.
     * @param nuovaModalita La nuova ModalitaOperativa da impostare
     */
    public void setModoOp(Modalita nuovaModalita){
        modoOp = nuovaModalita;
    }

    @Override
    public String toString() {
        return String.format(this.TOSTRING_TEMPLATE, this.getNome(), (getStato() ? "ON" : "OFF"), this.getModoOp().toString());
    }
}
