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
 * Listener per la gestione degli eventi legati al collegamento di sensori e attuatori ad una stanza da selezionare
 */
public class EditCollegaDispStanzaListener implements ActionListener {
    private Interpretatore inter;
    private Rappresentatore rapp;
    private PannelloNord pannelloNord;

    public EditCollegaDispStanzaListener(Interpretatore inter, Rappresentatore rapp, PannelloNord pannelloNord) {
        this.inter = inter;
        this.rapp = rapp;
        this.pannelloNord = pannelloNord;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if(actionCommand.equalsIgnoreCase("Collega sensore esistente ad un'altra stanza")){
            String[] nomiStanze = rapp.getNomiStanze(pannelloNord.getUnitaCorrente());
            JComboBox comboStanze = new JComboBox(nomiStanze);
            AutoCompletion.enable(comboStanze);

            JButton selezionaStanza = new JButton("Scegli stanza");

            JPanel alto = new JPanel(new GridLayout(2,2));
            alto.add(new JLabel("Scegli una stanza a cui collegare un sensore"));
            alto.add(new JLabel(""));
            alto.add(comboStanze);
            alto.add(selezionaStanza);

            JDialog dialog = new JDialog();
            dialog.setTitle("Collegamento sensore preesistente ad una stanza");
            dialog.getContentPane().add(alto, BorderLayout.NORTH);
            dialog.setLocationRelativeTo(null);
            dialog.pack();
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            selezionaStanza.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    comboStanze.setEnabled(false);
                    selezionaStanza.setEnabled(false);

                    String[] nomiSensori = rapp.getNomiSensoriAggiungibiliStanza(nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente());
                    if(nomiSensori.length > 0) {
                        JComboBox comboSensori = new JComboBox(nomiSensori);

                        JButton collega = new JButton("Collega sensore");

                        JPanel medio = new JPanel(new GridLayout(2, 2));
                        medio.add(new JLabel("Scegli un sensore da collegare:"));
                        medio.add(new JLabel(""));
                        medio.add(comboSensori);
                        medio.add(collega);

                        dialog.getContentPane().add(medio, BorderLayout.CENTER);
                        dialog.pack();

                        collega.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                comboSensori.setEnabled(false);
                                collega.setEnabled(false);
                                if (inter.collegaSensore(nomiSensori[comboSensori.getSelectedIndex()], nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente()))
                                    JOptionPane.showConfirmDialog(null, "Sensore collegato con successo", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                                else
                                    JOptionPane.showConfirmDialog(null, "Sensore non collegato", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                                dialog.dispose();
                            }
                        });
                    }else{
                        JOptionPane.showOptionDialog(null, "Non sono presenti sensori collegabili alla stanza", "Impossibile collegare sensori", -1, 1, null, null, null);
                        dialog.dispose();
                        return;
                    }
                }
            });
            dialog.setVisible(true);
        }else{ //collegamento attuatore stanza
            String[] nomiStanze = rapp.getNomiStanze(pannelloNord.getUnitaCorrente(), true);
            JComboBox comboStanze = new JComboBox(nomiStanze);
            AutoCompletion.enable(comboStanze);

            JButton selezionaStanza = new JButton("Scegli stanza");

            JPanel alto = new JPanel(new GridLayout(2,2));
            alto.add(new JLabel("Scegli una stanza a cui collegare un attuatore"));
            alto.add(new JLabel(""));
            alto.add(comboStanze);
            alto.add(selezionaStanza);

            JDialog dialog = new JDialog();
            dialog.setTitle("Collegamento attuatore preesistente ad una stanza");
            dialog.getContentPane().add(alto, BorderLayout.NORTH);
            dialog.setLocationRelativeTo(null);
            dialog.pack();
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            selezionaStanza.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    comboStanze.setEnabled(false);
                    selezionaStanza.setEnabled(false);

                    String[] nomiAttuatori = rapp.getNomiAttuatoriAggiungibiliStanza(nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente());
                    if(nomiAttuatori.length > 0) {
                        JComboBox comboAttuatori = new JComboBox(nomiAttuatori);

                        JButton collega = new JButton("Collega attuatore");

                        JPanel medio = new JPanel(new GridLayout(2, 2));
                        medio.add(new JLabel("Scegli un attuatore da collegare:"));
                        medio.add(new JLabel(""));
                        medio.add(comboAttuatori);
                        medio.add(collega);

                        dialog.getContentPane().add(medio, BorderLayout.CENTER);
                        dialog.pack();

                        collega.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                comboAttuatori.setEnabled(false);
                                collega.setEnabled(false);
                                if (inter.collegaAttuatore(nomiAttuatori[comboAttuatori.getSelectedIndex()], nomiStanze[comboStanze.getSelectedIndex()], pannelloNord.getUnitaCorrente()))
                                    JOptionPane.showConfirmDialog(null, "Attuatore collegato con successo", "Successo operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                                else
                                    JOptionPane.showConfirmDialog(null, "Attuatore non collegato", "Fallimento operazione", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                                dialog.dispose();
                            }
                        });//qui sotto
                    }else{
                        JOptionPane.showOptionDialog(null, "Non sono presenti attuatori collegabili alla stanza", "Impossibile collegare attuatori", -1, 1, null, null, null);
                        dialog.dispose();
                        return;
                    }
                }
            });
            dialog.setVisible(true);
        }

    }
}
