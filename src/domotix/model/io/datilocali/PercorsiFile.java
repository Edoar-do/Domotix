package domotix.model.io.datilocali;

import domotix.model.util.Costanti;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che implementa il pattern Singleton per la gestione dei percorsi dei file locali in cui sono salvati i dati del programma.
 * Si intende in questo modo centralizzare la generazione dei percorsi e le codifiche delle chiavi identificative.
 * Aggiungere qui la gestione di percorsi per eventuali nuove entita' salvate.
 *
 * @author paolopasqua
 * @see DatiLocali
 */
public class PercorsiFile {

    private static final String NOME_GENERICO_ENTITA = ".*";

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
        return Costanti.PERCORSO_CARTELLA_CATEGORIE_SENSORI + File.separator + componiNomeCategoriaSensore(cat);
    }

    /**
     * Genera il nome del file specifico per un'entita' CategoriaSensore identificata dalla stringa passata
     * @param cat identificativo stringa dell'entita'
     * @return  Nome del file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.CategoriaSensore
     */
    public String componiNomeCategoriaSensore(String cat) {
        return cat == null ? "" : cat;
    }

    /**
     * Ritorna una lista di nomi delle entita' presenti nei file memorizzati
     *
     * @return  Lista di nomi delle entita' presenti
     * @see domotix.model.bean.device.CategoriaSensore
     */
    public List<String> getNomiCategorieSensori() {
        ArrayList<String> ret = new ArrayList<>();
        File cartella = new File(Costanti.PERCORSO_CARTELLA_CATEGORIE_SENSORI);

        for(File f : cartella.listFiles()) {
            ret.add(f.getName());
        }

        return ret;
    }

    /**
     * Genera il percorso del file specifico per un'entita' CategoriaAttuatore identificata dalla stringa passata.
     * @param cat   identificativo stringa dell'entita'
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.CategoriaAttuatore
     */
    public String getCategoriaAttuatore(String cat) {
        return Costanti.PERCORSO_CARTELLA_CATEGORIE_ATTUATORI + File.separator + componiNomeCategoriaAttuatore(cat);
    }

    /**
     * Genera il nome del file specifico per un'entita' CategoriaAttuatore identificata dalla stringa passata
     * @param cat identificativo stringa dell'entita'
     * @return  Nome del file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.CategoriaAttuatore
     */
    public String componiNomeCategoriaAttuatore(String cat) {
        return cat == null ? "" : cat;
    }

    /**
     * Ritorna una lista di nomi delle entita' presenti nei file memorizzati
     *
     * @return  Lista di nomi delle entita' presenti
     * @see domotix.model.bean.device.CategoriaAttuatore
     */
    public List<String> getNomiCategorieAttuatori() {
        ArrayList<String> ret = new ArrayList<>();
        File cartella = new File(Costanti.PERCORSO_CARTELLA_CATEGORIE_ATTUATORI);

        for(File f : cartella.listFiles()) {
            ret.add(f.getName());
        }

        return ret;
    }

    /**
     * Genera il percorso del file specifico per un'entita' Modalita identificata dalle stringhe passata.
     * @param modalita  identificativo stringa della modalita'
     * @param cat   identificativo stringa della categoria inerente alla modalita
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.Modalita
     */
    public String getModalita(String modalita, String cat) {
        return Costanti.PERCORSO_CARTELLA_MODALITA + File.separator + componiNomeModalita(modalita, cat);
    }

    /**
     * Genera il nome del file specifico per un'entita' Modalita identificata dalla stringa passata
     * @param modalita identificativo stringa dell'entita'
     * @param cat   identificativo stringa della categoria inerente alla modalita
     * @return  Nome del file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.Modalita
     */
    public String componiNomeModalita(String modalita, String cat) {
        return componi(modalita, cat);
    }

    /**
     * Ritorna una lista di nomi delle entita' presenti nei file memorizzati
     *
     * @return  Lista di nomi delle entita' presenti
     * @see domotix.model.bean.device.Modalita
     */
    public List<String> getNomiModalita(String categoriaAttuatore) {
        ArrayList<String> ret = new ArrayList<>();
        File cartella = new File(Costanti.PERCORSO_CARTELLA_MODALITA);
        File[] elencoFile = cartella.listFiles((dir, name) -> name.matches(componiNomeModalita(NOME_GENERICO_ENTITA, categoriaAttuatore)));

        for(File f : elencoFile) {
            int indice = f.getName().indexOf(categoriaAttuatore) - 1;
            if (indice >= 0) {
                String scomposto = f.getName().substring(0, indice); //rimuovo nome categoria dal nome file
                ret.add(scomposto);
            }
        }

        return ret;
    }

    /**
     * Genera il percorso del file specifico per un'entita' UnitaImmobiliare identificata dalla stringa passata.
     * @param unita   identificativo stringa dell'entita'
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.UnitaImmobiliare
     */
    public String getUnitaImmobiliare(String unita) {
        return Costanti.PERCORSO_CARTELLA_UNITA_IMMOB + File.separator + componiNomeUnitaImmobiliare(unita);
    }

    /**
     * Genera il nome del file specifico per un'entita' UnitaImmobiliare identificata dalla stringa passata
     * @param unita identificativo stringa dell'entita'
     * @return  Nome del file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.UnitaImmobiliare
     */
    public String componiNomeUnitaImmobiliare(String unita) {
        return unita == null ? "" : unita;
    }

    /**
     * Ritorna una lista di nomi delle entita' presenti nei file memorizzati
     *
     * @return  Lista di nomi delle entita' presenti
     * @see domotix.model.bean.UnitaImmobiliare
     */
    public List<String> getNomiUnitaImmobiliare() {
        ArrayList<String> ret = new ArrayList<>();
        File cartella = new File(Costanti.PERCORSO_CARTELLA_UNITA_IMMOB);

        for(File f : cartella.listFiles()) {
            ret.add(f.getName());
        }

        return ret;
    }

    /**
     * Genera il percorso del file specifico per un'entita' Stanza identificata dalle stringhe passate.
     * @param stanza   identificativo stringa della stanza
     * @param unita     identificativo stringa dell'unita' immobiliare contenente la stanza
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.system.Stanza
     */
    public String getStanza(String stanza, String unita) {
        return Costanti.PERCORSO_CARTELLA_STANZE + File.separator + componiNomeStanza(stanza, unita);
    }

    /**
     * Genera il nome del file specifico per un'entita' Stanza identificata dalla stringa passata
     * @param stanza identificativo stringa dell'entita'
     * @param unita     identificativo stringa dell'unita' immobiliare contenente la stanza
     * @return  Nome del file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.system.Stanza
     */
    public String componiNomeStanza(String stanza, String unita) {
        return componi(stanza, unita);
    }

    /**
     * Ritorna una lista di nomi delle entita' presenti nei file memorizzati
     *
     * @return  Lista di nomi delle entita' presenti
     * @see domotix.model.bean.system.Stanza
     */
    public List<String> getNomiStanze(String unitaImmobiliare) {
        ArrayList<String> ret = new ArrayList<>();
        File cartella = new File(Costanti.PERCORSO_CARTELLA_STANZE);
        File[] elencoFile = cartella.listFiles((dir, name) -> name.matches(componiNomeStanza(NOME_GENERICO_ENTITA, unitaImmobiliare)));

        for(File f : elencoFile) {
            int indice = f.getName().indexOf(unitaImmobiliare) - 1;
            if (indice >= 0) {
                String scomposto = f.getName().substring(0, indice); //rimuovo nome unitaImmob dal nome file
                ret.add(scomposto);
            }
        }

        return ret;
    }

    /**
     * Genera il percorso del file specifico per un'entita' Artefatto identificata dalle stringhe passate.
     * @param artefatto   identificativo stringa della stanza
     * @param unita     identificativo stringa dell'unita' immobiliare contenente l'artefatto'
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.system.Artefatto
     */
    public String getArtefatto(String artefatto, String unita) {
        return Costanti.PERCORSO_CARTELLA_ARTEFATTI + File.separator + componiNomeArtefatto(artefatto, unita);
    }

    /**
     * Genera il nome del file specifico per un'entita' Artefatto identificata dalla stringa passata
     * @param artefatto identificativo stringa dell'entita'
     * @param unita     identificativo stringa dell'unita' immobiliare contenente l'artefatto'
     * @return  Nome del file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.system.Artefatto
     */
    public String componiNomeArtefatto(String artefatto, String unita) {
        return componi(artefatto, unita);
    }

    /**
     * Ritorna una lista di nomi delle entita' presenti nei file memorizzati
     *
     * @return  Lista di nomi delle entita' presenti
     * @see domotix.model.bean.system.Artefatto
     */
    public List<String> getNomiArtefatti(String unitaImmobiliare) {
        ArrayList<String> ret = new ArrayList<>();
        File cartella = new File(Costanti.PERCORSO_CARTELLA_ARTEFATTI);
        File[] elencoFile = cartella.listFiles((dir, name) -> name.matches(componiNomeArtefatto(NOME_GENERICO_ENTITA, unitaImmobiliare)));

        for(File f : elencoFile) {
            int indice = f.getName().indexOf(unitaImmobiliare) - 1;
            if (indice >= 0) {
                String scomposto = f.getName().substring(0, indice); //rimuovo nome unitaImmob dal nome file
                ret.add(scomposto);
            }
        }

        return ret;
    }

    /**
     * Genera il percorso del file specifico per un'entita' Sensore identificata dalla stringa passata.
     * @param sensore   identificativo stringa del sensore
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.Sensore
     */
    public String getSensore(String sensore) {
        return Costanti.PERCORSO_CARTELLA_SENSORI + File.separator + componiNomeSensore(sensore);
    }

    /**
     * Genera il nome del file specifico per un'entita' Sensore identificata dalla stringa passata
     * @param sensore identificativo stringa dell'entita'
     * @return  Nome del file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.Sensore
     */
    public String componiNomeSensore(String sensore) {
        return sensore == null ? "" : sensore;
    }

    /**
     * Ritorna una lista di nomi delle entita' presenti nei file memorizzati
     *
     * @return  Lista di nomi delle entita' presenti
     * @see domotix.model.bean.device.Sensore
     */
    public List<String> getNomiSensori() {
        ArrayList<String> ret = new ArrayList<>();
        File cartella = new File(Costanti.PERCORSO_CARTELLA_SENSORI);

        for(File f : cartella.listFiles()) {
            ret.add(f.getName()); //nome composto con categoria e' da mantenere tale
        }

        return ret;
    }

    /**
     * Genera il percorso del file specifico per un'entita' Attuatore identificata dalla stringa passata.
     * @param attuatore   identificativo stringa dell'attuatore
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.Attuatore
     */
    public String getAttuatore(String attuatore) {
        return Costanti.PERCORSO_CARTELLA_ATTUATORI + File.separator + componiNomeAttuatore(attuatore);
    }

    /**
     * Genera il nome del file specifico per un'entita' Attuatore identificata dalla stringa passata
     * @param attuatore identificativo stringa dell'entita'
     * @return  Nome del file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.Attuatore
     */
    public String componiNomeAttuatore(String attuatore) {
        return attuatore == null ? "" : attuatore;
    }

    /**
     * Ritorna una lista di nomi delle entita' presenti nei file memorizzati
     *
     * @return  Lista di nomi delle entita' presenti
     * @see domotix.model.bean.device.Attuatore
     */
    public List<String> getNomiAttuatori() {
        ArrayList<String> ret = new ArrayList<>();
        File cartella = new File(Costanti.PERCORSO_CARTELLA_ATTUATORI);

        for(File f : cartella.listFiles()) {
            ret.add(f.getName()); //nome composto con categoria e' da mantenere tale
        }

        return ret;
    }

}
