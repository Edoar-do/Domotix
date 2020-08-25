package domotix.view.listeners.edit_listeners;

import domotix.controller.Interpretatore;
import domotix.controller.Rappresentatore;
import domotix.view.ModifySignal;
import domotix.view.PannelloNord;
import domotix.view.Presenter;
import domotix.view.listeners.utils.AutoCompletion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener per la gestione degli eventi legati al cambio di stato di sensori e attuatori all'interno dell'unità corrente
 */
public class EditCambioStatoDispositiviListener implements ActionListener, ModifySignal {
    private Interpretatore inter;
    private Rappresentatore rapp;
    private PannelloNord pannelloNord;
    private Presenter presenter;

    public EditCambioStatoDispositiviListener(Interpretatore inter, Rappresentatore rapp, PannelloNord pannelloNord, Presenter presenter) {
        this.inter = inter;
        this.rapp = rapp;
        this.pannelloNord = pannelloNord;
        this.presenter = presenter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if(actionCommand.equalsIgnoreCase("cambio stato sensore")){
            String[] nomiSensori = rapp.getNomiSensori(pannelloNord.getUnitaCorrente());
            if(nomiSensori.length > 0) {
                JComboBox comboNomi = new JComboBox(nomiSensori);
                AutoCompletion.enable(comboNomi);

                Object[] oggetti = new Object[]{"Scegli un sensore a cui cambiare stato: ", comboNomi};

                int ok = JOptionPane.showOptionDialog(null, oggetti, "Cambio stato sensore", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

                if (ok == 0) {
                    if (inter.cambiaStatoSensore(nomiSensori[comboNomi.getSelectedIndex()], pannelloNord.getUnitaCorrente())) {
                        JOptionPane.showConfirmDialog(null, "Cambio stato sensore effettuato con successo", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                        segnalaModifica(rapp.getDescrizioneUnita(pannelloNord.getUnitaCorrente()));
                    }else
                        JOptionPane.showConfirmDialog(null, "Cambio stato sensore fallito", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showOptionDialog(null, "Non sono presenti sensori a cui cambiare stato", "Assenza sensori a cui cambiare stato", -1, 1, null, null, null);
                return;
            }
        }else{ //cambio stato attuatore
            String[] nomiAttuatori = rapp.getNomiAttuatori(pannelloNord.getUnitaCorrente());
            if(nomiAttuatori.length > 0) {
                JComboBox comboNomi = new JComboBox(nomiAttuatori);
                AutoCompletion.enable(comboNomi);

                Object[] oggetti = new Object[]{"Scegli un attuatore a cui cambiare stato: ", comboNomi};

                int ok = JOptionPane.showOptionDialog(null, oggetti, "Cambio stato attuatore", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

                if (ok == 0) {
                    if (inter.cambiaStatoAttuatore(nomiAttuatori[comboNomi.getSelectedIndex()], pannelloNord.getUnitaCorrente())) {
                        JOptionPane.showConfirmDialog(null, "Cambio stato attuatore effettuato con successo", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                        segnalaModifica(rapp.getDescrizioneUnita(pannelloNord.getUnitaCorrente()));
                    }else
                        JOptionPane.showConfirmDialog(null, "Cambio stato attuatore fallito", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showOptionDialog(null, "Non sono presenti attuatori a cui cambiare stato", "Assenza attuatori a cui cambiare stato", -1, 1, null, null, null);
                return;
            }
        }

    }

    @Override
    public void segnalaModifica(String descrizione) {
        presenter.show(descrizione);
    }
}
