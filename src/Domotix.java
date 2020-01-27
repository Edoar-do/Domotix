import domotix.model.ElencoCategorieAttuatori;
import domotix.model.ElencoCategorieSensori;
import domotix.model.ElencoUnitaImmobiliari;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.model.io.LetturaDatiSalvati;
import domotix.model.io.ScritturaDatiSalvati;
import domotix.model.io.datilocali.LetturaDatiLocali;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import java.io.IOException;

public class Domotix {
    public static void main(String ...args) {
        CategoriaSensore categoriaSensore = new CategoriaSensore(
                "Termometro",
                "Questo e' un termometro e misura in Celsius",
                "Temperatura"
                );
        Sensore sensore = new Sensore("t1", categoriaSensore);
        Modalita modalita = new Modalita("avanti");
        CategoriaAttuatore categoriaAttuatore = new CategoriaAttuatore("motore", "Il motore e' un motore");
        categoriaAttuatore.addModalita(modalita);
        Attuatore attuatore = new Attuatore("m1", categoriaAttuatore, modalita);
        Artefatto artefatto = new Artefatto("cancello");
        artefatto.addSensore(sensore);
        artefatto.addAttuatore(attuatore);
        Stanza stanza = new Stanza("cucina");
        stanza.addSensore(sensore);
        stanza.addArtefatto(artefatto);
        stanza.addAttuatore(attuatore);
        Stanza stanza1 = new Stanza("cucina1");
        stanza1.addSensore(sensore);
        stanza1.addArtefatto(artefatto);
        stanza1.addAttuatore(attuatore);
        UnitaImmobiliare unitaImmobiliare = new UnitaImmobiliare("casa");
        unitaImmobiliare.addStanza(stanza);
        unitaImmobiliare.addStanza(stanza1);
        System.out.println(unitaImmobiliare);
    }
}
