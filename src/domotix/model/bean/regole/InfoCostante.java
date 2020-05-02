package domotix.model.bean.regole;

/**
 * Classe che rappresenta un InfoSensoriale costante
 * @author andrea
 */
public class InfoCostante implements Visitable implements InfoSensoriale {
    private Object info;

    public InfoCostante(Object info) {
        this.info = info;
    }

    public Object getInfo() {
        return info;
    }

    @Override
    public Object getValore() {
        return this.info;
    }

    @Override
    public String toString() {
        return info.toString();
    }

    @Override
    public Object fattiVisitare(Visitor v) {
        return v.visitaInfoCostante(this);
    }
}
