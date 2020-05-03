package domotix.model.bean.device;

import domotix.controller.io.visitor.Visitable;
import domotix.controller.io.visitor.Visitor;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe per la rappresentazione di un parametro di una Modalita' per le CategoriaAttuatore.
 *
 * @author paolopasqua
 * @see Modalita
 * @see CategoriaAttuatore
 */
public class Parametro implements Visitable {

    private String nome;
    private double valore;

    /**
     * Costruttore della classe
     * @param nome  stringa contenente il nome univoco a livello di classe
     * @param valore    valore numerico del parametro
     */
    public Parametro(String nome, double valore) {
        this.nome = nome;
        this.valore = valore;
    }

    /**
     * Recupera il nome del parametro
     * @return  stringa contenente il nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Recupera il valore del parametro
     * @return  numero decimale come valore del parametro
     */
    public double getValore() {
        return valore;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj))  return true;

        if (obj instanceof Parametro) {
            Parametro parametro = (Parametro)obj;
            AtomicBoolean esito = new AtomicBoolean(true);

            esito.set(this.nome.equals(parametro.getNome()));
            esito.set(esito.get() && this.valore == parametro.getValore());

            return esito.get();
        }

        return false;
    }

    @Override
    public Object fattiVisitare(Visitor v) {
        return v.visitaParametro(this);
    }
}
