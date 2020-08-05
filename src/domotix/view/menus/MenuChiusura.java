package domotix.view.menus;

import domotix.controller.ChiusuraProgramma;


import javax.swing.*;
import java.util.List;

/**
 * Classe di una sola istanza per il menu di scelta dell'utente su come proseguire in caso di errori in chiusura.
 * @author paolopasqua
 */
public class MenuChiusura {

    /** Scelta dell'utente di uscire **/
    public static final int ESCI = 0;
    /** Scelta dell'utente di annullare la chiusura **/
    public static final int ANNULLA_CHIUSURA = 1;
    /** Scelta dell'utente di ritentare il salvataggio **/
    public static final int RITENTA_SALVATAGGIO = 2;

    private ChiusuraProgramma controller = null;
    private JTextArea area;
    private JScrollPane scrollPane;

    /**
     * Costruttore della classe.
     * 
     * @param chiusura  istanza del controller ChiusuraProgramma
     */
    public MenuChiusura(ChiusuraProgramma chiusura) {
        this.controller = chiusura;
    }

    /**
     * Avvia il menu eseguendo le procedure del controller per la chiusura del programma.
     * Stampa a video l'elenco di errori, se presenti, e il menu con le possibili azioni.
     * Il tentare nuovamente il salvataggio dei dati e' gestito internamente.
     *
     * @return  esito della scelta dell'utente: True = Esci; False = Annulla chiusura
     */
    public boolean avvia() {
        int esito = RITENTA_SALVATAGGIO;

        while(esito != ESCI && esito != ANNULLA_CHIUSURA) {
            if (this.controller.chiudi()) {
                esito = ESCI;
            }
            else {
                area = new JTextArea(10, 40);
                area.setEditable(false);
                scrollPane = new JScrollPane(area);
                Object[] widgets = new Object[]{"Si sono verificati i seguenti errori: ", scrollPane, "Come desideri procedere? "};
                String[] opz = new String[]{"Esci", "Annulla Chiusura", "Ritenta Salvataggio"};

                List<String> errori = this.controller.getErroriScrittura();

                if (errori.isEmpty())
                    area.append("Errori sconosciuti");
                else
                    errori.forEach(s -> area.append(s + "\n"));
                
                esito = JOptionPane.showOptionDialog(null,
                        widgets,
                        "Errori Chiusura Programma",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        opz,
                        null);
            }
        }

        if (esito == ANNULLA_CHIUSURA)
            this.controller.pulisciErrori();
            
        return esito == ESCI;
    }

}
