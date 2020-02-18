package domotix.view.menus;

import domotix.logicUtil.MyMenu;

import java.util.List;

/**
 * Classe di una sola istanza per il menu di scelta dell'utente su come proseguire in caso di errori in apertura.
 */
public class MenuErroreApertura {

    /** Scelta dell'utente di uscire **/
    public static final int ESCI = 0;
    /** Scelta dell'utente di continuare **/
    public static final int CONTINUA = 1;

    private static MyMenu erroreApertura = new MyMenu("Proseguire con l'apertura del programma?", new String[]{"Continua"});

    /**
     * Avvia il menu stampando a video l'elenco di errori presentati e il menu con le possibili azioni.
     * Ritorna al chiamante l'esito della scelta fatta dall'utente.
     *
     * @param errori    elenco di errori riscontrati in apertura del programma.
     * @return  esito della scelta dell'utente: 0 -> Esci; 1 -> Continua
     */
    public static int avvia(List<String> errori) {
        System.out.println("Errore/i in lettura dati salvati:");
        if (errori.isEmpty())
            System.out.println("errore sconosciuto");
        else
            errori.forEach(s -> System.out.println(s));

        return erroreApertura.scegli();
    }

}
