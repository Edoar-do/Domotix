package domotix.model.bean.system;


import domotix.model.bean.device.Dispositivo;
import domotix.model.util.ObserverList;

import java.util.HashMap;
import java.util.Map;

public class Stanza extends Sistema {
    private Map<String, Artefatto> artefatti;
    private String unitaOwner;

    public Stanza(String nome) {
        super(nome);
        this.unitaOwner = null;
        artefatti = new HashMap<>();
    }

    public void removeArtefatto(Artefatto artefatto) {
        removeArtefatto(artefatto.getNome());
    }

    public void removeArtefatto(String nome) {
        Artefatto a = artefatti.get(nome);
        if (a != null) {
            artefatti.remove(nome);
            a.distruggi();
        }
    }

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

    public Artefatto[] getArtefatti() {
        return artefatti.values().toArray(new Artefatto[0]);
    }

    public Artefatto getArtefatto(String nome) {
        return artefatti.get(nome);
    }


    public void setUnitaOwner(String unitaOwner) {
        this.unitaOwner = unitaOwner;
    }

    public String getUnitaOwner() {
        return unitaOwner;
    }

    @Override
    public void addOsservatoreListaSensori(ObserverList<Dispositivo> oss) {
        super.addOsservatoreListaSensori(oss);
        artefatti.forEach((s, artefatto) -> artefatto.addOsservatoreListaSensori(oss)); //per riportare anche agli artefatti gli osservatori aggiunti
    }

    @Override
    public void removeOsservatoreListaSensori(ObserverList<Dispositivo> oss) {
        super.removeOsservatoreListaSensori(oss);
        artefatti.forEach((s, artefatto) -> artefatto.removeOsservatoreListaSensori(oss)); //per riportare anche agli artefatti gli osservatori rimossi
    }

    @Override
    public void addOsservatoreListaAttuatori(ObserverList<Dispositivo> oss) {
        super.addOsservatoreListaAttuatori(oss);
        artefatti.forEach((s, artefatto) -> artefatto.addOsservatoreListaAttuatori(oss)); //per riportare anche agli artefatti gli osservatori aggiunti
    }

    @Override
    public void removeOsservatoreListaAttuatori(ObserverList<Dispositivo> oss) {
        super.removeOsservatoreListaAttuatori(oss);
        artefatti.forEach((s, artefatto) -> artefatto.removeOsservatoreListaAttuatori(oss)); //per riportare anche agli artefatti gli osservatori rimossi
    }
}
