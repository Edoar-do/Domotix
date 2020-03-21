package domotix.model.bean.regole;

/**
 * Classe che rappresenta un InfoSensoriale costante
 * @author andrea
 */
public class InfoCostante implements InfoSensoriale {
    private Object info;

    public InfoCostante(Object info) {
        this.info = info;
    }

    @Override
    public Object getValore() {
        return this.info;
    }

    @Override
    public String toString() {
        return info.toString();
    }
}
