package domotix.controller;

import domotix.controller.util.StringUtil;
import domotix.model.*;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
/**
 * Classe per implementare una parte di logica controller relativa all'aggiunta e rimozione di entita'.
 * @author andrea e paolopasqua
 **/
public class Modificatore {

    /**
     * Aggiunge un parametro a una modalità operativa parametrica
     * @param nomeCategoria La categoria di attuatore
     * @param nomeModalita Il nome della modalità parametrica
     * @param nomeParametro Il nome del parametro
     * @param valore Il valore assunto dal parametro
     * @return True se l'aggiunta e' andata a buon fine
     */
    public static boolean aggiungiParametro(String nomeCategoria, String nomeModalita, String nomeParametro, double valore) {
        if (!Verificatore.checkValiditaParametro(nomeCategoria, nomeModalita, nomeParametro)) return false;
        CategoriaAttuatore categoria = Recuperatore.getCategoriaAttuatore(nomeCategoria);
        Modalita modalita = categoria.getModalita(nomeModalita);
        modalita.addParametro(new Parametro(nomeParametro, valore));
        return true;
    }

    /**
     * Aggiunge un'UnitaImmobiliare al model
     * @param nomeUnita Nome dell'UnitaImmobiliare da aggiungere
     * @return true se l'aggiunta e' andata a buon fine
     */
    public static boolean aggiungiUnitaImmobiliare(String nomeUnita) {
        if (!Verificatore.checkValiditaUnitaImmobiliare(nomeUnita)) return false;
        ElencoUnitaImmobiliari
                .getInstance()
                .add(new UnitaImmobiliare(nomeUnita));
        return true;
    }

    /**
     * Rimuove un'UnitaImmobiliare dal model
     * @param nomeUnita Nome dell'UnitaImmobiliare da rimuovere
     * @return true se la rimozione e' andata a buon fine
     */
    public static boolean rimuoviUnitaImmobiliare(String nomeUnita) {
        if (Recuperatore.getUnita(nomeUnita) == null) return false;
        ElencoUnitaImmobiliari.getInstance().remove(nomeUnita);
        return true;
    }

    public static boolean setModalitaOperativa(String nomeAttuatore, String nomeModalita) {
        if (!Verificatore.checkValiditaModalitaOperativaPerAttuatore(nomeAttuatore, nomeModalita)) return false;
        Attuatore attuatore = Recuperatore.getAttuatore(nomeAttuatore);
        CategoriaAttuatore categoriaAttuatore = attuatore.getCategoria();
        attuatore.setModoOp(categoriaAttuatore.getModalita(nomeModalita));
        return true;
    }


    public static boolean aggiungiInfoRilevabile(String nomeCat, String nome, boolean numerica) {
        if (!Verificatore.checkValiditaInfoRilevabile(nomeCat, nome)) return false;
        Recuperatore.getCategoriaSensore(nomeCat).addInformazioneRilevabile(new InfoRilevabile(nome, numerica));
        return true;
    }

    /**
     * Aggiunge una CategoriaSensore al model
     * @param nomeCat Nome della categoria da aggiungere
     * @param testoLibero Testo libero della categoria da aggiungere
     * @param infoRilevabile Informazione rilevabile dal sensore
     * @return true se l'aggiunta e' andata a buon fine
     */
    @Deprecated
    public static boolean aggiungiCategoriaSensore(String nomeCat, String testoLibero, String infoRilevabile) {
        if (!Verificatore.checkValiditaCategoriaSensore(nomeCat)) return false;
        ElencoCategorieSensori.getInstance().add(new CategoriaSensore(nomeCat, testoLibero, infoRilevabile));
        return true;
    }

    /**
     * Aggiunge una CategoriaSensore al model
     * @param nomeCat Nome della categoria da aggiungere
     * @param testoLibero Testo libero della categoria da aggiungere
     * @return true se l'aggiunta e' andata a buon fine
     */
    public static boolean aggiungiCategoriaSensore(String nomeCat, String testoLibero) {
        if (!Verificatore.checkValiditaCategoriaSensore(nomeCat)) return false;
        ElencoCategorieSensori.getInstance().add(new CategoriaSensore(nomeCat, testoLibero));
        return true;
    }

    /**
     * Rimuove una CategoriaSensore dal model
     * @param cat Nome della categoria da rimuovere
     * @return true se la rimozione e' andata a buon fine
     */
    public static boolean rimuoviCategoriaSensore(String cat) {
        if (Recuperatore.getCategoriaSensore(cat) == null) return false;
        ElencoCategorieSensori.getInstance().remove(cat);
        return true;
    }

