package domotix.controller;

import domotix.model.*;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.regole.Azione;
import domotix.model.bean.regole.Regola;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Sistema;
import domotix.model.bean.system.Stanza;

import java.util.*;
import java.util.stream.Stream;

/**Classe per implementare una parte di logica controller relativa al recupero di informazioni sulle entita'.
 * @author andrea*/
// ex recuperatore
public class Rappresentatore {
    private Recuperatore recuperatore;
    private Stringatore stringatore = new Stringatore();

    public Rappresentatore(Recuperatore recuperatore) {
        this.recuperatore = recuperatore;
    }

    /**
     * Metodo di recupero della modalita' operativa
     * in cui si trova correntemente l'attuatore.
     * @param nomeAttuatore Nome dell'Attuatore
     * @return Il nome della Modalita corrente
     */
    public String getModalitaOperativaCorrente(String nomeAttuatore) {
        Modalita modalitaCorrente = recuperatore.getModalitaOperativaCorrente(nomeAttuatore);
        return modalitaCorrente.getNome();
    }

    /**
     * Metodo di recupero di una lista di nomi delle modalita' operative
     * di un attuatore, tranne quella in cui si trova correntemente.
     * @return Array di nomi di Modalita
     */
    public String[] getModalitaOperativeImpostabili(String nomeAttuatore) {
        List<Modalita> elencoModalita = recuperatore.getModalitaOperativeImpostabili(nomeAttuatore);
        return  elencoModalita.stream()
            .map(mod -> mod.getNome())
            .toArray(String[]::new);
    }

    /**
     * Metodo di recupero di una lista di nomi delle unita' immobiliari
     * presenti all'interno del model.
     * @return Array di nomi delle unita' immobiliari esistenti
     */
    public String[] getNomiUnita() {
        return recuperatore.getListaUnita()
            .stream()
            .map(u -> u.getNome())
            .toArray(String[]::new);
    }

    /**
     * Metodo di recupero di una lista di descrizioni delle unita' immobiliari
     * presenti all'interno del model.
     * @return Array di descrizioni delle unita' immobiliari presenti
     */
    public String[] getDescrizioniUnita() {
        return recuperatore.getListaUnita()
            .stream()
            .map(u -> stringatore.rappresenta(u))
            .toArray(String[]::new);
    }

    /**
     * Metodo di recupero di una lista di nomi delle stanze
     * presenti all'interno di un'unita' immobiliare.
     * Se elencaStanzaDefault e' false, non verra' recuperato il nome della stanza
     * di default.
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @param elencaStanzaDefault Flag che specifica se recuperare il nome della stanza di default o meno
     * @return Array di nomi delle stanze esistenti
     */
    public String[] getNomiStanze(String nomeUnita, boolean elencaStanzaDefault) {
        List<Stanza> stanze = recuperatore.getStanze(nomeUnita, elencaStanzaDefault);
        return stanze.stream().map(s -> s.getNome()).toArray(String[]::new);
    }

    /**
     * Metodo di recupero di una lista di nomi di tutte le stanze
     * (compresa quella di default) presenti all'interno di un'unita' immobiliare.
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return Array di nomi delle stanze esistenti
     */
    public String[] getNomiStanze(String nomeUnita) {
        return this.getNomiStanze(nomeUnita, true);
    }

    private static String[] getNomiAttuatoriSistema(Sistema sistema) {
        return Stream.of(sistema.getAttuatori()).map(a -> a.getNome()).toArray(String[]::new);
    }

    private static String[] getNomiSensoriSistema(Sistema sistema) {
        return Stream.of(sistema.getSensori()).map(s -> s.getNome()).toArray(String[]::new);
    }

    /**
     * Metodo di recupero dei nomi degli attuatori all'interno di una stanza.
     * @param nomeStanza Nome della stanza selezionata
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return Array di nomi di attuatori
     */
    public String[] getNomiAttuatori(String nomeStanza, String nomeUnita) {
        return this.getNomiAttuatoriSistema(recuperatore.getStanza(nomeStanza, nomeUnita));
    }

    /**
     * Metodo di recupero dei nomi dei sensori all'interno di una stanza.
     * @param nomeStanza Nome della stanza selezionata
     * @param nomeUnita Nome dell'unita' selezionata
     * @return Array di nomi di sensori
     */
    public String[] getNomiSensori(String nomeStanza, String nomeUnita) {
        return this.getNomiSensoriSistema(recuperatore.getStanza(nomeStanza, nomeUnita));
    }

