package domotix.controller.util;

import domotix.model.ElencoAzioniProgrammate;
import domotix.model.bean.regole.Azione;

import java.util.TimerTask;

public class TimerTaskAzione extends TimerTask {

    private String idAzione;
    private Azione azione;

    public TimerTaskAzione(String idAzione, Azione azione) {
        this.idAzione = idAzione;
        this.azione = azione;
    }

    @Override
    public void run() {
        azione.esegui();
        ElencoAzioniProgrammate.getInstance().remove(idAzione);
    }

}
