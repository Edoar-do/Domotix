package domotix.model.bean.device;

/**@author Edoardo Coppola */
public class Attuatore extends Dispositivo {

    private CategoriaAttuatore categoria;
    private Modalita modoOp;

    // riceve come nome dell'attuatore solo la prima parte a cui poi affianco la categoria
    public Attuatore(String nome, CategoriaAttuatore categoria, Modalita modoOpIniziale){
        super.setNome(nome);
        this.categoria = categoria;
        this.modoOp = modoOpIniziale;
    }

    public CategoriaAttuatore getCategoria(){
        return categoria;
    }

    public Modalita getModoOp() {
        return modoOp;
    }

    public void setModoOp(Modalita nuovaModalita){
        modoOp = nuovaModalita;
    }

}
