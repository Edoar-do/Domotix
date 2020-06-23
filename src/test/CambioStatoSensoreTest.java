package test;

import domotix.controller.Modificatore;
import domotix.controller.Recuperatore;
import domotix.controller.Verificatore;
import domotix.model.AccessoModel;
import domotix.model.Model;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.CategoriaSensore;
import domotix.model.bean.device.Sensore;
import domotix.model.bean.system.Stanza;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class CambioStatoSensoreTest {

    /* Di seguito si applica una metodologia di testing black box atta a verificare il soddisfacimento di un caso d'uso:
       il cambimento di stato di un sensore

      Dalla documentazione si sa che quando un sensore è collegato ad una stanza o artefatto, il suo stato è True (ON). E' poi diritto del fruitore
      cambiarlo. Dalla documentazione emerge che se un sensore è ON può solo essere spento, così come se è OFF può solo essere acceso.
      Questo esclude i casi di test in cui un sensore ON viene posto nuovamente a ON e viceversa per OFF.

      Essendo il dominio dei valori di input un dominio booleano, vien da sé che ci saranno due classi di equivalenza: uno per input = true
      e l'altra per input = false, dove input è lo stato di partenza del sensore prima che venga avviata la procedura di cambiamento dello stato.

      Il codomio degli output è a sua volta booleano
     */

    private Model model;
    private Recuperatore recuperatore;
    private Verificatore verificatore;
    private Modificatore modificatore;

    @Before
    public void init(){
        model = new AccessoModel();
        recuperatore = new Recuperatore(model);
        verificatore = new Verificatore(recuperatore);
        modificatore = new Modificatore(model, recuperatore, verificatore);
    }

    @Test
    public void sensorStateShouldBeTrueAtCreation(){
        modificatore.aggiungiUnitaImmobiliare(new UnitaImmobiliare("villetta"));
        recuperatore.getUnita("villetta").addStanza(new Stanza("cucina"));
        modificatore.aggiungiCategoriaSensore(new CategoriaSensore("termometro", "caldo"));
        modificatore.aggiungiSensore("t1", "termometro", "cucina", "villetta");
        Stanza stanza = recuperatore.getStanza("cucina", "villetta");
        assertTrue(getDesiredSensor(stanza.getSensori(), "t1_termometro").getStato());
    }

    @Test
    public void sensorStateShouldBeFalseAfterHavingSwitchedItOff(){
        modificatore.aggiungiUnitaImmobiliare(new UnitaImmobiliare("villetta"));
        recuperatore.getUnita("villetta").addStanza(new Stanza("cucina"));
        modificatore.aggiungiCategoriaSensore(new CategoriaSensore("termometro", "caldo"));
        modificatore.aggiungiSensore("t1", "termometro", "cucina", "villetta");
        modificatore.cambiaStatoSensore("t1_termometro", "villetta");
        Sensore s = getDesiredSensor(recuperatore.getStanza("cucina", "villetta").getSensori(), "t1_termometro");
        assertFalse(s.getStato());
    }

    @Test
    public void sensorStateShouldBeTrueAfterHavingSwitchedItTwice(){
        modificatore.aggiungiUnitaImmobiliare(new UnitaImmobiliare("villetta"));
        recuperatore.getUnita("villetta").addStanza(new Stanza("cucina"));
        modificatore.aggiungiCategoriaSensore(new CategoriaSensore("termometro", "caldo"));
        modificatore.aggiungiSensore("t1", "termometro", "cucina", "villetta");
        modificatore.cambiaStatoSensore("t1_termometro", "villetta");
        modificatore.cambiaStatoSensore("t1_termometro", "villetta");
        Sensore s = getDesiredSensor(recuperatore.getStanza("cucina", "villetta").getSensori(), "t1_termometro");
        assertTrue(s.getStato());
    }

    public Sensore getDesiredSensor(Sensore[] sensores, String name){
        for(Sensore s : sensores)
            if(name.equals(s.getNome()))
                return s;
        return null;
    }
}