    /**
     * Aggiunge una CategoriaAttuatore senza modalita' operative al model
     * @param nomeCat Nome della categoria da aggiungere
     * @param testoLibero Testo libero della categoria da aggiungere
     * @return true se l'aggiunta e' andata a buon fine
     */
    public static boolean aggiungiCategoriaAttuatore(String nomeCat, String testoLibero) {
        if (!Verificatore.checkValiditaCategoriaAttuatore(nomeCat)) return false;
        ElencoCategorieAttuatori.getInstance().add(new CategoriaAttuatore(nomeCat, testoLibero));
        return true;
    }

    /**
     * Aggiunge una ModalitaOperativa a una CategoriaAttuatore preesistente
     * @param nomeCat Nome della categoria da selezionare
     * @param nomeModalita Nome della modalita' da aggiungere
     * @return true se l'aggiunta e' andata a buon fine
     */
    public static boolean aggiungiModalitaCategoriaAttuatore(String nomeCat, String nomeModalita) {
        if (!Verificatore.checkValiditaModalitaOperativa(nomeModalita)) return false;
        Recuperatore.getCategoriaAttuatore(nomeCat).addModalita(new Modalita(nomeModalita));
        return true;
    }

    /**
     * Rimuove una CategoriaAttuatore dal model
     * @param cat Nome della categoria da rimuovere
     * @return true se la rimozione e' andata a buon fine
     */
    public static boolean rimuoviCategoriaAttuatore(String cat) {
        if (Recuperatore.getCategoriaAttuatore(cat) == null) return false;
        ElencoCategorieAttuatori.getInstance().remove(cat);
        return true;
    }

    /**
     * Aggiunge una Stanza all'UnitaImmobiliare specificata
     * @param nomeStanza Nome della stanza da aggiungere
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se l'aggiunta e' andata a buon fine
     */
    public static boolean aggiungiStanza(String nomeStanza, String unita) {
        if (!Verificatore.checkValiditaStanza(nomeStanza, unita)) return false;
        Recuperatore.getUnita(unita).addStanza(new Stanza(nomeStanza));
        return true;
    }

    /**
     * Rimuove una Stanza dall'UnitaImmobiliare specificata
     * @param stanza Nome della stanza da rimuovere
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se la rimozione e' andata a buon fine
     */
    public static boolean rimuoviStanza(String stanza, String unita) {
        UnitaImmobiliare unitaImm = Recuperatore.getUnita(unita);
        if (unitaImm == null)
            return false;

        unitaImm.removeStanza(stanza);
        return true;
    }

    /**
     * Aggiunge un Artefatto al model. Per l'aggiunta occorre specificare
     * Stanza e UnitaImmobiliare di appartenenza dell'artefatto.
     * @param artefatto Nome dell'artefatto da aggiungere
     * @param stanza Nome della stanza a cui aggiungere l'artefatto
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se l'aggiunta e' andata a buon fine
     */
    public static boolean aggiungiArtefatto(String artefatto, String stanza, String unita) {
        if (!Verificatore.checkValiditaArtefatto(artefatto, unita)) return false;
        Recuperatore.getStanza(stanza, unita).addArtefatto(new Artefatto(artefatto));
        return true;
    }

    /**
     * Rimuove un Artefatto dal model. Per la rimozione occorre specificare
     * Stanza e UnitaImmobiliare di appartenenza dell'artefatto.
     * @param artefatto Nome dell'artefatto da rimuovere
     * @param stanza Nome della stanza a cui rimuovere l'artefatto
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se la rimozione e' andata a buon fine
     */
    public static boolean rimuoviArtefatto(String artefatto, String stanza, String unita) {
        Stanza stanzaInst = Recuperatore.getStanza(stanza, unita);
        if (stanza == null)
            return false;

        stanzaInst.removeArtefatto(artefatto);
        return true;
    }

    /**
     * Aggiunge un Sensore all'interno di una stanza.
     * @param fantasia Nome di fantasia del sensore
     * @param categoria Nome della categoria (preesistente) del sensore
     * @param stanza Nome della stanza a cui aggiungere il sensore
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se l'aggiunta e' andata a buon fine
     */
    public static boolean aggiungiSensore(String fantasia, String categoria, String stanza, String unita) {
        String nomeComposto = StringUtil.componiNome(fantasia, categoria);
        if (!Verificatore.checkValiditaSensore(nomeComposto, categoria, stanza, unita)) return false;
        Sensore sensore = new Sensore(nomeComposto, Recuperatore.getCategoriaSensore(categoria));
        sensore.setStato(true);
        Recuperatore.getStanza(stanza, unita).addSensore(sensore);
        return true;
    }

