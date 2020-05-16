package domotix.model.bean.regole;

import domotix.model.visitor.Visitor;
import domotix.model.bean.device.SensoreOrologio;

import java.time.LocalTime;

/**
 * Classe che rappresenta un InfoSensoriale rappresentante un valore temporale
 * @author andrea
 */
public class InfoTemporale implements InfoSensoriale {
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
