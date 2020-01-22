package domotix.model.bean.device;
/** @author Edoardo Coppola */
public class Modalita {
    private String nome;

    public Modalita(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
}
