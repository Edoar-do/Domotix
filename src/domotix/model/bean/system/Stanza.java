package domotix.model.bean.system;


import domotix.model.bean.device.Dispositivo;
import domotix.model.util.OsservatoreLista;

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

    @Override
    public void addOsservatoreListaSensori(OsservatoreLista<Dispositivo> oss) {
        super.addOsservatoreListaSensori(oss);
        artefatti.forEach((s, artefatto) -> artefatto.addOsservatoreListaSensori(oss)); //per riportare anche agli artefatti gli osservatori aggiunti
    }

    @Override
    public void removeOsservatoreListaSensori(OsservatoreLista<Dispositivo> oss) {
        super.removeOsservatoreListaSensori(oss);
        artefatti.forEach((s, artefatto) -> artefatto.removeOsservatoreListaSensori(oss)); //per riportare anche agli artefatti gli osservatori rimossi
    }

    @Override
    public void addOsservatoreListaAttuatori(OsservatoreLista<Dispositivo> oss) {
        super.addOsservatoreListaAttuatori(oss);
        artefatti.forEach((s, artefatto) -> artefatto.addOsservatoreListaAttuatori(oss)); //per riportare anche agli artefatti gli osservatori aggiunti
    }

    @Override
    public void removeOsservatoreListaAttuatori(OsservatoreLista<Dispositivo> oss) {
        super.removeOsservatoreListaAttuatori(oss);
        artefatti.forEach((s, artefatto) -> artefatto.removeOsservatoreListaAttuatori(oss)); //per riportare anche agli artefatti gli osservatori rimossi
    }
}
