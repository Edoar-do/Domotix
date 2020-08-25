package domotix.view.listeners.edit_listeners;

import domotix.controller.Interpretatore;
import domotix.controller.Rappresentatore;
import domotix.controller.Verificatore;
import domotix.view.ModifySignal;
import domotix.view.PannelloNord;
import domotix.view.Presenter;
import domotix.view.listeners.utils.AutoCompletion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Listener per la gestione di eventi legati alla creazione di regole, alla loro rimozione dall'unità corrente e al loro cambio di stato
 */
public class EditRegoleListener implements ActionListener, ModifySignal {
    private Interpretatore inter;
    private Rappresentatore rapp;
    private Verificatore ver;
    private PannelloNord pannelloNord;
    private Presenter presenter;

    public EditRegoleListener(Interpretatore inter, Rappresentatore rapp, Verificatore ver, PannelloNord pannelloNord, Presenter presenter) {
        this.inter = inter;
        this.rapp = rapp;
        this.ver = ver;
        this.pannelloNord = pannelloNord;
        this.presenter = presenter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        String unita = pannelloNord.getUnitaCorrente();
        if(actionCommand.equalsIgnoreCase("Rimuovi una regola dell'unità corrente")){ //TODO: RIMOZIONE REGOLA
            HashMap<String, String> coppieAntID = (HashMap<String, String>) rapp.getAntecedentiRegoleUnita(unita);
            String[] antecedenti = coppieAntID.keySet().toArray(new String[0]);

            if(antecedenti.length > 0) {
                JComboBox comboAntecedenti = new JComboBox(antecedenti);
                AutoCompletion.enable(comboAntecedenti);

                JOptionPane.showOptionDialog(null, comboAntecedenti, "Selezione regola da eliminare", -1, 3, null, null, null);
                String daEliminare = coppieAntID.get(antecedenti[comboAntecedenti.getSelectedIndex()]);
                if (inter.rimuoviRegola(unita, daEliminare)) {
                    JOptionPane.showOptionDialog(null, "Regola rimossa correttamente", "Successo Operazione", -1, 1, null, null, null);
                    segnalaModifica(rapp.getDescrizioneUnita(unita));
                }else
                    JOptionPane.showOptionDialog(null, "Regola non rimossa", "Fallimento Operazione", -1, 0, null, null, null);
            }else{
                JOptionPane.showOptionDialog(null, "Non sono presenti regole", "Nessuna regola da rimuovere", -1, 1, null, null, null);
                return;
            }
        }else{ //TODO: ATTIVA - DISATTIVA REGOLE
            HashMap<String, String> coppieAntIDregoleAttive_Disattive = (HashMap<String, String>) rapp.getAntecentiRegoleAttiveDisattive(unita);
            String[] antecedentiPiuStato = coppieAntIDregoleAttive_Disattive.keySet().toArray(new String[0]);
            if(antecedentiPiuStato.length > 0) {
                JComboBox comboAntecedenti = new JComboBox(antecedentiPiuStato);
                AutoCompletion.enable(comboAntecedenti);

                JOptionPane.showOptionDialog(null, comboAntecedenti, "Selezione regola a cui cambiare stato", -1, 3, null, null, null);
                String daCambiare = coppieAntIDregoleAttive_Disattive.get(antecedentiPiuStato[comboAntecedenti.getSelectedIndex()]);
                switch (inter.cambioStatoRegola(daCambiare, unita)) {
                    case -1:
                        JOptionPane.showOptionDialog(null, "Errore nel cambio di stato della regola", "Esito cambo stato regola", -1, 0, null, null, null);
                        break;
                    case 1:
                        JOptionPane.showOptionDialog(null, "Disattivazione della regola avvenuta correttamente", "Esito cambo stato regola", -1, 1, null, null, null);
                        segnalaModifica(rapp.getDescrizioneUnita(unita));
                        break;
                    case 2:
                        JOptionPane.showOptionDialog(null, "Attivazione della regola avvenuta correttamente", "Esito cambo stato regola", -1, 1, null, null, null);
                        segnalaModifica(rapp.getDescrizioneUnita(unita));
                        break;
                    case 3:
                        JOptionPane.showOptionDialog(null, "La regola da attivare è stata sospesa a causa di dispositivi \n in essa coinvolti che sono spenti", "Esito cambo stato regola", -1, 0, null, null, null);
                        segnalaModifica(rapp.getDescrizioneUnita(unita));
                        break;
                }
            }else{
                JOptionPane.showOptionDialog(null, "Non sono presenti regole", "Nessuna regola a cui cambiare stato", -1, 1, null, null, null);
                return;
            }
        }
    }

    @Override
    public void segnalaModifica(String descrizione) {
        presenter.show(descrizione);
    }
}
