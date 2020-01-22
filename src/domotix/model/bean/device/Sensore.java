package domotix.model.bean.device;
import domotix.model.bean.device.Dispositivo;
/** @author Edoardo Coppola */
public class Sensore extends Dispositivo {

    private CategoriaSensore categoria;
    private int valore;

    public Sensore(String nome, CategoriaSensore categoria){
        this.categoria = categoria;
        super.setNome(nome);
    }

    public int getValore(){
        return valore;
    }

    public CategoriaSensore getCategoria() {
        return categoria;
    }

    public void setValore(int nuovoValore){
        this.valore = nuovoValore;
    }
}
