package domotix.model.bean.regole;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Regola {
    private String id; //UUID
    private boolean stato;
    private Antecedente antecedente;
    private List<Azione> conseguente;

    public Regola(boolean stato, Antecedente antecedente, List<Azione> conseguente) {
        this.id = UUID.randomUUID().toString();
        this.stato = stato;
        this.antecedente = antecedente;
        this.conseguente = conseguente;
    }

    public Regola() {
        this(false, null, new ArrayList<>());
    }

    public void addAzione(Azione azione) {
        this.conseguente.add(azione);
    }

    public void initAntecedente(Antecedente antecedente) {
        this.antecedente = antecedente;
    }

    public void initAntecedente(Condizione condizione) {
        this.initAntecedente(new Antecedente(condizione));
    }

    public void addCondizione(String operatore, Condizione condizione) {
        this.antecedente.addCondizione(operatore, condizione);
    }

    public void esegui() {
        if (!stato) return;

        // antecedente == null equivale a dire condizione sempre true
        if (antecedente == null || antecedente.valuta()) {
            conseguente.forEach(a -> a.esegui());
        }
    }

    public String getId() {
        return id;
    }

    public boolean getStato() {
        return stato;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }

    @Override
    public String toString() {
        String antstr = antecedente == null ? "true" : antecedente.toString();
        String consstr = conseguente.stream().map(a -> a.toString()).collect(Collectors.joining(", "));
        return "if " + antstr + " then " + consstr;
    }
}
