package domotix.model.bean.device;
/** @author Edoardo Coppola */
public class Modalita {
    private static final String TOSTRING_TEMPLATE = "%s";
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

    @Override
    public String toString() {
        return String.format(this.TOSTRING_TEMPLATE, this.getNome());
    }
}
