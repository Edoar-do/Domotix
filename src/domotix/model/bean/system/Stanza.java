package domotix.model.bean.system;


import domotix.model.visitor.Visitable;
import domotix.model.visitor.Visitor;
import domotix.model.bean.device.Dispositivo;
import domotix.model.util.ObserverList;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Stanza extends Sistema implements Visitable {
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
     * @return true: aggiunto con successo
     */
    public boolean addArtefatto(Artefatto artefatto) {
        if (artefatti.get(artefatto.getNome()) == null) {
            artefatto.setUnitaOwner(this.getUnitaOwner());
            //collego all'artefatto i listener per le rimozioni sensori/artefatti
            for (ActionListener actionListener : getRimuoviSensoreListener()) {
                artefatto.addRimuoviSensoreListener(actionListener);
            }
            for (ActionListener actionListener : getRimuoviSensoreListener()) {
                artefatto.addRimuoviAttuatoreListener(actionListener);
            }
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
    public void addRimuoviSensoreListener(ActionListener lst) {
        super.addRimuoviSensoreListener(lst);
        if (artefatti != null) {
            artefatti.forEach((s, artefatto) -> artefatto.addRimuoviSensoreListener(lst)); //per riportare anche agli artefatti gli osservatori aggiunti
        }
    }

    @Override
    public void removeRimuoviSensoreListener(ActionListener lst) {
        super.removeRimuoviSensoreListener(lst);
        if (artefatti != null) {
            artefatti.forEach((s, artefatto) -> artefatto.removeRimuoviSensoreListener(lst)); //per riportare anche agli artefatti gli osservatori aggiunti
        }
    }

    @Override
    public void addRimuoviAttuatoreListener(ActionListener lst) {
        super.addRimuoviAttuatoreListener(lst);
        if (artefatti != null) {
            artefatti.forEach((s, artefatto) -> artefatto.addRimuoviAttuatoreListener(lst)); //per riportare anche agli artefatti gli osservatori aggiunti
        }
    }

    @Override
    public void removeRimuoviAttuatoreListener(ActionListener lst) {
        super.removeRimuoviAttuatoreListener(lst);
        if (artefatti != null) {
            artefatti.forEach((s, artefatto) -> artefatto.removeRimuoviAttuatoreListener(lst)); //per riportare anche agli artefatti gli osservatori aggiunti
        }
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
    public void distruggi() {
        for (Artefatto a : getArtefatti())
            a.distruggi();
        super.distruggi();
    }

    @Override
    public Object fattiVisitare(Visitor v) {
        return v.visitaStanza(this);
    }
}
