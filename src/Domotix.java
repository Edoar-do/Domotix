import domotix.controller.*;
import domotix.model.ElencoUnitaImmobiliari;
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
        boolean esegui = OperazioniIniziali.getInstance().apri();

        if (esegui) {
            //Controllo se non sono presenti unita immobiliari e in tal caso aggiungo l'unita base generata.
            if (!OperazioniIniziali.getInstance().controlloEsistenzaUnita())
                ElencoUnitaImmobiliari.getInstance().add(OperazioniIniziali.getInstance().generaUnitaBase());

            TimerAzioniProgrammate.getInstance().start(); //avvio timer gestione azioni programmate
            TimerRinfrescoDati.getInstance().start(); //avvio timer rinfresco dati
            TimerGestioneRegole.getInstance().start(); //avvio timer gestione regole
        }

        while (esegui) {

            MenuLogin.avvia();

            esegui = !OperazioniFinali.getInstance().chiudi();
        }

        TimerAzioniProgrammate.getInstance().stop();
        TimerRinfrescoDati.getInstance().stop();;
        TimerGestioneRegole.getInstance().stop();
    }

}
