package domotix.model.bean.system;

import domotix.model.util.ElencoDispositivi;
import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.Dispositivo;
import domotix.model.bean.device.Sensore;

import java.util.Arrays;

public abstract class Sistema implements Osservabile, Azionabile {
    private String nome;
    private ElencoDispositivi sensori;
    private ElencoDispositivi attuatori;

    public Sistema(String nome) {
        this.nome = nome;
        this.sensori = new ElencoDispositivi();
        this.attuatori = new ElencoDispositivi();
    }

    public Sistema(String nome, ElencoDispositivi sensoriIniziali, ElencoDispositivi attuatoriIniziali) {
        this.nome = nome;
        this.attuatori = attuatoriIniziali;
        this.sensori = sensoriIniziali;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean addSensore(Sensore sensore) {
        return sensori.add(sensore.getCategoria().getNome(), sensore, true);
    }

    @Override
    public void removeSensore(Sensore sensore) {
        sensori.remove(sensore.getCategoria().getNome(), true);
    }

    @Override
    public void removeSensore(String categoriaSensore) {
        sensori.remove(categoriaSensore, true);
    }

    @Override
    public Sensore[] getSensori() {
        Dispositivo[] arraySensori = sensori.getDispositivi();
        return Arrays.copyOf(arraySensori, arraySensori.length, Sensore[].class);
    }

    @Override
    public boolean addAttuatore(Attuatore attuatore) {
        return attuatori.add(attuatore.getCategoria().getNome(), attuatore, true);
    }

    @Override
    public void removeAttuatore(Attuatore attuatore) {
        attuatori.remove(attuatore.getCategoria().getNome(), true);
    }

    @Override
    public void removeAttuatore(String categoriaAttuatore) {
        attuatori.remove(categoriaAttuatore, true);
    }

    @Override
    public Attuatore[] getAttuatori() {
        Dispositivo[] arrayAttuatori = attuatori.getDispositivi();
        return Arrays.copyOf(arrayAttuatori, arrayAttuatori.length, Attuatore[].class);
    }
}
