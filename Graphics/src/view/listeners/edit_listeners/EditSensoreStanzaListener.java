package view.listeners.edit_listeners;

import domotix.controller.Interpretatore;
import domotix.controller.Rappresentatore;
import view.PannelloNord;
import view.listeners.utils.AutoCompletion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Listener per la gestione degli eventi legati all'aggiunta e alla rimozione di sensori da una stanza da selezionare
 */
public class EditSensoreStanzaListener implements ActionListener {
    private Interpretatore inter;
    private Rappresentatore rapp;
    private PannelloNord pannelloNord;

    public EditSensoreStanzaListener(Interpretatore inter, Rappresentatore rapp, PannelloNord pannelloNord) {
        this.inter = inter;
        this.rapp = rapp;
        this.pannelloNord = pannelloNord;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equalsIgnoreCase("Aggiungi un sensore ad una stanza")){ //aggiungi sensore a stanza
            String[] nomiStanze = rapp.getNomiStanze(pannelloNord.getUnitaCorrente(), true);
            JComboBox comboNomiStanze = new JComboBox(nomiStanze);
            AutoCompletion.enable(comboNomiStanze);
            String[] nomiCategorie = rapp.getNomiCategorieSensori();
            JComboBox comboCategorie = new JComboBox(nomiCategorie);
            AutoCompletion.enable(comboCategorie);
            JTextField campoNome = new JTextField(20);
            Object[] oggetti = new Object[]{
                    "Seleziona una stanza a cui aggiungere il nuovo sensore: ",
                    comboNomiStanze,
                    "Seleziona la categoria del nuovo sensore: ",
                    comboCategorie,
                    "Inserisci il nome del nuovo sensore: ",
                    campoNome
            };
            int ok = JOptionPane.showOptionDialog(null, oggetti, "Aggiunta sensore ad una stanza", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if(ok == 0){
                if(inter.aggiungiSensore(campoNome.getText(), nomiCategorie[comboCategorie.getSelectedIndex()], nomiStanze[comboNomiStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente()))
                    JOptionPane.showConfirmDialog(null, "Sensore inserito con successo", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showConfirmDialog(null, "Sensore non inserito", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        }else{ //rimozione sensore da stanza
            String[] nomiStanze = rapp.getNomiStanze(pannelloNord.getUnitaCorrente(), true);
            JComboBox comboNomiStanze = new JComboBox(nomiStanze);
            AutoCompletion.enable(comboNomiStanze);

            JButton selezionaStanza = new JButton("Seleziona stanza");

            JPanel alto = new JPanel(new GridLayout(2,2));
            alto.add(new JLabel("Seleziona una stanza da cui rimuovere un sensore: "));
            alto.add(new JLabel(""));
            alto.add(comboNomiStanze);
            alto.add(selezionaStanza);

            JDialog dialog = new JDialog();
            dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            dialog.setLocationRelativeTo(null);
            dialog.getContentPane().add(alto, BorderLayout.NORTH);
            dialog.pack();

            selezionaStanza.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    comboNomiStanze.setEnabled(false);
                    selezionaStanza.setEnabled(false);
                    String[] nomiSensori = rapp.getNomiSensori(nomiStanze[comboNomiStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente());
                    if(nomiSensori.length > 0) {
                        JComboBox comboSensori = new JComboBox(nomiSensori);
                        AutoCompletion.enable(comboSensori);

                        JButton selezionaSensore = new JButton("Seleziona sensore");

                        JPanel medio = new JPanel(new GridLayout(2, 2));
                        medio.add(new JLabel("Seleziona un sensore da rimuovere: "));
                        medio.add(new JLabel(""));
                        medio.add(comboSensori);
                        medio.add(selezionaSensore);

                        dialog.getContentPane().add(medio, BorderLayout.CENTER);
                        dialog.pack();

                        selezionaSensore.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (inter.rimuoviSensore(nomiSensori[comboSensori.getSelectedIndex()], nomiStanze[comboNomiStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente()))
                                    JOptionPane.showConfirmDialog(null, "Sensore rimosso con successo", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                                else
                                    JOptionPane.showConfirmDialog(null, "Sensore non rimosso", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                                dialog.dispose();
                            }
                        });//qui sotto
                    }else{
                        JOptionPane.showOptionDialog(null, "Non sono presenti sensori da rimuovere", "Impossibile rimuovere sensori", -1, 1, null, null, null);
                        dialog.dispose();
                        return;
                    }
                }
            });
            dialog.setVisible(true);
        }
    }
}
