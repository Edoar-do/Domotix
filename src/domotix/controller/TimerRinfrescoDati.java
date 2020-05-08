package domotix.controller;

import domotix.controller.io.RinfrescaDati;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Classe di una sola istanza per le operazioni temporizzate di rinfresco dati del programma.
 *
 * @author paolopasqua
 */
public class TimerRinfrescoDati {

    public static final int DEFAULT_DELAY = 10000; //10 seconds

    private Timer timer;
    private int delay = DEFAULT_DELAY;
    private RinfrescaDati rinfrescaDati = null;

    public TimerRinfrescoDati(RinfrescaDati rinfrescaDati) {
        this.timer = new Timer(delay, this::action);
        this.timer.setRepeats(true);
        this.rinfrescaDati = rinfrescaDati;
    }

    private void action(ActionEvent e) {
        try {
            rinfrescaDati.rinfrescaSensori();
        } catch (Exception ex) {
            //ex.printStackTrace();
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
};
