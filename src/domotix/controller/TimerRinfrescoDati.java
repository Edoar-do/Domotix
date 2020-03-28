package domotix.controller;

import domotix.model.ElencoSensori;
import domotix.model.io.RinfrescaDati;

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

    private static TimerRinfrescoDati instance = null;

    /**
     * Recupera la unica istanza della classe
     * @return  unica istanza della classe
     */
    public static TimerRinfrescoDati getInstance() {
        if (instance == null)
            instance = new TimerRinfrescoDati();
        return instance;
    }

    private TimerRinfrescoDati() {
        this.timer = new Timer(delay, this::action);
        this.timer.setRepeats(true);
    }

    private void action(ActionEvent e) {
        try {
            RinfrescaDati.getInstance().rinfrescaSensori(ElencoSensori.getInstance());
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
