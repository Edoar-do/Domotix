package domotix.model.bean.regole;

public class InfoCostante implements InfoSensoriale {
    private Object info;

    public InfoCostante(Object info) {
        this.info = info;
    }

    @Override
    public Object getValore() {
        return this.info;
    }
}
