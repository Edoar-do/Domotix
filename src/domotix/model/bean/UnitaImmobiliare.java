package domotix.model.bean;

import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.Sensore;
import domotix.model.bean.system.Stanza;

import java.util.ArrayList;
import java.util.List;

public class UnitaImmobiliare {
    public static final String NOME_STANZA_DEFAULT = "";
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
        return null; //TODO
    }

    public Attuatore[] getAttuatori() {
        return null; //TODO
    }
}
