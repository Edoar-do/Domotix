package domotix.controller;

import domotix.controller.util.StringUtil;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.regole.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe per implementare una parte di logica controller relativa all'aggiunta e rimozione di entita'.
 * @author andrea, paolopasqua, Edoardo Coppola
 **/
public class Interpretatore {
    private Modificatore modificatore;

    public Interpretatore(Modificatore modificatore) {
        this.modificatore = modificatore;
    }

    /**
     * Aggiunge un parametro a una modalità operativa parametrica
     * @param nomeCategoria La categoria di attuatore
     * @param nomeModalita Il nome della modalità parametrica
     * @param nomeParametro Il nome del parametro
     * @param valore Il valore assunto dal parametro
     * @return True se l'aggiunta e' andata a buon fine
     */
    public boolean aggiungiParametro(String nomeCategoria, String nomeModalita, String nomeParametro, double valore) {
        return modificatore.aggiungiParametro(nomeCategoria, nomeModalita, new Parametro(nomeParametro, valore));
    }

    /**
     * Aggiunge un'UnitaImmobiliare al model
     * @param nomeUnita Nome dell'UnitaImmobiliare da aggiungere
     * @return true se l'aggiunta e' andata a buon fine
     */
    public boolean aggiungiUnitaImmobiliare(String nomeUnita) {
        return modificatore.aggiungiUnitaImmobiliare(new UnitaImmobiliare(nomeUnita));
    }

    /**
     * Rimuove un'UnitaImmobiliare dal model
     * @param nomeUnita Nome dell'UnitaImmobiliare da rimuovere
     * @return true se la rimozione e' andata a buon fine
     */
    public boolean rimuoviUnitaImmobiliare(String nomeUnita) {
        return modificatore.rimuoviUnitaImmobiliare(nomeUnita);
    }

    public boolean setModalitaOperativa(String nomeAttuatore, String nomeModalita) {
        return modificatore.setModalitaOperativa(nomeAttuatore, nomeModalita);
    }


    public boolean aggiungiInfoRilevabile(String nomeCat, String nome, boolean numerica) {
        return modificatore.aggiungiInfoRilevabile(nomeCat, new InfoRilevabile(nome, numerica));
    }

    /**
     * Aggiunge una CategoriaSensore al model
     * @param nomeCat Nome della categoria da aggiungere
     * @param testoLibero Testo libero della categoria da aggiungere
     * @return true se l'aggiunta e' andata a buon fine
     */
    public boolean aggiungiCategoriaSensore(String nomeCat, String testoLibero) {
        return modificatore.aggiungiCategoriaSensore(new CategoriaSensore(nomeCat, testoLibero));
    }

    /**
     * Rimuove una CategoriaSensore dal model
     * @param cat Nome della categoria da rimuovere
     * @return true se la rimozione e' andata a buon fine
     */
    public boolean rimuoviCategoriaSensore(String cat) {
        return modificatore.rimuoviCategoriaSensore(cat);
    }

    /**
     * Aggiunge una CategoriaAttuatore senza modalita' operative al model
     * @param nomeCat Nome della categoria da aggiungere
     * @param testoLibero Testo libero della categoria da aggiungere
     * @return true se l'aggiunta e' andata a buon fine
     */
    public boolean aggiungiCategoriaAttuatore(String nomeCat, String testoLibero) {
        return modificatore.aggiungiCategoriaAttuatore(new CategoriaAttuatore(nomeCat, testoLibero));
    }

    /**
     * Aggiunge una ModalitaOperativa a una CategoriaAttuatore preesistente
     * @param nomeCat Nome della categoria da selezionare
     * @param nomeModalita Nome della modalita' da aggiungere
     * @return true se l'aggiunta e' andata a buon fine
     */
    public boolean aggiungiModalitaCategoriaAttuatore(String nomeCat, String nomeModalita) {
        return modificatore.aggiungiModalitaCategoriaAttuatore(nomeCat, new Modalita(nomeModalita));
    }

    /**
     * Rimuove una CategoriaAttuatore dal model
     * @param cat Nome della categoria da rimuovere
     * @return true se la rimozione e' andata a buon fine
     */
    public boolean rimuoviCategoriaAttuatore(String cat) {
        return modificatore.rimuoviCategoriaAttuatore(cat);
    }

    /**
     * Aggiunge una Stanza all'UnitaImmobiliare specificata
     * @param nomeStanza Nome della stanza da aggiungere
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se l'aggiunta e' andata a buon fine
     */
    public boolean aggiungiStanza(String nomeStanza, String unita) {
        Stanza s = new Stanza(nomeStanza);
        s.setUnitaOwner(unita);
        return modificatore.aggiungiStanza(s);
    }

