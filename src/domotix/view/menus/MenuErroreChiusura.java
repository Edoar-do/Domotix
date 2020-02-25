package domotix.view.menus;

import domotix.view.MyMenu;

import java.util.List;

public class MenuErroreChiusura {

    /** Scelta dell'utente di uscire **/
    public static final int ESCI = 0;
    /** Scelta dell'utente di annullare la chiusura **/
    public static final int ANNULLA_CHIUSURA = 1;
    /** Scelta dell'utente di ritentare il salvataggio **/
    public static final int RITENTA_SALVATAGGIO = 2;

    private static MyMenu erroreChiusura = new MyMenu("Proseguire con l'apertura del programma?", new String[]{"Annulla chiusura", "Ritenta salvataggio"});

    /**
     * Avvia il menu stampando a video l'elenco di errori presentati e il menu con le possibili azioni.
     * Ritorna al chiamante l'esito della scelta fatta dall'utente.
     *
     * @param errori    elenco di errori riscontrati in apertura del programma.
     * @return  esito della scelta dell'utente: 0 -> Esci; 1 -> Annulla chiusura; 2 -> Ritenta salvataggio
     */
    public static int avvia(List<String> errori) {
        System.out.println("Errore/i in salvataggio dati salvati:");
        if (errori.isEmpty())
            System.out.println("errore sconosciuto");
        else
            errori.forEach(s -> System.out.println(s));

        return erroreChiusura.scegli();
    }

}
