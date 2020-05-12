package domotix.controller;

import domotix.model.*;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.regole.Antecedente;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;

import java.util.regex.Pattern;

/**Classe per implementare una parte di logica controller relativa alla verifica della validita' dei dati che dovranno essere aggiunti al model.
 * @author andrea*/
public class Verificatore {

    private static final String REGEX_NOMI = "[A-Za-z][A-Za-z0-9]*";
    private static final String REGEX_NOMI_DISPOSITIVI = REGEX_NOMI + "_" + REGEX_NOMI;

    private Recuperatore recuperatore;

    /**
     * Costruttore della classe che si serve di una istanze di Recuperatore
     * @param recuperatore
     */
    public Verificatore(Recuperatore recuperatore) {
        this.recuperatore = recuperatore;
    }

    private boolean isNomeValido(String nome) {
        return nome.matches("^(" + REGEX_NOMI + ")$");
    }

    private boolean isNomeDispositivoValido(String nome) {
        return nome.matches("^(" + REGEX_NOMI_DISPOSITIVI + ")$");
    }

    private boolean checkUnivocitaUnitaImmobiliare(String nome) {
        return !recuperatore.getListaUnita().stream().anyMatch(u -> nome.equals(u.getNome()));
    }

    /**
     * Controlla l'esistenza di almeno un'unita' immobiliare.
     * Questo perche' vi deve essere una sola (o almeno una) unita immobiliare, la quale viene generata vuota con
     * un nome scolpito all'avvio del programma nel caso non sia gia' presente.
     *
     * @return  true: esiste almeno un'unita immobiliare; false: l'elenco e' vuoto
     */
    public boolean controlloEsistenzaUnita() {
        return !this.recuperatore.getListaUnita().isEmpty();
    }

    /**
     * Verifica che il valore passato come parametro coincida con il nome del sensore orologio oppure alla composizione
     * del nome del sensore orologio con la sua informazione rilevabile (composizione tramite un punto '.')
     *
     * @param valore    stringa da controllare
     * @return  true: coincide con i dati del SensoreOrologio; false: altrimenti;
     * @see SensoreOrologio
     */
    public boolean checkIsSensoreOrologio(String valore) {
        return valore.equals(SensoreOrologio.NOME_SENSORE_OROLOGIO) ||
            valore.equals(SensoreOrologio.NOME_SENSORE_OROLOGIO + "." + SensoreOrologio.NOME_INFO_RILEVABILE_OROLOGIO);
    }

    public boolean checkValiditaOperatoreLogico(String oplog) {
        return Antecedente.OPERATORE_AND.equals(oplog) || Antecedente.OPERATORE_OR.equals(oplog);
    }

    /**
     * Verifica che le informazioni fornite per la costruzione e l'aggiunta di
     * un'InfoRilevabile all'interno del Model siano valide
     * @param catSensore Nome della CategoriaSensore che misura l'InfoRilevabile
     * @param nome Nome dell'InfoRilevabile
     * @return true se le informazioni sono valide
     */
    public boolean checkValiditaInfoRilevabile(String catSensore, String nome) {
        // Il nome deve essere univoco a livello di categoria sensore
        if (!isNomeValido(nome)) return false;
        CategoriaSensore categoriaSensore = recuperatore.getCategoriaSensore(catSensore);
        if (categoriaSensore == null) return false;
        return true;
    }

    /**
     * Verifica che le informazioni fornite per la costruzione e l'aggiunta di
     * un Parametro all'interno del Model siano valide
     * @param cat Nome della CategoriaAttuatore di appartenenza
     * @param mod Nome della ModalitaOperativa di appartenenza
     * @param nome Nome del Parametro
     * @return true se le informazioni sono valide
     */
    public boolean checkValiditaParametro(String cat, String mod, String nome) {
        if (!isNomeValido(nome)) return false;
        CategoriaAttuatore categoria = recuperatore.getCategoriaAttuatore(cat);
        if (categoria == null) return false;
        if (!categoria.hasModalita(mod)) return false;
        Modalita modalita = categoria.getModalita(mod);
        //if (!modalita.isParametrica()) return false;
        if (modalita.getParametro(nome) != null) return false;
        return true;
    }

    /**
     * Verifica che le informazioni fornite per la costruzione e l'aggiunta di
     * un'UnitaImmobiliare all'interno del Model siano valide
     * @param nome Nome dell'UnitaImmobiliare
     * @return true se le informazioni sono valide
     */
    public boolean checkValiditaUnitaImmobiliare(String nome) {
        return isNomeValido(nome) && checkUnivocitaUnitaImmobiliare(nome);
    }

    /**
     * Verifica che le informazioni fornite per la costruzione e l'aggiunta di
     * una ModalitaOperativa all'interno del Model siano valide
     * @param nome Nome della modalita' operativa
     * @return true se le informazioni sono valide
     */
    public boolean checkValiditaModalitaOperativa(String nome) {
        return isNomeValido(nome);
    }

