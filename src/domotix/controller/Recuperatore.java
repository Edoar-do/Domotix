package domotix.controller;

import domotix.model.*;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.regole.Azione;
import domotix.model.bean.regole.Regola;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Sistema;
import domotix.model.bean.system.Stanza;
import domotix.model.util.SommarioDispositivi;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**Classe per implementare una parte di logica controller relativa al recupero di informazioni sulle entita'.
 * Tale classe funge anche da unico punto di accesso per view e controller al model, passando per l'interfaccia Model.
 * Recuperatore è quindi un accesso centralizzato da cui passano tutte le richieste di oggetti di dominio.
 * @author andrea*/
public class Recuperatore {
    // TODO: rivedi javadoc (rimuoverlo tutto e rifarlo da 0?)
    private Model model;

    private UnitaImmobiliare unitaBase = null;

    public Recuperatore(Model model) {
        this.model = model;
    }
    
    /**
     * Metodo di recupero della modalita' operativa
     * in cui si trova correntemente l'attuatore.
     * @param nomeAttuatore Nome dell'Attuatore
     * @return Il nome della Modalita corrente
     */
    public Modalita getModalitaOperativaCorrente(String nomeAttuatore) {
        // catena evitabile?
        Attuatore attuatore = model.getAttuatore(nomeAttuatore);
        Modalita modalitaCorrente = attuatore.getModoOp();
        return modalitaCorrente;
    }

    /**
     * Metodo di recupero di una lista di nomi delle modalita' operative
     * di un attuatore, tranne quella in cui si trova correntemente.
     * @return Array di nomi di Modalita
     */
    public List<Modalita> getModalitaOperativeImpostabili(String nomeAttuatore) {
        // catena evitabile?
        Attuatore attuatore = model.getAttuatore(nomeAttuatore);
        Modalita modalitaCorrente = attuatore.getModoOp();
        CategoriaAttuatore categoria = attuatore.getCategoria();
        return categoria.getElencoModalita().stream()
                .filter(mod -> !mod.getNome().equals(modalitaCorrente.getNome()))
                .collect(Collectors.toList());
    }

    /**
     * Metodo di recupero di una lista di nomi delle unita' immobiliari
     * presenti all'interno del model.
     * @return Array di nomi delle unita' immobiliari esistenti
     */
    public List<UnitaImmobiliare> getListaUnita() {
        return model.getListaUnita();
    }

    /**
     * Recupera l'unita' base che deve esistere per il funzionamento del programma.
     *
     * @return  istanza di una nuova unita immobiliare inizializzata con i dati di default del programma
     */
    public UnitaImmobiliare getUnitaBase() {
        if (unitaBase == null)
            unitaBase = this.model.generaUnitaBase();
        return unitaBase;
    }

