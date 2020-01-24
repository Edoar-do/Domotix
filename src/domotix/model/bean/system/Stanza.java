package domotix.model.bean.system;


import java.util.HashMap;
import java.util.Map;

public class Stanza extends Sistema {
    private Map<String, Artefatto> artefatti;
    private String unitaOwner;

    public Stanza(String nome, String unitaOwner) {
        super(nome);
        this.unitaOwner = unitaOwner;
        artefatti = new HashMap<>();
    }

    public void removeArtefatto(Artefatto artefatto) {
        removeArtefatto(artefatto.getNome());
    }

    public void removeArtefatto(String nome) {
        Artefatto a = artefatti.get(nome);
        if (a != null) {
            artefatti.remove(nome);
            a.distruggi(); //TODO: decidere se fare questa cosa drastica o meno in rimozione di una stanza
        }
    }

    public boolean addArtefatto(Artefatto artefatto) {
        if (artefatti.get(artefatto.getNome()) == null) {
            artefatto.ereditaOsservatoriLista(this);
            artefatti.put(artefatto.getNome(), artefatto);
            return true;
        } else {
            return false;
        }
    }

    public Artefatto[] getArtefatti() {
        return artefatti.values().toArray(new Artefatto[0]);
    }

    public Artefatto getArtefatto(String nome) {
        return artefatti.get(nome);
    }

    public String getUnitaOwner() {
        return unitaOwner;
    }
}
