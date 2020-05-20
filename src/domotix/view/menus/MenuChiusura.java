package domotix.view.menus;

import domotix.controller.ChiusuraProgramma;
import domotix.view.MyMenu;

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

    private MyMenu erroreChiusura = null;
    private ChiusuraProgramma controller = null;

    /**
     * Costruttore della classe.
     * 
     * @param chiusura  istanza del controller ChiusuraProgramma
     */
    public MenuChiusura(ChiusuraProgramma chiusura) {
        this.controller = chiusura;
        this.erroreChiusura = new MyMenu("Proseguire con l'apertura del programma?", new String[]{"Annulla chiusura", "Ritenta salvataggio"});
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
                List<String> errori = this.controller.getErroriScrittura();

                System.out.println("Errore/i in salvataggio dati salvati:");
                if (errori.isEmpty())
                    System.out.println("\tErrore sconosciuto");
                else
                    errori.forEach(s -> System.out.println("\t"+s));
                
                esito = this.erroreChiusura.scegli();
            }
        }

        if (esito == ANNULLA_CHIUSURA)
            this.controller.pulisciErrori();
            
        return esito == ESCI;
    }

}
