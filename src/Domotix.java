import domotix.controller.*;
import domotix.controller.io.ImportaDati;
import domotix.controller.io.LetturaDatiSalvati;
import domotix.controller.io.RimozioneDatiSalvati;
import domotix.controller.io.RinfrescaDati;
import domotix.controller.io.ScritturaDatiSalvati;
import domotix.controller.io.datilocali.ImportaDatiLocali;
import domotix.controller.io.datilocali.LetturaDatiLocali;
import domotix.controller.io.datilocali.PercorsiFile;
import domotix.controller.io.datilocali.RimozioneDatiLocali;
import domotix.controller.io.datilocali.RinfrescaDatiLocali;
import domotix.controller.io.datilocali.ScritturaDatiLocali;
import domotix.controller.io.xml.VisitorXML;
import domotix.model.AccessoModel;
import domotix.model.ElencoUnitaImmobiliari;
import domotix.model.Model;
import domotix.view.menus.MenuAzioniConflitto;
import domotix.view.menus.MenuApertura;
import domotix.view.menus.MenuChiusura;
import domotix.view.menus.MenuLogin;
import domotix.view.strategyException.ContextStrategy;
import domotix.view.strategyException.NotDirectoryExceptionRoutine;
import domotix.view.strategyException.ParserConfigurationExceptionRoutine;
import domotix.view.strategyException.TransformerConfigurationExceptionRoutine;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import java.nio.file.NotDirectoryException;

/**
 * Classe principale del programma.
 * Contiene il metodo main per l'avvio di un processo del programma.
 *
 * @author paolopasqua
 */
public class Domotix {

    /**
     * Metodo main di avvio del programma.
     * @param args  eventuali argomenti non utilizzati
     */
    public static void main(String ...args) {
        /* DICHIARAZIONE ELEMENTI BASE */
        Model model = new AccessoModel();
        Recuperatore recuperatore = new Recuperatore(model);
        Verificatore verificatore = new Verificatore(recuperatore);
        Modificatore modificatore = new Modificatore(model, recuperatore, verificatore);

        /* DICHIARAZIONE ELEMENTI GENERAZIONE PERCORSI LOCALI */
        PercorsiFile generatoreDati = new PercorsiFile(PercorsiFile.SORGENTE.DATI);
        PercorsiFile generatoreLibreria = new PercorsiFile(PercorsiFile.SORGENTE.LIBRERIA);
        PercorsiFile generatoreLibreriaImportata = new PercorsiFile(PercorsiFile.SORGENTE.LIBRERIA_IMPORTATA);

        ContextStrategy contextStrategy;

        try {
            /* CONTROLLO STRUTTURA DATI LOCALI */
            generatoreDati.controllaStruttura();
            generatoreLibreria.controllaStruttura();
            generatoreLibreriaImportata.controllaStruttura();

            /* DICHIARAZIONE ELEMENTI IO */
            VisitorXML visitatoriXML = null;
            visitatoriXML = new VisitorXML();
            LetturaDatiSalvati letturaDati = new LetturaDatiLocali(generatoreDati, recuperatore);
            LetturaDatiSalvati letturaLibreria = new LetturaDatiLocali(generatoreLibreria, recuperatore);
            ScritturaDatiSalvati scritturaDati = new ScritturaDatiLocali(generatoreDati, visitatoriXML);
            RimozioneDatiSalvati rimozioneDati = new RimozioneDatiLocali(generatoreDati);
            ImportaDati importaDati = new ImportaDatiLocali(generatoreLibreria, generatoreLibreriaImportata, letturaLibreria);

            /* DICHIARAZIONE ELEMENTI CONTROLLER */
            Importatore importatoreLocale = new Importatore(modificatore, importaDati, recuperatore);
            Interpretatore interpretatore = new Interpretatore(modificatore);
            Rappresentatore rappresentatore = new Rappresentatore(recuperatore);

            /* DICHIARAZIONE ELEMENTI TIMER */
            RinfrescaDati rinfrescaDati = new RinfrescaDatiLocali(letturaDati, recuperatore);
            TimerRinfrescoDati timerRinfrescoDati = new TimerRinfrescoDati(rinfrescaDati);
            TimerGestioneRegole timerGestioneRegole = new TimerGestioneRegole(recuperatore);
            TimerAzioniProgrammate timerAzioniProgrammate = new TimerAzioniProgrammate(recuperatore, modificatore);

            /* DICHIARAZIONE ELEMENTI FUNZIONAMENTO PROGRAMMA */
            AperturaProgramma apertura = new AperturaProgramma(letturaDati, modificatore);
            MenuApertura menuApertura = new MenuApertura(apertura);
            MenuAzioniConflitto menuAzioniConflitto = new MenuAzioniConflitto(rappresentatore, modificatore, recuperatore);

            ChiusuraProgramma chiusura = new ChiusuraProgramma(scritturaDati, rimozioneDati, recuperatore);
            MenuChiusura menuChiusura = new MenuChiusura(chiusura);

            MenuLogin menuLogin = new MenuLogin(interpretatore, verificatore, rappresentatore, importatoreLocale);


            /* AVVIO DEL PROGRAMMMA */

            boolean esegui = menuApertura.avvia();

            //Controllo di integrita' dati caricati e avvio servizi
            if (esegui) {
                menuAzioniConflitto.avvia();

                //Controllo se non sono presenti unita immobiliari e in tal caso aggiungo l'unita base generata.
                if (!verificatore.controlloEsistenzaUnita())
                    modificatore.aggiungiUnitaImmobiliare(recuperatore.getUnitaBase());

                timerAzioniProgrammate.start(); //avvio timer gestione azioni programmate
                timerRinfrescoDati.start(); //avvio timer rinfresco dati
                timerGestioneRegole.start(); //avvio timer gestione regole
            }

            //Esecuzione routine di esecuzione
            while (esegui) {

                menuLogin.avvia();

                esegui = !menuChiusura.avvia();
            }

            //Arresto servizi
            timerAzioniProgrammate.stop();
            timerRinfrescoDati.stop();
            timerGestioneRegole.stop();
        }catch(NotDirectoryException e){
            contextStrategy = new ContextStrategy(new NotDirectoryExceptionRoutine());
            contextStrategy.executeStrategy(e);
        }catch(ParserConfigurationException e){
            contextStrategy = new ContextStrategy(new ParserConfigurationExceptionRoutine());
            contextStrategy.executeStrategy(e);
        }catch(TransformerConfigurationException e){
            contextStrategy = new ContextStrategy(new TransformerConfigurationExceptionRoutine());
            contextStrategy.executeStrategy(e);
        }
    }
}