    /**
     * Collega un Sensore preesistente a una stanza. In sostanza, permette
     * la condivisione di sensori tra artefatti e stanze.
     * @param nomeSensore Nome del sensore
     * @param nomeStanza Nome della stanza con cui condividere il sensore
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return true se la condivisione e' andata a buon fine
     */
    public static boolean collegaSensore(String nomeSensore, String nomeStanza, String nomeUnita) {
        Sensore sens = Recuperatore.getSensore(nomeSensore);
        Stanza stanza = Recuperatore.getStanza(nomeStanza, nomeUnita);

        if (sens == null)
            return false;
        if (stanza == null)
            return false;

        stanza.addSensore(sens);
        return true;
    }

    /**
     * Rimuove un Sensore da una stanza.
     * @param sensore Nome del sensore da rimuovere
     * @param stanza Nome della stanza a cui rimuovere il sensore
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se la rimozione e' andata a buon fine
     */
    public static boolean rimuoviSensore(String sensore, String stanza, String unita) {
        Stanza stanzaInst = Recuperatore.getStanza(stanza, unita);
        if (stanza == null)
            return false;

        Sensore sens = Recuperatore.getSensore(sensore);
        if (sens == null)
            return false;

        stanzaInst.removeSensore(sens);
        return true;
    }

    /**
     * Aggiunge un Sensore all'interno di un artefatto.
     * @param fantasia Nome di fantasia del sensore
     * @param categoria Nome della categoria (preesistente) del sensore
     * @param artefatto Nome dell'artefatto a cui aggiungere il sensore
     * @param stanza Nome della stanza selezionata
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se l'aggiunta e' andata a buon fine
     */
    public static boolean aggiungiSensore(String fantasia, String categoria, String artefatto, String stanza, String unita) {
        String nomeComposto = StringUtil.componiNome(fantasia, categoria);
        if (!Verificatore.checkValiditaSensore(nomeComposto, categoria, artefatto, stanza, unita)) return false;
        Sensore sensore = new Sensore(nomeComposto, Recuperatore.getCategoriaSensore(categoria));
        sensore.setStato(true);
        Recuperatore.getArtefatto(artefatto, stanza, unita).addSensore(sensore);
        return true;
    }

    /**
     * Collega un Sensore preesistente a un artefatto. In sostanza, permette
     * la condivisione di sensori tra artefatti e stanze.
     * @param nomeSensore Nome del sensore
     * @param nomeArtefatto Nome dell'artefatto con cui condividere il sensore
     * @param nomeStanza Nome della stanza selezionata
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return true se la condivisione e' andata a buon fine
     */
    public static boolean collegaSensore(String nomeSensore, String nomeArtefatto, String nomeStanza, String nomeUnita) {
        Sensore sens = Recuperatore.getSensore(nomeSensore);
        Artefatto artefatto = Recuperatore.getArtefatto(nomeArtefatto, nomeStanza, nomeUnita);

        if (sens == null)
            return false;
        if (artefatto == null)
            return false;

        artefatto.addSensore(sens);
        return true;
    }

    /**
     * Rimuove un Sensore da un Artefatto.
     * @param sensore Nome del sensore da rimuovere
     * @param artefatto Nome dell'artefatto a cui rimuovere il sensore
     * @param stanza Nome della stanza selezionata
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se la rimozione e' andata a buon fine
     */
    public static boolean rimuoviSensore(String sensore, String artefatto, String stanza, String unita) {
        Artefatto artefattoInst = Recuperatore.getArtefatto(artefatto, stanza, unita);
        if (stanza == null)
            return false;

        Sensore sens = Recuperatore.getSensore(sensore);
        if (sens == null)
            return false;

        artefattoInst.removeSensore(sens);
        return true;
    }

    /**
     * Aggiunge un Attuatore all'interno di una stanza.
     * @param fantasia Nome di fantasia dell'attuatore
     * @param categoria Nome della categoria (preesistente) dell'attuatore
     * @param stanza Nome della stanza a cui aggiungere l'attuatore
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se l'aggiunta e' andata a buon fine
     */
    public static boolean aggiungiAttuatore(String fantasia, String categoria, String stanza, String unita) {
        String nomeComposto = StringUtil.componiNome(fantasia, categoria);
        if (!Verificatore.checkValiditaAttuatore(nomeComposto, categoria, stanza, unita)) return false;
        Attuatore attuatore = new Attuatore(nomeComposto, Recuperatore.getCategoriaAttuatore(categoria), Recuperatore.getCategoriaAttuatore(categoria).getModalitaDefault());
        attuatore.setStato(true);
        Recuperatore.getStanza(stanza, unita).addAttuatore(attuatore);
        return true;
    }

