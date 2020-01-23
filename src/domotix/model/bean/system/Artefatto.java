package domotix.model.bean.system;

public class Artefatto extends Sistema {

    private String unitaOwner;

    public Artefatto(String nome, String unitaOwner) {
        super(nome);
        this.unitaOwner = unitaOwner;
    }

    public String getUnitaOwner() {
        return unitaOwner;
    }
}
