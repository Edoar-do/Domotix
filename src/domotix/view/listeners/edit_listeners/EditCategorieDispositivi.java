package domotix.view.listeners.edit_listeners;

import domotix.controller.Interpretatore;
import domotix.controller.Rappresentatore;
import domotix.view.passiveView.PannelloNord;
import domotix.view.listeners.utils.AutoCompletion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener per la gestione degli eventi legati alla rimozione di categorie di sensori e di attuatori
 */
public class EditCategorieDispositivi implements ActionListener {

    private Interpretatore inter;
    private Rappresentatore rapp;
    private PannelloNord pannelloNord;

    public EditCategorieDispositivi(Interpretatore inter, Rappresentatore rapp, PannelloNord pannelloNord) {
        this.inter = inter;
        this.rapp = rapp;
        this.pannelloNord = pannelloNord;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        String[] nomiCategorie;
        JComboBox comboCategorie;
        if(actionCommand.equalsIgnoreCase("Rimuovi Categoria Sensori")) {
            nomiCategorie = rapp.getNomiCategorieSensori();
            if(nomiCategorie.length > 0) {
                comboCategorie = new JComboBox(nomiCategorie);
                AutoCompletion.enable(comboCategorie);

                Object[] oggetti = new Object[]{"Seleziona la categoria di sensori da rimuovere:  ", comboCategorie};

                int ok = JOptionPane.showOptionDialog(null, oggetti, "Rimozione Categoria Sensori", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                if (ok == 0) {
                    if (inter.rimuoviCategoriaSensore(nomiCategorie[comboCategorie.getSelectedIndex()]))
                        JOptionPane.showConfirmDialog(null, "Categoria Sensori rimoossa con successo", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showConfirmDialog(null, "Categoria Sensori non rimossa", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showOptionDialog(null, "Non sono presenti categorie di sensori", "Assenza categorie di sensori da rimuovere", -1, 1, null, null, null);
                return;
            }
        }else{ //rimozione categoria attuatori
            nomiCategorie = rapp.getNomiCategorieAttuatori();
            if(nomiCategorie.length > 0) {
                comboCategorie = new JComboBox(nomiCategorie);
                AutoCompletion.enable(comboCategorie);

                Object[] oggetti = new Object[]{"Seleziona la categoria di attuatori da rimuovere:  ", comboCategorie};

                int ok = JOptionPane.showOptionDialog(null, oggetti, "Rimozione Categoria Attuatori", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                if (ok == 0) {
                    if (inter.rimuoviCategoriaAttuatore(nomiCategorie[comboCategorie.getSelectedIndex()]))
                        JOptionPane.showConfirmDialog(null, "Categoria Attuatori rimoossa con successo", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showConfirmDialog(null, "Categoria Attuatori non rimossa", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showOptionDialog(null, "Non sono presenti categorie di attuatori", "Assenza categorie di attuatori da rimuovere", -1, 1, null, null, null);
                return;
            }
        }
    }
}
