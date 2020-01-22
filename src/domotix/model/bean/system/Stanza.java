package domotix.model.bean.system;


import java.util.HashMap;
import java.util.Map;

public class Stanza extends Sistema {
    private Map<String, Artefatto> artefatti;

    public Stanza(String nome) {
        super(nome);
        artefatti = new HashMap<>();
    }

    public void removeArtefatto(Artefatto artefatto) {
        artefatti.remove(artefatto.getNome());
    }

    public void removeArtefatto(String nome) {
        artefatti.remove(nome);
    }

    public boolean addArtefatto(Artefatto artefatto) {
        if (artefatti.get(artefatto.getNome()) != null) {
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
}
