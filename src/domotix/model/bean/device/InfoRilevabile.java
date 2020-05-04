package domotix.model.bean.device;

import domotix.model.visitor.Visitable;
import domotix.model.visitor.Visitor;

/**
 * Classe per la rappresentazione di una informazione rilevabile da un Sensore di una CategoriaSensore.
 *
 * @author paolopasqua
 * @see Sensore
 * @see CategoriaSensore
 */
public class InfoRilevabile implements Visitable {

    private String nome;
    private boolean numerica;

    /**
     * Costruttore della classe
     *
     * @param nome  stringa contenente il nome univo a livello di CategoriaSensore
     * @param numerica  booleano per esprimere se l'informazione e' numerica o meno
     */
    public InfoRilevabile(String nome, boolean numerica) {
        this.nome = nome;
        this.numerica = numerica;
    }

    /**
     * Recupera il nome dell'informazione rilevabile
     * @return  stringa contenente il nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Ritorna se l'informazione e' numerica o meno.
     * @return  true: informazione numerica; false: altrimenti.
     */
    public boolean isNumerica() {
        return numerica;
    }

    @Override
    public Object fattiVisitare(Visitor v) {
        return v.visitaInfoRilevabile(this);
    }
}