    /**
     * Metodo di recupero di una lista di nomi di tutte le stanze
     * (compresa quella di default) presenti all'interno di un'unita' immobiliare.
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return Array di nomi delle stanze esistenti
     */
    public List<Stanza> getStanze(String nomeUnita) {
        return this.getStanze(nomeUnita, true);
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
    public List<Stanza> getStanze(String nomeUnita, boolean elencaStanzaDefault) {
        UnitaImmobiliare unita = model.getUnita(nomeUnita);
        return Stream.of(unita.getStanze()).filter(s -> elencaStanzaDefault || !s.equals(UnitaImmobiliare.NOME_STANZA_DEFAULT)).collect(Collectors.toList());
    }

    public List<Attuatore> getAttuatoriSistema(Sistema sistema) {
        return Arrays.asList(sistema.getAttuatori()); // nonsense
    }

    public List<Sensore> getSensoriSistema(Sistema sistema) {
        return Arrays.asList(sistema.getSensori()); // nonsense
    }

    /**
     * Metodo di recupero dei nomi degli attuatori all'interno di una stanza.
     * @param nomeStanza Nome della stanza selezionata
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return Array di nomi di attuatori
     */
    public List<Attuatore> getAttuatori(String nomeStanza, String nomeUnita) {
        return this.getAttuatoriSistema(model.getStanza(nomeStanza, nomeUnita));
    }

    /**
     * Metodo di recupero dei nomi dei sensori all'interno di una stanza.
     * @param nomeStanza Nome della stanza selezionata
     * @param nomeUnita Nome dell'unita' selezionata
     * @return Array di nomi di sensori
     */
    public List<Sensore> getSensori(String nomeStanza, String nomeUnita) {
        return this.getSensoriSistema(model.getStanza(nomeStanza, nomeUnita));
    }

    /**
     * Metodo di recupero dei nomi degli attuatori all'interno di un artefatto.
     * @param nomeArtefatto Nome dell'artefatto selezionato
     * @param nomeStanza Nome della stanza selezionata
     * @param nomeUnita Nome dell'unita' selezionata
     * @return Array di nomi di attuatori
     */
    public List<Attuatore> getAttuatori(String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return this.getAttuatoriSistema(getArtefatto(nomeArtefatto, nomeStanza, nomeUnita));
    }

    /**
     * Metodo di recupero dei nomi dei sensori all'interno di un artefatto.
     * @param nomeArtefatto Nome dell'artefatto selezionato
     * @param nomeStanza Nome della stanza selezionata
     * @param nomeUnita Nome dell'unita' selezionata
     * @return Array di nomi di sensori
     */
    public List<Sensore> getSensori(String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return this.getSensoriSistema(getArtefatto(nomeArtefatto, nomeStanza, nomeUnita));
    }

    /**
     * Metodo di recupero dei nomi degli artefatti all'interno di una stanza
     * @param nomeStanza Nome della stanza selezionata
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return Array di nomi di artefatti
     */
    public List<Artefatto> getArtefatti(String nomeStanza, String nomeUnita) {
        // catena evitabile?
        List<Artefatto> artefatti = Arrays.asList(model.getStanza(nomeStanza, nomeUnita).getArtefatti());
        return artefatti;
    }

    /**
     * Metodo di recupero delle descrizioni delle categorie di sensori
     * all'interno del model.
     * @return Array di descrizioni di categorie di sensori
     */
    public List<CategoriaSensore> getCategorieSensore() {
        return model.getCategorieSensore();
    }

    /**
     * Metodo di recupero delle descrizioni delle categorie di attuatori
     * all'interno del model.
     * @return Array di descrizioni di categorie di attuatori
     */
    public List<CategoriaAttuatore> getCategorieAttuatore() {
        return model.getCategorieAttuatore();
    }

    /**
     * Metodo di recupero della descrizione di un'unita' immobiliare
     * all'interno del model.
     * @param nomeUnita Nome dell'untia' immobiliare
     * @return Descrizione dell'unita' immobiliare
     */
    public UnitaImmobiliare getUnita(String nomeUnita) {
        return model.getUnita(nomeUnita);
    }

    /**
     * Metodo di recupero della descrizione di una stanza
     * all'interno di un'unita' immobiliare specificata.
     * @param nomeStanza Nome della stanza
     * @param nomeUnita Nome dell'untia' immobiliare selezionata
     * @return Descrizione della stanza
     */
    public Stanza getStanza(String nomeStanza, String nomeUnita) {
        return model.getStanza(nomeStanza, nomeUnita);
    }

    /**
     * Metodo di recupero della descrizione di un artefatto
     * all'interno di una stanza specificata.
     * @param nomeArtefatto Nome dell'artefatto
     * @param nomeStanza Nome della stanza selezionata
     * @param nomeUnita Nome dell'untia' immobiliare selezionata
     * @return Descrizione della stanza
     */
    public Artefatto getArtefatto(String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return model.getArtefatto(nomeArtefatto, nomeStanza, nomeUnita);
    }

    public List<Sensore> getSensoriAggiungibiliSistema(Sistema sistema, String nomeUnita) {
        List<Sensore> sensori = Arrays.asList(model.getUnita(nomeUnita).getSensori());
        return sensori.stream()
                .filter(s -> !sistema.contieneCategoriaSensore(s.getCategoria().getNome()))
                .collect(Collectors.toList());
    }

    public List<Attuatore> getAttuatoriAggiungibiliSistema(Sistema sistema, String nomeUnita) {
        List<Attuatore> attuatori = Arrays.asList(model.getUnita(nomeUnita).getAttuatori());
        return attuatori.stream()
                .filter(a -> !sistema.contieneCategoriaAttuatore(a.getCategoria().getNome()))
                .collect(Collectors.toList());
    }

    /**
     * Metodo che recupera i nomi dei sensori presenti in un'unita' immobiliare
     * @param unita da cui pescare i nomi dei sensori
     * @return un array dei nomi dei sensori dell'unità immobiliare
     */
    public List<Sensore> getSensori(String unita){
        return this.getSensori(unita, false);
    }

    /**
     * Metodo che recupera i nomi dei sensori presenti in un'unita' immobiliare
     * @param unita da cui pescare i nomi dei sensori
     * @param includiOrologio   true: include in elenco il sensore universale SensoreOrologio; false: altrimenti
     * @return un array dei nomi dei sensori dell'unità immobiliare
     */
    public List<Sensore> getSensori(String unita, boolean includiOrologio){
        List<Sensore> sensori = new ArrayList<>();

        if (includiOrologio)
            sensori.add(model.getOrologio());

        List<Sensore> sensoriUnita = Arrays.asList(model.getUnita(unita).getSensori());
        sensori.addAll(sensoriUnita);
        return sensori;
    }

    /**
     * Metodo che recupera i nomi degli attuatori presenti in un'unità immobiliare
     * @param unita da cui pescare i nomi degli attuatori
     * @return un array dei nomi degli attuatori dell'unità immobiliare
     */
    public List<Attuatore> getAttuatori(String unita){
        return Arrays.asList(model.getUnita(unita).getAttuatori());
    }

    /**
     * Metodo che ritorna un array dei nomi delle informazioni rilevabili da un sensore
     * @param nomeSensore di cui sapere le info rilevabili
     * @return un array dei nomi delle info rilevabili dal sensore
     */
    public List<InfoRilevabile> getInformazioniRilevabili(String nomeSensore) {
        return model.getSensore(nomeSensore)
                .getCategoria()
                .getInformazioniRilevabili()
                .stream()
                .collect(Collectors.toList()); // nonsense
    }

    /**
     * Metodo che ritorna la mappa delle descrizioni degli antecedenti delle regole relative a un'unita'.
     * La mappa ha come chiavi le descrizioni delle regole unite al loro stato, come valori gli ID delle regole.
     * @param unita UnitaImmobiliare selezionata
     * @return La mappa delle descrizioni degli antecedenti
     */
    public List<Regola> getRegoleUnita(String unita) {
        return Arrays.asList(model.getUnita(unita).getRegole());
    }

    public List<Parametro> getParametriModalita(String attuatore, String modalita) {
        return model.getAttuatore(attuatore)
                .getCategoria()
                .getModalita(modalita)
                .getParametri()
                .stream()
                .collect(Collectors.toList()); //
    }

    /**
     * Metodo che ritorna la lista delle descrizioni delle modalita' relative a un attuatore.
     * @param attuatore Attuatore selezionato
     * @return La lista delle descrizioni
     */
    public List<Modalita> getModalitaTutte(String attuatore) {
        return model.getAttuatore(attuatore)
                .getCategoria()
                .getElencoModalita()
                .stream()
                .collect(Collectors.toList()); //
    }

    /**
     * Metodo che restituisce una mappa delle descrizioni delle regole dell'unità i cui stati sono 'Attiva' o 'Disattiva'
     * La mappa ha come chiavi le antecedente delle regola unita al loro stato, come valori gli ID delle regole
     * @param unita
     * @return una mappa della descrizioni regole sopra descritte
     */
    public List<Regola> getRegoleAttiveDisattive(String unita){
        List<Regola> list = new ArrayList<>();
        Regola[] regole = model.getUnita(unita).getRegole();
        for (Regola r : regole) {
            if(r.getStato().name().equals("ATTIVA") || r.getStato().name().equals("DISATTIVA")) {
                list.add(r);
            }
        }
        return list;
    }

    /**
     * Ritorna la descrizione di un'azione programmata in elenco se presente.
     * @param id    identificativo dell'azione
     * @return  stringa contenente la descrizione dell'azione se presente, stringa vuota altrimenti
     */
    public Azione getAzioneProgrammata(String id) {
        Azione a = model.getAzioneProgrammata(id);
        return a;
    }

    /**
     * Ritorna l'elenco di azioni programmate.
     * @return  array di azioni programmate
     */
    public List<Azione> getAzioniProgrammate() {
        return model.getAzioniProgrammate();
    }

     /**
     * Ritorna l'elenco di id delle azioni programmate.
     * @return  array di stringhe con gli id delle azioni programmate
     */
    public List<String> getIdAzioniProgrammate() {
        return model.getIdAzioniProgrammate();
    }

    /**
     * Ritorna un elenco di coppie id - azione per le azioni programmate
     * @return  map di stringhe e azioni per tutte le azioni programmate con relativo id
     */
    public Map<String, Azione> getCoppieIdAzione() {
        Map<String, Azione> ret = new HashMap<>();
        getIdAzioniProgrammate().forEach(s -> ret.put(s, getAzioneProgrammata(s)));
        return ret;
    }

    /**
     * Ritorna la categoria di sensori specificata dal nome univoco
     * @param catSensore nome su cui avviene la ricerca
     * @return la categoria di sensori richiesta
     */
    public CategoriaSensore getCategoriaSensore(String catSensore) {   return this.model.getCategoriaSensore(catSensore);   }

    /**
     * Ritorna la categoria di attuatori specificata dal nome univoco
     * @param cat nome su cui avviene la ricerca
     * @return la  categoria di attuatori richiesta
     */
    public CategoriaAttuatore getCategoriaAttuatore(String cat) {   return this.model.getCategoriaAttuatore(cat);   }

    /**
     * Ritorna l'attuatore richiesto sulla base del suo nome
     * @param nomeAttuatore su cui effettuare la ricerca
     * @return l'attuatore richiesto
     */
    public Attuatore getAttuatore(String nomeAttuatore) {  return this.model.getAttuatore(nomeAttuatore);  }

    /**
     * Ritorna il sensore richiesto sulla base del nome fornito
     * @param nsensoreDestro nome su cui effettuare la ricerca
     * @return sensore richiesto
     */
    public Sensore getSensore(String nsensoreDestro) { return this.model.getSensore(nsensoreDestro); }

    /**
     * Ritorna la lista di sensori
     * @return  lista di sensori
     */
    public List<Sensore> getSensori() {
        return model.getSensori();
    }
    
    /**
     * Ritorna la lista di attuatori
     * @return  lista di attuatori
     */
    public List<Attuatore> getAttuatori() {
        return model.getAttuatori();
    }

    /**
     * Ritorna il sensore orologio
     * @return  istanza di sensore orologio
     */
    public SensoreOrologio getSensoreOrologio() {
        return this.model.getOrologio();
    }

    /**
     * Ritorna il sommario per i sensori
     * @return  istanza di sommariodispositivi inerenti a sensori
     */
    public SommarioDispositivi getSommarioSensori() {
        return this.model.getSommarioSensori();
    }

    /**
     * Ritorna il sommario per i attuatori
     * @return  istanza di sommariodispositivi inerenti a attuatori
     */
    public SommarioDispositivi getSommarioAttuatori() {
        return this.model.getSommarioAttuatori();
    }
}
