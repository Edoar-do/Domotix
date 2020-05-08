package domotix.view.menus;

import domotix.controller.AperturaProgramma;
import domotix.view.MyMenu;

import java.util.List;

/**
 * Classe per il menu di avvio del programma:
 * esegue le procedure di avvio predisposte dal controller e, in caso di errori,
 * pone la scelta di come proseguire all'utente.
 * 
 * @author paolopasqua
 */
public class MenuApertura {

    /** Scelta dell'utente di uscire **/
    public static final int ESCI = 0;
    /** Scelta dell'utente di continuare **/
    public static final int CONTINUA = 1;

    private MyMenu erroreApertura = null;
    private AperturaProgramma controller = null;

    /**
     * Costruttore della classe.
     * 
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
            erroreApertura = new MyMenu("Proseguire con l'apertura del programma?", new String[]{"Continua"});
            List<String> errori = this.controller.getErroriLettura();

            System.out.println("Errore/i in lettura dati salvati:");
            if (errori.isEmpty())
                System.out.println("Errore sconosciuto");
            else
                errori.forEach(s -> System.out.println(s));

            return erroreApertura.scegli() == CONTINUA;
        }
    }

}
