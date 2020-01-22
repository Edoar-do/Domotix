package domotix.model.bean.device;
/** @author Edoardo Coppola */
public abstract class Dispositivo {
    private String nome;
    private boolean stato;
    private int numeroAssociazioni;

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public boolean getStato(){
        return stato;
    }

    public void setStato(boolean stato){
        this.stato = stato;
    }

    public int getNumeroAssociazioni() {
        return numeroAssociazioni;
    }

    public void incrNumAssociazioni(){
        this.numeroAssociazioni++;
    }

    public void decrNumAssociazioni(){
        this.numeroAssociazioni--;
    }

}