    /**
     * Rimuove una Stanza dall'UnitaImmobiliare specificata
     * @param stanza Nome della stanza da rimuovere
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se la rimozione e' andata a buon fine
     */
    public boolean rimuoviStanza(String stanza, String unita) {
      return modificatore.rimuoviStanza(stanza, unita);
    }

    /**
     * Aggiunge un Artefatto al model. Per l'aggiunta occorre specificare
     * Stanza e UnitaImmobiliare di appartenenza dell'artefatto.
     * @param artefatto Nome dell'artefatto da aggiungere
     * @param stanza Nome della stanza a cui aggiungere l'artefatto
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se l'aggiunta e' andata a buon fine
     */
    public boolean aggiungiArtefatto(String artefatto, String stanza, String unita) {
        return modificatore.aggiungiArtefatto(new Artefatto(artefatto), stanza, unita);
    }

    /**
     * Rimuove un Artefatto dal model. Per la rimozione occorre specificare
     * Stanza e UnitaImmobiliare di appartenenza dell'artefatto.
     * @param artefatto Nome dell'artefatto da rimuovere
     * @param stanza Nome della stanza a cui rimuovere l'artefatto
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se la rimozione e' andata a buon fine
     */
    public boolean rimuoviArtefatto(String artefatto, String stanza, String unita) {
        return modificatore.rimuoviArtefatto(artefatto, stanza, unita);
    }

    /**
     * Aggiunge un Sensore all'interno di una stanza.
     * @param fantasia Nome di fantasia del sensore
     * @param categoria Nome della categoria (preesistente) del sensore
     * @param stanza Nome della stanza a cui aggiungere il sensore
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se l'aggiunta e' andata a buon fine
     */
    public boolean aggiungiSensore(String fantasia, String categoria, String stanza, String unita) {
        return modificatore.aggiungiSensore(fantasia, categoria, stanza, unita);
    }

    /**
     * Collega un Sensore preesistente a una stanza. In sostanza, permette
     * la condivisione di sensori tra artefatti e stanze.
     * @param nomeSensore Nome del sensore
     * @param nomeStanza Nome della stanza con cui condividere il sensore
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return true se la condivisione e' andata a buon fine
     */
    public boolean collegaSensore(String nomeSensore, String nomeStanza, String nomeUnita) {
        return modificatore.collegaSensore(nomeSensore, nomeStanza, nomeUnita);
    }

