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
import domotix.model.AccessoModel;
import domotix.model.ElencoUnitaImmobiliari;
import domotix.model.Model;
import domotix.view.menus.MenuAzioniConflitto;
import domotix.view.menus.MenuApertura;
import domotix.view.menus.MenuChiusura;
import domotix.view.menus.MenuLogin;

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
        PercorsiFile generatoreDati = new PercorsiFile(PercorsiFile.SORGENTE.DATI);
        PercorsiFile generatoreLibreria = new PercorsiFile(PercorsiFile.SORGENTE.LIBRERIA);
        PercorsiFile generatoreLibreriaImportata = new PercorsiFile(PercorsiFile.SORGENTE.LIBRERIA_IMPORTATA);

        //TODO manage exceptions
        generatoreDati.controllaStruttura();
        generatoreLibreria.controllaStruttura();
        generatoreLibreriaImportata.controllaStruttura();

        LetturaDatiSalvati letturaDati = new LetturaDatiLocali(generatoreDati);
        LetturaDatiSalvati letturaLibreria = new LetturaDatiLocali(generatoreLibreria);
        ScritturaDatiSalvati scritturaDati = new ScritturaDatiLocali(generatoreDati);
        RimozioneDatiSalvati rimozioneDati = new RimozioneDatiLocali(generatoreDati);

        ImportaDati importaDati = new ImportaDatiLocali(generatoreLibreria, letturaLibreria, generatoreLibreriaImportata);
        //Da vedere per la storicizzazione

        Model model = new AccessoModel();
        Recuperatore recuperatore = new Recuperatore(model);
        Verificatore verificatore = new Verificatore(recuperatore);
        Modificatore modificatore = new Modificatore(model, recuperatore, verificatore);
        Importatore importatoreLocale = new Importatore(modificatore, importaDatiLocali);
        Interpretatore interpretatore = new InterpretatoreConsole(modificatore);
        Rappresentatore rappresentatore = new RappresentatoreConsole(recuperatore);

        RinfrescaDati rinfrescaDati = new RinfrescaDatiLocali(letturaDati, recuperatore);
        TimerRinfrescoDati timerRinfrescoDati = new TimerRinfrescoDati(rinfrescaDati);
        TimerGestioneRegole timerGestioneRegole = new TimerGestioneRegole(recuperatore);
        TimerAzioniProgrammate timerAzioniProgrammate = new TimerAzioniProgrammate(recuperatore);

        AperturaProgramma apertura = new AperturaProgramma(letturaDati, modificatore);
        MenuApertura menuApertura = new MenuApertura(apertura);
        MenuAzioniConflitto menuAzioniConflitto = new MenuAzioniConflitto(recuperatore, modificatore);
        
        ChiusuraProgramma chiusura = new ChiusuraProgramma(scritturaDati, rimozioneDati, recuperatore);
        MenuChiusura menuChiusura = new MenuChiusura(chiusura);

        MenuLogin menuLogin = new MenuLogin(interpretatore, rappresentatore, verificatore);


        /* AVVIO DEL PROGRAMMMA */

        boolean esegui = menuApertura.avvia();

        //Controllo di integrita' dati caricati e avvio servizi
        if (esegui) {
            menuAzioniConflitto.avvia();

            //Controllo se non sono presenti unita immobiliari e in tal caso aggiungo l'unita base generata.
            if (!verificatore.controlloEsistenzaUnita())
                modificatore.aggiungiUnita(recuperatore.getUnitaBase());

            timerAzioniProgrammate.start(); //avvio timer gestione azioni programmate
            timerRinfrescoDati.start(); //avvio timer rinfresco dati
            timerGestioneRegole.start(); //avvio timer gestione regole
        }

        //Esecuzione routine di esecuzione
        while (esegui) {

            menuLogin.avvia();

            esegui = !chiusura.chiudi();
        }

        //Arresto servizi
        timerAzioniProgrammate.stop();
        timerRinfrescoDati.stop();
        timerGestioneRegole.stop();
    }

}
