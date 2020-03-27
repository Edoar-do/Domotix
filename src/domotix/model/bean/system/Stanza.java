package domotix.model.bean.system;


import domotix.controller.util.StringUtil;
import domotix.model.bean.device.Dispositivo;
import domotix.model.util.ObserverList;

import java.util.HashMap;
import java.util.Map;

public class Stanza extends Sistema {
    private static final String NO_ARTEFATTI = "Non e' presente alcun artefatto";
    private Map<String, Artefatto> artefatti;
    private String unitaOwner;

    public Stanza(String nome) {
        super(nome);
        this.unitaOwner = null;
        artefatti = new HashMap<>();
    }

    /**
     * Metodo di rimozione di un Artefatto dalla Stanza.
     * @param artefatto L'Artefatto da rimuovere
     */
    public void removeArtefatto(Artefatto artefatto) {
        removeArtefatto(artefatto.getNome());
    }

    /**
     * Metodo di rimozione di un Artefatto dalla Stanza.
     * @param nome Nome dell'Artefatto da rimuovere
     */
    public void removeArtefatto(String nome) {
        Artefatto a = artefatti.get(nome);
        if (a != null) {
            artefatti.remove(nome);
            a.distruggi();
        }
    }

    /**
     * Metodo di aggiunta di un Artefatto alla Stanza.
     * @param artefatto L'Artefatto da aggiungere
     * @return true: inserito con successo
     */
    public boolean addArtefatto(Artefatto artefatto) {
        if (artefatti.get(artefatto.getNome()) == null) {
            artefatto.setUnitaOwner(this.getUnitaOwner());
            //artefatto.ereditaOsservatoriLista(this);
            artefatti.put(artefatto.getNome(), artefatto);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo che recupera una lista degli Artefatti presenti nella stanza.
     * @return La lista di Artefatti
     */
    public Artefatto[] getArtefatti() {
        return artefatti.values().toArray(new Artefatto[0]);
    }

    /**
     * Metodo che recupera l'istanza di un Artefatto nella Stanza dato il nome.
     * @param nome Il nome dell'Artefatto
     * @return L'istanza di Artefatto
     */
    public Artefatto getArtefatto(String nome) {
        return artefatti.get(nome);
    }


    /**
     * Metodo che imposta il riferimento all'UnitaImmobiliare di appartenenza.
     * @param unitaOwner Il nome dell'UnitaImmobiliare
     */
    public void setUnitaOwner(String unitaOwner) {
        this.unitaOwner = unitaOwner;
    }

    /**
     * Metodo che ritorna il nome dell'UnitaImmobiliare di appartenenza.
     * @return Il nome dell'UnitaImmobiliare
     */
    public String getUnitaOwner() {
        return unitaOwner;
    }

    @Override
    public void addOsservatoreListaSensori(ObserverList<Dispositivo> oss) {
        super.addOsservatoreListaSensori(oss);
        if (artefatti != null) {
            artefatti.forEach((s, artefatto) -> artefatto.addOsservatoreListaSensori(oss)); //per riportare anche agli artefatti gli osservatori aggiunti
        }
    }

    @Override
    public void removeOsservatoreListaSensori(ObserverList<Dispositivo> oss) {
        super.removeOsservatoreListaSensori(oss);
        if (artefatti != null) {
            artefatti.forEach((s, artefatto) -> artefatto.removeOsservatoreListaSensori(oss)); //per riportare anche agli artefatti gli osservatori rimossi
        }
    }

    @Override
    public void addOsservatoreListaAttuatori(ObserverList<Dispositivo> oss) {
        super.addOsservatoreListaAttuatori(oss);
        if (artefatti != null) {
            artefatti.forEach((s, artefatto) -> artefatto.addOsservatoreListaAttuatori(oss)); //per riportare anche agli artefatti gli osservatori aggiunti
        }
    }

    @Override
    public void removeOsservatoreListaAttuatori(ObserverList<Dispositivo> oss) {
        super.removeOsservatoreListaAttuatori(oss);
        if (artefatti != null) {
            artefatti.forEach((s, artefatto) -> artefatto.removeOsservatoreListaAttuatori(oss)); //per riportare anche agli artefatti gli osservatori rimossi
        }
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(super.toString() + "\n");
        buffer.append("\tARTEFATTI:");
        if (getArtefatti().length > 0) {
            for (Artefatto artefatto : getArtefatti()) {
                String stringaArtefatto = "\n" + artefatto.toString();
                buffer.append(StringUtil.indent(stringaArtefatto, 2));
            }
        } else {
            buffer.append(StringUtil.indent("\n" + NO_ARTEFATTI, 2));
        }
        return buffer.toString();
    }
}
