package domotix.model.bean.regole;

import domotix.model.ElencoAzioniProgrammate;
import domotix.model.visitor.Visitable;
import domotix.model.visitor.Visitor;
import domotix.model.bean.device.Sensore;
import domotix.model.bean.device.Attuatore;

import java.util.UUID;

import static domotix.model.bean.regole.StatoRegola.ATTIVA;
import static domotix.model.bean.regole.StatoRegola.DISATTIVA;

/**
 * Classe che rappresenta una delle regole periodicamente eseguite.
 * @author andrea
 */
public class Regola implements Visitable {
    private String id; //UUID
    private StatoRegola stato;
    private Antecedente antecedente;
    private Conseguente conseguente;
    private boolean eseguita; //booleano utilizzato come rilevatore di fronte per l'esecuzione del conseguente
    private ElencoAzioniProgrammate azioni;

    public Regola(String id, StatoRegola stato, Antecedente antecedente, Conseguente conseguente) {
        this.id = id;
        this.stato = stato;
        this.antecedente = antecedente;
        this.conseguente = conseguente;
        this.eseguita = false;
    }

    public Regola(StatoRegola stato, Antecedente antecedente, Conseguente conseguente) {
        this(UUID.randomUUID().toString(), stato, antecedente, conseguente);
    }

    public Regola() {
        this(DISATTIVA, null, new Conseguente());
    }

    public boolean contieneSensore(String nome) {
        if(antecedente != null)
            return antecedente.contieneSensore(nome);
        return false;
    }

    public boolean contieneAttuatore(String nome) {
        return conseguente.contieneAttuatore(nome);
    }

    /**
     * Metodo di aggiunta di un'azione al conseguente
     * @param azione Azione da aggiungere
     */
    public void addAzione(Azione azione) {
        this.conseguente.addAzione(azione);
    }

    /**
     * Metodo di aggiunta di una condizione all'antecedente
     * @param condizione La condizione da aggiungere
     */
    public void addCondizone(Condizione condizione) {
        if (antecedente == null) {
            antecedente = new Antecedente(condizione);
        } else {
            antecedente.addCondizione(condizione);
        }
    }

    /**
     * Metodo di aggiunta di un operatore pendente tra condizioni dell'antecedente
     * @param op Operatore logico di collegamento tra condizioni
     */
    public void addOperatore(String op) {
        if (antecedente == null) {
            throw new IllegalArgumentException("Non si possono aggiungere operazioni quando non sono state inserite condizioni");
        } else {
            antecedente.addOperatore(op);
        }
    }

    /**
     * Metodo di aggiunta di una condizione all'antecedente.
     * L'operatore logico e' appeso all'antecedente che e' in coda prima della chiamata del metodo.
     * @param operatore Operatore logico di collegamento tra condizioni
     * @param condizione La condizione da aggiungere
     */
    public void addCondizione(String operatore, Condizione condizione) {
        this.antecedente.addCondizione(operatore, condizione);
    }

    /**
     * Metodo che viene eseguito se la regola e' attiva.
     * Attenzione: viene posto un rilevatore di fronte sull'antecedente, in modo che questo
     * Viene valutato l'antecedente. Se risulta true, viene eseguito il conseguente.
     */
    public void esegui() {
        if (stato != ATTIVA) return;

        // antecedente == null equivale a dire condizione sempre true
        if (antecedente == null || antecedente.valuta()) {
            if (!eseguita) { //controllo che esegua solo se al fronte di attivazione della condizione
                conseguente.esegui();
                eseguita = true; //inibisce eventuali ripetizioni per la stessa volta in cui soddisfatto l'antecedente
            }
        }
        else
            eseguita = false; //reset di fronte
    }

    /**
     * Metodo che restituisce l'ID della regola.
     * @return L'ID
     */
    public String getId() {
        return id;
    }

    /**
     * Metodo che restituisce lo stato.
     * @return True se attivo, false altrimenti
     */
    public StatoRegola getStato() {
        return stato;
    }

    /**
     * Metodo per impostare il nuovo stato
     * @param stato Nuovo stato
     */
    public void setStato(StatoRegola stato) {
        this.stato = stato;
    }

    /**
     * Metodo che restituisce l'antecedente
     * @return L'antecedente
     */
    public Antecedente getAntecedente() {
        return this.antecedente;
    }

    /**
     * Metodo che restituisce il conseguente
     * @return Il conseguente
     */
    public Conseguente getConseguente() {
        return conseguente;
    }

    public Sensore[] getSensori(){
        return antecedente.getSensori();
    }

    public Attuatore[] getAttuatori(){
        return conseguente.getAttuatori();
    }

    public boolean isAttivabile(){
        //se anche solo un sensore è spento allora non è riattivabile
        if(this.antecedente != null) {
            for (Sensore s : this.getSensori())
                if (s.getStato() == false) //sensore spento
                    return false;
        }
        //se anche solo un attuatore è spento allora non è riattivabile
        for (Attuatore a : this.getAttuatori())
            if(a.getStato() == false) //attuatore spento
                return false;
        //non ci sono né sensori né attuatori spenti -> è riattivabile
        return true;
    }

    @Override
    public Object fattiVisitare(Visitor v) {
        return v.visitaRegola(this);
    }

    public void addElencoAzioni(ElencoAzioniProgrammate azioni) {
        this.azioni = azioni;
        this.conseguente.addElencoAzioni(this.azioni);
    }
}
