package domotix.controller;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.regole.Regola;
import domotix.model.bean.regole.StatoRegola;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Classe di una sola istanza per le operazioni temporizzate di gestione delle regole del programma.
 *
 * @author paolopasqua
 */
public class TimerGestioneRegole {

    public static final int DEFAULT_DELAY = 10000; //10 seconds

    private Timer timer;
    private int delay = DEFAULT_DELAY;
    private Recuperatore recuperatore = null;

    public TimerGestioneRegole(Recuperatore recuperatore) {
        this.timer = new Timer(delay, this::action);
        this.timer.setRepeats(true);
        this.recuperatore = recuperatore;
    }

    private void action(ActionEvent e) {
        //controllo le regole per ciascuna unita immobiliare presente
        for (UnitaImmobiliare u : recuperatore.getListaUnita()) {
            for (Regola r : u.getRegole()) {
                //se la regola e' attiva e l'antecedente risulta verificato allora eseguo
                if (r.getStato().equals(StatoRegola.ATTIVA)) {
                    r.esegui();
                }
            }
        }
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
        timer.start();
    }

    /**
     * Stoppa il timer
     */
    public void stop() {
        timer.stop();
    }
}