    /**
     * Metodo di recupero dei nomi degli attuatori all'interno di un artefatto.
     * @param nomeArtefatto Nome dell'artefatto selezionato
     * @param nomeStanza Nome della stanza selezionata
     * @param nomeUnita Nome dell'unita' selezionata
     * @return Array di nomi di attuatori
     */
    public String[] getNomiAttuatori(String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return this.getNomiAttuatoriSistema(recuperatore.getArtefatto(nomeArtefatto, nomeStanza, nomeUnita));
    }

    /**
     * Metodo di recupero dei nomi dei sensori all'interno di un artefatto.
     * @param nomeArtefatto Nome dell'artefatto selezionato
     * @param nomeStanza Nome della stanza selezionata
     * @param nomeUnita Nome dell'unita' selezionata
     * @return Array di nomi di sensori
     */
    public String[] getNomiSensori(String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return this.getNomiSensoriSistema(recuperatore.getArtefatto(nomeArtefatto, nomeStanza, nomeUnita));
    }

    /**
     * Metodo di recupero dei nomi degli artefatti all'interno di una stanza
     * @param nomeStanza Nome della stanza selezionata
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return Array di nomi di artefatti
     */
    public String[] getNomiArtefatti(String nomeStanza, String nomeUnita) {
        List<Artefatto> artefatti = recuperatore.getArtefatti(nomeStanza, nomeUnita);
        return artefatti.stream().map(a -> a.getNome()).toArray(String[]::new);
    }

    /**
     * Metodo di recupero dei nomi delle categorie di sensori all'interno
     * del model.
     * @return Array di nomi di categorie di sensori
     */
    public String[] getNomiCategorieSensori() {
        return recuperatore.getCategorieSensore()
            .stream()
            .map(cs -> cs.getNome())
            .toArray(String[]::new);
    }

    /**
     * Metodo di recupero dei nomi delle categorie di attuatori all'interno
     * del model.
     * @return Array di nomi di categorie di attuatori
     */
    public String[] getNomiCategorieAttuatori() {
        return  recuperatore.getCategorieAttuatore()
            .stream()
            .map(cs -> cs.getNome())
            .toArray(String[]::new);
    }

    /**
     * Metodo di recupero delle descrizioni delle categorie di sensori
     * all'interno del model.
     * @return Array di descrizioni di categorie di sensori
     */
    public String[] getDescrizioniCategorieSensori() {
        return  recuperatore.getCategorieSensore()
            .stream()
            .map(cs -> stringatore.rappresenta(cs))
            .toArray(String[]::new);
    }

    /**
     * Metodo di recupero delle descrizioni delle categorie di attuatori
     * all'interno del model.
     * @return Array di descrizioni di categorie di attuatori
     */
    public String[] getDescrizioniCategorieAttuatori() {
        return  recuperatore.getCategorieAttuatore()
            .stream()
            .map(ca -> stringatore.rappresenta(ca))
            .toArray(String[]::new);
    }

    /**
     * Metodo di recupero della descrizione di un'unita' immobiliare
     * all'interno del model.
     * @param nomeUnita Nome dell'untia' immobiliare
     * @return Descrizione dell'unita' immobiliare
     */
    public String getDescrizioneUnita(String nomeUnita) {
        return stringatore.rappresenta(recuperatore.getUnita(nomeUnita));
    }

    /**
     * Metodo di recupero della descrizione di una stanza
     * all'interno di un'unita' immobiliare specificata.
     * @param nomeStanza Nome della stanza
     * @param nomeUnita Nome dell'untia' immobiliare selezionata
     * @return Descrizione della stanza
     */
    public String getDescrizioneStanza(String nomeStanza, String nomeUnita) {
        return stringatore.rappresenta(recuperatore.getStanza(nomeStanza, nomeUnita));
    }

    /**
     * Metodo di recupero della descrizione di un artefatto
     * all'interno di una stanza specificata.
     * @param nomeArtefatto Nome dell'artefatto
     * @param nomeStanza Nome della stanza selezionata
     * @param nomeUnita Nome dell'untia' immobiliare selezionata
     * @return Descrizione della stanza
     */
    public String getDescrizioneArtefatto(String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return stringatore.rappresenta(recuperatore.getArtefatto(nomeArtefatto, nomeStanza, nomeUnita));
    }

