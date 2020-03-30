package domotix.controller;

import domotix.controller.util.TimerTaskAzione;
import domotix.model.ElencoAzioniProgrammate;
import domotix.model.bean.device.SensoreOrologio;
import domotix.model.bean.regole.Azione;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Classe di una sola istanza per le operazioni temporizzate di gestione delle azioni programamte.
 *
 * @author paolopasqua
 */
public class TimerAzioniProgrammate {

    public static final int DEFAULT_DELAY = 10000; //10 seconds

    private Timer timer, timerAzione;
    private int delay = DEFAULT_DELAY;
    private TimerTask taskControlloAzioni;
    private ArrayList<String> azioniPianificate;

    private static TimerAzioniProgrammate instance = null;

    /**
     * Recupera la unica istanza della classe
     * @return  unica istanza della classe
     */
    public static TimerAzioniProgrammate getInstance() {
        if (instance == null)
            instance = new TimerAzioniProgrammate();
        return instance;
    }

    private TimerAzioniProgrammate() {
        this.timer = new Timer();
        this.timerAzione = new Timer();
        this.azioniPianificate = new ArrayList<>();
        this.taskControlloAzioni = new TimerTask() {
            @Override
            public void run() {
                ElencoAzioniProgrammate
                        .getInstance()
                        .getIdAzioni()
                        .stream()
                        .filter(s -> !azioniPianificate.contains(s))
                        .forEach(s -> {
                            //per ogni nuova azione da pianificare
                            Azione a = ElencoAzioniProgrammate.getInstance().getAzione(s); //recupero l'azione
                            long delay = SensoreOrologio.getInstance().getMinutiDifferenza(a.getStart()) * 60000; //ricavo il tempo in millisecondi
                            timerAzione.schedule(new TimerTaskAzione(s, a, getInstance()), delay); //schedulo l'azione
                            //la classe TimerTaskAzione esegue l'azione e richiama il metodo consume
                });
            }
        };
    }

    /**
     * Ritorna l'attuale ritardo di tempo impostato tra un'esecuzione e l'altra in millisecondi
     * @return  ritardo di tempo in millisecondi
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Imposta il ritardo di tempo impostato tra un'esecuzione e l'altra in millisecondi
     * @param delay ritardo di tempo in millisecondi
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }

    /**
     * Avvia il timer
     */
    public void start() {
        this.timer.scheduleAtFixedRate(this.taskControlloAzioni, delay, delay);
    }

    /**
     * Stoppa il timer
     */
    public void stop() {
        timer.cancel();
        timer.purge();
        timerAzione.cancel();
        timerAzione.purge();
    }

    /**
     * Rimuove un'azione pianificata in quanto consumata, cioe' eseguita.
     * @param idAzione  identificativo dell'azione
     */
    public void consume(String idAzione) {
        ElencoAzioniProgrammate.getInstance().remove(idAzione);
        azioniPianificate.remove(idAzione);
    }
}
