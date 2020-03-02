package domotix.model.bean.system;

public class Artefatto extends Sistema {

    private String unitaOwner;

    public Artefatto(String nome) {
        super(nome);
        this.unitaOwner = null;
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
}