    private String[] getNomiSensoriAggiungibiliSistema(Sistema sistema, String nomeUnita) {
        List<Sensore> sensori = recuperatore.getSensoriAggiungibiliSistema(sistema, nomeUnita);
        return sensori.stream()
            .map(s -> s.getNome())
            .toArray(String[]::new);
    }

    private String[] getNomiAttuatoriAggiungibiliSistema(Sistema sistema, String nomeUnita) {
        List<Attuatore> attuatori = recuperatore.getAttuatoriAggiungibiliSistema(sistema, nomeUnita);
        return attuatori.stream()
            .map(a -> a.getNome())
            .toArray(String[]::new);
    }

    /**
     * Metodo di recupero dei nomi dei sensori gia' esistenti che possono essere
     * condivisi con la stanza specificata. In sostanza: il metodo recupera tutti i sensori
     * con una categoria di sensore che non e' gia' presente all'interno della stanza.
     * @param nomeStanza Nome della stanza
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return Array di nomi di sensori condivisibili con la stanza
     */
    public String[] getNomiSensoriAggiungibiliStanza(String nomeStanza, String nomeUnita) {
        return this.getNomiSensoriAggiungibiliSistema(recuperatore.getStanza(nomeStanza, nomeUnita), nomeUnita);
    }

    /**
     * Metodo di recupero dei nomi dei sensori gia' esistenti che possono essere
     * condivisi con l'artefatto specificato. In sostanza: il metodo recupera tutti i sensori
     * con una categoria di sensore che non e' gia' presente all'interno dell'artefatto.
     * @param nomeArtefatto Nome dell'artefatto
     * @param nomeStanza Nome della stanza selezionata
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return Array di nomi di sensori condivisibili con l'artefatto
     */
    public String[] getNomiSensoriAggiungibiliArtefatto(String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return this.getNomiSensoriAggiungibiliSistema(recuperatore.getArtefatto(nomeArtefatto, nomeStanza, nomeUnita), nomeUnita);
    }

    /**
     * Metodo di recupero dei nomi degli attuatori gia' esistenti che possono essere
     * condivisi con la stanza specificata. In sostanza: il metodo recupera tutti gli attuatori
     * con una categoria di attuatore che non e' gia' presente all'interno della stanza.
     * @param nomeStanza Nome della stanza
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return Array di nomi di attuatori condivisibili con la stanza
     */
    public String[] getNomiAttuatoriAggiungibiliStanza(String nomeStanza, String nomeUnita) {
        return this.getNomiAttuatoriAggiungibiliSistema(recuperatore.getStanza(nomeStanza, nomeUnita), nomeUnita);
    }

    /**
     * Metodo di recupero dei nomi degli attuatori gia' esistenti che possono essere
     * condivisi con l'artefatto specificato. In sostanza: il metodo recupera tutti gli attuatori
     * con una categoria di attuatore che non e' gia' presente all'interno dell'artefatto.
     * @param nomeArtefatto Nome dell'artefatto
     * @param nomeStanza Nome della stanza selezionata
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return Array di nomi di attuatori condivisibili con l'artefatto
     */
    public String[] getNomiAttuatoriAggiungibiliArtefatto(String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return this.getNomiAttuatoriAggiungibiliSistema(recuperatore.getArtefatto(nomeArtefatto, nomeStanza, nomeUnita), nomeUnita);
    }

    /**
     * Metodo che recupera i nomi dei sensori presenti in un'unita' immobiliare
     * @param unita da cui pescare i nomi dei sensori
     * @return un array dei nomi dei sensori dell'unità immobiliare
     */
    public String[] getNomiSensori(String unita){
        return this.getNomiSensori(unita, false);
    }

    /**
     * Metodo che recupera i nomi dei sensori presenti in un'unita' immobiliare
     * @param unita da cui pescare i nomi dei sensori
     * @param includiOrologio   true: include in elenco il sensore universale SensoreOrologio; false: altrimenti
     * @return un array dei nomi dei sensori dell'unità immobiliare
     */
    public String[] getNomiSensori(String unita, boolean includiOrologio){
        List<Sensore> sensori = recuperatore.getSensori(unita, includiOrologio);
        return sensori.stream().map(a -> a.getNome()).toArray(String[]::new);
    }

    /**
     * Metodo che recupera i nomi degli attuatori presenti in un'unità immobiliare
     * @param unita da cui pescare i nomi degli attuatori
     * @return un array dei nomi degli attuatori dell'unità immobiliare
     */
    public String[] getNomiAttuatori(String unita){
        List<Attuatore> attuatori = recuperatore.getAttuatori(unita);
        return attuatori.stream().map(a -> a.getNome()).toArray(String[]::new);
    }

