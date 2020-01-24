package domotix.model.io.datilocali;

import domotix.model.util.Costanti;

import java.io.File;

/**
 * Classe che implementa il pattern Singleton per la gestione dei percorsi dei file locali in cui sono salvati i dati del programma.
 * Si intende in questo modo centralizzare la generazione dei percorsi e le codifiche delle chiavi identificative.
 * Aggiungere qui la gestione di percorsi per eventuali nuove entita' salvate.
 *
 * @author paolopasqua
 * @see DatiLocali
 */
public class PercorsiFile {

    private static PercorsiFile instance = null;

    public static PercorsiFile getInstance() {
        if (instance == null)
            instance = new PercorsiFile();
        return instance;
    }

    private PercorsiFile() {
    }

    private String componi (String first, String ...str) {
        String res = first == null ? "" : first;

        for (String s : str)
            res += "_" + (s == null ? "" : s);

        return res;
    }

    /**
     * Genera il percorso del file specifico per un'entita' CategoriaSensore identificata dalla stringa passata.
     * @param cat   identificativo stringa dell'entita'
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.CategoriaSensore
     */
    public String getCategoriaSensore(String cat) {
        return Costanti.PERCORSO_CARTELLA_CATEGORIE_SENSORI + File.separator + getNomeCategoriaSensore(cat);
    }

    /**
     * Genera il nome del file specifico per un'entita' CategoriaSensore identificata dalla stringa passata
     * @param cat identificativo stringa dell'entita'
     * @return  Nome del file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.CategoriaSensore
     */
    public String getNomeCategoriaSensore(String cat) {
        return cat == null ? "" : cat;
    }

    /**
     * Genera il percorso del file specifico per un'entita' CategoriaAttuatore identificata dalla stringa passata.
     * @param cat   identificativo stringa dell'entita'
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.CategoriaAttuatore
     */
    public String getCategoriaAttuatore(String cat) {
        return Costanti.PERCORSO_CARTELLA_CATEGORIE_ATTUATORI + File.separator + getNomeCategoriaAttuatore(cat);
    }

    /**
     * Genera il nome del file specifico per un'entita' CategoriaAttuatore identificata dalla stringa passata
     * @param cat identificativo stringa dell'entita'
     * @return  Nome del file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.CategoriaAttuatore
     */
    public String getNomeCategoriaAttuatore(String cat) {
        return cat == null ? "" : cat;
    }

    /**
     * Genera il percorso del file specifico per un'entita' Modalita identificata dalle stringhe passata.
     * @param modalita  identificativo stringa della modalita'
     * @param cat   identificativo stringa della categoria inerente alla modalita
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.Modalita
     */
    public String getModalita(String modalita, String cat) {
        return Costanti.PERCORSO_CARTELLA_MODALITA + File.separator + getNomeModalita(modalita, cat);
    }

    /**
     * Genera il nome del file specifico per un'entita' Modalita identificata dalla stringa passata
     * @param modalita identificativo stringa dell'entita'
     * @param cat   identificativo stringa della categoria inerente alla modalita
     * @return  Nome del file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.Modalita
     */
    public String getNomeModalita(String modalita, String cat) {
        return componi(modalita, cat);
    }

    /**
     * Genera il percorso del file specifico per un'entita' UnitaImmobiliare identificata dalla stringa passata.
     * @param unita   identificativo stringa dell'entita'
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.UnitaImmobiliare
     */
    public String getUnitaImmobiliare(String unita) {
        return Costanti.PERCORSO_CARTELLA_UNITA_IMMOB + File.separator + getNomeUnitaImmobiliare(unita);
    }

    /**
     * Genera il nome del file specifico per un'entita' UnitaImmobiliare identificata dalla stringa passata
     * @param unita identificativo stringa dell'entita'
     * @return  Nome del file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.UnitaImmobiliare
     */
    public String getNomeUnitaImmobiliare(String unita) {
        return unita == null ? "" : unita;
    }

    /**
     * Genera il percorso del file specifico per un'entita' Stanza identificata dalle stringhe passate.
     * @param stanza   identificativo stringa della stanza
     * @param unita     identificativo stringa dell'unita' immobiliare contenente la stanza
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.system.Stanza
     */
    public String getStanza(String stanza, String unita) {
        return Costanti.PERCORSO_CARTELLA_STANZE + File.separator + getNomeStanza(stanza, unita);
    }

    /**
     * Genera il nome del file specifico per un'entita' Stanza identificata dalla stringa passata
     * @param stanza identificativo stringa dell'entita'
     * @param unita     identificativo stringa dell'unita' immobiliare contenente la stanza
     * @return  Nome del file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.system.Stanza
     */
    public String getNomeStanza(String stanza, String unita) {
        return componi(stanza, unita);
    }

    /**
     * Genera il percorso del file specifico per un'entita' Artefatto identificata dalle stringhe passate.
     * @param artefatto   identificativo stringa della stanza
     * @param unita     identificativo stringa dell'unita' immobiliare contenente l'artefatto'
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.system.Artefatto
     */
    public String getArtefatto(String artefatto, String unita) {
        return Costanti.PERCORSO_CARTELLA_ARTEFATTI + File.separator + getNomeArtefatto(artefatto, unita);
    }

    /**
     * Genera il nome del file specifico per un'entita' Artefatto identificata dalla stringa passata
     * @param artefatto identificativo stringa dell'entita'
     * @param unita     identificativo stringa dell'unita' immobiliare contenente l'artefatto'
     * @return  Nome del file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.system.Artefatto
     */
    public String getNomeArtefatto(String artefatto, String unita) {
        return componi(artefatto, unita);
    }

    /**
     * Genera il percorso del file specifico per un'entita' Sensore identificata dalla stringa passata.
     * @param sensore   identificativo stringa del sensore
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.Sensore
     */
    public String getSensore(String sensore) {
        return Costanti.PERCORSO_CARTELLA_SENSORI + File.separator + getNomeSensore(sensore);
    }

    /**
     * Genera il nome del file specifico per un'entita' Sensore identificata dalla stringa passata
     * @param sensore identificativo stringa dell'entita'
     * @return  Nome del file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.Sensore
     */
    public String getNomeSensore(String sensore) {
        return sensore == null ? "" : sensore;
    }

    /**
     * Genera il percorso del file specifico per un'entita' Attuatore identificata dalla stringa passata.
     * @param attuatore   identificativo stringa dell'attuatore
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.Attuatore
     */
    public String getAttuatore(String attuatore) {
        return Costanti.PERCORSO_CARTELLA_ATTUATORI + File.separator + getNomeAttuatore(attuatore);
    }

    /**
     * Genera il nome del file specifico per un'entita' Attuatore identificata dalla stringa passata
     * @param attuatore identificativo stringa dell'entita'
     * @return  Nome del file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.Attuatore
     */
    public String getNomeAttuatore(String attuatore) {
        return attuatore == null ? "" : attuatore;
    }

}
