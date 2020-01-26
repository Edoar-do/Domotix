package domotix.model.bean;

import domotix.model.ElencoAttuatori;
import domotix.model.ElencoSensori;
import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.Dispositivo;
import domotix.model.bean.device.Sensore;
import domotix.model.bean.system.Stanza;
import domotix.model.util.SommarioDispositivi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnitaImmobiliare {
    public static final String NOME_STANZA_DEFAULT = "";
    private static final int POS_STANZA_DEFAULT = 0;

    private String nome;
    private List<Stanza> stanze;
    private SommarioDispositivi sensori;
    private SommarioDispositivi attuatori;

    public UnitaImmobiliare(String nome) {
        this.nome = nome;
        this.stanze = new ArrayList<>();
        this.stanze.add(new Stanza(NOME_STANZA_DEFAULT)); // stanza di default
        this.sensori = new SommarioDispositivi();
        this.attuatori = new SommarioDispositivi();

        this.getStanzaDefault().setUnitaOwner(this.getNome());
        this.getStanzaDefault().addOsservatoreListaAttuatori(ElencoAttuatori.getInstance());
        this.getStanzaDefault().addOsservatoreListaSensori(ElencoSensori.getInstance());
        this.getStanzaDefault().addOsservatoreListaAttuatori(attuatori);
        this.getStanzaDefault().addOsservatoreListaSensori(sensori);
    }

    public boolean addStanza(Stanza stanza) {
        for (Stanza s : this.stanze) {
            if (s.getNome().equals(stanza.getNome())) {
                return false;
            }
        }
        stanza.setUnitaOwner(this.getNome());
        stanza.addOsservatoreListaSensori(sensori);
        stanza.addOsservatoreListaAttuatori(attuatori);
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
            stanza.addOsservatoreListaSensori(sensori);
            stanza.addOsservatoreListaAttuatori(attuatori);
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

    public Sensore getSensore(String nome) {
        return (Sensore) sensori.getDispositivo(nome);
    }

    public Sensore[] getSensori() {
        Dispositivo[] arraySensori = sensori.getDispositivi();
        return Arrays.copyOf(arraySensori, arraySensori.length, Sensore[].class);
    }

    public Attuatore getAttuatore(String nome) {
        return (Attuatore) attuatori.getDispositivo(nome);
    }

    public Attuatore[] getAttuatori() {
        Dispositivo[] arrayAttuatori = attuatori.getDispositivi();
        return Arrays.copyOf(arrayAttuatori, arrayAttuatori.length, Attuatore[].class);
    }
}
