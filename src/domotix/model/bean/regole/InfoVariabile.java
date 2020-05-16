package domotix.model.bean.regole;

import domotix.model.visitor.Visitor;
import domotix.model.bean.device.Sensore;

/**
 * Classe che rappresenta un InfoSensoriale variabile (i.e. che dipende dal valore del sensore)
 * @author andrea
 */

public class InfoVariabile implements InfoSensoriale {
    private Sensore sensore;
    private String nomeInfo;

    public InfoVariabile(Sensore sensore, String nomeInfo) {
        this.sensore = sensore;
        this.nomeInfo = nomeInfo;
    }

    public Sensore getSensore() {
        return sensore;
    }

    public String getNomeInfo() {
        return nomeInfo;
    }

    @Override
    public Object getValore() {
        return this.sensore.getValore(this.nomeInfo);
    }

    @Override
    public Object fattiVisitare(Visitor v) {
        return v.visitaInfoVariabile(this);
    }
}
