package domotix.controller;

import domotix.controller.util.StringUtil;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.regole.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Classe per implementare una parte di logica controller relativa all'aggiunta e rimozione di entita'.
 * @author andrea, paolopasqua, Edoardo Coppola
 **/
public class Interpretatore {
    // TODO: mi servirebbe l'istanza di verificatore
    // TODO: come faccio a fare cose come quelle alla riga 96 (0 188)? Bisognerebbe aggiungere questa roba a verificatore
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
        if (!Verificatore.checkValiditaParametro(nomeCategoria, nomeModalita, nomeParametro)) return false;
        modificatore.aggiungiParametro(new Parametro(nomeParametro, valore));
        return true;
    }

    /**
     * Aggiunge un'UnitaImmobiliare al model
     * @param nomeUnita Nome dell'UnitaImmobiliare da aggiungere
     * @return true se l'aggiunta e' andata a buon fine
     */
    public boolean aggiungiUnitaImmobiliare(String nomeUnita) {
        if (!Verificatore.checkValiditaUnitaImmobiliare(nomeUnita)) return false;
        modificatore.aggiungiUnitaImmobiliare(new UnitaImmobiliare(nomeUnita)
        return true;
    }

    /**
     * Rimuove un'UnitaImmobiliare dal model
     * @param nomeUnita Nome dell'UnitaImmobiliare da rimuovere
     * @return true se la rimozione e' andata a buon fine
     */
    public boolean rimuoviUnitaImmobiliare(String nomeUnita) {
        if (Recuperatore.getUnita(nomeUnita) == null) return false;
        modificatore.rimuoviUnitaImmobiliare(nomeUnita);
        return true;
    }

    public boolean setModalitaOperativa(String nomeAttuatore, String nomeModalita) {
        if (!Verificatore.checkValiditaModalitaOperativaPerAttuatore(nomeAttuatore, nomeModalita)) return false;
        modificatore.setModalitaOperativa(nomeAttuatore, nomeModalita);
        return true;
    }


    public boolean aggiungiInfoRilevabile(String nomeCat, String nome, boolean numerica) {
        if (!Verificatore.checkValiditaInfoRilevabile(nomeCat, nome)) return false;
        modificatore.aggiungiInfoRilevabile(new InfoRilevabile(nome, numerica)); 
        return true;
    }

    /**
     * Aggiunge una CategoriaSensore al model
     * @param nomeCat Nome della categoria da aggiungere
     * @param testoLibero Testo libero della categoria da aggiungere
     * @return true se l'aggiunta e' andata a buon fine
     */
    public boolean aggiungiCategoriaSensore(String nomeCat, String testoLibero) {
        if (!Verificatore.checkValiditaCategoriaSensore(nomeCat)) return false;
        modificatore.aggiungiCategoriaSensore(new CategoriaSensore(nomeCat, testoLibero));
        return true;
    }

    /**
     * Rimuove una CategoriaSensore dal model
     * @param cat Nome della categoria da rimuovere
     * @return true se la rimozione e' andata a buon fine
     */
    public boolean rimuoviCategoriaSensore(String cat) {
        if (Recuperatore.getCategoriaSensore(cat) == null) return false;
        modificatore.rimuoviCategoriaSensore(cat);
        return true;
    }

    /**
     * Aggiunge una CategoriaAttuatore senza modalita' operative al model
     * @param nomeCat Nome della categoria da aggiungere
     * @param testoLibero Testo libero della categoria da aggiungere
     * @return true se l'aggiunta e' andata a buon fine
     */
    public boolean aggiungiCategoriaAttuatore(String nomeCat, String testoLibero) {
        if (!Verificatore.checkValiditaCategoriaAttuatore(nomeCat)) return false;
        modificatore.aggiungiCategoriaAttuatore(new CategoriaAttuatore(nomeCat, testoLibero));
        return true;
    }

    /**
     * Aggiunge una ModalitaOperativa a una CategoriaAttuatore preesistente
     * @param nomeCat Nome della categoria da selezionare
     * @param nomeModalita Nome della modalita' da aggiungere
     * @return true se l'aggiunta e' andata a buon fine
     */
    public boolean aggiungiModalitaCategoriaAttuatore(String nomeCat, String nomeModalita) {
        if (!Verificatore.checkValiditaModalitaOperativa(nomeModalita)) return false;
        modificatore.aggiungiModalitaCategoriaAttuatore(new Modalita(nomeModalita));
        return true;
    }

    /**
     * Rimuove una CategoriaAttuatore dal model
     * @param cat Nome della categoria da rimuovere
     * @return true se la rimozione e' andata a buon fine
     */
    public boolean rimuoviCategoriaAttuatore(String cat) {
        if (Recuperatore.getCategoriaAttuatore(cat) == null) return false;
        modificatore.rimuoviCategoriaAttuatore(cat);
        return true;
    }

    /**
     * Aggiunge una Stanza all'UnitaImmobiliare specificata
     * @param nomeStanza Nome della stanza da aggiungere
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se l'aggiunta e' andata a buon fine
     */
    public boolean aggiungiStanza(String nomeStanza, String unita) {
        if (!Verificatore.checkValiditaStanza(nomeStanza, unita)) return false;
        modificatore.aggiungiStanza(new Stanza(nomeStanza));
        return true;
    }

    /**
     * Rimuove una Stanza dall'UnitaImmobiliare specificata
     * @param stanza Nome della stanza da rimuovere
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se la rimozione e' andata a buon fine
     */
    public boolean rimuoviStanza(String stanza, String unita) {
// TODO: cosa faccio qua?
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
    public boolean aggiungiArtefatto(String artefatto, String stanza, String unita) {
        if (!Verificatore.checkValiditaArtefatto(artefatto, unita)) return false;
        modificatore.aggiungiArtefatto(new Artefatto(artefatto));
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
    public boolean rimuoviArtefatto(String artefatto, String stanza, String unita) {
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
    public boolean aggiungiSensore(String fantasia, String categoria, String stanza, String unita) {
        String nomeComposto = StringUtil.componiNome(fantasia, categoria);
        if (!Verificatore.checkValiditaSensore(nomeComposto, categoria, stanza, unita)) return false;
        Sensore sensore = new Sensore(nomeComposto, Recuperatore.getCategoriaSensore(categoria));
        sensore.setStato(true);
        modificatore.aggiungiSensore(sensore, stanza, unita);
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
    public boolean collegaSensore(String nomeSensore, String nomeStanza, String nomeUnita) {
        Sensore sens = Recuperatore.getSensore(nomeSensore);
        Stanza stanza = Recuperatore.getStanza(nomeStanza, nomeUnita);

        if (sens == null)
            return false;
        if (stanza == null)
            return false;

        modificatore.collegaSensore(sens, nomeStanza, nomeUnita);
        return true;
    }

    /**
     * Rimuove un Sensore da una stanza.
     * @param sensore Nome del sensore da rimuovere
     * @param stanza Nome della stanza a cui rimuovere il sensore
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se la rimozione e' andata a buon fine
     */
    public boolean rimuoviSensore(String sensore, String stanza, String unita) {
        Stanza stanzaInst = Recuperatore.getStanza(stanza, unita);
        if (stanzaInst == null)
            return false;

        Sensore sens = Recuperatore.getSensore(sensore);
        if (sens == null)
            return false;

        modificatore.rimuoviSensore(sens, stanza, unita);
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
    public boolean aggiungiSensore(String fantasia, String categoria, String artefatto, String stanza, String unita) {
        String nomeComposto = StringUtil.componiNome(fantasia, categoria);
        if (!Verificatore.checkValiditaSensore(nomeComposto, categoria, artefatto, stanza, unita)) return false;
        Sensore sensore = new Sensore(nomeComposto, Recuperatore.getCategoriaSensore(categoria));
        sensore.setStato(true);
        modificatore.aggiungiSensore(sensore, artefatto, stanza, unita);
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
    public boolean collegaSensore(String nomeSensore, String nomeArtefatto, String nomeStanza, String nomeUnita) {
        Sensore sens = Recuperatore.getSensore(nomeSensore);
        Artefatto artefatto = Recuperatore.getArtefatto(nomeArtefatto, nomeStanza, nomeUnita);

        if (sens == null)
            return false;
        if (artefatto == null)
            return false;
        modificatore.collegaSensore(nomeSensore, nomeArtefatto, nomeStanza, nomeUnita);
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
    public boolean rimuoviSensore(String sensore, String artefatto, String stanza, String unita) {
        Artefatto artefattoInst = Recuperatore.getArtefatto(artefatto, stanza, unita);
        if (stanza == null)
            return false;

        Sensore sens = Recuperatore.getSensore(sensore);
        if (sens == null)
            return false;

        modificatore.rimuoviSensore(sensore, artefatto, stanza, unita);
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
    public boolean aggiungiAttuatore(String fantasia, String categoria, String stanza, String unita) {
        String nomeComposto = StringUtil.componiNome(fantasia, categoria);
        if (!Verificatore.checkValiditaAttuatore(nomeComposto, categoria, stanza, unita)) return false;
        Attuatore attuatore = new Attuatore(nomeComposto, Recuperatore.getCategoriaAttuatore(categoria), Recuperatore.getCategoriaAttuatore(categoria).getModalitaDefault());
        attuatore.setStato(true);
        modificatore.aggiungiAttuatore(attuatore, stanza, unita);
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
    public boolean collegaAttuatore(String nomeAttuatore, String nomeStanza, String nomeUnita) {
        Attuatore attuatore = Recuperatore.getAttuatore(nomeAttuatore);
        Stanza stanza = Recuperatore.getStanza(nomeStanza, nomeUnita);

        if (attuatore == null)
            return false;
        if (stanza == null)
            return false;

        modificatore.collegaAttuatore(attuatore, nomeStanza, nomeUnita)
        return true;
    }

    /**
     * Rimuove un Attuatore da una stanza.
     * @param attuatore Nome dell'attuatore da rimuovere
     * @param stanza Nome della stanza a cui rimuovere l'attuatore
     * @param unita Nome dell'unita' immobiliare selezionata
     * @return true se la rimozione e' andata a buon fine
     */
    public boolean rimuoviAttuatore(String attuatore, String stanza, String unita) {
        Stanza stanzaInst = Recuperatore.getStanza(stanza, unita);
        if (stanza == null)
            return false;

        Attuatore att = Recuperatore.getAttuatore(attuatore);
        if (att == null)
            return false;

        modificatore.rimuoviAttuatore(attuatore, stanza, unita);
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
    public boolean aggiungiAttuatore(String fantasia, String categoria, String artefatto, String stanza, String unita) {
        String nomeComposto = StringUtil.componiNome(fantasia, categoria);
        if (!Verificatore.checkValiditaAttuatore(nomeComposto, categoria, artefatto, stanza, unita)) return false;
        Attuatore attuatore = new Attuatore(nomeComposto, Recuperatore.getCategoriaAttuatore(categoria), Recuperatore.getCategoriaAttuatore(categoria).getModalitaDefault());
        attuatore.setStato(true);
        modificatore.aggiungiAttuatore(attuatore, artefatto, stanza, unita);
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
    public boolean collegaAttuatore(String nomeAttuatore, String nomeArtefatto, String nomeStanza, String nomeUnita) {
        Attuatore attuatore = Recuperatore.getAttuatore(nomeAttuatore);
        Artefatto artefatto = Recuperatore.getArtefatto(nomeArtefatto, nomeStanza, nomeUnita);

        if (attuatore == null)
            return false;
        if (artefatto == null)
            return false;

        modificatore.collegaAttuatore(nomeAttuatore, nomeArtefatto, nomeStanza, nomeUnita);
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
    public boolean rimuoviAttuatore(String attuatore, String artefatto, String stanza, String unita) {
        Artefatto artefattoInst = Recuperatore.getArtefatto(artefatto, stanza, unita);
        if (stanza == null)
            return false;

        Attuatore att = Recuperatore.getAttuatore(attuatore);
        if (att == null)
            return false;

        modificatore.rimuoviAttuatore(attuatore, artefatto, stanza, unita);
        return true;
    }

    /**
     * Metodo per aggiungere una regola vuota a un'UnitaImmobiliare
     * @param nomeUnita Unita' selezionata
     * @return L'ID della nuova regola
     */
    public String aggiungiRegola(String nomeUnita) {
        UnitaImmobiliare unita = Recuperatore.getUnita(nomeUnita);
        if (unita == null) return null;
        Regola regola = new Regola();
        modificatore.aggiungiRegola(regola, nomeUnita);
        return regola.getId();
    }

    private static InfoVariabile costruisciInfoDaSensore(String sinistroVar) {
        String[] campi = sinistroVar.split(Pattern.quote("."));
        String nomeSensore = campi[0];
        String nomeInfo = campi[1];
        Sensore sensore = Recuperatore.getSensore(nomeSensore);
        InfoVariabile sinistro = new InfoVariabile(sensore, nomeInfo);
        return sinistro;
    }

    private static boolean aggiungiComponenteCostanteAntecedente(String sinistroVar, String op, Object destroConst, String unita, String idRegola) {
        // TODO: da fare
        try {
            Regola regola = Recuperatore.getUnita(unita).getRegola(idRegola);
            InfoVariabile sinistro = costruisciInfoDaSensore(sinistroVar);
            InfoSensoriale destro;
            if(Verificatore.checkIsSensoreOrologio(sinistroVar)){
                    destro = new InfoTemporale(SensoreOrologio.getTempo((Double) destroConst));
            }else {
                destro = new InfoCostante(destroConst);
            }
            regola.addCondizone(new Condizione(sinistro, op, destro));
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
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
        return aggiungiComponenteCostanteAntecedente(sinistroVar, op, destroConst, unita, idRegola);
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
        try {
            if (scalare) {
                return aggiungiComponenteCostanteAntecedente(sinistroVar, op, destro, unita, idRegola);
            }
            Regola regola = Recuperatore.getUnita(unita).getRegola(idRegola);
            InfoVariabile sx = costruisciInfoDaSensore(sinistroVar);
            InfoVariabile dx = costruisciInfoDaSensore(destro);
            modificatore.aggiungiComponenteAntecedente(new Condizione(sx, op, dx), regola, unita);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Metodo di aggiunta di un operatore booleano (pendente) all'antecedente di una regola.
     * Non e' garantita la correttezza dell'ordine degli inserimenti
     * (e.g. se si inserisce due volte di seguito un operatore senza inserire una condizione nel mezzo)
     * @return true se l'inserimento va a buon fine
     */
    public boolean aggiungiOperatoreLogico(String unita, String regola, String op) {
        if (Recuperatore.getUnita(unita) == null) return false;
        if (Recuperatore.getUnita(unita).getRegola(regola) == null) return false;
        if (!Verificatore.checkValiditaOperatoreLogico(op)) return false;
        modificatore.aggiungiOperatoreLogico(op, regola, unita);
        return true;
    }

    /**
     * Metodo di rimozione di una regola da un'UnitaImmobiliare
     * @param unita Unita' selezionata
     * @param idRegola ID della regola
     * @return true se la rimozione va a buon fine
     */
    public boolean rimuoviRegola(String unita, String idRegola) {
        UnitaImmobiliare unitaImm = Recuperatore.getUnita(unita);
        if (unitaImm == null || unitaImm.getRegola(idRegola) == null) return false;
        modificatore.rimuoviRegola(unita, idRegola);
        return true;
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
        if (Recuperatore.getUnita(unita) == null) return false;
        if (Recuperatore.getUnita(unita).getRegola(idRegola) == null) return false;
        if (Recuperatore.getAttuatore(attuatore) == null) return false;
        if (!Recuperatore.getAttuatore(attuatore).getCategoria().hasModalita(modalita)) return false;
        Attuatore att = Recuperatore.getAttuatore(attuatore);
        Modalita mod = att.getCategoria().getModalita(modalita);
        List<Parametro> parametri = new ArrayList<>();
        listaParams.forEach((k, v) -> parametri.add(new Parametro(k, v)));
        modificatore.aggiungiAzioneConseguente(unita, idRegola, new Azione(att, mod, parametri));
        return true;
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
        if (Recuperatore.getUnita(unita) == null) return false;
        if (Recuperatore.getUnita(unita).getRegola(idRegola) == null) return false;
        if (Recuperatore.getAttuatore(attuatore) == null) return false;
        if (!Recuperatore.getAttuatore(attuatore).getCategoria().hasModalita(modalita)) return false;
        String orario = String.valueOf(orarioStart);
        Attuatore att = Recuperatore.getAttuatore(attuatore);
        Modalita mod = att.getCategoria().getModalita(modalita);
        List<Parametro> parametri = new ArrayList<>();
        listaParams.forEach((k, v) -> parametri.add(new Parametro(k, v)));
        modificatore.aggiungiAzioneConseguente(unita, idRegola, new Azione(att, mod, parametri,SensoreOrologio.getTempo(orarioStart))M
        return true;
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
        // TODO: da fare
        Sensore s = Recuperatore.getSensore(sensore);
        if(s == null) return false;
        boolean opposto = !s.getStato();
        s.setStato(opposto);
        //eventuale sospensione/attivazione regola
        UnitaImmobiliare u = Recuperatore.getUnita(unita);
        Regola[] regole = u.getRegole();
        for (Regola r: regole) {
            if(r.contieneSensore(sensore)){//se la regola contiene il sensore a cui ho cambiato stato me ne curo altrimenti me ne frego e vado a quella dopo
                if(s.getStato() == false) {//sensore appena spento
                    if(!(r.getStato().equals(StatoRegola.DISATTIVA))) { //sospendo solo se attiva o sospesa. Se disattiva lascio com'è
                        r.setStato(StatoRegola.SOSPESA);
                    }
                }
                else { //sensore appena acceso - guardo se è possibile riattivare la regola
                   if(r.getStato().equals(StatoRegola.SOSPESA) && r.isAttivabile()) { r.setStato(StatoRegola.ATTIVA); }
                }
            }
        }
        //fine sospensione/attivazione
        return true;
    }

    /**
     * Metodo che cambia lo stato del attuatore facendolo passare da ON ad OFF e viceversa
     * Sospende le regole in cui compare l'attuatore che si è spento, se le stesse non sono però disattive
     * Riattiva le regole solo se non sono presenti altri sensori o attuatori spenti (ie tutti i dispositivi coinvolti sono ON)
     * @param attuatore a cui cambiare stato
     * @return true se il cambio stato ha avuto successo
     */
    public boolean cambiaStatoAttuatore(String attuatore, String unita){
        // TODO: da fare
        Attuatore a = Recuperatore.getAttuatore(attuatore);
        if(a == null) return false;
        boolean opposto = !a.getStato();
        a.setStato(opposto);
        //eventuale sospensione/attivazione regola
        UnitaImmobiliare u = Recuperatore.getUnita(unita);
        Regola[] regole = u.getRegole();
        for (Regola r: regole) {
            if(r.contieneAttuatore(attuatore)){//se la regola contiene l'attuatore a cui ho cambiato stato me ne curo altrimenti me ne frego e vado a quella dopo
                if(a.getStato() == false) {//attuatore appena spento
                    if(!(r.getStato().equals(StatoRegola.DISATTIVA))) { //sospendo solo se attiva o sospesa. Se disattiva lascio com'è
                        r.setStato(StatoRegola.SOSPESA);
                    }
                }
                else {//attuatore appena acceso - guardo se posso riattivare la regola
                    if (r.getStato().equals(StatoRegola.SOSPESA) && r.isAttivabile()){ r.setStato(StatoRegola.ATTIVA); }
                }
            }
        }
        //fine sospensione/attivazione
        return true;
    }


    /**
     * Metodo che cambia lo stato delle regole da 'Attiva' a 'Disattiva' e viceversa
     * @param idRegola della regola a cui cambiare stato
     * @param unita in cui vale la regola
     * @return true se il cambio stato ha avuto successo
     */
    public int cambioStatoRegola(String idRegola, String unita){
        // TODO: da fare
        Regola r = Recuperatore.getUnita(unita).getRegola(idRegola);
        if(r == null)  return -1;
        StatoRegola s = r.getStato();
        if(s.name().equals("ATTIVA")){ s = StatoRegola.DISATTIVA; r.setStato(s); return 1; }
        else{
            if(r.isAttivabile()){ s = StatoRegola.ATTIVA; r.setStato(s); return 2; }
            else{ s = StatoRegola.SOSPESA; r.setStato(s); return 3; }
        }
    }

    /**
     * Rimuove un'azione programmata dall'elenco. Nella rimozione puo' essere eseguita
     * l'azione oppure semplicemente ignorata.
     * @param idAzione  identificativo dell'azione
     * @param esegui    true: esegui l'azione durante la rimozione; false: rimuovi solamente
     * @return true: rimozione eseguita con successo; false: altrimenti
     */
    public boolean rimuoviAzioneProgrammata(String idAzione, boolean esegui) {
        Azione a = Recuperatore.getAzioneProgrammata(idAzione);
        if (a != null) {
            if (esegui)
                a.esegui();
            modificatore.rimuoviAzioneProgrammata(idAzione);
            return true;
        }
        return false;
    }

}
