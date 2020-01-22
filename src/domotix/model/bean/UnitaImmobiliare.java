package domotix.model.bean;

import domotix.model.ElencoDispositivi;
import domotix.model.bean.system.Stanza;

import java.util.ArrayList;
import java.util.List;

public class UnitaImmobiliare {
    private String nome;
    private List<Stanza> stanze;
    private ElencoDispositivi sensori;
    private ElencoDispositivi attuatori;

    public UnitaImmobiliare(String nome) {
        this.nome = nome;
        this.stanze = new ArrayList<>();
        this.stanze.add(new Stanza("")); // stanza di default
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
        return true;
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
}