    public boolean checkValiditaModalitaOperativaPerAttuatore(String nomeAttuatore, String nomeModalita) {
        Attuatore attuatore = recuperatore.getAttuatore(nomeAttuatore);
        if (attuatore == null) return false;
        CategoriaAttuatore categoriaAttuatore = attuatore.getCategoria();
        if (!categoriaAttuatore.hasModalita(nomeModalita)) return false;
        return true;
    }

    /**
     * Verifica che le informazioni fornite per la costruzione e l'aggiunta di
     * una CategoriaSensore all'interno del Model siano valide
     * @param nome Nome della categoria di sensore
     * @return true se le informazioni sono valide
     */
    public boolean checkValiditaCategoriaSensore(String nome) {
        return isNomeValido(nome) &&
            !recuperatore.getCategorieSensore()
            .stream()
            .anyMatch(cs -> nome.equals(cs.getNome()));
    }

    /**
     * Verifica che le informazioni fornite per la costruzione e l'aggiunta di
     * una CategoriaAttuatore all'interno del Model siano valide
     * @param nome Nome della categoria di attuatore
     * @return true se le informazioni sono valide
     */
    public boolean checkValiditaCategoriaAttuatore(String nome) {
        return isNomeValido(nome) &&
            !recuperatore.getCategorieSensore()
            .stream()
            .anyMatch(ca -> nome.equals(ca.getNome()));
    }

    /**
     * Verifica che le informazioni fornite per la costruzione e l'aggiunta di
     * una Stanza all'interno del Model siano valide
     * @param nomeStanza Nome della stanza
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return true se le informazioni sono valide
     */
    public boolean checkValiditaStanza(String nomeStanza, String nomeUnita) {
        return isNomeValido(nomeStanza) &&
            !nomeStanza.equals(UnitaImmobiliare.NOME_STANZA_DEFAULT) &&
            recuperatore.getUnita(nomeUnita) != null &&
            recuperatore.getStanza(nomeStanza, nomeUnita) == null;
    }

    /**
     * Verifica che le informazioni fornite per la costruzione e l'aggiunta di
     * un Artefatto all'interno del Model siano valide
     * @param nomeArtefatto Nome dell'artefatto
     * @param nomeUnita Nome dell'unita' immobiliare selezionata
     * @return true se le informazioni sono valide
     */
    public boolean checkValiditaArtefatto(String nomeArtefatto, String nomeUnita) {
        if (!isNomeValido(nomeArtefatto)) return false;
        if (recuperatore.getUnita(nomeUnita) == null) return false;
        for (Stanza s : recuperatore.getUnita(nomeUnita).getStanze()) {
            for (Artefatto a : s.getArtefatti()) {
                if (a.getNome().equals(nomeArtefatto)) return false;
            }
        }
        return true;
    }

    /**
     * Verifica che il sensore specificato sia univoco a livello di Model
     * @param nomeSensore Nome del sensore
     * @return true se il sensore e' univoco
     */
    public boolean checkUnivocitaSensore(String nomeSensore) {
        for (Sensore s : recuperatore.getSensori()) {
            if (s.getNome().equals(nomeSensore)) return false;
        }
        return true;
    }

    /**
     * Verifica che l'attuatore specificato sia univoco a livello di Model
     * @param nomeAttuatore Nome dell'attuatore
     * @return true se l'attuatore e' univoco
     */
    public boolean checkUnivocitaAttuatore(String nomeAttuatore) {
        for (Attuatore a : recuperatore.getAttuatori()) {
            if (a.getNome().equals(nomeAttuatore)) return false;
        }
        return true;
    }

    /**
     * Verifica che le informazioni fornite per la costruzione e l'aggiunta di
     * un Sensore all'interno di una Stanza siano valide
     * @param nomeComposto Nome del sensore
     * @param nomeCategoria Nome della categoria del sensore
     * @param nomeStanza Nome della stanza a cui appartiene il sensore
     * @param nomeUnita Nome dell'unita' immobiliare a cui appartiene il sensore
     * @return true se le informazioni sono valide
     */
    public boolean checkValiditaSensore(String nomeComposto, String nomeCategoria, String nomeStanza, String nomeUnita) {
        return isNomeDispositivoValido(nomeComposto) &&
            checkUnivocitaSensore(nomeComposto) &&
            recuperatore.getUnita(nomeUnita) != null &&
            recuperatore.getStanza(nomeStanza, nomeUnita) != null &&
            !recuperatore.getStanza(nomeStanza, nomeUnita).contieneCategoriaSensore(nomeCategoria);
    }

