package domotix.view.menus;

import domotix.controller.AperturaProgramma;


import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Classe per il menu di avvio del programma:
 * esegue le procedure di avvio predisposte dal controller e, in caso di errori,
 * pone la scelta di come proseguire all'utente.
 *
 * @author paolopasqua
 */
public class MenuApertura {

    private static final int CONTINUA = 0;

    private AperturaProgramma controller = null;
    private JTextArea area;
    private JScrollPane scrollPane;

    /**
     * Costruttore della classe.
     * @param apertura  istanza del controllore AperturaProgramma per eseguirne le procedure
     */
    public MenuApertura(AperturaProgramma apertura) {
        this.controller = apertura;
    }

    /**
     * Esegue le procedure di avvio del controller e presenta a video l'elenco di errori presentati
     * e il menu con le possibili azioni, sia per il caricamento dei dati che per le azioni in conflitto.
     * Ritorna l'esito della procedura: se proseguire con l'apertura o chiudere il programma per errori.
     *
     * @return  esito dell'apertura del programma: True = Continua; False = Esci
     */
    public boolean avvia() {
        if (this.controller.apri()) {
            return true;
        }
        else {
            area = new JTextArea(10, 40);
            area.setEditable(false);
            scrollPane = new JScrollPane(area);
            Object[] widgets = new Object[]{"Si sono verificati i seguenti errori: ", scrollPane, "Desideri procedere con l'apertura del programma? "};
            String[] opz = new String[]{"Sì", "No"};

            List<String> errori = this.controller.getErroriLettura();

            if (errori.isEmpty())
                area.append("Errori sconosciuti");
            else
                errori.forEach(s -> area.append(s + "\n"));

            int scelta = JOptionPane.showOptionDialog(null,
                    widgets,
                    "Errori Apertura Programma",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    opz,
                    null);

            return scelta == CONTINUA;
        }
    }

}