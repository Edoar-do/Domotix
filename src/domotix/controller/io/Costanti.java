package domotix.controller.io;

import java.io.File;

/**
 * Classe per la definizione di costanti di utilita' nel programma.
 * Si intende centralizzare la localizzazione delle costanti, in modo da individuar velocemente quella ricercata e il relativo valore.
 * Aggiungere qui le costanti di utilita' da definire.
 *
 * @author paolopasqua
 */
public final class Costanti {
    private Costanti() {}

    /*
    Costanti utilizzate per la costruzione dei percorsi in cui trovare i dati locali del programma
     */
    public static final String PERCORSO_RADICE_UTENTE = System.getProperty("user.home");
    public static final String PERCORSO_CARTELLA_APP = PERCORSO_RADICE_UTENTE + File.separator + ".DOMOTIX";

    public static final String PERCORSO_CARTELLA_DATI = PERCORSO_CARTELLA_APP + File.separator + "dati";
    public static final String PERCORSO_CARTELLA_LIBRERIA = PERCORSO_CARTELLA_APP + File.separator + "libreria";
    public static final String PERCORSO_CARTELLA_LIBRERIA_IMPORTATA = PERCORSO_CARTELLA_LIBRERIA + File.separator + "importata";

    public static final String NOME_CARTELLA_CATEGORIE_SENSORI = "categorie_sensori";
    public static final String NOME_CARTELLA_INFO_RILEVABILE = "info_rilevabile";
    public static final String NOME_CARTELLA_CATEGORIE_ATTUATORI = "categorie_attuatori";
    public static final String NOME_CARTELLA_MODALITA = "modalita";
    public static final String NOME_CARTELLA_UNITA_IMMOB = "unita_immob";
    public static final String NOME_CARTELLA_STANZE = "stanze";
    public static final String NOME_CARTELLA_ARTEFATTI = "artefatti";
    public static final String NOME_CARTELLA_REGOLE = "regole";
    public static final String NOME_CARTELLA_SENSORI = "sensori";
    public static final String NOME_CARTELLA_ATTUATORI = "attuatori";
    public static final String NOME_CARTELLA_AZIONI_PROGRAMMATE = "azioni_programmate";
    
}
