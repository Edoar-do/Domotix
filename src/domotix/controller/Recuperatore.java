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
public class Recuperatore {
    // TODO: rivedi javadoc (rimuoverlo tutto e rifarlo da 0?)
    private Model model;

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
    public List<Unita> getListaUnita() {
        return model.getListaUnita();
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
        return unita.getStanze().stream().filter(s -> elencaStanzaDefault || !s.equals(UnitaImmobiliare.NOME_STANZA_DEFAULT)).collect(Collectors.toList());
    }

    private static List<Attuatore> getAttuatoriSistema(Sistema sistema) {
        return sistema.getAttuatori(); // nonsense
    }

    private static List<Sensore> getSensoriSistema(Sistema sistema) {
        return sistema.getSensori(); // nonsense
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
        List<Artefatto> artefatti = model.getStanza(nomeStanza, nomeUnita).getArtefatti();
        return artefatti;
    }

    /**
     * Metodo di recupero delle descrizioni delle categorie di sensori
     * all'interno del model.
     * @return Array di descrizioni di categorie di sensori
     */
    public List<CategoriaSensore> getCategorieSensori() {
        return model.getCategorie()
                .stream()
                .collect(Collectors.toList()); // nonsense
    }

    /**
     * Metodo di recupero delle descrizioni delle categorie di attuatori
     * all'interno del model.
     * @return Array di descrizioni di categorie di attuatori
     */
    public List<CategoriaAttuatore> getCategorieAttuatori() {
        return model.getCategorie()
                .stream()
                .collect(Collectors.toList()); // nonsense
    }

    /**
     * Metodo di recupero della descrizione di un'unita' immobiliare
     * all'interno del model.
     * @param nomeUnita Nome dell'untia' immobiliare
     * @return Descrizione dell'unita' immobiliare
     */
    public Unita getUnita(String nomeUnita) {
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

    private static List<Sensore> getSensoriAggiungibiliSistema(Sistema sistema, String nomeUnita) {
        List<Sensore> sensori = model.getUnita(nomeUnita).getSensori();
        return Stream.of(sensori)
                .filter(s -> !sistema.contieneCategoriaSensore(s.getCategoria().getNome()))
                .collect(Collectors.toList());
    }

    private static List<Attuatore> getAttuatoriAggiungibiliSistema(Sistema sistema, String nomeUnita) {
        List<Attuatore> attuatori = model.getUnita(nomeUnita).getAttuatori();
        return attuatori
                .filter(a -> !sistema.contieneCategoriaAttuatore(a.getCategoria().getNome()))
                .collect(Collectors.toList());
    }

    /**
     * Metodo di recupero dei nomi dei sensori gia' esistenti che possono essere
     * condivisi con la stanza specificata. In sostanza: il metodo recupera tutti i sensori
     * con una categoria di sensore che non e' gia' presente all'interno della stanza.
     * @param nomeStanza Nome della stanza
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return Array di nomi di sensori condivisibili con la stanza
     */
    public List<Sensore> getSensoriAggiungibiliStanza(String nomeStanza, String nomeUnita) {
        return this.getSensoriAggiungibiliSistema(getStanza(nomeStanza, nomeUnita), nomeUnita);
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
    public List<Sensore> getSensoriAggiungibiliArtefatto(String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return this.getSensoriAggiungibiliSistema(getArtefatto(nomeArtefatto, nomeStanza, nomeUnita), nomeUnita);
    }

    /**
     * Metodo di recupero dei nomi degli attuatori gia' esistenti che possono essere
     * condivisi con la stanza specificata. In sostanza: il metodo recupera tutti gli attuatori
     * con una categoria di attuatore che non e' gia' presente all'interno della stanza.
     * @param nomeStanza Nome della stanza
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return Array di nomi di attuatori condivisibili con la stanza
     */
    public List<Attuatore> getAttuatoriAggiungibiliStanza(String nomeStanza, String nomeUnita) {
        return this.getAttuatoriAggiungibiliSistema(getStanza(nomeStanza, nomeUnita), nomeUnita);
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
    public List<Attuatore> getAttuatoriAggiungibiliArtefatto(String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return this.getAttuatoriAggiungibiliSistema(getArtefatto(nomeArtefatto, nomeStanza, nomeUnita), nomeUnita);
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

        List<Sensore> sensoriUnita = model.getUnita(unita).getSensori();
        sensori.addAll(sensoriUnita);
        return sensori;
    }

    /**
     * Metodo che recupera i nomi degli attuatori presenti in un'unità immobiliare
     * @param unita da cui pescare i nomi degli attuatori
     * @return un array dei nomi degli attuatori dell'unità immobiliare
     */
    public List<Attuatori> getAttuatori(String unita){
        return model.getUnita(unita).getAttuatori();
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
        return model.getUnita(unita).getRegole();
    }

    public List<Parametro> getParametriModalita(String attuatore, String modalita) {
        return getAttuatore(attuatore)
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
    public Azione getProgrammata(String id) {
        Azione a = model.getAzioneProgrammata(id);
        return a;
    }

    /**
     * Ritorna l'elenco di descrizioni delle azioni programmate.
     * @return  array di stringhe con le descrizioni delle azioni programmate
     */
    public List<Azione> getAzioniProgrammate() {
        return model.getAzioniProgrammate().stream().map(azione -> azione.toString()).collect(Collectors.toList());
    }
}
