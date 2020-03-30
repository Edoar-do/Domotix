package domotix.model.bean.regole;

import domotix.controller.util.StringUtil;

/**
 * Classe che rappresenta un InfoSensoriale rappresentante un valore temporale
 * @author andrea
 */
public class InfoTemporale implements InfoSensoriale {
    private int tempo;

    public InfoTemporale(int tempo) {
        this.tempo = tempo;
    }

    public int getTempo() {
        return tempo;
    }

    @Override
    public Object getValore() {
        return tempo;
    }

    @Override
    public String toString() {
        return StringUtil.timestr(tempo);
    }
}
