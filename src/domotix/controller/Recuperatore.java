package domotix.controller;

import domotix.model.*;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.regole.Azione;
import domotix.model.bean.regole.Regola;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Sistema;
import domotix.model.bean.system.Stanza;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**Classe per implementare una parte di logica controller relativa al recupero di informazioni sulle entita'.
 * @author andrea*/
public class Recuperatore {
    static UnitaImmobiliare getUnita(String nomeUnita) {
        UnitaImmobiliare unita = ElencoUnitaImmobiliari.getInstance().getUnita(nomeUnita);
        return unita;
    }

    static Stanza getStanza(String nomeStanza, String nomeUnita) {
        Stanza[] stanze = getUnita(nomeUnita).getStanze();
        Stanza stanza = null;
        for (Stanza s : stanze) {
            if (s.getNome().equals(nomeStanza)) {
                stanza = s;
                break;
            }
        }
        return stanza;
    }

    static Artefatto getArtefatto(String nomeArtefatto, String nomeStanza, String nomeUnita) {
        Artefatto[] artefatti = getStanza(nomeStanza, nomeUnita).getArtefatti();
        Artefatto artefatto = null;
        for (Artefatto a : artefatti) {
            if (a.getNome().equals(nomeArtefatto)) {
                artefatto = a;
                break;
            }
        }
        return artefatto;
    }

    static Sensore getSensore(String nomeSensore) {
        return ElencoSensori.getInstance().getDispositivo(nomeSensore);
    }

    static Attuatore getAttuatore(String nomeAttuatore) {
        return ElencoAttuatori.getInstance().getDispositivo(nomeAttuatore);
    }

    static CategoriaSensore getCategoriaSensore(String nomeCategoria) {
        return ElencoCategorieSensori.getInstance().getCategoria(nomeCategoria);
    }

    static CategoriaAttuatore getCategoriaAttuatore(String nomeCategoria) {
        return ElencoCategorieAttuatori.getInstance().getCategoria(nomeCategoria);
    }

    static Azione getAzioneProgrammata(String idAzione) {
        return ElencoAzioniProgrammate.getInstance().getAzione(idAzione);
    }

    /**
     * Metodo di recupero della modalita' operativa
     * in cui si trova correntemente l'attuatore.
     * @param nomeAttuatore Nome dell'Attuatore
     * @return Il nome della Modalita corrente
     */
    public static String getModalitaOperativaCorrente(String nomeAttuatore) {
        Attuatore attuatore = getAttuatore(nomeAttuatore);
        Modalita modalitaCorrente = attuatore.getModoOp();
        return modalitaCorrente.getNome();
    }

    /**
     * Metodo di recupero di una lista di nomi delle modalita' operative
     * di un attuatore, tranne quella in cui si trova correntemente.
     * @return Array di nomi di Modalita
     */
    public static String[] getModalitaOperativeImpostabili(String nomeAttuatore) {
        Attuatore attuatore = getAttuatore(nomeAttuatore);
        Modalita modalitaCorrente = attuatore.getModoOp();
        CategoriaAttuatore categoria = attuatore.getCategoria();
        return categoria.getElencoModalita().stream()
                .filter(mod -> !mod.getNome().equals(modalitaCorrente.getNome()))
                .map(mod -> mod.getNome())
                .toArray(String[]::new);
    }

    /**
     * Metodo di recupero di una lista di nomi delle unita' immobiliari
     * presenti all'interno del model.
     * @return Array di nomi delle unita' immobiliari esistenti
     */
    public static String[] getNomiUnita() {
        return ElencoUnitaImmobiliari.getInstance().getUnita()
                .stream()
                .map(u -> u.getNome())
                .toArray(String[]::new);
    }

    /**
     * Metodo di recupero di una lista di descrizioni delle unita' immobiliari
     * presenti all'interno del model.
     * @return Array di descrizioni delle unita' immobiliari presenti
     */
    public static String[] getDescrizioniUnita() {
        return ElencoUnitaImmobiliari.getInstance().getUnita()
                .stream()
                .map(u -> u.toString())
                .toArray(String[]::new);
    }

