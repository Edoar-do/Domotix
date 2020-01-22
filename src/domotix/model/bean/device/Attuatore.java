package domotix.model.bean.device;

/**@author Edoardo Coppola */
public class Attuatore extends Dispositivo {

    private CategoriaAttuatore categoria;
    private Modalita modoOp;
    private String nome;
    //private boolean stato;

    public Attuatore(String nome, CategoriaAttuatore categoria, Modalita modoOpIniziale){
        this.nome = nome;
        this.categoria = categoria;
        this.modoOp = modoOpIniziale;
    }

    public CategoriaAttuatore getCategoria(){
        return categoria;
    }

    public Modalita getModoOp() {
        return modoOp;
    }

    public String getNome(){
        return nome;
    }

    public void setModoOp(Modalita nuovaModalita){
        modoOp = nuovaModalita;
    }

}