    /**
     * Verifica che le informazioni fornite per la costruzione e l'aggiunta di
     * un Attuatore all'interno di una Stanza siano valide
     * @param nomeComposto Nome dell'attuatore
     * @param nomeCategoria Nome della categoria dell'attuatore
     * @param nomeStanza Nome della stanza a cui appartiene l'attuatore
     * @param nomeUnita Nome dell'unita' immobiliare a cui appartiene l'attuatore
     * @return true se le informazioni sono valide
     */
    public boolean checkValiditaAttuatore(String nomeComposto, String nomeCategoria, String nomeStanza, String nomeUnita) {
        return isNomeDispositivoValido(nomeComposto) &&
            checkUnivocitaAttuatore(nomeComposto) &&
            recuperatore.getUnita(nomeUnita) != null &&
            recuperatore.getStanza(nomeStanza, nomeUnita) != null &&
            !recuperatore.getStanza(nomeStanza, nomeUnita).contieneCategoriaAttuatore(nomeCategoria);
    }

    /**
     * Verifica che le informazioni fornite per la costruzione e l'aggiunta di
     * un Sensore all'interno di un Artefatto siano valide
     * @param nomeComposto Nome del sensore
     * @param nomeCategoria Nome della categoria del sensore
     * @param nomeArtefatto Nome dell'artefatto a cui appartiene il sensore
     * @param nomeStanza Nome della stanza a cui appartiene il sensore
     * @param nomeUnita Nome dell'unita' immobiliare a cui appartiene il sensore
     * @return true se le informazioni sono valide
     */
    public boolean checkValiditaSensore(String nomeComposto, String nomeCategoria, String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return isNomeDispositivoValido(nomeComposto) &&
            checkUnivocitaSensore(nomeComposto) &&
            recuperatore.getUnita(nomeUnita) != null &&
            recuperatore.getStanza(nomeStanza, nomeUnita) != null &&
            recuperatore.getArtefatto(nomeArtefatto, nomeStanza, nomeUnita) != null &&
            !recuperatore.getArtefatto(nomeArtefatto, nomeStanza, nomeUnita).contieneCategoriaSensore(nomeCategoria);
    }

    /**
     * Verifica che le informazioni fornite per la costruzione e l'aggiunta di
     * un Attuatore all'interno di un Artefatto siano valide
     * @param nomeComposto Nome del sensore
     * @param nomeCategoria Nome della categoria dell'attuatore
     * @param nomeArtefatto Nome dell'artefatto a cui appartiene l'attuatore
     * @param nomeStanza Nome della stanza a cui appartiene l'attuatore
     * @param nomeUnita Nome dell'unita' immobiliare a cui appartiene l'attuatore
     * @return true se le informazioni sono valide
     */
    public boolean checkValiditaAttuatore(String nomeComposto, String nomeCategoria, String nomeArtefatto, String nomeStanza, String nomeUnita) {
        return isNomeDispositivoValido(nomeComposto) &&
            checkUnivocitaAttuatore(nomeComposto) &&
            recuperatore.getUnita(nomeUnita) != null &&
            recuperatore.getStanza(nomeStanza, nomeUnita) != null &&
            recuperatore.getArtefatto(nomeArtefatto, nomeStanza, nomeUnita) != null &&
            !recuperatore.getArtefatto(nomeArtefatto, nomeStanza, nomeUnita).contieneCategoriaAttuatore(nomeCategoria);
    }

    /**
     * Metodo che verifica la validità dell'orario passato come double
     * L'ora deve stare dentro [0, 23] e i minuti devono stare dentro [0, 59]
     * @param orario da verificare
     * @return true se l'orario è valido. False altrimenti
     */
    public boolean checkValiditaOrario(double orario){
        String orarioStringa = String.valueOf(orario);
        int ora = Integer.parseInt(orarioStringa.split(Pattern.quote("."))[0]);
        int minuti = Integer.parseInt(orarioStringa.split(Pattern.quote("."))[1]);
        if(ora < 0 || ora > 23) return false;
        if(minuti < 0 || minuti > 59) return false;
        return true;
    }

    /**
     * Metodo che indica se una modalità operativa di una categoria di attuatori è parametrica o meno
     * @param attuatore la cui modalità operativa è da verificare
     * @param modalita da verificare
     * @return true se parametrica
     */
    public boolean isModalitaParametrica(String attuatore, String modalita) {
        return recuperatore.getAttuatore(attuatore)
            .getCategoria()
            .getModalita(modalita)
            .isParametrica(); //
    }

    /**
     * Metodo che indica se una informazione rilevabile di una categoria di sensori è numerica o meno
     * @param nsensoreDestro la cui informazione rivelabile è da verificare
     * @param info da verificare
     * @return true se numerica
     */
    public boolean isInfoNumerica(String nsensoreDestro, String info) {
        Sensore sensore = recuperatore.getSensore(nsensoreDestro);
        return (sensore.getValore(info) instanceof Number);
    }
}
