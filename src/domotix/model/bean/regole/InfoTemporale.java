package domotix.model.bean.regole;

import domotix.controller.util.StringUtil;
import domotix.model.bean.device.Sensore;
import domotix.model.bean.device.SensoreOrologio;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

/**
 * Classe che rappresenta un InfoSensoriale rappresentante un valore temporale
 * @author andrea
 */
public class InfoTemporale implements Visitable, InfoSensoriale {
    private LocalTime tempo;

    public InfoTemporale(LocalTime tempo) {
        this.tempo = tempo;
    }

    public LocalTime getTempo() {
        return tempo;
    }

    @Override
    public Object getValore() {
        return SensoreOrologio.getTempo(tempo);
    }

    @Override
    public Object fattiVisitare(Visitor v) {
        return v.visitaInfoTemporale(this);
    }
}
