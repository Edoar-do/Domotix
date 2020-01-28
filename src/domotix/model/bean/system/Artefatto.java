package domotix.model.bean.system;

public class Artefatto extends Sistema {

    private String unitaOwner;

    public Artefatto(String nome) {
        super(nome);
        this.unitaOwner = null;
    }

    public void setUnitaOwner(String unitaOwner) {
        this.unitaOwner = unitaOwner;
    }

    public String getUnitaOwner() {
        return unitaOwner;
    }
}
