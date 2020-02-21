import domotix.controller.OperazioniFinali;
import domotix.controller.OperazioniIniziali;
import domotix.model.ElencoUnitaImmobiliari;
import domotix.view.menus.MenuLogin;

public class Domotix {

    public static void main(String ...args) {
        boolean esegui = OperazioniIniziali.getInstance().apri();

        //Controllo se non sono presenti unita immobiliari e in tal caso aggiungo l'unita base generata.
        if (!OperazioniIniziali.getInstance().controlloEsistenzaUnita())
            ElencoUnitaImmobiliari.getInstance().add(OperazioniIniziali.getInstance().generaUnitaBase());

        while (esegui) {

            MenuLogin.avvia();

            esegui = !OperazioniFinali.getInstance().chiudi();
        }
    }

}
