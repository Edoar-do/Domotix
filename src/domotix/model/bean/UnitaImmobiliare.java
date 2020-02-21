package domotix.model.bean;

import domotix.logicUtil.StringUtil;
import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.Sensore;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class UnitaImmobiliare {
    public static final String NOME_UNITA_DEFAULT = "casa";
    public static final String NOME_STANZA_DEFAULT = "esterno";
    private static final int POS_STANZA_DEFAULT = 0;

    private String nome;
    private List<Stanza> stanze;

    public UnitaImmobiliare(String nome) {
        this.nome = nome;
        this.stanze = new ArrayList<>();
        this.stanze.add(new Stanza(NOME_STANZA_DEFAULT)); // stanza di default

        this.getStanzaDefault().setUnitaOwner(this.getNome());
    }

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

    public void removeStanza(Stanza stanza) {
        this.removeStanza(stanza.getNome());
    }

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

    public boolean setStanzaDefault(Stanza stanza) {
        if (stanza.getNome().equals(NOME_STANZA_DEFAULT)) {
            getStanzaDefault().distruggi(); //distruggo la precedente

            stanza.setUnitaOwner(this.getNome());
            stanze.set(POS_STANZA_DEFAULT, stanza);
            return true;
        }
        return false;
    }

    public Stanza getStanzaDefault() {
        return stanze.get(POS_STANZA_DEFAULT);
    }

    public Stanza[] getStanze() {
        return stanze.toArray(new Stanza[0]);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

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
    
    public boolean isPresent(String nome){
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