    /**
     * Collega un Attuatore preesistente a una stanza. In sostanza, permette
     * la condivisione di attuatori tra artefatti e stanze.
     * @param nomeAttuatore Nome dell'attuatore
     * @param nomeStanza Nome della stanza con cui condividere l'attuatore
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return true se la condivisione e' andata a buon fine
     */
    public static boolean collegaAttuatore(String nomeAttuatore, String nomeStanza, String nomeUnita) {
        Attuatore attuatore = Recuperatore.getAttuatore(nomeAttuatore);
        Stanza stanza = Recuperatore.getStanza(nomeStanza, nomeUnita);

        if (attuatore == null)
            return false;
        if (stanza == null)
            return false;

        stanza.addAttuatore(attuatore);
        return true;
    }

    /**
     * Rimuove un Attuatore da una stanza.
     * @param attuatore Nome dell'attuatore da rimuovere
     * @param stanza Nome della stanza a cui rimuovere l'attuatore
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se la rimozione e' andata a buon fine
     */
    public static boolean rimuoviAttuatore(String attuatore, String stanza, String unita) {
        Stanza stanzaInst = Recuperatore.getStanza(stanza, unita);
        if (stanza == null)
            return false;

        Attuatore att = Recuperatore.getAttuatore(attuatore);
        if (att == null)
            return false;

        stanzaInst.removeAttuatore(att);
        return true;
    }

    /**
     * Aggiunge un Attuatore all'interno di un artefatto.
     * @param fantasia Nome di fantasia del sensore
     * @param categoria Nome della categoria (preesistente) dell'attuatore
     * @param artefatto Nome dell'artefatto a cui aggiungere l'attuatore
     * @param stanza Nome della stanza selezionata
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se l'aggiunta e' andata a buon fine
     */
    public static boolean aggiungiAttuatore(String fantasia, String categoria, String artefatto, String stanza, String unita) {
        String nomeComposto = StringUtil.componiNome(fantasia, categoria);
        if (!Verificatore.checkValiditaAttuatore(nomeComposto, categoria, artefatto, stanza, unita)) return false;
        Attuatore attuatore = new Attuatore(nomeComposto, Recuperatore.getCategoriaAttuatore(categoria), Recuperatore.getCategoriaAttuatore(categoria).getModalitaDefault());
        attuatore.setStato(true);
        Recuperatore.getArtefatto(artefatto, stanza, unita).addAttuatore(attuatore);
        return true;
    }

    /**
     * Collega un Attuatore preesistente a un artefatto. In sostanza, permette
     * la condivisione di attuatore tra artefatti e stanze.
     * @param nomeAttuatore Nome dell'attuatore
     * @param nomeArtefatto Nome dell'artefatto con cui condividere il sensore
     * @param nomeStanza Nome della stanza selezionata
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return true se la condivisione e' andata a buon fine
     */
    public static boolean collegaAttuatore(String nomeAttuatore, String nomeArtefatto, String nomeStanza, String nomeUnita) {
        Attuatore attuatore = Recuperatore.getAttuatore(nomeAttuatore);
        Artefatto artefatto = Recuperatore.getArtefatto(nomeArtefatto, nomeStanza, nomeUnita);

        if (attuatore == null)
            return false;
        if (artefatto == null)
            return false;

        artefatto.addAttuatore(attuatore);
        return true;
    }

    /**
     * Rimuove un Attuatore da un Atyrfatto.
     * @param attuatore Nome dell'attuatore da rimuovere
     * @param artefatto Nome dell'artefatto a cui rimuovere l'attuatore
     * @param stanza Nome della stanza selezionata
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se la rimozione e' andata a buon fine
     */
    public static boolean rimuoviAttuatore(String attuatore, String artefatto, String stanza, String unita) {
        Artefatto artefattoInst = Recuperatore.getArtefatto(artefatto, stanza, unita);
        if (stanza == null)
            return false;

        Attuatore att = Recuperatore.getAttuatore(attuatore);
        if (att == null)
            return false;

        artefattoInst.removeAttuatore(att);
        return true;
    }
}
