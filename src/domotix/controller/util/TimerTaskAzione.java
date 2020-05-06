package domotix.controller.util;

import domotix.controller.TimerAzioniProgrammate;
import domotix.model.bean.regole.Azione;

import java.util.TimerTask;

/**
 * Classe per rappresentare l'esecuzione di un'azione da parte di un TimerAzioniProgrammate
 * (a cui si delega il consumo dell'azione a fine esecuzione)
 *
 * @author paolopasqua
 */
public class TimerTaskAzione extends TimerTask {

    private String idAzione;
    private Azione azione;
    private TimerAzioniProgrammate owner;

    public TimerTaskAzione(String idAzione, Azione azione, TimerAzioniProgrammate owner) {
        this.idAzione = idAzione;
        this.azione = azione;
        this.owner = owner;
    }

    @Override
    public void run() {
        azione.esegui();
        owner.consume(idAzione);
    }

}
