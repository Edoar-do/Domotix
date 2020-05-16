package domotix.model.bean.device;

import domotix.model.visitor.Visitor;

import java.util.HashMap;
import java.util.Map;

/** @author Edoardo Coppola */
public class Sensore extends Dispositivo {
    private CategoriaSensore categoria;
    private Map<String, Object> valori;

    /**
     * Costruttore della classe
     * @param nome  stringa contenente il nome del sensore
     * @param categoria categoria da applicare al sensore
     */
    public Sensore(String nome, CategoriaSensore categoria){
        this.categoria = categoria;
        super.setNome(nome);
        this.valori = new HashMap<>();
        categoria.getInformazioniRilevabili().forEach(i -> valori.put(i.getNome(), i.isNumerica() ? 0 : "null")); //popolo la map con le info rilevabili della categoria
    }

    /**
     * Metodo, lasciato per retro-compatibilita', che recupera il valore rilevato dal sensore secondo le specifiche della versione 1.
     * Queste specifiche assegnavano a ciascun sensore un solo dato rilevabile di tipo intero e pertando il metodo ritorna il primo valore
     * se intero, altrimenti 0.
     * Si applica il tag di deprecato per sfavorirne l'uso.
     *
     * @return Il primo valore rilevato se intero, 0 altrimenti
     */
    @Deprecated
    public int getValore(){
        InfoRilevabile primaInfo = categoria.getInformazioneRilevabile(0);
        if (primaInfo.isNumerica()) {
            Object val = valori.get(primaInfo.getNome());
            if (val instanceof Integer)
                return ((Integer)val).intValue();
            else if (val instanceof Double)
                return ((Double)val).intValue();
            else if (val instanceof Float)
                return ((Float)val).intValue();
        }

        return 0;
    }

    /**
     * Metodo, lasciato per retro-compatibilita', che imposta il valore rilevato dal Sensore secondo le specifiche della versione 1.
     * Queste specifiche assegnavano a ciascun sensore un solo dato rilevabile di tipo intero e pertando il metodo imposta il valore
     * alla prima informazione rilevabile se intera, altrimenti non fa nulla.
     * Si applica il tag di deprecato per sfavorirne l'uso.
     *
     * @param nuovoValore Il nuovo valore rilevato
     */
    @Deprecated
    public void setValore(int nuovoValore){
        InfoRilevabile primaInfo = categoria.getInformazioneRilevabile(0);
        if (primaInfo.isNumerica()) {
            valori.put(primaInfo.getNome(), nuovoValore);
        }
    }

    /**
     * Ritorna tutti i valori del sensore con le relative associazioni ai nomi delle informazioni rilevabili della categoria.
     *
     * @return  HashMap popolata con chiavi stringhe per i nomi dell'informazione rilevabile e valori i rispettivi valori.
     */
    public Map<String, Object> getValori() {
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.putAll(valori);
        return ret;
    }

    /**
     * Recupera il valore associato all'informazione rilevabile identificata dal nome. Questa deve esistere
     * nella categoria applicata al sensore o si incorre in un'errore logico segnato da un'eccezione.
     *
     * @param nomeInfo  stringa contenente il nome dell'informazione rilevabile
     * @return  valore associato all'informazione rilevabile
     * @throws IllegalArgumentException eccezione lanciata nel caso si specifichi un'informazione rilevabile che non fa parte
     *  della categoria applicata al sensore
     */
    public Object getValore(String nomeInfo) throws IllegalArgumentException {
        if (!valori.containsKey(nomeInfo)) {
            throw new IllegalArgumentException("Nome informazione rilevabile non compatibile con la categoria sensore");
        }
        return valori.get(nomeInfo);
    }

    /**
     * Imposta un nuovo valore per l'informazione rilevabile identificata dal nome indicato.
     * Se passato un nome inesistente tra le informazioni rilevabili della categoria l'inserimento fallisce con un
     * valore di ritorno falso.
     * Nessun controllo sul dato viene effettuato.
     *
     * @param nomeInfo  stringa contenente nome dell'informazione rilevabile
     * @param nuovoValore   nuovo valore da impostare, il cui contenuto deve essere significativamente e di tipo corretto
     * @return  true: inserimento con successo; false: altrimenti
     */
    public boolean setValore(String nomeInfo, Object nuovoValore) {
        if (!valori.containsKey(nomeInfo)) {
            return false;
        }

        valori.put(nomeInfo, nuovoValore);
        return true;
    }

    /**
     * Metodo che recupera la CategoriaSensore del Sensore
     * @return La CategoriaSensore
     */
    public CategoriaSensore getCategoria() {
        return categoria;
    }

    @Override
    public Object fattiVisitare(Visitor v) {
        return v.visitaSensore(this);
    }
}
