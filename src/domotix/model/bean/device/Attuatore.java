package domotix.model.bean.device;

import domotix.controller.io.visitor.Visitable;
import domotix.controller.io.visitor.Visitor;

import java.util.concurrent.atomic.AtomicBoolean;

/**@author Edoardo Coppola */
public class Attuatore extends Dispositivo implements Visitable {
    private CategoriaAttuatore categoria;
    private Modalita modoOp;

    // riceve come nome dell'attuatore solo la prima parte a cui poi affianco la categoria
    public Attuatore(String nome, CategoriaAttuatore categoria, Modalita modoOpIniziale){
        super.setNome(nome);
        this.categoria = categoria;
        this.modoOp = modoOpIniziale;
    }

    /**
     * Metodo che recupera la CategoriaAttuatore dell'Attuatore.
     * @return La CategoriaAttuatore
     */
    public CategoriaAttuatore getCategoria(){
        return categoria;
    }

    /**
     * Metodo che recupera la ModalitaOperativa attuale dell'Attuatore.
     * @return La ModalitaOperativa
     */
    public Modalita getModoOp() {
        return modoOp;
    }

    /**
     * Metodo per impostare la ModalitaOperativa dell'Attuatore.
     * @param nuovaModalita La nuova ModalitaOperativa da impostare
     */
    public void setModoOp(Modalita nuovaModalita){
        modoOp = nuovaModalita;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj))  return true;

        if (obj instanceof Attuatore) {
            Attuatore attuatore = (Attuatore)obj;
            AtomicBoolean esito = new AtomicBoolean(true);

            esito.set(this.getNome().equals(attuatore.getNome()));

            return esito.get();
        }

        return false;
    }

    @Override
    public Object fattiVisitare(Visitor v) {
        return v.visitaAttuatore(this);
    }
}