    /**
     * Metodo che ritorna un array dei nomi delle informazioni rilevabili da un sensore
     * @param nomeSensore di cui sapere le info rilevabili
     * @return un array dei nomi delle info rilevabili dal sensore
     */
    public String[] getInformazioniRilevabili(String nomeSensore) {
        List<InfoRilevabile> infoRilevabili = recuperatore.getInformazioniRilevabili(nomeSensore);
        return infoRilevabili
            .stream()
            .map(i -> i.getNome())
            .toArray(String[]::new);
    }

    /**
     * Metodo che ritorna la lista delle descrizioni delle regole relative a un'unita'.
     * @param unita UnitaImmobiliare selezionata
     * @return La lista delle descrizioni
     */
    public String[] getRegoleUnita(String unita) {
        List<Regola> regole = recuperatore.getRegoleUnita(unita);
        return regole.stream().map(r -> stringatore.rappresenta(r)).toArray(String[]::new);
    }

    // TODO: sposta in verificatore
    public boolean isInfoNumerica(String nsensoreDestro, String info) {
        Sensore sensore = getSensore(nsensoreDestro);
        return (sensore.getValore(info) instanceof Number); 
    }

    /**
     * Metodo che ritorna la mappa delle descrizioni degli antecedenti delle regole relative a un'unita'.
     * La mappa ha come chiavi le descrizioni delle regole unite al loro stato, come valori gli ID delle regole.
     * @param unita UnitaImmobiliare selezionata
     * @return La mappa delle descrizioni degli antecedenti
     */
    public Map<String, String> getAntecedentiRegoleUnita(String unita) {
        Map<String, String> map = new HashMap<>();
        List<Regola> regole = recuperatore.getRegoleUnita(unita);
        for (Regola r : regole) {
            map.put(stringatore.rappresenta(r), r.getId());
        }
        return map;
    }

    // TODO: sposta in verificatore
    public boolean isModalitaParametrica(String attuatore, String modalita) {
        return getAttuatore(attuatore)
            .getCategoria()
            .getModalita(modalita)
            .isParametrica(); //
    }

    public String[] getNomiParametriModalita(String attuatore, String modalita) {
        List<Parametro> parametri = recuperatore.getParametriModalita(attuatore, modalita);
        return parametri
            .stream()
            .map(p -> p.getNome())
            .toArray(String[]::new); //
    }

    /**
     * Metodo che ritorna la lista delle descrizioni delle modalita' relative a un attuatore.
     * @param attuatore Attuatore selezionato
     * @return La lista delle descrizioni
     */
    public String[] getModalitaTutte(String attuatore) {
        List<Modalita> modalita = recuperatore.getModalitaTutte(attuatore);
        return modalita
            .stream()
            .map(c -> c.getNome())
            .toArray(String[]::new);
    }

    /**
     * Metodo che restituisce una mappa delle descrizioni delle regole dell'unità i cui stati sono 'Attiva' o 'Disattiva'
     * La mappa ha come chiavi le antecedente delle regola unita al loro stato, come valori gli ID delle regole
     * @param unita
     * @return una mappa della descrizioni regole sopra descritte
     */
    public Map<String, String> getAntecentiRegoleAttiveDisattive(String unita){
        Map<String, String> map = new HashMap<>();
        List<Regola> regole = recuperatore.getRegoleAttiveDisattive(unita);
        for (Regola r : regole) {
            map.put(stringatore.rappresenta(r.getAntecedente()) + ":" + r.getStato().name(), r.getId());
        }
        return map;
    }

    /**
     * Ritorna la descrizione di un'azione programmata in elenco se presente.
     * @param id    identificativo dell'azione
     * @return  stringa contenente la descrizione dell'azione se presente, stringa vuota altrimenti
     */
    public String getDescrizioneAzioneProgrammata(String id) {
        // TODO: da rimuovere? (Non e' mai usato)
        Azione a = recuperatore.getAzioneProgrammata(id);
        if (a != null)
            return stringatore.rappresenta(a);
        else
            return "";
    }

    /**
     * Ritorna l'elenco di descrizioni delle azioni programmate.
     * @return  array di stringhe con le descrizioni delle azioni programmate
     */
    public String[] getDescrizioniAzioniProgrammate() {
        return recuperatore.getAzioniProgrammate().stream().map(azione -> stringatore.rappresenta(azione)).toArray(String[]::new);
    }
}
