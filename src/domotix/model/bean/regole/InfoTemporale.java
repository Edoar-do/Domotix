package domotix.model.bean.regole;

import domotix.controller.util.StringUtil;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Classe che rappresenta un InfoSensoriale rappresentante un valore temporale
 * @author andrea
 */
public class InfoTemporale implements InfoSensoriale {
    private LocalDateTime tempo;

    public InfoTemporale(LocalDateTime tempo) {
        this.tempo = tempo;
    }

    public LocalDateTime getTempo() {
        return tempo;
    }

    @Override
    public Object getValore() {
        return tempo.getHour() * 60 + tempo.getMinute();
    }

    @Override
    public String toString() {
        return tempo.getHour() + "." + tempo.getMinute();
    }
}
