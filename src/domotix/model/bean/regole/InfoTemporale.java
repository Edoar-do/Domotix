package domotix.model.bean.regole;

import domotix.controller.util.StringUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
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

    public InfoTemporale(LocalTime tempo){this.tempo = LocalDateTime.of(1998, 1, 1, tempo.getHour(), tempo.getMinute()); } //porcata fatta da me

    public LocalDateTime getTempo() {
        return tempo;
    }

    @Override
    public Object getValore() {
        return  (double) (tempo.getHour() * 60 + tempo.getMinute()); //cast a double fatto da me
    }

    @Override
    public String toString() {
        return tempo.getHour() + "." + tempo.getMinute();
    }
}