    /**
     * Rimuove un Sensore da una stanza.
     * @param sensore Nome del sensore da rimuovere
     * @param stanza Nome della stanza a cui rimuovere il sensore
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se la rimozione e' andata a buon fine
     */
    public boolean rimuoviSensore(String sensore, String stanza, String unita) {
        return modificatore.rimuoviSensore(sensore, stanza, unita);
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
    public boolean aggiungiSensore(String fantasia, String categoria, String artefatto, String stanza, String unita) {
        return modificatore.aggiungiSensore(fantasia, categoria, stanza, unita);
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
    public boolean collegaSensore(String nomeSensore, String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return modificatore.collegaSensore(nomeSensore, nomeArtefatto, nomeStanza, nomeUnita);
    }

    /**
     * Rimuove un Sensore da un Artefatto.
     * @param sensore Nome del sensore da rimuovere
     * @param artefatto Nome dell'artefatto a cui rimuovere il sensore
     * @param stanza Nome della stanza selezionata
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se la rimozione e' andata a buon fine
     */
    public boolean rimuoviSensore(String sensore, String artefatto, String stanza, String unita) {
        return modificatore.rimuoviSensore(sensore, artefatto, stanza, unita);
    }

    /**
     * Aggiunge un Attuatore all'interno di una stanza.
     * @param fantasia Nome di fantasia dell'attuatore
     * @param categoria Nome della categoria (preesistente) dell'attuatore
     * @param stanza Nome della stanza a cui aggiungere l'attuatore
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se l'aggiunta e' andata a buon fine
     */
    public boolean aggiungiAttuatore(String fantasia, String categoria, String stanza, String unita) {
        return modificatore.aggiungiAttuatore(fantasia, categoria, stanza, unita);
    }

    /**
     * Collega un Attuatore preesistente a una stanza. In sostanza, permette
     * la condivisione di attuatori tra artefatti e stanze.
     * @param nomeAttuatore Nome dell'attuatore
     * @param nomeStanza Nome della stanza con cui condividere l'attuatore
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return true se la condivisione e' andata a buon fine
     */
    public boolean collegaAttuatore(String nomeAttuatore, String nomeStanza, String nomeUnita) {
        return modificatore.collegaAttuatore(nomeAttuatore, nomeStanza, nomeUnita);
    }

    /**
     * Rimuove un Attuatore da una stanza.
     * @param attuatore Nome dell'attuatore da rimuovere
     * @param stanza Nome della stanza a cui rimuovere l'attuatore
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se la rimozione e' andata a buon fine
     */
    public boolean rimuoviAttuatore(String attuatore, String stanza, String unita) {
        return modificatore.rimuoviAttuatore(attuatore, stanza, unita);
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
    public boolean aggiungiAttuatore(String fantasia, String categoria, String artefatto, String stanza, String unita) {
        return modificatore.aggiungiAttuatore(fantasia, categoria, artefatto, stanza, unita);
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
    public boolean collegaAttuatore(String nomeAttuatore, String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return modificatore.collegaAttuatore(nomeAttuatore, nomeArtefatto, nomeStanza, nomeUnita);
    }

    /**
     * Rimuove un Attuatore da un Atyrfatto.
     * @param attuatore Nome dell'attuatore da rimuovere
     * @param artefatto Nome dell'artefatto a cui rimuovere l'attuatore
     * @param stanza Nome della stanza selezionata
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se la rimozione e' andata a buon fine
     */
    public boolean rimuoviAttuatore(String attuatore, String artefatto, String stanza, String unita) {
        return modificatore.rimuoviAttuatore(attuatore, artefatto, stanza, unita);
    }

    /**
     * Metodo per aggiungere una regola vuota a un'UnitaImmobiliare
     * @param nomeUnita Unita' selezionata
     * @return L'ID della nuova regola
     */
    public String aggiungiRegola(String nomeUnita) {
        Regola regola = new Regola();
        return modificatore.aggiungiRegola(regola, nomeUnita);
    }

    /**
     * Metodo di aggiunta di una condizione con costante numerica a una regola
     * @param sinistroVar Parte sinistra
     * @param op Operatore relazionale
     * @param destroConst Parte destra
     * @param unita Nome unita' selezionata
     * @param idRegola ID regola selezionata
     * @return true se l'inserimento va a buon fine
     */
    public boolean aggiungiComponenteAntecedente(String sinistroVar, String op, double destroConst, String unita, String idRegola) {
        return modificatore.aggiungiComponenteAntecedente(sinistroVar, op, destroConst, unita, idRegola);
    }

    /**
     * Metodo di aggiunta di una condizione con costante scalare (i.e. stringa) o con costanti variabili a una regola
     * @param sinistroVar Parte sinistra
     * @param op Operatore relazionale
     * @param destro Parte destra
     * @param scalare Flag che indica se destra e' scalare o meno
     * @param unita Nome unita' selezionata
     * @param idRegola ID regola selezionata
     * @return true se l'inserimento va a buon fine
     */
    public boolean aggiungiComponenteAntecedente(String sinistroVar, String op, String destro, boolean scalare, String unita, String idRegola) {
        return modificatore.aggiungiComponenteAntecedente(sinistroVar, op, destro, scalare, unita, idRegola);
    }

    /**
     * Metodo di aggiunta di un operatore booleano (pendente) all'antecedente di una regola.
     * Non e' garantita la correttezza dell'ordine degli inserimenti
     * (e.g. se si inserisce due volte di seguito un operatore senza inserire una condizione nel mezzo)
     * @return true se l'inserimento va a buon fine
     */
    public boolean aggiungiOperatoreLogico(String unita, String regola, String op) {
        return modificatore.aggiungiOperatoreLogico(op, regola, unita);
    }

    /**
     * Metodo di rimozione di una regola da un'UnitaImmobiliare
     * @param unita Unita' selezionata
     * @param idRegola ID della regola
     * @return true se la rimozione va a buon fine
     */
    public boolean rimuoviRegola(String unita, String idRegola) {
        return modificatore.rimuoviRegola(unita, idRegola);
    }

    /**
     * Metodo di aggiunta di un'azione al conseguente di una regola.
     * @param attuatore Attuatore dell'azione
     * @param modalita Modalita' dell'azione
     * @param listaParams Lista dei parametri nuovi per la modalita'
     * @param unita Unita' Immobiliare selezionata
     * @param idRegola ID della regola di cui fa parte il conseguente
     * @return true se l'inserimento e' andato a buon fine
     */
    public boolean aggiungiAzioneConseguente(String attuatore, String modalita, Map<String, Double> listaParams, String unita, String idRegola) { //SENZA START
        return modificatore.aggiungiAzioneConseguente(attuatore, modalita, listaParams, unita, idRegola);
    }

    /**
     * Metodo di aggiunta di un'azione al conseguente di una regola.
     * @param attuatore Attuatore dell'azione
     * @param modalita Modalita' dell'azione
     * @param unita Unita' Immobiliare selezionata
     * @param idRegola ID della regola di cui fa parte il conseguente
     * @return true se l'inserimento e' andato a buon fine
     */
    public boolean aggiungiAzioneConseguente(String attuatore, String modalita, String unita, String idRegola) { //SENZA START
        return aggiungiAzioneConseguente(attuatore, modalita, new HashMap<>(), unita, idRegola);
    }

    /**
     * Metodo di aggiunta di un'azione programmata al conseguente di una regola
     * @param attuatore attuatore dell'azione programmata
     * @param modalita modalità dell'azione programmata
     * @param listaParams lista dei parametri nuovi per la modalità
     * @param orarioStart orario di start dell'azione programmata
     * @param unita unità immobiliare selezionata
     * @param idRegola id della regola a cui l'azione appartiene
     * @return true se l'inserimento è andato a buon fine
     */
    public boolean aggiungiAzioneConseguente(String attuatore, String modalita, Map<String, Double> listaParams, double orarioStart, String unita, String idRegola){ //CON START
        return modificatore.aggiungiAzioneConseguente(attuatore, modalita, listaParams, orarioStart, unita, idRegola);
    }

    /**
     * Metodo di aggiunta di un'azione programmata al conseguente di una regola
     * @param attuatore attuatore dell'azione programmata
     * @param modalita modalità dell'azione programmata
     * @param orarioStart orario di start dell'azione programmata
     * @param unita unità immobiliare selezionata
     * @param idRegola id della regola a cui l'azione appartiene
     * @return true se l'inserimento è andato a buon fine
     */
    public boolean aggiungiAzioneConseguente(String attuatore, String modalita, double orarioStart, String unita, String idRegola){ //CON START
        return aggiungiAzioneConseguente(attuatore, modalita, new HashMap<>(), orarioStart, unita, idRegola);
    }

    /**
     * Metodo che cambia lo stato del sensore facendolo passare da ON ad OFF e viceversa
     * Sospende le regole in cui compare il sensore che si è spento, se le stesse non sono però disattive
     * Riattiva le regole solo se non sono presenti altri sensori o attuatori spenti (ie tutti i dispositivi coinvolti sono ON)
     * @param sensore a cui cambiare stato
     * @return true se il cambio stato ha avuto successo
     */
    public boolean cambiaStatoSensore(String sensore, String unita){
        return modificatore.cambiaStatoSensore(sensore, unita);
    }

    /**
     * Metodo che cambia lo stato del attuatore facendolo passare da ON ad OFF e viceversa
     * Sospende le regole in cui compare l'attuatore che si è spento, se le stesse non sono però disattive
     * Riattiva le regole solo se non sono presenti altri sensori o attuatori spenti (ie tutti i dispositivi coinvolti sono ON)
     * @param attuatore a cui cambiare stato
     * @return true se il cambio stato ha avuto successo
     */
    public boolean cambiaStatoAttuatore(String attuatore, String unita){
        return modificatore.cambiaStatoAttuatore(attuatore, unita);
    }


    /**
     * Metodo che cambia lo stato delle regole da 'Attiva' a 'Disattiva' e viceversa
     * @param idRegola della regola a cui cambiare stato
     * @param unita in cui vale la regola
     * @return true se il cambio stato ha avuto successo
     */
    public int cambioStatoRegola(String idRegola, String unita){
        return modificatore.cambioStatoRegola(idRegola, unita);
    }

    /**
     * Rimuove un'azione programmata dall'elenco. Nella rimozione puo' essere eseguita
     * l'azione oppure semplicemente ignorata.
     * @param idAzione  identificativo dell'azione
     * @param esegui    true: esegui l'azione durante la rimozione; false: rimuovi solamente
     * @return true: rimozione eseguita con successo; false: altrimenti
     */
    public boolean rimuoviAzioneProgrammata(String idAzione, boolean esegui) {
        return modificatore.rimuoviAzioneProgrammata(idAzione, esegui);
    }

}
