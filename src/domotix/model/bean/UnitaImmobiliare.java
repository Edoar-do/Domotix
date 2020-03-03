package domotix.model.bean;

import domotix.controller.util.StringUtil;
import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.Sensore;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che implementa l'entita' unita' immobiliare: un insieme di stanze al cui interno vi sono artefatti/sensori/attuatori.
 * Vi e' sempre una stanza chiamata di "default" per identificare l'esterno dell'unita' immobiliare.
 */
public class UnitaImmobiliare {
    public static final String NOME_UNITA_DEFAULT = "casa";
    public static final String NOME_STANZA_DEFAULT = "esterno";
    private static final int POS_STANZA_DEFAULT = 0;

    private String nome;
    private List<Stanza> stanze;

    /**
     * Costruttore di base.
     * @param nome  stringa contenente il nome identificativo dell'unita' immobiliare
     */
    public UnitaImmobiliare(String nome) {
        this.nome = nome;
        this.stanze = new ArrayList<>();
        this.stanze.add(new Stanza(NOME_STANZA_DEFAULT)); // stanza di default

        this.getStanzaDefault().setUnitaOwner(this.getNome());
    }

    /**
     * Aggiunge una stanza indicata come parametro, se questa non e' gia' contenuta (identificata dal nome).
     * @param stanza    istanza della stanza da aggiungere
     * @return  true: aggiunta andata a buon fine; false: altrimenti
     */
    public boolean addStanza(Stanza stanza) {
        for (Stanza s : this.stanze) {
            if (s.getNome().equals(stanza.getNome())) {
                return false;
            }
        }
        stanza.setUnitaOwner(this.getNome());
        this.stanze.add(stanza);
        return true;
    }

    /**
     * Rimuove la stanza indicata come parametro
     * @param stanza    istanza della stanza da rimuovere
     */
    public void removeStanza(Stanza stanza) {
        this.removeStanza(stanza.getNome());
    }

    /**
     * Rimuove la stanza indicata da una stringa identificativa
     * @param nome  stringa identificativa della stanza
     */
    public void removeStanza(String nome) {
        //non posso eliminare la stanza di default
        if (nome.equals(NOME_STANZA_DEFAULT))
            return;

        for (int i = 0; i < stanze.size(); i++) {
            if (stanze.get(i).getNome().equals(nome)) {
                stanze.get(i).distruggi();
                stanze.remove(i);
                break;
            }
        }
    }

    /**
     * Imposta una nuova stanza di default per l'unita' immobiliare.
     * La stanza da aggiungere deve avere il nome indicato dalla costanza NOME_STANZA_DEFAULT.
     *
     * @param stanza    istanza della stanza da impostare come stanza di default
     * @return  true: stanza impostata correttamente; false: altrimenti
     */
    public boolean setStanzaDefault(Stanza stanza) {
        if (stanza.getNome().equals(NOME_STANZA_DEFAULT)) {
            getStanzaDefault().distruggi(); //distruggo la precedente

            stanza.setUnitaOwner(this.getNome());
            stanze.set(POS_STANZA_DEFAULT, stanza);
            return true;
        }
        return false;
    }

    /**
     * Ritorna la stanza impostata come stanza di default
     * @return  istanza di stanza di default
     */
    public Stanza getStanzaDefault() {
        return stanze.get(POS_STANZA_DEFAULT);
    }

    /**
     * Ritorna l'elenco delle stanze contenute (stanza di default compresa)
     * @return  array di stanze
     */
    public Stanza[] getStanze() {
        return stanze.toArray(new Stanza[0]);
    }

    /**
     * Ritorna il nome dell'unita immobiliare
     * @return  stringa contenente il nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome dell'unita' immobliare
     * @param nome  stringa contenete il nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Ritorna l'elenco dei sensori contenuti in tutte le stanze/artefatti dell'unita' immobiliare.
     * @return  array di sensori
     */
    public Sensore[] getSensori() {
        List<Sensore> sensori = new ArrayList<>();
        for (Stanza stanza : stanze) {
            for (Sensore sensore : stanza.getSensori()) {
                sensori.add(sensore);
            }
            for (Artefatto artefatto : stanza.getArtefatti()) {
                for (Sensore sensoreArtefatto : artefatto.getSensori()) {
                    sensori.add(sensoreArtefatto);
                }
            }
        }
        return sensori.toArray(new Sensore[0]);
    }

    /**
     * Ritorna l'elenco di attuatori contenuti in tutte le stanze/artefatti dell'unita' immobiliare.
     * @return  array di attuatori
     */
    public Attuatore[] getAttuatori() {
        List<Attuatore> attuatori = new ArrayList<>();
        for (Stanza stanza : stanze) {
            for (Attuatore attuatore : stanza.getAttuatori()) {
                attuatori.add(attuatore);
            }
            for (Artefatto artefatto : stanza.getArtefatti()) {
                for (Attuatore attuatoreArtefatto : artefatto.getAttuatori()) {
                    attuatori.add(attuatoreArtefatto);
                }
            }
        }
        return attuatori.toArray(new Attuatore[0]);
    }

    /**
     * Verifica se e' contenuta nell'unita' immobiliare la stanza identificata dal nome passato.
     * @param nome  stringa contenente il nome della stanza
     * @return  true: contiente la stanza identificata dal nome; false: altrimenti
     */
    public boolean containsStanza(String nome){
        for (Stanza s: stanze){
            if(s.getNome().equals(nome))
                return true;
            }
            return false;

    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getNome() + ":\n");
        buffer.append("\tSTANZE:");
        for (Stanza stanza : getStanze()) {
            String stringaStanza = "\n" + stanza.toString();
            buffer.append(StringUtil.indent(stringaStanza, 2));
        }
        return buffer.toString();
    }

}
