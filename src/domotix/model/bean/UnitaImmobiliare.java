package domotix.model.bean;

import domotix.model.ElencoDispositivi;
import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.Dispositivo;
import domotix.model.bean.device.Sensore;
import domotix.model.bean.system.Stanza;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnitaImmobiliare {
    private String nome;
    private List<Stanza> stanze;
    private ElencoDispositivi sensori;
    private ElencoDispositivi attuatori;

    public UnitaImmobiliare(String nome) {
        this.nome = nome;
        this.stanze = new ArrayList<>();
        this.stanze.add(new Stanza("", this.nome)); // stanza di default
        this.sensori = new ElencoDispositivi();
        this.attuatori = new ElencoDispositivi();
    }

    public boolean addStanza(Stanza stanza) {
        for (Stanza s : this.stanze) {
            if (s.getNome().equals(stanza.getNome())) {
                return false;
            }
        }
        this.stanze.add(stanza);
        this.addDispositivi(stanza);
        return true;
    }

    public void removeStanza(Stanza stanza) {
        this.removeStanza(stanza.getNome());
    }

    public void removeStanza(String nome) {
        for (int i = 0; i < stanze.size(); i++) {
            if (stanze.get(i).getNome().equals(nome)) {
                removeDispositivi(stanze.get(i));
                stanze.remove(i);
                break;
            }
        }
    }

    public Stanza getStanzaDefault() {
        return stanze.get(0);
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

    private void addDispositivi(Stanza stanza) {
        for (Sensore s : stanza.getSensori()) {
            sensori.add(s, false);
        }
        for (Attuatore a : stanza.getAttuatori()) {
            attuatori.add(a, false);
        }
    }

    private void removeDispositivi(Stanza stanza) {
        for (Sensore s : stanza.getSensori()) {
            stanza.addSensore(s);
            if (sensori.getDispositivo(s.getNome()).getNumeroAssociazioni() == 0) {
                sensori.remove(s);
            }
        }
        for (Attuatore a : stanza.getAttuatori()) {
            stanza.removeAttuatore(a);
            if (attuatori.getDispositivo(a.getNome()).getNumeroAssociazioni() == 0) {
                attuatori.remove(a);
            }
        }
    }
}
