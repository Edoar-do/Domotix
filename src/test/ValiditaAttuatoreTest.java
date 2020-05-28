package test;

import domotix.controller.Recuperatore;
import domotix.controller.Verificatore;
import domotix.model.*;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.CategoriaAttuatore;
import domotix.model.bean.device.Modalita;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValiditaAttuatoreTest {

    /* La seguente tipologia di testing è white box in quanto tiene conto dell'implementazione del codice */

    /*  A   isNomeDispositivoValido(nomeComposto) &&
        B   checkUnivocitaAttuatore(nomeComposto) &&
        C   recuperatore.getUnita(nomeUnita) != null &&
        D   recuperatore.getStanza(nomeStanza, nomeUnita) != null &&
        E   recuperatore.getArtefatto(nomeArtefatto, nomeStanza, nomeUnita) != null &&
        F   !recuperatore.getArtefatto(nomeArtefatto, nomeStanza, nomeUnita).contieneCategoriaAttuatore(nomeCategoria);
     */

    /* soddisfacimento del criterio di Condition Coverage: tutte le espessione booleane atomiche devono essere valutate almeno una volta TRUE
       e almeno una volta FALSE per soddisfare il criterio.
       A tal fine si procede con una tabella di verità per individuare i casi di test
     */

    /*
            A   B   C   D   E   F   out
         1  f   -   -   -   -   -   f
         2  v   f   -   -   -   -   f
         3  v   v   f   -   -   -   f
         4  v   v   v   f   -   -   f
         5  v   v   v   v   f   -   f
         6  v   v   v   v   v   f   f
         7  v   v   v   v   v   v   v
     */

    /*
        per realizzare questa tabella della verità metto la prima condizione falsa e le altre manco passo i valori
        nei test successivi metto a vera la condizione precedente e ripeto la cosa fatta sopra
     */

    private Verificatore verificatore;
    private Recuperatore recuperatore;
    private Model model;

    @Before
    public void initializeVerificatore() {
        model = new AccessoModel();
        recuperatore = new Recuperatore(model);
        verificatore = new Verificatore(recuperatore);
    }

    @Test
    public void validitaAttuatoreFalsaPerNomeNonValido(){ //nome non valido
        String nomeComposto = "2m_motore";
        assertFalse(verificatore.checkValiditaAttuatore(nomeComposto,  null, null, null, null));
    }

    @Test
    public void validitaAttuatoreFalsaPerAttuatoreNonUnivoco(){//nome valido MA attuatore non univoco
        model.addUnita(new UnitaImmobiliare("Maniero"));
        recuperatore.getUnita("Maniero").addStanza(new Stanza("cucina"));
        recuperatore.getStanza("cucina", "Maniero").addArtefatto(new Artefatto("termosifone"));
        Attuatore att = new Attuatore("m2_motore", new CategoriaAttuatore("motore", "brum"), new Modalita("Avanti"));
        recuperatore.getArtefatto("termosifone", "cucina", "Maniero").addAttuatore(att);
        assertFalse(verificatore.checkValiditaAttuatore("m2_motore",  null, null, null, null));
    }

    @Test
    public void validitaAttuatoreFalsaPerUnitaNull(){ //nome valido, att univoco MA unità cercata insistente
        model.addUnita(new UnitaImmobiliare("Maniero"));
        recuperatore.getUnita("Maniero").addStanza(new Stanza("cucina"));
        recuperatore.getStanza("cucina", "Maniero").addArtefatto(new Artefatto("termosifone"));
        Attuatore att = new Attuatore("m2_motore", new CategoriaAttuatore("motore", "brum"), new Modalita("Avanti"));
        recuperatore.getArtefatto("termosifone", "cucina", "Maniero").addAttuatore(att);
        assertFalse(verificatore.checkValiditaAttuatore("NomeDiversoDam2_motore",  null, null, null, "casa"));
    }

    @Test
    public void validitaAttuatoreFalsaPerStanzaNull(){ //nome valido, att univoco, unità cercata esistente MA stanza cercata inesistente
        model.addUnita(new UnitaImmobiliare("Maniero"));
        recuperatore.getUnita("Maniero").addStanza(new Stanza("cucina"));
        recuperatore.getStanza("cucina", "Maniero").addArtefatto(new Artefatto("termosifone"));
        Attuatore att = new Attuatore("m2_motore", new CategoriaAttuatore("motore", "brum"), new Modalita("Avanti"));
        recuperatore.getArtefatto("termosifone", "cucina", "Maniero").addAttuatore(att);
        assertFalse(verificatore.checkValiditaAttuatore("NomeDiversoDam2_motore",  null, null, "salotto", "Maniero"));
    }

    @Test
    public void validitaAttuatoreFalsaPerArtefattoNull(){//nome valido, att univoco, unità cercata esistente, stanza cercata esistente MA artefatto cercato inesistente
        model.addUnita(new UnitaImmobiliare("Maniero"));
        recuperatore.getUnita("Maniero").addStanza(new Stanza("cucina"));
        recuperatore.getStanza("cucina", "Maniero").addArtefatto(new Artefatto("termosifone"));
        Attuatore att = new Attuatore("m2_motore", new CategoriaAttuatore("motore", "brum"), new Modalita("Avanti"));
        recuperatore.getArtefatto("termosifone", "cucina", "Maniero").addAttuatore(att);
        assertFalse(verificatore.checkValiditaAttuatore("NomeDiversoDam2_motore",  null, "Ventilatore", "cucina", "Maniero"));
    }

    @Test
    public void validitaAttuatoreFalsaPerArtefattoContenenteGiaCategoriaAttuatore(){ //nome valido, att univoco, unità cercata esistente, stanza cercata esistente, artF cercato esistente MA artf contiene già catAtt cercata
        model.addUnita(new UnitaImmobiliare("Maniero"));
        recuperatore.getUnita("Maniero").addStanza(new Stanza("cucina"));
        recuperatore.getStanza("cucina", "Maniero").addArtefatto(new Artefatto("termosifone"));
        Attuatore att = new Attuatore("m2_motore", new CategoriaAttuatore("motore", "brum"), new Modalita("Avanti"));
        recuperatore.getArtefatto("termosifone", "cucina", "Maniero").addAttuatore(att);
        assertFalse(verificatore.checkValiditaAttuatore("NomeDiversoDam2_motore",  "motore", "termosifone", "cucina", "Maniero"));
    }

    @Test
    public void validitaAttuatoreTrueGrazieATutteCondizioniSoddisfatte(){//nome valido, att univoco, unità cercata esistente, stanza cercata esistente, artF cercato esistente e cerco una catAtt non già presente su artf
        model.addUnita(new UnitaImmobiliare("Maniero"));
        recuperatore.getUnita("Maniero").addStanza(new Stanza("cucina"));
        recuperatore.getStanza("cucina", "Maniero").addArtefatto(new Artefatto("termosifone"));
        Attuatore att = new Attuatore("m2_motore", new CategoriaAttuatore("motore", "brum"), new Modalita("Avanti"));
        recuperatore.getArtefatto("termosifone", "cucina", "Maniero").addAttuatore(att);
        assertTrue(verificatore.checkValiditaAttuatore("NomeDiversoDam2_motore",  "NomeDiversoDamotore", "termosifone", "cucina", "Maniero"));
    }
}

