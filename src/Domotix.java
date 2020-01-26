import domotix.model.ElencoCategorieAttuatori;
import domotix.model.ElencoCategorieSensori;
import domotix.model.ElencoUnitaImmobiliari;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.model.io.AccessoDatiSalvati;
import domotix.model.io.datilocali.DatiLocali;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class Domotix {
    public static void main(String ...args) {
        boolean esegui = true; //true -> test scrittura; false -> test lettura

        if (esegui) {
            CategoriaSensore cs = new CategoriaSensore("termometro", "categoria per termoemtri", "temperatura");
            ElencoCategorieSensori.getInstance().add(cs);

            CategoriaAttuatore ca = new CategoriaAttuatore("servo", "categoria per i servo motori");
            ca.addModalita(new Modalita("Apertura"));
            ca.addModalita(new Modalita("Chiusura"));
            ElencoCategorieAttuatori.getInstance().add(ca);

            ca = new CategoriaAttuatore("rele", "categoria per un rele azionatore");
            ca.addModalita(new Modalita("normalmente aperto"));
            ca.addModalita(new Modalita("normalmente chiuso"));
            ElencoCategorieAttuatori.getInstance().add(ca);

            UnitaImmobiliare ui = new UnitaImmobiliare("casa");
            ui.addStanza(new Stanza("cucina"));
            ui.addStanza(new Stanza("salotto"));
            ui.getStanze()[1].addArtefatto(new Artefatto("finestra"));
            ca = ElencoCategorieAttuatori.getInstance().getCategoria("servo");
            Modalita m = ca.getModalita(0);
            ui.getStanze()[1].getArtefatti()[0].addAttuatore(new Attuatore("servoFinestra", ca, m));
            ui.getStanze()[2].addArtefatto(new Artefatto("lampada"));
            ca = ElencoCategorieAttuatori.getInstance().getCategoria("rele");
            m = ca.getModalita(0);
            ui.getStanze()[2].getArtefatti()[0].addAttuatore(new Attuatore("releLampada", ca, m));
            ui.getStanze()[1].addSensore(new Sensore("termometro", ElencoCategorieSensori.getInstance().getCategoria("termometro")));
            ui.getStanze()[2].addSensore(ui.getStanze()[1].getSensori()[0]);

            ui.getStanzaDefault().addSensore(new Sensore("esterno", ElencoCategorieSensori.getInstance().getCategoria("termometro")));
            ui.getStanzaDefault().addArtefatto(new Artefatto("cancello"));
            ca = ElencoCategorieAttuatori.getInstance().getCategoria("servo");
            m = ca.getModalita(0);
            ui.getStanzaDefault().getArtefatti()[0].addAttuatore(new Attuatore("servoCancello", ca, m));

            ElencoCategorieSensori.getInstance().getCategorie().forEach(categoriaSensore -> {
                try {
                    AccessoDatiSalvati.getInstance().salva(categoriaSensore);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            ElencoCategorieAttuatori.getInstance().getCategorie().forEach(categoriaAttuatore -> {
                try {
                    AccessoDatiSalvati.getInstance().salva(categoriaAttuatore);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            try {
                AccessoDatiSalvati.getInstance().salva(ui);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                DatiLocali.getInstance().leggiCategorieSensori().forEach(categoriaSensore -> ElencoCategorieSensori.getInstance().add(categoriaSensore));
                DatiLocali.getInstance().leggiCategorieAttuatori().forEach(categoriaAttuatore -> ElencoCategorieAttuatori.getInstance().add(categoriaAttuatore));
                DatiLocali.getInstance().leggiUnitaImmobiliare().forEach(unitaImmobiliare -> ElencoUnitaImmobiliari.getInstance().add(unitaImmobiliare));

                System.out.println("DONE");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
