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
 * Listener per la gestione degli eventi legati alla rimozione dell'unità corrente, all'aggiunta e alla rimozione di stanze alla/dalla stessa
 */
public class EditUnitaListener implements ActionListener, ModifySignal {

    private Interpretatore inter;
    private Rappresentatore rapp;
    private PannelloNord pannelloNord;
    private Presenter presenter;

    public EditUnitaListener(Interpretatore inter, Rappresentatore rapp, PannelloNord pannelloNord, Presenter presenter) {
        this.inter = inter;
        this.rapp = rapp;
        this.pannelloNord = pannelloNord;
        this.presenter = presenter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        String unita = pannelloNord.getUnitaCorrente();
        if(actionCommand.equalsIgnoreCase("Rimuovi unità immobiliare corrente")){
            if(inter.rimuoviUnitaImmobiliare(unita)) {
                JOptionPane.showConfirmDialog(null, "Unità rimossa correttamente", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                pannelloNord.pulisciUnitaCorrente();
            }else
                JOptionPane.showConfirmDialog(null, "Unità non rimossa", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }else if(actionCommand.equalsIgnoreCase("aggiungi una stanza all'unità")){
            JTextField campoNomeStanza = new JTextField(20);
            campoNomeStanza.setToolTipText("Nome della nuova stanza da aggiungere");
            int ok = JOptionPane.showOptionDialog(null, new Object[]{"Nome nuova stanza: ", campoNomeStanza}, "Inserimento Nuova Stanza", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if(ok == 0){
                if(inter.aggiungiStanza(campoNomeStanza.getText(), unita)) {
                    JOptionPane.showConfirmDialog(null, "Stanza aggiunta con successo", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    segnalaModifica(rapp.getDescrizioneUnita(pannelloNord.getUnitaCorrente()));
                } else
                    JOptionPane.showConfirmDialog(null, "Stanza non aggiunta", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        }else{ //rimozione stanze
            String[] nomiStanze = rapp.getNomiStanze(unita, false);
            JComboBox comboNomiStanze = new JComboBox(nomiStanze);
            AutoCompletion.enable(comboNomiStanze);
            Object[] oggetti = new Object[]{"Seleziona una stanza da rimuovere: ", comboNomiStanze};
            int ok = JOptionPane.showOptionDialog(null, oggetti, "Rimozione stanza da unità immobiliare", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if(ok == 0){
                if(inter.rimuoviStanza(nomiStanze[comboNomiStanze.getSelectedIndex()], unita)) {
                    JOptionPane.showConfirmDialog(null, "Stanza rimossa con successo", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    segnalaModifica(rapp.getDescrizioneUnita(pannelloNord.getUnitaCorrente()));
                }else
                    JOptionPane.showConfirmDialog(null, "Stanza non rimossa", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void segnalaModifica(String descrizione) {
        presenter.show(descrizione);
    }
}