    /**
     * Metodo di recupero di una lista di nomi di tutte le stanze
     * (compresa quella di default) presenti all'interno di un'unita' immobiliare.
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return Array di nomi delle stanze esistenti
     */
    public static String[] getNomiStanze(String nomeUnita) {
        return getNomiStanze(nomeUnita, true);
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
    public static String[] getNomiStanze(String nomeUnita, boolean elencaStanzaDefault) {
        UnitaImmobiliare unita = getUnita(nomeUnita);
        return Stream.of(unita.getStanze()).map(s -> s.getNome()).filter(s -> elencaStanzaDefault || !s.equals(UnitaImmobiliare.NOME_STANZA_DEFAULT)).toArray(String[]::new);
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
    public static String[] getNomiAttuatori(String nomeStanza, String nomeUnita) {
        return getNomiAttuatoriSistema(getStanza(nomeStanza, nomeUnita));
    }

    /**
     * Metodo di recupero dei nomi dei sensori all'interno di una stanza.
     * @param nomeStanza Nome della stanza selezionata
     * @param nomeUnita Nome dell'unita' selezionata
     * @return Array di nomi di sensori
     */
    public static String[] getNomiSensori(String nomeStanza, String nomeUnita) {
        return getNomiSensoriSistema(getStanza(nomeStanza, nomeUnita));
    }

    /**
     * Metodo di recupero dei nomi degli attuatori all'interno di un artefatto.
     * @param nomeArtefatto Nome dell'artefatto selezionato
     * @param nomeStanza Nome della stanza selezionata
     * @param nomeUnita Nome dell'unita' selezionata
     * @return Array di nomi di attuatori
     */
    public static String[] getNomiAttuatori(String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return getNomiAttuatoriSistema(getArtefatto(nomeArtefatto, nomeStanza, nomeUnita));
    }

    /**
     * Metodo di recupero dei nomi dei sensori all'interno di un artefatto.
     * @param nomeArtefatto Nome dell'artefatto selezionato
     * @param nomeStanza Nome della stanza selezionata
     * @param nomeUnita Nome dell'unita' selezionata
     * @return Array di nomi di sensori
     */
    public static String[] getNomiSensori(String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return getNomiSensoriSistema(getArtefatto(nomeArtefatto, nomeStanza, nomeUnita));
    }

    /**
     * Metodo di recupero dei nomi degli artefatti all'interno di una stanza
     * @param nomeStanza Nome della stanza selezionata
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return Array di nomi di artefatti
     */
    public static String[] getNomiArtefatti(String nomeStanza, String nomeUnita) {
        Artefatto[] artefatti = getStanza(nomeStanza, nomeUnita).getArtefatti();
        return Stream.of(artefatti).map(a -> a.getNome()).toArray(String[]::new);
    }

    /**
     * Metodo di recupero dei nomi delle categorie di sensori all'interno
     * del model.
     * @return Array di nomi di categorie di sensori
     */
    public static String[] getNomiCategorieSensori() {
        return ElencoCategorieSensori.getInstance().getCategorie()
                .stream()
                .map(cs -> cs.getNome())
                .toArray(String[]::new);
    }

    /**
     * Metodo di recupero dei nomi delle categorie di attuatori all'interno
     * del model.
     * @return Array di nomi di categorie di attuatori
     */
    public static String[] getNomiCategorieAttuatori() {
        return ElencoCategorieAttuatori.getInstance().getCategorie()
                .stream()
                .map(cs -> cs.getNome())
                .toArray(String[]::new);
    }

    /**
     * Metodo di recupero delle descrizioni delle categorie di sensori
     * all'interno del model.
     * @return Array di descrizioni di categorie di sensori
     */
    public static String[] getDescrizioniCategorieSensori() {
        return ElencoCategorieSensori.getInstance().getCategorie()
                .stream()
                .map(cs -> cs.toString())
                .toArray(String[]::new);
    }

    /**
     * Metodo di recupero delle descrizioni delle categorie di attuatori
     * all'interno del model.
     * @return Array di descrizioni di categorie di attuatori
     */
    public static String[] getDescrizioniCategorieAttuatori() {
        return ElencoCategorieAttuatori.getInstance().getCategorie()
                .stream()
                .map(cs -> cs.toString())
                .toArray(String[]::new);
    }

    /**
     * Metodo di recupero della descrizione di un'unita' immobiliare
     * all'interno del model.
     * @param nomeUnita Nome dell'untia' immobiliare
     * @return Descrizione dell'unita' immobiliare
     */
    public static String getDescrizioneUnita(String nomeUnita) {
        return getUnita(nomeUnita).toString();
    }

    /**
     * Metodo di recupero della descrizione di una stanza
     * all'interno di un'unita' immobiliare specificata.
     * @param nomeStanza Nome della stanza
     * @param nomeUnita Nome dell'untia' immobiliare selezionata
     * @return Descrizione della stanza
     */
    public static String getDescrizioneStanza(String nomeStanza, String nomeUnita) {
        return getStanza(nomeStanza, nomeUnita).toString();
    }

    /**
     * Metodo di recupero della descrizione di un artefatto
     * all'interno di una stanza specificata.
     * @param nomeArtefatto Nome dell'artefatto
     * @param nomeStanza Nome della stanza selezionata
     * @param nomeUnita Nome dell'untia' immobiliare selezionata
     * @return Descrizione della stanza
     */
    public static String getDescrizioneArtefatto(String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return getArtefatto(nomeArtefatto, nomeStanza, nomeUnita).toString();
    }

    private static String[] getNomiSensoriAggiungibiliSistema(Sistema sistema, String nomeUnita) {
        Sensore[] sensori = getUnita(nomeUnita).getSensori();
        return Stream.of(sensori)
                .filter(s -> !sistema.contieneCategoriaSensore(s.getCategoria().getNome()))
                .map(s -> s.getNome())
                .toArray(String[]::new);
    }

    private static String[] getNomiAttuatoriAggiungibiliSistema(Sistema sistema, String nomeUnita) {
        Attuatore[] attuatori = getUnita(nomeUnita).getAttuatori();
        return Stream.of(attuatori)
                .filter(a -> !sistema.contieneCategoriaAttuatore(a.getCategoria().getNome()))
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
    public static String[] getNomiSensoriAggiungibiliStanza(String nomeStanza, String nomeUnita) {
        return getNomiSensoriAggiungibiliSistema(getStanza(nomeStanza, nomeUnita), nomeUnita);
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
    public static String[] getNomiSensoriAggiungibiliArtefatto(String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return getNomiSensoriAggiungibiliSistema(getArtefatto(nomeArtefatto, nomeStanza, nomeUnita), nomeUnita);
    }

    /**
     * Metodo di recupero dei nomi degli attuatori gia' esistenti che possono essere
     * condivisi con la stanza specificata. In sostanza: il metodo recupera tutti gli attuatori
     * con una categoria di attuatore che non e' gia' presente all'interno della stanza.
     * @param nomeStanza Nome della stanza
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return Array di nomi di attuatori condivisibili con la stanza
     */
    public static String[] getNomiAttuatoriAggiungibiliStanza(String nomeStanza, String nomeUnita) {
        return getNomiAttuatoriAggiungibiliSistema(getStanza(nomeStanza, nomeUnita), nomeUnita);
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
    public static String[] getNomiAttuatoriAggiungibiliArtefatto(String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return getNomiAttuatoriAggiungibiliSistema(getArtefatto(nomeArtefatto, nomeStanza, nomeUnita), nomeUnita);
    }

    /**
     * Metodo che recupera i nomi dei sensori presenti in un'unita' immobiliare
     * @param unita da cui pescare i nomi dei sensori
     * @return un array dei nomi dei sensori dell'unità immobiliare
     */
    public static String[] getNomiSensori(String unita){
        Sensore [] sensori = getUnita(unita).getSensori();
        return Stream.of(sensori).map(a -> a.getNome()).toArray(String[]::new);
    }

    /**
     * Metodo che recupera i nomi degli attuatori presenti in un'unità immobiliare
     * @param unita da cui pescare i nomi degli attuatori
     * @return un array dei nomi degli attuatori dell'unità immobiliare
     */
    public static String[] getNomiAttuatori(String unita){
        Attuatore [] attuatori = getUnita(unita).getAttuatori();
        return Stream.of(attuatori).map(a -> a.getNome()).toArray(String[]::new);
    }

    /**
     * Metodo che ritorna un array dei nomi delle informazioni rilevabili da un sensore
     * @param nomeSensore di cui sapere le info rilevabili
     * @return un array dei nomi delle info rilevabili dal sensore
     */
    public static String[] getInformazioniRilevabili(String nomeSensore) {
        return getSensore(nomeSensore)
                .getCategoria()
                .getInformazioniRilevabili()
                .stream()
                .map(i -> i.getNome())
                .toArray(String[]::new);
    }

    /**
     * Metodo che ritorna la lista delle descrizioni delle regole relative a un'unita'.
     * @param unita UnitaImmobiliare selezionata
     * @return La lista delle descrizioni
     */
    public static String[] getRegoleUnita(String unita) {
        Regola[] regole = getUnita(unita).getRegole();
        return Stream.of(regole).map(r -> r.toString()).toArray(String[]::new);
    }

    public static boolean isInfoNumerica(String nsensoreDestro, String info) {
        Sensore sensore = getSensore(nsensoreDestro);
        return (sensore.getValore(info) instanceof Number); //
    }

    /**
     * Metodo che ritorna la mappa delle descrizioni degli antecedenti delle regole relative a un'unita'.
     * La mappa ha come chiavi le antecedenti delle regole unite al loro stato, come valori gli ID delle regole.
     * @param unita UnitaImmobiliare selezionata
     * @return La mappa delle descrizioni degli antecedenti
     */
    public static Map<String, String> getAntecedentiRegoleUnita(String unita) {
        Map<String, String> map = new HashMap<>();
        Regola[] regole = getUnita(unita).getRegole();
        for (Regola r : regole) {
            map.put(r.getAntecedente().toString() + ":" + r.getStato().name(), r.getId());
        }
        return map;
    }

    public static boolean isModalitaParametrica(String attuatore, String modalita) {
        return getAttuatore(attuatore)
                .getCategoria()
                .getModalita(modalita)
                .isParametrica(); //
    }

    public static String[] getNomiParametriModalita(String attuatore, String modalita) {
        return getAttuatore(attuatore)
                .getCategoria()
                .getModalita(modalita)
                .getParametri()
                .stream()
                .map(p -> p.getNome())
                .toArray(String[]::new); //
    }

    /**
     * Metodo che ritorna la lista delle descrizioni delle modalita' relative a un attuatore.
     * @param attuatore Attuatore selezionato
     * @return La lista delle descrizioni
     */
    public static String[] getModalitaTutte(String attuatore) {
        return getAttuatore(attuatore)
                .getCategoria()
                .getElencoModalita()
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
    public static Map<String, String> getAntecentiRegoleAttiveDisattive(String unita){
        Map<String, String> map = new HashMap<>();
        Regola[] regole = getUnita(unita).getRegole();
        for (Regola r : regole) {
            if(r.getStato().name().equals("ATTIVA") || r.getStato().name().equals("DISATTIVA"))
                map.put(r.getAntecedente().toString() + ":" + r.getStato().name(), r.getId());
        }
        return map;
    }

    /**
     * Ritorna la descrizione di un'azione programmata in elenco se presente.
     * @param id    identificativo dell'azione
     * @return  stringa contenente la descrizione dell'azione se presente, stringa vuota altrimenti
     */
    public static String getDescrizioneAzioneProgrammata(String id) {
        Azione a = getAzioneProgrammata(id);
        if (a != null)
            return a.toString();
        else
            return "";
    }
}
