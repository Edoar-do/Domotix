package domotix.model.bean.regole;

import domotix.model.bean.device.Sensore;

public class InfoVariabile implements InfoSensoriale {
    private Sensore sensore;
    private String nomeInfo;

    public InfoVariabile(Sensore sensore, String nomeInfo) {
        this.sensore = sensore;
        this.nomeInfo = nomeInfo;
    }

    @Override
    public Object getValore() {
        return this.sensore.getValore(this.nomeInfo);
    }
}
